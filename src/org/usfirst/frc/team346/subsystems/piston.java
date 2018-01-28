package org.usfirst.frc.team346.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.XboxController;

public class piston {
	
	XboxController xbox;
	
	DoubleSolenoid piston;

public void piston() {
	piston = new DoubleSolenoid(1,2,3);
	xbox = new XboxController(0);
	
	
}

public void release() {
	//xbox.getRawButton(2);
	if(xbox.getRawButton(2)) {
		piston.set(Value.kForward);
	}
	else {
		piston.set(Value.kReverse);
	}
}

}
