package org.usfirst.frc.team346.subsystems;

import org.usfirst.frc.team346.robot.RobotMap;

import edu.wpi.first.wpilibj.Solenoid;

public class RGBLights implements Subsystem{
	
	Solenoid r;	
	Solenoid b;
	Solenoid g;
	Solenoid p;
	
	public static RGBLights lightsInstance= new RGBLights();
	
	public RGBLights getInstance() {
		return this.lightsInstance;
	}
	
	
	public RGBLights() {
		r = new Solenoid(RobotMap.kPCMPort,RobotMap.kLightRedChannel);
		b = new Solenoid(RobotMap.kPCMPort,RobotMap.kLightBlueChannel);
		g = new Solenoid(RobotMap.kPCMPort,RobotMap.kLightGreenChannel);
		p = new Solenoid(RobotMap.kPCMPort,RobotMap.kLightPowerChannel);
	}
	
	public void ledInit() {
		r.set(false);
		g.set(false);
		b.set(false);
		p.set(true);
		
	}
	public void epilepsy() {
		r.set(Math.random() < 0.5);
		g.set(Math.random() < 0.5);
		b.set(Math.random() < 0.5);
	}
	public void red() {
		r.set(true);
		g.set(false);
		b.set(false);
	}
	public void blue() {
		r.set(false);
		g.set(false);
		b.set(true);
	}
	public void yellow() {
		r.set(true);
		g.set(true);
		b.set(false);
	}
	public void white() {
		r.set(true);
		g.set(true);
		b.set(true);
	}
	public void purple() {
		r.set(true);
		g.set(false);
		b.set(true);
	}
	public void black() {
		r.set(false);
		g.set(false);
		b.set(false);
	}
	public void green() {
		r.set(false);
		g.set(true);
		b.set(false);
	}
	public void lightBlue() {
		r.set(false);
		g.set(true);
		b.set(true);
	}
	
	@Override
	public void disable() {
		r.set(false);
		g.set(false);
		b.set(false);
		
	}
	@Override
	public void publishData() {
		
	}
}
