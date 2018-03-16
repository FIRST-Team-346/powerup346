package org.usfirst.frc.team346.auto.plans;

import org.usfirst.frc.team346.auto.actions.ActionRunner;

public class BaselineDriveFollow extends AutoPlan {
	ActionRunner action;
	
	public String getGoal() {
		return "cross baseline using drivefollow";
	}
	
	public void run(double _startingLeft, double _switchLeft, double _scaleLeft) {
		super.driveUsingDF(16);
		this.action.setJustIntakeIn(-1);
		super.waitTime(2);
		this.action.setJustIntakeIn(0);
	}
}
