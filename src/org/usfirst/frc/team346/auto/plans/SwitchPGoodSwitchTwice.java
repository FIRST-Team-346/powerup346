package org.usfirst.frc.team346.auto.plans;

import org.usfirst.frc.team346.auto.AutoPlan;
import org.usfirst.frc.team346.auto.actions.ActionRunner;
import org.usfirst.frc.team346.auto.actions.Rotate;
import org.usfirst.frc.team346.robot.RobotMap;
import org.usfirst.frc.team346.subsystems.Gyro;
import org.usfirst.frc.team346.subsystems.Lights;

import edu.wpi.first.wpilibj.GenericHID.Hand;

public class SwitchPGoodSwitchTwice extends AutoPlan {

	Gyro sGyro = Gyro.getInstance();
	ActionRunner sAction = new ActionRunner();
	Rotate sRotator = new Rotate();
	
	private double startingOnLeft, switchLeft, scaleLeft;
	
	public String getGoal() {
		return "good switch, then good scale, " + this.startingOnLeft;
	}
	
	public void run(double _startingLeft, double _switchLeft, double _scaleLeft) {
		this.startingOnLeft = _startingLeft;
		this.switchLeft = _switchLeft;
		this.scaleLeft = _scaleLeft;
		
//		Lights.getInstance().setGreen();
		this.goodSwitchTwice();
	}
	
	public void goodSwitchTwice() {
		this.sAction.setTilterPosNu(RobotMap.kTiltPosVault+20);
		super.driveUsingDF(16.5);
		
		this.sAction.setJustIntakeIn(0);
		super.rotateUsingRT(90 * this.startingOnLeft);
		
		this.sAction.setOuttakePercentFront(1);
		this.sAction.setShooterPercentFront(0.55);
		super.waitTime(0.25);
		this.sAction.setOuttakePercentFront(0);
		this.sAction.setShooterPercentFront(0);
		this.sAction.setTilterPosNu(RobotMap.kTiltPosScaleLow);
		super.waitTime(0.25);
		
		super.rotateUsingRT(90 * this.startingOnLeft);
		
		this.sAction.setJustIntakeIn(-0.5);
		super.driveUsingDF(-12);
		this.sAction.setJustIntakeIn(0);
		
		super.rotateUsingRT(-30 * this.startingOnLeft);
		
		this.sAction.setIntakeIn(0.75);
		super.driveUsingDF(6.5);
		this.sAction.setTilterPosNu(RobotMap.kTiltPosNeutral);
		super.waitTime(0.25);
		this.sAction.setIntakeIn(0);
		
		this.sAction.setTilterPosNu(RobotMap.kTiltPosVault+20);
		super.waitTime(0.75);
		this.sAction.setOuttakePercentFront(1);
		this.sAction.setShooterPercentFront(0.55);
	}
}