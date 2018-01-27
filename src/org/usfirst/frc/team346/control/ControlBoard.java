package org.usfirst.frc.team346.control;

import org.usfirst.frc.team346.robot.Robot;
import org.usfirst.frc.team346.robot.RobotMap;
import org.usfirst.frc.team346.subsystems.Drive.DriveMode;

import edu.wpi.first.wpilibj.Joystick;

public class ControlBoard {

	private Robot sRobot;
	private Joystick mController;
	private Joystick mButtonBoard;
	    
	private final int LEFT_STICK = 1, RIGHT_STICK = 5;
	
	public ControlBoard(Robot _robot) {
		this.sRobot = _robot;
		this.mController = new Joystick(RobotMap.kXboxControllerPort);
		this.mButtonBoard = new Joystick(RobotMap.kButtonBoardPort);
	}
    
	public void drive() {
//		this.sRobot.sDrive.drive(DriveMode.PERCENT, this.mController.getRawAxis(LEFT_STICK), this.mController.getRawAxis(RIGHT_STICK));
		this.sRobot.sDrive.drive(DriveMode.VELOCITY, this.mController.getRawAxis(LEFT_STICK)*1200, this.mController.getRawAxis(RIGHT_STICK)*1200);
//		this.sRobot.sDrive.drive(DriveMode.THROTTLE_TURN, this.mController.getRawAxis(RIGHT_STICK), this.mController.getRawAxis(LEFT_STICK));
	}
	
    public void checkIntake() {
    	//TODO
    }
    
    public void checkTurner() {
    	//TODO
    }
    
    public void checkTilter() {
    	//TODO
    }
    
    public void checkShooter() {
    	//TODO
    }
    
    public void checkClimber() {
    	//TODO
    }

}
