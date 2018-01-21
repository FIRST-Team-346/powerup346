package org.usfirst.frc.team346.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Intake implements Subsystem {
	
	private TalonSRX leftIntake, rightIntake;
	
	public enum IntakeMode {
		FORWARD,
		REVERSE,
		OFF;
	}
	
	private static Intake intakeInstance = new Intake();
	protected Intake() {
		this.initTalons();
	}
	
	public static Intake getInstance() {
		return intakeInstance;
	}
	
	public void initTalons() {
		this.leftIntake = new TalonSRX(0);
		this.leftIntake.set(ControlMode.PercentOutput, 0);
		this.leftIntake.setNeutralMode(NeutralMode.Brake);
		
		this.rightIntake = new TalonSRX(1);
		this.rightIntake.set(ControlMode.PercentOutput, 0);
		this.rightIntake.setNeutralMode(NeutralMode.Brake);
	}
	
	public void set(IntakeMode _mode) {
		switch(_mode) {
			case FORWARD : {
				this.leftIntake.set(ControlMode.PercentOutput, 1);
				this.rightIntake.set(ControlMode.PercentOutput, -1);
			}; break;
			
			case REVERSE : {
				this.leftIntake.set(ControlMode.PercentOutput, -1);
				this.rightIntake.set(ControlMode.PercentOutput, 1);
			}; break;
			
			default : {
				this.leftIntake.set(ControlMode.PercentOutput, 0);
				this.rightIntake.set(ControlMode.PercentOutput, 0);
			}; break;
		}
	}

	public void disable() {
		this.leftIntake.set(ControlMode.Disabled, 0);
		this.rightIntake.set(ControlMode.Disabled, 0);
	}

	public void publishData() {
		System.out.println("Left Intake " + this.leftIntake.getMotorOutputVoltage());
		System.out.println("Right Intake " + this.rightIntake.getMotorOutputVoltage());
	}
}
