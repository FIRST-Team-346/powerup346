package org.usfirst.frc.team346.control;

import edu.wpi.first.wpilibj.Joystick;

public class ControlBoard {

	private final Joystick mLeftStick;
    private final Joystick mRightStick;
    private final Joystick mButtons;

    public ControlBoard() {
        mLeftStick = new Joystick(0);
        mRightStick = new Joystick(1);
        mButtons = new Joystick(2);
    }

}
