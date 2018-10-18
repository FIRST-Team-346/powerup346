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

public class GoodScaleTwice extends AutoPlan {

	Gyro sGyro = Gyro.getInstance();
	ActionRunner sAction = new ActionRunner();
	
	private double startingOnLeft, switchLeft, scaleLeft;
	
	public String getGoal() {
		return "good scale twice" + this.startingOnLeft;
	}
	
	public void run(double _startingLeft, double _switchLeft, double _scaleLeft) {
		this.startingOnLeft = _startingLeft;
		this.switchLeft = _switchLeft;
		this.scaleLeft = _scaleLeft;
		
		this.left();
	}
	
	public void left() {
		this.sAction.setTilterPosNu(RobotMap.kTiltPosScaleHigh);
		
		super.driveUsingDF(28.75);
		
		this.sAction.setShooter(RobotMap.kShooterLeftSetpointNuAutoMidHigh-200, RobotMap.kShooterRightSetpointNuAutoMidHigh-200);
		super.rotateUsingRT(45 * this.startingOnLeft);
		
		this.sAction.setOuttakePercentFront(1);
		super.waitTime(0.5);
		this.sAction.setShooter(0, 0);
		this.sAction.setOuttakePercentFront(0);
		
		this.sAction.startOpenIntake();
		
		this.rotateUsingRT((90-45 +52) * this.startingOnLeft);
		
		this.sAction.stopOpenIntake();
		this.sAction.startIntakeCube();
		super.driveUsingDF(7);
//		super.waitTime(0.1);

		super.driveUsingDF(-7.5);
		this.sAction.stopIntakeCube();
		
		this.sAction.setTilterPosNu(RobotMap.kTiltPosScaleHigh);
		this.sAction.setShooter(RobotMap.kShooterLeftSetpointNuAutoMidHigh-100, RobotMap.kShooterRightSetpointNuAutoMidHigh-100);
		super.rotateUsingRT(-(90-52 +45) * this.startingOnLeft);
		
		this.sAction.setOuttakePercentFront(1);
	}
	
//	public void right() {
//		this.sAction.setTilterPosNu(RobotMap.kTiltPosScaleHigh);
//		
//		super.driveUsingDF(28.5);
//		
//		this.sAction.setShooter(RobotMap.kShooterLeftSetpointNuAutoMid+250, RobotMap.kShooterRightSetpointNuAutoMid+250);
//		super.rotateUsingRT(50 * this.scaleLeft);
//		
//		super.waitTime(0.35);
//		this.sAction.setOuttakePercentFront(1);
//		super.waitTime(0.25);
//		this.sAction.setShooter(0, 0);
//		this.sAction.setOuttakePercentFront(0);
//		
//		this.sAction.startOpenIntake();
//		
//		this.rotateUsingRT((90-50+50) * this.scaleLeft);
//		
//		this.sAction.stopOpenIntake();
//		this.sAction.startIntakeCube();
//		super.driveUsingDF(8);
//		this.sAction.stopIntakeCube();
//
//		
//		this.sAction.setTilterPosNu(RobotMap.kTiltPosScaleLow);
//		this.sAction.setShooter(RobotMap.kShooterLeftSetpointNuAutoMid, RobotMap.kShooterRightSetpointNuAutoMid);
//		super.driveUsingDF(-6.5);
//		super.rotateUsingRT(-(90-50+50) * this.startingOnLeft);
//		
//		this.sAction.setOuttakePercentFront(1);
//	}
	
}