package org.usfirst.frc.team346.auto.plans;

import org.usfirst.frc.team346.robot.Robot;

public class AutoPlan {
	
	public String getGoal() {
		return "default goal";
	}
	
	public void run(Robot _robot) {
		System.out.println("default run");
	}
	
	public void waitTime(double _seconds) {
		long initialTime = System.currentTimeMillis();
		while(System.currentTimeMillis() - initialTime < Math.abs(_seconds) * 1000) {
		}
	}
	
}