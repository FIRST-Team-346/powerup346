package org.usfirst.frc.team346.subsystems;

import org.usfirst.frc.team346.robot.RobotMap;
import org.usfirst.frc.team346.subsystems.Subsystem;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Drive implements Subsystem{
	
	/*
	 * You need to initialize things at the beginning of every class because
	 * you can't give them values till the robot is turned on, which is done in the init 
	 * method, but you need them to be initialized not in a method or other places won't
	 * kniw they exist
	 */
	private TalonSRX mDriveLeftMaster, mDriveLeftSlave1, mDriveLeftSlave2;
	private TalonSRX mDriveRightMaster, mDriveRightSlave1, mDriveRightSlave2;
	
	private final int PID_VEL = 0, PID_POS = 1;
	private double mTurn;
	private double mSecondsFromNeutralToFull = 0.25;
	private double leftNominal = 0, rightNominal = 0;
	
	private double leftMaxVel = 0, rightMaxVel = 0, mPrevTime, mPrevVel = 0, mPrevAccel = 0;
	
	/*
	 * So enums are a very useful way to optimize your method input. Try just googling
	 * what they are and how to use them. Here each variable of the enum makes it so the 
	 * drive() method controls the drive train in a different way. 
	 */
	public enum DriveMode {
		PERCENT,
		PERCENT_VELOCITY,
		VELOCITY,
		POSITION;
	}
	
	//Look up singleton classes. That's what this getInstance() things is. 
	private static Drive sDriveInstance = new Drive();
	public static Drive getInstance() {
		return sDriveInstance;
	}
	
	/*
	 * So this is the Drive class. Each class will create an object of the same name.
	 * When a version of the object is created it runs what is in this constructor.
	 * We just have the constructor run all the inits.
	 */

	//Every object will have a constructor. This is not a method. 
	protected Drive() {
		this.initTalons();
		this.initEncoders();
		this.setSpeedPIDs();
	}
	
	//This initializes all the talons...duh.
	private void initTalons() {
		
		this.mDriveLeftMaster = new TalonSRX(RobotMap.kDriveLeftMasterPort); //makes the talon with the id RobotMap.kDriveLeftMasterPort mDriveLeftMaster
		this.mDriveLeftMaster.set(ControlMode.PercentOutput, 0);//This just sets the talon to 0V so it doesnt start out spinning
		this.mDriveLeftMaster.setNeutralMode(NeutralMode.Brake);//This will make the talon try to stop the motor when it is told to turn off. Alternative is coast/
		this.mDriveLeftMaster.overrideLimitSwitchesEnable(true);//These next 2 don't really need to be here they were just to test things don't know why we didn't get rid of them. 
		this.mDriveLeftMaster.overrideSoftLimitsEnable(true);
		
		/*
		 * All of the motors that are on the same side of the drive just need to do the same thing.
		 * Because of that it would be a waste to tell all three to do thing same thing every time
		 * we want them to do something. So we have the master/slave methods(we didn't come up with 
		 * that it's just a thing don't be offended). We set the slaves to follow(the master) then all
		 * we have to do is tell the master to do things and the slaves will just do the exact same thing
		 */
		this.mDriveLeftSlave1 = new TalonSRX(RobotMap.kDriveLeftSlave1Port);//creates talon name on id x
		this.mDriveLeftSlave1.set(ControlMode.Follower, RobotMap.kDriveLeftMasterPort);//Makes this talon follow - aka do exactly what - its master talon is 
		this.mDriveLeftSlave1.setNeutralMode(NeutralMode.Brake);
		this.mDriveLeftSlave1.follow(mDriveLeftMaster);//Same thing. tells it to follow another talon
		this.mDriveLeftSlave1.overrideLimitSwitchesEnable(true);
		this.mDriveLeftSlave1.overrideSoftLimitsEnable(true);
		
		this.mDriveLeftSlave2 = new TalonSRX(RobotMap.kDriveLeftSlave2Port);
		this.mDriveLeftSlave2.set(ControlMode.Follower, RobotMap.kDriveLeftMasterPort);
		this.mDriveLeftSlave2.setNeutralMode(NeutralMode.Brake);
		this.mDriveLeftSlave2.follow(mDriveLeftMaster);
		this.mDriveLeftSlave2.overrideLimitSwitchesEnable(true);
		this.mDriveLeftSlave2.overrideSoftLimitsEnable(true);
		
		this.mDriveRightMaster = new TalonSRX(RobotMap.kDriveRightMasterPort);
		this.mDriveRightMaster.set(ControlMode.PercentOutput, 0);
		this.mDriveRightMaster.setNeutralMode(NeutralMode.Brake);
		this.mDriveRightMaster.overrideLimitSwitchesEnable(true);
		this.mDriveRightMaster.overrideSoftLimitsEnable(true);
		
		this.mDriveRightSlave1 = new TalonSRX(RobotMap.kDriveRightSlave1Port);
		this.mDriveRightSlave1.set(ControlMode.Follower, RobotMap.kDriveRightMasterPort);
		this.mDriveRightSlave1.setNeutralMode(NeutralMode.Brake);
		this.mDriveRightSlave1.follow(mDriveRightMaster);
		this.mDriveRightSlave1.overrideLimitSwitchesEnable(true);
		this.mDriveRightSlave1.overrideSoftLimitsEnable(true);
		
		this.mDriveRightSlave2 = new TalonSRX(RobotMap.kDriveRightSlave2Port);
		this.mDriveRightSlave2.set(ControlMode.Follower, RobotMap.kDriveRightMasterPort);
		this.mDriveRightSlave2.setNeutralMode(NeutralMode.Brake);		
		this.mDriveRightSlave2.follow(mDriveRightMaster);
		this.mDriveRightSlave2.overrideLimitSwitchesEnable(true);
		this.mDriveRightSlave2.overrideSoftLimitsEnable(true);
		
		//Here's a challenge look up the TalonSRX API and find out what these methods do
		this.mDriveLeftMaster.configClosedloopRamp(this.mSecondsFromNeutralToFull, 0);
		this.mDriveRightMaster.configClosedloopRamp(this.mSecondsFromNeutralToFull, 0);
		this.mDriveLeftMaster.configOpenloopRamp(this.mSecondsFromNeutralToFull, 0);
		this.mDriveRightMaster.configOpenloopRamp(this.mSecondsFromNeutralToFull, 0);
	}
	
	/**Initializes the encoders on the master CANTalons.**/
	private void initEncoders() {
		//This will init the encoder plugged into the Talon
		this.mDriveLeftMaster.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		/*
		 * These are to make sure that forward is forward and that positive voltage is where
		 * you want it to go. To figure these out you just have to run the talons and see
		 * what direction they start outputting then changing these booleans as appropriate
		 */
		this.mDriveRightMaster.setInverted(false); 
		this.mDriveRightMaster.setSensorPhase(false);
		
		this.mDriveRightMaster.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		this.mDriveRightMaster.setInverted(false);
		this.mDriveRightMaster.setSensorPhase(false);
	}
	
	/**Initializes the PID values on the master encoders.**/
	public void setPosPIDs() {
		this.mDriveLeftMaster.config_kP(PID_POS, RobotMap.kDrivePosLeftP, 0);
		this.mDriveLeftMaster.config_kI(PID_POS, RobotMap.kDrivePosLeftI, 0);
		this.mDriveLeftMaster.config_kD(PID_POS, RobotMap.kDrivePosLeftD, 0);
		this.mDriveLeftMaster.config_kF(PID_POS, RobotMap.kDrivePosLeftF, 0);
		
		this.mDriveRightMaster.config_kP(PID_POS, RobotMap.kDrivePosRightP, 0);
		this.mDriveRightMaster.config_kI(PID_POS, RobotMap.kDrivePosRightI, 0);
		this.mDriveRightMaster.config_kD(PID_POS, RobotMap.kDrivePosRightD, 0);
		this.mDriveRightMaster.config_kF(PID_POS, RobotMap.kDrivePosRightF, 0);
	}
	
	/*
	 * You may notice we have seperate PIDs. Position mode and speed mode need different pids to get
	 * good results but you can only have one set of values stored. Make sure to set the pids to the
	 * desired drive method before running that drive methods
	 */
	public void setSpeedPIDs() {
		this.mDriveLeftMaster.config_kP(PID_VEL, RobotMap.kDriveVelLeftP, 0);
		this.mDriveLeftMaster.config_kI(PID_VEL, RobotMap.kDriveVelLeftI, 0);
		this.mDriveLeftMaster.config_kD(PID_VEL, RobotMap.kDriveVelLeftD, 0);
		this.mDriveLeftMaster.config_kF(PID_VEL, RobotMap.kDriveVelLeftF, 0);
		
		this.mDriveRightMaster.config_kP(PID_VEL, RobotMap.kDriveVelRightP, 0);
		this.mDriveRightMaster.config_kI(PID_VEL, RobotMap.kDriveVelRightI, 0);
		this.mDriveRightMaster.config_kD(PID_VEL, RobotMap.kDriveVelRightD, 0);
		this.mDriveRightMaster.config_kF(PID_VEL, RobotMap.kDriveVelRightF, 0);
	}
	
	/*
	 *This is the method that actually drives the motors. This is where that DriveMode enum from
	 *earlier comes into play. Based on what DriveMode is input when the method is called it will 
	 *run a different method. It also takes in a left and right input which could be percentSpeed, Velocity,
	 *PercentVelocity, or a position.
	 */
	public void drive(DriveMode _mode, double _left, double _right) {
		switch(_mode) {
			case PERCENT : {
				/*
				 * The .set() method is built into the talon. It takes in an enum of what controlMode
				 * and what number to set that controlMode to. Kind of like our drive() method.
				 * There are lots of controlModes built into the talons look into them in the
				 * talon API. Api's are the best. 
				 */
				this.mDriveLeftMaster.set(ControlMode.PercentOutput, _left);
				this.mDriveRightMaster.set(ControlMode.PercentOutput, -_right);
			}; break;
			
			case PERCENT_VELOCITY : {
				this.mDriveLeftMaster.set(ControlMode.Velocity, _left * RobotMap.kDriveVelAverage);
				this.mDriveRightMaster.set(ControlMode.Velocity, -_right * RobotMap.kDriveVelAverage);
			};break;
		
			case VELOCITY: {
				this.mDriveLeftMaster.set(ControlMode.Velocity, _left);
				this.mDriveRightMaster.set(ControlMode.Velocity, -_right);
			}; break;
			
			case POSITION : {
				this.mDriveLeftMaster.set(ControlMode.Position, _left);
				this.mDriveRightMaster.set(ControlMode.Position, -_right);
			}; break;
		
			default : {
				System.out.println("Drive command unsuccessful: invalid drive mode specified.");
			}; break;
		}
	}
	
	/*
	 * So bickeal requested an alternate drive method like halfway through the build season so
	 * the code for it is slightly a mess. Because it does not fit the standard left right input
	 * that all the other drive methods do we put it in a separate method. Ask micheal about his 
	 * special drive method. This is how it works
	 */
	public void driveThrottleTurn(double _throttle, double _turn) {
		this.mTurn = (Math.abs(_turn) <= 0.08)? 0. : _turn*RobotMap.kThrottleTurnRotationStrength;
		_throttle = (Math.abs(_throttle) <= 0.1)? 0 : _throttle;
		
		this.mDriveLeftMaster.set(ControlMode.PercentOutput, _throttle + this.mTurn);
		this.mDriveRightMaster.set(ControlMode.PercentOutput, -_throttle + this.mTurn);
	}
	
	public void setLeftPIDs(double _P, double _I, double _D) {
		this.mDriveLeftMaster.config_kP(PID_VEL, _P, 0);
		this.mDriveLeftMaster.config_kI(PID_VEL, _I, 0);
		this.mDriveLeftMaster.config_kD(PID_VEL, _D, 0);
	}
	public void setRightPIDs(double _P, double _I, double _D) {
		this.mDriveRightMaster.config_kP(PID_VEL, _P, 0);
		this.mDriveRightMaster.config_kI(PID_VEL, _I, 0);
		this.mDriveRightMaster.config_kD(PID_VEL, _D, 0);
	}
	
	public void publishData() {
//		this.publishVoltage();
		this.publishPercent();
		this.publishVelocity();
		this.publishPosition();
//		this.publishMaxVel();
//		this.publishVelDifference();
//		this.publishAccel();
	}
	
	public void publishVoltage() {
		SmartDashboard.putNumber("DriveLeftVoltage", this.getLeftVoltage());
		SmartDashboard.putNumber("DriveRightVoltage", this.getRightVoltage());
	}
	
	public void publishPercent() {
		SmartDashboard.putNumber("DriveLeftPercent", this.getLeftPercent());
		SmartDashboard.putNumber("DriveRightPercent", this.getRightPercent());
	}
	
	public void publishPosition() {
		SmartDashboard.putNumber("DriveLeftPosition", this.getLeftPosition());
		SmartDashboard.putNumber("DriveRightPosition", this.getRightPosition());
	}
	
	public void publishVelocity() {
		SmartDashboard.putNumber("DriveLeftVelocity", this.getLeftVelocity());
		SmartDashboard.putNumber("DriveRightVelocity", this.getRightVelocity());
	}
	
	public void publishMaxVel() {
		SmartDashboard.putNumber("leftMaxVel", this.getMaxLeftVel());
		SmartDashboard.putNumber("rightMaxVel", this.getMaxRightVel());
	}
	
	public void publishAccel() {
		SmartDashboard.putNumber("DriveAccel", this.getAveragedAccel());
	}

	public void publishVelDifference() {
		SmartDashboard.putNumber("DriveVelocityDifference", 
		   Math.abs(this.getLeftVelocity())-Math.abs(this.getRightVelocity()));
	}
	
	public double getLeftVoltage() {
		return (this.mDriveLeftMaster.getMotorOutputVoltage() +
				this.mDriveLeftSlave1.getMotorOutputVoltage() +
				this.mDriveLeftSlave2.getMotorOutputVoltage()) /3.;
	}
	
	public double getRightVoltage() {
		return (this.mDriveRightMaster.getMotorOutputVoltage() +
				this.mDriveRightSlave1.getMotorOutputVoltage() +
				this.mDriveRightSlave2.getMotorOutputVoltage()) /-3.;
	}
	
	public double getLeftPercent() {
		return this.mDriveLeftMaster.getMotorOutputPercent();
	}
	
	public double getRightPercent() {
		return this.mDriveRightMaster.getMotorOutputPercent() * -1.;
	}
	
	public double getLeftPosition() {
		return this.mDriveLeftMaster.getSelectedSensorPosition(0);
	}
	
	public double getRightPosition() {
		return this.mDriveRightMaster.getSelectedSensorPosition(0) * -1.;
	}
	
	public double getLeftPositionFt() {
		return this.mDriveLeftMaster.getSelectedSensorPosition(0)/1425.;
	}
	
	public double getRightPositionFt() {
		return this.mDriveRightMaster.getSelectedSensorPosition(0)/-1425.;
	}
	
	public double getAveragedPosition() {
		return 1./2. * ( this.getLeftPosition() + this.getRightPosition() );
	}
	
	public double getLeftVelocity() {
		return this.mDriveLeftMaster.getSelectedSensorVelocity(0);
	}
	
	public double getRightVelocity() {
		return this.mDriveRightMaster.getSelectedSensorVelocity(0) * -1.;
	}
	
	public double getAveragedVelocity() {
		return 1./2. * ( this.getLeftVelocity() + this.getRightVelocity() );
	}
	
	public double getMaxLeftVel() {
		if(Math.abs(this.leftMaxVel) < Math.abs(this.mDriveLeftMaster.getSelectedSensorVelocity(0))){
			this.leftMaxVel = Math.abs(this.mDriveLeftMaster.getSelectedSensorVelocity(0));
		}
		return leftMaxVel;
	}
	
	public double getMaxRightVel() {
		if(Math.abs(this.rightMaxVel) < Math.abs(this.mDriveRightMaster.getSelectedSensorVelocity(0))){
			this.rightMaxVel = Math.abs(this.mDriveRightMaster.getSelectedSensorVelocity(0));
		}
		return rightMaxVel;
	}
	
	public double getAveragedAccel() {
		double lAccel = this.mPrevAccel;
		if(System.currentTimeMillis()/1000. - this.mPrevTime > 0.1) {
			lAccel = (this.getAveragedVelocity() - this.mPrevVel) /0.1;
			this.mPrevTime = System.currentTimeMillis() /1000.;
			this.mPrevVel = this.getAveragedVelocity();
			this.mPrevAccel = lAccel;
		}
		return lAccel;
	}
	
	public void setNominal(double _leftLimit, double _rightLimit){
//		this.mDriveLeftMaster.configNominalOutputForward(_leftLimit, 0);
//		this.mDriveRightMaster.configNominalOutputForward(_rightLimit, 0);
//		this.mDriveLeftSlave1.configNominalOutputForward(_leftLimit, 0);
//		this.mDriveLeftSlave2.configNominalOutputForward(_leftLimit, 0);
//		this.mDriveRightSlave1.configNominalOutputForward(_rightLimit, 0);
//		this.mDriveRightSlave2.configNominalOutputForward(_rightLimit, 0);
		
		SmartDashboard.putNumber("Left Nominal", leftNominal);
		SmartDashboard.putNumber("Right Nominal", rightNominal);
	}
	
	public void zeroEncoders() {
		this.mDriveLeftMaster.setSelectedSensorPosition(0, 0, 0);
		this.mDriveLeftSlave1.setSelectedSensorPosition(0, 0, 0);
		this.mDriveLeftSlave2.setSelectedSensorPosition(0, 0, 0);
		this.mDriveRightMaster.setSelectedSensorPosition(0, 0, 0);
		this.mDriveRightSlave1.setSelectedSensorPosition(0, 0, 0);
		this.mDriveRightSlave2.setSelectedSensorPosition(0, 0, 0);
		
		this.mDriveLeftMaster.configNominalOutputForward(0, 0);
		this.mDriveRightMaster.configNominalOutputForward(0, 0);
		this.mDriveLeftSlave1.configNominalOutputForward(0, 0);
		this.mDriveLeftSlave2.configNominalOutputForward(0, 0);
		this.mDriveRightSlave1.configNominalOutputForward(0, 0);
		this.mDriveRightSlave2.configNominalOutputForward(0, 0);
		
		this.mDriveLeftMaster.configPeakOutputForward(1, 0);
		this.mDriveLeftSlave1.configPeakOutputForward(1, 0);
		this.mDriveLeftSlave2.configPeakOutputForward(1, 0);
		this.mDriveRightMaster.configPeakOutputForward(1, 0);
		this.mDriveRightSlave1.configPeakOutputForward(1, 0);
		this.mDriveRightSlave2.configPeakOutputForward(1, 0);
		
		this.mDriveLeftMaster.configPeakOutputReverse(-1, 0);
		this.mDriveLeftSlave1.configPeakOutputReverse(-1, 0);
		this.mDriveLeftSlave2.configPeakOutputReverse(-1, 0);
		this.mDriveRightMaster.configPeakOutputReverse(-1, 0);
		this.mDriveRightSlave1.configPeakOutputReverse(-1, 0);
		this.mDriveRightSlave2.configPeakOutputReverse(-1, 0);
	}
	
	public void enable() {
		this.mDriveLeftMaster.set(ControlMode.PercentOutput, 0);
		this.mDriveLeftSlave1.set(ControlMode.Follower, this.mDriveLeftMaster.getDeviceID());
		this.mDriveLeftSlave2.set(ControlMode.Follower, this.mDriveLeftMaster.getDeviceID());
		this.mDriveRightMaster.set(ControlMode.PercentOutput, 0);
		this.mDriveRightSlave1.set(ControlMode.Follower, this.mDriveRightMaster.getDeviceID());
		this.mDriveRightSlave1.set(ControlMode.Follower, this.mDriveRightMaster.getDeviceID());
	}
	
	public void disable() {
		this.mDriveLeftMaster.set(ControlMode.Disabled, 0);
//		this.mDriveLeftSlave1.set(ControlMode.Disabled, 0);
//		this.mDriveLeftSlave2.set(ControlMode.Disabled, 0);
		this.mDriveRightMaster.set(ControlMode.Disabled, 0);
//		this.mDriveRightSlave1.set(ControlMode.Disabled, 0);
//		this.mDriveRightSlave2.set(ControlMode.Disabled, 0);
	}
	
}