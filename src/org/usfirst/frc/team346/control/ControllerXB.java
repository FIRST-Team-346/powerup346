package org.usfirst.frc.team346.control;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.XboxController;

public class ControllerXB {
	
	private XboxController mXboxController;
	
	public enum Button {
		A, B, X, Y, START, DUP, DDOWN, DLEFT, DRIGHT;
	}
	
	public ControllerXB(int _port) {
		this.mXboxController = new XboxController(_port);
	}
	
	public double getX(Hand _side) {
		return this.mXboxController.getX(_side);
	}
	
	public double getY(Hand _side) {
		return this.mXboxController.getY(_side);
	}
	
	public boolean checkButton(Button _button) {
		switch(_button) {
			case A : {
				return this.mXboxController.getAButton();
			}
			case B : {
				return this.mXboxController.getBButton();
			}
			case X : {
				return this.mXboxController.getXButton();
			}
			case Y : {
				return this.mXboxController.getYButton();
			}
			case START : {
				return this.mXboxController.getStartButton();
			}
			case DUP : {
				return this.mXboxController.getPOV(0) == 0;
			}
			case DRIGHT : {
				return this.mXboxController.getPOV(0) == 90;
			}
			case DDOWN : {
				return this.mXboxController.getPOV(0) == 180;
			}
			case DLEFT : {
				return this.mXboxController.getPOV(0) == 270;
			}
			default : {
				System.out.println("Invalid button.");
				return false;
			}
		}
	}
	
	public boolean checkButtonRisingEdge(Button _button) {
		switch(_button) {
			case A : {
				return this.mXboxController.getAButtonPressed();
			}
			case B : {
				return this.mXboxController.getBButtonPressed();
			}
			case X : {
				return this.mXboxController.getXButtonPressed();
			}
			case Y : {
				return this.mXboxController.getYButtonPressed();
			}
			case START : {
				return this.mXboxController.getStartButtonPressed();
			}
			default : {
				System.out.println("Invalid rising-edge button.");
				return false;
			}
		}
	}
	
	public boolean checkShoulder(Hand _side) {
		return this.mXboxController.getBumper(_side);
	}
	
	public boolean checkShoulderRisingEdge(Hand _side) {
		return this.mXboxController.getBumperPressed(_side);
	}
	
	public double getTrigger(Hand _side) {
		return this.mXboxController.getTriggerAxis(_side);
	}
	
}