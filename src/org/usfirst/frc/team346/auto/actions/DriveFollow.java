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
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveFollow implements Runnable {

	private Drive sDrive;
	private Gyro sGyro;
	private Preferences pref;
	
	private double timeZero, timePrev, timeCurr, timeDelta, timePrevPublish;
	private double headingCurr;
	private double distancePrev, distanceCurr, distanceDelta;
	private double velocityPrev, velocityCurr, velocityDelta, velocitySetLeft, velocitySetRight, outputPublish;
	private double courseDistanceDelta, courseDistanceTotal, courseDistanceRemaining;
	private double courseErrorDelta, courseErrorTotal;
	
	private final double courseDistanceSetpoint, velocitySetpointMag;
	
	private boolean inThresholdCountdownBegun;
	private double timeEnteredThreshold;
	private final double thresholdTimeOutSec, thresholdVelocity, timeOutSec, updateFreq;
	
	private boolean isActive;
	private boolean isDrivingForward;
	
	/**The DriveFollow object aims to drive along a course, correcting for any accumulated area off-course
	 * in order to return to the original course. This maintains the correct distance driven along the course
	 * as well as the correct heading along the course.
	 * @param _velocityPercent use 0.6 for distances >= 5ft else 0.3**/
	public DriveFollow(double _distanceFt, double _velocityPercent) {
		System.out.println("DriveF| created: d:" + _distanceFt + ",v:" + _velocityPercent);
		this.timeZero = System.currentTimeMillis()/1000.;
		
		this.sDrive = Drive.getInstance();
		this.sGyro = Gyro.getInstance();
		this.pref = Preferences.getInstance();
		
		this.timeOutSec = 10.;
		this.updateFreq = 0.01;
		this.thresholdTimeOutSec = 0.125;
		this.thresholdVelocity = 0.05 * RobotMap.kDriveVelAverage;
		
		this.courseDistanceSetpoint = _distanceFt * 1024.;
//		this.velocitySetpointMag = Math.abs(_velocityPercent);						//TODO: velocity isn't used right now
		if(Math.abs(_distanceFt) >= 5.5) {
			this.velocitySetpointMag = 0.6;
		}
		else {
			this.velocitySetpointMag = 0.3;
		}
		
		this.isDrivingForward = (this.courseDistanceSetpoint >= 0);
		
		this.isActive = true;
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
//			System.out.println("currT:" + (this.timeCurr - this.timeZero) + " currH:" + this.headingCurr + " currD:" + (this.distanceCurr/1024.) + " currV:" + this.velocityCurr);
//			System.out.println("courseDT:" + (this.courseDistanceTotal/1024.) + " courseET:" + (this.courseErrorTotal/1024.) + " courseDR:" + (this.courseDistanceRemaining/1024.));
//			System.out.println("leftV:" + this.velocitySetLeft + " rightV:" + this.velocitySetRight + " output:" + this.outputPublish);
			
			this.timePrevPublish = System.currentTimeMillis();
		}
	}
	
	private void setDriveVelocity(double _courseOutput) {
		this.velocitySetLeft = _courseOutput * this.velocitySetpointMag;
		this.velocitySetRight = _courseOutput * this.velocitySetpointMag;
		this.outputPublish = _courseOutput;
		
		double lSign, rSign;
		if(this.isDrivingForward) {
			lSign = (this.headingCurr >= 0) ? -1. : 1.;
			rSign = (this.headingCurr >= 0) ? 1. : -1.;
		}
		else {
			lSign = (this.headingCurr >= 0) ? 1. : -1.;
			rSign = (this.headingCurr >= 0) ? -1. : 1.;
		}
		
		double multiplier = RobotMap.kDriveFollowAngleErrorScaler;
		
		this.velocitySetLeft = this.velocitySetpointMag * _courseOutput
				* (1 + (lSign * multiplier * Math.abs(this.headingCurr)) );
		this.velocitySetRight = this.velocitySetpointMag * _courseOutput
				* (1 + (rSign * multiplier * Math.abs(this.headingCurr)) );
		
		if(this.inThresholdCountdownBegun) {
			this.velocitySetLeft = 0.;
			this.velocitySetRight = 0.;
		}
		
		this.sDrive.drive(DriveMode.PERCENT_VELOCITY, this.velocitySetLeft, this.velocitySetRight);
	}
	
	private void checkCompletionVelocity() {
		this.checkDisabled();
		if(System.currentTimeMillis()/1000. - this.timeZero < 0.5) {
				return;
		}
		
		if(Math.abs(this.velocityCurr) < Math.abs(this.thresholdVelocity)) {
			if(!this.inThresholdCountdownBegun) {
				System.out.println("Velocity countdown started");
				this.timeEnteredThreshold = System.currentTimeMillis()/1000.;
				this.inThresholdCountdownBegun = true;
			}
			if(System.currentTimeMillis()/1000. - this.timeEnteredThreshold > this.thresholdTimeOutSec) {
				System.out.println("Drive Follow| complete via velocity threshold");
				this.stop();
			}
		}
		else if(System.currentTimeMillis()/1000. - this.timeEnteredThreshold > this.thresholdTimeOutSec) {
//			System.out.println("Velocity countdown aborted");
			this.timeEnteredThreshold = System.currentTimeMillis()/1000.;
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
		if(System.currentTimeMillis()/1000. - this.timeZero > this.timeOutSec || DriverStation.getInstance().isDisabled() || !DriverStation.getInstance().isAutonomous()) {
			System.out.println("DF| is not driving from timeouts");
			return false;
		}
		return this.isActive;
	}
	
	private double reverseModSigmoid(double _width, double _x) {
		double lWidthScaler;
		if(Math.abs(_width) >= 5.5) {
			lWidthScaler = 0.5;
		}
		else {
			lWidthScaler = 2.0;
		}
		
		double lStopDistance;
		if(Math.abs(_width) >= 5.5) {
			lStopDistance = 4.5;
		}
		else if(Math.abs(_width) > 1.5) {
			lStopDistance = 1.2;
		}
		else if(Math.abs(_width) == 1.5) {
			lStopDistance = 1.1;
		}
		else {
			lStopDistance = Math.abs(_width) /2.;
		}
		
		double lTranslator = -Math.abs(_width) + lStopDistance;
		return 1. /(1. + Math.pow(Math.E, lWidthScaler * (_x + lTranslator) ));
	}

}