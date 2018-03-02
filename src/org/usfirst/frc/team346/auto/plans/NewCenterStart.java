package org.usfirst.frc.team346.auto.plans;

import org.usfirst.frc.team346.auto.actions.ActionRunner;
import org.usfirst.frc.team346.auto.actions.Rotate;
import org.usfirst.frc.team346.auto.actions.RotateSingleSide;
import org.usfirst.frc.team346.robot.Robot;
import org.usfirst.frc.team346.robot.RobotMap;
import org.usfirst.frc.team346.subsystems.Gyro;

import edu.wpi.first.wpilibj.GenericHID.Hand;

public class NewCenterStart extends AutoPlan {

	Gyro sGyro = Gyro.getInstance();
	ActionRunner sAction = new ActionRunner();
	Rotate sRotator = new Rotate();
	RotateSingleSide sRotatorSS = new RotateSingleSide();
	
	public String getGoal() {
		return "shoot forward to scale";
	}
	
	public void run(double _switchLeft, double _scaleLeft) {
		this.sGyro.calibrate();
		
		if(_switchLeft == 1) {
			this.leftSwitch();
		}
		else {
			this.rightSwitch();
		}
	}
	
	public void leftSwitch() {
		this.sRotatorSS.setSingleSide(Hand.kLeft);
		this.sRotatorSS.rotate(-45, 0.5, 3, 1.5);
		
		this.driveUsingDF(-9);
		
		this.sRotatorSS.setSingleSide(Hand.kRight);
		this.sRotatorSS.rotate(45, 0.5, 3, 1.5);
		
		this.driveUsingDF(-2);
		
//		this.sAction.shootToSwitchBack();
			super.waitTime(1);
		
		this.driveUsingDF(2.5);
		
		this.sRotator.rotate(90, 0.5, 3, 1.5);
	}
	
	public void rightSwitch() {
		this.sRotatorSS.setSingleSide(Hand.kRight);
		this.sRotatorSS.rotate(45, 0.5, 3, 1.5);
		
		this.driveUsingDF(-8.5);
		
		this.sRotatorSS.setSingleSide(Hand.kLeft);
		this.sRotatorSS.rotate(-45, 0.5, 3, 1.5);
		
		this.driveUsingDF(-2);
		
//		this.sAction.shootToSwitchBack();
			super.waitTime(1);
		
		this.driveUsingDF(2.5);
		
		this.sRotator.rotate(-90, 0.5, 3, 1.5);
	}
}