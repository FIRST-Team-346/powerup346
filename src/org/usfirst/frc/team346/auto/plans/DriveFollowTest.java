package org.usfirst.frc.team346.auto.plans;

import org.usfirst.frc.team346.auto.actions.DriveFollow;
import org.usfirst.frc.team346.auto.actions.DriveFollowProfile;
import org.usfirst.frc.team346.robot.Robot;

import edu.wpi.first.wpilibj.Preferences;

public class DriveFollowTest extends AutoPlan {
	
	Preferences mPref = Preferences.getInstance();
	DriveFollowProfile mDFP;
	
	public String getGoal() {
		return "test DriveFollow";
	}
	
	public void run(Robot _robot, String _layout) {
//		new DriveFollow(_robot).followLine(this.mPref.getDouble("DFDistance", 0));
		this.driveUsingDFP(0, 0);
	}
	
	private void driveUsingDFP(double _distance, double _velocity) {
		this.mDFP = new DriveFollowProfile(_distance, _velocity);
		new Thread(this.mDFP).start();
		while (this.mDFP.isDriving()) {
		}
	}

}