package org.usfirst.frc.team346.auto.plans;

import org.usfirst.frc.team346.auto.actions.DriveStraight;
import org.usfirst.frc.team346.auto.actions.Rotate;
import org.usfirst.frc.team346.auto.actions.ActionRunner;
import org.usfirst.frc.team346.robot.Robot;
import org.usfirst.frc.team346.robot.RobotMap;
import org.usfirst.frc.team346.subsystems.Drive;
import org.usfirst.frc.team346.subsystems.Gyro;

import edu.wpi.first.wpilibj.Preferences;

public class OldSideStart extends AutoPlan {
	
	Rotate mRotate;
	DriveStraight mDriveStraight;
	ActionRunner mAction;
	Drive sDrive = Drive.getInstance();
	Preferences pref = Preferences.getInstance();
	
	Gyro mGyro = Gyro.getInstance();
	
	public String getGoal() {
		return "cross baseline, place 1 cube in switch";
	}
	
	public void run(double _switchLeft, double _scaleLeft) {
		this.mAction = new ActionRunner();
		
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
		this.mRotate.rotate(35*_scaleLeft, 0.5, 5, 3);
		
		this.sDrive.zeroEncoders();
		this.mDriveStraight = new DriveStraight(-3.5, 0.6, 0.2, 0.5);
		this.mDriveStraight.setLeftPID(pref.getDouble("leftDriveP", 0), pref.getDouble("leftDriveI", 0), pref.getDouble("leftDriveD", 0));
		this.mDriveStraight.setRightPID(pref.getDouble("rightDriveP", 0), pref.getDouble("rightDriveI", 0), pref.getDouble("rightDriveD", 0));
		this.mDriveStraight.runPID();
		
		this.mAction.openIntake();
		this.mAction.shootToScaleBack();
	}
	
}