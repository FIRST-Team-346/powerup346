package org.usfirst.frc.team346.auto.plans;

import org.usfirst.frc.team346.subsystems.Drive;
import org.usfirst.frc.team346.subsystems.Drive.DriveMode;

public class CrossBaseline extends AutoPlan {

	private Drive sDrive = Drive.getInstance();
	
	public String getGoal() {
		return "cross baseline, using only percent voltage";
	}
	
	public void run(double _switchLeft, double _scaleLeft) {
		double lInitialTime = System.currentTimeMillis() /1000.;
		while(System.currentTimeMillis()/1000. - lInitialTime < 5) {
			this.sDrive.drive(DriveMode.PERCENT, 0.5, 0.5);
		}
		this.sDrive.drive(DriveMode.PERCENT, 0, 0);
	}

}