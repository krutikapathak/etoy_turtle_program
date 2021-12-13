package cecs575.expressions;

import java.util.ArrayList;

import cecs575.visitor.*;

public interface TurtleExpression {
	ArrayList<Double> interpret(Context context);
	public void accept(TurtleVisitor visitor);
}
