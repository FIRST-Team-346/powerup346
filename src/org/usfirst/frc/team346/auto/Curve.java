package org.usfirst.frc.team346.auto;

public class Curve {

	private double[][] mPoint;
	
	public double getArcLength() {
		return 0;//TODO
	}
	
	public double[] getPoint(int _index) {
		double[] returnPoint = {0,0};
		
		if(mPoint.length <= _index) {
			System.out.println("Curve| GetPoint() out of bounds index");
			return returnPoint;
		}
		returnPoint[0] = this.mPoint[_index][0];
		returnPoint[1] = this.mPoint[_index][1];
		return returnPoint;
	}

}