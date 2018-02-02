package org.usfirst.frc.team346.auto.actions;

import org.usfirst.frc.team346.auto.Curve;
import org.usfirst.frc.team346.robot.Robot;
import org.usfirst.frc.team346.robot.RobotMap;
import org.usfirst.frc.team346.subsystems.Drive.DriveMode;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class MalachisMinivan {

private Robot sRobot;
	
	private PIDSource mCourseSource;
	private PIDOutput mCourseOutput;
	private PIDController mCoursePID;
	
	private final int DELTA = 0, PREV = 1, CURR = 2, TOTAL = 1;
	private long[] mTime = {0,0,0};
	private double[] mHeading = {0};
	private double[] mDistance = {0,0,0};
	private double[] mVelocity = {0,0,0};

	private double[] mDeltaX = {0,0};
	private double[] mDeltaY = {0,0};
	
	private double mVelSetpoint, mDistanceSetpoint;
	private double mLeftVel, mRightVel;
	
	private long mStartTime;
	
	public MalachisMinivan(Robot _robot) {
		sRobot = _robot;
		this.mStartTime = System.currentTimeMillis();
	}
	
	private double sigmoid(double _distance) {
		return 1./(1.+Math.pow(Math.E, -(_distance)));
	}
	
	private double sigmoidMod(double _distance) {
		return 2./(1.+Math.pow(Math.E, -Math.abs(_distance))) - 1.;
	}
	
	public void followCurve(Curve _curve) {
		mDistanceSetpoint = _curve.getArcLength();
		mVelSetpoint = RobotMap.kDriveFollowVelSetpoint;
		
		//Zeros the gyro and drive at the beginning of a course and sets "previous" values to startup values.
		sRobot.zeroDevices();
		updateFinal();
		
		createCoursePID();
		mCoursePID.setSetpoint(mDistanceSetpoint);
		enablePID();
	}
	
	public void updateCycle(double _courseOutput) {
		updateStart();
		setDriveToCurve(_courseOutput);
		updateFinal();
		checkCompletion();
	}
	
	private void updateStart() {
		mTime[CURR] = System.currentTimeMillis();
		mTime[DELTA] = mTime[CURR] - mTime[PREV];
		
		mHeading[CURR] = sRobot.sGyro.getAngle();
		mHeading[DELTA] = mHeading[CURR] - mHeading[PREV];
		
		mDistance[CURR] = 1./2. * (sRobot.sDrive.getLeftPosition() + sRobot.sDrive.getRightPosition());
		mDistance[DELTA] = mDistance[CURR] - mDistance[PREV];
		
		mDeltaX[DELTA] = mDistance[DELTA] * Math.sin(Math.toRadians(mHeading[CURR]));		//perpendicular distance from y-axis
		mDeltaX[TOTAL] += mDeltaX[DELTA];
		
		mDeltaY[DELTA] = mDistance[DELTA] * Math.cos(Math.toRadians(mHeading[CURR]));		//perpendicular distance from x-axis
		mDeltaY[TOTAL] += mDeltaY[DELTA];
		
		System.out.println("lD:" + sRobot.sDrive.getLeftPosition() + " rD:" + sRobot.sDrive.getRightPosition() + " cD:" + mDistance[CURR]);
		System.out.println("dT:" + mTime[DELTA] + " dH:" + mHeading[CURR] + " dD:" + mDistance[CURR]);
		System.out.println("dX:" + mDeltaX[TOTAL] + " dY:" + mDeltaY[TOTAL]);
	}
	
	private void setDriveToCurve(double _courseOutput) {
		//assuming driving forward
		mLeftVel = _courseOutput * mVelSetpoint;
		mRightVel = _courseOutput * mVelSetpoint;
		
		System.out.println("lV:" + mLeftVel + " rV:" + mRightVel);
		
		mLeftVel = mVelSetpoint * ( (mVelocity[CURR] - mVelocity[PREV])/mVelocity[PREV] + (mHeading[CURR] - mHeading[PREV])/360 );
		mRightVel = mVelSetpoint * ( (mVelocity[CURR] - mVelocity[PREV])/mVelocity[PREV] + (mHeading[PREV] - mHeading[CURR])/360 );
		
		System.out.println("lV:" + mLeftVel + " rV:" + mRightVel);
		sRobot.sDrive.drive(DriveMode.VELOCITY, mLeftVel, mRightVel);
	}
	
	private void updateFinal() {
		mTime[PREV] = mTime[CURR];
		mHeading[PREV] = mHeading[CURR];
		mDistance[PREV] = mDistance[CURR];
	}
	
	private void checkCompletion() {
		//TODO
	}
	
	private void createCoursePID() {
		System.out.println("Malachis Minivan| creating course PIDC");
		
		mCourseSource = new PIDSource() {
			@Override
			public void setPIDSourceType(PIDSourceType pidSource) { }
			@Override
			public PIDSourceType getPIDSourceType() {
				return PIDSourceType.kDisplacement;
			}
			@Override
			public double pidGet() {
				return 0;//TODO
			}
		};
		
		/**Sets the drive velocity outputs to a fraction of their velocity setpoint based on remaining distance.**/
		mCourseOutput = new PIDOutput() {
			@Override
			public void pidWrite(double _output) {
				System.out.println("pidWrite:" + _output);
				updateCycle(_output);
			}
		};
		
		mCoursePID = new PIDController(RobotMap.kDriveFollowP, RobotMap.kDriveFollowI, RobotMap.kDriveFollowD, RobotMap.kDriveFollowF,
									   mCourseSource, mCourseOutput, RobotMap.kDriveFollowUpdateRate);
	}
	
	private void enablePID() {
		System.out.println("Auto Runner| enabling course PIDC");
		mCoursePID.enable();
	}
	
	private void disablePID() {
		System.out.println("Auto Runner| disabling course PIDC");
		mCoursePID.free();
	}

}