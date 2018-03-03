package org.usfirst.frc.team346.auto.plans;

import org.usfirst.frc.team346.auto.actions.DriveFollow;
import org.usfirst.frc.team346.robot.Robot;
import org.usfirst.frc.team346.subsystems.Drive;

import edu.wpi.first.wpilibj.DriverStation;

public class AutoPlan {
	
	public String getGoal() {
		return "default goal";
	}
	
	public void run(double _switchLeft, double _scaleLeft) {
		System.out.println("default run; check that your run takes the layout parameters");
	}
	
	public void driveUsingDF(double _distanceFt) {
		if(!DriverStation.getInstance().isAutonomous() || DriverStation.getInstance().isDisabled()) {
			return;
		}
		
		DriveFollow DF = new DriveFollow(_distanceFt, 0);
		new Thread(DF).start();
		while (DF.isDriving()) {
		}
		System.out.println("DF| driving complete, final distance:" + Drive.getInstance().getAveragedPosition()/1024.);
	}
	
	public void waitTime(double _seconds) {
		long initialTime = System.currentTimeMillis();
		while(System.currentTimeMillis() - initialTime < Math.abs(_seconds) * 1000.) {
			if(!DriverStation.getInstance().isAutonomous() || DriverStation.getInstance().isDisabled()) {
				return;
			}
		}
	}
	
}