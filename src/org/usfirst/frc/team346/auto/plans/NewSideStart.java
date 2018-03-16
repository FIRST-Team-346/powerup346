package org.usfirst.frc.team346.auto.plans;

import org.usfirst.frc.team346.auto.actions.ActionRunner;
import org.usfirst.frc.team346.auto.actions.Rotate;
import org.usfirst.frc.team346.robot.Robot;
import org.usfirst.frc.team346.robot.RobotMap;
import org.usfirst.frc.team346.subsystems.Gyro;
import org.usfirst.frc.team346.subsystems.Lights;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.GenericHID.Hand;

public class NewSideStart extends AutoPlan {

	Gyro sGyro = Gyro.getInstance();
	ActionRunner sAction = new ActionRunner();
	Rotate sRotator = new Rotate();
	
	private double startingOnLeft, switchLeft, scaleLeft;
	
	public String getGoal() {
		return "side start, starting on left: " + this.startingOnLeft;
	}
	
	public void run(double _startingLeft, double _switchLeft, double _scaleLeft) {
		this.startingOnLeft = _startingLeft;
		this.switchLeft = _switchLeft;
		this.scaleLeft = _scaleLeft;
		
		if(this.startingOnLeft == _scaleLeft) {
			Lights.getInstance().setGreen();
			this.goodScale();
		}
		else if(this.startingOnLeft == _switchLeft && this.startingOnLeft != _scaleLeft) {
			Lights.getInstance().setYellow();
			this.goodSwitchBadScale();
		}
		else {
			Lights.getInstance().setRed();
			this.badSwitchBadScale();
		}
	}
	
	public void goodScale() {
		this.sAction.setTilterPosNu(RobotMap.kTiltPosScaleHigh);
		
		super.driveUsingDF(28);
		
		this.sAction.setShooter(RobotMap.kShooterLeftSetpointNuLow, RobotMap.kShooterRightSetpointNuLow);
		this.rotate(45 * this.scaleLeft, 2);
//		this.sRotator.rotate(45 * this.scaleLeft, 0.5, 3, 2.);
		
		this.sAction.setOuttakePercentFront(1);
			super.waitTime(1);
		this.sAction.setShooter(0, 0);
		this.sAction.setOuttakePercentFront(0);
		
		this.sAction.setJustIntakeIn(-1);
		
		this.rotate((45+45) * this.scaleLeft, 2);
//		this.sRotator.rotate((45+60) * this.scaleLeft, 0.5, 3, 1.5);
		
		this.sAction.setIntakeIn(1);
		super.driveUsingDF(7);
		this.sAction.setIntakeIn(0);
		
		if(this.switchLeft == this.scaleLeft) {
			this.sAction.setTilterPosNu(RobotMap.kTiltPosVault);
			super.waitTime(0.25);
			this.sAction.shootToSwitchFront();
//				super.waitTime(0.5);
			super.driveUsingDF(-6);
			this.rotate(-(45+60+45) * this.scaleLeft, 2);
//			this.sRotator.rotate(-(45+60+45) * this.scaleLeft, 0.5, 5, 1.5);
		}
		else {
			super.driveUsingDF(-6);
			this.rotate(-(45+60) * this.scaleLeft, 2);
//			this.sRotator.rotate(-(45+60) * this.scaleLeft, 0.5, 5, 1.5);
		}
	}
	
	public void goodSwitchBadScale() {
		this.sAction.setTilterPosNu(RobotMap.kTiltPosScaleLow);
		super.driveUsingDF(16.5);
		
		this.sAction.setJustIntakeIn(-1);
		this.sRotator.rotate(-90 * this.switchLeft, 0.5, 5, 1.5);
		this.sAction.setJustIntakeIn(0);
		this.sAction.setTilterPosNu(RobotMap.kTiltPosSwitchBack);
		
		this.driveUsingDF(-3);
		
		//shoot during the small drive ^
		this.sAction.setOuttakePercentFront(-1);
		
		if(this.switchLeft == 1) {
			this.rotateSingleSide(Hand.kLeft, 90, 2);
//			this.sRotator.rotateSingleSide(Hand.kLeft, 90, 0.5, 5, 1.5);
		}
		else {
			this.rotateSingleSide(Hand.kRight, -90, 2);
//			this.sRotator.rotateSingleSide(Hand.kRight, -90, 0.5, 5, 1.5);
		}
		this.sAction.setOuttakePercentFront(0);
		
		super.driveUsingDF(9);
		
		this.rotate((90+45) * this.startingOnLeft, 2);
//		this.sRotator.rotate((90+45) * this.switchLeft, 0.5, 5, 1.5);
		
		this.sAction.setIntakeIn(1);
		super.driveUsingDF(3);
		
//		super.waitTime(0.25);
		this.sAction.setIntakeIn(0);
		
		super.driveUsingDF(-3.5);
		
		this.rotate(-30 * this.startingOnLeft, 2);
//		this.sRotator.rotate(-30 * this.switchLeft, 0.5, 5, 1.5);
	}
	
	public void badSwitchBadScale() {
		this.sAction.setTilterPosNu(RobotMap.kTiltPosScaleHigh);
		
		super.driveUsingDF(25.);
		this.sAction.setJustIntakeIn(-1);
		this.rotate(-90 * this.startingOnLeft, 2);
//		this.sRotator.rotate(90 * this.startingOnLeft, 0.5, 3, 2);
		this.sAction.setJustIntakeIn(0);
		
		if(this.startingOnLeft == 1) {
			super.driveUsingDF(-12.5);
		}
		else {
			super.driveUsingDF(-13);
		}
		//slows down to drive over the conduit without ramping 80ft in the air
		super.driveUsingDF(-11);
		
		this.rotate(90 * this.startingOnLeft, 2);
//		this.sRotator.rotate(-90 * this.startingOnLeft, 0.5, 3, 2);
		
		this.sAction.setShooter(RobotMap.kShooterLeftSetpointNuHigh, RobotMap.kShooterRightSetpointNuHigh);
//		super.driveUsingDF(4.5);
		super.waitTime(1.5);
		
		this.sAction.setOuttakePercentFront(1);
		super.waitTime(1);
		this.sAction.setOuttakePercentFront(0);
		this.sAction.setShooterPercentFront(0);
			
//		this.rotate(180 * this.startingOnLeft, 2);
//		this.sAction.setJustIntakeIn(1);
//		this.sRotator.rotate(-180 * this.startingOnLeft, 0.5, 3, 2);
//
//		super.driveUsingDF(6);
	}
	
}