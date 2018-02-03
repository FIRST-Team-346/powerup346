package org.usfirst.frc.team346.auto;

import org.usfirst.frc.team346.auto.plans.AutoPlan;
import org.usfirst.frc.team346.robot.Robot;
import org.usfirst.frc.team346.robot.RobotMap;
import edu.wpi.first.wpilibj.DriverStation;

public class AutoRunner {
	
	private boolean mRunning = false;
	private String mLayout = "000";
	private Robot sRobot;
	
	DriverStation sDriverStation;
	
	AutoPlan mAutoPlan;
	
	public AutoRunner(Robot _robot) {
		this.sDriverStation = DriverStation.getInstance();
		this.mAutoPlan = RobotMap.kAutoPlan;
		this.sRobot = _robot;
	}
	
	public void run() {
		this.mRunning = true;
		System.out.println("Auto Runner| booting up");
		this.receiveLayout();
		try {
			this.perform();
		}
		catch(AutoTerminatedException e) {
			this.terminate();
			return;
		}
		this.complete();
		return;
	}
	
	private void receiveLayout() {
		this.mLayout = this.sDriverStation.getGameSpecificMessage();
	}
	
	public String getLayout() {
		if(this.mLayout == "000") {
			this.receiveLayout();
		}
		return this.mLayout;
	}
	
	private void perform() throws AutoTerminatedException {
		if(!isAuto()) {
			throw new AutoTerminatedException();
		}
		System.out.println("Auto Runner| goal: " + this.mAutoPlan.getGoal());
		System.out.println("Auto Runner| field layout: " + this.getLayout());
		this.mAutoPlan.run(this.sRobot, this.getLayout());
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
