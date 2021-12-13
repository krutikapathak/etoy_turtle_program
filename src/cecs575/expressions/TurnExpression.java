package cecs575.expressions;

import java.util.ArrayList;

import cecs575.visitor.TurtleVisitor;

//import com.csulb.cecs575.visitors.IVisitor;

/*
 * Class to represent move expressions like:
 * turn 90
 *
 * The expression one ITerminalExpression child.
 * The child node is used to get how much to turn.
 *
 * Interpreting this returns the value of how much the turtle turned.
 */
public class TurnExpression implements TurtleExpression {

	private TurtleExpression valueExpression;

	public TurnExpression(TurtleExpression valueExpression) {
		this.valueExpression = valueExpression;
	}

	public TurtleExpression getValueExpression() {
		return valueExpression;
	}

	@Override
	public ArrayList<Double> interpret(Context context) {
		ArrayList<Double> degreesToTurn = valueExpression.interpret(context);
		context.getTurtle().turn(degreesToTurn);

		return degreesToTurn;
	}

	@Override
	public void accept(TurtleVisitor visitor) {
		visitor.visit(this);
	}
}
