package org.usfirst.frc.team346.auto.actions;

import org.usfirst.frc.team346.robot.RobotMap;
import org.usfirst.frc.team346.subsystems.Drive;
import org.usfirst.frc.team346.subsystems.Gyro;
import org.usfirst.frc.team346.subsystems.Drive.DriveMode;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class RotateSingleSide {

	private double angleSetpoint, percentSpeed, timeOutTime, tolerance, updateFreq;
	private Hand side;
	
	private Drive drive;
	private Gyro gyro;
	
	private PIDSource angleSource;
	private PIDOutput angleOutput;
	private PIDController anglePID;
	
	private double leftEnabled, rightEnabled;
	private double leftSetPercent, rightSetPercent;
	private final double minPercent = 0.225;
	
	private DriverStation driverStation = DriverStation.getInstance();
	
	public RotateSingleSide() {
		drive = Drive.getInstance();
		gyro = Gyro.getInstance();	
		updateFreq = 0.02;
		
		this.leftEnabled = 1.;
		this.rightEnabled = 1.;
	}
	
	public void setSingleSide(Hand _side) {
		this.side = _side;
		this.leftEnabled = (this.side == Hand.kLeft) ? 1. : 0.65;
		this.rightEnabled = (this.side == Hand.kRight) ? 1. : 0.65;
	}
	
	public void rotate(double _angle, double _percentSpeed, double _timeOutTime, double _tolerance) {
		angleSetpoint = _angle;
		percentSpeed = _percentSpeed;
		timeOutTime  = _timeOutTime;
		tolerance = _tolerance;
		
		this.createPID();
		this.anglePID.setSetpoint(angleSetpoint);
		this.drive.zeroEncoders();
		this.drive.enable();
		this.drive.setSpeedPIDs();
		
		this.runPID();
	}
	
	private void createPID() {
		this.angleSource = new PIDSource() {		
			public void setPIDSourceType(PIDSourceType pidSource) {
			}
			public double pidGet() {
				return gyro.getAngle();
			}
			public PIDSourceType getPIDSourceType() {
				return PIDSourceType.kDisplacement;
			}
		};
		
		this.angleOutput = new PIDOutput() {
			public void pidWrite(double _output) {
				if(angleSetpoint == 0) {
					drive.drive(DriveMode.PERCENT, 0, 0);
				}
				else {
					SmartDashboard.putNumber("anglePIDOutput", _output);
					leftSetPercent = _output * percentSpeed * leftEnabled;
					rightSetPercent = -_output * percentSpeed * rightEnabled;
				}
			}
		};
		this.anglePID = new PIDController(RobotMap.kRotateP, RobotMap.kRotateI, RobotMap.kRotateD, this.angleSource, this.angleOutput, this.updateFreq);
	}
	
	private void runPID() {
		long l_driveStartTime = System.currentTimeMillis();
		double l_thresholdStartTime = l_driveStartTime;
		boolean l_inThreshold = false;
		System.out.println("Rotating to: " + angleSetpoint);
		
		while(System.currentTimeMillis() - l_driveStartTime < timeOutTime * 1000) {
			this.publishData();
			if(!driverStation.isAutonomous()) {
				anglePID.disable();
				
				drive.drive(DriveMode.VELOCITY, 0, 0);
				
				System.out.println("Thread Killed");
				System.out.println(drive.getLeftPosition() + "|" + drive.getRightPosition());
				this.drive.disable();
				return;
			}
			
			if(Math.abs(gyro.getAngle() - angleSetpoint) < tolerance) {
				this.drive.drive(DriveMode.PERCENT, 0, 0);
				if(!l_inThreshold) {
					l_thresholdStartTime = System.currentTimeMillis();
					l_inThreshold = true;
				}
				else if(System.currentTimeMillis() - l_thresholdStartTime >= 250) {
					if(Math.abs((gyro.getAngle() - angleSetpoint)) < tolerance) {
						anglePID.disable();
						
						System.out.println("in setpoint");
						this.drive.drive(DriveMode.PERCENT, 0, 0);
						
						System.out.println("Rotation to " + angleSetpoint + " Complete via Threshold");
						System.out.println(gyro.getAngle());
						this.drive.disable();
						return;
					}
					else {
						l_inThreshold = false;
					}
				}
			}
			else {
				if(Math.abs(this.leftSetPercent) < this.minPercent) {
					if(this.leftEnabled == 1) {
						this.leftSetPercent = this.minPercent * (this.leftSetPercent >= 0 ? 1. : -1.);
					}
				}
				if(Math.abs(this.rightSetPercent) < this.minPercent) {
					if(this.rightEnabled == 1) {
						this.rightSetPercent = this.minPercent * (this.rightSetPercent >= 0 ? 1. : -1.);
					}
				}
				this.drive.drive(DriveMode.PERCENT, this.leftSetPercent, this.rightSetPercent);
			}
			
			this.anglePID.enable();
		}
		this.disablePID();
		
		System.out.println("Rotate completed via timeout");
		System.out.println("Current angle: " + this.gyro.getAngle());
	}
	
	public void setPID(double _P, double _I, double _D) {
		this.anglePID.setPID(_P, _I, _D);
		System.out.println(this.anglePID.getP());
	}
	
	public void disablePID(){
		this.anglePID.disable();
	}
	
	public void publishData() {
		this.drive.publishPercent();
	}
	
}