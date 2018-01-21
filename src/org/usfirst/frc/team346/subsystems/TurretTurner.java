package org.usfirst.frc.team346.subsystems;

import org.usfirst.frc.team346.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TurretTurner implements Subsystem {

	private TalonSRX mTurner;
	private double mTurnerSetpoint;
	
	private static TurretTurner sTurretTurnerInstance = new TurretTurner();
	protected TurretTurner() {
		initTalon();
	}
	
	public static TurretTurner getInstance() {
		return sTurretTurnerInstance;
	}
	
	private void initTalon() {
		this.mTurner = new TalonSRX(RobotMap.kTurretTurnerPort);
		this.mTurner.set(ControlMode.MotionMagic, 0);
		this.mTurner.setNeutralMode(NeutralMode.Brake);
		
		this.mTurner.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0, 0);
		
		this.mTurner.configMotionAcceleration(RobotMap.kTurretTurnerMaxAcceleration, 0);
		this.mTurner.configMotionCruiseVelocity(RobotMap.kTurretTurnerCruiseVelocity, 0);
	}
	
	public void set(double _degrees) {
		//TODO: Test
		if(_degrees < 0) {
			System.out.println("Set turret turner inside [0,360) to avoid unexpected results.");
			_degrees += 360.0;
		}
		else if(_degrees >= 360) {
			System.out.println("Set turret turner inside [0,360) to avoid unexpected results.");
			_degrees -= 360.0;
		}
		
		double lNativeUnits = 0;
		if(_degrees <= 180) {
			lNativeUnits = (180.0-_degrees)/180.0 * -1023.0;
		}
		if(_degrees > 180) {
			lNativeUnits = (_degrees-180.0)/180.0 * 1023.0;
		}
		
		this.mTurnerSetpoint = lNativeUnits;
		this.mTurner.set(ControlMode.MotionMagic, this.mTurnerSetpoint);
	}
	
	public double getPosition() {
		return this.mTurner.getSelectedSensorPosition(0);
	}
	
	public double getSetpoint() {
		return this.mTurnerSetpoint;
	}
	
	public double getVelocity() {
		return this.mTurner.getSelectedSensorVelocity(0);
	}
	
	public double getAcceleration() {
		//TODO: make a loop to check if previous time has exceeded 100ms, then divided the average velocity by the time
		return 0;
	}

	public void disable() {
		this.mTurner.set(ControlMode.Disabled, 0);
	}

	public void publishData() {
		SmartDashboard.putNumber("TurnerPosition", this.getPosition());
		SmartDashboard.putNumber("TurnerVelocity", this.getVelocity());
		SmartDashboard.putNumber("TurnerAcceleration", this.getAcceleration());
	}
	
}
