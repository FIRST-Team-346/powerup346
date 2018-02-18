package org.usfirst.frc.team346.subsystems;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.XboxController;

public class RGBLights {
	
	Solenoid r;	
	Solenoid b;
	Solenoid g;
	Solenoid p;
	
	public void waitTime(double _seconds) {
		long lInitialTime = System.currentTimeMillis();
		while(System.currentTimeMillis() - lInitialTime < Math.abs(_seconds) * 1000) {
		}
	}
	public RGBLights(int sol, int red, int blue, int green, int power) {
		r = new Solenoid(sol,red);
		b = new Solenoid(sol,blue);
		g = new Solenoid(sol,green);
		p = new Solenoid(sol,power);
	}
	
	Joystick xbx = new Joystick(0);
	public void ledInit() {
		r.set(false);
		g.set(false);
		b.set(false);
		p.set(true);
		
	}
//	public void ledChange () {
//		if(xbx.getRawButton(2)) {
//			b.set(true);
//		}else {
//			b.set(false);
//		}
//		if(xbx.getRawButton(3)) {
//			r.set(true);
//		}else {
//			r.set(false);
//		}
//		if(xbx.getRawButton(4)) {
//			g.set(true);
//		}else {
//			g.set(false);
//		}
//	}
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
}
