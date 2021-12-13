package cecs575.visitor;

import cecs575.expressions.*;

public class DistanceCalculator implements TurtleVisitor {
	private double totalDistance;
	private Context context;

	public DistanceCalculator(Context context) {
		this.context = context;
	}

	public double getTotalDistance() {
		return totalDistance;
	}

	@Override
	public void visit(MoveExpression moveExpression) {
		moveExpression.getValueExpression().accept(this);
	}

	@Override
	public void visit(AssignmentExpression assignmentExpression) {
		assignmentExpression.interpret(context);
	}

	@Override
	public void visit(RepeatExpression repeatExpression) {
		TurtleExpression numberOfRepeats = repeatExpression.getNumberOfRepetitionsExpression();
		double numberOfRepetitions = (int) (numberOfRepeats.interpret(context).get(0)).doubleValue();

		for (int i = 0; i < numberOfRepetitions; i++) {
			for (TurtleExpression expression : repeatExpression.getExpressions()) {
				expression.accept(this);
			}
		}
	}

	@Override
	public void visit(VariableExpression variableExpression) {
		totalDistance += variableExpression.interpret(context).get(0);
	}

	@Override
	public void visit(ConstantExpression constantExpression) {
		totalDistance += constantExpression.interpret(context).get(0);
	}

	@Override
	public void visit(TurnExpression turnExpression) {

	}
}
