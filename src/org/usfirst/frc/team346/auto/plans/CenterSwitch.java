package org.usfirst.frc.team346.auto.plans;

import org.usfirst.frc.team346.auto.AutoPlan;
import org.usfirst.frc.team346.auto.actions.ActionRunner;
import org.usfirst.frc.team346.auto.actions.Rotate;
import org.usfirst.frc.team346.robot.RobotMap;
import org.usfirst.frc.team346.subsystems.Gyro;
import org.usfirst.frc.team346.subsystems.Lights;

import edu.wpi.first.wpilibj.GenericHID.Hand;

public class CenterSwitch extends AutoPlan {

	Gyro sGyro = Gyro.getInstance();
	ActionRunner sAction = new ActionRunner();
	Rotate sRotator = new Rotate();
	
	private double startingOnLeft, switchLeft, scaleLeft;
	
	public String getGoal() {
		return "good scale" + this.startingOnLeft;
	}
	
	public void run(double _startingLeft, double _switchLeft, double _scaleLeft) {
		this.startingOnLeft = _startingLeft;
		this.switchLeft = _switchLeft;
		this.scaleLeft = _scaleLeft;
		
		if(_switchLeft == 1) {
//			Lights.getInstance().setLeftBlueRightRed((DriverStation.getInstance().getAlliance() == DriverStation.Alliance.Blue));
			this.leftSwitch();
		}
		else {
//			Lights.getInstance().setLeftBlueRightRed(!(DriverStation.getInstance().getAlliance() == DriverStation.Alliance.Blue));
			this.rightSwitch();
		}
	}
	
	public void leftSwitch() {
			//LEFT SWITCH
		this.sAction.setTilterPosNu(RobotMap.kTiltPosScaleLow);
		
		super.rotateUsingRT(Hand.kLeft, -45);
		
		this.sAction.setJustIntakeIn(-1);
		super.driveUsingDF(-9);
		this.sAction.setJustIntakeIn(0);
		
		this.sAction.setTilterPosNu(RobotMap.kTiltPosSwitchBack);
		super.rotateUsingRT(45);
		
		super.driveUsingDF(-7.0);
		this.sAction.setOuttakePercentFront(-1);
		super.waitTime(0.2);
		this.sAction.setOuttakePercentFront(0);
		
		
			//VAULT
		super.driveUsingDF(7);
		
		super.rotateUsingRT(90);
		
//		super.driveUsingDF(-6);
//		
//		super.rotateUsingRT(90);
//		
//		this.sAction.setIntakeIn(1);
//		super.driveUsingDF(3);
//		this.sAction.setIntakeIn(0);
	}
	
	public void rightSwitch() {
			//RIGHT SWITCH
		
		this.sAction.setTilterPosNu(RobotMap.kTiltPosScaleLow);
		super.waitTime(0.2);
		
		super.rotateUsingRT(Hand.kRight, 45.0);
		super.waitTime(0.2);
		
		super.driveUsingDF(-7.5);
		super.waitTime(0.2);
		
		this.sAction.setJustIntakeIn(-1);

		super.rotateUsingRT(-45.0);
		
		this.sAction.setJustIntakeIn(0);
		
		this.sAction.setTilterPosNu(RobotMap.kTiltPosSwitchBack);
		super.waitTime(0.2);
		
		super.driveUsingDF(-7.5);
		
		this.sAction.setOuttakePercentFront(-1);
		super.waitTime(0.2);
		this.sAction.setOuttakePercentFront(0);
		
		
		
			//VAULT
		//super.driveUsingDF(7);
		
		//super.rotateUsingRT(-90);
		
//		super.driveUsingDF(-6);
//		
//		super.rotateUsingRT(-90);
//		
//		this.sAction.setIntakeIn(1);
//		super.driveUsingDF(3);
//		this.sAction.setIntakeIn(0);
	}
}