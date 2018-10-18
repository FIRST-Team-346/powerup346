package org.usfirst.frc.team346.auto.actions;

import org.usfirst.frc.team346.robot.RobotMap;

import edu.wpi.first.wpilibj.DriverStation;

public class ShootToScaleBack implements Runnable {

	private ActionRunner action = new ActionRunner();
	
	public void run() {
		if(this.action.isDisabled()) return;
		this.action.setTilterPosNu(RobotMap.kTiltPosScaleBack);
		this.action.setShooter(RobotMap.kShooterLeftSetpointNuBack, RobotMap.kShooterRightSetpointNuBack);
		this.action.waitUntilAtSpeed(2.0);
		this.action.setOuttakePercentFront(1.0);
		double lShootTime = System.currentTimeMillis();
		while(System.currentTimeMillis() - lShootTime < 1.0 * 1000.) {
			if(this.action.isDisabled()) return;
		}
		this.action.setOuttakePercentFront(0.0);
		this.action.setShooterPercentFront(0.0);
	}

}