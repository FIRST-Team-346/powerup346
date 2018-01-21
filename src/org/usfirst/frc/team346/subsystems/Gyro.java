package org.usfirst.frc.team346.subsystems;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Gyro implements Subsystem {

	private ADXRS450_Gyro mGyroscope;
	
	private long mLastGyroTime;
	
	private static Gyro sGyroInstance = new Gyro();
	protected Gyro() {
		this.mGyroscope = new ADXRS450_Gyro(SPI.Port.kOnboardCS0);
		this.mGyroscope.calibrate();
	}
	
	public static Gyro getInstance() {
		return sGyroInstance;
	}
	
	public double getAngle() {
		return this.mGyroscope.getAngle();
	}
	
	public void zeroGyro() {
		this.mGyroscope.reset();
	}
	
	/**Prints gyroscope angle periodically, 0.5s to avoid greater drift.**/
	public void publishData() {
		if(System.currentTimeMillis() - this.mLastGyroTime >= 500) {
			SmartDashboard.putNumber("Gyroscope Angle", this.getAngle());
			this.mLastGyroTime = System.currentTimeMillis();
		}
	}

	public void disable() {
		this.mGyroscope.free();
	}

}