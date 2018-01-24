package org.usfirst.frc.team346.robot;

import org.usfirst.frc.team346.auto.plans.AutoPlan;
import org.usfirst.frc.team346.auto.plans.CrossBaseline;

public class RobotMap {
	
//	BOT-WIDE UPDATES----------------------------------------------------------------

	public static final AutoPlan mAutoPlan = new CrossBaseline();
	
	public static boolean kTuningState = false;
	
//	COMPONENT PORTS-----------------------------------------------------------------
	
	public static final int kDriveLeftMasterPort = 1;
	public static final int kDriveLeftSlave1Port = 2;
	public static final int kDriveLeftSlave2Port = 3;
	public static final int kDriveRightMasterPort = 4;
	public static final int kDriveRightSlave1Port = 5;
	public static final int kDriveRightSlave2Port = 6;
	
	public static final int kIntakeLeftPort = 7;
	public static final int kIntakeRightPort = 8;
	
	public static final int kTurretTurnerPort = 9;
	public static final int kTurretTilterPort = 10;
	
	public static final int kTurretShooterLeftPort = 11;
	public static final int kTurretShooterRightPort = 12;
	
	public static final int kClimberPort = 13;
	
//	CONTROLLER PORTS/BUTTONS--------------------------------------------------------
	
	public static final int kXboxControllerPort = 0;
	public static final int kButtonBoardPort = 1;
	
	public static final int kIntakeForwardButton = 0;
	public static final int kIntakeReverseButton = 0;
	
//	PID/SUBSYSTEM VALUES------------------------------------------------------------
	
	public static final double kDriveFollowLeftKP = 0;
	public static final double kDriveFollowLeftKI = 0;
	public static final double kDriveFollowLeftKD = 0;
	public static final double kDriveFollowLeftKF = 0;
	
	public static final double kDriveFollowRightKP = 0;
	public static final double kDriveFollowRightKI = 0;
	public static final double kDriveFollowRightKD = 0;
	public static final double kDriveFollowRightKF = 0;
	
	public static final double kDriveFollowCourseErrorScaler = 0;
	
	public static final double kDriveRampRate = 0;
	public static final double kDriveVoltageMin = 0;
	public static final double kDriveVoltageMax = 0;
	
	public static final double kDriveFollowCruiseVelocityRPM = 0;
	
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