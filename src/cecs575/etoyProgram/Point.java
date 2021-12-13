package cecs575.etoyProgram;

public class Point {
	private double xCoordinate, yCoordinate;
//	private double secondXCoordinate, secondYCoordinate;

	public Point() {
		this(0.0, 0.0);
	}

	public Point(double xCoordinate, double yCoordinate) {
		this.xCoordinate = xCoordinate;
		this.yCoordinate = yCoordinate;
	}
	
//	public void anotherPoint(double secondXCoordinate, double secondYCoordinate) {
//		this.secondXCoordinate = secondXCoordinate;
//		this.secondYCoordinate = secondYCoordinate;
//	}

	public double getXCoordinate() {
		return Math.round(xCoordinate);
	}

	public double getYCoordinate() {
		return Math.round(yCoordinate);
	}
	
//	public double getSecondXCoordinate() {
//		return Math.round(secondXCoordinate);
//	}
//
//	public double getSecondYCoordinate() {
//		return Math.round(secondYCoordinate);
//	}
}
