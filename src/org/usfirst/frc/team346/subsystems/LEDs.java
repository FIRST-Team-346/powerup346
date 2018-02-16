package org.usfirst.frc.team346.subsystems;

import edu.wpi.first.wpilibj.Solenoid;

public class LEDs implements Subsystem{

	private Solenoid led1;
	private Solenoid led2;	
	private Solenoid led3;	
	
	public static LEDs ledInstance = new LEDs();

	public static LEDs getInstance() {
		return ledInstance;
	}
	
	public LEDs() {
		this.led1 = new Solenoid(0,1);
		this.led2 = new Solenoid(0,2);
		this.led3 = new Solenoid(0,3);
	}
	
	public void on() {
		this.led1.set(true);
		this.led2.set(true);
		this.led3.set(true);
	}
	
	public void disable() {
		
	}

	public void publishData() {
		// TODO Auto-generated method stub
		
	}

	
	
}
