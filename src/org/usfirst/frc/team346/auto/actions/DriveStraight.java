package org.usfirst.frc.team346.auto.actions;

import org.usfirst.frc.team346.robot.RobotMap;
import org.usfirst.frc.team346.subsystems.Drive;
import org.usfirst.frc.team346.subsystems.Drive.DriveMode;
import org.usfirst.frc.team346.subsystems.Gyro;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class DriveStraight{

	private double distance,angleSetpoint,percentSpeed,timeOutTime,tolerance;
	
	private Drive sDrive;
	private Gyro sGyro;
	
	private DriverStation driverStation = DriverStation.getInstance();
	
	private PIDSource leftSource,rightSource;
	private PIDOutput rightOutput,leftOutput;
	private PIDController leftDistancePID,rightDistancePID;
	
	private double angleDifference;
	private double leftSpeed,rightSpeed;
	private double angleKP = 25;
	
	/**Use DriveFollow instead.**/
	@Deprecated
	public DriveStraight(double _distance, double _percentSpeed, double _timeOutTime, double _tolerance) {
		this.distance = _distance;
		this.percentSpeed = _percentSpeed;
		this.timeOutTime = _timeOutTime;
		this.tolerance = _tolerance;
		
		sDrive = Drive.getInstance();
		sGyro = Gyro.getInstance();
		
		this.angleSetpoint = this.sGyro.getAngle();
		
		this.createPID();
		this.enablePID();
		
//		this.runPID();
	}
	
	public void createPID() {

		this.leftSource = new PIDSource() {		
			@Override
			public void setPIDSourceType(PIDSourceType pidSource) {
			}
			@Override
			public double pidGet() {
				return sDrive.getLeftPositionFt();
			}
			@Override
			public PIDSourceType getPIDSourceType() {
				return PIDSourceType.kDisplacement;
			}
		};
		
		this.rightSource = new PIDSource() {		
			@Override
			public void setPIDSourceType(PIDSourceType pidSource) {
			}
			@Override
			public double pidGet() {
				return sDrive.getRightPositionFt();
				
			}
			@Override
			public PIDSourceType getPIDSourceType() {
				return PIDSourceType.kDisplacement;
			}
		};
		
		this.leftOutput = new PIDOutput(){
			public void pidWrite(double _output){
				angleDifference = sGyro.getAngle() - angleSetpoint;
					leftSpeed = percentSpeed * ((RobotMap.kDriveVelAverage*_output) - (angleDifference * angleKP));
			}
		};
		
		this.rightOutput = new PIDOutput(){
			public void pidWrite(double _output){
				angleDifference = sGyro.getAngle() - angleSetpoint;
					rightSpeed = percentSpeed*(_output*RobotMap.kDriveVelAverage) + (angleDifference * angleKP);
			}
		};
		
		leftDistancePID = new PIDController(0.18, 0, 0, leftSource, leftOutput, 0.02);
		rightDistancePID = new PIDController(0.18, 0, 0, rightSource, rightOutput, 0.02);
	}

	public void enablePID() {
		this.leftDistancePID.enable();
		this.leftDistancePID.setSetpoint(distance);
		
		this.rightDistancePID.enable();
		this.rightDistancePID.setSetpoint(distance);

	}
	
	public void runPID() {
		long l_driveStartTime = System.currentTimeMillis();
		double l_thresholdStartTime = l_driveStartTime;
		boolean l_inThreshold = false;
		System.out.println("Driving distance speed: " + distance);
		System.out.println("P is " +  this.leftDistancePID.getP());
		while(System.currentTimeMillis() - l_driveStartTime < timeOutTime * 1000) {
			if(!driverStation.isAutonomous()) {
				leftDistancePID.disable();
				rightDistancePID.disable();
				
				sDrive.drive(DriveMode.PERCENT, 0, 0);
				
				System.out.println("Thread Killed");
				System.out.println(sDrive.getLeftPosition() + "|" + sDrive.getRightPosition());
				this.sDrive.disable();;
				return;
			}
			
			if(Math.abs(((Math.abs(sDrive.getLeftPosition())+Math.abs(sDrive.getRightPosition()))/2) - Math.abs(distance)) < tolerance) {
				if(!l_inThreshold) {
					l_thresholdStartTime = System.currentTimeMillis();
					l_inThreshold = true;
				}
				else if(System.currentTimeMillis() - l_thresholdStartTime >= 500) {
					if(Math.abs((Math.abs(sDrive.getLeftPosition())+Math.abs(sDrive.getRightPosition()))/2 - Math.abs(distance)) < tolerance) {
						leftDistancePID.disable();
						rightDistancePID.disable();
						
						this.sDrive.drive(DriveMode.VELOCITY, 0, 0);
						
						System.out.println("Driving Distance (Speed) Complete via Threshold");
						System.out.println(sDrive.getLeftPositionFt() + "|" + sDrive.getRightPositionFt());
						this.sDrive.disable();;
						return;
					}
					else {
						l_inThreshold = false;
					}
				}
			}
			
			this.sDrive.drive(DriveMode.VELOCITY, leftSpeed, rightSpeed);
//			this.publishData();
		}
		System.out.println("Drive completed via timeout");
		System.out.println("Drove : " + sDrive.getLeftPositionFt() + "|" + sDrive.getRightPositionFt());
		this.sDrive.drive(DriveMode.PERCENT, 0, 0);
		
		this.disablePID();
	}
	
	public void setLeftPID(double _P, double _I, double _D) {
		this.leftDistancePID.setPID(_P, _I, _D);
	}
	
	public void setRightPID(double _P, double _I, double _D) {
		this.rightDistancePID.setPID(_P, _I, _D);
	}
	
	public void printDistance() {
		System.out.println("Drove : " + sDrive.getLeftPositionFt() + "|" + sDrive.getRightPositionFt());
	}
	
	public void disablePID() {
		this.leftDistancePID.disable();
		this.rightDistancePID.disable();
	}
}