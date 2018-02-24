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
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveFollow implements Runnable {

	private Drive sDrive;
	private Gyro sGyro;
	
	private double timeZero, timePrev, timeCurr, timeDelta, timePrevPublish;
	private double headingCurr;
	private double distancePrev, distanceCurr, distanceDelta;
	private double velocityPrev, velocityCurr, velocityDelta, velocitySetLeft, velocitySetRight;
	private double courseDistanceDelta, courseDistanceTotal, courseDistanceRemaining;
	private double courseErrorDelta, courseErrorTotal;
	
	private final double courseDistanceSetpoint, velocitySetpointMag;
	
	private boolean inThresholdCountdownBegun;
	private double timeInThreshold;
	private final double thresholdTimeOutSec, thresholdVelocity, timeOutSec, updateFreq;
	
	private boolean isActive;
	private boolean isDrivingForward;
	
	/**The DriveFollow object aims to drive along a course, correcting for any accumulated area off-course
	 * in order to return to the original course. This maintains the correct distance driven along the course
	 * as well as the correct heading along the course.**/
	public DriveFollow(double _distanceFt, double _velocityFtPerDs) {
		System.out.println("DriveF| created: d:" + _distanceFt + ",v:" + _velocityFtPerDs);
		
		this.sDrive = Drive.getInstance();
		this.sGyro = Gyro.getInstance();
		
		this.timeOutSec = 10.;
		this.updateFreq = 0.01;
		this.thresholdTimeOutSec = 0.25;
		this.thresholdVelocity = 0.1 * RobotMap.kDriveVelAverage;
		
		this.courseDistanceSetpoint = _distanceFt * 1024.;
		this.velocitySetpointMag = Math.abs(_velocityFtPerDs);
		
		this.isDrivingForward = (this.courseDistanceSetpoint >= 0);
	}
	
	public void run() {
		System.out.println("DriveF| run");
		this.init();
		
		while(this.isDriving()) {
			long lWaitTime = System.currentTimeMillis();
			while(System.currentTimeMillis() - lWaitTime < this.updateFreq * 1000.) {
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
		this.sGyro.zeroGyro();
		
		this.timePrev = System.currentTimeMillis()/1000;
		this.distancePrev = this.sDrive.getAveragedPosition();
		this.velocityPrev = this.sDrive.getAveragedVelocity();
	}

	/**This method is run every cycle, calculating the error and adjusting the drive accordingly.**/
	public void updateCycle() {
		this.checkDisabled();
		this.updateValues();
		this.publishValues();
		
		double lSigmoidOutput = reverseModSigmoid(this.courseDistanceSetpoint/1024., this.courseDistanceTotal/1024.)
									* (this.isDrivingForward ? 1. : -1.);
		this.setDriveVelocity(lSigmoidOutput);
		
		this.checkCompletionVelocity();
		this.updatePrevValues();
	}
	
	/**To be run at the beginning of each cycle for use in determining deltas since the previous cycle.**/
	private void updateValues() {
		this.timeCurr = System.currentTimeMillis()/1000.;
		this.timeDelta = this.timeCurr - this.timePrev;
		
		this.headingCurr = this.sGyro.getAngle();
		
		this.distanceCurr = this.sDrive.getAveragedPosition();
		this.distanceDelta = this.distanceCurr - this.distancePrev;
		
		this.velocityCurr = this.sDrive.getAveragedVelocity();
		this.velocityDelta = this.velocityCurr - this.velocityPrev;
		
		this.courseDistanceDelta = this.distanceDelta * Math.cos(Math.toRadians(this.headingCurr)) * (this.isDrivingForward ? 1. : -1.);	//course arc-length travelled so far
		this.courseErrorDelta = this.distanceDelta * Math.sin(Math.toRadians(this.headingCurr)) * (this.isDrivingForward ? 1. : -1.);		//perpendicular error away from course
		
		this.courseDistanceTotal += this.courseDistanceDelta;
		this.courseErrorTotal += this.courseErrorDelta;
		
		this.courseDistanceRemaining = this.courseDistanceSetpoint - this.courseDistanceTotal * (this.isDrivingForward ? 1. : -1.);
	}
	
	/**To be run at the end of each cycle to inform the next cycle of the previous values.**/
	private void updatePrevValues() {
		this.timePrev = this.timeCurr;
		this.distancePrev = this.distanceCurr;
		this.velocityPrev = this.velocityCurr;
	}
	
	private void publishValues() {
		if(System.currentTimeMillis() - this.timePrevPublish > 250) {
			System.out.println("currT:" + (this.timeCurr - this.timeZero) + " currH:" + this.headingCurr + " currD:" + this.distanceCurr + " currV:" + this.velocityCurr);
			System.out.println("courseDT:" + this.courseDistanceTotal + " courseET:" + this.courseErrorTotal + " courseDR:" + this.courseDistanceRemaining);
			System.out.println("leftV:" + this.velocitySetLeft + " rightV" + this.velocitySetRight);
			
			this.timePrevPublish = System.currentTimeMillis();
		}
	}
	
	private void setDriveVelocity(double _courseOutput) {
		this.velocitySetLeft = _courseOutput * this.velocitySetpointMag;
		this.velocitySetRight = _courseOutput * this.velocitySetpointMag;
		
		double lSign, rSign;
		if(this.isDrivingForward) {
			if(_courseOutput >= 0) {
				if(this.courseErrorTotal >= 0) {
					lSign = 0.;
					rSign = 1.;
				}
				else {
					lSign = 1.;
					rSign = 0.;
				}
			}
			else {
				if(this.courseErrorTotal >= 0) {
					lSign = 0.;
					rSign = 1.;
				}
				else {
					lSign = 1.;
					rSign = 0.;
				}
			}
		}
		else {
			if(_courseOutput >= 0) {
				if(this.courseErrorTotal >= 0) {
					lSign = 0.;
					rSign = -1.;
				}
				else {
					lSign = -1.;
					rSign = 0.;
				}
			}
			else {
				if(this.courseErrorTotal >= 0) {
					lSign = 0.;
					rSign = -1.;
				}
				else {
					lSign = -1.;
					rSign = 0.;
				}
			}
		}
		
		this.velocitySetLeft = _courseOutput * this.velocitySetpointMag
								* (1 + (lSign * RobotMap.kDriveFollowErrorScalerMultiplier * Math.abs(this.courseErrorTotal/this.courseDistanceSetpoint)) );	//TODO was errorTotal/1024
		this.velocitySetRight = _courseOutput * this.velocitySetpointMag
								* (1 + (rSign * RobotMap.kDriveFollowErrorScalerMultiplier * Math.abs(this.courseErrorTotal/this.courseDistanceSetpoint)) );	//TODO was errorTotal/1024
		
		if(this.inThresholdCountdownBegun) {
			this.velocitySetLeft = 0.;
			this.velocitySetRight = 0.;
		}
		
		this.sDrive.drive(DriveMode.VELOCITY, this.velocitySetLeft, this.velocitySetRight);
	}
	
	private void checkCompletionVelocity() {
		this.checkDisabled();
//		if(Math.abs(this.courseDistanceRemaining) < Math.abs(this.courseDistanceSetpoint/2.)) {		TODO check below
		if(Math.abs(this.courseDistanceTotal/1024.) <= 1) {
				return;
		}
		
		if(Math.abs(this.velocityCurr) < Math.abs(this.thresholdVelocity)) {
			if(!this.inThresholdCountdownBegun) {
				System.out.println("Velocity countdown started");
				this.timeInThreshold = System.currentTimeMillis()/1000.;
				this.inThresholdCountdownBegun = true;
			}
			if(System.currentTimeMillis()/1000. - this.timeInThreshold > this.thresholdTimeOutSec) {
				System.out.println("Drive Follow| complete via velocity threshold");
				this.stop();
			}
		}
		else if(System.currentTimeMillis()/1000. - this.timeInThreshold > this.thresholdTimeOutSec) {
			System.out.println("Velocity countdown aborted");
			this.timeInThreshold = System.currentTimeMillis()/1000.;
			this.inThresholdCountdownBegun = false;
		}
	}
	
	private void stop() {
		System.out.println("DriveF| driving = false");
		this.setDriveVelocity(0);
		this.isActive = false;
	}
	
	private void checkDisabled() {
		if(System.currentTimeMillis()/1000. - this.timeZero > this.timeOutSec || DriverStation.getInstance().isDisabled() || !DriverStation.getInstance().isAutonomous()) {
			System.out.println("Drive Follow| timeout or disabled");
			this.stop();
		}
	}
	
	public boolean isDriving() {
		return this.isActive;
	}
	
	private double reverseSigmoid(double _x) {
		return 1. /(1. + Math.pow(Math.E, _x ));
	}
	
	private double reverseModSigmoid(double _width, double _x) {
		double lWidthScaler = 12. /Math.abs(_width);
		double lTranslator = Math.abs(_width) /2.;
		return 1. /(1. + Math.pow(Math.E, lWidthScaler * (_x - lTranslator) ));
	}

}