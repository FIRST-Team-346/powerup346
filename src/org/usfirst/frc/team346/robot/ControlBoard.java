package org.usfirst.frc.team346.robot;
/*
 * This class is what checks the button board, controller, or any other
 * weird input device you choose to use.
 * It will check if a button is pressed then do a thing
 */

import org.usfirst.frc.team346.robot.Robot;
import org.usfirst.frc.team346.subsystems.Drive.DriveMode;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Preferences;

public class ControlBoard {

	private Robot sRobot;
	private Joystick mController;
	private Joystick mButtonBoard;
	
	private final boolean IS_DRONE_CONTROLLER = true;
	private boolean isDrivingXboxThrottleTurn = true;
	
//	@SuppressWarnings("unused")
//	private Preferences mPref;
	    
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
					  
	/*
	 * This is the constructor for the object.
	 * it takes in the robot as a parameter so it can do things to it.
	 * Can't use a getInstance() here because it uses the Robot class and
	 * the Robot class uses it, so it would cause some recursive getInstance().
	 */
	public ControlBoard(Robot _robot) {
		this.sRobot = _robot;
		this.mController = new Joystick(RobotMap.kXboxControllerPort);
		this.mButtonBoard = new Joystick(RobotMap.kButtonBoardPort);
		
//		this.mPref = Preferences.getInstance();
	}
	
//	----------------------------------------------------------------------------------------------------------------------------------------------------------------
	
	/*
	 * Different controllers have different button numbers and different axis variations
	 * so we have to have different methods based on which one we're using. 
	 */
	
	public void drive() {
		if(this.IS_DRONE_CONTROLLER) {
			this.driveDroneController();
		}
		else {
			this.driveXboxController();
		}
	}
    
	private void driveXboxController() {
		//takes axis input and drives the robot
		if(this.isDrivingXboxThrottleTurn) {
			this.sRobot.sDrive.driveThrottleTurn(-this.mController.getRawAxis(RIGHT_STICK_Y), this.mController.getRawAxis(LEFT_STICK_X));
		}
		else {
			this.sRobot.sDrive.drive(DriveMode.PERCENT, -this.mController.getRawAxis(LEFT_STICK_Y), -this.mController.getRawAxis(RIGHT_STICK_Y));
		}
		
		//This will change which drive method is being used.
		if(this.mController.getRawButtonPressed(this.CIRCLE)) {
			this.isDrivingXboxThrottleTurn = !this.isDrivingXboxThrottleTurn;
		}
	}
	
	private void driveDroneController() {
		this.sRobot.sDrive.driveThrottleTurn(1./0.77 * this.mController.getRawAxis(DRONE_RIGHT_STICK_Y),  1./0.9 * this.mController.getRawAxis(DRONE_LEFT_STICK_X));
	}
	
//	----------------------------------------------------------------------------------------------------------------------------------------------------------------
	
	/*
	 * All of the rest of these are fairly explanatory.
	 * They do a different thing based on the button being pressed.
	 * This is the entirety of teleop control. It's pretty simple.
	 * 
	 * This is all the fun logic of the code. Make sure that when you have
	 *  a long list of if-else statements, put the most important ones at
	 *  the top. And if two buttons can be pressed at the same time on
	 *  accident (on and off together), give the more important one
	 *  precedence (usually off for safety).
	 */
	
	public void checkIntake() {
		if(this.mButtonBoard.getRawButton(RobotMap.kButtonIntakeOut)) {
			this.sRobot.sIntake.setSpeedIn(-1.0);
		}
		else if(this.mButtonBoard.getRawButton(RobotMap.kButtonIntakeIn)) {
			this.sRobot.sIntake.setSpeedIn(0.75);
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
			this.sRobot.sOuttake.setSpeedFront(-0.07);
		}
		else if(this.mButtonBoard.getRawButton(RobotMap.kButtonOuttakeBack)) {
			this.sRobot.sOuttake.setSpeedFront(-1.0);
		}
		else if(this.mButtonBoard.getRawButton(RobotMap.kButtonOuttakeFront)) {
			this.sRobot.sOuttake.setSpeedFront(1.0);
		}
		else {
			this.sRobot.sOuttake.setSpeedFront(0.0);
		}
	}
	
//	----------------------------------------------------------------------------------------------------------------------------------------------------------------
	
	public void checkTilter() {
		if(this.mButtonBoard.getRawButton(RobotMap.kButtonShooterTilterOff)) {
			this.sRobot.sTilter.disable();
		}
		else if(this.mButtonBoard.getRawButtonPressed(RobotMap.kButtonTilterScaleHigh)) {
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
		else if(this.mButtonBoard.getRawButton(RobotMap.kButtonTiltDownFlipCube)) {
			this.sRobot.sTilter.setSetpointNu(RobotMap.kTiltPosNeutral);
		}
		else if(this.sRobot.sTilter.getSetpointNu() == RobotMap.kTiltPosNeutral) {
			this.sRobot.sTilter.checkandSetDrivePosition();
		}
		
		//updates tilter position mid-match slightly
		if(this.mButtonBoard.getRawButton(RobotMap.kButtonShooterTilterOff) && this.mButtonBoard.getRawButtonPressed(RobotMap.kButtonTilterScaleLow)) {
			this.sRobot.sTilter.increaseSlopConstantNu(-5);
		}
		else if(this.mButtonBoard.getRawButton(RobotMap.kButtonShooterTilterOff) && this.mButtonBoard.getRawButtonPressed(RobotMap.kButtonTilterScaleHigh)) {
			this.sRobot.sTilter.increaseSlopConstantNu(5);
		}
	}
	
//	----------------------------------------------------------------------------------------------------------------------------------------------------------------
	
	public void checkShooter() {
		if(this.mButtonBoard.getRawButton(RobotMap.kButtonShooterTilterOff)) {
			this.sRobot.sShooter.disable();
		}
		else if(this.mButtonBoard.getRawButtonPressed(RobotMap.kButtonShooterOn)) {
			if(this.sRobot.sTilter.getSetpointNu() == RobotMap.kTiltPosScaleLow) {
				this.sRobot.sShooter.setLeftSpeedSetpointNu(RobotMap.kShooterLeftSetpointNuLow);
				this.sRobot.sShooter.setRightSpeedSetpointNu(RobotMap.kShooterRightSetpointNuLow);
			}
			else if(this.sRobot.sTilter.getSetpointNu() == RobotMap.kTiltPosScaleHigh) {
				this.sRobot.sShooter.setLeftSpeedSetpointNu(RobotMap.kShooterLeftSetpointNuHigh);
				this.sRobot.sShooter.setRightSpeedSetpointNu(RobotMap.kShooterRightSetpointNuHigh);
			}
			else if(this.sRobot.sTilter.getSetpointNu() == RobotMap.kTiltPosScaleBack) {
				this.sRobot.sShooter.setLeftSpeedSetpointNu(RobotMap.kShooterLeftSetpointNuBack);
				this.sRobot.sShooter.setRightSpeedSetpointNu(RobotMap.kShooterRightSetpointNuBack);
			}
			this.sRobot.sShooter.holdSpeedSetpoint();
		}
		else if(this.mButtonBoard.getRawButton(RobotMap.kButtonIntakeIn)) {
			this.sRobot.sShooter.setPercentFront(-0.6);
		}
		else if(this.mButtonBoard.getRawButton(RobotMap.kButtonOuttakeBack)) {
			this.sRobot.sShooter.setPercentFront(-0.25);
		}
		else if(!this.sRobot.sShooter.isInVelocityModeAndOn() && this.mButtonBoard.getRawButton(RobotMap.kButtonOuttakeFront)) {
			this.sRobot.sShooter.setPercentFront(0.5);
		}
		else if(this.sRobot.sShooter.isInVelocityModeAndOn()) {
			if(this.mButtonBoard.getRawButton(RobotMap.kButtonTilterScaleLow)) {
				this.sRobot.sShooter.setLeftSpeedSetpointNu(RobotMap.kShooterLeftSetpointNuLow);
				this.sRobot.sShooter.setRightSpeedSetpointNu(RobotMap.kShooterRightSetpointNuLow);
			}
			else if(this.mButtonBoard.getRawButton(RobotMap.kButtonTilterScaleHigh)) {
				this.sRobot.sShooter.setLeftSpeedSetpointNu(RobotMap.kShooterLeftSetpointNuHigh);
				this.sRobot.sShooter.setRightSpeedSetpointNu(RobotMap.kShooterRightSetpointNuHigh);
			}
			else if(this.mButtonBoard.getRawButton(RobotMap.kButtonTilterScaleBack)) {
				this.sRobot.sShooter.setLeftSpeedSetpointNu(RobotMap.kShooterLeftSetpointNuBack);
				this.sRobot.sShooter.setRightSpeedSetpointNu(RobotMap.kShooterRightSetpointNuBack);
			}
			else if(this.mButtonBoard.getRawButton(RobotMap.kButtonTilterSwitchBack)) {
				this.sRobot.sShooter.disable();
			}
			else if(this.mButtonBoard.getRawButton(RobotMap.kButtonTilterVault)) {
				this.sRobot.sShooter.disable();
			}
			else if(this.mButtonBoard.getRawButton(RobotMap.kButtonTiltDownFlipCube)) {
				this.sRobot.sShooter.disable();
			}
			else {
				this.sRobot.sShooter.holdSpeedSetpoint();
			}
		}
		else {
			this.sRobot.sShooter.disable();
		}
	}
	
//	----------------------------------------------------------------------------------------------------------------------------------------------------------------
	
	public void checkClimber() {
		if(this.mButtonBoard.getRawButton(RobotMap.kButtonClimbRaiseHook)) {
			this.sRobot.sClimber.raiseHook(true);
		}
		else {
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
		if(this.mButtonBoard.getRawButton(RobotMap.kButtonOuttakeFront) || this.mButtonBoard.getRawButton(RobotMap.kButtonOuttakeBack)) {
			this.sRobot.sLights.setYellow();
		}
//		else if(this.sRobot.sShooter.isInVelocityModeAndOn() && this.sRobot.sShooter.isAtSpeed()) {
//			this.sRobot.sLights.setGreen();
//		}
		else if(this.sRobot.sTilter.getSetpointNu() == RobotMap.kTiltPosScaleLow) {
			this.sRobot.sLights.setBlue();
		}
		else if(this.sRobot.sTilter.getSetpointNu() == RobotMap.kTiltPosScaleHigh) {
			this.sRobot.sLights.setBlue();
		}
		else if(this.sRobot.sTilter.getSetpointNu() == RobotMap.kTiltPosScaleBack) {
			this.sRobot.sLights.setBlue();
		}
		else if(this.sRobot.sTilter.getSetpointNu() == RobotMap.kTiltPosVault) {
			this.sRobot.sLights.setPurple();
		}
		else if(this.mButtonBoard.getRawButton(RobotMap.kButtonClimbRaiseHook) || this.mButtonBoard.getRawButtonPressed(RobotMap.kButtonClimb)) {
			this.sRobot.sLights.setRed();
		}
		else if(this.mButtonBoard.getRawButton(RobotMap.kButtonLights)) {
			this.sRobot.sLights.setRandom();
		}
		else {
			this.sRobot.sLights.setCyan();
		}
	}
	
//	----------------------------------------------------------------------------------------------------------------------------------------------------------------
	
}