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

public class ScalePGoodScaleTwice extends AutoPlan {

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
		this.goodScaleThenGoodScale();
	}
	
	public void goodScaleThenGoodScale() {
//		this.sAction.setTilterPosNu(RobotMap.kTiltPosScaleHigh);
		
		super.driveUsingDF(28);
		
//		this.sAction.setShooter(RobotMap.kShooterLeftSetpointNuLow, RobotMap.kShooterRightSetpointNuLow);
		super.rotateUsingRTAbsolute(45 * this.scaleLeft);
		
		super.waitTime(0.1);
//		this.sAction.setOuttakePercentFront(1);
//		this.sAction.setShooter(0, 0);
//		this.sAction.setOuttakePercentFront(0);
		
//		this.sAction.setJustIntakeIn(-1);
		
		this.rotateUsingRTAbsolute((45+65) * this.scaleLeft);
		
//		this.sAction.setIntakeIn(1);
		super.driveUsingDF(6.5);
//		this.sAction.setIntakeIn(0);

		
//		this.sAction.setTilterPosNu(RobotMap.kTiltPosScaleLow);
//		this.sAction.setShooter(RobotMap.kShooterLeftSetpointNuLow, RobotMap.kShooterRightSetpointNuLow);
		super.driveUsingDF(-6);
		super.rotateUsingRTAbsolute(-(65+45) * this.startingOnLeft);
		
		super.waitTime(0.1);
//		this.sAction.setOuttakePercentFront(1);
//		this.sAction.setShooter(0, 0);
//		this.sAction.setOuttakePercentFront(0);
	}
	
}