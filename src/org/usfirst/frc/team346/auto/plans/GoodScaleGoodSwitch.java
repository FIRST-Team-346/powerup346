package org.usfirst.frc.team346.auto.plans;

import org.usfirst.frc.team346.auto.AutoPlan;
import org.usfirst.frc.team346.auto.actions.ActionRunner;
import org.usfirst.frc.team346.auto.actions.Rotate;
import org.usfirst.frc.team346.robot.Robot;
import org.usfirst.frc.team346.robot.RobotMap;
import org.usfirst.frc.team346.subsystems.Gyro;
import org.usfirst.frc.team346.subsystems.Lights;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.GenericHID.Hand;

public class GoodScaleGoodSwitch extends AutoPlan {

	Gyro sGyro = Gyro.getInstance();
	ActionRunner sAction = new ActionRunner();
	Rotate sRotator = new Rotate();
	
	private double startingOnLeft, switchLeft, scaleLeft;
	
	public String getGoal() {
		return "good scale" + this.startingOnLeft;
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
		
		super.driveUsingDF(28.5);
		
		this.sAction.setShooter(RobotMap.kShooterLeftSetpointNuAutoMid+250, RobotMap.kShooterRightSetpointNuAutoMid+250);
		super.rotateUsingRT(50 * this.scaleLeft);
		
		super.waitTime(0.35);
		this.sAction.setOuttakePercentFront(1);
		super.waitTime(0.25);
		this.sAction.setShooter(0, 0);
		this.sAction.setOuttakePercentFront(0);
		
		this.sAction.setJustIntakeIn(-1);
		
		this.rotateUsingRT((90-50+50) * this.scaleLeft);
		
		this.sAction.setIntakeIn(1);
		super.driveUsingDF(8);
		this.sAction.setIntakeIn(0);

		
		this.sAction.setTilterPosNu(RobotMap.kTiltPosVault+20);
		super.waitTime(0.75);
		this.sAction.setOuttakePercentFront(1);
		this.sAction.setShooterPercentFront(0.4);
	}
	
	public void right() {
		this.sAction.setTilterPosNu(RobotMap.kTiltPosScaleHigh);
		
		super.driveUsingDF(28.5);
		
		this.sAction.setShooter(RobotMap.kShooterLeftSetpointNuAutoMid+250, RobotMap.kShooterRightSetpointNuAutoMid+250);
		super.rotateUsingRT(50 * this.scaleLeft);
		
		super.waitTime(0.35);
		this.sAction.setOuttakePercentFront(1);
		super.waitTime(0.25);
		this.sAction.setShooter(0, 0);
		this.sAction.setOuttakePercentFront(0);
		
		this.sAction.setJustIntakeIn(-1);
		
		this.rotateUsingRT((90-50+50) * this.scaleLeft);
		
		this.sAction.setIntakeIn(1);
		super.driveUsingDF(8);
		this.sAction.setIntakeIn(0);

		
		this.sAction.setTilterPosNu(RobotMap.kTiltPosVault+20);
		super.waitTime(0.75);
		this.sAction.setOuttakePercentFront(1);
		this.sAction.setShooterPercentFront(0.4);
	}
	
}