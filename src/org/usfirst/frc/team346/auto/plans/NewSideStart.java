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
	
	private double switchLeft, scaleLeft;
	
	public String getGoal() {
		return "shoot forward to scale";
	}
	
	public void run(double _switchLeft, double _scaleLeft) {
		this.switchLeft = _switchLeft;
		this.scaleLeft = _scaleLeft;
		
		if(RobotMap.kStartingOnLeft == _scaleLeft) {
			Lights.getInstance().setGreen();
			this.goodScale();
		}
		else if(RobotMap.kStartingOnLeft == _switchLeft && RobotMap.kStartingOnLeft != _scaleLeft) {
			Lights.getInstance().setLeftGreenRightRed(false);
			this.goodSwitchBadScale();
		}
		else {
			Lights.getInstance().setLeftGreenRightRed(true);
			this.badSwitchBadScale();
		}
	}
	
	public void goodScale() {
		this.sAction.setTilterPosNu(RobotMap.kTiltPosScaleLow);
		
		super.driveUsingDF(28);
		
		this.sAction.setShooter(RobotMap.kShooterLeftSetpointNuMid, RobotMap.kShooterRightSetpointNuMid);
		this.sRotator.rotate(45 * this.scaleLeft, 0.5, 3, 2.);
		
		this.sAction.setOuttakePercentFront(1);
			super.waitTime(1);
		this.sAction.setShooter(0, 0);
		this.sAction.setOuttakePercentFront(0);
		
		this.sAction.openIntake();
		
		this.sRotator.rotate((45+60) * this.scaleLeft, 0.5, 3, 1.5);
		
		this.sAction.setIntakeIn(1);
		super.driveUsingDF(6);
		this.sAction.setIntakeIn(0);
		
		if(this.switchLeft == this.scaleLeft) {
			this.sAction.setTilterPosNu(RobotMap.kTiltPosVault);
			super.waitTime(0.25);
			this.sAction.shootToSwitchFront();
//				super.waitTime(0.5);
			super.driveUsingDF(-6);
			this.sRotator.rotate(-(45+60+45) * this.scaleLeft, 0.5, 5, 1.5);
		}
		else {
			super.driveUsingDF(-6);
			this.sRotator.rotate(-(45+60) * this.scaleLeft, 0.5, 5, 1.5);
		}
	}
	
	public void goodSwitchBadScale() {
		super.driveUsingDF(16.5);
		
		this.sAction.openIntake();
		this.sRotator.rotate(-90 * this.switchLeft, 0.5, 5, 1.5);
		this.sAction.setTilterPosNu(RobotMap.kTiltPosSwitchBack);
		
		this.driveUsingDF(-2);
		
		//shoot during the small drive ^
		this.sAction.shootToSwitchBack();
		
		if(this.switchLeft == 1) {
			this.sRotator.rotateSingleSide(Hand.kLeft, 90, 0.5, 5, 1.5);
		}
		else {
			this.sRotator.rotateSingleSide(Hand.kRight, -90, 0.5, 5, 1.5);
		}
		
		super.driveUsingDF(9);
		
		this.sRotator.rotate((90+45) * this.switchLeft, 0.5, 5, 1.5);
		
		this.sAction.setIntakeIn(1);
		super.driveUsingDF(3);
		
//		super.waitTime(0.25);
		this.sAction.setIntakeIn(0);
		
		super.driveUsingDF(-3.5);
		
		this.sRotator.rotate(-30 * this.switchLeft, 0.5, 5, 1.5);
	}
	
	public void badSwitchBadScale() {
		if(RobotMap.kStartingOnLeft == 1) {
			super.driveUsingDF(25.5);
		}
		else {
			super.driveUsingDF(26);
		}
		this.sRotator.rotate(90 * RobotMap.kStartingOnLeft, 0.5, 3, 2);
		System.out.println(this.sGyro.getAngle());
		
		if(RobotMap.kStartingOnLeft == 1) {
			super.driveUsingDF(12.5);
		}
		else {
			super.driveUsingDF(13);
		}
		//slows down to drive over the conduit without ramping 80ft in the air
		super.driveUsingDF(11);
		
		this.sRotator.rotate(-90 * RobotMap.kStartingOnLeft, 0.5, 3, 2);
		
		super.driveUsingDF(4.5);
		
		this.sAction.shootToScaleBack();
//			super.waitTime(2);
			
		this.sRotator.rotate(-180 * RobotMap.kStartingOnLeft, 0.5, 3, 2);

		super.driveUsingDF(-6);
	}
	
}