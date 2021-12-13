package cecs575.command;

import cecs575.etoyProgram.*;

public interface TurtleCommand {

	public Turtle execute();

	public Turtle undo();
}
