package org.usfirst.frc.team346.auto.plans;

import org.usfirst.frc.team346.auto.actions.DriveFollow;
import org.usfirst.frc.team346.auto.actions.DriveStraight;
import org.usfirst.frc.team346.auto.actions.Rotate;
import org.usfirst.frc.team346.robot.Robot;
import org.usfirst.frc.team346.subsystems.Gyro;

import edu.wpi.first.wpilibj.Preferences;

public class Test extends AutoPlan{

	Rotate mRotate;
	DriveStraight mDriveStraight;
	DriveFollow mDriveFollow;
	
	Preferences pref = Preferences.getInstance();
	
	Gyro mGyro = Gyro.getInstance();
	
	
	public String getGoal() {
		return "test";
	}
	
	public void run(Robot _robot, String _layout) {
		mDriveStraight = new DriveStraight(10., 1., this.mGyro.getAngle(), 0.5,5.);
		this.mDriveStraight.setLeftPID(pref.getDouble("leftDriveP", 0), pref.getDouble("leftDriveI", 0), pref.getDouble("leftDriveD", 0));
		this.mDriveStraight.setRightPID(pref.getDouble("rightDriveP", 0), pref.getDouble("rightDriveI", 0), pref.getDouble("rightDriveD", 0));
		this.mDriveStraight.runPID();
		super.waitTime(1);
		this.mDriveStraight.printDistance();

	}
	
}