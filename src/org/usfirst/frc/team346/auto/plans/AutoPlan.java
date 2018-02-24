package org.usfirst.frc.team346.auto.plans;

import org.usfirst.frc.team346.auto.actions.DriveFollow;
import org.usfirst.frc.team346.robot.Robot;
import org.usfirst.frc.team346.subsystems.Drive;

public class AutoPlan {
	
	public String getGoal() {
		return "default goal";
	}
	
	public void run(Robot _robot, String _layout) {
		System.out.println("default run; check that your run takes the _layout parameter");
	}
	
	public void driveUsingDF(double _distanceFt) {
		DriveFollow DF = new DriveFollow(_distanceFt, 0);
		new Thread(DF).start();
		while (DF.isDriving()) {
		}
		System.out.println("DF| driving complete, final distance:" + Drive.getInstance().getAveragedPosition()/1024.);
	}
	
	public void waitTime(double _seconds) {
		long initialTime = System.currentTimeMillis();
		while(System.currentTimeMillis() - initialTime < Math.abs(_seconds) * 1000.) {
		}
	}
	
}