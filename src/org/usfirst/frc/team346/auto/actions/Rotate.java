package org.usfirst.frc.team346.auto.actions;

import org.usfirst.frc.team346.robot.RobotMap;
import org.usfirst.frc.team346.subsystems.Drive;
import org.usfirst.frc.team346.subsystems.Gyro;
import org.usfirst.frc.team346.subsystems.Drive.DriveMode;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Rotate {

	private double angleSetpoint, percentSpeed, timeOutTime, tolerance;
	
	private Drive drive;
	private Gyro gyro;
	private Preferences pref = Preferences.getInstance();
	
	private PIDSource angleSource;
	private PIDOutput angleOutput;
	private PIDController anglePID;
	
	private double leftSideEnabled = 1, rightSideEnabled = 1;
	private final double minPercent = 0.225;
	
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
		
		this.gyro.zeroGyro();
		this.createPID();
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
					SmartDashboard.putNumber("anglePIDOutput", _output);
//					drive.drive(DriveMode.PERCENTVElOCITY, _output * percentSpeed, _output * percentSpeed);
					drive.drive(DriveMode.PERCENT, _output*leftSideEnabled*percentSpeed, -_output*rightSideEnabled*percentSpeed);
//					System.out.println("output" + (-1200. * _output * percentSpeed) + "," + (-1200. * _output * percentSpeed));
				}
			}
		};
		this.anglePID = new PIDController(this.pref.getDouble("rotateP", 0), RobotMap.kRotateI, this.pref.getDouble("rotateD", 0), this.angleSource, this.angleOutput, 0.02);
//		this.anglePID = new PIDController(RobotMap.kRotateP, RobotMap.kRotateI, RobotMap.kRotateD, this.angleSource, this.angleOutput, 0.02);
	}
	
	private void runPID() {
		long l_driveStartTime = System.currentTimeMillis();
		double l_thresholdStartTime = l_driveStartTime;
		boolean l_inThreshold = false;
		System.out.println("Rotating to: " + angleSetpoint);
		while(System.currentTimeMillis() - l_driveStartTime < timeOutTime * 500) {
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
				if(!l_inThreshold) {
					l_thresholdStartTime = System.currentTimeMillis();
					l_inThreshold = true;

				}
				else if(System.currentTimeMillis() - l_thresholdStartTime >= 1000) {
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
		
			
			this.anglePID.enable();
		}
		this.disablePID();

		
		System.out.println("Rotate completed via timeout");
		System.out.println("Current angle: " + this.gyro.getAngle());
		System.out.println("");
	}
	
	public void setPID(double _P, double _I, double _D) {
		this.anglePID.setPID(_P, _I, _D);
		System.out.println(this.anglePID.getP());
	}
	
	public void setLeftSide(double _leftSideScale) {
		this.leftSideEnabled = _leftSideScale;		
	}
	
	public void setRightSide(double _rightSideScale) {
		this.rightSideEnabled = _rightSideScale;
	}
	
	
	public void disablePID(){
		this.anglePID.disable();
	}
	
	public void PublishData() {
		this.drive.publishPercent();
	}
	
}