package org.usfirst.frc.team346.subsystems;

import org.usfirst.frc.team346.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Climber implements Subsystem {

	private TalonSRX mWinch;
	private Servo mServo;
	private final double mOnValue = 1.0;
	private final boolean mServoOnFrontLeft = true;
	private final int mLeftServoPort = 0;
	private final int mRightServoPort = 1;
	
	private static Climber sClimberInstance = new Climber();
	public static Climber getInstance() {
		return sClimberInstance;
	}
	
	protected Climber() {
		initTalons();
		initServo();
	}
	
	private void initTalons() {
		this.mWinch = new TalonSRX(RobotMap.kClimberPort);
		this.mWinch.set(ControlMode.PercentOutput, 0);
		this.mWinch.setNeutralMode(NeutralMode.Brake);
		this.mWinch.overrideLimitSwitchesEnable(true);
		this.mWinch.overrideSoftLimitsEnable(true);
	}
	
	/*
	 * So this is the only time i've ever used a servo but they're super easy. They're just a small
	 * motor, this one can only rotate about 180 degree. You just set them 0 or 1 which sets it's position.
	 * They're wired directly into the roborio through PWM.
	 * 
	 * In this scenario, we had to have two options: servo on left, and servo on right.
	 *  since it could be moved on the robot. We had to account for this a bit and make
	 *  it spin clockwise on the left but counterclockwise on the right, for example.
	 */
	private void initServo() {
		if(this.mServoOnFrontLeft) {
			this.mServo = new Servo(this.mLeftServoPort);
		}
		else {
			this.mServo = new Servo(this.mRightServoPort);
		}
	}
	
	public void setOn() {
		this.mWinch.set(ControlMode.PercentOutput, this.mOnValue);
	}
	
	public void setOff() {
		this.mWinch.set(ControlMode.PercentOutput, 0);
	}
	
	public void raiseHook(boolean _raise) {
		if(_raise) {
			/*
			 * If you guys don't know the ? operator, look it up. It's useful and simple.
			 * this.mServoOnFrontLeft ? 1 : 0 is the same as:
			 * 
			 * if(this.mServoOnFrontLeft) {
			 *     this.mServo.set(1);
			 * }
			 * else {
			 *     this.mServo.set(0);
			 * }
			 */
			this.mServo.set(this.mServoOnFrontLeft ? 1 : 0);
		}
		else {
			this.mServo.set(this.mServoOnFrontLeft ? 0 : 1);
		}
	}

	public void disable() {
		this.mWinch.set(ControlMode.Disabled, 0);
	}

	public void publishData() {
		SmartDashboard.putNumber("WinchCurrent", this.mWinch.getOutputCurrent());
	}
	
}