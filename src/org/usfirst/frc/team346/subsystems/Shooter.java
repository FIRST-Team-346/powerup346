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
	private int mLeftSetpointNu, mRightSetpointNu;
	
	private boolean mIsInVelocityMode;
	private boolean mIsDisabled;
	
	private static Shooter sShooterInstance = new Shooter();
	public static Shooter getInstance() {
		return sShooterInstance;
	}
	
	protected Shooter() {
		this.initTalons();
		this.initEncoders();
		this.setLeftPIDs(RobotMap.kShooterLeftP, RobotMap.kShooterLeftI, RobotMap.kShooterLeftD);
		this.setRightPIDs(RobotMap.kShooterRightP, RobotMap.kShooterRightI, RobotMap.kShooterRightD);
		this.disable();
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
		
		this.mLeftShooter.configClosedloopRamp(RobotMap.kShooterRampRateSeconds, 0);
		this.mRightShooter.configClosedloopRamp(RobotMap.kShooterRampRateSeconds, 0);
	}
	
	private void initEncoders() {
		//Sets the Talon to get input from the QuadEncoder plugged into it.
		this.mLeftShooter.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		//Gets the motor to spin in the direction we want and that positive sensor is
		//in phase with positive motor output.
		this.mLeftShooter.setInverted(false);
		this.mLeftShooter.setSensorPhase(true);
		
		this.mRightShooter.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		this.mRightShooter.setInverted(true);
		this.mRightShooter.setSensorPhase(true);
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
	
	public void setLeftSpeedSetpointNu(int _nu) {
		this.mLeftSetpointNu = _nu;
		
		this.mIsInVelocityMode = true;
		this.mIsDisabled = false;
		this.mLeftShooter.setNeutralMode(NeutralMode.Coast);
		this.mRightShooter.setNeutralMode(NeutralMode.Coast);
	}
	
	public void setRightSpeedSetpointNu(int _nu) {
		this.mRightSetpointNu = _nu;
		
		this.mIsInVelocityMode = true;
		this.mIsDisabled = false;
		this.mLeftShooter.setNeutralMode(NeutralMode.Coast);
		this.mRightShooter.setNeutralMode(NeutralMode.Coast);
	}
	
	/*
	 * This year, we wanted the shooters to be in setpoint mode for shooting to the scale,
	 *  so they would be accurate, but in velocity mode for spitting out to the switch,
	 *  to be quick and reliable. That's why we have all these "velocity mode" checks.
	 */
	public void holdSpeedSetpoint() {
		if(this.isInVelocityModeAndOn()) {
			this.mLeftShooter.set(ControlMode.Velocity, this.mLeftSetpointNu);
			this.mRightShooter.set(ControlMode.Velocity, this.mRightSetpointNu);
		}
	}
	
	/*
	 * This checks if the shooter speed is within a certain amount from where we want it to be,
	 *  and if both left and right are within that range, then it is considered "at speed".
	 *  This can potentially be useful, but if the range is too small, then the natural variations
	 *  will make them rarely be at speed. So don't rely too heavily on this to be accurate.
	 */
	public boolean isAtSpeed() {
		boolean lLeftAtSpeed = false, lRightAtSpeed = false;
		if(Math.abs(this.mLeftShooter.getSelectedSensorVelocity(0) - this.mLeftSetpointNu) < 200) {
			lLeftAtSpeed = true;
		}
		if(Math.abs(this.mRightShooter.getSelectedSensorVelocity(0) - this.mRightSetpointNu) < 200) {
			lRightAtSpeed = true;
		};
		return (lLeftAtSpeed && lRightAtSpeed);
	}
	
	public void setPercentFront(double _percent) {
		this.mLeftShooter.set(ControlMode.PercentOutput, _percent);
		this.mRightShooter.set(ControlMode.PercentOutput, _percent);
		
		this.mIsInVelocityMode = false;
		this.mIsDisabled = false;
		this.mLeftShooter.setNeutralMode(NeutralMode.Brake);
		this.mRightShooter.setNeutralMode(NeutralMode.Brake);
	}
	
	public boolean isInVelocityMode() {
		return this.mIsInVelocityMode;
	}
	
	public boolean isInVelocityModeAndOn() {
		return (this.mIsInVelocityMode && !this.mIsDisabled);
	}

	public void disable() {
		this.mLeftShooter.set(ControlMode.Disabled, 0);
		this.mRightShooter.set(ControlMode.Disabled, 0);
		
		this.mIsDisabled = true;
	}
	
	public void zeroEncoders() {
		this.mLeftShooter.setSelectedSensorPosition(0, 0, 0);
		this.mRightShooter.setSelectedSensorPosition(0, 0, 0);
	}

	public void publishData() {
		SmartDashboard.putNumber("LeftShooterVelocity", this.mLeftShooter.getSelectedSensorVelocity(0));
		SmartDashboard.putNumber("RightShooterVelocity", this.mRightShooter.getSelectedSensorVelocity(0));
	}
	
}