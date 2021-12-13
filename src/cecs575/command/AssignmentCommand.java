package cecs575.command;

import java.util.ArrayList;

import cecs575.etoyProgram.Turtle;
import cecs575.expressions.AssignmentExpression;
import cecs575.expressions.Context;

public class AssignmentCommand implements TurtleCommand {

	private AssignmentExpression assign;
	public ArrayList<Double> previousValue;
	private Turtle turtle;
	private Context context;

	public AssignmentCommand(Turtle turtle, Context context, AssignmentExpression assign) {
		this.turtle = turtle;
		this.assign = assign;
		this.context = context;
	}

	@Override
	public Turtle execute() {
		previousValue = context.getVariableValue(assign.getVariableName());
		context.setVariableValue(assign.getVariableName(), assign.getConstantExpression().interpret(context));
		return turtle;
	}

	@Override
	public Turtle undo() {
		if (previousValue == null) {
			context.removeValues(assign.getVariableName());
		} else {
			context.setVariableValue(assign.getVariableName(), previousValue);
		}
		return turtle;
	}
}
