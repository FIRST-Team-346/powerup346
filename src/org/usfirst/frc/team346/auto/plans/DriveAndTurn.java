package org.usfirst.frc.team346.auto.plans;

import org.usfirst.frc.team346.auto.actions.DriveFollow;
import org.usfirst.frc.team346.auto.actions.Rotate;
import org.usfirst.frc.team346.robot.Robot;
import org.usfirst.frc.team346.robot.RobotMap;

import edu.wpi.first.wpilibj.DriverStation;

public class DriveAndTurn extends AutoPlan {

	Rotate mRotator;
	
	double side;
	
	DriverStation driverStation = DriverStation.getInstance();
	
	public String getGoal() {
		return "drive straight and turn left";
	}

	public void run(Robot _robot, String _layout) {
		this.mRotator = new Rotate();

		if(driverStation.getGameSpecificMessage().charAt(0)=='L') {
			side = -1;
		}
		else {
			side = 1;
		}
		
		new DriveFollow(3, 0.7 * RobotMap.kDriveVelAverage).followLine();
		super.waitTime(2);
		this.mRotator.rotate(side*48, 0.4, 3, 3);
		super.waitTime(2);
		new DriveFollow(8, 0.7 * RobotMap.kDriveVelAverage).followLine();
		super.waitTime(2);
		this.mRotator = new Rotate();
		this.mRotator.rotate(side*-51, 0.4, 3, 3);
		super.waitTime(2);
		new DriveFollow(2, 0.7 * RobotMap.kDriveVelAverage).followLine();
	}
		
}