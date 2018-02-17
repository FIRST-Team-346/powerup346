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
//		mDriveStraight = new DriveStraight(10, 1, mGyro.getAngle(), 5, 0.5);
//		super.waitTime(5);
//		mRotate = new Rotate(90,1, 5, 5);
		
//		this.mDriveFollow = new DriveFollow(_robot);
//		this.mDriveFollow.followLine(5);
		
		this.mRotate = new Rotate();
		this.mRotate.setPID(pref.getDouble("angleP", 0), pref.getDouble("angleI", 0), pref.getDouble("angleD", 0));
		this.mRotate.rotate(pref.getDouble("rotateAngle", 0), 0.4, 5, 1.5);
		super.waitTime(1);
		System.out.println(this.mGyro.getAngle());
		
//		this.mDriveStraight = new DriveStraight(17, 0.5, 1, 1);
//		super.waitTime(3);
//		this.mRotate.rotate(-90,0.4,5,1.5);
//		super.waitTime(3);
//		this.mDriveStraight = new DriveStraight(13, 0.5, 1, 1);
//		super.waitTime(3);
//		this.mRotate.rotate(-90,0.4,5,1.5);
//		super.waitTime(3);
//		this.mDriveStraight = new DriveStraight(17,0.5,1,1);
//		super.waitTime(3);
//		this.mRotate.rotate(-90,0.4,5,1.5);
//		super.waitTime(3);
//		this.mDriveStraight = new DriveStraight(13, 0.5, 1, 1);
//		super.waitTime(3);
	}
	
}