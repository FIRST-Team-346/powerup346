package org.usfirst.frc.team346.auto;

import org.usfirst.frc.team346.auto.plans.AutoPlan;
import org.usfirst.frc.team346.robot.RobotMap;
import edu.wpi.first.wpilibj.DriverStation;

public class AutoRunner {
	
	private boolean mRunning = false;
	private double mUpdateRate = 0.02;
	private String mLayout = "000";
	
	DriverStation sDriverStation;
	
	AutoPlan mAutoPlan;
	
	private static AutoRunner sAutoRunnerInstance = new AutoRunner();
	protected AutoRunner() {
		sDriverStation = DriverStation.getInstance();
		mAutoPlan = RobotMap.mAutoPlan;
	}
	
	public AutoRunner getInstance() {
		return sAutoRunnerInstance;
	}
	
	public void run() {
		mRunning = true;
		System.out.println("Auto Runner| booting up");
		receiveLayout();
		try {
			perform();
		}
		catch(AutoTerminatedException e) {
			terminate();
			return;
		}
		complete();
		return;
	}
	
	private void receiveLayout() {
		mLayout = sDriverStation.getGameSpecificMessage();
	}
	
	public String getLayout() {
		if(mLayout == "000") {
			receiveLayout();
		}
		return mLayout;
	}
	
	private void perform() throws AutoTerminatedException {
		if(!isAuto()) {
			throw new AutoTerminatedException();
		}
		System.out.println("Auto Runner| goal: " + mAutoPlan.getGoal());
		System.out.println("Auto Runner| field layout: " + getLayout());
		mAutoPlan.run();
	}
	
	private void complete() {
		System.out.println("Auto Runner| complete");
		mRunning = false;
	}
	
	private void terminate() {
		System.out.println("Auto Runner| terminated unexpectedly");
		complete();
	}
	
	public boolean isAuto() {
		if(sDriverStation.isDisabled() || !sDriverStation.isAutonomous()) {
			System.out.println("Auto Runner| not autonomous mode");
			return false;
		}
		return true;
	}
	
	public boolean isRunning() {
		return mRunning;
	}
	
	public void waitTime(double _seconds) {
		long initialTime = System.currentTimeMillis();
		while(System.currentTimeMillis() - initialTime < Math.abs(_seconds) * 1000) {
		}
	}
}
