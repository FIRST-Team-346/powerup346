package org.usfirst.frc.team346.auto.actions;

import org.usfirst.frc.team346.robot.RobotMap;
import org.usfirst.frc.team346.subsystems.Drive;
import org.usfirst.frc.team346.subsystems.Gyro;
import org.usfirst.frc.team346.subsystems.Drive.DriveMode;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Preferences;

/*
 * Read the comments on DriveFollow before these. DriveFollow is actually nicely organized and written,
 * as opposed to the mess here. They follow similar concepts, but this one uses PIDs, which always makes
 * things more complicated.
 */
public class RotateThread implements Runnable {

	private Drive sDrive;
	private Gyro sGyro;
//	private Preferences pref;
	
	private PIDController anglePIDController;
	private PIDSource anglePIDSource;
	private PIDOutput anglePIDOutput;
	
	private double timeZero, timePrev, timeCurr, timeDelta, timePrevPublish;
	private double leftVelocityCurr, rightVelocityCurr;
	private double velocitySetLeft, velocitySetRight, outputPublish;
	private double anglePrev, angleCurr, angleRemaining;
	
	private Hand side;
	private double leftEnabled, rightEnabled, minVelocityPercent;
	private double angleSetpoint, velocitySetpointMag;
	
	private boolean inThresholdAngleCountdownBegun, inThresholdVelCountdownBegun, hasNeverEnteredAngleThreshold;
	private double timeEnteredAngleThreshold, timeEnteredVelThreshold;
	private double thresholdAngleTimeOutSec, thresholdVelTimeOutSec, timeOutSec, updateFreq;
	private double thresholdVelocity, thresholdAngle;
	
	private boolean isActive;
	private boolean isRotatingClockwise;
	
	public RotateThread(double _angleDegrees, double _velocityPercent) {
		this.setup();
		
		this.leftEnabled = 1.;
		this.rightEnabled = 1.;
		
		this.angleSetpoint = _angleDegrees;
		this.velocitySetpointMag = Math.abs(_velocityPercent);

		this.isRotatingClockwise = (this.angleSetpoint >= 0);
		
		this.isActive = true;
	}
	
	public RotateThread(Hand _side, double _angleDegrees, double _velocityPercent) {
		this.setup();
		
		this.side = _side;
		this.leftEnabled = (this.side == Hand.kLeft) ? 1. : 0.65;
		this.rightEnabled = (this.side == Hand.kRight) ? 1. : 0.65;
		
		this.angleSetpoint = _angleDegrees;
		this.velocitySetpointMag = Math.abs(_velocityPercent);
		
		this.isRotatingClockwise = (this.angleSetpoint >= 0);
		
		this.isActive = true;
	}
	
	private void setup() {
		this.timeZero = System.currentTimeMillis()/1000.;
		this.checkDisabled();
		
		this.sDrive = Drive.getInstance();
		this.sGyro = Gyro.getInstance();
//		this.pref = Preferences.getInstance();
		
		this.timeOutSec = 3.;
		this.updateFreq = 0.02;
		this.thresholdAngleTimeOutSec = 0.05;
		this.thresholdVelTimeOutSec = 0.5;
		this.thresholdVelocity = 0.01 * RobotMap.kDriveVelAverage;
		this.thresholdAngle = 1.5;
		
		this.minVelocityPercent = RobotMap.kRotateThreadVelMinPercent;
//		this.minVelocityPercent = this.pref.getDouble("rtMin", 0);		//TODO
		this.hasNeverEnteredAngleThreshold = true;
//		this.sGyro.zeroGyro();
	}
	
	public void run() {
		this.timeZero = System.currentTimeMillis()/1000.;
		this.isActive = true;
		if(this.isRotating()) {
			System.out.println("RotateT| rotate " + this.angleSetpoint);
			this.init();
			
			while(this.isRotating()) {
				long lWaitTime = System.currentTimeMillis();
				while(System.currentTimeMillis() - lWaitTime < this.updateFreq * 1000.) {
					this.checkDisabled();
				}
			}
			this.setAnglePID(0, 0, 0);
			this.anglePIDController.disable();
			this.sDrive.drive(DriveMode.PERCENT_VELOCITY, 0, 0);
		}
		System.out.println("RotateT| run complete");
	}
	
	private void init() {
		this.timeZero = System.currentTimeMillis()/1000.;
		this.inThresholdAngleCountdownBegun = false;
		this.inThresholdVelCountdownBegun = false;
		
		this.sDrive.zeroEncoders();
		this.sDrive.enable();
		this.sDrive.setSpeedPIDs();
		this.sGyro.zeroGyro();
		
		this.createPID();
		
		this.timePrev = System.currentTimeMillis()/1000;
		this.anglePrev = this.sGyro.getAngle();
		
		this.anglePIDController.setSetpoint(this.angleSetpoint);
		this.anglePIDController.enable();
	}
	
	public void updateCycle(double _outputPercent) {
		this.checkDisabled();
		this.updateValues();
		this.publishValues();
		
//		double lSigmoidOutput = reverseModSigmoid(this.angleSetpoint, this.angleCurr)
//									* (this.isRotatingClockwise ? 1. : -1.);
//		this.setDriveVelocity(lSigmoidOutput);
		this.setDriveVelocity(_outputPercent);
		
		this.checkCompletionVelocity();
		this.updatePrevValues();
	}
	
	private void updateValues() {
		this.timeCurr = System.currentTimeMillis()/1000.;
		this.timeDelta = this.timeCurr - this.timePrev;
		
//		this.angleCurr = this.sGyro.getAngle();
		this.angleRemaining = this.angleSetpoint - this.angleCurr * (this.isRotatingClockwise ? 1. : -1.);
		
		this.leftVelocityCurr = this.sDrive.getLeftVelocity();
		this.rightVelocityCurr = this.sDrive.getRightVelocity();
	}
	
	private void updatePrevValues() {
		this.timePrev = this.timeCurr;
		this.anglePrev = this.angleCurr;
	}
	
	private void publishValues() {
		if(System.currentTimeMillis() - this.timePrevPublish > 500) {
//			System.out.println("currT:" + (this.timeCurr - this.timeZero) + " angleC:" + this.angleCurr + " angleR:" + this.angleRemaining);
//			System.out.println("leftV:" + this.velocitySetLeft + " rightV:" + this.velocitySetRight + " output:" + this.outputPublish);
			
			this.timePrevPublish = System.currentTimeMillis();
		}
	}
	
	/*
	 * This is similar to the velocity setting in DriveFollow. See which side is rotating which direction
	 * and then set their velocities based on that. If it's within the threshold, set velocities to 0.
	 */
	private void setDriveVelocity(double _courseOutput) {
		this.checkDisabled();
		
		this.velocitySetLeft = _courseOutput * this.velocitySetpointMag;
		this.velocitySetRight = _courseOutput * this.velocitySetpointMag;
		this.outputPublish = _courseOutput;
		
//		double lSign = (this.isRotatingClockwise) ? 1. : -1.;
//		double rSign = (this.isRotatingClockwise) ? -1. : 1.;
		
		double lSign = 1.;
		double rSign = -1.;
		
		this.velocitySetLeft = this.velocitySetpointMag * _courseOutput * lSign * this.leftEnabled;
		this.velocitySetRight = this.velocitySetpointMag * _courseOutput * rSign * this.rightEnabled;
		
		if(this.hasNeverEnteredAngleThreshold) {
			if(Math.abs(this.velocitySetLeft) < this.minVelocityPercent) {
				this.velocitySetLeft = this.minVelocityPercent * lSign * _courseOutput/Math.abs(_courseOutput) * this.leftEnabled;
			}
			if(Math.abs(this.velocitySetRight) < this.minVelocityPercent) {
				this.velocitySetRight = this.minVelocityPercent * rSign * _courseOutput/Math.abs(_courseOutput) * this.rightEnabled;
			}
		}
		
		if(this.inThresholdAngleCountdownBegun) {
			this.velocitySetLeft = 0.;
			this.velocitySetRight = 0.;
		}
		
		this.sDrive.drive(DriveMode.PERCENT_VELOCITY, this.velocitySetLeft, this.velocitySetRight);
	}
	
	/*
	 * This is very similar to the velocity countdown in DriveFollow. If the robot has stopped within
	 * an angle threshold for some time, then terminate. If the robot has stopped moving, or is moving
	 * really slowly because it's super close to the angle threshold, then wait some threshold time and
	 * stop regardless. This prevents a scenario where the bot is 1 degree off but waits 5 more seconds.
	 */
	private void checkCompletionVelocity() {
		this.checkDisabled();
		if(System.currentTimeMillis()/1000. - this.timeZero < 0.5) {
				return;
		}
		
		if( Math.abs(this.angleCurr - this.angleSetpoint) < Math.abs(this.thresholdAngle) ) {
			this.hasNeverEnteredAngleThreshold = false;
			if(!this.inThresholdAngleCountdownBegun) {
				this.timeEnteredAngleThreshold = System.currentTimeMillis()/1000.;
				this.inThresholdAngleCountdownBegun = true;
			}
			if(System.currentTimeMillis()/1000. - this.timeEnteredAngleThreshold > this.thresholdAngleTimeOutSec) {
				System.out.println("RotateT| complete via angle threshold");
				this.stop();
			}
		}
		else if(System.currentTimeMillis()/1000. - this.timeEnteredAngleThreshold > this.thresholdAngleTimeOutSec) {
			this.timeEnteredAngleThreshold = System.currentTimeMillis()/1000.;
			this.inThresholdAngleCountdownBegun = false;
		}
		
		if(1./2. *(Math.abs(this.leftVelocityCurr) + Math.abs(this.rightVelocityCurr)) < Math.abs(this.thresholdVelocity) ) {
			if(!this.inThresholdVelCountdownBegun) {
				this.timeEnteredVelThreshold = System.currentTimeMillis()/1000.;
				this.inThresholdVelCountdownBegun = true;
			}
			if(System.currentTimeMillis()/1000. - this.timeEnteredVelThreshold > this.thresholdVelTimeOutSec) {
				System.out.println("RotateT| complete via velocity threshold");
				this.stop();
			}
		}
		else if(System.currentTimeMillis()/1000. - this.timeEnteredVelThreshold > this.thresholdVelTimeOutSec) {
			this.timeEnteredVelThreshold = System.currentTimeMillis()/1000.;
			this.inThresholdVelCountdownBegun = false;
		}
	}
	
	private void stop() {
		this.isActive = false;
//		Thread.currentThread().interrupt();
	}
	
	private void checkDisabled() {
		if(System.currentTimeMillis()/1000. - this.timeZero > this.timeOutSec || DriverStation.getInstance().isDisabled() || !DriverStation.getInstance().isAutonomous()) {
//			System.out.println("RotateT| timeout or disabled");
			this.stop();
		}
	}
	
	public boolean isRotating() {
		this.checkDisabled();
		return this.isActive;
	}
	
	private void createPID() {
		this.anglePIDSource = new PIDSource() {		
			public void setPIDSourceType(PIDSourceType pidSource) {
			}
			public double pidGet() {
				angleCurr = sGyro.getAngle();
				return angleCurr;
			}
			public PIDSourceType getPIDSourceType() {
				return PIDSourceType.kDisplacement;
			}
		};
		this.anglePIDOutput = new PIDOutput() {
			public void pidWrite(double _output) {
				if(angleSetpoint == 0) {
					setDriveVelocity(0);
				}
				else {
					updateCycle(_output);
				}
			}
		};
		this.anglePIDController = new PIDController(RobotMap.kRotateP, RobotMap.kRotateI, RobotMap.kRotateD,
													this.anglePIDSource, this.anglePIDOutput, this.updateFreq);
//		this.setAnglePID(this.pref.getDouble("rotateP", 0), this.pref.getDouble("rotateI", 0), this.pref.getDouble("rotateD", 0));	//TODO
	}
	
	public void setAnglePID(double _P, double _I, double _D) {
		this.anglePIDController.setPID(_P, _I, _D);
	}
	
}