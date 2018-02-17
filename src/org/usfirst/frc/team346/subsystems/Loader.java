package org.usfirst.frc.team346.subsystems;

import org.usfirst.frc.team346.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Loader implements Subsystem {
	
	private TalonSRX mLeftLoader, mRightLoader;
	
	private static Loader sLoaderInstance = new Loader();
	public static Loader getInstance() {
		return sLoaderInstance;
	}
	
	protected Loader() {
		this.initTalons();
	}
	
	private void initTalons() {
		this.mLeftLoader = new TalonSRX(RobotMap.kLoaderLeftPort);
		this.mLeftLoader.set(ControlMode.PercentOutput, 0);
		this.mLeftLoader.setNeutralMode(NeutralMode.Brake);
		
		this.mRightLoader = new TalonSRX(RobotMap.kLoaderRightPort);
		this.mRightLoader.set(ControlMode.PercentOutput, 0);
		this.mRightLoader.setNeutralMode(NeutralMode.Brake);
	}
	
	public void setSpeedIn(double _percentIn) {
		this.setLeftLoaderSpeedIn(_percentIn);
		this.setRightLoaderSpeedIn(_percentIn);
	}
	
	private void setLeftLoaderSpeedIn(double _leftPercentIn) {
		this.mLeftLoader.set(ControlMode.PercentOutput, _leftPercentIn);
	}

	private void setRightLoaderSpeedIn(double _rightPercentIn) {
		this.mRightLoader.set(ControlMode.PercentOutput, -_rightPercentIn);
	}
	
	public void disable() {
		this.mLeftLoader.set(ControlMode.Disabled, 0);
		this.mRightLoader.set(ControlMode.Disabled, 0);
	}

	public void publishData() {
		SmartDashboard.putNumber("LeftLoaderVoltage", this.mLeftLoader.getMotorOutputVoltage());
		SmartDashboard.putNumber("RightLoaderVoltage", this.mRightLoader.getMotorOutputVoltage());
	}
}