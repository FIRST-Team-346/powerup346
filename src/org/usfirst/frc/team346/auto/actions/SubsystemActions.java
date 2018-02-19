package org.usfirst.frc.team346.auto.actions;

import org.usfirst.frc.team346.robot.RobotMap;
import org.usfirst.frc.team346.subsystems.Intake;
import org.usfirst.frc.team346.subsystems.Loader;
import org.usfirst.frc.team346.subsystems.Outtake;
import org.usfirst.frc.team346.subsystems.Shooter;
import org.usfirst.frc.team346.subsystems.Tilter;

import edu.wpi.first.wpilibj.Preferences;

public class SubsystemActions {
	
	private Tilter mTilter;
	private Shooter mShooter;
	private Intake mIntake;
	private Loader mLoader;
	private Outtake mOuttake;
	private Preferences mPref = Preferences.getInstance();
	
	public SubsystemActions() {
		this.mTilter = Tilter.getInstance();
		this.mShooter = Shooter.getInstance();
		this.mIntake = Intake.getInstance();
		this.mLoader = Loader.getInstance();
		this.mOuttake = Outtake.getInstance();
	}
	
	public void shootToScale() {
		this.setTilterPosNu(RobotMap.kTiltPosScaleHigh);
		this.setShooter(RobotMap.kShooterLeftSetpointNuMid, RobotMap.kShooterRightSetpointNuMid);
		this.waitUntilAtSpeed(1.5);
		this.setOuttakePercentFront(1.0);
		this.waitTime(1);
		this.setOuttakePercentFront(0.0);
		this.setShooterPercentFront(0.0);
	}
	
	public void shootToSwitchBack() {
		this.setTilterPosNu(RobotMap.kTiltPosSwitchBack);
		this.setOuttakePercentFront(-1.0);
		this.setShooterPercentFront(-0.5);
		this.waitTime(1);
		this.setOuttakePercentFront(0.0);
		this.setShooterPercentFront(0.0);
	}
	
	public void openIntake() {
		this.mIntake.setSpeedIn(-1.0);
		this.waitTime(0.15);
		this.mIntake.setSpeedIn(0.0);
	}
	
	public void setTilterPosNu(int _tiltPosNu) {
		this.mTilter.setSetpointNu(_tiltPosNu);
	}
	
	public void waitUntilAtSpeed(double _timeOutTimeSeconds) {
		long lWaitInitialTime = System.currentTimeMillis();
		while(!this.mShooter.isAtSpeed()) {
			if(System.currentTimeMillis() - lWaitInitialTime > _timeOutTimeSeconds * 1000) {
				System.out.println("Shooter Wait| timeout");
				return;
			}
		}
	}
	
	public void setShooter(int _leftShooterSpeedNu, int _rightShooterSpeedNu) {
		this.mShooter.setLeftSpeedSetpointNu(_leftShooterSpeedNu);
		this.mShooter.setRightSpeedSetpointNu(_rightShooterSpeedNu);
		this.mShooter.holdSpeedSetpoint();
	}
	
	public void setShooterPercentFront(double _percentFront) {
		this.mShooter.setPercentFront(_percentFront);
	}
	
	public void setOuttakePercentFront(double _percentFront) {
		this.mOuttake.setSpeedFront(_percentFront);
	}
	
	public void setIntakeIn(double _percentIn) {
		this.mIntake.setSpeedIn(_percentIn);
		this.mLoader.setSpeedIn(_percentIn >= 0 ? 1.0 : 0.0);
		this.mShooter.setPercentFront(_percentIn >= 0 ? -0.5 : 0.5);
	}
	
	public void setIntakeOff() {
		this.mIntake.setSpeedIn(0.0);
		this.mLoader.setSpeedIn(0.0);
		this.mShooter.setPercentFront(0.0);
	}
	
	public void waitTime(double _seconds) {
		long initialTime = System.currentTimeMillis();
		while(System.currentTimeMillis() - initialTime < Math.abs(_seconds) * 1000.) {
		}
	}
	
}