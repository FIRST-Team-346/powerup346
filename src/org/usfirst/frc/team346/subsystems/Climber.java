package org.usfirst.frc.team346.subsystems;

public class Climber implements Subsystem {

	private static Climber sClimberInstance = new Climber();
	protected Climber() {
		initTalons();
	}
	
	public static Climber getInstance() {
		return sClimberInstance;
	}
	
	private void initTalons() {
		
	}

	public void disable() {
		//TODO
	}

	public void publishData() {
		//TODO
	}
	
}
