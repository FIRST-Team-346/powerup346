package org.usfirst.frc.team346.auto.plans;

import org.usfirst.frc.team346.auto.actions.DriveFollow;
import org.usfirst.frc.team346.robot.Robot;
import org.usfirst.frc.team346.subsystems.Drive;

import edu.wpi.first.wpilibj.Preferences;

public class DriveFollowTest extends AutoPlan {
	
	Preferences mPref = Preferences.getInstance();
	DriveFollow DF;
	
	public String getGoal() {
		return "test DriveFollow";
	}
	
	public void run(Robot _robot, String _layout) {
		_robot.sGyro.calibrate();
		super.driveUsingDF(this.mPref.getDouble("dfDistance", 0));
	}

}