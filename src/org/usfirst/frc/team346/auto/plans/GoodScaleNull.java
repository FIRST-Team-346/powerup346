package org.usfirst.frc.team346.auto.plans;

import org.usfirst.frc.team346.auto.AutoPlan;
import org.usfirst.frc.team346.auto.actions.ActionRunner;
import org.usfirst.frc.team346.auto.actions.Rotate;
import org.usfirst.frc.team346.robot.Robot;
import org.usfirst.frc.team346.robot.RobotMap;
import org.usfirst.frc.team346.subsystems.Gyro;
import org.usfirst.frc.team346.subsystems.Lights;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.GenericHID.Hand;

public class GoodScaleNull extends AutoPlan {

	Gyro sGyro = Gyro.getInstance();
	ActionRunner sAction = new ActionRunner();
	Rotate sRotator = new Rotate();
	
	private double startingOnLeft, switchLeft, scaleLeft;
	
	public String getGoal() {
		return "good scale null" + this.startingOnLeft;
	}
	
	public void run(double _startingLeft, double _switchLeft, double _scaleLeft) {
		this.startingOnLeft = _startingLeft;
		this.switchLeft = _switchLeft;
		this.scaleLeft = _scaleLeft;
		
		this.run();
	}
	
	public void run() {
		this.sAction.setTilterPosNu(RobotMap.kTiltPosScaleHigh);
		
		super.driveUsingDF(34.5);
		
		this.sAction.setShooter(RobotMap.kShooterLeftSetpointNuAutoMidHigh, RobotMap.kShooterRightSetpointNuAutoMidHigh);
		super.rotateUsingRT(90 * this.startingOnLeft, 0.7);
		
		super.driveUsingDF(-3.5);
		super.waitTime(0.5);
		this.sAction.setOuttakePercentFront(1);
		
		super.waitTime(1);
		this.sAction.startOpenIntake();
		super.waitTime(1);
		this.sAction.stopOpenIntake();
	}
}