package org.usfirst.frc.team346.robot;

import org.usfirst.frc.team346.robot.Robot;
import org.usfirst.frc.team346.robot.RobotMap;
import org.usfirst.frc.team346.subsystems.Drive.DriveMode;
import org.usfirst.frc.team346.subsystems.Tilter.TiltPos;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Preferences;

public class ControlBoard {

	private Robot sRobot;
	private Joystick mController;
	private Joystick mButtonBoard;
	
	private boolean mThrottleTurnMode = false;
	
	private Preferences mPreference;
	    
	private final int LEFT_STICK_X = 0, LEFT_STICK_Y = 1,
					  RIGHT_STICK_X = 2, RIGHT_STICK_Y = 5,
					  LEFT_TRIGGER_AXIS = 3, RIGHT_TRIGGER_AXIS = 4;
	private final int SQUARE = 1, X = 2, CIRCLE = 3, TRIANGLE = 4,
					  LEFT_SHOULDER = 5, RIGHT_SHOULDER = 6,
					  LEFT_TRIGGER_BUTTON = 7, RIGHT_TRIGGER_BUTTON = 8,
					  SELECT = 9, START = 10, LEFT_CLICK = 11, RIGHT_CLICK = 12;
					  
	
	public ControlBoard(Robot _robot) {
		this.sRobot = _robot;
		this.mController = new Joystick(RobotMap.kXboxControllerPort);
		this.mButtonBoard = new Joystick(RobotMap.kButtonBoardPort);
		
		this.mPreference = Preferences.getInstance();
	}
    
	public void drive() {
		if(this.mController.getRawButton(LEFT_CLICK)) {
			if(this.mThrottleTurnMode) {
				this.mThrottleTurnMode = false;
			}
			else {
				this.mThrottleTurnMode = true;
			}
		}
		
		if(this.mThrottleTurnMode) {
			this.sRobot.sDrive.driveThrottleTurn(-this.mController.getRawAxis(RIGHT_STICK_Y), this.mController.getRawAxis(LEFT_STICK_X));
		}
		else {
			this.sRobot.sDrive.drive(DriveMode.PERCENT, -this.mController.getRawAxis(LEFT_STICK_Y), -this.mController.getRawAxis(RIGHT_STICK_Y));
//			this.sRobot.sDrive.drive(DriveMode.VELOCITY, -this.mController.getRawAxis(LEFT_STICK_Y)*1200, -this.mController.getRawAxis(RIGHT_STICK_Y)*1200);
//			this.sRobot.sDrive.driveDifferential(DriveMode.PERCENT, -this.mController.getRawAxis(LEFT_STICK_Y), -this.mController.getRawAxis(RIGHT_STICK_Y));
		}
	}
    
	public void checkIntake() {
		if(this.mController.getRawButton(LEFT_SHOULDER)) {
    		this.sRobot.sIntake.setLeftIntakeSpeed(-0.5);
    	}
		else if(this.mController.getRawButton(LEFT_TRIGGER_BUTTON)) {
    		this.sRobot.sIntake.setLeftIntakeSpeed(0.85 * this.mController.getRawAxis(LEFT_TRIGGER_AXIS));
		}
    	else {
    		this.sRobot.sIntake.setLeftIntakeSpeed(0);
    	}
    	
		if(this.mController.getRawButton(RIGHT_SHOULDER)) {
    		this.sRobot.sIntake.setRightIntakeSpeed(-0.5);
    	}
		else if(this.mController.getRawButton(RIGHT_TRIGGER_BUTTON)) {
    		this.sRobot.sIntake.setRightIntakeSpeed(0.85 * this.mController.getRawAxis(RIGHT_TRIGGER_AXIS));
    	}
    	else {
    		this.sRobot.sIntake.setRightIntakeSpeed(0);
    	}
    }
	
	public void checkLoader() {
		this.sRobot.sIntake.setLeftLoaderSpeed(this.mController.getRawAxis(LEFT_STICK_Y));
		this.sRobot.sIntake.setRightLoaderSpeed(this.mController.getRawAxis(LEFT_STICK_Y));
	}
	
	public void checkTilter() {
		if(this.mController.getPOV(0) == 0) {
			this.sRobot.sTilter.setPos(TiltPos.SWITCH_CLOSE);
		}
		else if(this.mController.getPOV(0) == 180) {
			this.sRobot.sTilter.setPos(TiltPos.SCALE_CLOSE);
		}
	}
	
	public void checkShooter() {
		
	}

}