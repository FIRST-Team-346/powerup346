package org.usfirst.frc.team346.auto.plans;

import org.usfirst.frc.team346.auto.AutoPlan;
import org.usfirst.frc.team346.auto.actions.ActionRunner;
import org.usfirst.frc.team346.robot.RobotMap;
import org.usfirst.frc.team346.subsystems.Gyro;

public class CrossConduit extends AutoPlan {

	Gyro sGyro = Gyro.getInstance();
	ActionRunner sAction = new ActionRunner();
	
	private double startingOnLeft, switchLeft, scaleLeft;
	
	public String getGoal() {
		return "cross conduit" + this.startingOnLeft;
	}
	
	public void run(double _startingLeft, double _switchLeft, double _scaleLeft) {
		this.startingOnLeft = _startingLeft;
		this.switchLeft = _switchLeft;
		this.scaleLeft = _scaleLeft;
		
		this.start();
	}
	
	public void start() {
		this.sAction.setTilterPosNu(RobotMap.kTiltPosScaleHigh+25);
		
		super.driveUsingDF(25.25);
		
		super.rotateUsingRT(-90 * this.startingOnLeft);
		
		super.driveUsingDF(-12);
		
		this.sAction.startOpenIntake();
		super.waitTime(2);
		this.sAction.stopOpenIntake();
	}
	
}