package org.usfirst.frc.team346.subsystems;

import org.usfirst.frc.team346.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Intake implements Subsystem {
	
	private TalonSRX mLeftIntake, mRightIntake;
	
	private static Intake sIntakeInstance = new Intake();
	public static Intake getInstance() {
		return sIntakeInstance;
	}
	
	protected Intake() {
		this.initTalons();
	}
	
	public void initTalons() {
		this.mLeftIntake = new TalonSRX(RobotMap.kIntakeLeftPort);
		this.mLeftIntake.set(ControlMode.PercentOutput, 0);
		this.mLeftIntake.setNeutralMode(NeutralMode.Brake);
		
		this.mRightIntake = new TalonSRX(RobotMap.kIntakeRightPort);
		this.mRightIntake.set(ControlMode.PercentOutput, 0);
		this.mRightIntake.setNeutralMode(NeutralMode.Brake);
	}
	
	public void setLeftSpeed(double _leftSpeed) {
		this.mLeftIntake.set(ControlMode.PercentOutput, _leftSpeed);
	}

	public void setRightSpeed(double _rightSpeed) {
		this.mRightIntake.set(ControlMode.PercentOutput, -(_rightSpeed));
	}
	
	public void disable() {
		this.mLeftIntake.set(ControlMode.Disabled, 0);
		this.mRightIntake.set(ControlMode.Disabled, 0);
	}

	public void publishData() {
		System.out.println("LeftIntakeVoltage:" + this.mLeftIntake.getMotorOutputVoltage());
		System.out.println("RightIntakeVoltage:" + this.mRightIntake.getMotorOutputVoltage());
	}
}