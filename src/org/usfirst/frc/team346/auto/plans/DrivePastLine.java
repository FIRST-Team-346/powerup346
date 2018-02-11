package org.usfirst.frc.team346.auto.plans;

import org.usfirst.frc.team346.auto.actions.DriveFollow;
import org.usfirst.frc.team346.auto.actions.Rotate;
import org.usfirst.frc.team346.robot.Robot;

import edu.wpi.first.wpilibj.DriverStation;

public class DrivePastLine extends AutoPlan{
	DriveFollow mStraightDriver;
	Rotate mRotator;
	
	double side;
	
	DriverStation driverStation = DriverStation.getInstance();
	
	public String getGoal() {
		return "drive straight and turn left";
	}

	public void run(Robot _robot, String _layout) {
		this.mStraightDriver = new DriveFollow(_robot);
		this.mRotator = new Rotate();

		if(driverStation.getGameSpecificMessage().charAt(1)=='L') {
			side = -1;
		}
		else {
			side = 1;
		}
			this.mStraightDriver.followLine(3);
			super.waitTime(2);
			this.mRotator.rotate(side*51, 0.4, 1, 3);
			super.waitTime(2);
			this.mStraightDriver = new DriveFollow(_robot);
			this.mStraightDriver.followLine(22);
			super.waitTime(4);
			this.mRotator = new Rotate();
			this.mRotator.rotate(side*-51, 0.4, 1, 3);
			this.waitTime(2);
			this.mStraightDriver = new DriveFollow(_robot);
			this.mStraightDriver.followLine(25);
	}
		
}