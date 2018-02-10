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
	
	private double mPrevTime, mPrevVel, mPrevAccel;
	private double mMaxVel, mMaxAccel;
	private boolean mIsDisabled;
	
	public enum TiltPos {
		NEUTRAL,
		SWITCH_CLOSE,
		SWITCH_FAR,
		SCALE_CLOSE,
		SCALE_FAR;
	}
	
	private static Tilter sTilterInstance = new Tilter();
	/**Gets the single instance of Tilter.
	 * @return instance of Tilter**/
	public static Tilter getInstance() {
		return sTilterInstance;
	}
	
	protected Tilter() {
		this.initTalon();
		this.setPID(RobotMap.kTilterP, RobotMap.kTilterI, RobotMap.kTilterD);
		this.disable();
	}
	
	/**Instantiates the Talon controllers for the Tilter.**/
	private void initTalon() {
		this.mTilter = new TalonSRX(RobotMap.kTilterPort);
		this.mTilter.setNeutralMode(NeutralMode.Brake);
		this.mTilter.overrideLimitSwitchesEnable(true);
		this.mTilter.overrideSoftLimitsEnable(true);
		
		this.mTilter.configSelectedFeedbackSensor(FeedbackDevice.Analog, 0, 5);
		this.mTilter.setInverted(false);
		this.mTilter.setSensorPhase(false);
		
		this.setMotionMagicAccelerationNu(RobotMap.kTilterDesiredAccelerationNu);
		this.setMotionMagicVelocityNu(RobotMap.kTilterDesiredVelocityNu);
	}
	
	/**Sets the PIDF-controller values for Motion Magic mode.
	 * @param _kP kP value
	 * @param _kI kI value
	 * @param _kD kD value**/
	public void setPID(double _kP, double _kI, double _kD) {
		this.mTilter.config_kP(0, _kP, 0);
		this.mTilter.config_kI(0, _kI, 0);
		this.mTilter.config_kD(0, _kD, 0);
		this.mTilter.config_kF(0, RobotMap.kTilterF, 0);
	}
	
	/**Sets the Tilter motor to a voltage percent.
	 * @param _percent percent voltage**/
	public void setPercent(double _percent) {
		this.mTilter.set(ControlMode.PercentOutput, _percent);
	}
	
	/**Sets the absolute position setpoint of the Tilter for Motion Magic mode.
	 * @param _position named position setpoint from TiltPos enum
	 * @options SWITCH_CLOSE, SWITCH_FAR, SCALE_CLOSE, SCALE_FAR, NEUTRAL**/
	public void setSetpointPos(TiltPos _position) {
		double lPosNeg = (RobotMap.kTiltUpIsPositive ? 1 : -1);
		double lPosition1 = RobotMap.kTiltPosNeutral + RobotMap.kTiltPosNeutralToPOSITION1 * lPosNeg;
		double lPosition2 = RobotMap.kTiltPosNeutral + RobotMap.kTiltPosNeutralToPOSITION2 * lPosNeg;
		double lPosition3 = RobotMap.kTiltPosNeutral + RobotMap.kTiltPosNeutralToPOSITION3 * lPosNeg;
		double lPosition4 = RobotMap.kTiltPosNeutral + RobotMap.kTiltPosNeutralToPOSITION4 * lPosNeg;
		
		switch(_position) {
			case SWITCH_CLOSE : {
				this.setSetpointNu(lPosition1);
			}; break;
			
			case SWITCH_FAR : {
				this.setSetpointNu(lPosition2);
			};  break;
			
			case SCALE_CLOSE : {
				this.setSetpointNu(lPosition3);
			}; break;
			
			case SCALE_FAR : {
				this.setSetpointNu(lPosition4);
			}; break;
			
			default : {
				this.setSetpointNu(RobotMap.kTiltPosNeutral);
			}; break;
		}
	}
	
	/**Sets the absolute position setpoint of the Tilter for Motion Magic mode.
	 * @param _nu position setpoint in Nu**/
	public void setSetpointNu(double _nu) {
		this.mTilterSetpoint = _nu;
		this.mIsDisabled = false;
	}
	
	/**Puts the Tilter into Motion Magic mode to follow its current setpoint.**/
	public void holdSetpoint() {
		if(!this.mIsDisabled) {
			this.mTilter.set(ControlMode.MotionMagic, this.mTilterSetpoint);
		}
		else {
			this.disable();
		}
	}
	
	/**Sets the cruise velocity of the Tilter for Motion Magic mode.
	 * @param _nu cruise velocity in Nu per 100ms**/
	public void setMotionMagicVelocityNu(int _nu) {
		this.mTilter.configMotionCruiseVelocity(_nu, 0);
	}
	
	/**Sets the maximum acceleration of the Tilter for Motion Magic mode.
	 * @param _nu maximum acceleration in Nu per 100ms per 1s**/
	public void setMotionMagicAccelerationNu(int _nu) {
		this.mTilter.configMotionAcceleration(_nu, 0);
	}
	
	/**Gets the absolute position setpoint of the Tilter for Motion Magic mode.
	 * @return position setpoint in Nu**/
	public double getSetpointNu() {
		return this.mTilterSetpoint;
	}
	
	/**Gets the current absolute position of the Tilter.
	 * @return position in Nu (1 rev = 1024 Nu)**/
	public double getPositionNu() {
		return this.mTilter.getSelectedSensorPosition(0);
	}
	
	/**Gets the current velocity of the Tilter.
	 * @return velocity in Nu per 100ms (1 rpm = 1.70666... Nu/100ms)**/
	public double getVelocityNu() {
		return this.mTilter.getSelectedSensorVelocity(0);
	}
	
	/**Gets the current acceleration of the Tilter.
	 * @return acceleration in Nu per 100ms per 1s (1 rpm/s = 1.70666... Nu/100ms/s)**/
	public double getAccelerationNu() {
		double lAccel = this.mPrevAccel;
		if(System.currentTimeMillis() - this.mPrevTime > 0.1) {
			lAccel = (this.getVelocityNu() - this.mPrevVel) /0.1;
			this.mPrevTime = System.currentTimeMillis() /1000.;
			this.mPrevVel = this.getVelocityNu();
			this.mPrevAccel = lAccel;
		}
		return lAccel;
	}
	
	/**Gets the maximum velocity of the Tilter since current life-cycle.
	 * @return maximum velocity in Nu per 100ms**/
	public double getMaxVelNu() {
		double lCurrentVel = this.getVelocityNu();
		
		if(Math.abs(lCurrentVel) > Math.abs(this.mMaxVel)) {
			this.mMaxVel = Math.abs(lCurrentVel);
		}
		return this.mMaxVel;
	}
	
	/**Gets the maximum acceleration of the Tilter since current life-cycle.
	 * @return maximum acceleration in Nu per 100ms per 1s**/
	public double getMaxAccelNu() {
		double lCurrentAccel = this.getAccelerationNu();
		
		if(Math.abs(lCurrentAccel) > Math.abs(this.mMaxAccel)) {
			this.mMaxAccel = Math.abs(lCurrentAccel);
		}
		return this.mMaxAccel;
	}
	
	/**Publishes data about the Tilter subsystem to the SmartDashboard.**/
	public void publishData() {
		SmartDashboard.putNumber("TilterVoltage", this.mTilter.getMotorOutputVoltage());
		SmartDashboard.putNumber("TilterPosition", this.getPositionNu());
		SmartDashboard.putNumber("TilterVelocity", this.getVelocityNu());
		SmartDashboard.putNumber("TilterAcceleration", this.getAccelerationNu());
		SmartDashboard.putNumber("TilterMaxVel", this.getMaxVelNu());
		SmartDashboard.putNumber("TilterMaxAccel", this.getMaxAccelNu());
	}
	
	/**Disables the Tilter subsystem.**/
	public void disable() {
		this.mPrevTime = System.currentTimeMillis() /1000.;
		this.mPrevVel = 0;
		this.mPrevAccel = 0;
		
		this.mTilter.set(ControlMode.Disabled, 0);
		this.mIsDisabled = true;
	}
	
}