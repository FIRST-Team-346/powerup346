package org.usfirst.frc.team346.robot;

import org.usfirst.frc.team346.auto.AutoRunner;
import org.usfirst.frc.team346.subsystems.Climber;
import org.usfirst.frc.team346.subsystems.Drive;
import org.usfirst.frc.team346.subsystems.Drive.DriveMode;
import org.usfirst.frc.team346.subsystems.Gyro;
import org.usfirst.frc.team346.subsystems.Intake;
import org.usfirst.frc.team346.subsystems.Shooter;
import org.usfirst.frc.team346.subsystems.Tilter;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends IterativeRobot{
	
	public Drive sDrive;
	public Gyro sGyro;
	public Intake sIntake;
	public Tilter sTilter;
	public Shooter sShooter;
	public Climber sClimber;
	private Compressor sCompressor;
	private DriverStation sDriverStation;
	
	private AutoRunner sAutoRunner;
	private ControlBoard sControlBoard;
	
	public Robot() {
		this.robotInit();
	}
	
	public void robotInit() {
//		this.sDrive = Drive.getInstance();
//		this.sGyro = Gyro.getInstance();
		this.sIntake = Intake.getInstance();
//		this.sTilter = Tilter.getInstance();
//		this.sShooter = Shooter.getInstance();
//		this.sClimber = Climber.getInstance();
		this.sDriverStation = DriverStation.getInstance();
		
//		this.sCompressor = new Compressor();
//	    this.sCompressor.start();
		
		this.sAutoRunner = new AutoRunner(this);
		this.sControlBoard = new ControlBoard(this);
	}
	
	public void autonomousInit() {
		System.out.println("Autonomous Init| begun");
		this.zeroDevices();
		this.sAutoRunner.run();
		System.out.println("Autonomous Init| complete");
	}

	public void autonomousPeriodic() {
//		this.sDrive.publishData();
//		this.sGyro.publishData();
//		this.sIntake.publishData();
//		this.sTurretTilter.publishData();
	}

	public void teleopInit() {
		System.out.println("Teleop Init| begun");
//		this.sCompressor.start();
//		this.zeroDevices();
//		this.sDrive.drive(DriveMode.PERCENT, 0, 0);
		
		System.out.println("Field layout: " + this.sAutoRunner.getLayout());
		System.out.println("Teleop Init| complete");
	}

	public void teleopPeriodic() {
//		this.sControlBoard.drive();
//		this.sControlBoard.checkIntake();
		this.sControlBoard.checkLoader();
//		this.sControlBoard.checkTilter();
//		this.sControlBoard.checkShooter();
//		this.sControlBoard.checkClimber();
		
//		this.sDrive.publishData();
//		this.sGyro.publishData();
//		this.sIntake.publishData();
//		this.sTurretTilter.publishData();
//		this.sTurretShooter.publishData();
//		this.sClimber.publishData();
	}
	
	public void zeroDevices() {
		this.sGyro.zeroGyro();
		this.sDrive.zeroEncoders();
	}
	
	public void zeroGyro() {
		this.sGyro.zeroGyro();
	}

}