package org.usfirst.frc.team346.auto.plans;

import org.usfirst.frc.team346.auto.AutoPlan;
import org.usfirst.frc.team346.auto.actions.ActionRunner;
import org.usfirst.frc.team346.auto.actions.Rotate;
import org.usfirst.frc.team346.robot.RobotMap;
import org.usfirst.frc.team346.subsystems.Gyro;
import org.usfirst.frc.team346.subsystems.Lights;

import edu.wpi.first.wpilibj.GenericHID.Hand;

public class SwitchPGoodSwitchThenCross extends AutoPlan {

	Gyro sGyro = Gyro.getInstance();
	ActionRunner sAction = new ActionRunner();
	Rotate sRotator = new Rotate();
	
	private double startingOnLeft, switchLeft, scaleLeft;
	
	public String getGoal() {
		return "good switch, then cross conduit, " + this.startingOnLeft;
	}
	
	public void run(double _startingLeft, double _switchLeft, double _scaleLeft) {
		this.startingOnLeft = _startingLeft;
		this.switchLeft = _switchLeft;
		this.scaleLeft = _scaleLeft;
		
//		Lights.getInstance().setGreen();
		this.goodSwitchThenCross();
	}
	
	public void goodSwitchThenCross() {
//		this.sAction.setTilterPosNu(RobotMap.kTiltPosScaleLow);
//		this.sAction.setJustIntakeIn(-1);
		super.driveUsingDF(16.5);
		
//		this.sAction.setJustIntakeIn(0);
//		this.sAction.setTilterPosNu(RobotMap.kTiltPosSwitchBack);
		super.rotateUsingRTAbsolute(-90 * this.startingOnLeft);
		
		this.driveUsingDF(-3);
		
//		this.sAction.setOuttakePercentFront(-1);
		
		if(this.startingOnLeft == 1) {
			super.rotateUsingRTAbsolute(Hand.kLeft, 90);
		}
		else {
			super.rotateUsingRTAbsolute(Hand.kRight, -90);
		}
//		this.sAction.setOuttakePercentFront(0);
		
		super.driveUsingDF(8);
		
		super.rotateUsingRTAbsolute(-90 * this.startingOnLeft);
		
		super.driveUsingDF(-21.5);
		
		super.rotateUsingRTAbsolute(-90 * this.startingOnLeft);
		
//		this.sAction.setIntakeIn(1);
		super.driveUsingDF(4);
	}
}