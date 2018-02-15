package org.usfirst.frc.team346.robot;

import org.usfirst.frc.team346.robot.Robot;
import org.usfirst.frc.team346.subsystems.Drive.DriveMode;
import org.usfirst.frc.team346.subsystems.Shooter.ShooterMode;
import org.usfirst.frc.team346.subsystems.Tilter.TiltPos;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Preferences;

public class ControlBoard {

	private Robot sRobot;
	private Joystick mController;
	private Joystick mButtonBoard;
	private Joystick mDroneController;
	
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
		this.mButtonBoard = new Joystick(RobotMap.kButtonBoardPort);
//		this.mDroneController = new Joystick(RobotMap.KDroneControllerPort);
		
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
			this.sRobot.sDrive.drive(DriveMode.PERCENT, -this.mController.getRawAxis(LEFT_STICK_Y), -this.mController.getRawAxis(RIGHT_STICK_Y));///			this.sRobot.sDrive.drive(DriveMode.PERCENTVElOCITY, -this.mController.getRawAxis(LEFT_STICK_Y), -this.mController.getRawAxis(RIGHT_STICK_Y));
//			this.sRobot.sDrive.drive(DriveMode.VELOCITY, -this.mController.getRawAxis(LEFT_STICK_Y)*2100., -this.mController.getRawAxis(RIGHT_STICK_Y)*2100.);
		}
//		this.sRobot.sDrive.driveThrottleTurn(-this.mDroneController.getRawAxis(0), this.mDroneController.getRawAxis(3));

	}
    
	public void checkIntake() {
		if(this.mButtonBoard.getRawButton(RobotMap.kButton2IntakeOut)) {
			this.sRobot.sIntake.setLeftIntakeSpeedOut(0.75);
			this.sRobot.sIntake.setRightIntakeSpeedOut(0.75);
		}
		else if(this.mButtonBoard.getRawButton(RobotMap.kButton1IntakeIn) || this.mController.getRawButton(LEFT_TRIGGER_BUTTON)) {
			this.sRobot.sIntake.setLeftIntakeSpeedIn(0.50);
			this.sRobot.sIntake.setRightIntakeSpeedIn(0.50);
		}
		else {
			this.sRobot.sIntake.setLeftIntakeSpeedIn(0.0);
			this.sRobot.sIntake.setRightIntakeSpeedIn(0.0);
		}
    }
	
	public void checkLoader() {
		if(this.mButtonBoard.getRawButton(RobotMap.kButton2IntakeOut)) {
			this.sRobot.sLoader.setLeftLoaderSpeedOut(0.5);
			this.sRobot.sLoader.setRightLoaderSpeedOut(0.5);
		}
		else if(this.mButtonBoard.getRawButton(RobotMap.kButton1IntakeIn) || this.mController.getRawButton(LEFT_TRIGGER_BUTTON)) {
			this.sRobot.sLoader.setLeftLoaderSpeedIn(1);
			this.sRobot.sLoader.setRightLoaderSpeedIn(1);
		}
		else {
			this.sRobot.sLoader.setLeftLoaderSpeedIn(0.0);
			this.sRobot.sLoader.setRightLoaderSpeedIn(0.0);
		}
	}
	
	public void checkOuttake() {
		if(this.mButtonBoard.getRawButton(RobotMap.kButton5OuttakeFront)) {
			this.sRobot.sOuttake.setLeftOuttakeSpeedForward(0.75);
			this.sRobot.sOuttake.setRightOuttakeSpeedForward(0.75);
		}
		else if(this.mButtonBoard.getRawButton(RobotMap.kButton6OuttakeBack)) {
			this.sRobot.sOuttake.setLeftOuttakeSpeedReverse(0.65);
			this.sRobot.sOuttake.setRightOuttakeSpeedReverse(0.65);
		}
		else if(this.mButtonBoard.getRawButton(13)) {
			this.sRobot.sOuttake.setLeftOuttakeSpeedForward(1);
			this.sRobot.sOuttake.setRightOuttakeSpeedForward(1);
		}
		else {
			this.sRobot.sOuttake.setLeftOuttakeSpeedForward(0.0);
			this.sRobot.sOuttake.setRightOuttakeSpeedForward(0.0);
		}
	}
	
	public void checkTilter() {
		if(this.mButtonBoard.getRawButtonPressed(RobotMap.kButton7)) {
			this.sRobot.sTilter.disable();
		}
		else if(this.mButtonBoard.getRawButton(RobotMap.kButton11)) {
			this.sRobot.sTilter.setPercent(0.5);
		}
		else if(this.mButtonBoard.getRawButtonPressed(RobotMap.kButton3TilterNeutral)){
			this.sRobot.sTilter.setSetpointPos(TiltPos.NEUTRAL);
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
		
		if(this.mButtonBoard.getRawButton(RobotMap.kButton13)) {
			this.sRobot.sTilter.setTiltNeutralNu();
		}
		
		this.sRobot.sTilter.setMotionMagicVelocityNu(this.mPrefs.getInt("tiltVelNu", 0));
		this.sRobot.sTilter.setMotionMagicAccelerationNu(this.mPrefs.getInt("tiltAccelNu", 0));
		this.sRobot.sTilter.setPID(this.mPrefs.getDouble("tiltP", 0), this.mPrefs.getDouble("tiltI", 0), this.mPrefs.getDouble("tiltD", 0));
	}
	
	public void checkShooter() {
		if(this.mButtonBoard.getRawButton(RobotMap.kButton10ShooterOff)) {
			this.sRobot.sShooter.disable();
		}
		else if(this.mButtonBoard.getRawButtonPressed(RobotMap.kButton9ShooterOn)) {
			this.sRobot.sShooter.setLeftSetpointNu(this.mPrefs.getDouble("shooterLeft", 0));
			this.sRobot.sShooter.setRightSetpointNu(this.mPrefs.getDouble("shooterRight", 0));
		}
		else if(this.mButtonBoard.getRawButton(RobotMap.kButton2IntakeOut) || this.mButtonBoard.getRawButton(RobotMap.kButton5OuttakeFront)) {
			this.sRobot.sShooter.setPercentBack(0.5);
		}
		else if(this.mButtonBoard.getRawButton(RobotMap.kButton1IntakeIn) || this.mButtonBoard.getRawButton(RobotMap.kButton6OuttakeBack)) {
			this.sRobot.sShooter.setPercentFront(0.5);
		}
		else if(this.sRobot.sShooter.getShooterMode() == ShooterMode.PERCENT_VOLTAGE) {
			this.sRobot.sShooter.setPercentFront(0);
		}
		
		if(this.sRobot.sShooter.getShooterMode() != ShooterMode.PERCENT_VOLTAGE) {
			this.sRobot.sShooter.holdSpeedSetpoint();
		}
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