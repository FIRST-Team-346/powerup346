package org.usfirst.frc.team346.auto.plans;

import org.usfirst.frc.team346.subsystems.Gyro;

import edu.wpi.first.wpilibj.Preferences;

public class DriveFollowTune extends AutoPlan {
	
	Gyro sGyro = Gyro.getInstance();
	Preferences mPref = Preferences.getInstance();
	
	public String getGoal() {
		return "test DriveFollow";
	}
	
	public void run(double _switchLeft, double _scaleLeft) {
		this.sGyro.calibrate();
		super.driveUsingDF(this.mPref.getDouble("dfDistance", 0));
	}

}