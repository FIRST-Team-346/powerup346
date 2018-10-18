package org.usfirst.frc.team346.auto.plans.firstcomp;

import org.usfirst.frc.team346.auto.AutoPlan;
import org.usfirst.frc.team346.auto.actions.ActionRunner;
import org.usfirst.frc.team346.auto.actions.Rotate;
import org.usfirst.frc.team346.robot.RobotMap;
import org.usfirst.frc.team346.subsystems.Gyro;
import org.usfirst.frc.team346.subsystems.Lights;

import edu.wpi.first.wpilibj.GenericHID.Hand;

public class NewGoodSwitchBadScale extends AutoPlan {

	Gyro sGyro = Gyro.getInstance();
	ActionRunner sAction = new ActionRunner();
	Rotate sRotator = new Rotate();
	
	private double startingOnLeft, switchLeft, scaleLeft;
	
	public String getGoal() {
		return "good switch, bad scale, " + this.startingOnLeft;
	}
	
	public void run(double _startingLeft, double _switchLeft, double _scaleLeft) {
		this.startingOnLeft = _startingLeft;
		this.switchLeft = _switchLeft;
		this.scaleLeft = _scaleLeft;
		
//		Lights.getInstance().setGreen();
		this.goodSwitchBadScale();
	}
	
	public void goodSwitchBadScale() {
//		this.sAction.setTilterPosNu(RobotMap.kTiltPosScaleLow);
		super.driveUsingDF(16.5);
		
//		this.sAction.setJustIntakeIn(-1);
		this.sRotator.rotate(-90 * this.switchLeft, 0.5, 5, 1.5);
//		this.sAction.setJustIntakeIn(0);
//		this.sAction.setTilterPosNu(RobotMap.kTiltPosSwitchBack);
		
		this.driveUsingDF(-3);
		
//		this.sAction.setOuttakePercentFront(-1);
		
		if(this.switchLeft == 1) {
			this.rotateSingleSide(Hand.kLeft, 90, 2);
		}
		else {
			this.rotateSingleSide(Hand.kRight, -90, 2);
		}
//		this.sAction.setOuttakePercentFront(0);
		
		super.driveUsingDF(9);
		
		this.rotate((90+45) * this.startingOnLeft, 2);
		
//		this.sAction.setIntakeIn(1);
		super.driveUsingDF(3);
		
//		this.sAction.setIntakeIn(0);
		
		super.driveUsingDF(-3.5);
		
		this.rotate(-30 * this.startingOnLeft, 2);
	}
}