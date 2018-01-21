package org.usfirst.frc.team346.subsystems;

public class TurretShooter implements Subsystem {

	private static TurretShooter sTurretShooterInstance = new TurretShooter();
	protected TurretShooter() {
		initTalons();
	}
	
	public static TurretShooter getInstance() {
		return sTurretShooterInstance;
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
