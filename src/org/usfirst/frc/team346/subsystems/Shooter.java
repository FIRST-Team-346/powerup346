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
	private double mLeftSetpointRPM, mRightSetpointRPM;
	private double mSecondsFromNeutralToFull = 1.0;
	private boolean mIsDisabled;
	
	private static Shooter sTurretShooterInstance = new Shooter();
	public static Shooter getInstance() {
		return sTurretShooterInstance;
	}
	
	protected Shooter() {
		this.initTalons();
		this.initEncoders();
		this.initPIDs();
		this.disable();
		
		this.setLeftSetpointRPM(RobotMap.kShooterLeftSetpointRPM);
		this.setRightSetpointRPM(RobotMap.kShooterRightSetpointRPM);
		
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
	
	private void initPIDs() {
		this.mLeftShooter.config_kP(0, RobotMap.kShooterLeftP, 0);
		this.mLeftShooter.config_kI(0, RobotMap.kShooterLeftI, 0);
		this.mLeftShooter.config_kD(0, RobotMap.kShooterLeftD, 0);
		this.mLeftShooter.config_kF(0, RobotMap.kShooterLeftF, 0);
		
		this.mRightShooter.config_kP(0, RobotMap.kShooterRightP, 0);
		this.mRightShooter.config_kI(0, RobotMap.kShooterRightI, 0);
		this.mRightShooter.config_kD(0, RobotMap.kShooterRightD, 0);
		this.mRightShooter.config_kF(0, RobotMap.kShooterRightF, 0);
	}
	
	public void setLeftSetpointRPM(double _rpm) {
		this.mLeftSetpointRPM = _rpm;
		
		this.mIsDisabled = false;
	}
	
	public void setRightSetpointRPM(double _rpm) {
		this.mRightSetpointRPM = _rpm;
		
		this.mIsDisabled = false;
	}
	
	public void holdSpeedSetpoint() {
		if(!this.mIsDisabled) {
			int lLeftSetpointNu = (int)(this.mLeftSetpointRPM *1024./60./10.);
			int lRightSetpointNu = (int)(this.mRightSetpointRPM *-1024./60./10.);
			this.mLeftShooter.set(ControlMode.Velocity, lLeftSetpointNu);
			this.mRightShooter.set(ControlMode.Velocity, lRightSetpointNu);
		}
		else {
			this.disable();
		}
	}
	
	public void setPercentForward(double _percent) {
		this.setLeftPercentForward(-_percent);
		this.setRightPercentForward(-_percent);
		
		this.mIsDisabled = false;
	}
	
	public void setPercentReverse(double _percent) {
		this.setLeftPercentReverse(_percent);
		this.setRightPercentReverse(_percent);
		
		this.mIsDisabled = false;
	}
	
	public void setLeftPercentForward(double _percent) {
		this.mLeftShooter.set(ControlMode.PercentOutput, _percent);
		
		this.mIsDisabled = false;
	}
	
	public void setRightPercentForward(double _percent) {
		this.mRightShooter.set(ControlMode.PercentOutput, -_percent);
		
		this.mIsDisabled = false;
	}
	
	public void setLeftPercentReverse(double _percent) {
		this.mLeftShooter.set(ControlMode.PercentOutput, -_percent);
		
		this.mIsDisabled = false;
	}
	
	public void setRightPercentReverse(double _percent) {
		this.mRightShooter.set(ControlMode.PercentOutput, _percent);
		
		this.mIsDisabled = false;
	}

	public void disable() {
		this.mLeftShooter.set(ControlMode.Disabled, 0);
		this.mRightShooter.set(ControlMode.Disabled, 0);
		
		this.mIsDisabled = true;
	}

	public void publishData() {
		SmartDashboard.putNumber("LeftShooterRPM", this.mLeftShooter.getSelectedSensorVelocity(0));
		SmartDashboard.putNumber("RightShooterRPM", this.mRightShooter.getSelectedSensorVelocity(0));
	}
	
}