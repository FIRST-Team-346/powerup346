# FRC Team 346 — Power-Up (Prototype)
This prototyping code for Team 346 aims to prepare the team for the upcoming 2018 game season.
The code for Team 346's robot is written in Java and is based upon WPILib's Java control system.

The code is separated into packages, each controlling a wide aspect of the overall function. Classes located inside each package deal with subsets of these functions.

## Package Functions
 - com.team346.frc2018
 
 	Contains all robot functions and settings for the robot.
 	
 - com.team346.frc2018.auto
 
 	Handles autonomous plans.
 	
 - com.team346.frc2018.auto.steps
 
 	Contains all steps (actions) used during the autonomous period, including driving, rotating, shooting, and placing gears.
 	
 - com.team346.frc2018.auto.plans
 
 	Contains all autonomous plans (routines). Each plan is a series of steps which accomplishes a specific goal.
 	
 - com.team346.frc2018.loops
 
 	Runs periodic routines on the robot, including updating subsystems.
 	
 - com.team346.frc2018.subsystems
 
 	Subsystems are groups of interrelated components on the robot which serve a central purpose. There can be only one of each subsystem created at any time, thus subsystems are singletons. Subsystems are controlled during the autonomous and teleoperated periods by getting and setting states.
 	
 - com.team346.frc2018.vision
 
 	Handles the camera for driver sight in the teleoperated period, as well as vision analysis for lining up during the autonomous period. Team 346 would like to improve its vision analysis capabilities during the 2018 season.
 	
 - com.team346.frc2018.util
 
 	Contains custom classes made for hardware devices, currently only gyroscopes.
 	
 - com.team346.frc2018.control
 
 	Contains classes used to control the robot's driving during the autonomous and teleoperated periods.
 	
## Variable Naming Conventions
 
 - K_*** (i.e. K_RED_SIDE)			: static constants, such as enumeration options for setting subsystem states
 - CAPS  (i.e. DEPLOY_BUTTON)		: final constants, which can be updated through the Constants.java file
 - m***  (i.e. mAngleSetpoint)		: private instance variables, which can be updated through setter methods
 - l***  (i.e. lCurrentAngle)		: local instance variables
 - _***  (i.e. _angle)				: parameters