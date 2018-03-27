package org.usfirst.frc.team346.auto;

import org.usfirst.frc.team346.subsystems.Gyro;

import edu.wpi.first.wpilibj.DriverStation;

public class AutoRunner {
	
	private boolean mRunning = false;
	private String mLayout = "000";
	private double mSwitchOnLeft = 0, mScaleOnLeft = 0, mBotStartingOnLeft = 0;
	private boolean mIsGoodScale, mIsGoodSwitch;
	
	DriverStation sDriverStation;
	
	AutoBuilder mAutoBuildPlan;
	AutoPlan mAutoPlan;
	
	public AutoRunner() {
		this.sDriverStation = DriverStation.getInstance();
	}
	
	public void run(boolean _startingOnLeft, AutoBuilder _buildPlan) {
		this.mAutoBuildPlan = _buildPlan;
		this.mBotStartingOnLeft = (_startingOnLeft)? 1. : -1.;
		
		this.mRunning = true;
		System.out.println("Auto Runner| booting up");
		this.receiveLayout();
		try {
//			Gyro.getInstance().calibrate();
			this.perform();
		}
		catch(AutoTerminatedException e) {
			this.terminate();
			return;
		}
		this.complete();
		return;
	}
	
	public void receiveLayout() {
		//Gets the layout of the field from the DriverStation/Field Management System
		this.mLayout = this.sDriverStation.getGameSpecificMessage();
		
		this.mSwitchOnLeft = (this.mLayout.charAt(0) == 'L') ? 1. : -1.;
		this.mScaleOnLeft = (this.mLayout.charAt(1) == 'L') ? 1. : -1.;
		
		this.mIsGoodScale = false;
		this.mIsGoodSwitch = false;
		if(this.mBotStartingOnLeft == this.mSwitchOnLeft) {
			this.mIsGoodSwitch = true;
		}
		if(this.mBotStartingOnLeft == this.mScaleOnLeft) {
			this.mIsGoodScale = true;
		}
	}
	
	public String getLayout() {
		return this.mLayout;
	}
	
	private void perform() throws AutoTerminatedException {
		if(!isAuto()) {
			throw new AutoTerminatedException();
		}
		if(this.mLayout == "000") {
			this.receiveLayout();
		}
		
		if(this.mIsGoodSwitch && this.mIsGoodScale) {
			System.out.println("GoodGood");
			this.mAutoPlan = this.mAutoBuildPlan.getGG();
		}
		else if(!this.mIsGoodSwitch && this.mIsGoodScale) {
			System.out.println("BadGood");
			this.mAutoPlan = this.mAutoBuildPlan.getBG();
		}
		else if(this.mIsGoodSwitch && !this.mIsGoodScale) {
			System.out.println("GoodBad");
			this.mAutoPlan = this.mAutoBuildPlan.getGB();
		}
		else if(!this.mIsGoodSwitch && !this.mIsGoodScale) {
			System.out.println("BadBad");
			this.mAutoPlan = this.mAutoBuildPlan.getBB();
		}
		
		System.out.println("Auto Runner| goal: " + this.mAutoPlan.getGoal());
		System.out.println("Auto Runner| field layout: " + this.getLayout());

		this.mAutoPlan.run(this.mBotStartingOnLeft, this.mSwitchOnLeft, this.mScaleOnLeft);
	}
	
	private void complete() {
		System.out.println("Auto Runner| complete");
		this.mRunning = false;
	}
	
	private void terminate() {
		System.out.println("Auto Runner| terminated unexpectedly");
		this.complete();
	}
	
	public boolean isAuto() {
		if(this.sDriverStation.isDisabled() || !this.sDriverStation.isAutonomous()) {
			System.out.println("Auto Runner| not autonomous mode");
			return false;
		}
		return true;
	}
	
	public boolean isRunning() {
		return mRunning;
	}
	
	public void waitTime(double _seconds) {
		long lInitialTime = System.currentTimeMillis();
		while(System.currentTimeMillis() - lInitialTime < Math.abs(_seconds) * 1000) {
		}
	}
}
