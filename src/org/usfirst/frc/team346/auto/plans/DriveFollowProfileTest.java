package org.usfirst.frc.team346.auto.plans;

import org.usfirst.frc.team346.auto.actions.DriveFollowProfile;
import org.usfirst.frc.team346.robot.Robot;

import edu.wpi.first.wpilibj.Preferences;

public class DriveFollowProfileTest extends AutoPlan {
	
	Preferences mPref = Preferences.getInstance();
	DriveFollowProfile mDFP;
	
	public String getGoal() {
		return "test DriveFollowProfile";
	}
	
	public void run(Robot _robot, String _layout) {
		this.driveUsingDFP(this.mPref.getDouble("dfpDistance", 0), this.mPref.getDouble("dfpVelocity", 0));
	}
	
	private void driveUsingDFP(double _distance, double _velocity) {
		this.mDFP = new DriveFollowProfile(_distance, _velocity);
		new Thread(this.mDFP).start();
		while (this.mDFP.isDriving()) {
		}
		System.out.println("DFP| complete");
	}

}