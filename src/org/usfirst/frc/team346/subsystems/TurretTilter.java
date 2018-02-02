package org.usfirst.frc.team346.subsystems;

import org.usfirst.frc.team346.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TurretTilter implements Subsystem {

	private TalonSRX mTilter;
	private double mTilterSetpoint;
	
	private static TurretTilter sTurretTilterInstance = new TurretTilter();
	public static TurretTilter getInstance() {
		return sTurretTilterInstance;
	}
	
	protected TurretTilter() {
		initTalon();
	}
	
	private void initTalon() {
		this.mTilter = new TalonSRX(RobotMap.kTurretTilterPort);
		this.mTilter.set(ControlMode.MotionMagic, 0);
		this.mTilter.setNeutralMode(NeutralMode.Brake);
		this.mTilter.overrideLimitSwitchesEnable(true);
		this.mTilter.overrideSoftLimitsEnable(true);
		
		this.mTilter.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0, 0);
		
		this.mTilter.configMotionAcceleration(RobotMap.kTurretTilterMaxAcceleration, 0);
		this.mTilter.configMotionCruiseVelocity(RobotMap.kTurretTilterCruiseVelocity, 0);
	}
	
	public void set(double _degrees) {
		//TODO: Test
		if(_degrees < 0) {
			System.out.println("Set turret tilter inside [0,360) to avoid unexpected results.");
			_degrees += 360.0;
		}
		else if(_degrees >= 360) {
			System.out.println("Set turret tilter inside [0,360) to avoid unexpected results.");
			_degrees -= 360.0;
		}
		
		double lNativeUnits = 0;
		if(_degrees <= 180) {
			lNativeUnits = (180.0-_degrees)/180.0 * -1023.0;
		}
		if(_degrees > 180) {
			lNativeUnits = (_degrees-180.0)/180.0 * 1023.0;
		}
		
		this.mTilterSetpoint = lNativeUnits;
		this.mTilter.set(ControlMode.MotionMagic, this.mTilterSetpoint);
	}
	
	public void setCruiseVelocityRPM(double _rpm) {
		int lSetpoint = (int)(_rpm *1024./60./10.);
		this.mTilter.configMotionCruiseVelocity(lSetpoint, 0);
	}
	
	public void setMaxAccelerationRPM(double _rpm) {
		int lSetpoint = (int)(_rpm *1024./60./10.);
		this.mTilter.configMotionAcceleration(lSetpoint, 0);
	}
	
	public double getSetpoint() {
		return this.mTilterSetpoint;
	}
	
	public double getPosition() {
		return this.mTilter.getSelectedSensorPosition(0);
	}
	
	public double getVelocity() {
		return this.mTilter.getSelectedSensorVelocity(0);
	}
	
	public double getAcceleration() {
		//TODO: make a loop to check if previous time has exceeded 100ms, then divided the average velocity by the time
		return 0;
	}

	public void disable() {
		this.mTilter.set(ControlMode.Disabled, 0);
	}

	public void publishData() {
		SmartDashboard.putNumber("TilterPosition", this.getPosition());
		SmartDashboard.putNumber("TilterVelocity", this.getVelocity());
		SmartDashboard.putNumber("TilterAcceleration", this.getAcceleration());
	}
	
}