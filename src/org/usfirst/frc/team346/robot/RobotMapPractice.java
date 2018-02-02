package org.usfirst.frc.team346.robot;

import org.usfirst.frc.team346.auto.plans.AutoPlan;
import org.usfirst.frc.team346.auto.plans.CrossBaseline;
import org.usfirst.frc.team346.auto.plans.DriveFollowTest;
import org.usfirst.frc.team346.auto.plans.SwitchCube;

public class RobotMapPractice {
	
//	BOT-WIDE UPDATES----------------------------------------------------------------

	public static final AutoPlan kAutoPlan = new DriveFollowTest();
	
	public static boolean kPracticeBot = true;
	
//	COMPONENT PORTS-----------------------------------------------------------------
	
	public static final int kDriveLeftMasterPort = 1;
	public static final int kDriveLeftSlave1Port = 2;
	public static final int kDriveLeftSlave2Port = 4;
	public static final int kDriveRightMasterPort = 3;
	public static final int kDriveRightSlave1Port = 5;
	public static final int kDriveRightSlave2Port = 6;
	
	public static final int kIntakeLeftPort = 21;
	public static final int kIntakeRightPort = 24;
	
	public static final int kTurretTurnerPort = 9;
	public static final int kTurretTilterPort = 10;
	
	public static final int kTurretShooterLeftPort = 11;
	public static final int kTurretShooterRightPort = 12;
	
	public static final int kClimberPort = 13;
	
//	CONTROLLER PORTS/BUTTONS--------------------------------------------------------
	
	public static final int kXboxControllerPort = 0;
	public static final int kButtonBoardPort = 1;
	public static final double kThrottleTurnRotationStrength = 0.8;
	
	public static final int kIntakeForwardButton = 0;
	public static final int kIntakeReverseButton = 0;
	
//	PID/SUBSYSTEM VALUES------------------------------------------------------------
	
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
	
	public static final double kDriveFollowErrorScalerMultiplier = 10.;
	public static final double kDriveFollowErrorScalerDivider = 0.35;
	public static final double kDriveFollowVelSetpoint = 0.6 * 1200.;
	public static final double kDriveFollowUpdateRate = 0.05;
	
	public static final double kDriveFollowP = 0.5;
	public static final double kDriveFollowI = 0;
	public static final double kDriveFollowD = 0.5;
	public static final double kDriveFollowF = 0;
	
	public static final double kDriveRampRate = 0;
	
	public static final double KMinDriveOutput = 0.09;
	
	//Motion Magic values are in Native Units Per 100ms
	public static final int kTurretTurnerCruiseVelocity = 0;
	public static final int kTurretTurnerMaxAcceleration = 0;
	
	public static final int kTurretTilterCruiseVelocity = 0;
	public static final int kTurretTilterMaxAcceleration = 0;

	public static final int kTurretShooterCruiseVelocity = 0;
	public static final int kTurretShooterMaxAcceleration = 0;
	
//	PHYSICAL REFERENCE VALUES-------------------------------------------------------
	
	public static final double kDistanceVaultToSwitch = 0;
	public static final double kDistanceSwitchToScale = 0;
	
	public static final double kWheelDiameter = 0;
	public static final double kDistanceCenterToBumperFront = 0;
	public static final double kDistanceCenterToBumperBack = 0;
	public static final double kDistanceCenterToBumperLeft = 0;
	public static final double kDistanceCenterToBumperRight = 0;
	
//	--------------------------------------------------------------------------------

}