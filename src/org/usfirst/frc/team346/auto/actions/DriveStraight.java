package org.usfirst.frc.team346.auto.actions;

import org.usfirst.frc.team346.subsystems.Drive;
import org.usfirst.frc.team346.subsystems.Gyro;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.GenericHID.Hand;

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
	private double angleKP;
	
	public DriveStraight(double distance, double _percentSpeed, double _angle, double _timeOutTime, double _tolerance) {
		this.distance = distance;
		this.angleSetpoint = _angle;
		this.percentSpeed = _percentSpeed;
		this.timeOutTime = _timeOutTime;
		this.tolerance = _tolerance;
		
		sDrive = Drive.getInstance();
		sGyro = Gyro.getInstance();
		
		this.createPID();
		this.enablePID();
		
		this.runPID();
	}
	
	public void createPID() {

		this.leftSource = new PIDSource() {		
			@Override
			public void setPIDSourceType(PIDSourceType pidSource) {
			}
			@Override
			public double pidGet() {
				return sDrive.getPosition(Hand.kLeft);
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
				return sDrive.getPosition(Hand.kRight);
				
			}
			@Override
			public PIDSourceType getPIDSourceType() {
				return PIDSourceType.kDisplacement;
			}
		};
		
		this.leftOutput = new PIDOutput(){
			public void pidWrite(double _output){
				angleDifference = sGyro.getAngle() - angleSetpoint;
				if(distance >= 0) {
					leftSpeed = percentSpeed * ((1080*_output) - (angleDifference * angleKP));
				}
				else {
					leftSpeed = percentSpeed * ((1080*_output) + (angleDifference * angleKP));
				}
			}
		};
		
		this.rightOutput = new PIDOutput(){
			public void pidWrite(double _output){
				if(distance >= 0) {
					rightSpeed = percentSpeed * ((1080*_output) + (angleDifference * angleKP));
				}
				else {
					rightSpeed = percentSpeed * ((1080*_output) - (angleDifference * angleKP));
				}
			}
		};
		
		leftDistancePID = new PIDController(0, 0, 0, leftSource, leftOutput, 0.02);
		rightDistancePID = new PIDController(0, 0, 0, rightSource, rightOutput, 0.02);
	}

	public void enablePID() {
		this.leftDistancePID.enable();
		this.rightDistancePID.enable();
	}
	
	public void runPID() {
		long l_driveStartTime = System.currentTimeMillis();
		double l_thresholdStartTime = l_driveStartTime;
		boolean l_inThreshold = false;
		System.out.println("Driving distance speed: " + distance);
		while(System.currentTimeMillis() - l_driveStartTime < timeOutTime * 1000) {
			if(driverStation.isAutonomous()) {
				leftDistancePID.disable();
				rightDistancePID.disable();
				
				sDrive.drive(0, 0);
				
				System.out.println("Thread Killed");
				System.out.println(sDrive.getPosition(Hand.kLeft) + "|" + sDrive.getPosition(Hand.kRight));
				this.sDrive.disable();;
				return;
			}
			
			if(Math.abs((sDrive.getPosition(Hand.kLeft)+sDrive.getPosition(Hand.kRight))/2 - distance) < tolerance) {
				if(!l_inThreshold) {
					l_thresholdStartTime = System.currentTimeMillis();
					//TODO: Test if this helps/works
//					this.s_robot.s_drive.setGear(GearMode.SLOW_GEAR);
					l_inThreshold = true;
				}
				else if(System.currentTimeMillis() - l_thresholdStartTime >= timeOutTime) {
					if(Math.abs((sDrive.getPosition(Hand.kLeft) + sDrive.getPosition(Hand.kRight))/2 - distance) < tolerance) {
						leftDistancePID.disable();
						rightDistancePID.disable();
						
						this.sDrive.drive(0, 0);
						
						System.out.println("Driving Distance (Speed) Complete via Threshold");
						System.out.println(sDrive.getPosition(Hand.kLeft) + "|" + sDrive.getPosition(Hand.kRight));
						this.sDrive.disable();;
						return;
					}
					else {
						l_inThreshold = false;
					}
				}
			}
			
			this.sDrive.drive(leftSpeed, rightSpeed);
//			this.publishData();
		}
		
		this.disablePID();
	}
	
	public void disablePID() {
		this.leftDistancePID.disable();
		this.rightDistancePID.disable();
	}
}
