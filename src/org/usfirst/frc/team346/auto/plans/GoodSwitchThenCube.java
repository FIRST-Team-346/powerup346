package org.usfirst.frc.team346.auto.plans;

import org.usfirst.frc.team346.auto.AutoPlan;
import org.usfirst.frc.team346.auto.actions.ActionRunner;
import org.usfirst.frc.team346.robot.RobotMap;
import org.usfirst.frc.team346.subsystems.Gyro;

import edu.wpi.first.wpilibj.GenericHID.Hand;

public class GoodSwitchThenCube extends AutoPlan {

	Gyro sGyro = Gyro.getInstance();
	ActionRunner sAction = new ActionRunner();
	
	private double startingOnLeft, switchLeft, scaleLeft;
	
	public String getGoal() {
		return "good switch then cube" + this.startingOnLeft;
	}
	
	public void run(double _startingLeft, double _switchLeft, double _scaleLeft) {
		this.startingOnLeft = _startingLeft;
		this.switchLeft = _switchLeft;
		this.scaleLeft = _scaleLeft;
		
		this.start();
	}
	
	public void start() {
		this.sAction.setTilterPosNu(RobotMap.kTiltPosVault+20);
		super.driveUsingDF(16.5);
		
		super.rotateUsingRT(90 * this.startingOnLeft);
		
		super.driveUsingDF(4);
		this.sAction.setOuttakePercentFront(1);
		this.sAction.setShooterPercentFront(0.32);
		super.waitTime(0.3);
		this.sAction.setOuttakePercentFront(0);
		this.sAction.setShooterPercentFront(0);
		this.sAction.setTilterPosNu(RobotMap.kTiltPosScaleMid);
		
		if(this.startingOnLeft == 1) {
			super.rotateUsingRT(Hand.kRight, 90);
		}
		else {
			super.rotateUsingRT(Hand.kLeft, -90);
		}
		
		this.sAction.startOpenIntake();
		super.driveUsingDF(-12);
		this.sAction.stopOpenIntake();
		
		super.rotateUsingRT(-37 * this.startingOnLeft);
		
		this.sAction.startIntakeCube();
		super.driveUsingDF(7);
		super.waitTime(0.2);
		super.driveUsingDF(-1);
	}
}