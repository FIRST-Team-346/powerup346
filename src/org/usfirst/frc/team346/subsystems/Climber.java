package org.usfirst.frc.team346.subsystems;

import org.usfirst.frc.team346.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Climber implements Subsystem {

	private TalonSRX mWinch;
	private Solenoid mHook;
	private final double mOnValue = 1.0;
	
	private static Climber sClimberInstance = new Climber();
	public static Climber getInstance() {
		return sClimberInstance;
	}
	
	protected Climber() {
		initTalons();
		initSolenoid();
	}
	
	private void initTalons() {
		this.mWinch = new TalonSRX(RobotMap.kClimberPort);
		this.mWinch.set(ControlMode.PercentOutput, 0);
		this.mWinch.setNeutralMode(NeutralMode.Brake);
		this.mWinch.overrideLimitSwitchesEnable(true);
		this.mWinch.overrideSoftLimitsEnable(true);
	}
	
	private void initSolenoid() {
		this.mHook = new Solenoid(RobotMap.kPCMPort, RobotMap.kClimberSolenoidChannel);
	}
	
	public void setOn() {
		this.mWinch.set(ControlMode.PercentOutput, this.mOnValue);
	}
	
	public void setOff() {
		this.mWinch.set(ControlMode.PercentOutput, 0);
	}
	
	public void deployHook() {
		this.mHook.set(true);
	}

	public void disable() {
		this.mWinch.set(ControlMode.Disabled, 0);
		this.mHook.set(false);
	}

	public void publishData() {
		SmartDashboard.putNumber("WinchCurrent", this.mWinch.getOutputCurrent());
	}
	
}