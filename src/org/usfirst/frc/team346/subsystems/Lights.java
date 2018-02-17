package org.usfirst.frc.team346.subsystems;

import edu.wpi.first.wpilibj.Solenoid;

public class Lights implements Subsystem{

	private Solenoid led1;
	private Solenoid led2;	
	private Solenoid led3;	
	
	public static Lights ledInstance = new Lights();

	public static Lights getInstance() {
		return ledInstance;
	}
	
	public Lights() {
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
