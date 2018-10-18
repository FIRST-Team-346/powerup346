package org.usfirst.frc.team346.auto.plans.safe;

import org.usfirst.frc.team346.auto.AutoPlan;
import org.usfirst.frc.team346.auto.actions.*;
import org.usfirst.frc.team346.robot.Robot;
import org.usfirst.frc.team346.robot.RobotMap;
import org.usfirst.frc.team346.subsystems.Drive;
import org.usfirst.frc.team346.subsystems.Gyro;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.Preferences;

public class Test extends AutoPlan{

	ActionRunner mAction = new ActionRunner();
	Preferences pref = Preferences.getInstance();
	
	public String getGoal() {
		return "test";
	}
	
	public void run(double _startingLeft, double _switchLeft, double _scaleLeft) {
//		Gyro.getInstance().calibrate();
		
		super.driveUsingDF(this.pref.getDouble("dfDistance", 0));
//		super.rotateUsingRT(this.pref.getDouble("rtAngle", 0));
		
		super.waitTime(1);
		System.out.println("Final Distance: " + Drive.getInstance().getAveragedPosition()/1024.*1.037);
		
		
		//Remember to take out pref from DF and RT
	}
	
}