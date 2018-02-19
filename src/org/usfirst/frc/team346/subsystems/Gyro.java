package org.usfirst.frc.team346.subsystems;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Gyro implements Subsystem {

//	public ADXRS450_Gyro mGyroscope;
	private AnalogGyro mGyroscope;
	
	private long mLastGyroTime;
	
	private static Gyro sGyroInstance = new Gyro();
	public static Gyro getInstance() {
		return sGyroInstance;
	}
	protected Gyro() {
		this.initialize();
	}
	
	public void initialize() {
//		this.mGyroscope = new ADXRS450_Gyro();
		this.mGyroscope = new AnalogGyro(0);
		this.mGyroscope.calibrate();
		this.mLastGyroTime = System.currentTimeMillis();
	}
	
	public double getAngle() {
		return this.mGyroscope.getAngle();
		//TODO fix angle between 0 and 360 or -180 and 180
	}
	
	public void zeroGyro() {
		this.mGyroscope.reset();
	}
	
	public void calibrate() {
		this.mGyroscope.calibrate();
	}
	
	/**Prints gyroscope angle periodically, 0.5s to avoid greater drift.**/
	public void publishData() {
		if(System.currentTimeMillis() - this.mLastGyroTime >= 250) {
			SmartDashboard.putNumber("Gyroscope Angle", this.getAngle());
			this.mLastGyroTime = System.currentTimeMillis();
		}
	}

	public void disable() {
		this.mGyroscope.free();
	}

}