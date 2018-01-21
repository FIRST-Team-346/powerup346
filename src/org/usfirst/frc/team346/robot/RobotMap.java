package org.usfirst.frc.team346.robot;

import org.usfirst.frc.team346.auto.plans.AutoPlan;
import org.usfirst.frc.team346.auto.plans.CrossBaseline;

public class RobotMap {

	public static final AutoPlan mAutoPlan = new CrossBaseline();
	
	public static boolean kTuningState = false;
	
	public static final int kDriveLeftMasterPort = 1;
	public static final int kDriveLeftSlave1Port = 2;
	public static final int kDriveLeftSlave2Port = 3;
	public static final int kDriveRightMasterPort = 4;
	public static final int kDriveRightSlave1Port = 5;
	public static final int kDriveRightSlave2Port = 6;
	
	public static final int kXboxControllerPort = 0;
	public static final int kButtonBoardPort = 1;
	
	public static final int kIntakeForwardButton = 0;
	public static final int kIntakeReverseButton = 0;
	
	public static final double kDistanceVaultToSwitch = 0;
	public static final double kDistanceSwitchToScale = 0;
	
	public static final double kWheelDiameter = 0;
	public static final double kDistanceCenterToBumperFront = 0;
	public static final double kDistanceCenterToBumperBack = 0;
	public static final double kDistanceCenterToBumperLeft = 0;
	public static final double kDistanceCenterToBumperRight = 0;
	
	public static final double kDriveKP = 0;
	public static final double kDriveKI = 0;
	public static final double kDriveKD = 0;
	public static final double kDriveKF = 0;
	
	public static final double kDriveRampRate = 0;
	public static final double kDriveVoltageMin = 0;
	public static final double kDriveVoltageMax = 0;

}