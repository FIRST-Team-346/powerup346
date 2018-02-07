package org.usfirst.frc.team346.subsystems;

import org.usfirst.frc.team346.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Tilter implements Subsystem {

	private TalonSRX mTilter;
	private double mTilterSetpoint;
	
	private double mNativeUnitMin = 2611, mNativeUnitMax = 2874, mAngleMin, mAngleMax;
	private double mPrevVel = 0, mPrevAccel = 0, mPrevTime = 0;
	private final double kCodesPerRev = 1024;
	private double mMaxVel = 50, mMaxAccel = 100;
	
	public enum TiltPos {
		NEUTRAL,
		SWITCH_CLOSE,
		SWITCH_FAR,
		SCALE_CLOSE,
		SCALE_FAR;
	}
	
	private static Tilter sTilterInstance = new Tilter();
	public static Tilter getInstance() {
		return sTilterInstance;
	}
	
	protected Tilter() {
		initTalon();
		initPID();
		
		this.mPrevTime = System.currentTimeMillis() /1000.;
	}
	
	private void initTalon() {
		this.mTilter = new TalonSRX(RobotMap.kTilterPort);
		this.mTilter.set(ControlMode.MotionMagic, 0);
		this.mTilter.setNeutralMode(NeutralMode.Brake);
		this.mTilter.overrideLimitSwitchesEnable(true);
		this.mTilter.overrideSoftLimitsEnable(true);
		
		this.mTilter.configSelectedFeedbackSensor(FeedbackDevice.Analog, 0, 5);
		this.mTilter.setInverted(true);
		
		this.mTilter.configMotionAcceleration(RobotMap.kTilterMaxAccelerationRPM, 0);
		this.mTilter.configMotionCruiseVelocity(RobotMap.kTilterCruiseVelocityRPM, 0);
	}
	
	private void initPID() {
		this.mTilter.config_kP(0, 0.5, 0);
		this.mTilter.config_kF(0, 1023./(102.*this.kCodesPerRev/60./10.), 0);
	}
	
	public void setMotorPercent(double _percent) {
		this.mTilter.set(ControlMode.PercentOutput, _percent);
	}
	
	public void setPos(TiltPos _position) {
		switch(_position) {
			case SWITCH_CLOSE : {
				this.setSetpointDegrees(0);
				this.setSetpointNu(0);
			}; break;
			
			case SWITCH_FAR : {
				this.setSetpointDegrees(0);
				this.setSetpointNu(0);
			};  break;
			
			case SCALE_CLOSE : {
				this.setSetpointDegrees(0);
				this.setSetpointNu(0);
			}; break;
			
			case SCALE_FAR : {
				this.setSetpointDegrees(0);
				this.setSetpointNu(0);
			}; break;
			
			default : {
				this.setSetpointDegrees(0);
				this.setSetpointNu(0);
			}; break;
		}
	}
	
	public void setSetpointDegrees(double _degrees) {
		//TODO: Test
		if(_degrees < this.mAngleMin) {
			System.out.println("Set tilter inside (min,max) to avoid unexpected results.");
			_degrees = this.mAngleMin;
		}
		else if(_degrees > this.mAngleMax) {
			System.out.println("Set tilter inside (min,max) to avoid unexpected results.");
			_degrees = this.mAngleMax;
		}
		
		double lNuRange = this.mNativeUnitMax - this.mNativeUnitMin;
		double lAngleRange = this.mAngleMax - this.mAngleMin;
		
		double lPercentRange = (_degrees - this.mAngleMin) /lAngleRange;
		double lNuSetpoint = this.mNativeUnitMin + (lPercentRange *lNuRange);
		
		this.mTilterSetpoint = lNuSetpoint;
		this.mTilter.set(ControlMode.MotionMagic, this.mTilterSetpoint);
	}
	
	public void setSetpointNu(double _nu) {
		this.mTilterSetpoint = _nu;
		this.mTilter.set(ControlMode.MotionMagic, this.mTilterSetpoint);
	}
	
	public void setCruiseVelocityRPM(double _rpm) {
		int lSetpoint = (int)(_rpm *this.kCodesPerRev/60./10.);
		this.mTilter.configMotionCruiseVelocity(lSetpoint, 0);
	}
	
	public void setMaxAccelerationRPM(double _rpmPs) {
		int lSetpoint = (int)(_rpmPs *this.kCodesPerRev/60./10.);
		this.mTilter.configMotionAcceleration(lSetpoint, 0);
	}
	
	public void setCruiseVelocityNu(int _nu) {
		this.mTilter.configMotionCruiseVelocity(_nu, 0);
	}
	
	public void setMaxAccelerationNu(int _nuPs) {
		this.mTilter.configMotionAcceleration(_nuPs, 0);
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
	
	public double getMaxVel() {
		double lCurrentVel = this.getVelocity();
		
		if(Math.abs(lCurrentVel) > Math.abs(this.mMaxVel)) {
			this.mMaxVel = Math.abs(lCurrentVel);
		}
		return this.mMaxVel;
	}
	
	public double getMaxAccel() {
		double lCurrentAccel = this.getAcceleration();
		
		if(Math.abs(lCurrentAccel) > Math.abs(this.mMaxAccel)) {
			this.mMaxAccel = Math.abs(lCurrentAccel);
		}
		return this.mMaxAccel;
	}
	
	public double getAcceleration() {
		//TODO: check
		double lAccel = this.mPrevAccel;
		if(System.currentTimeMillis() - this.mPrevTime > 0.1) {
			lAccel = (this.getVelocity() - this.mPrevVel) /0.1;
			this.mPrevVel = this.getVelocity();
			this.mPrevTime = System.currentTimeMillis() /1000.;
			this.mPrevAccel = lAccel;
		}
		return lAccel;
	}

	public void publishData() {
		SmartDashboard.putNumber("TilterPosition", this.getPosition());
		SmartDashboard.putNumber("TilterVelocity", this.getVelocity());
		SmartDashboard.putNumber("TilterAcceleration", this.getAcceleration());
	}
	
	public void disable() {
		this.mTilter.set(ControlMode.Disabled, 0);
	}
	
}