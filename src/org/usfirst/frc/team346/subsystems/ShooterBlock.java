package org.usfirst.frc.team346.subsystems;

import org.usfirst.frc.team346.robot.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class ShooterBlock implements Subsystem {

	private DoubleSolenoid mShooterBlock;
	
	private static ShooterBlock sShooterBlockInstance = new ShooterBlock();
	public static ShooterBlock getInstance() {
		return sShooterBlockInstance;
	}
	
	protected ShooterBlock() {
		this.mShooterBlock = new DoubleSolenoid(RobotMap.kShooterBlockForwardAirPort, RobotMap.kShooterBlockReverseAirPort);
	}
	
	public void extend() {
		this.mShooterBlock.set(Value.kForward);
	}
	
	public void retract() {
		this.mShooterBlock.set(Value.kReverse);
	}
	
	public void publishData() {
		
	}
	
	public void disable() {
		this.mShooterBlock.set(Value.kOff);
	}

}