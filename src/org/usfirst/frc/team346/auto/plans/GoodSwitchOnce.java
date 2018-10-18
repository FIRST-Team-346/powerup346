package org.usfirst.frc.team346.auto.plans;

import org.usfirst.frc.team346.auto.AutoPlan;
import org.usfirst.frc.team346.auto.actions.ActionRunner;
import org.usfirst.frc.team346.auto.actions.Rotate;
import org.usfirst.frc.team346.robot.RobotMap;
import org.usfirst.frc.team346.subsystems.Gyro;

public class GoodSwitchOnce extends AutoPlan {

	Gyro sGyro = Gyro.getInstance();
	ActionRunner sAction = new ActionRunner();
	Rotate sRotator = new Rotate();
	
	private double startingOnLeft, switchLeft, scaleLeft;
	
	public String getGoal() {
		return "good switch once" + this.startingOnLeft;
	}
	
	public void run(double _startingLeft, double _switchLeft, double _scaleLeft) {
		this.startingOnLeft = _startingLeft;
		this.switchLeft = _switchLeft;
		this.scaleLeft = _scaleLeft;
		
		this.start();
	}
	
	public void start() {
		this.sAction.setTilterPosNu(RobotMap.kTiltPosVault+20);
		super.driveUsingDF(16.5);
		
		super.rotateUsingRT(90 * this.startingOnLeft);
		
		super.driveUsingDF(4);
		this.sAction.setOuttakePercentFront(1);
		this.sAction.setShooterPercentFront(0.32);
		super.waitTime(1);
		this.sAction.setOuttakePercentFront(0);
		this.sAction.setShooterPercentFront(0);
		
		super.driveUsingDF(-4);
		this.sAction.startOpenIntake();
		super.rotateUsingRT(-90 * this.startingOnLeft);
		this.sAction.stopOpenIntake();
	}
}