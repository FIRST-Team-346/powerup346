package org.usfirst.frc.team346.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import org.usfirst.frc.team346.subsystem.Drive;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends IterativeRobot{
		
	private Drive s_drive;
	
	private DriverStation driverStation = DriverStation.getInstance();
	

	
	public void robotInit() {
		s_drive = Drive.getInstance();
	}


	
	public void autonomousInit() {
//		System.out.println(driverStation.getGameSpecificMessage().charAt(0));
//		System.out.println(driverStation.getGameSpecificMessage().charAt(1));
//		System.out.println(driverStation.getGameSpecificMessage().charAt(2));
	}

	public void autonomousPeriodic() {
//		System.out.println(driverStation.getGameSpecificMessage().charAt(0));
//		System.out.println(driverStation.getGameSpecificMessage().charAt(1));
//		System.out.println(driverStation.getGameSpecificMessage().charAt(2));
	}


	
	
	public void teleopInit() {
		
	}

	
	@Override
	public void teleopPeriodic() {
	
	}

}
