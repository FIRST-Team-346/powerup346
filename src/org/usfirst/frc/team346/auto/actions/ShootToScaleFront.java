package org.usfirst.frc.team346.auto.actions;

import org.usfirst.frc.team346.robot.RobotMap;

public class ShootToScaleFront implements Runnable {

	private ActionRunner action = new ActionRunner();
	
	public void run() {
		this.action.setTilterPosNu(RobotMap.kTiltPosScaleHigh);
		this.action.setShooter(RobotMap.kShooterLeftSetpointNuMid, RobotMap.kShooterRightSetpointNuMid);
		this.action.waitUntilAtSpeed(2.0);
		this.action.setOuttakePercentFront(1.0);
		this.action.waitTime(1);
		this.action.setOuttakePercentFront(0.0);
		this.action.setShooterPercentFront(0.0);
	}

}