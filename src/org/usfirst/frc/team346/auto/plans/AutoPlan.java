package org.usfirst.frc.team346.auto.plans;

import edu.wpi.first.wpilibj.DriverStation;

public interface AutoPlan {
	
	public String getGoal();
	
	public void run();
	
}