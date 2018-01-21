package org.usfirst.frc.team346.robot;

import org.usfirst.frc.team346.auto.plans.AutoPlan;
import org.usfirst.frc.team346.auto.plans.CrossBaseline;

public class RobotMap {

	public static final AutoPlan mAutoPlan = new CrossBaseline();
	
	public static boolean kTuningState = false;
	
	public static int kDriveLeftMasterPort = 1;
	public static int kDriveLeftSlave1Port = 2;
	public static int kDriveLeftSlave2Port = 3;
	public static int kDriveRightMasterPort = 4;
	public static int kDriveRightSlave1Port = 5;
	public static int kDriveRightSlave2Port = 6;
	
	public static double kDistanceVaultToSwitch;
	public static double kDistanceSwitchToScale;
	
	public static double kWheelDiameter;
	public static double kDistanceCenterToBumperFront;
	public static double kDistanceCenterToBumperBack;
	public static double kDistanceCenterToBumperLeft;
	public static double kDistanceCenterToBumperRight;
	
	public static double kDriveKP;
	public static double kDriveKI;
	public static double kDriveKD;
	public static double kDriveKF;
	
	public static int kIntakeButton;
	public static int kIntakeReverseButton;
	public static int kSwitchDeployButton;
	public static int kScaleDeployButton;
	public static int kVaultDeployButton;
	public static int kClimbButton;
	
	public static double kDriveRampRate;
	public static double kDriveVoltageMin;
	public static double kDriveVoltageMax;

}