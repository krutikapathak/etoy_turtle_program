package cecs575.etoyProgram;

import java.util.ArrayList;

public class Turtle {
	private Point currentLocation = new Point();
	private int currentDirection = 0;

	public Turtle(Point currentLocation, int currentDirection) {
		this.currentLocation = currentLocation;
		this.currentDirection = currentDirection;
	}

	public Turtle() {}

	public Point location() {
		return currentLocation;
	}

	public double direction() {
		return currentDirection;
	}

	public void move(ArrayList<Double> distance) {
		double radians = currentDirection * Math.PI / 180;
		double deltaX = Math.cos(radians) * distance.get(0);
		double deltaY = Math.sin(radians) * distance.get(0);

		double newX = currentLocation.getXCoordinate() + deltaX;
		double newY = currentLocation.getYCoordinate() + deltaY;

		currentLocation = new Point(newX, newY);
	}

	public void turn(ArrayList<Double> degree) {
		currentDirection += degree.get(0);

		if (currentDirection >= 360)
			currentDirection = currentDirection % 360;
	}

	public double distanceTo(Point point) {
		double y = point.getYCoordinate() - currentLocation.getYCoordinate();
		double x = point.getXCoordinate() - currentLocation.getXCoordinate();
		return Math.round(Math.sqrt((y * y) - (x * x)));
	}

	public double bearingTo(Point point) {
		double y = point.getYCoordinate() - currentLocation.getYCoordinate();
		double x = point.getXCoordinate() - currentLocation.getXCoordinate();
		return Math.round(Math.toDegrees(Math.atan(y / x)));
	}
}
