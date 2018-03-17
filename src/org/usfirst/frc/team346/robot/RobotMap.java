package org.usfirst.frc.team346.robot;

public class RobotMap {
	
//	BOT-WIDE UPDATES----------------------------------------------------------------
	
//	COMPONENT PORTS-----------------------------------------------------------------
	
	public static final int kDriveLeftMasterPort = 27;			//27-01
	public static final int kDriveLeftSlave1Port = 13;			//13-08
	public static final int kDriveLeftSlave2Port = 7;			//07-05
	public static final int kDriveRightMasterPort = 16;			//16-03
	public static final int kDriveRightSlave1Port = 11;			//11-02
	public static final int kDriveRightSlave2Port = 4;			//04-21

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
	
	public static final int kLightLeftPositive = 0;
	public static final int kLightLeftRedChannel = 2;
	public static final int kLightLeftGreenChannel = 1;
	public static final int kLightLeftBlueChannel = 3;
	
	public static final int kLightRightPositive = 7;
	public static final int kLightRightRedChannel = 5;
	public static final int kLightRightGreenChannel = 6;
	public static final int kLightRightBlueChannel = 4;
	
//	CONTROLLER PORTS/BUTTONS--------------------------------------------------------
	
	public static final int kXboxControllerPort = 0;
	public static final int kButtonBoardPort = 1;
	public static final int KDroneControllerPort = 2;
	public static final double kThrottleTurnRotationStrength = 0.8;
	
	public static final int kButtonIntakeIn = 12;
	public static final int kButtonIntakeOut = 3;

	public static final int kButtonOuttakeFront = 10;
	public static final int kButtonOuttakeBack = 15;
	
	public static final int kButtonTilterScaleLow = 5;
	public static final int kButtonTilterScaleHigh = 6;
	public static final int kButtonTilterScaleBack = 7;
	public static final int kButtonTilterVault = 14;
	public static final int kButtonTilterSwitchBack = 13;
	public static final int kButtonTiltDownFlipCube = 16;
	
	public static final int kButtonShooterOn = 11;
	public static final int kButtonShooterTilterOff = 4;
	
	public static final int kButtonClimb = 8;
	
	public static final int kButtonClimbRaiseHook = 1;
	
	public static final int kButtonLights = 9;
	public static final int kButton2 = 2;
	
//	DRIVE PID/SUBSYSTEM VALUES------------------------------------------------------
	
	public static final double kDriveLeftVelMax = 2000.;

	//leftvelP was 0.75 on left side scale front
	public static final double kDriveVelLeftP = 0.70;
	public static final double kDriveVelLeftI = 0;
	public static final double kDriveVelLeftD = 0;
	public static final double kDriveVelLeftF = 1023. / kDriveLeftVelMax;
		
	public static final double kDriveRightVelMax = 1900.;

	public static final double kDriveVelRightP = 0.65;
	public static final double kDriveVelRightI = 0;
	public static final double kDriveVelRightD = 0;
	public static final double kDriveVelRightF = 1023. / kDriveRightVelMax;
	
	public static final double kDriveVelAverage = 1950.;
	
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
	
	public static final double kRotateP = 0.045;							//0.05 - 0.045
	public static final double kRotateI = 0;								//0    - 0
	public static final double kRotateD = 0.2;								//0.4  - 0.2
	
	public static final double kRotateThreadVelPercent = 0.325;
	
	public static final double kDriveFollowAngleErrorScaler = 0.0225;		//0.0225
	public static final double kDriveFollowStopFeetHigh = 4.5;				//4.5  - 
	public static final double kDriveFollowStopFeetLow = 1.2;				//1.2  - 
	
	public static final double kDriveFollowVelSetpoint = 0.7 * RobotMap.kDriveVelAverage;
	public static final double kDriveFollowAccelSetpoint = 1000.;
	public static final double kDriveFollowDecelSetpoint = 175.;
	
	public static final double kDriveFollowUpdateRate = 0.02;
	public static final double kDriveFollowMinVelocity = 0.175 * RobotMap.kDriveVelAverage;
	
//	SHOOTER PID/SUBSYSTEM VALUES----------------------------------------------------
	
	public static final double kShooterLeftP = 0.15;
	public static final double kShooterLeftI = 0;
	public static final double kShooterLeftD = 10.0;
	public static final double kShooterLeftF = 0.041;
	
	public static final double kShooterRightP = 0.15;
	public static final double kShooterRightI = 0;
	public static final double kShooterRightD = 20.0;
	public static final double kShooterRightF = 0.041;
	
	public static final int kShooterLeftSetpointNuLow = 11000;		//11650
	public static final int kShooterRightSetpointNuLow = 11000;		//11500
	public static final int kShooterLeftSetpointNuHigh = 13000;		//14750
	public static final int kShooterRightSetpointNuHigh = 13000;	//14600
	public static final int kShooterLeftSetpointNuBack = 11700;		//14750
	public static final int kShooterRightSetpointNuBack = 11700;	//14600
	
	public static final int kShooterRampRateSeconds = 1;
	
//	TILTER SUBSYSTEM VALUES---------------------------------------------------------
	
	public static final int kTiltPosNeutral 	= 1315;
	public static final int kTiltPosRange 		= 370;
	
//	public static final int kTiltPosDrive		= 27  + kTiltPosNeutral;	//Not used currently
	public static final int kTiltPosSwitchBack 	= 60  + kTiltPosNeutral;
	public static final int kTiltPosVault 		= 150 + kTiltPosNeutral;
	public static final int kTiltPosScaleLow 	= 240 + kTiltPosNeutral;
	public static final int kTiltPosScaleHigh 	= 250 + kTiltPosNeutral;//255
	public static final int kTiltPosScaleBack 	= 330 + kTiltPosNeutral;
	
	public static final int kTilterDesiredVelocityNu = 40;
	public static final int kTilterDesiredAccelerationNu = 90;
	
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