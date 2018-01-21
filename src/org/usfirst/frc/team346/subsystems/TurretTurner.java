package org.usfirst.frc.team346.subsystems;

public class TurretTurner implements Subsystem {

	private static TurretTurner sTurretTurnerInstance = new TurretTurner();
	protected TurretTurner() {
		initTalons();
	}
	
	public static TurretTurner getInstance() {
		return sTurretTurnerInstance;
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
