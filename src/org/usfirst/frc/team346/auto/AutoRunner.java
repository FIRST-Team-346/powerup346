package org.usfirst.frc.team346.auto;

import edu.wpi.first.wpilibj.DriverStation;

public abstract class AutoRunner {
	protected boolean m_running = false;
	protected double m_update_rate = 0.02;
	
	public enum AutoPlan {
		
	}
	
	DriverStation sDriverStation;
	static String mLayout;
	
	
	public void run() {
		m_running = true;
		System.out.println("Auto Runner| booting up");
		receiveLayout();
		try {
			perform();
		}
		catch(AutoTerminatedException e) {
			System.out.println("Auto Runner| complete, terminated unexpectedly");
			return;
		}
		
		complete();
	}
	
	private void receiveLayout() {
		sDriverStation = DriverStation.getInstance();
//		mLayout = sDriverStation.getGameSpecificMessage();
	}
	
	protected void perform() throws AutoTerminatedException {
		
	};
	
	public void complete() {
		System.out.println("Auto Runner| complete");
	}
	
	public void terminate() {
		m_running = false;
	}
	
	public boolean isRunning() throws AutoTerminatedException {
		if(!isRunning()) {
			throw new AutoTerminatedException();
		}
		
		return isRunning();
	}
	
//	public void run(Step _step) throws AutoTerminatedException {
//		isRunning();
//		_step.begin();
		
//		while(isRunning() && !_step.isComplete()) {
//			_step.update();
//			waitTime(m_update_rate);
//		}
		
//		_step.complete();
//	}
	
	public void waitTime(double _seconds) {
		long initialTime = System.currentTimeMillis();
		while(System.currentTimeMillis() - initialTime < _seconds * 1000) {
		}
	}
}
