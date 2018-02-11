package org.usfirst.frc.team346.robot;

import org.usfirst.frc.team346.robot.Robot;
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
	
	private Preferences mPrefs;
	    
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
//		this.mButtonBoard = new Joystick(RobotMap.kButtonBoardPort);
		
		this.mPrefs = Preferences.getInstance();
	}
    
	public void drive() {
		if(this.mController.getRawButtonPressed(LEFT_CLICK)) {
			this.mIsThrottleTurnMode = !this.mIsThrottleTurnMode;
		}
		
		if(this.mIsThrottleTurnMode) {
			this.sRobot.sDrive.driveThrottleTurn(-this.mController.getRawAxis(RIGHT_STICK_Y), this.mController.getRawAxis(LEFT_STICK_X));
		}
		else {
//			this.sRobot.sDrive.drive(DriveMode.PERCENT, -this.mController.getRawAxis(LEFT_STICK_Y), -this.mController.getRawAxis(RIGHT_STICK_Y));
//			this.sRobot.sDrive.drive(DriveMode.PERCENTVElOCITY, -this.mController.getRawAxis(LEFT_STICK_Y), -this.mController.getRawAxis(RIGHT_STICK_Y));
			this.sRobot.sDrive.drive(DriveMode.VELOCITY, -this.mController.getRawAxis(LEFT_STICK_Y)*2100., -this.mController.getRawAxis(RIGHT_STICK_Y)*2100.);
		}
	}
    
	public void checkIntake() {
		if(this.mButtonBoard.getRawButton(RobotMap.kButton5IntakeOut)) {
			this.sRobot.sIntake.setLeftIntakeSpeedOut(0.75);
			this.sRobot.sIntake.setRightIntakeSpeedOut(0.75);
		}
		else if(this.mButtonBoard.getRawButton(RobotMap.kButton1IntakeIn) || this.mController.getRawButton(LEFT_TRIGGER_BUTTON)) {
			this.sRobot.sIntake.setLeftIntakeSpeedIn(0.75);
			this.sRobot.sIntake.setRightIntakeSpeedIn(0.75);
		}
		else {
			this.sRobot.sIntake.setLeftIntakeSpeedIn(0.0);
			this.sRobot.sIntake.setRightIntakeSpeedIn(0.0);
		}
    }
	
	public void checkLoader() {
		if(this.mButtonBoard.getRawButton(RobotMap.kButton5IntakeOut)) {
			this.sRobot.sLoader.setLeftLoaderSpeedOut(1.0);
			this.sRobot.sLoader.setRightLoaderSpeedOut(1.0);
		}
		else if(this.mButtonBoard.getRawButton(RobotMap.kButton1IntakeIn) || this.mController.getRawButton(LEFT_TRIGGER_BUTTON)) {
			this.sRobot.sLoader.setLeftLoaderSpeedIn(1.0);
			this.sRobot.sLoader.setRightLoaderSpeedIn(1.0);
		}
		else {
			this.sRobot.sLoader.setLeftLoaderSpeedIn(0.0);
			this.sRobot.sLoader.setRightLoaderSpeedIn(0.0);
		}
	}
	
	public void checkOuttake() {
		if(this.mButtonBoard.getRawButton(RobotMap.kButton2OuttakeForward)) {
			this.sRobot.sOuttake.setLeftOuttakeSpeedForward(0.75);
			this.sRobot.sOuttake.setRightOuttakeSpeedForward(0.75);
		}
		else if(this.mButtonBoard.getRawButton(RobotMap.kButton6OuttakeReverse)) {
			this.sRobot.sOuttake.setLeftOuttakeSpeedReverse(0.65);
			this.sRobot.sOuttake.setRightOuttakeSpeedReverse(0.65);
		}
		else {
			this.sRobot.sOuttake.setLeftOuttakeSpeedForward(0.0);
			this.sRobot.sOuttake.setRightOuttakeSpeedForward(0.0);
		}
	}
	
	public void checkTilter() {
		if(this.mButtonBoard.getRawButtonPressed(RobotMap.kButton7TilterOff)){
			this.sRobot.sTilter.disable();
		}
		else if(this.mButtonBoard.getRawButtonPressed(RobotMap.kButton4TilterScaleFar)) {
			this.sRobot.sTilter.setSetpointPos(TiltPos.SCALE_FAR);
		}
		else if(this.mButtonBoard.getRawButtonPressed(RobotMap.kButton8TilterScaleClose)) {
			this.sRobot.sTilter.setSetpointPos(TiltPos.SCALE_CLOSE);
		}
		else if(this.mButtonBoard.getRawButtonPressed(RobotMap.kButton12TilterSwitchFar)) {
			this.sRobot.sTilter.setSetpointPos(TiltPos.SWITCH_FAR);
		}
		else if(this.mButtonBoard.getRawButtonPressed(RobotMap.kButton15TilterSwitchClose)) {
			this.sRobot.sTilter.setSetpointPos(TiltPos.SWITCH_CLOSE);
		}
		
		this.sRobot.sTilter.holdSetpoint();
		
		this.sRobot.sTilter.setMotionMagicVelocityNu(this.mPrefs.getInt("tiltVelNu", 0));
		this.sRobot.sTilter.setMotionMagicAccelerationNu(this.mPrefs.getInt("tiltAccelNu", 0));
		this.sRobot.sTilter.setPID(this.mPrefs.getDouble("tiltP", 0), this.mPrefs.getDouble("tiltI", 0), this.mPrefs.getDouble("tiltD", 0));
	}
	
	public void checkShooter() {
		if(this.mButtonBoard.getRawButton(RobotMap.kButton10ShooterOff)) {
			this.sRobot.sShooter.disable();
		}
		else if(this.mButtonBoard.getRawButtonPressed(RobotMap.kButton9ShooterOn)) {
//			this.sRobot.sShooter.setLeftPercentReverse(0.7);
//			this.sRobot.sShooter.setRightPercentReverse(0.7);
			this.sRobot.sShooter.setLeftSetpointNu(RobotMap.kShooterLeftSetpointNu);
			this.sRobot.sShooter.setRightSetpointNu(RobotMap.kShooterRightSetpointNu);
		}
		else if(this.mButtonBoard.getRawButtonPressed(RobotMap.kButton6OuttakeReverse)) {
			this.sRobot.sShooter.setLeftPercentReverse(0.1);
			this.sRobot.sShooter.setRightPercentReverse(0.1);
		}
		else if(this.mButtonBoard.getRawButtonPressed(RobotMap.kButton2OuttakeForward)) {
			this.sRobot.sShooter.setLeftPercentForward(0.1);
			this.sRobot.sShooter.setRightPercentForward(0.1);
		}
		
		this.sRobot.sShooter.holdSpeedSetpoint();
	}
	
	public void checkClimb() {
		if(this.mButtonBoard.getRawButton(RobotMap.kButton14Climb)) {
			this.sRobot.sClimber.setOn();
		}
		else {
			this.sRobot.sClimber.setOff();
		}
	}

}