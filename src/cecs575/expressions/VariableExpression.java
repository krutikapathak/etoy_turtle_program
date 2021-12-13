package cecs575.expressions;

import java.util.ArrayList;

import cecs575.visitor.TurtleVisitor;

public class VariableExpression implements TurtleExpression{
	private String variableName;


    public VariableExpression(String name) {
        this.variableName = name;
    }


    public String getVariableName() {
        return variableName;
    }


    @Override
    public ArrayList<Double> interpret(Context context) {
        return context.getVariableValue(variableName);
    }


	@Override
	public void accept(TurtleVisitor visitor) {
		visitor.visit(this);
	}

}
