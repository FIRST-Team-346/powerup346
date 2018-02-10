package org.usfirst.frc.team346.subsystems;

import org.usfirst.frc.team346.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Outtake implements Subsystem {
	
	private TalonSRX mLeftOuttake, mRightOuttake;
	
	private static Outtake sOuttakeInstance = new Outtake();
	public static Outtake getInstance() {
		return sOuttakeInstance;
	}
	
	protected Outtake() {
		this.initTalons();
	}
	
	public void initTalons() {
		this.mLeftOuttake = new TalonSRX(RobotMap.kOuttakeLeftPort);
		this.mLeftOuttake.set(ControlMode.PercentOutput, 0);
		this.mLeftOuttake.setNeutralMode(NeutralMode.Brake);
		
		this.mRightOuttake = new TalonSRX(RobotMap.kOuttakeRightPort);
		this.mRightOuttake.set(ControlMode.PercentOutput, 0);
		this.mRightOuttake.setNeutralMode(NeutralMode.Brake);
	}
	
	public void setLeftOuttakeSpeedForward(double _leftSpeed) {
		this.mLeftOuttake.set(ControlMode.PercentOutput, _leftSpeed);
	}

	public void setRightOuttakeSpeedForward(double _rightSpeed) {
		this.mRightOuttake.set(ControlMode.PercentOutput, -(_rightSpeed));
	}
	
	public void setLeftOuttakeSpeedReverse(double _leftSpeed) {
		this.mLeftOuttake.set(ControlMode.PercentOutput, -_leftSpeed);
	}

	public void setRightOuttakeSpeedReverse(double _rightSpeed) {
		this.mRightOuttake.set(ControlMode.PercentOutput, _rightSpeed);
	}
	
	public void disable() {
		this.mLeftOuttake.set(ControlMode.Disabled, 0);
		this.mLeftOuttake.set(ControlMode.Disabled, 0);
	}

	public void publishData() {
		SmartDashboard.putNumber("LeftOuttakeVoltage", this.mLeftOuttake.getMotorOutputVoltage());
		SmartDashboard.putNumber("RightOuttakeVoltage", this.mRightOuttake.getMotorOutputVoltage());
	}
}