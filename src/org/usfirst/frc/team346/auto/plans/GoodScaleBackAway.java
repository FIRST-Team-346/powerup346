package org.usfirst.frc.team346.auto.plans;

import org.usfirst.frc.team346.auto.AutoPlan;
import org.usfirst.frc.team346.auto.actions.ActionRunner;
import org.usfirst.frc.team346.robot.RobotMap;
import org.usfirst.frc.team346.subsystems.Gyro;

public class GoodScaleBackAway extends AutoPlan {

	Gyro sGyro = Gyro.getInstance();
	ActionRunner sAction = new ActionRunner();
	
	private double startingOnLeft, switchLeft, scaleLeft;
	
	public String getGoal() {
		return "good scale back away" + this.startingOnLeft;
	}
	
	public void run(double _startingLeft, double _switchLeft, double _scaleLeft) {
		this.startingOnLeft = _startingLeft;
		this.switchLeft = _switchLeft;
		this.scaleLeft = _scaleLeft;
		
		this.start();
	}
	
	public void start() {
		this.sAction.setTilterPosNu(RobotMap.kTiltPosScaleHigh);
		
		super.driveUsingDF(28.75);
		
		this.sAction.setShooter(RobotMap.kShooterLeftSetpointNuAutoMidHigh-200, RobotMap.kShooterRightSetpointNuAutoMidHigh-200);
		super.rotateUsingRT(45 * this.startingOnLeft);
		
		this.sAction.setOuttakePercentFront(1);
		super.waitTime(0.75);
		this.sAction.setShooter(0, 0);
		this.sAction.setOuttakePercentFront(0);
		
		this.sAction.startOpenIntake();
		super.driveUsingDF(-8);
		this.sAction.stopOpenIntake();
	}
	
}