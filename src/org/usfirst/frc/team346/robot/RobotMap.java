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
	
	public static final int kDriveLeftMasterPort = 1;			//27-01
	public static final int kDriveLeftSlave1Port = 5;			//13-05
	public static final int kDriveLeftSlave2Port = 8;			//07-08
	public static final int kDriveRightMasterPort = 3;			//26-03
	public static final int kDriveRightSlave1Port = 2;			//18-02
	public static final int kDriveRightSlave2Port = 21;			//17-21
	
	public static final int kIntakeLeftPort = 20;				//20-
	public static final int kIntakeRightPort = 41;				//41-
	
	public static final int kLoaderLeftPort = 15;				//15-
	public static final int kLoaderRightPort = 19;				//19-
	
	public static final int kOuttakeLeftPort = 23;				//23-
	public static final int kOuttakeRightPort = 14;				//14-
	
	public static final int kTilterPort = 25;					//25-
	
	public static final int kShooterLeftPort = 19;				//19-
	public static final int kShooterRightPort = 15;				//15-
	
	public static final int kClimberPort = 0;					//
	
//	;CONTROLLER PORTS/BUTTONS--------------------------------------------------------
	
	public static final int kXboxControllerPort = 0;
	public static final int kButtonBoardPort = 1;
	public static final double kThrottleTurnRotationStrength = 0.8;
	
	public static final int kButton1IntakeIn = 1;
	public static final int kButton2OuttakeForward = 2;
	public static final int kButton3 = 3;
	public static final int kButton4TilterScaleFar = 4;
	public static final int kButton5IntakeOut = 5;
	public static final int kButton6OuttakeReverse = 6;
	public static final int kButton7TilterOff = 7;
	public static final int kButton8TilterScaleClose = 8;
	public static final int kButton9ShooterOn = 9;
	public static final int kButton10ShooterOff = 10;
	public static final int kButton11 = 11;
	public static final int kButton12TilterSwitchFar = 12;
	public static final int kButton13 = 13;
	public static final int kButton14Climb = 14;
	public static final int kButton15TilterSwitchClose = 15;
	
//	DRIVE PID/SUBSYSTEM VALUES------------------------------------------------------
	
	public static final double kDriveVelLeftP = 0;
	public static final double kDriveVelLeftI = 0;
	public static final double kDriveVelLeftD = 0;
	public static final double kDriveVelLeftF = 0.5;
	
	public static final double kDriveVelRightP = 0;
	public static final double kDriveVelRightI = 0;
	public static final double kDriveVelRightD = 0;
	public static final double kDriveVelRightF = 0.5;
	
	
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
	public static final double kDriveFollowD = 0.5;
	public static final double kDriveFollowF = 0;

	public static final double kDriveFollowErrorScalerMultiplier = 10.;
	public static final double kDriveFollowErrorScalerDivider = 0.5;
	
	public static final double kDriveFollowVelSetpoint = 0.8 * 1200.;
	public static final double kDriveFollowAccelSetpoint = 0.;
	public static final double kDriveFollowDecelSetpoint = 0.;
	
	public static final double kDriveFollowUpdateRate = 0.02;
	public static final double kDriveFollowMinVelocity = 0.;
	
//	SHOOTER PID/SUBSYSTEM VALUES----------------------------------------------------
	
	public static final double kShooterLeftP = 0.2;
	public static final double kShooterLeftI = 0;
	public static final double kShooterLeftD = 10.0;
	public static final double kShooterLeftF = 0.041;
	
	public static final double kShooterRightP = 0.2;
	public static final double kShooterRightI = 0;
	public static final double kShooterRightD = 10.0;
	public static final double kShooterRightF = 0.041;
	
	public static final int kShooterLeftSetpointNu = 17000;
	public static final int kShooterRightSetpointNu = 17000;
	
	public static final int kShooterRampRateSeconds = 1;
	
//	TILTER SUBSYSTEM VALUES---------------------------------------------------------
	
	public static final boolean kTiltUpIsPositive = true;
	
	public static final int kTiltPosNeutral = 336;
	public static final int kTiltPosRange = 260;
	
	public static final int kTiltPosNeutralToSwitchClose = 50;
	public static final int kTiltPosNeutralToSwitchFar = 100;
	public static final int kTiltPosNeutralToScaleClose = 150;
	public static final int kTiltPosNeutralToScaleFar = 200;
	
	public static final int kTilterMaxVelocityNu = 83;
	public static final int kTilterMaxAccelerationNu = 100;
	
	public static final int kTilterDesiredVelocityNu = 40;
	public static final int kTilterDesiredAccelerationNu = 80;
	
	public static final double kTilterP = 12.0;
	public static final double kTilterI = 0.0;
	public static final double kTilterD = 0.0;
	public static final double kTilterF = 1023./kTilterMaxVelocityNu;
	
//	PHYSICAL REFERENCE VALUES-------------------------------------------------------
	
	public static final double kWheelCircumferenceLeft = 0;
	public static final double kWheelCircumferenceRight = 0;
	
//	--------------------------------------------------------------------------------

}