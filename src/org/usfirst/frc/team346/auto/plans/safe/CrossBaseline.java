package org.usfirst.frc.team346.auto.plans.safe;

import org.usfirst.frc.team346.auto.AutoPlan;
import org.usfirst.frc.team346.auto.actions.ActionRunner;
import org.usfirst.frc.team346.subsystems.Drive;
import org.usfirst.frc.team346.subsystems.Drive.DriveMode;

public class CrossBaseline extends AutoPlan {
	ActionRunner action;
	
	public String getGoal() {
		return "cross baseline using drivefollow";
	}
	
	public void run(double _startingLeft, double _switchLeft, double _scaleLeft) {
		this.action.setJustIntakeIn(-1);
		super.driveUsingDF(18);
		this.action.setJustIntakeIn(0);
	}
	
//	private void crossOnVoltage() {
//		double lInitialTime = System.currentTimeMillis() /1000.;
//		while(System.currentTimeMillis()/1000. - lInitialTime < 5) {
//			Drive.getInstance().drive(DriveMode.PERCENT, 0.5, 0.5);
//		}
//		Drive.getInstance().drive(DriveMode.PERCENT, 0, 0);
//	}
}
