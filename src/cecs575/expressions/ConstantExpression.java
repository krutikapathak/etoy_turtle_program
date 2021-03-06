package cecs575.expressions;

import java.util.ArrayList;
/*
 * Expression to evaluate constant values.
 * Used as terminal/leaf nodes.
 *
 * Interpreting this returns the constant value.
 */

import cecs575.visitor.TurtleVisitor;

public class ConstantExpression implements TurtleExpression {
	private ArrayList<Double> constantValues = new ArrayList<>();


    public ConstantExpression(double value) {
        constantValues.add(value);
    }
    
	public void setConstantValues(double value) {
		this.constantValues.add(value);
	}

	@Override
    public ArrayList<Double> interpret(Context context) {
        return constantValues;
    }

	@Override
	public void accept(TurtleVisitor visitor) {
		visitor.visit(this);		
	}

}
