package org.usfirst.frc.team346.auto.actions;

import org.usfirst.frc.team346.robot.RobotMap;
import org.usfirst.frc.team346.subsystems.Drive;
import org.usfirst.frc.team346.subsystems.Gyro;
import org.usfirst.frc.team346.subsystems.Drive.DriveMode;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveFollowProfile implements Runnable {

	private Drive sDrive;
	private Gyro sGyro;
	
	private final double kUpdateFreq, kCourseDistanceSetpoint, kAccelSetpointMag, kDecelSetpointMag;
	private double kVelocitySetpointMag, mSetDriveVelocity;
	
	private final double mTimeZero, mTimeOutSec, mThresholdSec, mCourseDistanceThreshold;
	private double mThresholdCountdown;
	private boolean mPhaseDecelCompleteCountdownStarted;
	
	private boolean mIsDriving, mPhaseAccelComplete, mPhaseCruiseComplete, mPhaseDecelComplete;
	
	private double mTimePrev, mTimeCurr, mTimeDelta, mTimeInCurrPhase, mTimePrevPublish;
	private double mHeadingCurrent;
	private double mDistancePrev, mDistanceCurr, mDistanceDelta;
	private double mVelocityPrev, mVelocityCurr, mVelocityDelta, mLeftVelocityPublish, mRightVelocityPublish;
	
	private double mCourseDistanceDelta, mCourseDistanceTotal, mCourseDistanceRemaining;
	private double mCourseErrorDelta, mCourseErrorTotal;
	
	private double mExpectedCourseDistancePhaseAccel, mExpectedCourseDistancePhaseCruise, mExpectedCourseDistancePhaseDecel;
	
	private boolean mIsDrivingForward;
	
	public DriveFollowProfile(double _distanceFt, double _velocityFtPerDs) {
		System.out.println("DriveFP| created: d:" + _distanceFt + ",v:" + _velocityFtPerDs);
		
		this.sDrive = Drive.getInstance();
		this.sGyro = Gyro.getInstance();
		
		this.mTimeZero = System.currentTimeMillis()/1000.;
		this.kUpdateFreq = 0.02;
		this.mTimeOutSec = 10.;
		this.mThresholdSec = 0.25;
		this.mCourseDistanceThreshold = 0.25;
		
		this.kCourseDistanceSetpoint = _distanceFt * 1024.;
		this.kVelocitySetpointMag = Math.abs(_velocityFtPerDs);
		this.kAccelSetpointMag = Math.abs(RobotMap.kDriveFollowAccelSetpoint);
		this.kDecelSetpointMag = Math.abs(RobotMap.kDriveFollowDecelSetpoint);
		
		this.mIsDrivingForward = (this.kCourseDistanceSetpoint >= 0);
		
		this.mIsDriving = true;
		this.mPhaseDecelCompleteCountdownStarted = false;
	}
	
	public void run() {
		System.out.println("DriveFP| run");
		
		this.phaseInit();
		while(this.isDriving()) {
			long lWaitTime = System.currentTimeMillis();
			while(System.currentTimeMillis() - lWaitTime < this.kUpdateFreq * 1000.) {
			}
			this.cycleUpdate();
		};
		System.out.println("DriveFP| run complete");
	}
	
	private void cycleUpdate() {
		this.checkDisabled();
		this.updateValues();
		this.publishValues();
		
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
		
		this.updatePrevValues();
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
		
		if(this.mIsDrivingForward) {
			this.mExpectedCourseDistancePhaseAccel = 1./2. * this.kVelocitySetpointMag * this.kVelocitySetpointMag / this.kAccelSetpointMag;
			this.mExpectedCourseDistancePhaseDecel = 1./2. * this.kVelocitySetpointMag * this.kVelocitySetpointMag / this.kDecelSetpointMag;
			this.mExpectedCourseDistancePhaseCruise = this.kCourseDistanceSetpoint - this.mExpectedCourseDistancePhaseAccel - this.mExpectedCourseDistancePhaseDecel;
		}
		else {
			this.mExpectedCourseDistancePhaseAccel = -1./2. * this.kVelocitySetpointMag * this.kVelocitySetpointMag / this.kAccelSetpointMag;
			this.mExpectedCourseDistancePhaseDecel = -1./2. * this.kVelocitySetpointMag * this.kVelocitySetpointMag / this.kDecelSetpointMag;
			this.mExpectedCourseDistancePhaseCruise = this.kCourseDistanceSetpoint - this.mExpectedCourseDistancePhaseAccel - this.mExpectedCourseDistancePhaseDecel;
		}
		
		this.checkTriangleProfile();
		System.out.println("DriveFP| init: ecdA:" + this.mExpectedCourseDistancePhaseAccel + ",ecdB:" + this.mExpectedCourseDistancePhaseCruise + ",ecdC:" + this.mExpectedCourseDistancePhaseDecel);
	}
	
	private void checkTriangleProfile() {
		if(this.mIsDrivingForward && this.mExpectedCourseDistancePhaseCruise < 0) {
			System.out.println("DriveFP| switching to forward triangle profile");
			this.kVelocitySetpointMag = Math.sqrt(2. * Math.abs(this.kCourseDistanceSetpoint) / (1. / this.kAccelSetpointMag + 1. / this.kDecelSetpointMag));
			this.mExpectedCourseDistancePhaseAccel = 1./2. * this.kVelocitySetpointMag * this.kVelocitySetpointMag / this.kAccelSetpointMag;
			this.mExpectedCourseDistancePhaseDecel = 1./2. * this.kVelocitySetpointMag * this.kVelocitySetpointMag / this.kDecelSetpointMag;
			this.mExpectedCourseDistancePhaseCruise = 0;
		}
		if(!this.mIsDrivingForward && this.mExpectedCourseDistancePhaseCruise > 0) {
			System.out.println("DriveFP| switching to reverse triangle profile");
			this.kVelocitySetpointMag = Math.sqrt(2. * Math.abs(this.kCourseDistanceSetpoint) / (1. / this.kAccelSetpointMag + 1. / this.kDecelSetpointMag));
			this.mExpectedCourseDistancePhaseAccel = -1./2. * this.kVelocitySetpointMag * this.kVelocitySetpointMag / this.kAccelSetpointMag;
			this.mExpectedCourseDistancePhaseDecel = -1./2. * this.kVelocitySetpointMag * this.kVelocitySetpointMag / this.kDecelSetpointMag;
			this.mExpectedCourseDistancePhaseCruise = 0;
		}
	}
	
	private void phaseAccel() {
		if(this.mIsDrivingForward) {
			this.mSetDriveVelocity = this.kAccelSetpointMag * this.mTimeInCurrPhase;
			if(Math.abs(this.mSetDriveVelocity) < Math.abs(RobotMap.kDriveFollowMinVelocity)) {
				this.mSetDriveVelocity = Math.abs(RobotMap.kDriveFollowMinVelocity);
			}
		}
		else {
			this.mSetDriveVelocity = -this.kAccelSetpointMag * this.mTimeInCurrPhase;
			if(Math.abs(this.mSetDriveVelocity) < Math.abs(RobotMap.kDriveFollowMinVelocity)) {
				this.mSetDriveVelocity = -Math.abs(RobotMap.kDriveFollowMinVelocity);
			}
		}
		
		if(Math.abs(this.mSetDriveVelocity) < this.kVelocitySetpointMag) {
			this.setDriveVelocity(this.mSetDriveVelocity);
		}
		else {
			this.setDriveVelocity(this.kVelocitySetpointMag);
		}
		
		if(Math.abs(this.mCourseDistanceTotal) >= Math.abs(this.mExpectedCourseDistancePhaseAccel)) {
			System.out.println("DriveFP| phaseAccel complete");
			this.mTimeInCurrPhase = 0.;
			this.mPhaseAccelComplete = true;
		}
	}
	
	private void phaseCruise() {
		if(this.mIsDrivingForward) {
			this.mSetDriveVelocity = this.kVelocitySetpointMag;
			if(Math.abs(this.mSetDriveVelocity) < Math.abs(RobotMap.kDriveFollowMinVelocity)) {
				this.mSetDriveVelocity = Math.abs(RobotMap.kDriveFollowMinVelocity);
			}
		}
		else {
			this.mSetDriveVelocity = -this.kVelocitySetpointMag;
			if(Math.abs(this.mSetDriveVelocity) < Math.abs(RobotMap.kDriveFollowMinVelocity)) {
				this.mSetDriveVelocity = -Math.abs(RobotMap.kDriveFollowMinVelocity);
			}
		}
		
		this.setDriveVelocity(this.mSetDriveVelocity);
		
		if(Math.abs(this.mCourseDistanceRemaining) <= Math.abs(this.mExpectedCourseDistancePhaseDecel)) {
			System.out.println("DriveFP| phaseCruise complete");
			this.mTimeInCurrPhase = 0.;
			this.mPhaseCruiseComplete = true;
		}
	}
	
	private void phaseDecel() {
		if(this.mIsDrivingForward) {
			this.mSetDriveVelocity = this.kVelocitySetpointMag - this.kDecelSetpointMag * this.mTimeInCurrPhase;
			
			if(Math.abs(this.mCourseDistanceRemaining) < Math.abs(this.mCourseDistanceThreshold)) {
				this.setDriveVelocity(0);
			}
			else if(this.mCourseDistanceRemaining >= 0) {
				if(this.mSetDriveVelocity > 0) {
//					this.setDriveVelocity(this.mSetDriveVelocity);
					this.setDriveVelocity(0);
				}
				else {
//					this.setDriveVelocity(RobotMap.kDriveFollowMinVelocity);
					this.setDriveVelocity(0);
				}
			}
			else {
//				this.setDriveVelocity(-RobotMap.kDriveFollowMinVelocity);
				this.setDriveVelocity(0);
			}
		}
		else {
			this.mSetDriveVelocity = -this.kVelocitySetpointMag + this.kDecelSetpointMag * this.mTimeInCurrPhase;
			
			if(Math.abs(this.mCourseDistanceRemaining) < Math.abs(this.mCourseDistanceThreshold)) {
				this.setDriveVelocity(0);
			}
			else if(this.mCourseDistanceRemaining <= 0) {
				if(this.mSetDriveVelocity < 0) {
//					this.setDriveVelocity(this.mSetDriveVelocity);
					this.setDriveVelocity(0);
				}
				else {
//					this.setDriveVelocity(-RobotMap.kDriveFollowMinVelocity);
					this.setDriveVelocity(0);
				}
			}
			else {
				this.setDriveVelocity(0);
//				this.setDriveVelocity(RobotMap.kDriveFollowMinVelocity);
			}
		}
		
		//Countdown
		if(Math.abs(this.sDrive.getAveragedVelocity()) < 2./3. * Math.abs(RobotMap.kDriveFollowMinVelocity)) {
			if(!this.mPhaseDecelCompleteCountdownStarted) {
				System.out.println("DriveFP| phaseDecel countdown begun");
				this.setDriveVelocity(0);
				this.mThresholdCountdown = System.currentTimeMillis();
				this.mPhaseDecelCompleteCountdownStarted = true;
			}
			if(System.currentTimeMillis() - this.mThresholdCountdown > this.mThresholdSec * 1000.) {
				System.out.println("DriveFP| phaseDecel complete via countdown");
				this.setDriveVelocity(0);
				this.mTimeInCurrPhase = 0.;
				this.mPhaseDecelComplete = true;
			}
		}
		else if(System.currentTimeMillis() - this.mThresholdCountdown > this.mThresholdSec * 1000.) {
			System.out.println("DriveFP| phaseDecel countdown aborted");
			this.mThresholdCountdown = System.currentTimeMillis();
			this.mPhaseDecelCompleteCountdownStarted = false;
		}
	}
	
	private void phaseStop() {
		System.out.println("DriveFP| driving = false");
		this.setDriveVelocity(0);
		this.mIsDriving = false;
	}
	
	private void updateValues() {
		this.mTimeCurr = System.currentTimeMillis()/1000.;
		this.mTimeDelta = this.mTimeCurr - this.mTimePrev;
		this.mTimeInCurrPhase += this.mTimeDelta;
		
		this.mHeadingCurrent = this.sGyro.getAngle();
		
		this.mDistanceCurr = this.sDrive.getAveragedPosition();
		this.mDistanceDelta = this.mDistanceCurr - this.mDistancePrev;
		
		this.mVelocityCurr = this.sDrive.getAveragedVelocity();
		this.mVelocityDelta = this.mVelocityCurr - this.mVelocityPrev;
		
		if(this.mIsDrivingForward) {
			this.mCourseDistanceDelta = this.mDistanceDelta * Math.cos(Math.toRadians(this.mHeadingCurrent));	//course arc-length travelled so far
			this.mCourseErrorDelta = this.mDistanceDelta * Math.sin(Math.toRadians(this.mHeadingCurrent));		//perpendicular error away from course
		}
		else {
			this.mCourseDistanceDelta = -this.mDistanceDelta * Math.cos(Math.toRadians(this.mHeadingCurrent));	//course arc-length travelled so far
			this.mCourseErrorDelta = -this.mDistanceDelta * Math.sin(Math.toRadians(this.mHeadingCurrent));		//perpendicular error away from course
		}
		
		this.mCourseDistanceTotal += this.mCourseDistanceDelta;
		this.mCourseErrorTotal += this.mCourseErrorDelta;
		this.mCourseDistanceRemaining = this.kCourseDistanceSetpoint - this.mCourseDistanceTotal;
	}
	
	private void updatePrevValues() {
		this.mTimePrev = this.mTimeCurr;
		this.mDistancePrev = this.mDistanceCurr;
		this.mVelocityPrev = this.mVelocityCurr;
	}
	
	private void publishValues() {
		if(System.currentTimeMillis() - this.mTimePrevPublish > 250) {
			System.out.println("cTP:" + this.mTimeInCurrPhase + " cH:" + this.mHeadingCurrent + " cD:" + this.mDistanceCurr + " cV:" + this.mVelocityCurr);
			System.out.println("acT:" + this.mCourseDistanceTotal + " ccE:" + this.mCourseErrorTotal + " acR:" + this.mCourseDistanceRemaining);
			System.out.println("lV:" + this.mLeftVelocityPublish + " rV" + this.mRightVelocityPublish);
			
			this.mTimePrevPublish = System.currentTimeMillis();
		}
	}
	
	private void setDriveVelocity(double _setVelocityFtPerDs) {
		double lLeftSet = _setVelocityFtPerDs, lRightSet = _setVelocityFtPerDs;
		
		//TODO: this part may be unnecessary
//		if(this.mCourseErrorTotal > 1.) {
//			this.mCourseErrorTotal = 1.;
//		}
//		if(this.mCourseErrorTotal < -1.) {
//			this.mCourseErrorTotal = -1.;
//		}
		if(_setVelocityFtPerDs >= 0) {
			if(this.mCourseErrorTotal >= 0) {
				lRightSet = _setVelocityFtPerDs * (1+(RobotMap.kDriveFollowProfileErrorScalerMultiplier*Math.abs(this.mCourseErrorTotal/1024.)));
			}
			else if(this.mCourseErrorTotal < 0) {
				lLeftSet = _setVelocityFtPerDs * (1+(RobotMap.kDriveFollowProfileErrorScalerMultiplier*Math.abs(this.mCourseErrorTotal/1024.)));
			}
		}
		else if(_setVelocityFtPerDs < 0) {
			if(this.mCourseErrorTotal >= 0) {
				lRightSet = _setVelocityFtPerDs * (1+(RobotMap.kDriveFollowProfileErrorScalerMultiplier*Math.abs(this.mCourseErrorTotal/1024.)));
			}
			else if(this.mCourseErrorTotal < 0) {
				lLeftSet = _setVelocityFtPerDs * (1+(RobotMap.kDriveFollowProfileErrorScalerMultiplier*Math.abs(this.mCourseErrorTotal/1024.)));
			}
		}
		
		this.mLeftVelocityPublish = lLeftSet;
		this.mRightVelocityPublish = lRightSet;
		this.sDrive.drive(DriveMode.VELOCITY, lLeftSet, lRightSet);
	}
	
	public boolean isDriving() {
		return this.mIsDriving;
	}
	
	private void checkDisabled() {
		if(System.currentTimeMillis()/1000. - this.mTimeZero > this.mTimeOutSec || DriverStation.getInstance().isDisabled() || !DriverStation.getInstance().isAutonomous()) {
			System.out.println("Drive Follow Profile| timeout or disabled");
			this.phaseStop();
		}
	}

}