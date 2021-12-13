package cecs575.command;

import java.util.ArrayList;

import cecs575.etoyProgram.Turtle;

public class TurnCommand implements TurtleCommand {
	ArrayList<Double> degree;
	private double degrees;
	private Turtle turtle;
	private boolean isExecuted = false;

	public TurnCommand(Turtle turtle, ArrayList<Double> degree) {
		this.degrees = degree.get(0);
		this.turtle = turtle;
	}

	@Override
	public Turtle execute() {
		degree = new ArrayList<>();
		degree.add(degrees);
		turtle.turn(degree);
		isExecuted = true;
		return turtle;
	}

	@Override
	public Turtle undo() {
		degree = new ArrayList<>();
		degree.add(-(degrees));
		if (isExecuted) {
			turtle.turn(degree);
			return turtle;
		} else
			throw new RuntimeException("Can't undo this Command");

	}

}
