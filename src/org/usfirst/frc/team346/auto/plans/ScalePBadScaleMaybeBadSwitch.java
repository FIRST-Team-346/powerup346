package org.usfirst.frc.team346.auto.plans;

import org.usfirst.frc.team346.auto.AutoPlan;
import org.usfirst.frc.team346.auto.actions.ActionRunner;
import org.usfirst.frc.team346.auto.actions.Rotate;
import org.usfirst.frc.team346.robot.RobotMap;
import org.usfirst.frc.team346.subsystems.Gyro;
import org.usfirst.frc.team346.subsystems.Lights;

import edu.wpi.first.wpilibj.GenericHID.Hand;

public class ScalePBadScaleMaybeBadSwitch extends AutoPlan {

	Gyro sGyro = Gyro.getInstance();
	ActionRunner sAction = new ActionRunner();
	Rotate sRotator = new Rotate();
	
	private double startingOnLeft, switchLeft, scaleLeft;
	
	public String getGoal() {
		return "cross conduit, shoot bad scale, intake, maybe bad switch: " + this.startingOnLeft;
	}
	
	public void run(double _startingLeft, double _switchLeft, double _scaleLeft) {
		this.startingOnLeft = _startingLeft;
		this.switchLeft = _switchLeft;
		this.scaleLeft = _scaleLeft;
		
//		Lights.getInstance().setGreen();
		if(this.startingOnLeft == 1) {
			this.left();
		}
		else {
			this.right();
		}
	}
	
	public void left() {
		this.sAction.setTilterPosNu(RobotMap.kTiltPosScaleHigh);
		
		super.driveUsingDF(24);
		
		super.rotateUsingRT(-90 * this.startingOnLeft);
		
		super.driveUsingDF(-24);
		
		this.sAction.setShooter(RobotMap.kShooterLeftSetpointNuLow-250, RobotMap.kShooterRightSetpointNuLow-250);
		this.sAction.setJustIntakeIn(-0.2);
		super.rotateUsingRT(75 * this.startingOnLeft);
		this.sAction.setJustIntakeIn(0);
		
		super.driveUsingDF(3);
		this.sAction.setOuttakePercentFront(1);
		this.waitTime(0.25);
		this.sAction.setOuttakePercentFront(0);
		this.sAction.setShooterPercentFront(0);
		
		super.driveUsingDF(-4);
		super.rotateUsingRTAbsolute(-(75+55) * this.startingOnLeft);
		
		this.sAction.setIntakeIn(1);
		super.driveUsingDF(4);
		
		if(this.scaleLeft == this.switchLeft) {
			this.sAction.setTilterPosNu(RobotMap.kTiltPosVault);
			super.waitTime(0.5);
			this.sAction.setOuttakePercentFront(1);
			this.sAction.setShooterPercentFront(0.55);
		}
	}
	
	public void right() {
		this.sAction.setTilterPosNu(RobotMap.kTiltPosScaleHigh);
		
		super.driveUsingDF(25);
		
		super.rotateUsingRT(-90 * this.startingOnLeft);
		
		super.driveUsingDF(-24);
		
		this.sAction.setShooter(RobotMap.kShooterLeftSetpointNuLow-250, RobotMap.kShooterRightSetpointNuLow-250);
		this.sAction.setJustIntakeIn(-0.2);
		super.rotateUsingRT(75 * this.startingOnLeft);
		this.sAction.setJustIntakeIn(0);
		
		super.driveUsingDF(3.25);
		this.sAction.setOuttakePercentFront(1);
		this.waitTime(0.25);
		this.sAction.setOuttakePercentFront(0);
		this.sAction.setShooterPercentFront(0);
		
		super.driveUsingDF(-4);
		super.rotateUsingRTAbsolute(-(75+55) * this.startingOnLeft);
		
		this.sAction.setIntakeIn(1);
		super.driveUsingDF(4);
		
		if(this.scaleLeft == this.switchLeft) {
			this.sAction.setTilterPosNu(RobotMap.kTiltPosVault);
			super.waitTime(0.5);
			this.sAction.setOuttakePercentFront(1);
			this.sAction.setShooterPercentFront(0.55);
		}
	}
	
}