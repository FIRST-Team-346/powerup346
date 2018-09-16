package org.usfirst.frc.team346.auto;

import org.usfirst.frc.team346.auto.actions.DriveFollow;
import org.usfirst.frc.team346.auto.actions.Rotate;
import org.usfirst.frc.team346.auto.actions.RotateThread;
import org.usfirst.frc.team346.robot.Robot;
import org.usfirst.frc.team346.robot.RobotMap;
import org.usfirst.frc.team346.subsystems.Drive;
import org.usfirst.frc.team346.subsystems.Gyro;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.GenericHID.Hand;

/*
 * This class is the superclass of all our auto plans (they all extend this).
 * That means that they inherit all the methods here. Since there are things
 * that all autos will be doing (driving, rotating, waiting), we make those
 * methods here, then all autos can just do super.driveUsingDF(), etc.
 * The *specific* methods here are not that useful to next season, but the
 * concept of inheriting all the common methods here is useful.
 */
public class AutoPlan {
	
//	Preferences pref = Preferences.getInstance();
	double angleError = 0;
	
	public String getGoal() {
		return "default goal";
	}
	
	/*
	 * When AutoRunner tries to run the auto plan, if that auto plan's run() method is
	 * written wrong (usually taking the wrong parameters or you just forgot to make it),
	 * then it will run this default plan and you know exactly what is wrong.
	 */
	public void run(double _startingLeft, double _switchLeft, double _scaleLeft) {
		System.out.println("default run; check that your run takes the layout parameters");
	}
	
	/*
	 * Just drive forward a certain distance. This year, I made the auto driving method
	 * it's own thread, so that other stuff could run at the same time.
	 * I don't really recommend that unless you read a lot on how to do threads...
	 */
	public void driveUsingDF(double _distanceFt) {
		if(!DriverStation.getInstance().isAutonomous() || DriverStation.getInstance().isDisabled()) {
			return;
		}
		Gyro.getInstance().zeroGyro();
		
		DriveFollow DF = new DriveFollow(_distanceFt, 0);
		new Thread(DF).start();
		while (DF.isDriving()) {
		}
		
		this.angleError = Gyro.getInstance().getAngle();
		Gyro.getInstance().zeroGyro();
		
		System.out.println("DF| driving complete, final distance:" + Drive.getInstance().getAveragedPosition()/1024.*1.037);
	}
	
	@Deprecated
	public void rotate(double _angleDegrees, double _timeOutTime) {
		if(!DriverStation.getInstance().isAutonomous() || DriverStation.getInstance().isDisabled()) {
			return;
		}
		
		Gyro.getInstance().zeroGyro();
		
		Rotate rt = new Rotate();
		rt.rotate(_angleDegrees, 0.5, _timeOutTime, 1.5);
	}
	
	@Deprecated
	public void rotateSingleSide(Hand _side, double _angleDegrees, double _timeOutTime) {
		if(!DriverStation.getInstance().isAutonomous() || DriverStation.getInstance().isDisabled()) {
			return;
		}
		
		Gyro.getInstance().zeroGyro();
		
		Rotate rt = new Rotate();
		rt.rotateSingleSide(_side, _angleDegrees, 0.5, _timeOutTime, 1.5);
	}
	
	/*
	 * Just rotate a certain number of degrees. I did the thread thing here, too.
	 */
	public void rotateUsingRT(double _angleDegrees) {
		if(!DriverStation.getInstance().isAutonomous() || DriverStation.getInstance().isDisabled()) {
			return;
		}
		
		Gyro.getInstance().zeroGyro();
		
		double percent = RobotMap.kRotateThreadVelPercent;
//		double percent = this.pref.getDouble("rtMult", 0);
		
		RotateThread RT = new RotateThread(_angleDegrees, percent);
		new Thread(RT).start();
		while (RT.isRotating()) {
		}
		System.out.println("RT| rotating complete, final angle:" + (_angleDegrees + this.angleError));
	}
	
	@Deprecated
	public void rotateUsingRT(double _angleDegrees, double _percentVoltage) {
		if(!DriverStation.getInstance().isAutonomous() || DriverStation.getInstance().isDisabled()) {
			return;
		}
		
		Gyro.getInstance().zeroGyro();
		
		RotateThread RT = new RotateThread(_angleDegrees, _percentVoltage);
		new Thread(RT).start();
		while (RT.isRotating()) {
		}
		System.out.println("RT| rotating complete, final angle:" + (_angleDegrees + this.angleError));
	}
	
	/*
	 * This one rotates only one side at a time, instead of pivoting in place.
	 */
	public void rotateUsingRT(Hand _side, double _angleDegrees) {
		if(!DriverStation.getInstance().isAutonomous() || DriverStation.getInstance().isDisabled()) {
			return;
		}
		
		Gyro.getInstance().zeroGyro();
		
		double mult = RobotMap.kRotateThreadVelPercent;
//		double mult = this.pref.getDouble("rtMult", 0);
		
		RotateThread RT = new RotateThread(_side, _angleDegrees - this.angleError, mult*1.5);
		new Thread(RT).start();
		while (RT.isRotating()) {
		}
		System.out.println("RT| rotating complete, final angle:" + (_angleDegrees + this.angleError));
	}
	
	/*
	 * The other methods rotate based on where the robot currently is:
	 * if the robot was at 89 degrees, but supposed to be at 90, then
	 * we told it to rotate 90, it would aim for 89+90=179 instead of 180.
	 * Therefore the other ones are relative rotations. These "absolute"
	 * methods would require that you've kept track of where the robot is
	 * "supposed to be", then use that to determine the next turn. It's typically
	 * more complicated than it's worth, and it's hard to switch between relative and absolute,
	 * so I would recommend just using relative drives and rotates as much as possible.
	 */
	public void rotateUsingRTAbsolute(double _angleDegrees) {
		if(!DriverStation.getInstance().isAutonomous() || DriverStation.getInstance().isDisabled()) {
			return;
		}
		
		Gyro.getInstance().zeroGyro();
		
		double mult = RobotMap.kRotateThreadVelPercent;
//		double mult = this.pref.getDouble("rtMult", 0);
		
		RotateThread RT = new RotateThread(_angleDegrees - this.angleError, mult);
		new Thread(RT).start();
		while (RT.isRotating()) {
		}
		
		this.angleError = Gyro.getInstance().getAngle() - _angleDegrees;
		Gyro.getInstance().zeroGyro();
		
		System.out.println("RT| rotating complete, final angle:" + (_angleDegrees + this.angleError));
	}
	
	public void rotateUsingRTAbsolute(Hand _side, double _angleDegrees) {
		if(!DriverStation.getInstance().isAutonomous() || DriverStation.getInstance().isDisabled()) {
			return;
		}
		
		Gyro.getInstance().zeroGyro();
		
		double mult = RobotMap.kRotateThreadVelPercent;
//		double mult = this.pref.getDouble("rtMult", 0);
		
		RotateThread RT = new RotateThread(_side, _angleDegrees - this.angleError, mult*1.5);
		new Thread(RT).start();
		while (RT.isRotating()) {
		}
		
		this.angleError = Gyro.getInstance().getAngle() - _angleDegrees;
		Gyro.getInstance().zeroGyro();
		
		System.out.println("RT| rotating complete, final angle:" + (_angleDegrees + this.angleError));
	}
	
	public void waitTime(double _seconds) {
		long initialTime = System.currentTimeMillis();
		while(System.currentTimeMillis() - initialTime < Math.abs(_seconds) * 1000.) {
			if(!DriverStation.getInstance().isAutonomous() || DriverStation.getInstance().isDisabled()) {
				return;
			}
		}
	}
	
}