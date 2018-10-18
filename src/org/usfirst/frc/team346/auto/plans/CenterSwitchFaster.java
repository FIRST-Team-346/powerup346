package org.usfirst.frc.team346.auto.plans;

import org.usfirst.frc.team346.auto.AutoPlan;
import org.usfirst.frc.team346.auto.actions.ActionRunner;
import org.usfirst.frc.team346.robot.RobotMap;
import org.usfirst.frc.team346.subsystems.Gyro;

import edu.wpi.first.wpilibj.GenericHID.Hand;

public class CenterSwitchFaster extends AutoPlan {

	Gyro sGyro = Gyro.getInstance();
	ActionRunner sAction = new ActionRunner();
	
	private double startingOnLeft, switchLeft, scaleLeft;
	
	public String getGoal() {
		return "center switch faster" + this.startingOnLeft;
	}
	
	public void run(double _startingLeft, double _switchLeft, double _scaleLeft) {
		this.startingOnLeft = _startingLeft;
		this.switchLeft = _switchLeft;
		this.scaleLeft = _scaleLeft;
		
		if(_switchLeft == this.startingOnLeft) {
			this.fastSwitch();
		}
		else {
			this.slowSwitch();
		}
	}
	
	public void fastSwitch() {
			//FAST SWITCH
		this.sAction.setTilterPosNu(RobotMap.kTiltPosScaleMid);
		
		this.sAction.startOpenIntake();
		super.driveUsingDF(-12);
		this.sAction.stopOpenIntake();
		
		this.sAction.setTilterPosNu(RobotMap.kTiltPosSwitchBack);
		
		super.waitTime(0.75);
		this.sAction.setOuttakePercentFront(-1);
		super.waitTime(0.8);
		this.sAction.setOuttakePercentFront(0);
		
		
			//VAULT
		super.driveUsingDF(3);
		
		super.rotateUsingRT(-90 * this.startingOnLeft);
		
		this.sAction.startIntakeCube();
		super.driveUsingDF(5.5);
	}
	
	public void slowSwitch() {
			//SLOW SWITCH
		this.sAction.setTilterPosNu(RobotMap.kTiltPosScaleMid);
		
		if(this.startingOnLeft == 1) {
			super.rotateUsingRT(Hand.kRight, 60);
		}
		else {
			super.rotateUsingRT(Hand.kLeft, -60);
		}
		
		this.sAction.startOpenIntake();
		super.driveUsingDF(-12);
		this.sAction.stopOpenIntake();
		
		super.rotateUsingRT(-45 * this.startingOnLeft);
		
		this.sAction.setTilterPosNu(RobotMap.kTiltPosSwitchBack);
		
		super.driveUsingDF(-7.5);
		this.sAction.setOuttakePercentFront(-1);
		super.waitTime(0.8);
		this.sAction.setOuttakePercentFront(0);
		
		
			//VAULT
		super.driveUsingDF(3);
		
		super.rotateUsingRT(90 * this.startingOnLeft);
		
		this.sAction.startIntakeCube();
		super.driveUsingDF(5.5);
	}
}