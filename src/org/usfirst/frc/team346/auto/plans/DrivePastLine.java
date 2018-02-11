package org.usfirst.frc.team346.auto.plans;

import org.usfirst.frc.team346.auto.actions.DriveFollow;
import org.usfirst.frc.team346.auto.actions.Rotate;
import org.usfirst.frc.team346.robot.Robot;
import org.usfirst.frc.team346.robot.RobotMap;

import edu.wpi.first.wpilibj.DriverStation;

public class DrivePastLine extends AutoPlan {
	Rotate mRotator;
	
	double side;
	
	DriverStation driverStation = DriverStation.getInstance();
	
	public String getGoal() {
		return "drive past line";
	}

	public void run(Robot _robot, String _layout) {
		this.mRotator = new Rotate();

		if(driverStation.getGameSpecificMessage().charAt(1)=='L') {
			side = -1;
		}
		else {
			side = 1;
		}
			new DriveFollow(3, 0.7 * RobotMap.kDriveVelAverage).followLine();
			super.waitTime(2);
			this.mRotator.rotate(side*51, 0.4, 1, 3);
			super.waitTime(2);
			new DriveFollow(22, 0.7 * RobotMap.kDriveVelAverage).followLine();
			super.waitTime(4);
			this.mRotator = new Rotate();
			this.mRotator.rotate(side*-51, 0.4, 1, 3);
			this.waitTime(2);
			new DriveFollow(25, 0.7 * RobotMap.kDriveVelAverage).followLine();
	}
		
}