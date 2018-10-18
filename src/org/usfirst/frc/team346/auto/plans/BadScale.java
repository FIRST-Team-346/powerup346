package org.usfirst.frc.team346.auto.plans;

import org.usfirst.frc.team346.auto.AutoPlan;
import org.usfirst.frc.team346.auto.actions.ActionRunner;
import org.usfirst.frc.team346.robot.RobotMap;
import org.usfirst.frc.team346.subsystems.Gyro;

public class BadScale extends AutoPlan {

	Gyro sGyro = Gyro.getInstance();
	ActionRunner sAction = new ActionRunner();
	
	private double startingOnLeft, switchLeft, scaleLeft;
	
	public String getGoal() {
		return "bad scale" + this.startingOnLeft;
	}
	
	public void run(double _startingLeft, double _switchLeft, double _scaleLeft) {
		this.startingOnLeft = _startingLeft;
		this.switchLeft = _switchLeft;
		this.scaleLeft = _scaleLeft;
		
//		Lights.getInstance().setGreen();
//		if(this.startingOnLeft == 1) {
//			this.left();
//		}
//		else {
//			this.right();
//		}
		this.left();
	}
	
	public void left() {
		this.sAction.setTilterPosNu(RobotMap.kTiltPosScaleHigh+25);
		
		super.driveUsingDF(25.25);
		
		super.rotateUsingRT(-90 * this.startingOnLeft);
		
		super.driveUsingDF(-24.5);//was -24
		
		this.sAction.setShooter(RobotMap.kShooterLeftSetpointNuAutoMidHigh-300, RobotMap.kShooterRightSetpointNuAutoMidHigh-300);
		this.sAction.startOpenIntake();
		super.rotateUsingRT(80 * this.startingOnLeft);//75
		this.sAction.stopOpenIntake();
		
		super.driveUsingDF(3.5);
		this.sAction.setOuttakePercentFront(1);
	}
	
//	public void right() {
//		this.sAction.setTilterPosNu(RobotMap.kTiltPosScaleHigh);
//		
//		super.driveUsingDF(25);
//		
//		super.rotateUsingRT(-90 * this.startingOnLeft);
//		
//		super.driveUsingDF(-24);
//		
//		this.sAction.setShooter(RobotMap.kShooterLeftSetpointNuAutoMid-300, RobotMap.kShooterRightSetpointNuAutoMid-300);
//		this.sAction.startOpenIntake();
//		super.rotateUsingRT(75 * this.startingOnLeft);
//		this.sAction.stopOpenIntake();
//		
//		super.driveUsingDF(3);
//		this.sAction.setOuttakePercentFront(1);
//		this.waitTime(0.25);
//		this.sAction.setOuttakePercentFront(0);
//		this.sAction.setShooterPercentFront(0);
//		
//		super.driveUsingDF(-4);
//		super.rotateUsingRTAbsolute(-(75+55) * this.startingOnLeft);
//		
//		this.sAction.startIntakeCube();
//		super.driveUsingDF(4);
//	}
	
}