package org.usfirst.frc.team346.subsystems;

import org.usfirst.frc.team346.robot.RobotMap;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Lights implements Subsystem {

	private Solenoid mRed;
	private Solenoid mGreen;
	private Solenoid mBlue;
	
	private final int mUpdateRateMillis = 250;
	private boolean mCurrentlyFlickeredOff;
	private double mPrevFlickerTime, mPrevRainbowTime;
	private int mRainbowCount;
	
	private Color mCurrentColor;
	
	private Color[] RAINBOW_ORDER = {Color.RED, Color.YELLOW, Color.GREEN, Color.CYAN, Color.BLUE, Color.MAGENTA,
												Color.BLUE, Color.CYAN, Color.GREEN, Color.YELLOW};
	public enum Color {
		OFF,
		WHITE,
		RED,
		YELLOW,
		GREEN,
		CYAN,
		BLUE,
		MAGENTA;
	}
	
	public enum ColorMode {
		RANDOM,
		FLICKER,
		RAINBOW;
	}
	
	private static Lights sLightsInstance = new Lights();
	public static Lights getInstance() {
		return sLightsInstance;
	}
	
	protected Lights() {
		this.mRed = new Solenoid(0, RobotMap.kLightRedChannel);
		this.mGreen = new Solenoid(0, RobotMap.kLightGreenChannel);
		this.mBlue = new Solenoid(0, RobotMap.kLightBlueChannel);
	}
	
	public void setColor(Color _color) {
		switch(_color) {
		case WHITE : {
			this.mRed.set(true);
			this.mGreen.set(true);
			this.mBlue.set(true);
			this.mCurrentColor = _color;
		};
		
		case RED : {
			this.mRed.set(true);
			this.mGreen.set(false);
			this.mBlue.set(false);
			this.mCurrentColor = _color;
		};
		
		case YELLOW : {
			this.mRed.set(true);
			this.mGreen.set(true);
			this.mBlue.set(false);
			this.mCurrentColor = _color;
		};
		
		case GREEN : {
			this.mRed.set(false);
			this.mGreen.set(true);
			this.mBlue.set(false);
			this.mCurrentColor = _color;
		};
		
		case CYAN : {
			this.mRed.set(false);
			this.mGreen.set(true);
			this.mBlue.set(true);
			this.mCurrentColor = _color;
		};
		
		case BLUE : {
			this.mRed.set(false);
			this.mGreen.set(false);
			this.mBlue.set(true);
			this.mCurrentColor = _color;
		};
		
		case MAGENTA : {
			this.mRed.set(true);
			this.mGreen.set(false);
			this.mBlue.set(true);
			this.mCurrentColor = _color;
		};
		
		default : {
			this.mRed.set(false);
			this.mGreen.set(false);
			this.mBlue.set(false);
		};
		}
	}
	
	public void setColor(ColorMode _mode) {
		switch(_mode) {
		case RANDOM : {
			this.setColor(this.RAINBOW_ORDER[(int)(Math.random()*6)]);
		};
		
		case FLICKER : {
			if(System.currentTimeMillis() - this.mPrevFlickerTime > this.mUpdateRateMillis) {
				this.mPrevFlickerTime = System.currentTimeMillis();
				if(this.mCurrentlyFlickeredOff) {
					this.mCurrentlyFlickeredOff = false;
					this.setColor(this.mCurrentColor);
				}
				else {
					this.mCurrentlyFlickeredOff = true;
					this.setColor(Color.OFF);
				}
			}
		};
		
		case RAINBOW : {
			if(System.currentTimeMillis() - this.mPrevRainbowTime > this.mUpdateRateMillis) {
				this.mPrevRainbowTime = System.currentTimeMillis();
				this.setColor(this.RAINBOW_ORDER[this.mRainbowCount]);
				this.mRainbowCount++;
				if(this.mRainbowCount == this.RAINBOW_ORDER.length) {
					this.mRainbowCount = 0;
				}
			}
		};
		
		default : ;
		}
	}
	
	public void disable() {
		this.setColor(Color.OFF);
	}

	public void publishData() {
		SmartDashboard.putString("LEDColor", this.mCurrentColor.toString());
	}

}