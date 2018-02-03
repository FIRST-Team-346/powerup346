package org.usfirst.frc.team346.subsystems;

import edu.wpi.first.wpilibj.AnalogInput;

public class ProximitySensor {
	
	AnalogInput in;
	public ProximitySensor(int analogIn) {
		in = new AnalogInput(analogIn);
	}
	public boolean Read() {
		if(in.getVoltage() > 0.6) {
			return false;
		}else {
			return true;
		}
	}
}
