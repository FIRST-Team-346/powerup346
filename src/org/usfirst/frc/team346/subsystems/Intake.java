package org.usfirst.frc.team346.subsystems;

import org.usfirst.frc.team346.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Intake implements Subsystem {
	
	private TalonSRX mLeftIntake, mRightIntake;
	private TalonSRX mLeftLoader, mRightLoader;
	private TalonSRX mLeftOuttake, mRightOuttake;
	
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
		
		this.mLeftLoader = new TalonSRX(RobotMap.kLoaderLeftPort);
		this.mLeftLoader.set(ControlMode.PercentOutput, 0);
		this.mLeftLoader.setNeutralMode(NeutralMode.Brake);
		
		this.mRightLoader = new TalonSRX(RobotMap.kLoaderRightPort);
		this.mRightLoader.set(ControlMode.PercentOutput, 0);
		this.mRightLoader.setNeutralMode(NeutralMode.Brake);
		
		this.mLeftOuttake = new TalonSRX(RobotMap.kOuttakeLeftPort);
		this.mLeftOuttake.set(ControlMode.PercentOutput, 0);
		this.mLeftOuttake.setNeutralMode(NeutralMode.Brake);
		
		this.mRightOuttake = new TalonSRX(RobotMap.kOuttakeRightPort);
		this.mRightOuttake.set(ControlMode.PercentOutput, 0);
		this.mRightOuttake.setNeutralMode(NeutralMode.Brake);
	}
	
	public void setLeftIntakeSpeed(double _leftSpeed) {
		this.mLeftIntake.set(ControlMode.PercentOutput, _leftSpeed);
	}

	public void setRightIntakeSpeed(double _rightSpeed) {
		this.mRightIntake.set(ControlMode.PercentOutput, -(_rightSpeed));
	}
	
	public void setLeftLoaderSpeed(double _leftSpeed) {
		this.mLeftLoader.set(ControlMode.PercentOutput, _leftSpeed);
	}

	public void setRightLoaderSpeed(double _rightSpeed) {
		this.mRightLoader.set(ControlMode.PercentOutput, -(_rightSpeed));
	}
	
	public void setLeftOuttakeSpeed(double _leftSpeed) {
		this.mLeftOuttake.set(ControlMode.PercentOutput, _leftSpeed);
	}

	public void setRightOuttakeSpeed(double _rightSpeed) {
		this.mRightOuttake.set(ControlMode.PercentOutput, -(_rightSpeed));
	}
	
	public void disable() {
		this.mLeftIntake.set(ControlMode.Disabled, 0);
		this.mRightIntake.set(ControlMode.Disabled, 0);
		this.mLeftLoader.set(ControlMode.Disabled, 0);
		this.mRightLoader.set(ControlMode.Disabled, 0);
		this.mLeftOuttake.set(ControlMode.Disabled, 0);
		this.mLeftOuttake.set(ControlMode.Disabled, 0);
	}

	public void publishData() {
		SmartDashboard.putNumber("LeftIntakeVoltage", this.mLeftIntake.getMotorOutputVoltage());
		SmartDashboard.putNumber("RightIntakeVoltage", this.mRightIntake.getMotorOutputVoltage());
		SmartDashboard.putNumber("LeftLoaderVoltage", this.mLeftLoader.getMotorOutputVoltage());
		SmartDashboard.putNumber("RightLoaderVoltage", this.mRightLoader.getMotorOutputVoltage());
		SmartDashboard.putNumber("LeftOuttakeVoltage", this.mLeftOuttake.getMotorOutputVoltage());
		SmartDashboard.putNumber("RightOuttakeVoltage", this.mRightOuttake.getMotorOutputVoltage());
	}
}