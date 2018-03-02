package org.usfirst.frc.team346.auto.plans;

import org.usfirst.frc.team346.auto.actions.DriveStraight;
import org.usfirst.frc.team346.auto.actions.Rotate;
import org.usfirst.frc.team346.auto.actions.ActionRunner;
import org.usfirst.frc.team346.robot.Robot;
import org.usfirst.frc.team346.robot.RobotMap;
import org.usfirst.frc.team346.subsystems.Gyro;

import edu.wpi.first.wpilibj.Preferences;

public class SideStartFront extends AutoPlan {
	
	Rotate mRotate;
	DriveStraight mDriveStraight;
	ActionRunner mAction;
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
		this.mDriveStraight = new DriveStraight(-25, 0.6, 2.05, 0.5);
		this.mDriveStraight.setLeftPID(pref.getDouble("leftDriveP", 0), pref.getDouble("leftDriveI", 0), pref.getDouble("leftDriveD", 0));
		this.mDriveStraight.setRightPID(pref.getDouble("rightDriveP", 0), pref.getDouble("rightDriveI", 0), pref.getDouble("rightDriveD", 0));
		this.mDriveStraight.runPID();

		this.waitTime(0.25);
		this.mGyro.zeroGyro();
		this.mRotate = new Rotate();
		this.mRotate.setPID(pref.getDouble("angleP", 0), pref.getDouble("angleI", 0), pref.getDouble("angleD", 0));
		this.mRotate.rotate(-120*_scaleLeft, 0.5, 5, 3);
		
		this.mAction.openIntake();
		this.mAction.shootToScaleFront();
		
//		this.mRotate = new Rotate();
//		this.mRotate.rotate(90, 0.4, 5, 2);
	}
	
	
	
}