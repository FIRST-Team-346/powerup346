package org.usfirst.frc.team346.auto.plans;

import org.usfirst.frc.team346.auto.actions.*;
import org.usfirst.frc.team346.robot.Robot;
import org.usfirst.frc.team346.robot.RobotMap;
import org.usfirst.frc.team346.subsystems.Gyro;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.Preferences;

public class Test extends AutoPlan{

	Rotate mRotate;
	RotateSingleSide mRotateSS;
	DriveStraight mDriveStraight;
	DriveFollow mDriveFollow;
	ActionRunner mAction = new ActionRunner();
	
	Preferences pref = Preferences.getInstance();
	
	Gyro mGyro = Gyro.getInstance();
	
	public String getGoal() {
		return "test";
	}
	
	public void run(Robot _robot, String _layout) {
		this.mGyro.calibrate();
		
		this.mRotateSS = new RotateSingleSide();
		if(this.pref.getBoolean("ssLeft", true)) {
			this.mRotateSS.rotateSingleSide(pref.getDouble("angle", 0), Hand.kLeft, 0.5, 5, 1.5);
		}
		else {
			this.mRotateSS.rotateSingleSide(pref.getDouble("angle", 0), Hand.kRight, 0.5, 5, 1.5);
		}

	}
	
}