package org.usfirst.frc.team346.auto.plans;

import org.usfirst.frc.team346.robot.Robot;
import org.usfirst.frc.team346.subsystems.Drive.DriveMode;

public class CrossBaseline extends AutoPlan {

	public String getGoal() {
		return "cross baseline, using only percent voltage";
	}
	
	public void run(Robot _robot, String _layout) {
		double lInitialTime = System.currentTimeMillis() /1000.;
		while(System.currentTimeMillis()/1000. - lInitialTime < 5) {
			_robot.sDrive.drive(DriveMode.PERCENT, 0.5, 0.5);
		}
		_robot.sDrive.drive(DriveMode.PERCENT, 0, 0);
	}

}