package org.usfirst.frc.team346.auto.actions;

import org.usfirst.frc.team346.robot.RobotMap;
import org.usfirst.frc.team346.subsystems.Intake;
import org.usfirst.frc.team346.subsystems.Loader;
import org.usfirst.frc.team346.subsystems.Outtake;
import org.usfirst.frc.team346.subsystems.Shooter;
import org.usfirst.frc.team346.subsystems.Tilter;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Preferences;

public class ActionRunner {
	
	private Tilter mTilter;
	private Shooter mShooter;
	private Intake mIntake;
	private Loader mLoader;
	private Outtake mOuttake;
//	private Preferences mPref = Preferences.getInstance();
	
	public ActionRunner() {
		this.mTilter = Tilter.getInstance();
		this.mShooter = Shooter.getInstance();
		this.mIntake = Intake.getInstance();
		this.mLoader = Loader.getInstance();
		this.mOuttake = Outtake.getInstance();
	}
	
	public void setTilterPosNu(int _tiltPosNu) {
		if(this.isDisabled()) return;
		this.mTilter.setSetpointNu(_tiltPosNu);
	}
	
	public void waitUntilAtSpeed(double _timeOutTimeSeconds) {
		if(this.isDisabled()) return;
		long lWaitInitialTime = System.currentTimeMillis();
		while(!this.mShooter.isAtSpeed()) {
			if(this.isDisabled()) return;
			if(System.currentTimeMillis() - lWaitInitialTime > _timeOutTimeSeconds * 1000) {
				System.out.println("Shooter Wait| timeout");
				return;
			}
		}
	}
	
	public void setShooter(int _leftShooterSpeedNu, int _rightShooterSpeedNu) {
		if(this.isDisabled()) return;
		this.mShooter.setLeftSpeedSetpointNu(_leftShooterSpeedNu);
		this.mShooter.setRightSpeedSetpointNu(_rightShooterSpeedNu);
		this.mShooter.holdSpeedSetpoint();
	}
	
	public void setShooterPercentFront(double _percentFront) {
		if(this.isDisabled()) return;
		this.mShooter.setPercentFront(_percentFront);
	}
	
	public void setOuttakePercentFront(double _percentFront) {
		if(this.isDisabled()) return;
		this.mOuttake.setSpeedFront(_percentFront);
	}
	
	public void startOpenIntake() {
		if(this.isDisabled()) return;
		this.mIntake.setSpeedIn(-1);
	}
	
	public void stopOpenIntake() {
		if(this.isDisabled()) return;
		this.mIntake.setSpeedIn(0);
	}
	
	public void startIntakeCube() {
		if(this.isDisabled()) return;
		this.setTilterPosNu(RobotMap.kTiltPosNeutral);
		this.mIntake.setSpeedIn(1.0);
		this.mLoader.setSpeedIn(1.0);
		this.mShooter.setPercentFront(-0.5);
	}
	
	public void stopIntakeCube() {
		if(this.isDisabled()) return;
		this.mIntake.setSpeedIn(0.0);
		this.mLoader.setSpeedIn(0.0);
		this.mShooter.setPercentFront(0.0);
	}
	
	public boolean isDisabled() {
		if(!DriverStation.getInstance().isAutonomous() || DriverStation.getInstance().isDisabled()) {
			return true;
		}
		return false;
	}
	
	public void waitTime(double _seconds) {
		long initialTime = System.currentTimeMillis();
		while(System.currentTimeMillis() - initialTime < Math.abs(_seconds) * 1000.) {
			if(this.isDisabled()) return;
		}
	}
	
}