package org.usfirst.frc.team346.auto.plans;

import org.usfirst.frc.team346.auto.AutoPlan;
import org.usfirst.frc.team346.auto.actions.ActionRunner;
import org.usfirst.frc.team346.auto.actions.Rotate;
import org.usfirst.frc.team346.robot.RobotMap;
import org.usfirst.frc.team346.subsystems.Gyro;
import org.usfirst.frc.team346.subsystems.Lights;

import edu.wpi.first.wpilibj.GenericHID.Hand;

public class SwitchPBadSwitchTwice extends AutoPlan {

	Gyro sGyro = Gyro.getInstance();
	ActionRunner sAction = new ActionRunner();
	Rotate sRotator = new Rotate();
	
	private double startingOnLeft, switchLeft, scaleLeft;
	
	public String getGoal() {
		return "cross conduit to take control of bad switch: " + this.startingOnLeft;
	}
	
	public void run(double _startingLeft, double _switchLeft, double _scaleLeft) {
		this.startingOnLeft = _startingLeft;
		this.switchLeft = _switchLeft;
		this.scaleLeft = _scaleLeft;
		
//		Lights.getInstance().setGreen();
		this.badSwitchMaybeBadScale();
	}
	
	public void badSwitchMaybeBadScale() {
		this.sAction.setTilterPosNu(RobotMap.kTiltPosScaleLow);
		
		super.driveUsingDF(24);
		
		super.rotateUsingRT(-90 * this.startingOnLeft);
		
		super.driveUsingDF(-21);
		
		super.rotateUsingRT(-90 * this.startingOnLeft);
		
		this.sAction.setTilterPosNu(RobotMap.kTiltPosVault);
		super.driveUsingDF(2);
		this.sAction.setOuttakePercentFront(1);
		this.sAction.setShooterPercentFront(0.35);
		super.waitTime(0.3);
		
		this.sAction.setJustIntakeIn(-1);
		super.driveUsingDF(-3);
		
		this.sAction.setIntakeIn(1);
		super.waitTime(0.2);
		super.driveUsingDF(3.5);
		
		this.sAction.setTilterPosNu(RobotMap.kTiltPosVault);
		super.waitTime(0.3);
		this.sAction.setOuttakePercentFront(1);
		this.sAction.setShooterPercentFront(0.35);
	}
	
}