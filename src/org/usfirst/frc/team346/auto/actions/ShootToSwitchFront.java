package org.usfirst.frc.team346.auto.actions;

import org.usfirst.frc.team346.robot.RobotMap;

public class ShootToSwitchFront implements Runnable {

	private ActionRunner action = new ActionRunner();
	
	public void run() {
		this.action.setTilterPosNu(RobotMap.kTiltPosVault);
		this.action.setOuttakePercentFront(1.0);
		this.action.setShooterPercentFront(0.3);
		this.action.waitTime(1);
		this.action.setOuttakePercentFront(0.0);
		this.action.setShooterPercentFront(0.0);
	}

}