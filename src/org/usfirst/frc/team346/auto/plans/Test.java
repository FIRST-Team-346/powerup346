package org.usfirst.frc.team346.auto.plans;

import org.usfirst.frc.team346.auto.actions.*;
import org.usfirst.frc.team346.robot.Robot;
import org.usfirst.frc.team346.robot.RobotMap;
import org.usfirst.frc.team346.subsystems.Gyro;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.Preferences;

public class Test extends AutoPlan{

	Rotate mRotate = new Rotate();
	DriveStraight mDriveStraight;
	DriveFollow mDriveFollow;
	ActionRunner mAction = new ActionRunner();
	
	Gyro gyro = Gyro.getInstance();
	Preferences pref = Preferences.getInstance();
	
	public String getGoal() {
		return "test";
	}
	
	public void run(double _startingLeft, double _switchLeft, double _scaleLeft) {
		this.gyro.calibrate();
//		super.driveUsingDF(this.pref.getDouble("dfDistance", 0));
		
		super.rotateUsingRT(this.pref.getDouble("rtAngle", 0));
		super.waitTime(1);
		System.out.println("Final Rotate Angle" + this.gyro.getAngle());
	}
	
}