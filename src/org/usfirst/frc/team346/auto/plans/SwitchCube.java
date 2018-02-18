package org.usfirst.frc.team346.auto.plans;

import org.usfirst.frc.team346.auto.actions.DriveStraight;
import org.usfirst.frc.team346.auto.actions.Rotate;
import org.usfirst.frc.team346.robot.Robot;
import org.usfirst.frc.team346.subsystems.Gyro;

public class SwitchCube extends AutoPlan {
	
	Rotate mRotate;
	DriveStraight mDrive;
	
	Gyro mGyro = Gyro.getInstance();
	
	
	public String getGoal() {
		return "cross baseline, place 1 cube in switch";
	}
	
	public void run(Robot _robot, String _layout) {
		mDrive = new DriveStraight(10, 1, this.mGyro.getAngle(), 0.5, 5);
		super.waitTime(5);
		mRotate = new Rotate();
		this.mRotate.rotate(90,1, 5, 5);
	}
	
	public void runCloseLeft() {
		
	}
	
	public void runCloseRight() {
		
	}
	
	public void runFarLeft() {
		
	}
	
	public void runFarRight() {
		
	}
	
}