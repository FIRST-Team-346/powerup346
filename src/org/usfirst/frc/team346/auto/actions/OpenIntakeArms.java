package org.usfirst.frc.team346.auto.actions;

import org.usfirst.frc.team346.robot.RobotMap;

public class OpenIntakeArms implements Runnable {

	private ActionRunner action = new ActionRunner();
	
	public void run() {
		this.action.setJustIntakeIn(-1.0);
		double lOpenTime = System.currentTimeMillis();
		while(System.currentTimeMillis() - lOpenTime < 0.4 * 1000.) {
		}
		this.action.setJustIntakeIn(0.0);
	}

}