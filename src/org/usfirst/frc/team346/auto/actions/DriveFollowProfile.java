package org.usfirst.frc.team346.auto.actions;

import org.usfirst.frc.team346.robot.RobotMap;
import org.usfirst.frc.team346.subsystems.Drive;
import org.usfirst.frc.team346.subsystems.Gyro;

import edu.wpi.first.wpilibj.DriverStation;

public class DriveFollowProfile implements Runnable {

	private Drive sDrive;
	private Gyro sGyro;
	
	private final double kUpdateFreq, kCourseDistanceSetpoint, kAccelSetpointMag, kDecelSetpointMag;
	private double kVelocitySetpointMag, mSetDriveVelocity;
	
	private final double mTimeZero, mTimeOutSec, mThresholdSec, mCourseDistanceThreshold;
	private double mThresholdCountdown;
	private boolean mPhaseDecelCompleteCountdownStarted;
	
	private boolean mIsDriving, mPhaseAccelComplete, mPhaseCruiseComplete, mPhaseDecelComplete;
	
	private double mTimePrev, mTimeCurr, mTimeDelta, mTimeInCurrPhase;
	private double mHeadingCurrent;
	private double mDistancePrev, mDistanceCurr, mDistanceDelta;
	private double mVelocityPrev, mVelocityCurr, mVelocityDelta;
	
	private double mCourseDistanceDelta, mCourseDistanceTotal, mCourseDistanceRemaining;
	private double mCourseErrorDelta, mCourseErrorTotal;
	
	private double mExpectedCourseDistancePhaseAccel, mExpectedCourseDistancePhaseCruise, mExpectedCourseDistancePhaseDecel;
	
	public DriveFollowProfile(double _distanceFt, double _velocityFtPerDs) {
		this.mTimeZero = System.currentTimeMillis()/1000.;
		this.kUpdateFreq = 0.02;
		this.mTimeOutSec = 10.;
		this.mThresholdSec = 0.2;
		this.mCourseDistanceThreshold = 0.25;
		
		this.kCourseDistanceSetpoint = _distanceFt;
		
		this.kVelocitySetpointMag = _velocityFtPerDs;
		this.mPhaseDecelCompleteCountdownStarted = false;
		
		this.kAccelSetpointMag = RobotMap.kDriveFollowAccelSetpoint;
		this.kDecelSetpointMag = RobotMap.kDriveFollowAccelSetpoint;
		
		this.sDrive = Drive.getInstance();
		this.sGyro = Gyro.getInstance();
	}
	
	public void run() {
		this.phaseInit();
		while(this.isDriving()) {
			long lWaitTime = System.currentTimeMillis();
			while(System.currentTimeMillis() - lWaitTime < this.kUpdateFreq * 1000.) {
			}
			this.cycleUpdate();
		};
	}
	
	private void cycleUpdate() {
		this.checkDisabled();
		this.updateValues();
		
		if(!this.mPhaseAccelComplete) {
			this.phaseAccel();
		}
		else if(!this.mPhaseCruiseComplete) {
			this.phaseCruise();
		}
		else if(!this.mPhaseDecelComplete) {
			this.phaseDecel();
		}
		else {
			this.phaseStop();
		}
	}
	
	private void phaseInit() {
		this.mIsDriving = true;
		this.mPhaseAccelComplete = false;
		this.mPhaseCruiseComplete = false;
		this.mPhaseDecelComplete = false;
		
		
		this.sDrive.zeroEncoders();
		this.sGyro.zeroGyro();
		
		this.mTimePrev = System.currentTimeMillis()/1000.;
		this.mDistancePrev = this.sDrive.getAveragedPosition();
		this.mVelocityPrev = this.sDrive.getAveragedVelocity();
		
		this.mExpectedCourseDistancePhaseAccel = 1./2. * this.kVelocitySetpointMag * this.kVelocitySetpointMag / this.kAccelSetpointMag;
		this.mExpectedCourseDistancePhaseDecel = 1./2. * this.kVelocitySetpointMag * this.kVelocitySetpointMag / this.kDecelSetpointMag;
		this.mExpectedCourseDistancePhaseCruise = this.kCourseDistanceSetpoint - this.mExpectedCourseDistancePhaseAccel - this.mExpectedCourseDistancePhaseDecel;
		
		this.checkTriangleProfile();
	}
	
	private void checkTriangleProfile() {
		if(this.mExpectedCourseDistancePhaseCruise < 0) {
			this.kVelocitySetpointMag = Math.sqrt(2. * this.kCourseDistanceSetpoint / (1. / this.kAccelSetpointMag + 1. / this.kDecelSetpointMag));
			this.mExpectedCourseDistancePhaseAccel = 1./2. * this.kVelocitySetpointMag * this.kVelocitySetpointMag / this.kAccelSetpointMag;
			this.mExpectedCourseDistancePhaseDecel = 1./2. * this.kVelocitySetpointMag * this.kVelocitySetpointMag / this.kDecelSetpointMag;
			this.mExpectedCourseDistancePhaseCruise = 0;
		}
	}
	
	private void phaseAccel() {
		this.mSetDriveVelocity = this.kAccelSetpointMag * this.mTimeInCurrPhase;
		
		if(Math.abs(this.mSetDriveVelocity) < this.kVelocitySetpointMag) {
			this.setDriveVelocity(this.mSetDriveVelocity);
		}
		else {
			this.setDriveVelocity(this.kVelocitySetpointMag);
		}
		
		if(this.mCourseDistanceTotal >= this.mExpectedCourseDistancePhaseAccel) {
			this.mTimeInCurrPhase = 0.;
			this.mPhaseAccelComplete = true;
		}
	}
	
	private void phaseCruise() {
		this.mSetDriveVelocity = this.kVelocitySetpointMag;
		
		this.setDriveVelocity(this.mSetDriveVelocity);
		
		if(this.mCourseDistanceRemaining <= this.mExpectedCourseDistancePhaseDecel) {
			this.mTimeInCurrPhase = 0.;
			this.mPhaseCruiseComplete = true;
		}
	}
	
	private void phaseDecel() {
		this.mSetDriveVelocity = this.kVelocitySetpointMag - this.kDecelSetpointMag * this.mTimeInCurrPhase;
		
		if(Math.abs(this.mCourseDistanceRemaining) < this.mCourseDistanceThreshold) {
			this.setDriveVelocity(0);
		}
		else if(this.mCourseDistanceRemaining >= 0) {
			if(this.mSetDriveVelocity > 0) {
				this.setDriveVelocity(this.mSetDriveVelocity);
			}
			else {
				this.setDriveVelocity(RobotMap.kDriveFollowMinVelocity);
			}
		}
		else {
			this.setDriveVelocity(-RobotMap.kDriveFollowMinVelocity);
		}
		
		//Countdown
		if(Math.abs(this.sDrive.getAveragedVelocity()) < RobotMap.kDriveFollowMinVelocity) {
			if(!this.mPhaseDecelCompleteCountdownStarted) {
				this.mThresholdCountdown = System.currentTimeMillis();
				this.mPhaseDecelCompleteCountdownStarted = true;
			}
			if(System.currentTimeMillis() - this.mThresholdCountdown > this.mThresholdSec * 1000.) {
				this.mTimeInCurrPhase = 0.;
				this.mPhaseDecelComplete = true;
			}
		}
		else if(System.currentTimeMillis() - this.mThresholdCountdown > this.mThresholdSec * 1000.) {
			this.mThresholdCountdown = System.currentTimeMillis();
			this.mPhaseDecelCompleteCountdownStarted = false;
		}
	}
	
	private void phaseStop() {
		this.setDriveVelocity(0);
		this.mIsDriving = false;
	}
	
	private void updateValues() {
		this.mTimeCurr = System.currentTimeMillis()/1000.;
		this.mTimeDelta = this.mTimeCurr - this.mTimePrev;
		
		this.mHeadingCurrent = this.sGyro.getAngle();
		
		this.mDistanceCurr = this.sDrive.getAveragedPosition();
		this.mDistanceDelta = this.mDistanceCurr - this.mDistancePrev;
		
		this.mVelocityCurr = this.sDrive.getAveragedVelocity();
		this.mVelocityDelta = this.mVelocityCurr - this.mVelocityPrev;
		
		this.mCourseDistanceDelta = this.mDistanceDelta * Math.cos(Math.toRadians(this.mHeadingCurrent));	//course arc-length travelled so far
		this.mCourseDistanceTotal += this.mCourseDistanceDelta;
		
		this.mCourseErrorDelta = this.mDistanceDelta * Math.sin(Math.toRadians(this.mHeadingCurrent));		//perpendicular error away from course
		this.mCourseErrorTotal += this.mCourseErrorDelta;
		
		this.mCourseDistanceRemaining = this.kCourseDistanceSetpoint - this.mCourseDistanceTotal;
	}
	
	private void setDriveVelocity(double _setVelocityFtPerDs) {
		//TODO implement this from DriveFollow
	}
	
	public boolean isDriving() {
		return this.mIsDriving;
	}
	
	private void checkDisabled() {
		if(System.currentTimeMillis()/1000. - this.mTimeZero > this.mTimeOutSec || DriverStation.getInstance().isDisabled()) {
			System.out.println("Drive Follow Profile| timeout");
			this.phaseStop();
		}
	}

}