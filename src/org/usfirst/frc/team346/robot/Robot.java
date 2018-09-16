package org.usfirst.frc.team346.robot;// this is just the location of this class

/*
 *These are all imports from other classes
 *you need to add these imports to be able to
 *use the things you've created in those other classes
 *such as the Drivetrain or Climber
 */
import org.usfirst.frc.team346.auto.AutoBuilder;
import org.usfirst.frc.team346.auto.AutoPlan;
import org.usfirst.frc.team346.auto.AutoRunner;
import org.usfirst.frc.team346.auto.plans.*;
import org.usfirst.frc.team346.auto.plans.safe.*;
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

/*
 * These are all imports from the FIRST API 
 * to allow us to use those methods and objects
 */
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
	
	
	/*
	 * Here we initialize all the objects and variables
	 * we are going to use. Put initializations at the beginning
	 * of every class to keep things organized. 
	 */
	public Drive sDrive;
	public Gyro sGyro;
	public Intake sIntake;
	public Outtake sOuttake;
	public Loader sLoader;
	public Tilter sTilter;
	public Shooter sShooter;
	public Climber sClimber;
	public Lights sLights;
	
	/*
	 * SendableChoosers are option select boxes on the SmartDashboard that allow
	 *  you to change certain variables without having to resend code. These are
	 *  very useful for changing autos in an instant when your teammates inevitably
	 *  lie to you about what auto they're running and don't correct themselves until
	 *  a minute before the match. JUST MAKE SURE TO TEST THE SENDABLECHOOSER at
	 *  school and on the practice field at competitions.
	 */
	public SendableChooser<AutoBuilder> autoChooser;
	public SendableChooser<Boolean> autoStartingOnLeft;
	
	public SendableChooser<AutoPlan> autoBBChooser;
	public SendableChooser<AutoPlan> autoBGChooser;
	public SendableChooser<AutoPlan> autoGBChooser;
	public SendableChooser<AutoPlan> autoGGChooser;
	
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
		/*
		 * The roboitInit method is called when the robot is just turned on.
		 * Here we simply give values to all of the objects we initialized earlier.
		 * The getInstance method just gives one version of an object.
		 * We use getInstance to make sure we don't create duplicates of objects.
		 */
		this.sDrive = Drive.getInstance();
		this.sGyro = Gyro.getInstance();
		
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
		
		/*
		 * The steps of a SendableChooser are instantiating it (up there ^),
		 *  initializing it (here v), adding a default option, adding
		 *  all your other options. Then you need to use SmartDashboard.putData
		 *  to actually make it show up on SmartDashboard.
		 */
		this.autoStartingOnLeft = new SendableChooser<Boolean>();
		this.autoStartingOnLeft.addDefault("Left", true);
		this.autoStartingOnLeft.addObject("Right", false);
		SmartDashboard.putData("AutoStartingOnLeft", this.autoStartingOnLeft);
		
		//This is a method I made to clean up the autos *a little bit*.
		this.initAutoCustomBuilder();
		
		//Tons of options for what autos to run. These are just some quick presets.
		this.autoChooser = new SendableChooser<AutoBuilder>();
		this.autoChooser.addDefault("2-Scale/Conduit", new AutoBuilder( new GoodScaleTwice(), new GoodScaleTwice(), new CrossConduit(), new CrossConduit() ));
		this.autoChooser.addObject("CenterSwitch", new AutoBuilder( new CenterSwitch() ));
		this.autoChooser.addObject("CenterSwitchFaster", new AutoBuilder( new CenterSwitchFaster()));
		this.autoChooser.addObject("CenterSwitchSingle", new AutoBuilder( new CenterSwitchSingleSide()));
		this.autoChooser.addObject("AllScale", new AutoBuilder( new GoodScaleTwice(), new GoodScaleTwice(), new BadScale(), new BadScale() ));
		this.autoChooser.addObject("AllScale+GGScSw", new AutoBuilder( new GoodScaleGoodSwitch(), new GoodScaleTwice(), new BadScale(), new BadScale() ));
		this.autoChooser.addObject("GoodDoubles", new AutoBuilder( new GoodScaleTwice(), new GoodScaleTwice(), new GoodSwitchTwice(), new BadScale() ));
		this.autoChooser.addObject("GoodDoubles+GGScSw", new AutoBuilder( new GoodScaleGoodSwitch(), new GoodScaleTwice(), new GoodSwitchTwice(), new BadScale() ));
		this.autoChooser.addObject("GoodDoublesNoCross", new AutoBuilder( new GoodScaleTwice(), new GoodScaleTwice(), new GoodSwitchTwice(), new CrossBaseline() ));
		this.autoChooser.addObject("GoodDoublesNoCross+GGScSw", new AutoBuilder( new GoodScaleGoodSwitch(), new GoodScaleTwice(), new GoodSwitchTwice(), new CrossBaseline() ));
		this.autoChooser.addObject("ScaleBackAway/Conduit", new AutoBuilder( new GoodScaleBackAway(), new GoodScaleBackAway(), new CrossConduit(), new CrossConduit() ));
		this.autoChooser.addObject("Baseline", new AutoBuilder( new CrossBaseline() ));
		this.autoChooser.addObject("Custom", new AutoBuilder(this.autoGGChooser.getSelected(), this.autoBGChooser.getSelected(), this.autoGBChooser.getSelected(), this.autoBBChooser.getSelected()));
		
		SmartDashboard.putData("AutoChooser", this.autoChooser);
		SmartDashboard.putString("AutoSel", this.autoChooser.getSelected().getGoals());
		
		/*
		 * This is very important.
		 * The gyro needs to be calibrated to avoid it drifting significantly
		 * Drift is bad. Drift makes robot move wrong.
		 * Calibrating takes about 5 seconds and needs to be done while the robot is still.
		 * Thats why we put it in robotInit. It will run right when the robot is turned on
		 * which will be a good bit before the match starts and the robot moves.
		 */
		this.sGyro.calibrate();
	}
	
	/*
	 * This method is used for a custom auto builder. If we don't have a preset
	 *  that has what you want, you can select the "Custom" auto to run, then
	 *  select individual plans for the four field layouts.
	 */
	public void initAutoCustomBuilder() {
		this.autoBBChooser = new SendableChooser<AutoPlan>();
		this.autoBBChooser.addDefault("BadScale", new BadScale());
		this.autoBBChooser.addObject("CrossConduit", new CrossConduit());
		this.autoBBChooser.addObject("CrossBaseline", new CrossBaseline());
		SmartDashboard.putData("BadSwBadSc", this.autoBBChooser);
		
		this.autoBGChooser = new SendableChooser<AutoPlan>();
		this.autoBGChooser.addDefault("GoodScaleTwice", new GoodScaleTwice());
		this.autoBGChooser.addObject("GoodScaleNull", new GoodScaleNull());
		this.autoBGChooser.addObject("GoodScaleBackAway", new GoodScaleBackAway());
		this.autoBGChooser.addObject("CrossBaseline", new CrossBaseline());
		SmartDashboard.putData("BadSwGoodSc", this.autoBGChooser);
		
		this.autoGBChooser = new SendableChooser<AutoPlan>();
		this.autoGBChooser.addDefault("BadScale", new BadScale());
		this.autoGBChooser.addObject("GoodSwitchTwice", new GoodSwitchTwice());
		this.autoGBChooser.addObject("GoodSwitchOnce", new GoodSwitchOnce());
		this.autoGBChooser.addObject("GoodSwitchThenCube", new GoodSwitchThenCube());
		SmartDashboard.putData("GoodSwBadSc", this.autoGBChooser);
		
		this.autoGGChooser = new SendableChooser<AutoPlan>();
		this.autoGGChooser.addDefault("GoodScaleTwice", new GoodScaleTwice());
		this.autoGGChooser.addObject("GoodScaleGoodSwitch", new GoodScaleGoodSwitch());
		this.autoGGChooser.addObject("GoodScaleNull", new GoodScaleNull());
		this.autoGGChooser.addObject("GoodScaleBackAway", new GoodScaleBackAway());
		this.autoGGChooser.addObject("GoodSwitchTwice", new GoodSwitchTwice());
		this.autoGGChooser.addObject("GoodSwitchOnce", new GoodSwitchOnce());
		SmartDashboard.putData("GoodSwGoodSc", this.autoGGChooser);
	}
	
	//autonomousInit is automatically run when auto begins
	public void autonomousInit() {
		/*
		 * Printouts are important just so you know what you're code is 
		 * actually doing
		 * This also zeros all the devices so the robot doesn't think its 5 feet forward
		 * or at a 90 degree angle when auto starts. That would be bad. 
		 */
		System.out.println("Autonomous Init| begun");
		SmartDashboard.putString("AutoSel", this.autoChooser.getSelected().getGoals());
		this.zeroDevices();
		this.sLights.setGreen();
		
		/*
		 * This is what actually starts running the auto.
		 * It's parameters are gotten from the sendableChooser on the smartDashboard.
		 * Auto is by far the most complicated part of the code. I recommend having a solid
		 * understanding of the rest of the code before jumping down this rabbit hole. 
		 */
		this.sAutoRunner.run((boolean) this.autoStartingOnLeft.getSelected(), (AutoBuilder) this.autoChooser.getSelected());
		
		System.out.println("Autonomous Init| complete");
	}
	
	//autonomousPeriodic is run every 7ms during auto
	public void autonomousPeriodic() {
		//This will just print out what every susbsystems data are for tracking such as gyro angle.
		this.publishData();
	}

	//This method is called at the beginning of teleop
	public void teleopInit() {
		System.out.println("Teleop Init| begun");
		System.out.println("Field layout: " + this.sAutoRunner.getLayout());
		
		if(!this.mCameraAdded) {
			/*
			 * We manually change the cameraAdded variable depending on if we
			 * have a camera or not. If we don't it will try to start a nonexistent
			 * camera and spew out errors.
			 * It also makes sure it doesn't try to initialize the camera multiple
			 * times. The line of code automatically finds any USB camera plugged in
			 * and puts its output on the smartDashboard.
			 */
			CameraServer.getInstance().startAutomaticCapture();
			this.mCameraAdded = true;
		}
		
		this.zeroDevices();
		
		System.out.println("Teleop Init| complete");
	}

	//This method runs every 7ms in teleop
	public void teleopPeriodic() {
		/*
		 * These all just check the buttons then do what the buttons want them to do
		 */
		this.sControlBoard.drive();
		this.sControlBoard.checkIntake();
		this.sControlBoard.checkLoader();
		this.sControlBoard.checkOuttake();
		this.sControlBoard.checkTilter();
		this.sControlBoard.checkShooter();
		this.sControlBoard.checkClimber();
		this.sControlBoard.checkLights();
		
		//Data is important. Look at it. 
		this.publishData();
	}
	
	public void publishData() {
		/*
		 * This will make it run only after a set time. The main reason for
		 * adding this is because every time you read the gyro it drifts a little 
		 * more so reading less=less drift= more accuracy.
		 * 
		 */
		if(System.currentTimeMillis() - this.mPreviousTime > this.kUpdateRateMillis) {
			this.mPreviousTime = System.currentTimeMillis();
			
			//Only uncomment the data you want to look at
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
				this.sShooter.publishData();
				this.sClimber.publishData();
			}
		}
	}
	
	//this is ran when you disable the robot
	public void disabledInit() {
		/*
		 * When the robot is disabled you want it to turn things off.
		 * So these just turn everything off.
		 */
		this.zeroDevices();
		this.sClimber.disable();
		this.sTilter.disable();
		this.sShooter.setPercentFront(0);
		this.sShooter.disable();
		this.sLights.setBlack();
	}
	
	//This is pretty self explanatory you're smart you'll figure it out
	public void zeroDevices() {
		this.sGyro.zeroGyro();
		this.sDrive.zeroEncoders();
		
		this.sDrive.setNominal(0,0);
		this.sDrive.drive(DriveMode.PERCENT, 0.0, 0.0);
	}

}