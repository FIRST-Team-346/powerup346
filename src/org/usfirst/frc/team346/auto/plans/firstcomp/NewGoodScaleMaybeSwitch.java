package org.usfirst.frc.team346.auto.plans.firstcomp;

import org.usfirst.frc.team346.auto.AutoPlan;
import org.usfirst.frc.team346.auto.actions.ActionRunner;
import org.usfirst.frc.team346.auto.actions.Rotate;
import org.usfirst.frc.team346.robot.Robot;
import org.usfirst.frc.team346.robot.RobotMap;
import org.usfirst.frc.team346.subsystems.Gyro;
import org.usfirst.frc.team346.subsystems.Lights;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.GenericHID.Hand;

public class NewGoodScaleMaybeSwitch extends AutoPlan {

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
		this.goodScaleMaybeSwitch();
	}
	
	public void goodScaleMaybeSwitch() {
//		this.sAction.setTilterPosNu(RobotMap.kTiltPosScaleHigh);
		
		super.driveUsingDF(28);
		
//		this.sAction.setShooter(RobotMap.kShooterLeftSetpointNuLow, RobotMap.kShooterRightSetpointNuLow);
		this.rotate(45 * this.scaleLeft, 2);
		
//		this.sAction.setOuttakePercentFront(1);
		super.waitTime(1);
//		this.sAction.setShooter(0, 0);
//		this.sAction.setOuttakePercentFront(0);
		
//		this.sAction.setJustIntakeIn(-1);
		
		this.rotate((65+45) * this.scaleLeft, 2);
		
//		this.sAction.setIntakeIn(1);
		super.driveUsingDF(6.5);
//		this.sAction.setIntakeIn(0);
		
		if(this.switchLeft == this.scaleLeft) {
//			this.sAction.setTilterPosNu(RobotMap.kTiltPosVault);
			super.waitTime(0.25);
//			this.sAction.shootToSwitchFront();
			super.waitTime(1);
			super.driveUsingDF(-6);
			this.rotate(-(45+60+45) * this.scaleLeft, 2);
		}
		else {
			super.driveUsingDF(-6);
			this.rotate(-(45+60) * this.scaleLeft, 2);
		}
	}
	
}