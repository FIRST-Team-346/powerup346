package org.usfirst.frc.team346.auto.plans;

import org.usfirst.frc.team346.auto.actions.ActionRunner;
import org.usfirst.frc.team346.auto.actions.Rotate;
import org.usfirst.frc.team346.robot.Robot;
import org.usfirst.frc.team346.subsystems.Gyro;

public class NewSideScaleShoot extends AutoPlan {

	Gyro gyro;
	ActionRunner action;
	Rotate rotator;
	
	public String getGoal() {
		return "shoot forward to scale";
	}
	
	public void run(Robot _robot, String _layout) {
		this.action = new ActionRunner();
		this.gyro = Gyro.getInstance();
		this.rotator = new Rotate();
		
		double side;
		side = (_layout.charAt(0) == 'L') ? 1. : -1.;
		
		this.gyro.calibrate();
		super.driveUsingDF(28);
		this.rotator.rotate(30 * side, 0.5, 5, 1.5);
	}
	
}