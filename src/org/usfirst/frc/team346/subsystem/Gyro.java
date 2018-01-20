package org.usfirst.frc.team346.subsystem;


import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Gyro{

	public static Gyro gyroInstance = new Gyro();
	
	private ADXRS450_Gyro gyroscope;
	
	private long lastGyroTime;
	
	public Gyro() {
		this.gyroscope = new ADXRS450_Gyro(SPI.Port.kOnboardCS0);
		this.gyroscope.calibrate();		
	}
	
	public static Gyro getInstance() {
		return gyroInstance;
	}
	
	public double getAngle() {
		return this.gyroscope.getAngle();
	}
	
	public void zeroGyro() {
		this.gyroscope.reset();
	}
	
	public void publishData() {
		//Only prints Gyroscope angle every 0.5 seconds to avoid angle drifting
		if(System.currentTimeMillis() - this.lastGyroTime >= 100) {
			SmartDashboard.putNumber("Gyroscope Angle", this.getAngle());
			this.lastGyroTime = System.currentTimeMillis();
		}
	}


}