package org.usfirst.frc.team346.auto.actions;

import org.usfirst.frc.team346.robot.RobotMap;

import edu.wpi.first.wpilibj.DriverStation;

public class ShootToScaleFront implements Runnable {

	private ActionRunner action = new ActionRunner();
	
	public void run() {
		this.action.setTilterPosNu(RobotMap.kTiltPosScaleHigh);
		this.action.setShooter(RobotMap.kShooterLeftSetpointNuHigh, RobotMap.kShooterRightSetpointNuHigh);
		this.action.waitUntilAtSpeed(2.0);
		this.action.setOuttakePercentFront(1.0);
		double lShootTime = System.currentTimeMillis();
		while(System.currentTimeMillis() - lShootTime < 1.0 * 1000.) {
			if(!DriverStation.getInstance().isAutonomous() || DriverStation.getInstance().isDisabled()) {
				return;
			}
		}
		this.action.setOuttakePercentFront(0.0);
		this.action.setShooterPercentFront(0.0);
	}

}