package org.usfirst.frc.team346.auto.plans;

import org.usfirst.frc.team346.auto.actions.DriveFollow;
import org.usfirst.frc.team346.auto.actions.DriveFollowProfile;
import org.usfirst.frc.team346.robot.Robot;

import edu.wpi.first.wpilibj.Preferences;

public class DriveFollowTest extends AutoPlan {
	
	Preferences mPref = Preferences.getInstance();
	
	public String getGoal() {
		return "test DriveFollow";
	}
	
	public void run(Robot _robot, String _layout) {
		this.driveUsingDF(this.mPref.getDouble("dfpDistance", 0), this.mPref.getDouble("dfpVelocity", 0));
		super.waitTime(2);
		System.out.println("DF| actual distance:" + _robot.sDrive.getAveragedPosition()/1024.);
	}
	
	public void driveUsingDF(double _distance, double _velocity) {
		DriveFollow lDF = new DriveFollow(_distance, _velocity);
		lDF.followLine();
		while(lDF.isDriving()) {
		}
		System.out.println("DF| complete");
	}

}