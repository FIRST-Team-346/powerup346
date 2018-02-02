package org.usfirst.frc.team346.auto.plans;

import org.usfirst.frc.team346.auto.actions.DriveStraight;
import org.usfirst.frc.team346.auto.actions.Rotate;
import org.usfirst.frc.team346.subsystems.Gyro;

public class SwitchCube extends AutoPlan {
	
	Rotate rotate;
	DriveStraight drive;
	
	Gyro gyro = Gyro.getInstance();
	
	
	public String getGoal() {
		return "cross baseline, place 1 cube in switch";
	}
	
	public void run() {
		drive = new DriveStraight(10, 1,gyro.getAngle(), 5, 0.5);
		super.waitTime(5);
		rotate = new Rotate(90,1, 5, 5);
	}
	
}