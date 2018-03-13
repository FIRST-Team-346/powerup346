package org.usfirst.frc.team346.auto.plans;

import org.usfirst.frc.team346.auto.actions.ActionRunner;
import org.usfirst.frc.team346.auto.actions.Rotate;
import org.usfirst.frc.team346.robot.RobotMap;
import org.usfirst.frc.team346.subsystems.Gyro;
import org.usfirst.frc.team346.subsystems.Lights;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.GenericHID.Hand;

public class NewCenterStart extends AutoPlan {

	Gyro sGyro = Gyro.getInstance();
	ActionRunner sAction = new ActionRunner();
	Rotate sRotator = new Rotate();
	
	public String getGoal() {
		return "shoot forward to scale";
	}
	
	public void run(double _switchLeft, double _scaleLeft) {
		if(_switchLeft == 1) {
			Lights.getInstance().setLeftBlueRightRed((DriverStation.getInstance().getAlliance() == DriverStation.Alliance.Blue));
			this.leftSwitch();
		}
		else {
			Lights.getInstance().setLeftBlueRightRed(!(DriverStation.getInstance().getAlliance() == DriverStation.Alliance.Blue));
			this.rightSwitch();
		}
	}
	
	public void leftSwitch() {
		
		this.sAction.setTilterPosNu(RobotMap.kTiltPosScaleLow);
		
		this.rotateSingleSide(Hand.kLeft, -45, 2);
//		this.sRotator.rotateSingleSide(Hand.kLeft, -45, 0.5, 3, 1.5);
		
		super.driveUsingDF(-9);
		
		this.sAction.setJustIntakeIn(-1);
		
		this.rotate(45, 2);
//		this.sRotator.rotate(45, 0.5, 3, 1.5);
		
		this.sAction.setJustIntakeIn(0);
		
		this.sAction.setTilterPosNu(RobotMap.kTiltPosSwitchBack);
		
		super.driveUsingDF(-7.0);
		
		this.sAction.shootToSwitchBack();
		
		super.driveUsingDF(3);
		
		this.sRotator.rotate(90, 0.5, 3, 1.5);
	}
	
	public void rightSwitch() {
		
		this.sAction.setTilterPosNu(RobotMap.kTiltPosScaleLow);
		
		this.rotateSingleSide(Hand.kRight, 45, 2);
//		this.sRotator.rotateSingleSide(Hand.kRight, 45, 0.5, 3, 1.5);
		
		super.driveUsingDF(-7.5);
		
		this.sAction.setJustIntakeIn(-1);
		
		this.rotate(-45, 2);
//		this.sRotator.rotate(-45, 0.5, 3, 1.5);
		
		this.sAction.setJustIntakeIn(0);
		
		this.sAction.setTilterPosNu(RobotMap.kTiltPosSwitchBack);
		
		super.driveUsingDF(-7.5);
		
		this.sAction.shootToSwitchBack();
		
		super.driveUsingDF(3);
		
		this.sRotator.rotate(-90, 0.5, 3, 2);
	}
}