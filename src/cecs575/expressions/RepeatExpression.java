package cecs575.expressions;

import java.util.ArrayList;
import java.util.List;

import cecs575.visitor.TurtleVisitor;

/*
 * Class to represent repeat expressions like:
 * repeat 3
 *     move 10
 *     turn 90
 * end
 *
 * The expression contains the expressions to repeat and one
 * ITerminalExpression child.
 * The ITerminalExpression node is used to get the number of times to repeat
 * the expressions.
 *
 * Interpreting this returns the cumulative value of the expressions
 * multiplied by the number of iterations.
 */
public class RepeatExpression implements TurtleExpression {

	private TurtleExpression numberOfRepetitionsExpression;
	private List<TurtleExpression> expressions = new ArrayList<>();

	public RepeatExpression(TurtleExpression numberOfRepetitions) {
		this.numberOfRepetitionsExpression = numberOfRepetitions;
	}

	public TurtleExpression getNumberOfRepetitionsExpression() {
		return numberOfRepetitionsExpression;
	}

	public List<TurtleExpression> getExpressions() {
		return expressions;
	}

	@Override
	public ArrayList<Double> interpret(Context context) {
		ArrayList<Double> value = new ArrayList<>();
		value.add(0, 0.0);
		int numberOfRepetitions = (int) (numberOfRepetitionsExpression.interpret(context).get(0)).doubleValue();

		for (int i = 0; i < numberOfRepetitions; i++) {
			for (TurtleExpression expression : expressions) {
				value.set(0, value.get(0) + expression.interpret(context).get(0));
			}
		}
		return value;
	}

	public void addExpression(TurtleExpression expression) {
		expressions.add(expression);
	}

	@Override
	public void accept(TurtleVisitor visitor) {
		visitor.visit(this);
	}
}
