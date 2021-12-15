package cecs575.visitor;

import cecs575.expressions.*;
import cecs575.command.*;
import cecs575.etoyProgram.*;

public class VisitorCommandProcessor implements TurtleVisitor {
	private TurtleCommandManager turtleCommandManager;
	private Turtle turtle;
	private Context context;

	public VisitorCommandProcessor(Turtle turtle, Context context) {
		this.turtle = turtle;
		this.context = context;
		turtleCommandManager = new TurtleCommandManager();
	}

	public TurtleCommandManager getTurtleCommandManager() {
		return turtleCommandManager;
	}

	@Override
	public void visit(MoveExpression moveExpression) {
		turtleCommandManager.add(new MoveCommand(turtle, moveExpression.getValueExpression().interpret(context)));
	}

	@Override
	public void visit(TurnExpression turnExpression) {
		turtleCommandManager.add(new TurnCommand(turtle, turnExpression.getValueExpression().interpret(context)));
	}

	@Override
	public void visit(AssignmentExpression assignmentExpression) {
		context.setVariableValue(assignmentExpression.getVariableName(),
				assignmentExpression.getConstantExpression().interpret(context));
		turtleCommandManager.add(new AssignmentCommand(turtle, context, assignmentExpression));
	}

	@Override
	public void visit(RepeatExpression repeatExpression) {
		turtleCommandManager.add(new RepeatCommand(turtle, context, repeatExpression));
	}

	@Override
	public void visit(VariableExpression variableExpression) {
		// TODO Auto-generated method stub
	}

	@Override
	public void visit(ConstantExpression constantExpression) {
		// TODO Auto-generated method stub
	}
}
