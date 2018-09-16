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

public class AutoPlan {
	
//	Preferences pref = Preferences.getInstance();
	double angleError = 0;
	
	public String getGoal() {
		return "default goal";
	}
	
	public void run(double _startingLeft, double _switchLeft, double _scaleLeft) {
		System.out.println("default run; check that your run takes the layout parameters");
	}
	
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