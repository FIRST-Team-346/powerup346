package org.usfirst.frc.team346.auto.plans;

import org.usfirst.frc.team346.auto.actions.ActionRunner;
import org.usfirst.frc.team346.auto.actions.Rotate;
import org.usfirst.frc.team346.robot.Robot;
import org.usfirst.frc.team346.robot.RobotMap;
import org.usfirst.frc.team346.subsystems.Gyro;
import org.usfirst.frc.team346.subsystems.Lights;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.GenericHID.Hand;

public class BadSwitchBadScale extends AutoPlan {

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
		
//		Lights.getInstance().setGreen();
		this.badSwitchBadScale();
	}
	
	public void badSwitchBadScale() {
//		this.sAction.setTilterPosNu(RobotMap.kTiltPosScaleHigh);
		
		super.driveUsingDF(25.);
//		this.sAction.setJustIntakeIn(-1);
		this.rotate(-90 * this.startingOnLeft, 2);
//		this.sAction.setJustIntakeIn(0);
		
		if(this.startingOnLeft == 1) {
			super.driveUsingDF(-12.5);
		}
		else {
			super.driveUsingDF(-13);
		}
		//slows down to drive over the conduit without ramping 80ft in the air
		super.driveUsingDF(-11);
		
		this.rotate(90 * this.startingOnLeft, 2);
		
//		this.sAction.setShooter(RobotMap.kShooterLeftSetpointNuHigh, RobotMap.kShooterRightSetpointNuHigh);
//		super.driveUsingDF(4.5);
		super.waitTime(1.5);
		
//		this.sAction.setOuttakePercentFront(1);
		super.waitTime(1);
//		this.sAction.setOuttakePercentFront(0);
//		this.sAction.setShooterPercentFront(0);
		
//		this.rotate(180 * this.startingOnLeft, 2);
//		this.sAction.setJustIntakeIn(1);
//		this.sRotator.rotate(-180 * this.startingOnLeft, 0.5, 3, 2);
//
//		super.driveUsingDF(6);
	}
	
}