package org.usfirst.frc.team346.auto.actions;

import org.usfirst.frc.team346.subsystems.Drive;
import org.usfirst.frc.team346.subsystems.Gyro;
import org.usfirst.frc.team346.subsystems.Drive.DriveMode;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.GenericHID.Hand;

public class Rotate {

	private double angleSetpoint, percentSpeed, timeOutTime, tolerance;
	
	private Drive drive;
	private Gyro gyro;
	
	private PIDSource angleSource;
	private PIDOutput angleOutput;
	private PIDController anglePID;
	
	private DriverStation driverStation = DriverStation.getInstance();
	
	public Rotate() {
		drive = Drive.getInstance();
		gyro = Gyro.getInstance();	
		this.createPID();

	}
	
	public void rotate(double _angle, double _percentSpeed, double _timeOutTime, double _tolerance) {
		angleSetpoint = _angle;
		percentSpeed = _percentSpeed;
		timeOutTime  = _timeOutTime;
		tolerance = _tolerance;
		
//		this.createPID();
		this.anglePID.setSetpoint(angleSetpoint);
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
//					drive.drive(DriveMode.PERCENTVElOCITY, _output * percentSpeed, _output * percentSpeed);
					drive.drive(DriveMode.PERCENT, _output, -_output);
//					System.out.println("output" + (-1200. * _output * percentSpeed) + "," + (-1200. * _output * percentSpeed));
				}
			}
		};
		this.anglePID = new PIDController(0.003,0,0.1,this.angleSource,this.angleOutput, 0.02);
	}
	
	private void runPID() {
		long l_driveStartTime = System.currentTimeMillis();
		double l_thresholdStartTime = l_driveStartTime;
		boolean l_inThreshold = false;
		System.out.println("Rotating to: " + angleSetpoint);
		this.drive.setNominal(0.13);//.33
		while(System.currentTimeMillis() - l_driveStartTime < timeOutTime * 1000) {
			this.PublishData();
			if(!driverStation.isAutonomous()) {
				anglePID.disable();
				
				drive.drive(DriveMode.VELOCITY, 0, 0);
				
				System.out.println("Thread Killed");
				System.out.println(drive.getLeftPosition() + "|" + drive.getRightPosition());
				this.drive.disable();
				return;
			}
			
			if(Math.abs(gyro.getAngle() - angleSetpoint) < tolerance) {
				this.drive.setNominal(0);
				if(!l_inThreshold) {
					l_thresholdStartTime = System.currentTimeMillis();
					l_inThreshold = true;
					this.drive.setNominal(0);
				}
				else if(System.currentTimeMillis() - l_thresholdStartTime >= 500) {
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
				this.drive.setNominal(0.13);
			}
			
			this.anglePID.enable();
		}
		this.disablePID();
		this.drive.setNominal(0);
		
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
	
	public void PublishData() {
		this.drive.publishPercent();
	}
}