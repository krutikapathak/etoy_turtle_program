package cecs575.command;

import cecs575.etoyProgram.Turtle;
import cecs575.expressions.*;
import cecs575.visitor.VisitorCommandProcessor;

public class RepeatCommand implements TurtleCommand {
	private TurtleCommandManager turtleCommandManager;
	private RepeatExpression repeatExpression;
	private Turtle turtle;
	private Context context;
	private int index = 0;

	public RepeatCommand(Turtle turtle, Context context, RepeatExpression repeatExpression) {
		this.repeatExpression = repeatExpression;
		this.turtle = turtle;
		this.context = context;
	}

	@Override
	public Turtle execute() {
		if (!hasLoop()) {
			VisitorCommandProcessor visitor = new VisitorCommandProcessor(turtle, context);
			repeatExpression.getExpressions();
			int numberOfRepetitions = (int) (repeatExpression.getNumberOfRepetitionsExpression().interpret(context)
					.get(0)).doubleValue();
			for (int i = 0; i < numberOfRepetitions; i++) {
				for (TurtleExpression expression : repeatExpression.getExpressions()) {
					expression.accept(visitor);
					turtleCommandManager = visitor.getTurtleCommandManager();
					index++;
				}
			}
		}
		return turtle;
	}

	@Override
	public Turtle undo() {
		return turtle;

	}

	public TurtleCommandManager getCommandListManager() {
		return turtleCommandManager;
	}

	public boolean hasLoop() {
		return index < (repeatExpression.getNumberOfRepetitionsExpression().interpret(context)).get(0);
	}
}
