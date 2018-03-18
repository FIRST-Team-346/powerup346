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
		if(this.startingOnLeft == 1) {
			this.left();
		}
		else {
			this.right();
		}
	}
	
	public void left() {
		this.sAction.setTilterPosNu(RobotMap.kTiltPosScaleHigh);
		
		super.driveUsingDF(27.5);
		
		this.sAction.setShooter(RobotMap.kShooterLeftSetpointNuLow, RobotMap.kShooterRightSetpointNuLow);
		super.rotateUsingRT(45 * this.scaleLeft);
		
		this.sAction.setOuttakePercentFront(1);
		super.waitTime(0.15);
		this.sAction.setShooter(0, 0);
		this.sAction.setOuttakePercentFront(0);
		
		this.sAction.setJustIntakeIn(-0.2);
		
		this.rotateUsingRT((90-45+55) * this.scaleLeft);
		
		this.sAction.setIntakeIn(1);
		super.driveUsingDF(6.5);
		this.sAction.setIntakeIn(0);

		
		this.sAction.setTilterPosNu(RobotMap.kTiltPosScaleLow);
		this.sAction.setShooter(RobotMap.kShooterLeftSetpointNuLow+500, RobotMap.kShooterRightSetpointNuLow+500);
		super.driveUsingDF(-5.5);
		super.rotateUsingRT(-(55+90-45) * this.startingOnLeft);
		
		this.sAction.setOuttakePercentFront(1);
		super.waitTime(0.15);
		this.sAction.setShooter(0, 0);
		this.sAction.setOuttakePercentFront(0);
	}
	
	public void right() {
		this.sAction.setTilterPosNu(RobotMap.kTiltPosScaleHigh);
		
		super.driveUsingDF(29);
		
		this.sAction.setShooter(RobotMap.kShooterLeftSetpointNuLow, RobotMap.kShooterRightSetpointNuLow);
		super.rotateUsingRT(45 * this.scaleLeft);
		
		this.sAction.setOuttakePercentFront(1);
		super.waitTime(0.15);
		this.sAction.setShooter(0, 0);
		this.sAction.setOuttakePercentFront(0);
		
		this.sAction.setJustIntakeIn(-0.2);
		
		this.rotateUsingRT((90-45+55) * this.scaleLeft);
		
		this.sAction.setIntakeIn(1);
		super.driveUsingDF(6.5);
		this.sAction.setIntakeIn(0);

		
		this.sAction.setTilterPosNu(RobotMap.kTiltPosScaleLow);
		this.sAction.setShooter(RobotMap.kShooterLeftSetpointNuLow+500, RobotMap.kShooterRightSetpointNuLow+500);
		super.driveUsingDF(-6);
		super.rotateUsingRT(-(55+90-40) * this.startingOnLeft);
		
		this.sAction.setOuttakePercentFront(1);
		super.waitTime(0.15);
		this.sAction.setShooter(0, 0);
		this.sAction.setOuttakePercentFront(0);
	}
	
}