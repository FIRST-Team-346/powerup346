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
<<<<<<< HEAD
	private double mLeftSetpoint, mRightSetpoint;
	private double mSecondsFromNeutralToFull = 1.0;
=======
	private int mLeftSetpointNu, mRightSetpointNu;
	
	private boolean mIsInVelocityMode;
>>>>>>> 1c8071b5d50fcadd79039ea45b4acfdc7a72c06a
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
<<<<<<< HEAD
		
		this.setLeftSetpoint(RobotMap.kShooterLeftSetpointRPM);
		this.setRightSetpoint(RobotMap.kShooterRightSetpointRPM);
		
		this.mLeftShooter.configClosedloopRamp(this.mSecondsFromNeutralToFull, 0);
		this.mRightShooter.configClosedloopRamp(this.mSecondsFromNeutralToFull, 0);
=======
>>>>>>> 1c8071b5d50fcadd79039ea45b4acfdc7a72c06a
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
		this.mLeftShooter.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 5);
<<<<<<< HEAD
		this.mLeftShooter.setInverted(true);
=======
		this.mLeftShooter.setInverted(false);
>>>>>>> 1c8071b5d50fcadd79039ea45b4acfdc7a72c06a
		this.mLeftShooter.setSensorPhase(true);
		this.mRightShooter.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 5);
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
	
<<<<<<< HEAD
	public void setLeftPIDs(double _kP, double _kI, double _kD, double _kF) {
		this.mLeftShooter.config_kP(0, _kP, 0);
		this.mLeftShooter.config_kI(0, _kI, 0);
		this.mLeftShooter.config_kD(0, _kD, 0);
		this.mLeftShooter.config_kF(0, _kF, 0);
	}
	
	public void setRightPIDs(double _kP, double _kI, double _kD, double _kF) {
		this.mRightShooter.config_kP(0, _kP, 0);
		this.mRightShooter.config_kI(0, _kI, 0);
		this.mRightShooter.config_kD(0, _kD, 0);
		this.mRightShooter.config_kF(0, _kF, 0);
	}
	
	public void setLeftSetpoint(double _nu) {
		this.mLeftSetpoint = _nu;
=======
	public void setLeftSpeedSetpointNu(int _nu) {
		this.mLeftSetpointNu = _nu;
>>>>>>> 1c8071b5d50fcadd79039ea45b4acfdc7a72c06a
		
		this.mIsInVelocityMode = true;
		this.mLeftShooter.setNeutralMode(NeutralMode.Coast);
		this.mRightShooter.setNeutralMode(NeutralMode.Coast);
	}
	
<<<<<<< HEAD
	public void setRightSetpoint(double _nu) {
		this.mRightSetpoint = _nu;
=======
	public void setRightSpeedSetpointNu(int _nu) {
		this.mRightSetpointNu = _nu;
>>>>>>> 1c8071b5d50fcadd79039ea45b4acfdc7a72c06a
		
		this.mIsInVelocityMode = true;
		this.mLeftShooter.setNeutralMode(NeutralMode.Coast);
		this.mRightShooter.setNeutralMode(NeutralMode.Coast);
	}
	
	public void holdSpeedSetpoint() {
<<<<<<< HEAD
		if(!this.mIsDisabled) {
			int lLeftSetpointNu = (int)(this.mLeftSetpoint);
			int lRightSetpointNu = (int)(this.mRightSetpoint);
			this.mLeftShooter.set(ControlMode.Velocity, lLeftSetpointNu);
			this.mRightShooter.set(ControlMode.Velocity, lRightSetpointNu);
		}
		else {
			this.disable();
=======
		if(!this.mIsDisabled && this.mIsInVelocityMode) {
			this.mLeftShooter.set(ControlMode.Velocity, this.mLeftSetpointNu);
			this.mRightShooter.set(ControlMode.Velocity, this.mRightSetpointNu);
>>>>>>> 1c8071b5d50fcadd79039ea45b4acfdc7a72c06a
		}
	}
	
	public void setPercentFront(double _percent) {
		this.mLeftShooter.set(ControlMode.PercentOutput, _percent);
		this.mRightShooter.set(ControlMode.PercentOutput, _percent);
		
		this.mIsInVelocityMode = false;
		this.mLeftShooter.setNeutralMode(NeutralMode.Brake);
		this.mRightShooter.setNeutralMode(NeutralMode.Brake);
	}
	
	public boolean isInVelocityMode() {
		return this.mIsInVelocityMode;
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
<<<<<<< HEAD
		SmartDashboard.putNumber("LeftShooterSpeed", this.mLeftShooter.getSelectedSensorVelocity(0));
		SmartDashboard.putNumber("RightShooterSpeed", this.mRightShooter.getSelectedSensorVelocity(0));
		SmartDashboard.putNumber("LeftShooterPos", this.mLeftShooter.getSelectedSensorPosition(0));
=======
		SmartDashboard.putNumber("LeftShooterVelocity", this.mLeftShooter.getSelectedSensorVelocity(0));
		SmartDashboard.putNumber("RightShooterVelocity", this.mRightShooter.getSelectedSensorVelocity(0));
>>>>>>> 1c8071b5d50fcadd79039ea45b4acfdc7a72c06a
	}
	
}