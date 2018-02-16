package org.usfirst.frc.team346.robot;

import org.usfirst.frc.team346.auto.plans.AutoPlan;
import org.usfirst.frc.team346.auto.plans.CrossBaseline;
import org.usfirst.frc.team346.auto.plans.DriveFollowTest;
import org.usfirst.frc.team346.auto.plans.SwitchCube;
import org.usfirst.frc.team346.auto.plans.Test;

public class RobotMap {
	
//	BOT-WIDE UPDATES----------------------------------------------------------------

	public static final AutoPlan kAutoPlan = new DriveFollowTest();
	
	public static boolean kPracticeBot = false;
	public static boolean kStartingOnLeft = false;
	
//	COMPONENT PORTS-----------------------------------------------------------------
	
	public static final int kDriveLeftMasterPort = 27;			//27-03
	public static final int kDriveLeftSlave1Port =7;			//13-02
	public static final int kDriveLeftSlave2Port = 13;			//07-21
	public static final int kDriveRightMasterPort = 26;			//26-01
	public static final int kDriveRightSlave1Port = 18;			//18-05
	public static final int kDriveRightSlave2Port = 17;			//17-08

	public static final int kIntakeLeftPort = 24;			    //20-
	public static final int kIntakeRightPort = 20;				//41-
	
	public static final int kLoaderLeftPort = 41;				//15-
	public static final int kLoaderRightPort = 19;				//19-
	
	public static final int kOuttakeLeftPort = 14;				//23-
	public static final int kOuttakeRightPort = 23;				//14-
	
	public static final int kTilterPort = 25;					//25-
	
	public static final int kShooterLeftPort = 9;				//19-
	public static final int kShooterRightPort = 15;				//15-
	
	public static final int kClimberPort = 0;					//
	
//	;CONTROLLER PORTS/BUTTONS--------------------------------------------------------
	
	public static final int kXboxControllerPort = 0;
	public static final int kButtonBoardPort = 1;
	public static final int KDroneControllerPort = 2;
	public static final double kThrottleTurnRotationStrength = 0.6;
	
	public static final int kButton1IntakeIn = 1;
	public static final int kButton2IntakeOut = 2;
	public static final int kButton3TilterNeutral = 3;
	public static final int kButton4TilterScaleFar = 4;
	public static final int kButton5OuttakeFront = 5;
	public static final int kButton6OuttakeBack = 6;
	public static final int kButton7 = 7;
	public static final int kButton8TilterScaleClose = 8;
	public static final int kButton9ShooterOn = 9;
	public static final int kButton10ShooterOff = 10;
	public static final int kButton11 = 11;
	public static final int kButton12TilterSwitchFar = 12;
	public static final int kButton13 = 13;
	public static final int kButton14Climb = 14;
	public static final int kButton15TilterSwitchClose = 15;
	
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
	
	public static final int kShooterLeftSetpointNu = 14000;
	public static final int kShooterRightSetpointNu = -14000;
	
	public static final int kShooterRampRateSeconds = 1;
	
//	TILTER SUBSYSTEM VALUES---------------------------------------------------------
	
	public static final boolean kTiltUpIsPositive = true;
	
	public static final int kTiltPosNeutral = 40;
	public static final int kTiltPosRange = 260;
	
	public static final int kTiltPosNeutralToSwitchClose = 70 + kTiltPosNeutral;
	public static final int kTiltPosNeutralToVault = 170 + kTiltPosNeutral;
	public static final int kTiltPosNeutralToScaleFar = 200 + kTiltPosNeutral;
	public static final int kTiltPosNeutralToScaleClose = 255 + kTiltPosNeutral;
	
	public static final int kTilterMaxVelocityNu = 83;
	public static final int kTilterMaxAccelerationNu = 100;
	
	public static final int kTilterDesiredVelocityNu = 20;
	public static final int kTilterDesiredAccelerationNu = 60;
	
	public static final double kTilterP = 12.0;
	public static final double kTilterI = 0.0;
	public static final double kTilterD = 0.0;
	public static final double kTilterF = 1023./kTilterMaxVelocityNu;
	
//	PHYSICAL REFERENCE VALUES-------------------------------------------------------
	
	public static final double kWheelCircumferenceLeft = 0;
	public static final double kWheelCircumferenceRight = 0;
	
//	--------------------------------------------------------------------------------

}