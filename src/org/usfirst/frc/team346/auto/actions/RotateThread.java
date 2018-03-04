package org.usfirst.frc.team346.auto.actions;

import org.usfirst.frc.team346.robot.RobotMap;
import org.usfirst.frc.team346.subsystems.Drive;
import org.usfirst.frc.team346.subsystems.Gyro;
import org.usfirst.frc.team346.subsystems.Drive.DriveMode;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.Preferences;

public class RotateThread implements Runnable {

	private Drive sDrive;
	private Gyro sGyro;
	private Preferences pref;
	
	private double timeZero, timePrev, timeCurr, timeDelta, timePrevPublish;
	private double leftVelocityCurr, rightVelocityCurr;
	private double velocitySetLeft, velocitySetRight, outputPublish;
	private double anglePrev, angleCurr, angleRemaining;
	
	private Hand side;
	private double leftEnabled, rightEnabled, minVelocity;
	private final double angleSetpoint, velocitySetpointMag;
	
	private boolean inThresholdCountdownBegun;
	private double timeEnteredThreshold;
	private final double thresholdTimeOutSec, thresholdVelocity, timeOutSec, updateFreq;
	
	private boolean isActive;
	private boolean isRotatingClockwise;
	
	public RotateThread(double _angleDegrees, double _velocityPercent) {
		this.timeZero = System.currentTimeMillis()/1000.;
		this.checkDisabled();
		
		this.sDrive = Drive.getInstance();
		this.sGyro = Gyro.getInstance();
		this.pref = Preferences.getInstance();
		
		this.timeOutSec = 5.;
		this.updateFreq = 0.02;
		this.thresholdTimeOutSec = 0.125;
		this.thresholdVelocity = 0.05 * RobotMap.kDriveVelAverage;
		
		this.leftEnabled = 1.;
		this.rightEnabled = 1.;
		
		this.angleSetpoint = _angleDegrees;
		this.velocitySetpointMag = Math.abs(_velocityPercent);						//TODO: velocity isn't used right now
		this.minVelocity = RobotMap.kDriveVelAverage * 0.2;
		
		this.isRotatingClockwise = (this.angleSetpoint >= 0);
		
		this.isActive = true;
	}
	
	public RotateThread(Hand _side, double _angleDegrees, double _velocityPercent) {
		this.timeZero = System.currentTimeMillis()/1000.;
		this.checkDisabled();
		
		this.sDrive = Drive.getInstance();
		this.sGyro = Gyro.getInstance();
		this.pref = Preferences.getInstance();
		
		this.timeOutSec = 5.;
		this.updateFreq = 0.02;
		this.thresholdTimeOutSec = 0.125;
		this.thresholdVelocity = 0.05 * RobotMap.kDriveVelAverage;
		
		this.side = _side;
		this.leftEnabled = (this.side == Hand.kLeft) ? 1. : 0.65;
		this.rightEnabled = (this.side == Hand.kRight) ? 1. : 0.65;
		
		this.angleSetpoint = _angleDegrees;
		this.velocitySetpointMag = Math.abs(_velocityPercent);						//TODO: velocity isn't used right now
		
		this.isRotatingClockwise = (this.angleSetpoint >= 0);
		
		this.isActive = true;
	}
	
	public void run() {
		this.timeZero = System.currentTimeMillis()/1000.;
		this.checkDisabled();
		System.out.println("RotateT| rotate " + this.angleSetpoint);
		this.init();
		
		while(this.isRotating()) {
			long lWaitTime = System.currentTimeMillis();
			while(System.currentTimeMillis() - lWaitTime < this.updateFreq * 1000.) {
				this.checkDisabled();
			}
			this.updateCycle();
		}
		
		System.out.println("DriveF| run complete");
	}
	
	private void init() {
		this.timeZero = System.currentTimeMillis()/1000.;
		this.isActive = true;
		this.inThresholdCountdownBegun = false;
		
		this.sDrive.zeroEncoders();
		this.sDrive.enable();
		this.sDrive.setSpeedPIDs();
		this.sGyro.zeroGyro();
		
		this.timePrev = System.currentTimeMillis()/1000;
		this.anglePrev = this.sGyro.getAngle();
	}
	
	public void updateCycle() {
		this.checkDisabled();
		this.updateValues();
		this.publishValues();
		
		double lSigmoidOutput = reverseModSigmoid(this.angleSetpoint, this.angleCurr)
									* (this.isRotatingClockwise ? 1. : -1.);
		this.setDriveVelocity(lSigmoidOutput);
		
		this.checkCompletionVelocity();
		this.updatePrevValues();
	}
	
	private void updateValues() {
		this.timeCurr = System.currentTimeMillis()/1000.;
		this.timeDelta = this.timeCurr - this.timePrev;
		
		this.angleCurr = this.sGyro.getAngle();
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
			System.out.println("currT:" + (this.timeCurr - this.timeZero) + " angleC:" + this.angleCurr + " angleR:" + this.angleRemaining);
			System.out.println("leftV:" + this.velocitySetLeft + " rightV:" + this.velocitySetRight + " output:" + this.outputPublish);
			
			this.timePrevPublish = System.currentTimeMillis();
		}
	}
	
	private void setDriveVelocity(double _courseOutput) {
		this.checkDisabled();
		
		this.velocitySetLeft = _courseOutput * this.velocitySetpointMag;
		this.velocitySetRight = _courseOutput * this.velocitySetpointMag;
		this.outputPublish = _courseOutput;
		
		double lSign = (this.isRotatingClockwise) ? 1. : -1.;
		double rSign = (this.isRotatingClockwise) ? -1. : 1.;
		
		this.velocitySetLeft = this.velocitySetpointMag * _courseOutput * lSign * this.leftEnabled;
		this.velocitySetRight = this.velocitySetpointMag * _courseOutput * rSign * this.rightEnabled;
		
//		if(Math.abs(this.velocitySetLeft) < this.minVelocity) {
//			this.velocitySetLeft = this.minVelocity * lSign * this.leftEnabled;
//		}
//		if(Math.abs(this.velocitySetRight) < this.minVelocity) {
//			this.velocitySetRight = this.minVelocity * lSign * this.rightEnabled;
//		}
		
		if(this.inThresholdCountdownBegun) {
			this.velocitySetLeft = 0.;
			this.velocitySetRight = 0.;
		}
		
		this.sDrive.drive(DriveMode.PERCENT_VELOCITY, this.velocitySetLeft, this.velocitySetRight);
	}
	
	private void checkCompletionVelocity() {
		this.checkDisabled();
		if(System.currentTimeMillis()/1000. - this.timeZero < 0.75) {
				return;
		}
		
		if(Math.abs(this.leftVelocityCurr) < Math.abs(this.thresholdVelocity)
		&& Math.abs(this.rightVelocityCurr) < Math.abs(this.thresholdVelocity)) {
			if(!this.inThresholdCountdownBegun) {
				this.timeEnteredThreshold = System.currentTimeMillis()/1000.;
				this.inThresholdCountdownBegun = true;
			}
			if(System.currentTimeMillis()/1000. - this.timeEnteredThreshold > this.thresholdTimeOutSec) {
				System.out.println("RotateT| complete via velocity threshold");
				this.stop();
			}
		}
		else if(System.currentTimeMillis()/1000. - this.timeEnteredThreshold > this.thresholdTimeOutSec) {
			this.timeEnteredThreshold = System.currentTimeMillis()/1000.;
			this.inThresholdCountdownBegun = false;
		}
	}
	
	private void stop() {
		this.setDriveVelocity(0);
		this.isActive = false;
		Thread.currentThread().interrupt();
	}
	
	private void checkDisabled() {
		if(System.currentTimeMillis()/1000. - this.timeZero > this.timeOutSec || DriverStation.getInstance().isDisabled() || !DriverStation.getInstance().isAutonomous()) {
			System.out.println("RotateT| timeout or disabled");
			this.stop();
		}
	}
	
	public boolean isRotating() {
		this.checkDisabled();
		return this.isActive;
	}
	
	private double reverseModSigmoid(double _width, double _x) {
		double lAngleWidthScaler;
		lAngleWidthScaler = 2;
		
		double lStopAngle;
		lStopAngle = 15;
		
		double lTranslator = -Math.abs(_width) + lStopAngle;
		return 1. /(1. + Math.pow(Math.E, lAngleWidthScaler * (_x + lTranslator) ));
	}
	
}