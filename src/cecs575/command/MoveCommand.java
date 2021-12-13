package cecs575.command;

import java.util.ArrayList;

import cecs575.etoyProgram.Turtle;

public class MoveCommand implements TurtleCommand {
	ArrayList<Double> dist;
	private double distance;
	private Turtle turtle;
	private boolean isExecuted = false;

	public MoveCommand(Turtle turtle, ArrayList<Double> distance) {
		this.distance = distance.get(0);
		this.turtle = turtle;
	}

	@Override
	public Turtle execute() {
		dist = new ArrayList<>();
		dist.add(distance);
		turtle.move(dist);
		isExecuted = true;
		return turtle;
	}

	@Override
	public Turtle undo() {
		dist = new ArrayList<>();
		dist.add(distance * -1.0);
		isExecuted = true;
		if (isExecuted) {
			turtle.move(dist);
			return turtle;
		} else
			throw new RuntimeException("Can't undo this Command");
	}
}
