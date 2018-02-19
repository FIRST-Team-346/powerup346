package org.usfirst.frc.team346.auto.plans;

import org.usfirst.frc.team346.auto.actions.DriveStraight;
import org.usfirst.frc.team346.auto.actions.Rotate;
import org.usfirst.frc.team346.robot.Robot;
import org.usfirst.frc.team346.subsystems.Gyro;

public class SwitchCube extends AutoPlan {
	
	Rotate mRotate;
	DriveStraight mDrive;
	
	Gyro mGyro = Gyro.getInstance();
	
	String layout;
	
	public String getGoal() {
		return "cross baseline, place 1 cube in switch";
	}
	
	public void run(Robot _robot, String _layout) {
		layout = _layout;
//		if(layout.charAt(0)=='L') {
			this.mGyro.zeroGyro();
			mRotate = new Rotate();
			mRotate.setLeftSide(2);
			mRotate.setRightSide(0);
			
			mRotate.rotate(45,1, 5, 5);
			
//			mDrive = new DriveStraight(3, 1, 0.5, 5);
//			mDrive.runPID();
//			
//			mRotate.setLeftSide(true);
//			mRotate.rotate(45,1, 5, 5);
//			
//			mDrive = new DriveStraight(5, 1, 0.5, 5);
//			mDrive.runPID();
//		}
//		else {
//			mRotate = new Rotate();
//			mRotate.setRightSide(false);
//			mRotate.rotate(45,1, 5, 5);
//			
//			mDrive = new DriveStraight(3, 1, 0.5, 5);
//			mDrive.runPID();
//			
//			mRotate.setRightSide(true);
//			mRotate.rotate(-45,1, 5, 5);
//			
//			mDrive = new DriveStraight(5, 1, 0.5, 5);
//			mDrive.runPID();
//		}
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