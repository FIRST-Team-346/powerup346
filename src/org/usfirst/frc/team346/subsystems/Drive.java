package org.usfirst.frc.team346.subsystems;

import org.usfirst.frc.team346.robot.RobotMap;
import org.usfirst.frc.team346.subsystems.Subsystem;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Drive implements Subsystem{
	
	private TalonSRX mDriveLeftMaster, mDriveLeftSlave1, mDriveLeftSlave2;
	private TalonSRX mDriveRightMaster, mDriveRightSlave1, mDriveRightSlave2;
	
	public enum DriveMode {
		PERCENT,
		VELOCITY,
		POSITION;
	}
	
	private static Drive sDriveInstance = new Drive();
	protected Drive() {
		this.initialize();
	}
	
	public static Drive getInstance() {
		return sDriveInstance;
	}
	
	public void initialize() {
		this.initTalons();
		this.initEncoders();
	}
	
	private void initTalons() {
		this.mDriveLeftMaster = new TalonSRX(RobotMap.kDriveLeftMasterPort);
		this.mDriveLeftMaster.set(ControlMode.PercentOutput, 0);
		this.mDriveLeftMaster.setNeutralMode(NeutralMode.Brake);
		
		this.mDriveLeftSlave1 = new TalonSRX(RobotMap.kDriveLeftSlave1Port);
		this.mDriveLeftSlave1.set(ControlMode.Follower, 0);
		this.mDriveLeftSlave1.setNeutralMode(NeutralMode.Brake);
		this.mDriveLeftSlave1.follow(mDriveLeftMaster);
		
		this.mDriveLeftSlave2 = new TalonSRX(RobotMap.kDriveLeftSlave2Port);
		this.mDriveLeftSlave2.set(ControlMode.Follower, 0);
		this.mDriveLeftSlave2.setNeutralMode(NeutralMode.Brake);
		this.mDriveLeftSlave2.follow(mDriveLeftMaster);
		
		this.mDriveRightMaster = new TalonSRX(RobotMap.kDriveRightMasterPort);
		this.mDriveRightMaster.set(ControlMode.PercentOutput, 0);
		this.mDriveRightMaster.setNeutralMode(NeutralMode.Brake);
		
		this.mDriveRightSlave1 = new TalonSRX(RobotMap.kDriveRightSlave1Port);
		this.mDriveRightSlave1.set(ControlMode.Follower, 0);
		this.mDriveRightSlave1.setNeutralMode(NeutralMode.Brake);
		this.mDriveRightSlave1.follow(mDriveRightMaster);
		
		this.mDriveRightSlave2 = new TalonSRX(RobotMap.kDriveRightSlave2Port);
		this.mDriveRightSlave2.set(ControlMode.Follower, 0);
		this.mDriveRightSlave2.setNeutralMode(NeutralMode.Brake);		
		this.mDriveRightSlave2.follow(mDriveRightMaster);
	}
	
	/**Initializes the encoders on the master CANTalons.**/
	private void initEncoders() {
		this.mDriveLeftMaster.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		
		this.mDriveRightMaster.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
	}
	
	public void drive(DriveMode _mode, double _left, double _right) {
		switch(_mode) {
			case VELOCITY: {
				this.mDriveLeftMaster.set(ControlMode.Velocity, _left);
				this.mDriveRightMaster.set(ControlMode.Velocity, _right);
			}; break;
		
			case PERCENT : {
				this.mDriveLeftMaster.set(ControlMode.PercentOutput, _left);
				this.mDriveRightMaster.set(ControlMode.PercentOutput, _right);
			}; break;
			
			case POSITION : {
				this.mDriveLeftMaster.set(ControlMode.Position, _left);
				this.mDriveRightMaster.set(ControlMode.Position, _right);
			}; break;
		
			default : {
				System.out.println("Drive command unsuccessful: invalid drive mode specified.");
			}; break;
		}
	}
	
	public void publishData() {
		this.publishVoltage();
		this.publishVelocity();
		this.publishPosition();
	}
	
	public double getVoltage(Hand _hand) {
		if(_hand.equals(Hand.kLeft)) {
			return this.mDriveLeftMaster.getMotorOutputVoltage();
		}
		else if(_hand.equals(Hand.kRight)) {
			return this.mDriveRightMaster.getMotorOutputVoltage();
		}
		else return 0;
	}
	
	public double getPercent(Hand _hand) {
		if(_hand.equals(Hand.kLeft)) {
			return this.mDriveLeftMaster.getMotorOutputPercent();
		}
		else if(_hand.equals(Hand.kRight)) {
			return this.mDriveRightMaster.getMotorOutputPercent();
		}
		else return 0;
	}
	
	public double getPosition(Hand _hand) {
		if(_hand.equals(Hand.kLeft)) {
			return this.mDriveLeftMaster.getSelectedSensorPosition(0);
		}
		else if(_hand.equals(Hand.kRight)) {
			return this.mDriveRightMaster.getSelectedSensorPosition(0);
		}
		else return 0;
	}
	
	public double getVelocity(Hand _hand) {
		if(_hand.equals(Hand.kLeft)) {
			return this.mDriveLeftMaster.getSelectedSensorVelocity(0);
		}
		else if(_hand.equals(Hand.kRight)) {
			return this.mDriveRightMaster.getSelectedSensorVelocity(0);
		}
		else return 0;
	}
	
	public void publishVoltage() {
		SmartDashboard.putNumber("DriveLeftVoltage", this.getVoltage(Hand.kLeft));
		SmartDashboard.putNumber("DriveLeftVoltage", this.getVoltage(Hand.kLeft));
		
//		SmartDashboard.putNumber("DriveLeftPercent", this.getPercent(Hand.kLeft));
//		SmartDashboard.putNumber("DriveLeftPercent", this.getPercent(Hand.kLeft));
	}
	
	public void publishVelocity() {
		SmartDashboard.putNumber("DriveLeftVelocity", this.getVelocity(Hand.kLeft));
		SmartDashboard.putNumber("DriveRightVelocity", this.getVelocity(Hand.kRight));
	}
	
	public void publishPosition() {
		SmartDashboard.putNumber("DriveLeftPosition", this.getPosition(Hand.kLeft));
		SmartDashboard.putNumber("DriveRightPosition", this.getPosition(Hand.kRight));
	}
	
	public void zeroEncoders() {
		this.mDriveLeftMaster.setSelectedSensorPosition(0, 0, 0);
		this.mDriveLeftSlave1.setSelectedSensorPosition(0, 0, 0);
		this.mDriveLeftSlave2.setSelectedSensorPosition(0, 0, 0);
		this.mDriveRightMaster.setSelectedSensorPosition(0, 0, 0);
		this.mDriveRightSlave1.setSelectedSensorPosition(0, 0, 0);
		this.mDriveRightSlave2.setSelectedSensorPosition(0, 0, 0);
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