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
	
	public static final int kButton1 = 1;
	public static final int kButton2 = 2;
	public static final int kButton3 = 3;
	public static final int kButton4 = 4;
	public static final int kButton5 = 5;
	public static final int kButton6 = 6;
	public static final int kButton7 = 7;
	public static final int kButton8 = 8;
	public static final int kButton9 = 9;
	public static final int kButton10 = 10;
	public static final int kButton11 = 11;
	public static final int kButton12 = 12;
	
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
	
	public static final int kTilterCruiseVelocityRPM = 0;
	public static final int kTilterMaxAccelerationRPM = 0;
	
//	PHYSICAL REFERENCE VALUES-------------------------------------------------------
	
	public static final double kDistanceVaultToSwitch = 0;
	public static final double kDistanceSwitchToScale = 0;
	
	public static final double kWheelCircumferenceLeft = 0;
	public static final double kWheelCircumferenceRight = 0;
	
//	--------------------------------------------------------------------------------

}