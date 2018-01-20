package org.usfirst.frc.team346.subsystem;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Intake implements Subsystem{

	private static Intake intakeInstance = new Intake();
	
	private TalonSRX leftIntake,rightIntake;
	
	public Intake(){
		this.initialize();
	}
	
	public static Intake getInstance() {
		return intakeInstance;
	}
	
	public void initialize() {
		this.leftIntake = new TalonSRX(0);
		this.leftIntake.set(ControlMode.PercentOutput, 0);
		this.leftIntake.setNeutralMode(NeutralMode.Brake);
		
		this.rightIntake = new TalonSRX(1);
		this.rightIntake.set(ControlMode.PercentOutput, 0);
		this.rightIntake.setNeutralMode(NeutralMode.Brake);
	}
	
	public void on() {
		this.leftIntake.set(ControlMode.PercentOutput, 1);
		this.rightIntake.set(ControlMode.PercentOutput, -1);
	}
	
	public void off() {
		this.leftIntake.set(ControlMode.PercentOutput, 0);
		this.rightIntake.set(ControlMode.PercentOutput, 0);
	}

	@Override
	public void disable() {
		this.leftIntake.set(ControlMode.Disabled, 0);
		this.rightIntake.set(ControlMode.Disabled, 0);
	}

	@Override
	public void PublishData() {
		System.out.println("Left Intake " + this.leftIntake.getMotorOutputVoltage());
		System.out.println("Right Intake " + this.rightIntake.getMotorOutputVoltage());
	}
}
