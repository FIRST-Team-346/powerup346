package org.usfirst.frc.team346.subsystems;

public class TurretTilter implements Subsystem {

	private static TurretTilter sTurretTilterInstance = new TurretTilter();
	protected TurretTilter() {
		initTalons();
	}
	
	public static TurretTilter getInstance() {
		return sTurretTilterInstance;
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
