package org.usfirst.frc.team346.subsystem;

import org.usfirst.frc.team346.subsystem.Subsystem;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.GenericHID.Hand;

public class Drive implements Subsystem{
	
	private static Drive driveInstance = new Drive();
	
	private DriveMode drivemode;

	private TalonSRX leftDriveMaster, leftDriveSlave1, leftDriveSlave2;
	private TalonSRX rightDriveMaster, rightDriveSlave1, rightDriveSlave2;
	
	public enum DriveMode {
		PERCENT,
		VELOCITY,
		POSITION;
	}
	
	protected Drive() {
		this.initialize();
	}
	
	public static Drive getInstance() {
		return driveInstance;
	}
	
	public void initialize() {
		this.leftDriveMaster = new TalonSRX(1);
		this.leftDriveMaster.set(ControlMode.PercentOutput, 0);
		this.leftDriveMaster.setNeutralMode(NeutralMode.Brake);
		
		this.leftDriveSlave1 = new TalonSRX(2);
		this.leftDriveSlave1.set(ControlMode.Follower, 1);
		this.leftDriveSlave1.setNeutralMode(NeutralMode.Brake);
		this.leftDriveSlave1.follow(leftDriveMaster);
		
		this.leftDriveSlave2 = new TalonSRX(3);
		this.leftDriveSlave2.set(ControlMode.Follower, 1);
		this.leftDriveSlave2.setNeutralMode(NeutralMode.Brake);
		this.leftDriveSlave2.follow(leftDriveMaster);
		
		this.rightDriveMaster = new TalonSRX(4);
		this.rightDriveMaster.set(ControlMode.PercentOutput, 0);
		this.rightDriveMaster.setNeutralMode(NeutralMode.Brake);
		
		this.rightDriveSlave1 = new TalonSRX(5);
		this.rightDriveSlave1.set(ControlMode.Follower, 4);
		this.rightDriveSlave1.setNeutralMode(NeutralMode.Brake);
		this.rightDriveSlave1.follow(rightDriveMaster);
		
		this.rightDriveSlave2 = new TalonSRX(6);
		this.rightDriveSlave2.set(ControlMode.Follower, 4);
		this.rightDriveSlave2.setNeutralMode(NeutralMode.Brake);		
		this.rightDriveSlave2.follow(rightDriveMaster);
	}
	
	/**
	 * Initializes the encoders on the master CANTalons.
	 */
	private void initEncoders() {
		// Left drive master encoder configurations
		this.leftDriveMaster.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
//		this.leftDriveMaster.
//		this.leftDriveMaster.reverseSensor(false);
		
		// Right drive master encoder configurations
		this.rightDriveMaster.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
//		this.rightDriveMaster.reverseOutput(true);
//		this.rightDriveMaster.reverseSensor(true);
	}
	
	public void setMode(DriveMode _mode) {
		this.drivemode = _mode;
	}
	
	public void drive(double _leftSpeed, double _rightSpeed) {
		switch(drivemode) {
			case VELOCITY: {
				
				this.leftDriveMaster.set(ControlMode.PercentOutput, _leftSpeed);
				this.rightDriveMaster.set(ControlMode.PercentOutput, _rightSpeed);
				
			}; break;
		
			case PERCENT : {
			
				this.leftDriveMaster.set(ControlMode.PercentOutput, _leftSpeed);
				this.rightDriveMaster.set(ControlMode.PercentOutput, _rightSpeed);
				
			}; break;
		
			default : break;
		}
	}
	
	public double getPosition(Hand _hand) {
		if(_hand.equals(Hand.kLeft)) {
			return this.leftDriveMaster.getSelectedSensorPosition(0);
		}
		else if(_hand.equals(Hand.kRight)) {
			return this.rightDriveMaster.getSelectedSensorPosition(0);
		}
		else return 0;
	}
	
	public double getVelocity(Hand _hand) {
		if(_hand.equals(Hand.kLeft)) {
			return this.leftDriveMaster.getSelectedSensorVelocity(0);
		}
		else if(_hand.equals(Hand.kRight)) {
			return this.rightDriveMaster.getSelectedSensorVelocity(0);
		}
		else return 0;
	}
	
	public void disable() {
		this.leftDriveMaster.set(ControlMode.Disabled, 0);
		this.rightDriveSlave1.set(ControlMode.Disabled, 0);
	}
	
	public void PublishData() {
		System.out.println(this.leftDriveMaster.getMotorOutputVoltage());
		System.out.println(this.rightDriveMaster.getMotorOutputVoltage());
	}
	
}
