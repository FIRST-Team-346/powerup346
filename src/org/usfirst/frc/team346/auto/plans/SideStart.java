package org.usfirst.frc.team346.auto.plans;

import org.usfirst.frc.team346.auto.actions.DriveStraight;
import org.usfirst.frc.team346.auto.actions.Rotate;
import org.usfirst.frc.team346.auto.actions.SubsystemActions;
import org.usfirst.frc.team346.robot.Robot;
import org.usfirst.frc.team346.robot.RobotMap;
import org.usfirst.frc.team346.subsystems.Gyro;

import edu.wpi.first.wpilibj.Preferences;

public class SideStart extends AutoPlan {
	
	Rotate mRotate;
	DriveStraight mDriveStraight;
	SubsystemActions mAction;
	Preferences pref = Preferences.getInstance();
	
	double sideScaler;
	
	Gyro mGyro = Gyro.getInstance();
	
	public String getGoal() {
		return "cross baseline, place 1 cube in switch";
	}
	
	public void run(Robot _robot, String _layout) {
		this.mAction = new SubsystemActions();
		
		sideScaler = (_layout.charAt(0)=='L')? 1 : -1;
//		this.mAction.setTilterPosNu(RobotMap.kTiltPosScaleHigh);
//		this.mAction.openIntake();
		
		this.mGyro.calibrate();
		this.mDriveStraight = new DriveStraight(-25, 0.6, 1.8, 0.5);
		this.mDriveStraight.setLeftPID(pref.getDouble("leftDriveP", 0), pref.getDouble("leftDriveI", 0), pref.getDouble("leftDriveD", 0));
		this.mDriveStraight.setRightPID(pref.getDouble("rightDriveP", 0), pref.getDouble("rightDriveI", 0), pref.getDouble("rightDriveD", 0));
		this.mDriveStraight.runPID();

		this.mGyro.zeroGyro();
		this.mRotate = new Rotate();
		this.mRotate.setPID(pref.getDouble("angleP", 0), pref.getDouble("angleI", 0), pref.getDouble("angleD", 0));
		this.mRotate.rotate(35*sideScaler, 0.5, 5, 3);
		
		_robot.sDrive.zeroEncoders();
		this.mDriveStraight = new DriveStraight(-3.5, 0.6, 0.2, 0.5);
		this.mDriveStraight.setLeftPID(pref.getDouble("leftDriveP", 0), pref.getDouble("leftDriveI", 0), pref.getDouble("leftDriveD", 0));
		this.mDriveStraight.setRightPID(pref.getDouble("rightDriveP", 0), pref.getDouble("rightDriveI", 0), pref.getDouble("rightDriveD", 0));
		this.mDriveStraight.runPID();
		
		this.mAction.openIntake();
		this.mAction.shootToScaleBack();
		
//		this.mRotate = new Rotate();
//		this.mRotate.rotate(90, 0.4, 5, 2);
	}
	
	
	
}