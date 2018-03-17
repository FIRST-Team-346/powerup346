package org.usfirst.frc.team346.auto.plans;

import org.usfirst.frc.team346.auto.AutoPlan;
import org.usfirst.frc.team346.auto.actions.ActionRunner;
import org.usfirst.frc.team346.auto.actions.Rotate;
import org.usfirst.frc.team346.robot.RobotMap;
import org.usfirst.frc.team346.subsystems.Gyro;
import org.usfirst.frc.team346.subsystems.Lights;

import edu.wpi.first.wpilibj.GenericHID.Hand;

public class ScalePBadScaleMaybeBadSwitch extends AutoPlan {

	Gyro sGyro = Gyro.getInstance();
	ActionRunner sAction = new ActionRunner();
	Rotate sRotator = new Rotate();
	
	private double startingOnLeft, switchLeft, scaleLeft;
	
	public String getGoal() {
		return "cross conduit, shoot bad scale, intake, maybe bad switch: " + this.startingOnLeft;
	}
	
	public void run(double _startingLeft, double _switchLeft, double _scaleLeft) {
		this.startingOnLeft = _startingLeft;
		this.switchLeft = _switchLeft;
		this.scaleLeft = _scaleLeft;
		
//		Lights.getInstance().setGreen();
		this.badScaleMaybeBadSwitch();
	}
	
	public void badScaleMaybeBadSwitch() {
//		this.sAction.setTilterPosNu(RobotMap.kTiltPosScaleLow);
		
		super.driveUsingDF(25.5);
		
//		this.sAction.setJustIntakeIn(-1);
		super.rotateUsingRTAbsolute(-90 * this.startingOnLeft);
//		this.sAction.setJustIntakeIn(0);
		
		super.driveUsingDF(-25);
		
//		this.sAction.setShooter(RobotMap.kShooterLeftSetpointNuLow, RobotMap.kShooterRightSetpointNuLow);
		super.rotateUsingRTAbsolute(85 * this.startingOnLeft);
		
		super.driveUsingDF(4);
		this.waitTime(0.1);
//		this.sAction.setOuttakePercentFront(1);
		
		super.driveUsingDF(-4);
		super.rotateUsingRTAbsolute(-(85+55) * this.startingOnLeft);
		
//		this.sAction.setIntakeIn(1);
		super.driveUsingDF(4);
		
		if(this.scaleLeft == this.switchLeft) {
//			this.sAction.setTilterPosNu(RobotMap.kTiltPosVault);
			super.waitTime(0.5);
//			this.sAction.setOuttakePercentFront(1);
//			this.sAction.setShooterPercentFront(0.35);
		}
	}
	
}