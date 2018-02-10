package org.usfirst.frc.team346.robot;

import org.usfirst.frc.team346.auto.AutoRunner;
import org.usfirst.frc.team346.subsystems.Climber;
import org.usfirst.frc.team346.subsystems.Drive;
import org.usfirst.frc.team346.subsystems.Drive.DriveMode;
import org.usfirst.frc.team346.subsystems.Gyro;
import org.usfirst.frc.team346.subsystems.Intake;
import org.usfirst.frc.team346.subsystems.Loader;
import org.usfirst.frc.team346.subsystems.Outtake;
import org.usfirst.frc.team346.subsystems.Shooter;
import org.usfirst.frc.team346.subsystems.Tilter;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends IterativeRobot {
	
	public Drive sDrive;
	public Gyro sGyro;
	public Intake sIntake;
	public Outtake sOuttake;
	public Loader sLoader;
	public Tilter sTilter;
	public Shooter sShooter;
	public Climber sClimber;
	
	@SuppressWarnings("unused")
	private Compressor sCompressor;
	private DriverStation sDriverStation;
	
	private ControlBoard sControlBoard;
	private AutoRunner sAutoRunner;
	
	private double mPreviousTime;
	private final double kUpdateRateMillis = 100;
	
	public Robot() {
		this.robotInit();
	}
	
	public void robotInit() {
		this.sDrive = Drive.getInstance();
		this.sGyro = Gyro.getInstance();
		
		this.sIntake = Intake.getInstance();
		this.sOuttake = Outtake.getInstance();
		this.sLoader = Loader.getInstance();
		
		this.sTilter = Tilter.getInstance();
		this.sShooter = Shooter.getInstance();
		
//		this.sClimber = Climber.getInstance();
		
//		this.sCompressor = new Compressor();
		this.sDriverStation = DriverStation.getInstance();
		
		this.sControlBoard = new ControlBoard(this);
		this.sAutoRunner = new AutoRunner(this);
		
		this.mPreviousTime = System.currentTimeMillis();
	}
	
	public void autonomousInit() {
		System.out.println("Autonomous Init| begun");
		this.zeroDevices();
		
		this.sAutoRunner.run();
		
		System.out.println("Autonomous Init| complete");
	}

	public void autonomousPeriodic() {
		this.publishData();
	}

	public void teleopInit() {
		System.out.println("Teleop Init| begun");
		System.out.println("Field layout: " + this.sAutoRunner.getLayout());
		this.zeroDevices();
//		this.sCompressor.start();
		
		System.out.println("Teleop Init| complete");
	}

	public void teleopPeriodic() {
		this.sControlBoard.drive();
		this.sControlBoard.checkIntake();
		this.sControlBoard.checkLoader();
		this.sControlBoard.checkOuttake();
		this.sControlBoard.checkTilter();
		this.sControlBoard.checkShooter();
//		this.sControlBoard.checkShooterBlock();
//		this.sControlBoard.checkClimber();
		
		this.publishData();
	}
	
	public void publishData() {
		if(System.currentTimeMillis() - this.mPreviousTime > this.kUpdateRateMillis) {
			this.mPreviousTime = System.currentTimeMillis();
			
			if(this.sDriverStation.isAutonomous()) {
				this.sDrive.publishData();
				this.sGyro.publishData();
			}
			else if(this.sDriverStation.isOperatorControl() || this.sDriverStation.isTest()) {
				this.sDrive.publishData();
				this.sGyro.publishData();
//				this.sIntake.publishData();
//				this.sOuttake.publishData();
//				this.sLoader.publishData();
				this.sTilter.publishData();
//				this.sShooter.publishData();
//				this.sClimber.publishData();
			}
		}
	}
	
	public void disabledInit() {
		this.zeroDevices();
		this.sDrive.drive(DriveMode.PERCENT, 0.0, 0.0);
		this.sTilter.disable();
		this.sShooter.disable();
		
	}
	
	public void zeroDevices() {
		this.sGyro.zeroGyro();
		this.sDrive.zeroEncoders();
		
		this.sDrive.setNominal(0);
		this.sDrive.drive(DriveMode.PERCENT, 0, 0);
	}

}