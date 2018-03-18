package org.usfirst.frc.team346.robot;

import org.usfirst.frc.team346.auto.AutoBuilder;
import org.usfirst.frc.team346.auto.AutoRunner;
import org.usfirst.frc.team346.auto.plans.*;
import org.usfirst.frc.team346.auto.plans.firstcomp.NewBadScale;
import org.usfirst.frc.team346.auto.plans.firstcomp.NewCenterStart;
import org.usfirst.frc.team346.auto.plans.firstcomp.NewGoodScaleMaybeSwitch;
import org.usfirst.frc.team346.auto.plans.firstcomp.NewGoodSwitchBadScale;
import org.usfirst.frc.team346.auto.plans.safe.CrossBaseline;
import org.usfirst.frc.team346.auto.plans.safe.Nothing;
import org.usfirst.frc.team346.auto.plans.safe.Test;
import org.usfirst.frc.team346.subsystems.Climber;
import org.usfirst.frc.team346.subsystems.Drive;
import org.usfirst.frc.team346.subsystems.Drive.DriveMode;
import org.usfirst.frc.team346.subsystems.Gyro;
import org.usfirst.frc.team346.subsystems.Intake;
import org.usfirst.frc.team346.subsystems.Loader;
import org.usfirst.frc.team346.subsystems.Outtake;
import org.usfirst.frc.team346.subsystems.Lights;
import org.usfirst.frc.team346.subsystems.Shooter;
import org.usfirst.frc.team346.subsystems.Tilter;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


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
	public Lights sLights;
	
	public SendableChooser<AutoBuilder> autoChooser;
	public SendableChooser<Boolean> autoStartingOnLeft;
	
	@SuppressWarnings("unused")
	private Compressor sCompressor;
	private DriverStation sDriverStation;
	
	private ControlBoard sControlBoard;
	private AutoRunner sAutoRunner;
	
	private double mPreviousTime;
	private final double kUpdateRateMillis = 100;
	private boolean mCameraAdded;
	
	public Robot() {
		this.robotInit();
	}
	
	public void robotInit() {
		this.sDrive = Drive.getInstance();
		this.sGyro = Gyro.getInstance();
		this.sGyro.calibrate();
		
		this.sIntake = Intake.getInstance();
		this.sOuttake = Outtake.getInstance();
		this.sLoader = Loader.getInstance();
		
		this.sTilter = Tilter.getInstance();
		this.sShooter = Shooter.getInstance();
		
		this.sLights = Lights.getInstance();
		
		this.sClimber = Climber.getInstance();
		
		this.sDriverStation = DriverStation.getInstance();
		
		this.sControlBoard = new ControlBoard(this);
		
		this.mPreviousTime = System.currentTimeMillis();
		this.mCameraAdded = false;
		
		this.sAutoRunner = new AutoRunner();
		
		this.autoStartingOnLeft = new SendableChooser<Boolean>();
		this.autoStartingOnLeft.addDefault("Left", true);
		this.autoStartingOnLeft.addObject("Right", false);
		SmartDashboard.putData("AutoStartingOnLeft", this.autoStartingOnLeft);
		
		this.autoChooser = new SendableChooser<AutoBuilder>();
		this.autoChooser.addDefault("CenterSwitchVault", new AutoBuilder( new CenterSwitchVault() ));
		this.autoChooser.addObject("SideSwitchPriority", new AutoBuilder( new SwitchPGoodSwitchTwice(), new SwitchPBadSwitchTwice(), new SwitchPGoodSwitchThenCrossIntake(), new SwitchPBadSwitchTwice() ));
		this.autoChooser.addObject("SideScalePriority", new AutoBuilder( new ScalePGoodScaleTwice(), new ScalePGoodScaleTwice(), new ScalePBadScaleMaybeBadSwitch(), new ScalePBadScaleMaybeBadSwitch() ));
		this.autoChooser.addObject("Baseline", new AutoBuilder( new CrossBaseline() ));
		this.autoChooser.addObject("Nothing", new AutoBuilder( new Nothing() ));
		this.autoChooser.addObject("Test", new AutoBuilder( new Test() ));
		SmartDashboard.putData("AutoChooser", this.autoChooser);
		
		this.sGyro.calibrate();
	}
	
	public void autonomousInit() {
		System.out.println("Autonomous Init| begun");
		this.zeroDevices();
		
		this.sAutoRunner.run((boolean) this.autoStartingOnLeft.getSelected(), (AutoBuilder) this.autoChooser.getSelected());
		
		System.out.println("Autonomous Init| complete");
	}

	public void autonomousPeriodic() {
		this.publishData();
		if(DriverStation.getInstance().getMatchTime() >= 0) {
			this.sLights.setGreen();
		}
		else {
			this.sLights.setRed();
		}
	}

	public void teleopInit() {
		System.out.println("Teleop Init| begun");
		System.out.println("Field layout: " + this.sAutoRunner.getLayout());
		
		if(!this.mCameraAdded) {
			CameraServer.getInstance().startAutomaticCapture();
			this.mCameraAdded = true;
		}
		
		this.zeroDevices();
		
		System.out.println("Teleop Init| complete");
	}

	public void teleopPeriodic() {
		this.sControlBoard.drive();
		this.sControlBoard.checkIntake();
		this.sControlBoard.checkLoader();
		this.sControlBoard.checkOuttake();
		this.sControlBoard.checkTilter();
		this.sControlBoard.checkShooter();
		this.sControlBoard.checkClimber();
		this.sControlBoard.checkLights();
		
		this.publishData();
	}
	
	public void publishData() {
		if(System.currentTimeMillis() - this.mPreviousTime > this.kUpdateRateMillis) {
			this.mPreviousTime = System.currentTimeMillis();
			
			if(this.sDriverStation.isAutonomous()) {
				this.sDrive.publishData();
//				this.sGyro.publishData();
			}
			else if(this.sDriverStation.isOperatorControl() || this.sDriverStation.isTest()) {
				this.sDrive.publishData();
//				this.sGyro.publishData();
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
		this.sClimber.disable();
		this.sTilter.disable();
		this.sShooter.setPercentFront(0);
		this.sShooter.disable();
		this.sLights.setBlack();
	}
	
	public void zeroDevices() {
		this.sGyro.zeroGyro();
		this.sDrive.zeroEncoders();
		
		this.sDrive.setNominal(0,0);
		this.sDrive.drive(DriveMode.PERCENT, 0.0, 0.0);
	}

}