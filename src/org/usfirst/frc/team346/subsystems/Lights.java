package org.usfirst.frc.team346.subsystems;

import org.usfirst.frc.team346.robot.RobotMap;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Lights implements Subsystem{
	
	Solenoid leftPos;
	Solenoid leftRed;
	Solenoid leftGreen;
	Solenoid leftBlue;
	
	Solenoid rightPos;
	Solenoid rightRed;
	Solenoid rightGreen;
	Solenoid rightBlue;
	
	public static Lights lightsInstance = new Lights();
	
	public static Lights getInstance() {
		return lightsInstance;
	}
	
	public Lights() {
		leftPos = new Solenoid(RobotMap.kLightLeftPositive);
		leftRed = new Solenoid(RobotMap.kLightLeftRedChannel);
		leftGreen = new Solenoid(RobotMap.kLightLeftGreenChannel);
		leftBlue = new Solenoid(RobotMap.kLightLeftBlueChannel);
		
		rightPos = new Solenoid(RobotMap.kLightRightPositive);
		rightRed = new Solenoid(RobotMap.kLightRightRedChannel);
		rightGreen = new Solenoid(RobotMap.kLightRightGreenChannel);
		rightBlue = new Solenoid(RobotMap.kLightRightBlueChannel);
		
		this.setBlack();
		leftPos.set(true);
		rightPos.set(true);
	}
	
	public void setRandom() {
		leftRed.set(Math.random() < 0.5);
		leftGreen.set(Math.random() < 0.5);
		leftBlue.set(Math.random() < 0.5);
		
		rightRed.set(Math.random() < 0.5);
		rightGreen.set(Math.random() < 0.5);
		rightBlue.set(Math.random() < 0.5);
	}
	
	public void setLeftBlueRightRed(boolean _invert) {
		if(!_invert) {
			leftRed.set(false);
			leftGreen.set(false);
			leftBlue.set(true);
			
			rightRed.set(true);
			rightGreen.set(false);
			rightBlue.set(false);
		}
		else {
			leftRed.set(true);
			leftGreen.set(false);
			leftBlue.set(false);
			
			rightRed.set(false);
			rightGreen.set(false);
			rightBlue.set(true);
		}
	}
	
	public void setLeftGreenRightRed(boolean _invert) {
		if(!_invert) {
			leftRed.set(false);
			leftGreen.set(true);
			leftBlue.set(false);
			
			rightRed.set(true);
			rightGreen.set(false);
			rightBlue.set(false);
		}
		else {
			leftRed.set(true);
			leftGreen.set(false);
			leftBlue.set(false);
			
			rightRed.set(false);
			rightGreen.set(true);
			rightBlue.set(false);
		}
	}
	
	public void setRed() {
		leftRed.set(true);
		leftGreen.set(false);
		leftBlue.set(false);
		
		rightRed.set(true);
		rightGreen.set(false);
		rightBlue.set(false);
	}
	
	public void setYellow() {
		leftRed.set(true);
		leftGreen.set(true);
		leftBlue.set(false);
		
		rightRed.set(true);
		rightGreen.set(true);
		rightBlue.set(false);
	}
	
	public void setGreen() {
		leftRed.set(false);
		leftGreen.set(true);
		leftBlue.set(false);
		
		rightRed.set(false);
		rightGreen.set(true);
		rightBlue.set(false);
	}
	
	public void setCyan() {
		leftRed.set(false);
		leftGreen.set(true);
		leftBlue.set(true);
		
		rightRed.set(false);
		rightGreen.set(true);
		rightBlue.set(true);
	}
	
	public void setBlue() {
		leftRed.set(false);
		leftGreen.set(false);
		leftBlue.set(true);
		
		rightRed.set(false);
		rightGreen.set(false);
		rightBlue.set(true);
	}

	public void setPurple() {
		leftRed.set(true);
		leftGreen.set(false);
		leftBlue.set(true);
		
		rightRed.set(true);
		rightGreen.set(false);
		rightBlue.set(true);
	}
	
	public void setWhite() {
		leftRed.set(true);
		leftGreen.set(true);
		leftBlue.set(true);
		
		rightRed.set(true);
		rightGreen.set(true);
		rightBlue.set(true);
	}

	public void setBlack() {
		leftRed.set(false);
		leftGreen.set(false);
		leftBlue.set(false);
		
		rightRed.set(false);
		rightGreen.set(false);
		rightBlue.set(false);
	}
	
	@Override
	public void disable() {
		this.setBlack();
		this.leftPos.set(false);
		this.rightPos.set(false);
	}
	
	@Override
	public void publishData() {
	}
}
