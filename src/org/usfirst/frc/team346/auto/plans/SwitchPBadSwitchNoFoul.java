package org.usfirst.frc.team346.auto.plans;

import org.usfirst.frc.team346.auto.AutoPlan;
import org.usfirst.frc.team346.auto.actions.ActionRunner;
import org.usfirst.frc.team346.auto.actions.Rotate;
import org.usfirst.frc.team346.robot.RobotMap;
import org.usfirst.frc.team346.subsystems.Gyro;
import org.usfirst.frc.team346.subsystems.Lights;

import edu.wpi.first.wpilibj.GenericHID.Hand;

public class SwitchPBadSwitchNoFoul extends AutoPlan {

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
		this.badSwitchNoFoul();
	}
	
	public void badSwitchNoFoul() {
		this.sAction.setTilterPosNu(RobotMap.kTiltPosScaleLow);
		
		super.driveUsingDF(24);
		
		super.rotateUsingRT(90 * this.startingOnLeft);
		
		super.driveUsingDF(25);
		
		this.sAction.setJustIntakeIn(-1);
		super.rotateUsingRT(-45 * this.startingOnLeft);
		this.sAction.setJustIntakeIn(0);
		
		this.sAction.setTilterPosNu(RobotMap.kTiltPosSwitchBack);
		super.driveUsingDF(-4);
		
		super.waitTime(0.3);
		this.sAction.setOuttakePercentFront(-1);
	}
	
}