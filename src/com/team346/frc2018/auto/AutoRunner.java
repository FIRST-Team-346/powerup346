package com.team346.frc2018.auto;

import com.team346.frc2018.auto.step.Step;

public abstract class AutoRunner {
	protected boolean m_running = false;
	protected double m_update_rate = 0.02;
	
	protected abstract void perform() throws AutoTerminatedException;
	
	public void run() {
		m_running = true;
		System.out.println("Auto Runner| booting up");
		try {
			perform();
		}
		catch(AutoTerminatedException e) {
			System.out.println("Auto Runner| complete, terminated unexpectedly");
			return;
		}
		
		complete();
		System.out.println("Auto Runner| complete, terminated expectedly");
	}
	
	public void complete() {
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
	
	public void run(Step _step) throws AutoTerminatedException {
		isRunning();
		_step.begin();
		
		while(isRunning() && !_step.isComplete()) {
			_step.update();
			waitTime(m_update_rate);
		}
		
		_step.complete();
	}
	
	public void waitTime(double _seconds) {
		long initialTime = System.currentTimeMillis();
		while(System.currentTimeMillis() - initialTime < _seconds * 1000) {
		}
	}
}
