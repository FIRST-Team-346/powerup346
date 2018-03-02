package org.usfirst.frc.team346.auto.plans;

import org.usfirst.frc.team346.auto.actions.ActionRunner;
import org.usfirst.frc.team346.auto.actions.Rotate;
import org.usfirst.frc.team346.auto.actions.RotateSingleSide;
import org.usfirst.frc.team346.robot.Robot;
import org.usfirst.frc.team346.robot.RobotMap;
import org.usfirst.frc.team346.subsystems.Gyro;

import edu.wpi.first.wpilibj.GenericHID.Hand;

public class NewSideStart extends AutoPlan {

	Gyro sGyro = Gyro.getInstance();
	ActionRunner sAction = new ActionRunner();
	Rotate sRotator = new Rotate();
	RotateSingleSide sRotatorSS = new RotateSingleSide();
	
	private double switchLeft, scaleLeft;
	
	public String getGoal() {
		return "shoot forward to scale";
	}
	
	public void run(double _switchLeft, double _scaleLeft) {
		this.switchLeft = _switchLeft;
		this.scaleLeft = _scaleLeft;
		this.sGyro.calibrate();
		
		if(RobotMap.kStartingOnLeft == _scaleLeft) {
			this.goodScale();
		}
		else if(RobotMap.kStartingOnLeft == _switchLeft && RobotMap.kStartingOnLeft != _scaleLeft) {
			this.goodSwitchBadScale();
		}
		else {
			this.badSwitchBadScale();
		}
	}
	
	public void goodScale() {
		
		
		super.driveUsingDF(28);
		
		this.sRotator.rotate(45 * this.scaleLeft, 0.5, 5, 1.5);
		
//		this.sAction.shootToScaleFront();
//		this.sAction.openIntake();
			super.waitTime(2);
		
		this.sRotator.rotate((45+60) * this.scaleLeft, 0.5, 5, 1.5);
		
		super.driveUsingDF(6);
		
//		this.sAction.setIntakeIn(1);
		super.waitTime(1);
//		this.sAction.setIntakeIn(0);
		
		if(this.switchLeft == this.scaleLeft) {
//			this.sAction.shootToSwitchFront();
				super.waitTime(0.5);
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
		
		this.sRotator.rotate(-90 * this.switchLeft, 0.5, 5, 1.5);
		
		this.driveUsingDF(-2);
		
		//shoot during the small drive ^
//		this.sAction.shootToSwitchBack();
		
		if(this.switchLeft == 1) {
			this.sRotatorSS.setSingleSide(Hand.kLeft);
			this.sRotatorSS.rotate(90, 0.5, 5, 1.5);
		}
		else {
			this.sRotatorSS.setSingleSide(Hand.kRight);
			this.sRotatorSS.rotate(-90, 0.5, 5, 1.5);
		}
		
		super.driveUsingDF(9);
		
		this.sRotator.rotate((90+45) * this.switchLeft, 0.5, 5, 1.5);
		
		super.driveUsingDF(3);
		
//		this.sAction.setIntakeIn(1);
		super.waitTime(0.25);
//		this.sAction.setIntakeIn(0);
		
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
		
//		this.sAction.shootToScaleBack();
			super.waitTime(2);
			
		this.sRotator.rotate(-180 * RobotMap.kStartingOnLeft, 0.5, 3, 2);

		
//		super.driveUsingDF(-6);
	}
	
}