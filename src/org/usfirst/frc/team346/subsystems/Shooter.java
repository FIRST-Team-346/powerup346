package org.usfirst.frc.team346.subsystems;

import org.usfirst.frc.team346.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Shooter implements Subsystem {

	private TalonSRX mLeftShooter;
	private TalonSRX mRightShooter;
	private double mLeftSetpointNu, mRightSetpointNu;
	
	private double mSecondsFromNeutralToFull = RobotMap.kShooterRampRateSeconds;
	private boolean mIsDisabled;
	
	private static Shooter sTurretShooterInstance = new Shooter();
	public static Shooter getInstance() {
		return sTurretShooterInstance;
	}
	
	protected Shooter() {
		this.initTalons();
		this.initEncoders();
		this.setLeftPIDs(RobotMap.kShooterLeftP, RobotMap.kShooterLeftI, RobotMap.kShooterLeftD);
		this.setRightPIDs(RobotMap.kShooterRightP, RobotMap.kShooterRightI, RobotMap.kShooterRightD);
		this.disable();
		
		this.setLeftSetpointNu(RobotMap.kShooterLeftSetpointNu);
		this.setRightSetpointNu(RobotMap.kShooterRightSetpointNu);
		
		this.mLeftShooter.configClosedloopRamp(this.mSecondsFromNeutralToFull, 0);
		this.mRightShooter.configClosedloopRamp(this.mSecondsFromNeutralToFull, 0);
	}
	
	private void initTalons() {
		this.mLeftShooter = new TalonSRX(RobotMap.kShooterLeftPort);
		this.mLeftShooter.set(ControlMode.Velocity, 0);
		this.mLeftShooter.setNeutralMode(NeutralMode.Coast);
		this.mLeftShooter.overrideLimitSwitchesEnable(true);
		this.mLeftShooter.overrideSoftLimitsEnable(true);
		
		this.mRightShooter = new TalonSRX(RobotMap.kShooterRightPort);
		this.mRightShooter.set(ControlMode.Velocity, 0);
		this.mRightShooter.setNeutralMode(NeutralMode.Coast);
		this.mRightShooter.overrideLimitSwitchesEnable(true);
		this.mRightShooter.overrideSoftLimitsEnable(true);
	}
	
	private void initEncoders() {
		this.mLeftShooter.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 5);
		this.mRightShooter.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 5);
	}
	
	private void setLeftPIDs(double _kP, double _kI, double _kD) {
		this.mLeftShooter.config_kP(0, _kP, 0);
		this.mLeftShooter.config_kI(0, _kI, 0);
		this.mLeftShooter.config_kD(0, _kD, 0);
		this.mLeftShooter.config_kF(0, RobotMap.kShooterLeftF, 0);
	}
	
	private void setRightPIDs(double _kP, double _kI, double _kD) {
		this.mRightShooter.config_kP(0, _kP, 0);
		this.mRightShooter.config_kI(0, _kI, 0);
		this.mRightShooter.config_kD(0, _kD, 0);
		this.mRightShooter.config_kF(0, RobotMap.kShooterRightF, 0);
	}
	
	public void setLeftSetpointNu(double _nu) {
		this.mLeftSetpointNu = _nu;
		
		this.mIsDisabled = false;
	}
	
	public void setRightSetpointNu(double _nu) {
		this.mRightSetpointNu = _nu;
		
		this.mIsDisabled = false;
	}
	
	public void holdSpeedSetpoint() {
		if(!this.mIsDisabled) {
			this.mLeftShooter.set(ControlMode.Velocity, (int)this.mLeftSetpointNu);
			this.mRightShooter.set(ControlMode.Velocity, (int)this.mRightSetpointNu);
		}
		else {
			this.disable();
		}
	}
	
	public void setPercentForward(double _percent) {
		this.setLeftPercentForward(_percent);
		this.setRightPercentForward(_percent);
		
		this.mIsDisabled = false;
	}
	
	public void setPercentReverse(double _percent) {
		this.setLeftPercentReverse(-_percent);
		this.setRightPercentReverse(-_percent);
		
		this.mIsDisabled = false;
	}
	
	public void setLeftPercentForward(double _percent) {
		this.mLeftShooter.set(ControlMode.PercentOutput, _percent);
		
		this.mIsDisabled = false;
	}
	
	public void setRightPercentForward(double _percent) {
		this.mRightShooter.set(ControlMode.PercentOutput, _percent);
		
		this.mIsDisabled = false;
	}
	
	public void setLeftPercentReverse(double _percent) {
		this.mLeftShooter.set(ControlMode.PercentOutput, -_percent);
		
		this.mIsDisabled = false;
	}
	
	public void setRightPercentReverse(double _percent) {
		this.mRightShooter.set(ControlMode.PercentOutput, -_percent);
		
		this.mIsDisabled = false;
	}

	public void disable() {
		this.mLeftShooter.set(ControlMode.Disabled, 0);
		this.mRightShooter.set(ControlMode.Disabled, 0);
		
		this.mIsDisabled = true;
	}

	public void publishData() {
		SmartDashboard.putNumber("LeftShooterVelocity", this.mLeftShooter.getSelectedSensorVelocity(0));
		SmartDashboard.putNumber("RightShooterVelocity", this.mRightShooter.getSelectedSensorVelocity(0));
	}
	
}