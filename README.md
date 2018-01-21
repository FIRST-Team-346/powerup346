# FRC Team 346 — Power Up
This code for Team 346 is to be used on the robot in the 2018 game season.
The code for Team 346's robot is written in Java and is based upon WPILib's Java control system.

The code is separated into packages, each controlling a wide aspect of the overall function. Classes located inside each package deal with subsets of these functions.

## Package Functions
 - .robot
 
 	Contains all robot functions and settings for the robot.
 	
 - .auto
 
 	Handles autonomous running.
 	
 - .auto.plans
 
 	Contains all autonomous plans (routines). Each plan is a series of steps which accomplishes a specific goal.
 	
 - .auto.actions
 
 	Contains all actions used during the autonomous period, including driving and rotating.
 	
 - .subsystems
 
 	Subsystems are groups of interrelated components on the robot which serve a central purpose. There can be only one of each subsystem created at any time. Subsystems are controlled during the autonomous and teleoperated periods by getting and setting states.
 	
 - .vision
 
 	Handles the camera for driver sight in the teleoperated period, and potentially as vision analysis for lining up during the autonomous period.
 	
 - .control
 
 	Contains classes used to control the robot's driving during the teleoperated period.
 	
## Variable Naming Conventions
 
 - k***  (i.e. kRedSide)			: constants, such as identifier numbers for electrical components
 - s***  (i.e. sDrive)				: subsystem instances
 - m***  (i.e. mAngleSetpoint)		: private instance variables, which can be updated through setter methods
 - l***  (i.e. lCurrentAngle)		: local instance variables
 - _***  (i.e. _angle)				: parameters