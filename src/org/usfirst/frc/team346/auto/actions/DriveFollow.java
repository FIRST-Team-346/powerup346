package org.usfirst.frc.team346.auto.actions;

import org.usfirst.frc.team346.robot.Robot;
import org.usfirst.frc.team346.robot.RobotMap;
import org.usfirst.frc.team346.subsystems.Drive.DriveMode;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class DriveFollow {

	private Robot sRobot;
	
	private PIDSource mLeftSource, mRightSource;
	private PIDOutput mLeftOutput, mRightOutput;
	private PIDController mLeftPID, mRightPID;
	
	private long mTimePrev, mTime;
	private double mHeadingPrev, mHeading;
	private double mPositionPrev, mPosition;
	private double mDeltaT, mDeltaD, mDeltaH;
	private double mDeltaCourseTravelled, mCourseTravelled, mDeltaCourseError, mCourseError;
	private double mErrorArea, mErrorAreaTotal;
	
	private double mVelSetpoint, mDistanceSetpoint;
	private double mLeftSetVel, mRightSetVel;
	
	/**The DriveFollow object aims to drive along a course, correcting for any accumulated area off-course
	 * in order to return to the original course. This maintains the correct distance driven along the course
	 * as well as the correct heading along the course.**/
	public DriveFollow() {
		sRobot = Robot.getInstance();
	}
	
	/**This method to be used only for following straight lines, thus only a distance is required.**/
	public void followLine(double _distance) {
		mDistanceSetpoint = _distance;
		mVelSetpoint = RobotMap.kDriveFollowCruiseVelocityRPM;
		
		//Zeros the gyro and drive at the beginning of a course and sets "previous" values to startup values.
		sRobot.zeroDevices();
		updateFinal();
		
		createPID();
		enablePID();
	}
	
	/**This method is run every cycle, calculating the error and adjusting the drive accordingly.**/
	public void updateCycle() {
		updateStart();
		setDriveToFollow();
		updateFinal();
	}
	
	/**To be run at the beginning of each cycle for use in determining deltas since the previous cycle.**/
	private void updateStart() {
		mTime = System.currentTimeMillis();
		mHeading = sRobot.sGyro.getAngle();
		mPosition = 1/2*(sRobot.sDrive.getPosition(Hand.kLeft) + sRobot.sDrive.getPosition(Hand.kRight));
		
		mDeltaT = mTime - mTimePrev;
		mDeltaD = mPosition - mPositionPrev;
		mDeltaH = mHeading;		//TODO: figure out how to find the actual heading off course, not the deltaH since last cycle
		
		mDeltaCourseTravelled = mDeltaD * Math.cos(Math.toRadians(mDeltaH));	//course arc-length travelled so far
		mCourseTravelled += mDeltaCourseTravelled;
		mDeltaCourseError = mDeltaD * Math.sin(Math.toRadians(mDeltaH));		//perpendicular error away from course
		mCourseError += mDeltaCourseError;
		
		mErrorArea = 1/2*mDeltaD*mDeltaD * Math.sin(Math.toRadians(mDeltaH)) * Math.cos(Math.toRadians(mDeltaH));
		mErrorAreaTotal += mErrorArea;		//TODO:  this only reflects the triangular error total, NOT including the rectangular error total
	}
	
	/**To be run at the end of each cycle to inform the next cycle of the previous values.**/
	private void updateFinal() {
//		TODO: test mTimePrev = (long)DriverStation.getInstance().getMatchTime();
		mTimePrev = System.currentTimeMillis();
		mHeadingPrev = sRobot.sGyro.getAngle();
		mPositionPrev = 1/2*(sRobot.sDrive.getPosition(Hand.kLeft) + sRobot.sDrive.getPosition(Hand.kRight));
	}
	
	private void setDriveToFollow() {
		//assuming driving forward
		if(mCourseError > 0) {
			mLeftSetVel = mLeftSetVel - mCourseError/mDeltaT * RobotMap.kDriveFollowCourseErrorScaler;
		}
		else if(mCourseError < 0) {
			mRightSetVel = mRightSetVel + mCourseError/mDeltaT * RobotMap.kDriveFollowCourseErrorScaler;
		}
		sRobot.sDrive.drive(DriveMode.VELOCITY, mLeftSetVel, mRightSetVel);
	}
	
	private void checkCompletion() {
		//TODO: check if the course has been completed
	}
	
	private void createPID() {
		mLeftSource = new PIDSource() {
			public void setPIDSourceType(PIDSourceType pidSource) { }
			public PIDSourceType getPIDSourceType() {
				return PIDSourceType.kDisplacement;
			}
			public double pidGet() {
				//TODO: replace this with deltaX
				return sRobot.sDrive.getPosition(Hand.kLeft);
			}
		};
		
		mRightSource = new PIDSource() {
			public void setPIDSourceType(PIDSourceType pidSource) { }
			public PIDSourceType getPIDSourceType() {
				return PIDSourceType.kDisplacement;
			}
			public double pidGet() {
				//TODO: replace this with deltaX
				return sRobot.sDrive.getPosition(Hand.kRight);
			}
		};
		
		/**Sets the left velocity output to a fraction of its velocity setpoint based on remaining distance.**/
		mLeftOutput = new PIDOutput() {
			public void pidWrite(double _output) {
				mLeftSetVel = mVelSetpoint * _output;
			}
		};
		
		/**Sets the right velocity output to a fraction of its velocity setpoint based on remaining distance.**/
		mRightOutput = new PIDOutput() {
			public void pidWrite(double _output) {
				mRightSetVel = mVelSetpoint * _output;
			}
		};
		
		mLeftPID = new PIDController(RobotMap.kDriveFollowLeftKP, RobotMap.kDriveFollowLeftKI, RobotMap.kDriveFollowLeftKD, RobotMap.kDriveFollowLeftKF,
									 mLeftSource, mLeftOutput, 0.02);
		mRightPID = new PIDController(RobotMap.kDriveFollowRightKP, RobotMap.kDriveFollowRightKI, RobotMap.kDriveFollowRightKD, RobotMap.kDriveFollowRightKF,
									  mRightSource, mRightOutput, 0.02);
	}
	
	private void enablePID() {
		mLeftPID.enable();
		mRightPID.enable();
	}
	
	private void disablePID() {
		mLeftPID.disable();
		mRightPID.disable();
	}

}