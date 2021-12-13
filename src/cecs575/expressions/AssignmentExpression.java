package cecs575.expressions;

import java.util.ArrayList;

import cecs575.visitor.TurtleVisitor;

/*
 * Class to represent assignment expressions like:
 * #var = 10
 *
 * The expression contains the variable name and one ConstantExpression child.
 * The child node is used to get the value of the assignment.
 *
 * Interpreting this returns the value of the constant.
 */
public class AssignmentExpression implements TurtleExpression {

	private String variableName;
	private ConstantExpression constantExpression;

	public AssignmentExpression(String variableName, ConstantExpression constantExpression) {
		this.variableName = variableName;
		this.constantExpression = constantExpression;
	}

	public String getVariableName() {
		return variableName;
	}

	public ConstantExpression getConstantExpression() {
		return constantExpression;
	}

	@Override
	public ArrayList<Double> interpret(Context context) {
		ArrayList<Double> constantValue = constantExpression.interpret(context);
		context.setVariableValue(variableName, constantValue);
		return constantValue;
	}

	@Override
	public void accept(TurtleVisitor visitor) {
		visitor.visit(this);	
	}
}
