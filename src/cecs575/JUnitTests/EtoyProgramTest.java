package cecs575.JUnitTests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import cecs575.etoyProgram.*;

public class EtoyProgramTest {
	Point firstPoint;
	Point secondPoint;
	Turtle turtle;

	@Before
	public void setUp() {
		firstPoint = new Point(10.0, 10.0);
		secondPoint = new Point(30.0, 40.0);
		turtle = new Turtle(firstPoint, 180);
	}

	@Test
	public void testDistanceTo() {
		double distance = turtle.distanceTo(secondPoint);
		assertEquals(22.0, distance, 0.0);
	}
	
	@Test
	public void testDistanceZero() {
		secondPoint = new Point(5.0, 5.0);
		double distance = turtle.distanceTo(secondPoint);
		assertEquals(0.0, distance, 0.0);
	}
	
	@Test
	public void testDistanceToNegative() {
		secondPoint = new Point(40.0, 5.0);
		double distance = turtle.distanceTo(secondPoint);
		assertEquals(0.0, distance, 0.0);
	}

	@Test
	public void testBearingTo() {
		firstPoint = new Point(10.0, 10.0);
		secondPoint = new Point(30.0, 40.0);
		turtle = new Turtle(firstPoint, 180);

		double bearing = turtle.bearingTo(secondPoint);
		assertEquals(56.0, bearing, 0.0);
	}

}
