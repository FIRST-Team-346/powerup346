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
	
	public static final int kDriveLeftMasterPort = 27;			//27-01
	public static final int kDriveLeftSlave1Port = 13;			//13-02
	public static final int kDriveLeftSlave2Port = 7;			//07-04
	public static final int kDriveRightMasterPort = 26;			//26-03
	public static final int kDriveRightSlave1Port = 18;			//18-05
	public static final int kDriveRightSlave2Port = 17;			//17-08
	
	public static final int kIntakeLeftPort = 20;				//20-
	public static final int kIntakeRightPort = 41;				//41-
	
	public static final int kLoaderLeftPort = 15;				//15-
	public static final int kLoaderRightPort = 19;				//19-
	
	public static final int kOuttakeLeftPort = 23;				//23-
	public static final int kOuttakeRightPort = 14;				//14-
	
	public static final int kTilterPort = 25;					//25-
	
	public static final int kShooterLeftPort = 24;				//24-
	public static final int kShooterRightPort = 9;				//09-
	
	public static final int kClimberPort = 0;					//
	
	public static final int kShooterBlockForwardAirPort = 0;
	public static final int kShooterBlockReverseAirPort = 0;
	
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
	public static final int kButton7TilterDisable = 7;
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
	public static final double kDriveFollowUpdateRate = 0.05;
	
//	SHOOTER PID/SUBSYSTEM VALUES----------------------------------------------------
	
	public static final double kShooterLeftP = 0;
	public static final double kShooterLeftI = 0;
	public static final double kShooterLeftD = 0;
	public static final double kShooterLeftF = 0.5;
	
	public static final double kShooterRightP = 0;
	public static final double kShooterRightI = 0;
	public static final double kShooterRightD = 0;
	public static final double kShooterRightF = 0.5;
	
	public static final int kShooterLeftSetpointRPM = 1000;
	public static final int kShooterRightSetpointRPM = 1000;
	public static final int kShooterLeftMaxAcceleration = 0;
	public static final int kShooterRightMaxAcceleration = 0;
	
//	TILTER SUBSYSTEM VALUES---------------------------------------------------------
	
	public static final boolean kTiltUpIsPositive = true;
	
	public static final int kTiltPosRange = 260;
	public static final int kTiltPosNeutral = 336;
	public static final int kTiltPosNeutralToPOSITION1 = 50;
	public static final int kTiltPosNeutralToPOSITION2 = 100;
	public static final int kTiltPosNeutralToPOSITION3 = 150;
	public static final int kTiltPosNeutralToPOSITION4 = 200;
	
	public static final int kTilterDesiredVelocityNu = 0;
	public static final int kTilterDesiredAccelerationNu = 0;
	
	public static final int kTilterMaxVelocityNu = 0;
	public static final int kTilterMaxAccelerationNu = 0;
	
	public static final double kTilterP = 12.0;
	public static final double kTilterI = 0.0;
	public static final double kTilterD = 0.0;
	public static final double kTilterF = 1023./kTilterMaxVelocityNu;
	
//	PHYSICAL REFERENCE VALUES-------------------------------------------------------
	
	public static final double kDistanceVaultToSwitch = 0;
	public static final double kDistanceSwitchToScale = 0;
	
	public static final double kWheelCircumferenceLeft = 0;
	public static final double kWheelCircumferenceRight = 0;
	
//	--------------------------------------------------------------------------------

}