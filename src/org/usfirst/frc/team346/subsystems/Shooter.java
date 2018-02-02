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
	private double mLeftMaxAccel, mRightMaxAccel;
	
	private static Shooter sTurretShooterInstance = new Shooter();
	public static Shooter getInstance() {
		return sTurretShooterInstance;
	}
	
	protected Shooter() {
		this.initTalons();
		this.initEncoders();
		this.initPIDs();
		
		this.setLeftSetpointRPM(RobotMap.kShooterLeftSetpointRPM);
		this.setRightSetpointRPM(RobotMap.kShooterRightSetpointRPM);
		
		this.setRightMaxAccelerationRPM(RobotMap.kShooterRightMaxAcceleration);
		this.setLeftMaxAccelerationRPM(RobotMap.kShooterLeftMaxAcceleration);
	}
	
	private void initTalons() {
		this.mLeftShooter = new TalonSRX(RobotMap.kTurretShooterLeftPort);
		this.mLeftShooter.set(ControlMode.Velocity, 0);
		this.mLeftShooter.setNeutralMode(NeutralMode.Coast);
		this.mLeftShooter.overrideLimitSwitchesEnable(true);
		this.mLeftShooter.overrideSoftLimitsEnable(true);
		
		this.mRightShooter = new TalonSRX(RobotMap.kTurretShooterRightPort);
		this.mRightShooter.set(ControlMode.Velocity, 0);
		this.mRightShooter.setNeutralMode(NeutralMode.Coast);
		this.mRightShooter.overrideLimitSwitchesEnable(true);
		this.mRightShooter.overrideSoftLimitsEnable(true);
	}
	
	private void initEncoders() {
		this.mLeftShooter.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		this.mRightShooter.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
	}
	
	private void initPIDs() {
		this.mLeftShooter.config_kP(0, RobotMap.kShooterLeftP, 0);
		this.mLeftShooter.config_kI(0, RobotMap.kDriveVelLeftI, 0);
		this.mLeftShooter.config_kD(0, RobotMap.kDriveVelLeftD, 0);
		this.mLeftShooter.config_kF(0, RobotMap.kDriveVelLeftF, 0);
		
		this.mRightShooter.config_kP(0, RobotMap.kDriveVelRightP, 0);
		this.mRightShooter.config_kI(0, RobotMap.kDriveVelRightI, 0);
		this.mRightShooter.config_kD(0, RobotMap.kDriveVelRightD, 0);
		this.mRightShooter.config_kF(0, RobotMap.kDriveVelRightF, 0);
	}
	
	public void setLeftSetpointRPM(double _rpm) {
		this.mLeftSetpointRPM = _rpm;
	}
	
	public void setRightSetpointRPM(double _rpm) {
		this.mRightSetpointRPM = _rpm;
	}
	
	public void setOn() {
		int lLeftSetpointNU = (int)(this.mLeftSetpointRPM *1024./60./10.);
		int lRightSetpointNU = (int)(this.mRightSetpointRPM *-1024./60./10.);
		this.mLeftShooter.set(ControlMode.Velocity, lLeftSetpointNU);
		this.mRightShooter.set(ControlMode.Velocity, lRightSetpointNU);
	}
	
	public void setOff() {
		this.mLeftShooter.set(ControlMode.Velocity, 0);
		this.mRightShooter.set(ControlMode.Velocity, 0);
	}
	
	public void setLeftMaxAccelerationRPM(double _rpmm) {
		this.mLeftMaxAccel = _rpmm;
		//TODO: actually use this accel to moderate change in velocity
	}
	
	public void setRightMaxAccelerationRPM(double _rpmm) {
		this.mRightMaxAccel = _rpmm;
		//TODO: actually use this accel to moderate change in velocity
	}

	public void disable() {
		this.mLeftShooter.set(ControlMode.Disabled, 0);
		this.mRightShooter.set(ControlMode.Disabled, 0);
	}

	public void publishData() {
		SmartDashboard.putNumber("LeftShooterRPM:", this.mLeftShooter.getSelectedSensorVelocity(0));
		SmartDashboard.putNumber("RightShooterRPM:", this.mRightShooter.getSelectedSensorVelocity(0));
	}
	
}