package org.usfirst.frc.team346.auto.plans;

import org.usfirst.frc.team346.auto.actions.DriveFollow;
import org.usfirst.frc.team346.auto.actions.RotateThread;
import org.usfirst.frc.team346.robot.Robot;
import org.usfirst.frc.team346.subsystems.Drive;
import org.usfirst.frc.team346.subsystems.Gyro;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.GenericHID.Hand;

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
	
	public void rotateUsingRT(double _angleDegrees) {
		if(!DriverStation.getInstance().isAutonomous() || DriverStation.getInstance().isDisabled()) {
			return;
		}
		
		RotateThread RT = new RotateThread(_angleDegrees, 0.5);
		new Thread(RT).start();
		while (RT.isRotating()) {
		}
		System.out.println("DF| driving complete, final angle:" + Gyro.getInstance().getAngle());
	}
	
	public void rotateUsingRT(Hand _side, double _angleDegrees) {
		if(!DriverStation.getInstance().isAutonomous() || DriverStation.getInstance().isDisabled()) {
			return;
		}
		
		RotateThread RT = new RotateThread(_side, _angleDegrees, 0.5);
		new Thread(RT).start();
		while (RT.isRotating()) {
		}
		System.out.println("DF| driving complete, final angle:" + Gyro.getInstance().getAngle());
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