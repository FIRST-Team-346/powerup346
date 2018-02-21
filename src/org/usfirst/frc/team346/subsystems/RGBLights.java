package org.usfirst.frc.team346.subsystems;

import org.usfirst.frc.team346.robot.RobotMap;

import edu.wpi.first.wpilibj.Solenoid;

public class RGBLights implements Subsystem{
	
	Solenoid rL;	
	Solenoid bL;
	Solenoid gL;
	Solenoid pL;
	
	Solenoid rR;	
	Solenoid bR;
	Solenoid gR;
	Solenoid pR;
	
	public static RGBLights lightsInstance= new RGBLights();
	
	public static RGBLights getInstance() {
		return lightsInstance;
	}
	
	public RGBLights() {
		rL = new Solenoid(RobotMap.kLightLeftRedChannel);
		bL = new Solenoid(RobotMap.kLightLeftBlueChannel);
		gL = new Solenoid(RobotMap.kLightLeftGreenChannel);
		pL = new Solenoid(RobotMap.kLightLeftPositive);
		rR = new Solenoid(RobotMap.kLightRightRedChannel);
		bR = new Solenoid(RobotMap.kLightRightBlueChannel);
		gR = new Solenoid(RobotMap.kLightRightGreenChannel);
		pR = new Solenoid(RobotMap.kLightRightPositive);
	}
	
	public void ledInit() {
		rL.set(false);
		gL.set(false);
		bL.set(false);
		pL.set(true);
		
		rR.set(false);
		gR.set(false);
		bR.set(false);
		pR.set(true);
		
	}
	
	public void epilepsy() {
		rL.set(Math.random() < 0.5);
		gL.set(Math.random() < 0.5);
		bL.set(Math.random() < 0.5);
		
		rR.set(Math.random() < 0.5);
		gR.set(Math.random() < 0.5);
		bR.set(Math.random() < 0.5);
	}
	public void red() {
		rL.set(true);
		gL.set(false);
		bL.set(false);
		rR.set(true);
		gR.set(false);
		bR.set(false);
	}
	public void blue() {
		rL.set(false);
		gL.set(false);
		bL.set(true);
		rR.set(false);
		gR.set(false);
		bR.set(true);
	}
	public void yellow() {
		rL.set(true);
		gL.set(true);
		bL.set(false);
		rR.set(true);
		gR.set(true);
		bR.set(false);
	}
	public void white() {
		rL.set(true);
		gL.set(true);
		bL.set(true);
		
		rR.set(true);
		gR.set(true);
		bR.set(true);
	}
	public void purple() {
		rL.set(true);
		gL.set(false);
		bL.set(true);
		
		rR.set(true);
		gR.set(false);
		bR.set(true);
	}
	public void black() {
		rL.set(false);
		gL.set(false);
		bL.set(false);
		
		rR.set(false);
		gR.set(false);
		bR.set(false);
	}
	public void green() {
		rL.set(false);
		gL.set(true);
		bL.set(false);
		
		rR.set(false);
		gR.set(true);
		bR.set(false);
	}
	public void lightBlue() {
		rL.set(false);
		gL.set(true);
		bL.set(true);
		
		rR.set(false);
		gR.set(true);
		bR.set(true);
	}
	public void off() {
		rL.set(false);
		gL.set(false);
		bL.set(false);
		
		rR.set(false);
		gR.set(false);
		bR.set(false);
	}
	
	@Override
	public void disable() {
		rR.set(false);
		gR.set(false);
		bR.set(false);
		
	}
	@Override
	public void publishData() {
		
	}
}
