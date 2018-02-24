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
		this.driveUsingDF(this.mPref.getDouble("dfDistance", 0), this.mPref.getDouble("dfVelocity", 0));
	}
	
	private void driveUsingDF(double _distance, double _velocity) {
		this.DF = new DriveFollow(_distance, _velocity);
		new Thread(this.DF).start();
		while (this.DF.isDriving()) {
		}
		System.out.println("DF| driving complete");
		super.waitTime(2);
		System.out.println("DF| final distance:" + Drive.getInstance().getAveragedPosition()/1024.);
	}

}