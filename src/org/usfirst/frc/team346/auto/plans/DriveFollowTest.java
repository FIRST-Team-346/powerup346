package org.usfirst.frc.team346.auto.plans;

import org.usfirst.frc.team346.auto.actions.DriveFollow;
import org.usfirst.frc.team346.robot.Robot;

public class DriveFollowTest extends AutoPlan {
	
	public String getGoal() {
		return "test DriveFollow";
	}
	
	public void run(Robot _robot) {
		new DriveFollow(_robot).followLine(5);
	}

}