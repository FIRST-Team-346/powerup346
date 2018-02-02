package org.usfirst.frc.team346.auto.actions;

import org.usfirst.frc.team346.robot.Robot;
import org.usfirst.frc.team346.robot.RobotMap;
import org.usfirst.frc.team346.subsystems.Drive.DriveMode;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class DriveFollow {

	private Robot sRobot;
	
	private PIDSource mCourseSource;
	private PIDOutput mCourseOutput;
	private PIDController mCoursePID;
	
	private final int DELTA = 0, PREV = 1, CURR = 2;
	private double[] mTime = {0,0,0};
	private double[] mHeading = {0};
	private double[] mDistance = {0,0,0};
	
	private final int TOTAL = 1;
	private double[] mCourseTraveled = {0,0};
	private double[] mCourseError = {0,0};
	private double[] mCourseRemaining = {0,0};
	private double[] mCourseArea = {0,0};
	
	private double mVelSetpoint, mDistanceSetpoint;
	private double mLeftVel, mRightVel;
	
	private double mStartTime;
	private double mCountdown;
	private boolean mCountdownStarted;
	private int mPrintCount = 0;
	
	/**The DriveFollow object aims to drive along a course, correcting for any accumulated area off-course
	 * in order to return to the original course. This maintains the correct distance driven along the course
	 * as well as the correct heading along the course.**/
	public DriveFollow(Robot _robot) {
		sRobot = _robot;
		mStartTime = System.currentTimeMillis()/1000.;
		mCountdownStarted = false;
	}
	
	/**This method to be used only for following straight lines, thus only a distance is required.**/
	public void followLine(double _distance) {
		mDistanceSetpoint = _distance;
		mVelSetpoint = RobotMap.kDriveFollowVelSetpoint;
		
		//Zeros the gyro and drive at the beginning of a course and sets "previous" values to startup values.
		sRobot.zeroDevices();
		updateFinal();
		
		createCoursePID();
		mCoursePID.setSetpoint(mDistanceSetpoint);
		enablePID();
	}
	
	/**This method is run every cycle, calculating the error and adjusting the drive accordingly.**/
	public void updateCycle(double _courseOutput) {
		updateStart();
		setDriveToFollow(_courseOutput);
		updateFinal();
		checkCompletion();
	}
	
	/**To be run at the beginning of each cycle for use in determining deltas since the previous cycle.**/
	private void updateStart() {
		mTime[CURR] = System.currentTimeMillis()/1000.;
		mTime[DELTA] = mTime[CURR] - mTime[PREV];
		
		mHeading[DELTA] = sRobot.sGyro.getAngle();
		
		mDistance[CURR] = 1./2. * (sRobot.sDrive.getLeftPosition() + sRobot.sDrive.getRightPosition());
		mDistance[DELTA] = mDistance[CURR] - mDistance[PREV];
		
		mCourseTraveled[DELTA] = mDistance[DELTA] * Math.cos(Math.toRadians(mHeading[DELTA]));	//course arc-length travelled so far
		mCourseTraveled[TOTAL] += mCourseTraveled[DELTA];
		
		mCourseError[DELTA] = mDistance[DELTA] * Math.sin(Math.toRadians(mHeading[DELTA]));		//perpendicular error away from course
		mCourseError[TOTAL] += mCourseError[DELTA];
		
		mCourseArea[DELTA] = 1./2. * mDistance[DELTA]*mDistance[DELTA] * Math.sin(Math.toRadians(mHeading[DELTA])) * Math.cos(Math.toRadians(mHeading[DELTA]));
		mCourseArea[TOTAL] += mCourseArea[DELTA];		//this only reflects the triangular error total
		
		mCourseRemaining[TOTAL] = mDistanceSetpoint - mCourseTraveled[TOTAL];
		
		mPrintCount++;
		if(mPrintCount == 0.5/RobotMap.kDriveFollowUpdateRate) {
			System.out.println("lD:" + sRobot.sDrive.getLeftPosition() + " rD:" + sRobot.sDrive.getRightPosition() + " cD:" + mDistance[CURR]);
			System.out.println("dT:" + mTime[DELTA] + " dH:" + mHeading[DELTA] + " dD:" + mDistance[DELTA]);
			System.out.println("cT:" + mCourseTraveled[TOTAL] + " cE:" + mCourseError[TOTAL] + " cR:" + mCourseRemaining[TOTAL]);
		}
		else if(mPrintCount > 0.5/RobotMap.kDriveFollowUpdateRate) {
			mPrintCount = 0;
		}
	}
	
	private void setDriveToFollow(double _courseOutput) {
		if(mCourseRemaining[TOTAL] < 2. && mCourseRemaining[TOTAL] >= 0) {
			mVelSetpoint = 0.2*1200.;
		}
		else if(mCourseRemaining[TOTAL] > -2. && mCourseRemaining[TOTAL] <= 0) {
			mVelSetpoint = 0.3*1200.;
		}
		mLeftVel = _courseOutput * mVelSetpoint;
		mRightVel = _courseOutput * mVelSetpoint;
		
		if(mCourseError[TOTAL] > 1.) {
			mCourseError[TOTAL] = 1.;
		}
		if(mCourseError[TOTAL] < -1.) {
			mCourseError[TOTAL] = -1.;
		}
		if(_courseOutput >= 0) {
			if(mCourseError[TOTAL] >= 0) {
//				mRightVel = _courseOutput * mVelSetpoint + mCourseError[TOTAL]/mTime[DELTA] * RobotMap.kDriveFollowErrorScalerMultiplier;
				mRightVel = _courseOutput * mVelSetpoint * (1+(RobotMap.kDriveFollowErrorScalerDivider*Math.abs(mCourseError[TOTAL])));
			}
			else if(mCourseError[TOTAL] < 0) {
//				mLeftVel = _courseOutput * mVelSetpoint - mCourseError[TOTAL]/mTime[DELTA] * RobotMap.kDriveFollowErrorScalerMultiplier;
				mLeftVel = _courseOutput * mVelSetpoint * (1+(RobotMap.kDriveFollowErrorScalerDivider*Math.abs(mCourseError[TOTAL])));
			}
		}
		else if(_courseOutput < 0) {
			if(mCourseError[TOTAL] >= 0) {
//				mRightVel = _courseOutput * mVelSetpoint - mCourseError[TOTAL]/mTime[DELTA] * RobotMap.kDriveFollowErrorScalerMultiplier;
				mRightVel = _courseOutput * mVelSetpoint * (1+(RobotMap.kDriveFollowErrorScalerDivider*Math.abs(mCourseError[TOTAL])));
			}
			else if(mCourseError[TOTAL] < 0) {
//				mLeftVel = _courseOutput * mVelSetpoint + mCourseError[TOTAL]/mTime[DELTA] * RobotMap.kDriveFollowErrorScalerMultiplier;
				mLeftVel = _courseOutput * mVelSetpoint * (1+(RobotMap.kDriveFollowErrorScalerDivider*Math.abs(mCourseError[TOTAL])));
			}
		}
		
		if(mPrintCount == 0.5/RobotMap.kDriveFollowUpdateRate) {
			System.out.println("lV:" + mLeftVel + " rV:" + mRightVel + "\n");
		}
		sRobot.sDrive.drive(DriveMode.VELOCITY, mLeftVel, mRightVel);
	}
	
	/**To be run at the end of each cycle to inform the next cycle of the previous values.**/
	private void updateFinal() {
//		TODO: test mTimePrev = (long)DriverStation.getInstance().getMatchTime();
		mTime[PREV] = mTime[CURR];
		mDistance[PREV] = mDistance[CURR];
	}
	
	private void checkCompletion() {
		if(System.currentTimeMillis()/1000. - mStartTime > 10. || DriverStation.getInstance().isDisabled()) {
			System.out.println("Drive Follow| timeout");
			disablePID();
			setDriveToFollow(0);
		}
		else if(Math.abs(mDistanceSetpoint - mCourseTraveled[TOTAL]) < 0.5) {
			if(!mCountdownStarted) {
				System.out.println("Countdown started");
				mCountdown = System.currentTimeMillis()/1000.;
				mCountdownStarted = true;
			}
			if(System.currentTimeMillis()/1000. - mCountdown > 0.25) {
				System.out.println("Drive Follow| complete");
				disablePID();
				setDriveToFollow(0);
			}
		}
		else if(System.currentTimeMillis()/1000. - mCountdown > 0.25) {
			mCountdownStarted = false;
		}
	}
	
	private void createCoursePID() {
		System.out.println("Drive Follow| creating course PIDC");
		
		mCourseSource = new PIDSource() {
			@Override
			public void setPIDSourceType(PIDSourceType pidSource) { }
			@Override
			public PIDSourceType getPIDSourceType() {
				return PIDSourceType.kDisplacement;
			}
			@Override
			public double pidGet() {
				return mCourseTraveled[TOTAL];
			}
		};
		
		/**Sets the drive velocity outputs to a fraction of their velocity setpoint based on remaining distance.**/
		mCourseOutput = new PIDOutput() {
			@Override
			public void pidWrite(double _output) {
				updateCycle(_output);
			}
		};
		
		mCoursePID = new PIDController(RobotMap.kDriveFollowP, RobotMap.kDriveFollowI, RobotMap.kDriveFollowD, RobotMap.kDriveFollowF,
									   mCourseSource, mCourseOutput, RobotMap.kDriveFollowUpdateRate);
	}
	
	private void enablePID() {
		System.out.println("Drive Follow| enabling course PIDC");
		mCoursePID.enable();
	}
	
	private void disablePID() {
		System.out.println("Drive Follow| disabling course PIDC");
		mCoursePID.free();
	}

}