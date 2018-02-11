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

public class DriveFollow {

	private Drive sDrive;
	private Gyro sGyro;
	
	private PIDSource mCourseSource;
	private PIDOutput mCourseOutput;
	private PIDController mCoursePID;
	
	private final int DELTA = 0, PREV = 1, CURR = 2;
	private double[] mTime = {0,0,0};
	private double[] mHeading = {0};
	private double[] mDistance = {0,0,0};
	private double[] mVelocity = {0,0,0};
	
	private final int TOTAL = 1;
	private double[] mCourseTraveled = {0,0};
	private double[] mCourseError = {0,0};
	private double[] mCourseRemaining = {0,0};
	private double mCourseOutputPublish = 0;
	
	private double mVelSetpoint, mDistanceSetpoint;
	private double mLeftVel, mRightVel;
	private final boolean mIsDrivingForward;
	private boolean mIsDriving;
	
	private final double kTimeOutSeconds = 10.;
	private final double kThresholdSeconds = 0.25;
	private final double kThresholdFeet = 0.5;
	private final double kThresholdVelocity = 0.1 * RobotMap.kDriveVelAverage;
	private double mStartTime, mCountdown, mTimePrevPublish;
	private boolean mCountdownStarted;
	
	
	/**The DriveFollow object aims to drive along a course, correcting for any accumulated area off-course
	 * in order to return to the original course. This maintains the correct distance driven along the course
	 * as well as the correct heading along the course.**/
	public DriveFollow(double _distance, double _velocity) {
		this.sDrive = Drive.getInstance();
		this.sGyro = Gyro.getInstance();
		
		this.mCountdownStarted = false;
		this.mDistanceSetpoint = _distance * 1024.;
		this.mVelSetpoint = Math.abs(_velocity);
		
		this.mIsDrivingForward = (this.mDistanceSetpoint >= 0);
		this.mIsDriving = true;
	}
	
	/**This method to be used only for following straight lines, thus only a distance is required.**/
	public void followLine() {
		//Zeros the gyro and drive at the beginning of a course and sets "previous" values to startup values.
		this.sDrive.zeroEncoders();
		this.sGyro.zeroGyro();
		
		this.mIsDriving = true;
		
		this.updateFinal();
		this.createCoursePID();
		
		this.mStartTime = System.currentTimeMillis()/1000.;
		this.sDrive.enable();
		
		this.mCoursePID.setSetpoint(this.mDistanceSetpoint);
		this.enablePID();
	}
	
	/**This method is run every cycle, calculating the error and adjusting the drive accordingly.**/
	public void updateCycle(double _courseOutput) {
		this.updateStart();
		
		double lSigmoidOutput;
		if(this.mIsDrivingForward) {
			lSigmoidOutput = reverseModSigmoid(Math.abs(this.mDistanceSetpoint/1024.), this.mCourseTraveled[TOTAL]/1024.);
		}
		else {
			lSigmoidOutput = -reverseModSigmoid(Math.abs(this.mDistanceSetpoint/1024.), this.mCourseTraveled[TOTAL]/1024.);
		}
		
//		this.setDriveToFollow(_courseOutput);
		this.setDriveToFollow(lSigmoidOutput);
		
		this.publishValues();
		
		this.updateFinal();
		this.checkDisabled();
		
		if(Math.abs(this.mCourseRemaining[TOTAL]) < Math.abs(this.mDistanceSetpoint/2.)) {
//			this.checkCompletion();
			this.checkCompletionVelocity();	
		}
	}
	
	/**To be run at the beginning of each cycle for use in determining deltas since the previous cycle.**/
	private void updateStart() {
		this.mTime[CURR] = System.currentTimeMillis()/1000.;
		this.mTime[DELTA] = this.mTime[CURR] - this.mTime[PREV];
		
		this.mHeading[DELTA] = this.sGyro.getAngle();
		
		this.mDistance[CURR] = 1./2. * (this.sDrive.getLeftPosition() + this.sDrive.getRightPosition());
		this.mDistance[DELTA] = this.mDistance[CURR] - this.mDistance[PREV];
		
		this.mVelocity[CURR] = 1./2. * (this.sDrive.getLeftVelocity() + this.sDrive.getRightVelocity());
		this.mVelocity[DELTA] = this.mVelocity[CURR] - this.mVelocity[PREV];
		
		if(this.mIsDrivingForward) {
			this.mCourseTraveled[DELTA] = this.mDistance[DELTA] * Math.cos(Math.toRadians(this.mHeading[DELTA]));	//course arc-length travelled so far
			this.mCourseError[DELTA] = this.mDistance[DELTA] * Math.sin(Math.toRadians(this.mHeading[DELTA]));		//perpendicular error away from course
		}
		else {
			this.mCourseTraveled[DELTA] = -this.mDistance[DELTA] * Math.cos(Math.toRadians(this.mHeading[DELTA]));	//course arc-length travelled so far
			this.mCourseError[DELTA] = -this.mDistance[DELTA] * Math.sin(Math.toRadians(this.mHeading[DELTA]));		//perpendicular error away from course
		}
		
		this.mCourseTraveled[TOTAL] += this.mCourseTraveled[DELTA];
		this.mCourseError[TOTAL] += this.mCourseError[DELTA];
		if(this.mIsDrivingForward) {
			this.mCourseRemaining[TOTAL] = this.mDistanceSetpoint - this.mCourseTraveled[TOTAL];
		}
		else {
			this.mCourseRemaining[TOTAL] = this.mDistanceSetpoint + this.mCourseTraveled[TOTAL];
		}
	}
	
	private void publishValues() {
		if(System.currentTimeMillis() - this.mTimePrevPublish > 250) {
			System.out.println("dT:" + this.mTime[DELTA] + " dH:" + this.mHeading[DELTA] + " cD:" + this.mDistance[CURR] + " cV:" + this.mVelocity[CURR]);
			System.out.println("acT:" + this.mCourseTraveled[TOTAL] + " ccE:" + this.mCourseError[TOTAL] + " acR:" + this.mCourseRemaining[TOTAL]);
			System.out.println("lV:" + this.mLeftVel + " rV:" + this.mRightVel);
			System.out.println("cO:" + this.mCourseOutputPublish);
			
			this.mTimePrevPublish = System.currentTimeMillis();
		}
	}
	
	private void setDriveToFollow(double _courseOutput) {
		this.mCourseOutputPublish = _courseOutput;
		
		mLeftVel = _courseOutput * mVelSetpoint;
		mRightVel = _courseOutput * mVelSetpoint;
		if(this.mIsDrivingForward) {
			if(_courseOutput >= 0) {
				if(this.mCourseError[TOTAL] >= 0) {
//					this.mRightVel = _courseOutput * this.mVelSetpoint + this.mCourseError[TOTAL]/this.mTime[DELTA] * RobotMap.kDriveFollowErrorScalerMultiplier;
					this.mRightVel = _courseOutput * this.mVelSetpoint * (1+(RobotMap.kDriveFollowErrorScalerMultiplier*Math.abs(this.mCourseError[TOTAL]/1024.)));
//					this.mLeftVel = _courseOutput * mVelSetpoint * this.reverseSigmoid(Math.abs(this.mCourseError[TOTAL]));
				}
				else if(mCourseError[TOTAL] < 0) {
//					this.mLeftVel = _courseOutput * this.mVelSetpoint - this.mCourseError[TOTAL]/this.mTime[DELTA] * RobotMap.kDriveFollowErrorScalerMultiplier;
					this.mLeftVel = _courseOutput * this.mVelSetpoint * (1+(RobotMap.kDriveFollowErrorScalerMultiplier*Math.abs(this.mCourseError[TOTAL]/1024.)));
//					this.mRightVel = _courseOutput * this.mVelSetpoint * this.reverseSigmoid(Math.abs(this.mCourseError[TOTAL]));
				}
			}
			else if(_courseOutput < 0) {
				if(mCourseError[TOTAL] >= 0) {
//					this.mRightVel = _courseOutput * this.mVelSetpoint - this.mCourseError[TOTAL]/this.mTime[DELTA] * RobotMap.kDriveFollowErrorScalerMultiplier;
					this.mRightVel = _courseOutput * this.mVelSetpoint * (1+(RobotMap.kDriveFollowErrorScalerMultiplier*Math.abs(this.mCourseError[TOTAL]/1024.)));
//					this.mLeftVel = _courseOutput * this.mVelSetpoint * this.reverseSigmoid(Math.abs(this.mCourseError[TOTAL]));
				}
				else if(mCourseError[TOTAL] < 0) {
//					this.mLeftVel = _courseOutput * this.mVelSetpoint + this.mCourseError[TOTAL]/this.mTime[DELTA] * RobotMap.kDriveFollowErrorScalerMultiplier;
					this.mLeftVel = _courseOutput * this.mVelSetpoint * (1+(RobotMap.kDriveFollowErrorScalerMultiplier*Math.abs(this.mCourseError[TOTAL]/1024.)));
//					this.mRightVel = _courseOutput * this.mVelSetpoint * this.reverseSigmoid(Math.abs(this.mCourseError[TOTAL]));
				}
			}
		}
		else {
			if(_courseOutput >= 0) {
				if(this.mCourseError[TOTAL] >= 0) {
//					this.mRightVel = _courseOutput * this.mVelSetpoint + this.mCourseError[TOTAL]/this.mTime[DELTA] * RobotMap.kDriveFollowErrorScalerMultiplier;
					this.mRightVel = _courseOutput * this.mVelSetpoint * (1+(RobotMap.kDriveFollowErrorScalerMultiplier*-Math.abs(this.mCourseError[TOTAL]/1024.)));
//					this.mLeftVel = _courseOutput * mVelSetpoint * this.reverseSigmoid(Math.abs(this.mCourseError[TOTAL]));
				}
				else if(mCourseError[TOTAL] < 0) {
//					this.mLeftVel = _courseOutput * this.mVelSetpoint - this.mCourseError[TOTAL]/this.mTime[DELTA] * RobotMap.kDriveFollowErrorScalerMultiplier;
					this.mLeftVel = _courseOutput * this.mVelSetpoint * (1+(RobotMap.kDriveFollowErrorScalerMultiplier*-Math.abs(this.mCourseError[TOTAL]/1024.)));
//					this.mRightVel = _courseOutput * this.mVelSetpoint * this.reverseSigmoid(Math.abs(this.mCourseError[TOTAL]));
				}
			}
			else if(_courseOutput < 0) {
				if(mCourseError[TOTAL] >= 0) {
//					this.mRightVel = _courseOutput * this.mVelSetpoint - this.mCourseError[TOTAL]/this.mTime[DELTA] * RobotMap.kDriveFollowErrorScalerMultiplier;
					this.mRightVel = _courseOutput * this.mVelSetpoint * (1+(RobotMap.kDriveFollowErrorScalerMultiplier*-Math.abs(this.mCourseError[TOTAL]/1024.)));
//					this.mLeftVel = _courseOutput * this.mVelSetpoint * this.reverseSigmoid(Math.abs(this.mCourseError[TOTAL]));
				}
				else if(mCourseError[TOTAL] < 0) {
//					this.mLeftVel = _courseOutput * this.mVelSetpoint + this.mCourseError[TOTAL]/this.mTime[DELTA] * RobotMap.kDriveFollowErrorScalerMultiplier;
					this.mLeftVel = _courseOutput * this.mVelSetpoint * (1+(RobotMap.kDriveFollowErrorScalerMultiplier*-Math.abs(this.mCourseError[TOTAL]/1024.)));
//					this.mRightVel = _courseOutput * this.mVelSetpoint * this.reverseSigmoid(Math.abs(this.mCourseError[TOTAL]));
				}
			}
		}
		
		if(this.mCountdownStarted) {
			this.mLeftVel = 0.;
			this.mRightVel = 0.;
		}
		
		this.sDrive.drive(DriveMode.VELOCITY, this.mLeftVel, this.mRightVel);
	}
	
	/**To be run at the end of each cycle to inform the next cycle of the previous values.**/
	private void updateFinal() {
		this.mTime[PREV] = this.mTime[CURR];
		this.mDistance[PREV] = this.mDistance[CURR];
	}
	
	private void checkCompletion() {
		this.checkDisabled();
		if(Math.abs(this.mDistanceSetpoint - this.mCourseTraveled[TOTAL]) < this.kThresholdFeet) {
			if(!this.mCountdownStarted) {
				System.out.println("Countdown started");
				this.mCountdown = System.currentTimeMillis()/1000.;
				this.mCountdownStarted = true;
			}
			if(System.currentTimeMillis()/1000. - this.mCountdown > this.kThresholdSeconds) {
				System.out.println("Drive Follow| complete");
				this.mIsDriving = false;
				this.disablePID();
				this.setDriveToFollow(0);
			}
		}
		else if(System.currentTimeMillis()/1000. - this.mCountdown > this.kThresholdSeconds) {
			this.mCountdownStarted = false;
		}
	}
	
	private void checkCompletionVelocity() {
		this.checkDisabled();
		if(Math.abs(this.mVelocity[CURR]) < Math.abs(this.kThresholdVelocity)) {
			if(!this.mCountdownStarted) {
				System.out.println("Velocity countdown started");
				this.mCountdown = System.currentTimeMillis()/1000.;
				this.mCountdownStarted = true;
			}
			if(System.currentTimeMillis()/1000. - this.mCountdown > this.kThresholdSeconds) {
				System.out.println("Drive Follow| complete");
				this.mIsDriving = false;
				this.disablePID();
				this.setDriveToFollow(0);
			}
		}
		else if(System.currentTimeMillis()/1000. - this.mCountdown > this.kThresholdSeconds) {
			System.out.println("Velocity countdown aborted");
			this.mCountdown = System.currentTimeMillis()/1000.;
			this.mCountdownStarted = false;
		}
	}
	
	private void checkDisabled() {
		if(System.currentTimeMillis()/1000. - this.mStartTime > this.kTimeOutSeconds || DriverStation.getInstance().isDisabled()) {
			System.out.println("Drive Follow| timeout");
			this.disablePID();
			this.setDriveToFollow(0);
			this.mIsDriving = false;
		}
	}
	
	private void createCoursePID() {
		System.out.println("Drive Follow| creating course PIDC");
		
		this.mCourseSource = new PIDSource() {
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
		this.mCourseOutput = new PIDOutput() {
			@Override
			public void pidWrite(double _output) {
				updateCycle(_output);
			}
		};
		
		this.mCoursePID = new PIDController(RobotMap.kDriveFollowP, RobotMap.kDriveFollowI, RobotMap.kDriveFollowD, RobotMap.kDriveFollowF,
									   this.mCourseSource, this.mCourseOutput, RobotMap.kDriveFollowUpdateRate);
	}
	
	private void enablePID() {
		System.out.println("Drive Follow| enabling course PIDC");
		this.mCoursePID.enable();
	}
	
	private void disablePID() {
		System.out.println("Drive Follow| disabling course PIDC");
		this.mCoursePID.free();
		this.mIsDriving = false;
	}
	
	public boolean isDriving() {
		return this.mIsDriving;
	}
	
	private double reverseSigmoid(double _x) {
		return 1. /(1. + Math.pow(Math.E, _x ));
	}
	
	private double reverseModSigmoid(double _width, double _x) {
		double lWidthScaler = 12. /_width;
		double lTranslator = _width /2.;
		return 1. /(1. + Math.pow(Math.E, lWidthScaler * (_x - lTranslator) ));
	}

}