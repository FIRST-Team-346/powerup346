package org.usfirst.frc.team346.robot;

import org.usfirst.frc.team346.auto.plans.AutoPlan;
import org.usfirst.frc.team346.auto.plans.CrossBaseline;
import org.usfirst.frc.team346.auto.plans.DriveFollowTest;
import org.usfirst.frc.team346.auto.plans.SwitchCube;
import org.usfirst.frc.team346.auto.plans.Test;

public class RobotMap {
	
//	BOT-WIDE UPDATES----------------------------------------------------------------

	public static final AutoPlan kAutoPlan = new Test();
	
	public static boolean kPracticeBot = false;
	public static boolean kStartingOnLeft = false;
	
//	COMPONENT PORTS-----------------------------------------------------------------
	
	public static final int kDriveLeftMasterPort = 27;			//27-03
	public static final int kDriveLeftSlave1Port = 7;			//13-02
	public static final int kDriveLeftSlave2Port = 13;			//07-21
	public static final int kDriveRightMasterPort = 26;			//26-01
	public static final int kDriveRightSlave1Port = 18;			//18-05
	public static final int kDriveRightSlave2Port = 17;			//17-08

	public static final int kIntakeLeftPort = 24;			    //24-
	public static final int kIntakeRightPort = 20;				//20-
	
	public static final int kLoaderLeftPort = 41;				//41-
	public static final int kLoaderRightPort = 19;				//19-
	
	public static final int kOuttakeLeftPort = 14;				//14-
	public static final int kOuttakeRightPort = 23;				//23-
	
	public static final int kTilterPort = 25;					//25-
	
	public static final int kShooterLeftPort = 9;				//09-
	public static final int kShooterRightPort = 15;				//15-
	
	public static final int kClimberPort = 30;					//30
	
	public static final int kPCMPort = 0;
	
	public static final int kClimberSolenoidChannel = 5;
	
	public static final int kLightRedChannel = 1;
	public static final int kLightGreenChannel = 2;
	public static final int kLightBlueChannel = 3;
	public static final int kLightPowerChannel = 4;
	
//	CONTROLLER PORTS/BUTTONS--------------------------------------------------------
	
	public static final int kXboxControllerPort = 0;
	public static final int kButtonBoardPort = 1;
	public static final int KDroneControllerPort = 2;
	public static final double kThrottleTurnRotationStrength = 0.6;
	
	public static final int kButtonIntakeIn = 3;
	public static final int kButtonIntakeOut = 2;

	public static final int kButtonOuttakeFront = 6;
	public static final int kButtonOuttakeBack = 7;
	
	public static final int kButtonTilterScaleHigh = 4;
	public static final int kButtonTilterScaleLow = 8;
	public static final int kButtonTilterVault = 12;
	public static final int kButtonTilterSwitchBack = 15;
	public static final int kButtonTilterScaleBack = 14;
	
	public static final int kButtonShooterOn = 10;
	public static final int kButtonShooterOff = 11;
	
	public static final int kButtonClimb = 1;
	
	public static final int kButton5 = 5;
	public static final int kButton9 = 9;
	public static final int kButton13 = 13;
	
//	DRIVE PID/SUBSYSTEM VALUES------------------------------------------------------
	
	public static final double kDriveLeftVelMax = 3000.;

	public static final double kDriveVelLeftP = 0.75;
	public static final double kDriveVelLeftI = 0;
	public static final double kDriveVelLeftD = 0;
	public static final double kDriveVelLeftF = 1023. / kDriveLeftVelMax;
		
	public static final double kDriveRightVelMax = 3000.;

	public static final double kDriveVelRightP = 0.65;
	public static final double kDriveVelRightI = 0;
	public static final double kDriveVelRightD = 0;
	public static final double kDriveVelRightF = 1023. / kDriveRightVelMax;
	
	public static final double kDriveVelAverage = 3000.;
	
	public static final double kDrivePosLeftP = 0;
	public static final double kDrivePosLeftI = 0;
	public static final double kDrivePosLeftD = 0;
	public static final double kDrivePosLeftF = 0;
	
	public static final double kDrivePosRightP = 0;
	public static final double kDrivePosRightI = 0;
	public static final double kDrivePosRightD = 0;
	public static final double kDrivePosRightF = 0;
	
	
	public static final double kDriveFollowP = 0.5;
	public static final double kDriveFollowI = 0;
	public static final double kDriveFollowD = 0;
	public static final double kDriveFollowF = 0.5;

	public static final double kDriveFollowErrorScalerMultiplier = 2.0;
	public static final double kDriveFollowErrorScalerDivider = 0.5;
	public static final double kDriveFollowProfileErrorScalerMultiplier = 1.5;
	
	public static final double kDriveFollowVelSetpoint = 0.7 * RobotMap.kDriveVelAverage;
	public static final double kDriveFollowAccelSetpoint = 1000.;
	public static final double kDriveFollowDecelSetpoint = 175.;
	
	public static final double kDriveFollowUpdateRate = 0.02;
	public static final double kDriveFollowMinVelocity = 0.175 * RobotMap.kDriveVelAverage;
	
//	SHOOTER PID/SUBSYSTEM VALUES----------------------------------------------------
	
	public static final double kShooterLeftP = 0.2;
	public static final double kShooterLeftI = 0;
	public static final double kShooterLeftD = 10.0;
	public static final double kShooterLeftF = 0.041;
	
	public static final double kShooterRightP = 0.2;
	public static final double kShooterRightI = 0;
	public static final double kShooterRightD = 10.0;
	public static final double kShooterRightF = 0.041;
	
	public static final int kShooterLeftSetpointNuLow = 12650;
	public static final int kShooterRightSetpointNuLow = 12500;
	public static final int kShooterLeftSetpointNuHigh = 16250;
	public static final int kShooterRightSetpointNuHigh = 16100;
	
	public static final int kShooterRampRateSeconds = 1;
	
//	TILTER SUBSYSTEM VALUES---------------------------------------------------------
	
	public static final int kTiltPosNeutral 	= 81;
	public static final int kTiltPosRange 		= 329;
	
	public static final int kTiltPosDrive		= 20  + kTiltPosNeutral;
	public static final int kTiltPosSwitchBack 	= 65  + kTiltPosNeutral;
	public static final int kTiltPosVault 		= 160 + kTiltPosNeutral;
	public static final int kTiltPosScaleLow 	= 250 + kTiltPosNeutral;
	public static final int kTiltPosScaleHigh 	= 275 + kTiltPosNeutral;
	public static final int kTiltPosScaleBack 	= 324 + kTiltPosNeutral;
	
	public static final int kTilterDesiredVelocityNu = 50;
	public static final int kTilterDesiredAccelerationNu = 100;
	
	public static final int kTilterMaxVelocityNu = 83;
	public static final double kTilterP = 8.0;
	public static final double kTilterI = 0.0;
	public static final double kTilterD = 0.0;
	public static final double kTilterF = 1023./kTilterMaxVelocityNu;
	
//	PHYSICAL REFERENCE VALUES-------------------------------------------------------
	
	public static final double kWheelCircumferenceLeft = 0;
	public static final double kWheelCircumferenceRight = 0;
	
//	--------------------------------------------------------------------------------

}