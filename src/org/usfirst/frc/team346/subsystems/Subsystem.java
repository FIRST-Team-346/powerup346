package org.usfirst.frc.team346.subsystems;

/*This is the interface for all the Subsystems. All the Subsystems classes implements this because they 
 * all should use these methods. Its a good way to keep things organized especially when multiple people 
 * are coding. Each subsystem should have its respective method do something within its class such as the
 * gyro publishData() printing out the angle.
*/
public interface Subsystem {

	// Every subsystem needs a method to turn of all output. This method should do that/
	public void disable();
	
	//Every subsystem can be in various states. This method should put those on the SmartDashvoard.
	public void publishData();

}
