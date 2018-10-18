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
		return "center switch" + this.startingOnLeft;
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
		this.sAction.setTilterPosNu(RobotMap.kTiltPosScaleMid);
		
		super.rotateUsingRT(Hand.kLeft, -45);
		
		this.sAction.startOpenIntake();
		super.driveUsingDF(-9);
		this.sAction.stopOpenIntake();
		
		this.sAction.setTilterPosNu(RobotMap.kTiltPosSwitchBack);
		super.rotateUsingRT(45);
		
		super.driveUsingDF(-7.0);
		this.sAction.setOuttakePercentFront(-1);
		super.waitTime(0.8);
		this.sAction.setOuttakePercentFront(0);
		
		
			//VAULT
		super.driveUsingDF(5);
		
		super.rotateUsingRT(-90);
		
		this.sAction.startIntakeCube();
		super.driveUsingDF(7);
	}
	
	public void rightSwitch() {
			//RIGHT SWITCH
<<<<<<< HEAD
<<<<<<< HEAD
		
		this.sAction.setTilterPosNu(RobotMap.kTiltPosScaleLow);
		super.waitTime(0.2);
=======
		this.sAction.setTilterPosNu(RobotMap.kTiltPosScaleMid);
>>>>>>> d0721b8a512f9c5d32c75ac88170549a695a58ba
=======
		this.sAction.setTilterPosNu(RobotMap.kTiltPosScaleLow);
>>>>>>> parent of 8265c25... For center switch right side
		
		super.rotateUsingRT(Hand.kRight, 45);
		
		super.driveUsingDF(-7.5);
		
<<<<<<< HEAD
		this.sAction.setJustIntakeIn(-1);
<<<<<<< HEAD

		super.rotateUsingRT(-45.0);
=======
		this.sAction.startOpenIntake();
=======
		
		super.rotateUsingRT(-45);
>>>>>>> parent of 8265c25... For center switch right side
		
		super.rotateUsingRT(-45);
>>>>>>> d0721b8a512f9c5d32c75ac88170549a695a58ba
		
		this.sAction.stopOpenIntake();
		
		this.sAction.setTilterPosNu(RobotMap.kTiltPosSwitchBack);
		
		super.driveUsingDF(-7.5);
		this.sAction.setOuttakePercentFront(-1);
		super.waitTime(0.8);
		this.sAction.setOuttakePercentFront(0);
		
		
			//VAULT
<<<<<<< HEAD
<<<<<<< HEAD
		//super.driveUsingDF(7);
		
		//super.rotateUsingRT(-90);
=======
		super.driveUsingDF(5);
=======
		super.driveUsingDF(7);
		
		super.rotateUsingRT(-90);
>>>>>>> parent of 8265c25... For center switch right side
		
		super.rotateUsingRT(90);
>>>>>>> d0721b8a512f9c5d32c75ac88170549a695a58ba
		
		this.sAction.startIntakeCube();
		super.driveUsingDF(7);
	}
}