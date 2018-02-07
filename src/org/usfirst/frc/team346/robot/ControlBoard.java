package org.usfirst.frc.team346.robot;

import org.usfirst.frc.team346.robot.Robot;
import org.usfirst.frc.team346.robot.RobotMap;
import org.usfirst.frc.team346.subsystems.Drive.DriveMode;
import org.usfirst.frc.team346.subsystems.Tilter.TiltPos;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Preferences;

public class ControlBoard {

	private Robot sRobot;
	private Joystick mController;
	private Joystick mButtonBoard;
	
	private boolean mIsThrottleTurnMode = false;
	private boolean mIsShooterOn = false;
	
	private Preferences mPreference;
	private DriverStation mDriverStation;
	    
	@SuppressWarnings("unused")
	private final int LEFT_STICK_X = 0, LEFT_STICK_Y = 1,
					  RIGHT_STICK_X = 2, RIGHT_STICK_Y = 5,
					  LEFT_TRIGGER_AXIS = 3, RIGHT_TRIGGER_AXIS = 4;
	@SuppressWarnings("unused")
	private final int SQUARE = 1, X = 2, CIRCLE = 3, TRIANGLE = 4,
					  LEFT_SHOULDER = 5, RIGHT_SHOULDER = 6,
					  LEFT_TRIGGER_BUTTON = 7, RIGHT_TRIGGER_BUTTON = 8,
					  SELECT = 9, START = 10, LEFT_CLICK = 11, RIGHT_CLICK = 12;
					  
	
	public ControlBoard(Robot _robot) {
		this.sRobot = _robot;
		this.mController = new Joystick(RobotMap.kXboxControllerPort);
		this.mButtonBoard = new Joystick(RobotMap.kButtonBoardPort);
		
		this.mPreference = Preferences.getInstance();
		this.mDriverStation = DriverStation.getInstance();
	}
    
	public void drive() {
		if(this.mController.getRawButtonPressed(LEFT_CLICK)) {
			this.mIsThrottleTurnMode = !this.mIsThrottleTurnMode;
		}
		
		if(this.mIsThrottleTurnMode) {
			this.sRobot.sDrive.driveThrottleTurn(-this.mController.getRawAxis(RIGHT_STICK_Y), this.mController.getRawAxis(LEFT_STICK_X));
		}
		else {
			this.sRobot.sDrive.drive(DriveMode.PERCENT, -this.mController.getRawAxis(LEFT_STICK_Y), -this.mController.getRawAxis(RIGHT_STICK_Y));
//			this.sRobot.sDrive.drive(DriveMode.VELOCITY, -this.mController.getRawAxis(LEFT_STICK_Y)*1200, -this.mController.getRawAxis(RIGHT_STICK_Y)*1200);
//			this.sRobot.sDrive.driveDifferential(DriveMode.PERCENT, -this.mController.getRawAxis(LEFT_STICK_Y), -this.mController.getRawAxis(RIGHT_STICK_Y));
//			this.sRobot.sDrive.drive(DriveMode.PERCENT, this.mPreference.getDouble("minVL", 0), -this.mPreference.getDouble("minVL", 0));
		}
	}
    
	public void checkIntake() {
//		if(this.mController.getRawButton(LEFT_SHOULDER)) {
//    		this.sRobot.sIntake.setLeftIntakeSpeed(0.75);
//    	}
//		else if(this.mController.getRawButton(LEFT_TRIGGER_BUTTON)) {
//    		this.sRobot.sIntake.setLeftIntakeSpeed(-0.5);
//		}
//    	else {
//    		this.sRobot.sIntake.setLeftIntakeSpeed(0);
//    	}
//    	
//		if(this.mController.getRawButton(RIGHT_SHOULDER)) {
//    		this.sRobot.sIntake.setRightIntakeSpeed(-0.75);
//    	}
//		else if(this.mController.getRawButton(RIGHT_TRIGGER_BUTTON)) {
//    		this.sRobot.sIntake.setRightIntakeSpeed(0.5);
//    	}
//    	else {
//    		this.sRobot.sIntake.setRightIntakeSpeed(0);
//    	}
		
		if(this.mController.getRawButton(LEFT_TRIGGER_BUTTON)) {
			this.sRobot.sIntake.setLeftIntakeSpeed(0.75);
			this.sRobot.sIntake.setRightIntakeSpeed(0.75);
		}
		else if(this.mController.getRawButton(LEFT_SHOULDER)) {
			this.sRobot.sIntake.setLeftIntakeSpeed(-0.75);
			this.sRobot.sIntake.setRightIntakeSpeed(-0.75);
		}
		else {
			this.sRobot.sIntake.setLeftIntakeSpeed(0.0);
			this.sRobot.sIntake.setRightIntakeSpeed(0.0);
    	}
    }
	
	public void checkLoader() {
		if(this.mController.getRawButton(X)) {
			this.sRobot.sLoader.setLeftLoaderSpeed(-1.0);
			this.sRobot.sLoader.setRightLoaderSpeed(-1.0);
		}
		else if(this.mController.getRawButton(SQUARE)) {
			this.sRobot.sLoader.setLeftLoaderSpeed(1.0);
			this.sRobot.sLoader.setRightLoaderSpeed(1.0);
		}
		else {
			this.sRobot.sLoader.setLeftLoaderSpeed(0.0);
			this.sRobot.sLoader.setRightLoaderSpeed(0.0);
		}
	}
	
	public void checkOuttake() {
		if(this.mController.getRawButton(RIGHT_TRIGGER_BUTTON)) {
			this.sRobot.sOuttake.setLeftOuttakeSpeed(-.50);
			this.sRobot.sOuttake.setRightOuttakeSpeed(-.50);
		}
		else if(this.mController.getRawButton(RIGHT_SHOULDER)) {
			this.sRobot.sOuttake.setLeftOuttakeSpeed(0.5);
			this.sRobot.sOuttake.setRightOuttakeSpeed(0.5);
		}
		else {
			this.sRobot.sOuttake.setLeftOuttakeSpeed(0.0);
			this.sRobot.sOuttake.setRightOuttakeSpeed(0.0);
		}
	}
	
	public void checkTilter() {
//		if(this.mController.getPOV(0) == 0) {
//			this.sRobot.sTilter.setPos(TiltPos.SWITCH_CLOSE);
//		}
//		else if(this.mController.getPOV(0) == 180) {
//			this.sRobot.sTilter.setPos(TiltPos.SCALE_CLOSE);
//		}
		
		if(this.mController.getPOV(0) == 0) {
			this.sRobot.sTilter.setSetpointNu(this.mPreference.getDouble("tiltPosNu", 0));
			this.sRobot.sTilter.setCruiseVelocityNu(this.mPreference.getInt("tiltVelNu", 0));
			this.sRobot.sTilter.setMaxAccelerationNu(this.mPreference.getInt("tiltAccelNu", 0));
		}
		else {
		
			if(this.mController.getRawButton(SELECT)) {
				this.sRobot.sTilter.setMotorPercent(-0.4);
			}
			else if(this.mController.getRawButton(START)) {
			}
			else {
				this.sRobot.sTilter.setMotorPercent(0.0);
			}
		}
	}
	
	public void checkShooter() {
		if(this.mController.getRawButtonPressed(CIRCLE)) {
			this.mIsShooterOn = !this.mIsShooterOn;
		}
		
		if(this.mIsShooterOn) {
//			this.sRobot.sShooter.setOn();
			this.sRobot.sShooter.setLeftPercent(1.0);
			this.sRobot.sShooter.setRightPercent(1.0);
		}
		else if(this.mController.getRawButton(SQUARE)) {
			this.sRobot.sShooter.setLeftPercent(-0.25);
			this.sRobot.sShooter.setRightPercent(-0.25);
		}
		else {
//			this.sRobot.sShooter.setOff();
			this.sRobot.sShooter.setLeftPercent(0.0);
			this.sRobot.sShooter.setRightPercent(0.0);
		}
	}
	
	public void disableLocks() {
		this.mIsShooterOn = false;
		this.mIsThrottleTurnMode = false;
	}
	
//	public void checkShooterBlock() {
//		if(this.mController.getPOV(0) == 180) {
//			this.sRobot.sShooterBlock.extend();
//		}
//		else {
//			this.sRobot.sShooterBlock.retract();
//		}
//	}
	
//	public void checkClimber() {
//		if(this.mController.getRawButton(TRIANGLE)) { We NEEDED THE TRIANGLE 
//			this.sRobot.sClimber.setOn();
//		}
//		else {
//			this.sRobot.sClimber.setOff();
	//	}
//	}

}