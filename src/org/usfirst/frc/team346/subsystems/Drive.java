package org.usfirst.frc.team346.subsystems;

import org.usfirst.frc.team346.robot.RobotMap;
import org.usfirst.frc.team346.subsystems.Subsystem;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Drive implements Subsystem{
	
	private TalonSRX mDriveLeftMaster, mDriveLeftSlave1, mDriveLeftSlave2;
	private TalonSRX mDriveRightMaster, mDriveRightSlave1, mDriveRightSlave2;
	
	private final int PID_VEL = 0, PID_POS = 1;
	private double mTurn;
	private double mSecondsFromNeutralToFull = 0.25;
	
	public enum DriveMode {
		PERCENT,
		VELOCITY,
		POSITION;
	}
	
	private static Drive sDriveInstance = new Drive();
	public static Drive getInstance() {
		return sDriveInstance;
	}
	
	protected Drive() {
		this.initTalons();
		this.initEncoders();
		this.initPIDs();
	}
	
	private void initTalons() {
		this.mDriveLeftMaster = new TalonSRX(RobotMap.kDriveLeftMasterPort);
		this.mDriveLeftMaster.set(ControlMode.PercentOutput, 0);
		this.mDriveLeftMaster.setNeutralMode(NeutralMode.Brake);
		this.mDriveLeftMaster.overrideLimitSwitchesEnable(true);
		this.mDriveLeftMaster.overrideSoftLimitsEnable(true);
		
		this.mDriveLeftSlave1 = new TalonSRX(RobotMap.kDriveLeftSlave1Port);
		this.mDriveLeftSlave1.set(ControlMode.Follower, RobotMap.kDriveLeftMasterPort);
		this.mDriveLeftSlave1.setNeutralMode(NeutralMode.Brake);
		this.mDriveLeftSlave1.follow(mDriveLeftMaster);
		this.mDriveLeftSlave1.overrideLimitSwitchesEnable(true);
		this.mDriveLeftSlave1.overrideSoftLimitsEnable(true);
		
		this.mDriveLeftSlave2 = new TalonSRX(RobotMap.kDriveLeftSlave2Port);
		this.mDriveLeftSlave2.set(ControlMode.Follower, RobotMap.kDriveLeftMasterPort);
		this.mDriveLeftSlave2.setNeutralMode(NeutralMode.Brake);
		this.mDriveLeftSlave2.follow(mDriveLeftMaster);
		this.mDriveLeftSlave2.overrideLimitSwitchesEnable(true);
		this.mDriveLeftSlave2.overrideSoftLimitsEnable(true);
		
		this.mDriveRightMaster = new TalonSRX(RobotMap.kDriveRightMasterPort);
		this.mDriveRightMaster.set(ControlMode.PercentOutput, 0);
		this.mDriveRightMaster.setNeutralMode(NeutralMode.Brake);
		this.mDriveRightMaster.overrideLimitSwitchesEnable(true);
		this.mDriveRightMaster.overrideSoftLimitsEnable(true);
		
		this.mDriveRightSlave1 = new TalonSRX(RobotMap.kDriveRightSlave1Port);
		this.mDriveRightSlave1.set(ControlMode.Follower, RobotMap.kDriveRightMasterPort);
		this.mDriveRightSlave1.setNeutralMode(NeutralMode.Brake);
		this.mDriveRightSlave1.follow(mDriveRightMaster);
		this.mDriveRightSlave1.overrideLimitSwitchesEnable(true);
		this.mDriveRightSlave1.overrideSoftLimitsEnable(true);
		
		this.mDriveRightSlave2 = new TalonSRX(RobotMap.kDriveRightSlave2Port);
		this.mDriveRightSlave2.set(ControlMode.Follower, RobotMap.kDriveRightMasterPort);
		this.mDriveRightSlave2.setNeutralMode(NeutralMode.Brake);		
		this.mDriveRightSlave2.follow(mDriveRightMaster);
		this.mDriveRightSlave2.overrideLimitSwitchesEnable(true);
		this.mDriveRightSlave2.overrideSoftLimitsEnable(true);
		
		this.mDriveLeftMaster.configClosedloopRamp(this.mSecondsFromNeutralToFull, 0);
		this.mDriveRightMaster.configClosedloopRamp(this.mSecondsFromNeutralToFull, 0);
		this.mDriveLeftMaster.configOpenloopRamp(this.mSecondsFromNeutralToFull, 0);
		this.mDriveRightMaster.configOpenloopRamp(this.mSecondsFromNeutralToFull, 0);
	}
	
	/**Initializes the encoders on the master CANTalons.**/
	private void initEncoders() {
		this.mDriveLeftMaster.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 5);
		
		this.mDriveRightMaster.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 5);
	}
	
	/**Initializes the PID values on the master encoders.**/
	private void initPIDs() {
		this.mDriveLeftMaster.config_kP(PID_VEL, RobotMap.kDriveVelLeftP, 0);
		this.mDriveLeftMaster.config_kI(PID_VEL, RobotMap.kDriveVelLeftI, 0);
		this.mDriveLeftMaster.config_kD(PID_VEL, RobotMap.kDriveVelLeftD, 0);
		this.mDriveLeftMaster.config_kF(PID_VEL, RobotMap.kDriveVelLeftF, 0);
		
		this.mDriveRightMaster.config_kP(PID_VEL, RobotMap.kDriveVelRightP, 0);
		this.mDriveRightMaster.config_kI(PID_VEL, RobotMap.kDriveVelRightI, 0);
		this.mDriveRightMaster.config_kD(PID_VEL, RobotMap.kDriveVelRightD, 0);
		this.mDriveRightMaster.config_kF(PID_VEL, RobotMap.kDriveVelRightF, 0);
	}
	
	public void drive(DriveMode _mode, double _left, double _right) {
		switch(_mode) {
			case PERCENT : {
				this.mDriveLeftMaster.set(ControlMode.PercentOutput, _left);
				this.mDriveRightMaster.set(ControlMode.PercentOutput, -(_right));
			}; break;
		
			case VELOCITY: {
				this.setLeftVelocityRPM(_left);
				this.setRightVelocityRPM(_right);
			}; break;
			
			case POSITION : {
				this.mDriveLeftMaster.set(ControlMode.Position, _left);
				this.mDriveRightMaster.set(ControlMode.Position, -(_right));
			}; break;
		
			default : {
				System.out.println("Drive command unsuccessful: invalid drive mode specified.");
			}; break;
		}
	}
	
	public void driveThrottleTurn(double _throttle, double _turn) {
		this.mTurn = (Math.abs(_turn) <= 0.08)? 0. : _turn*RobotMap.kThrottleTurnRotationStrength;
		
		this.mDriveLeftMaster.set(ControlMode.PercentOutput, _throttle + this.mTurn);
		this.mDriveRightMaster.set(ControlMode.PercentOutput, -(_throttle) + this.mTurn);
	}
	
	public void driveDifferential(DriveMode _mode, double _left, double _right) {
		if(_mode == DriveMode.VELOCITY) {
			_left /= 1200.;
			_right /= 1200.;
		}
		
		int lNotchCount = 3;
		if(_left >= 0) {
			_left = (double)((int)((_left * lNotchCount) + 0.5))/lNotchCount;
		}
		else if(_left < 0) {
			_left = (double)((int)((_left * lNotchCount) - 0.5))/lNotchCount;
		}
		
		if(_right >= 0) {
			_right = (double)((int)((_right * lNotchCount) + 0.5))/lNotchCount;
		}
		else if(_right < 0) {
			_right = (double)((int)((_right * lNotchCount) - 0.5))/lNotchCount;
		}
		
		if(_mode == DriveMode.VELOCITY) {
			_left *= 1200.;
			_right *= 1200.;
		}
		
		switch(_mode) {
			case PERCENT : {
				this.mDriveLeftMaster.set(ControlMode.PercentOutput, _left);
				this.mDriveRightMaster.set(ControlMode.PercentOutput, -(_right));
			}; break;
		
			case VELOCITY: {
				this.mDriveLeftMaster.set(ControlMode.Velocity, _left *1024./60./10.);
				this.mDriveRightMaster.set(ControlMode.Velocity, -(_right) *1024./60./10.);
			}; break;
			
			default : {
				System.out.println("Drive command unsuccessful: invalid drive mode specified.");
			}; break;
		}
	}
	
	public void setLeftVelocityRPM(double _rpm) {
		int lSetpoint = (int)(_rpm *1024./60./10.);
		this.mDriveLeftMaster.set(ControlMode.Velocity, lSetpoint);
	}
	
	public void setRightVelocityRPM(double _rpm) {
		int lSetpoint = (int)(_rpm *-1024./60./10.);
		this.mDriveRightMaster.set(ControlMode.Velocity, lSetpoint);
	}
	
	public void publishData() {
		this.publishVoltage();
//		this.publishPercent();
		this.publishVelocity();
		this.publishPosition();
	}
	
	public void publishVoltage() {
		SmartDashboard.putNumber("DriveLeftVoltage", this.getLeftVoltage());
		SmartDashboard.putNumber("DriveRightVoltage", this.getRightVoltage());
	}
	
	public void publishPercent() {
		SmartDashboard.putNumber("DriveLeftPercent", this.getLeftPercent());
		SmartDashboard.putNumber("DriveRightPercent", this.getRightPercent());
	}
	
	public void publishVelocity() {
		SmartDashboard.putNumber("DriveLeftVelocity", this.getLeftVelocity());
		SmartDashboard.putNumber("DriveRightVelocity", this.getRightVelocity());
	}
	
	public void publishPosition() {
		SmartDashboard.putNumber("DriveLeftPosition", this.getLeftPosition());
		SmartDashboard.putNumber("DriveRightPosition", this.getRightPosition());
	}
	
	public double getLeftVoltage() {
		return (this.mDriveLeftMaster.getMotorOutputVoltage() +
				this.mDriveLeftSlave1.getMotorOutputVoltage() +
				this.mDriveLeftSlave2.getMotorOutputVoltage()) /3.;
	}
	
	public double getRightVoltage() {
		return (this.mDriveRightMaster.getMotorOutputVoltage() +
				this.mDriveRightSlave1.getMotorOutputVoltage() +
				this.mDriveRightSlave2.getMotorOutputVoltage()) /-3.;
	}
	
	public double getLeftPercent() {
		return this.mDriveLeftMaster.getMotorOutputPercent();
	}
	
	public double getRightPercent() {
		return this.mDriveRightMaster.getMotorOutputPercent() *-1.;
	}
	
	public double getLeftVelocity() {
		return this.mDriveLeftMaster.getSelectedSensorVelocity(0) /1024.*60.*10.;
	}
	
	public double getRightVelocity() {
		return this.mDriveRightMaster.getSelectedSensorVelocity(0) /-1024.*60.*10.;
	}
	
	public double getLeftPosition() {
		return this.mDriveLeftMaster.getSelectedSensorPosition(0) /1024.;
	}
	
	public double getRightPosition() {
		return this.mDriveRightMaster.getSelectedSensorPosition(0) /-1024.;
	}
	
	public void setNominal(double _limit){
		this.mDriveLeftMaster.configNominalOutputForward(_limit, 0);
		this.mDriveLeftMaster.configNominalOutputReverse(_limit, 0);
		this.mDriveRightMaster.configNominalOutputForward(_limit, 0);
		this.mDriveRightMaster.configNominalOutputReverse(_limit, 0);
	}
	
	public void zeroEncoders() {
		this.mDriveLeftMaster.setSelectedSensorPosition(0, 0, 0);
		this.mDriveLeftSlave1.setSelectedSensorPosition(0, 0, 0);
		this.mDriveLeftSlave2.setSelectedSensorPosition(0, 0, 0);
		this.mDriveRightMaster.setSelectedSensorPosition(0, 0, 0);
		this.mDriveRightSlave1.setSelectedSensorPosition(0, 0, 0);
		this.mDriveRightSlave2.setSelectedSensorPosition(0, 0, 0);
		
		this.setNominal(0);
	}
	
	public void disable() {
		this.mDriveLeftMaster.set(ControlMode.Disabled, 0);
		this.mDriveLeftSlave1.set(ControlMode.Disabled, 0);
		this.mDriveLeftSlave2.set(ControlMode.Disabled, 0);
		this.mDriveRightMaster.set(ControlMode.Disabled, 0);
		this.mDriveRightSlave1.set(ControlMode.Disabled, 0);
		this.mDriveRightSlave2.set(ControlMode.Disabled, 0);
		System.out.println("Drive train disabled.");
	}
	
}