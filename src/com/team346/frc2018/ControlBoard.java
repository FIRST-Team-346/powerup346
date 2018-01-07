package com.team346.frc2018;

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
