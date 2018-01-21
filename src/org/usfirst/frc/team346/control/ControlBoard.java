package org.usfirst.frc.team346.control;

import org.usfirst.frc.team346.robot.Robot;
import org.usfirst.frc.team346.robot.RobotMap;
import org.usfirst.frc.team346.subsystems.Drive.DriveMode;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;

public class ControlBoard {

	private ControllerXB mController;
    private Joystick mButtonBoard;
    private Robot sRobot;

    private static ControlBoard sControlBoardInstance = new ControlBoard();
    protected ControlBoard() {
        this.mController = new ControllerXB(RobotMap.kXboxControllerPort);
        this.mButtonBoard = new Joystick(RobotMap.kButtonBoardPort);
        this.sRobot = Robot.getInstance();
    }
    
    public static ControlBoard getInstance() {
    	return sControlBoardInstance;
    }
    
    public void drive() {
    	this.sRobot.sDrive.drive(DriveMode.PERCENT, this.mController.getY(Hand.kLeft), this.mController.getY(Hand.kRight));
    }
    
    public void checkIntake() {
    	//TODO
    }

}
