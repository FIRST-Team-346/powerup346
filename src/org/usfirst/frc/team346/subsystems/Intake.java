package org.usfirst.frc.team346.subsystems;

import org.usfirst.frc.team346.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Intake implements Subsystem {
	
	private TalonSRX mLeftIntake, mRightIntake;
	
	private static Intake sIntakeInstance = new Intake();
	public static Intake getInstance() {
		return sIntakeInstance;
	}
	
	protected Intake() {
		this.initTalons();
	}
	
	private void initTalons() {
		this.mLeftIntake = new TalonSRX(RobotMap.kIntakeLeftPort);
		this.mLeftIntake.set(ControlMode.PercentOutput, 0);
		this.mLeftIntake.setNeutralMode(NeutralMode.Brake);
		
		this.mRightIntake = new TalonSRX(RobotMap.kIntakeRightPort);
		this.mRightIntake.set(ControlMode.PercentOutput, 0);
		this.mRightIntake.setNeutralMode(NeutralMode.Brake);
	}
	
	public void setSpeedIn(double _percentIn) {
		this.setLeftIntakeSpeedIn(_percentIn);
		this.setRightIntakeSpeedIn(_percentIn);
	}
	
	public void setLeftIntakeSpeedIn(double _leftPercentIn) {
		this.mLeftIntake.set(ControlMode.PercentOutput, -_leftPercentIn);
	}

	public void setRightIntakeSpeedIn(double _rightPercentIn) {
		this.mRightIntake.set(ControlMode.PercentOutput, _rightPercentIn);
	}
	
	public void disable() {
		this.mLeftIntake.set(ControlMode.Disabled, 0);
		this.mRightIntake.set(ControlMode.Disabled, 0);
	}

	public void publishData() {
		SmartDashboard.putNumber("LeftIntakeVoltage", this.mLeftIntake.getMotorOutputVoltage());
		SmartDashboard.putNumber("RightIntakeVoltage", this.mRightIntake.getMotorOutputVoltage());
	}
}