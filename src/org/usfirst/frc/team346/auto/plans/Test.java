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
	
	Preferences pref = Preferences.getInstance();
	
	Gyro mGyro = Gyro.getInstance();
	
	public String getGoal() {
		return "test";
	}
	
	public void run(double _switchLeft, double _scaleLeft) {
		this.mGyro.calibrate();	
		if(_switchLeft == -1 && _scaleLeft == 1) {
			this.mRotate.rotateSingleSide(Hand.kLeft, pref.getDouble("rotateAngle", 0), 0.5, 5, 1.5);
		}
		else if(_switchLeft == -1 && _scaleLeft == -1){
			this.mRotate.rotateSingleSide(Hand.kRight, pref.getDouble("rotateAngle", 0), 0.5, 5, 1.5);
		}
		else {
			this.mRotate.rotate(pref.getDouble("rotateAngle", 0), 0.5, 5, 1.5);
		}
		super.waitTime(1);
		System.out.println("Rotate final angle:" + this.mGyro.getAngle());
		
//		this.mAction.openIntake();
//		System.out.println("Intake opened.");
//		super.waitTime(5);
//		this.mAction.shootToScaleBack();
//		System.out.println("Shot to scale back.");
//		super.waitTime(5);
//		this.mAction.shootToScaleFront();
//		System.out.println("Shot to scale front.");
//		super.waitTime(5);
//		this.mAction.shootToSwitchBack();
//		System.out.println("Shot to switch back.");
//		super.waitTime(5);
//		this.mAction.shootToSwitchFront();
//		System.out.println("Shot to switch front.");
	}
	
}