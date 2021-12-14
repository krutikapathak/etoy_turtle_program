package cecs575.command;

import java.util.Stack;

import cecs575.etoyProgram.Turtle;

public class CommandProcessor {
	private static CommandProcessor instance;
	private Stack<TurtleCommand> undoStack;
	private Stack<TurtleCommand> redoStack;

	public CommandProcessor() {
		undoStack = new Stack<TurtleCommand>();
		redoStack = new Stack<TurtleCommand>();
	}

	public CommandProcessor getInstance() {
		if (instance == null) {
			instance = new CommandProcessor();
		}
		return instance;
	}

	public Turtle execute(TurtleCommand command) {
		undoStack.push(command);
		return command.execute();
	}

	public Turtle undo() {
		if (undoStack.size() > 0) {
			TurtleCommand command = undoStack.pop();
			redoStack.push(command);
			return command.undo();
		}
		return null;
	}

	public Turtle redo() {
		if (redoStack.size() > 0) {
			TurtleCommand command = redoStack.pop();
			undoStack.push(command);
			return command.execute();
		}
		return null;
	}
}
