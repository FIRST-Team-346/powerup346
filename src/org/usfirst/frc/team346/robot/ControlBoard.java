package org.usfirst.frc.team346.robot;

import org.usfirst.frc.team346.robot.Robot;
import org.usfirst.frc.team346.subsystems.Drive.DriveMode;
import org.usfirst.frc.team346.subsystems.Lights.Color;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ControlBoard {

	private Robot sRobot;
	private Joystick mController;
	private Joystick mButtonBoard;
	
	private final boolean IS_DRONE_CONTROLLER = true;
	private boolean mIsThrottleTurnMode = false;
	
	@SuppressWarnings("unused")
	private Preferences mPref;
	    
	@SuppressWarnings("unused")
	private final int LEFT_STICK_X = 0,			LEFT_STICK_Y = 1,
					  RIGHT_STICK_X = 2,		RIGHT_STICK_Y = 5,
					  LEFT_TRIGGER_AXIS = 3,	RIGHT_TRIGGER_AXIS = 4,
					  
					  SQUARE = 1, 				X = 2,
					  CIRCLE = 3,				TRIANGLE = 4,
					  LEFT_SHOULDER = 5, 		RIGHT_SHOULDER = 6,
					  LEFT_TRIGGER_BUTTON = 7, 	RIGHT_TRIGGER_BUTTON = 8,
					  SELECT = 9,				START = 10,
					  LEFT_CLICK = 11,			RIGHT_CLICK = 12,
					  
					  DRONE_RIGHT_STICK_Y = 0, DRONE_LEFT_STICK_X = 3;
					  
	
	public ControlBoard(Robot _robot) {
		this.sRobot = _robot;
		this.mController = new Joystick(RobotMap.kXboxControllerPort);
		this.mButtonBoard = new Joystick(RobotMap.kButtonBoardPort);
		
		this.mPref = Preferences.getInstance();
	}
	
//	----------------------------------------------------------------------------------------------------------------------------------------------------------------
	
	public void drive() {
		if(this.IS_DRONE_CONTROLLER) {
			this.driveDroneController();
		}
		else {
			this.driveXboxController();
		}
	}
    
	private void driveXboxController() {
		this.sRobot.sDrive.driveThrottleTurn(-this.mController.getRawAxis(RIGHT_STICK_Y), this.mController.getRawAxis(LEFT_STICK_X));
	}
	
	private void driveDroneController() {
		this.sRobot.sDrive.driveThrottleTurn(-10./9. * this.mController.getRawAxis(DRONE_RIGHT_STICK_Y), 20./19. * this.mController.getRawAxis(DRONE_LEFT_STICK_X));
	}
	
//	----------------------------------------------------------------------------------------------------------------------------------------------------------------
	
	public void checkIntake() {
		if(this.mButtonBoard.getRawButton(RobotMap.kButtonIntakeOut)) {
			this.sRobot.sIntake.setSpeedIn(-1.0);
		}
		else if(this.mButtonBoard.getRawButton(RobotMap.kButtonIntakeIn)) {
			this.sRobot.sIntake.setSpeedIn(0.5);
		}
		else {
			this.sRobot.sIntake.setSpeedIn(0.0);
		}
    }
	
	public void checkLoader() {
		if(this.mButtonBoard.getRawButton(RobotMap.kButtonIntakeOut)) {
			this.sRobot.sLoader.setSpeedIn(-1.0);
		}
		else if(this.mButtonBoard.getRawButton(RobotMap.kButtonIntakeIn)) {
			this.sRobot.sLoader.setSpeedIn(1.0);
		}
		else {
			this.sRobot.sLoader.setSpeedIn(0.0);
		}
	}
	
	public void checkOuttake() {
		if(this.mButtonBoard.getRawButton(RobotMap.kButtonIntakeIn)) {
			this.sRobot.sOuttake.setSpeedFront(-0.15);
		}
		else if(this.mButtonBoard.getRawButton(RobotMap.kButtonOuttakeBack)) {
			this.sRobot.sOuttake.setSpeedFront(-1.0);
		}
		else if(this.mButtonBoard.getRawButton(RobotMap.kButtonOuttakeFront)) {
			this.sRobot.sOuttake.setSpeedFront(0.75);
		}
		else {
			this.sRobot.sOuttake.setSpeedFront(0.0);
		}
	}
	
//	----------------------------------------------------------------------------------------------------------------------------------------------------------------
	
	public void checkTilter() {
		if(this.mButtonBoard.getRawButtonPressed(RobotMap.kButtonTilterScaleHigh)) {
			this.sRobot.sTilter.setSetpointNu(RobotMap.kTiltPosScaleHigh);
		}
		else if(this.mButtonBoard.getRawButtonPressed(RobotMap.kButtonTilterScaleLow)) {
			this.sRobot.sTilter.setSetpointNu(RobotMap.kTiltPosScaleLow);
		}
		else if(this.mButtonBoard.getRawButtonPressed(RobotMap.kButtonTilterScaleBack)) {
			this.sRobot.sTilter.setSetpointNu(RobotMap.kTiltPosScaleBack);
		}
		else if(this.mButtonBoard.getRawButtonPressed(RobotMap.kButtonTilterVault)) {
			this.sRobot.sTilter.setSetpointNu(RobotMap.kTiltPosVault);
		}
		else if(this.mButtonBoard.getRawButtonPressed(RobotMap.kButtonTilterSwitchBack)) {
			this.sRobot.sTilter.setSetpointNu(RobotMap.kTiltPosSwitchBack);
		}
		else if(this.mButtonBoard.getRawButton(RobotMap.kButtonIntakeIn)) {
			this.sRobot.sTilter.setSetpointNu(RobotMap.kTiltPosNeutral);
		}
		//Temp button 13 to flip over tall cube without running reverse intake
		else if(this.mButtonBoard.getRawButton(RobotMap.kButtonTiltDownFlipCube)) {
			this.sRobot.sTilter.setSetpointNu(RobotMap.kTiltPosNeutral);
		}
		else if(this.mButtonBoard.getRawButton(RobotMap.kButtonIntakeOut)) {
			if(this.sRobot.sTilter.getSetpointNu() == RobotMap.kTiltPosNeutral) {
				this.sRobot.sTilter.setSetpointNu(RobotMap.kTiltPosDrive);
			}
		}
		else if(this.sRobot.sTilter.getSetpointNu() == RobotMap.kTiltPosNeutral) {
			this.sRobot.sTilter.checkDrivePosition();
		}
		
//		this.sRobot.sTilter.setMotionMagicVelocityNu(this.pref.getInt("tiltVelNu", 0));
//		this.sRobot.sTilter.setMotionMagicAccelerationNu(this.pref.getInt("tiltAccelNu", 0));
//		this.sRobot.sTilter.setPID(this.pref.getDouble("tiltP", 0), this.pref.getDouble("tiltI", 0), this.pref.getDouble("tiltD", 0));
	}
	
//	----------------------------------------------------------------------------------------------------------------------------------------------------------------
	
	public void checkShooter() {
		if(this.mButtonBoard.getRawButton(RobotMap.kButtonShooterOff)) {
			this.sRobot.sShooter.disable();
		}
		else if(this.mButtonBoard.getRawButtonPressed(RobotMap.kButtonShooterOn)) {
			if(this.sRobot.sTilter.getSetpointNu() == RobotMap.kTiltPosScaleHigh || this.sRobot.sTilter.getSetpointNu() == RobotMap.kTiltPosScaleBack) {
				this.sRobot.sShooter.setLeftSpeedSetpointNu(RobotMap.kShooterLeftSetpointNuHigh);
				this.sRobot.sShooter.setRightSpeedSetpointNu(RobotMap.kShooterRightSetpointNuHigh);
			}
			else if(this.sRobot.sTilter.getSetpointNu() == RobotMap.kTiltPosScaleLow) {
				this.sRobot.sShooter.setLeftSpeedSetpointNu(RobotMap.kShooterLeftSetpointNuLow);
				this.sRobot.sShooter.setRightSpeedSetpointNu(RobotMap.kShooterRightSetpointNuLow);
			}
			this.sRobot.sShooter.holdSpeedSetpoint();
		}
		else if(this.mButtonBoard.getRawButton(RobotMap.kButtonIntakeIn)) {
			this.sRobot.sShooter.setPercentFront(-0.6);
		}
		else if(this.mButtonBoard.getRawButton(RobotMap.kButtonOuttakeBack)) {
			this.sRobot.sShooter.setPercentFront(-0.25);
		}
		else if(this.sRobot.sShooter.isInVelocityMode()) {
			this.sRobot.sShooter.holdSpeedSetpoint();
		}
		else if(this.mButtonBoard.getRawButton(RobotMap.kButtonOuttakeFront)) {
			this.sRobot.sShooter.setPercentFront(0.6);
		}
		else {
			this.sRobot.sShooter.disable();
		}
	}
	
//	----------------------------------------------------------------------------------------------------------------------------------------------------------------
	
	public void checkClimber() {
		if(this.mButtonBoard.getRawButton(RobotMap.kButtonClimbRaiseHook)) {
			this.sRobot.sClimber.raiseHook(true);
			SmartDashboard.putBoolean("Servo Enabled", true);
		}
		else {
			SmartDashboard.putBoolean("Servo Enabled", false);
			this.sRobot.sClimber.raiseHook(false);
		}
		
		if(this.mButtonBoard.getRawButton(RobotMap.kButtonClimb)) {
			this.sRobot.sClimber.setOn();
		}
		else {
			this.sRobot.sClimber.setOff();
		}
	}
	
//	----------------------------------------------------------------------------------------------------------------------------------------------------------------

	public void checkLights() {
		if(this.mButtonBoard.getRawButton(RobotMap.kButtonLights)) {
			this.sRobot.sLights.setColor(Color.RED);
		}
		else if(this.mButtonBoard.getRawButton(RobotMap.kButtonClimbRaiseHook)) {
			this.sRobot.sLights.setColor(Color.PURPLE);
		}
		else {
			this.sRobot.sLights.setColor(Color.CYAN);
		}
	}
	
//	----------------------------------------------------------------------------------------------------------------------------------------------------------------
	
}