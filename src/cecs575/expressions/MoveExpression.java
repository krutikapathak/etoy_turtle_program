package cecs575.expressions;

import java.util.ArrayList;

import cecs575.visitor.*;

public class MoveExpression implements TurtleExpression {
	private TurtleExpression valueExpression;
	
	public MoveExpression(TurtleExpression valueExpression) {
		this.valueExpression = valueExpression;
	}
	
	public TurtleExpression getValueExpression(){
		return valueExpression;
	}
	@Override
	public ArrayList<Double> interpret(Context context) {
		ArrayList<Double> distance = new ArrayList<>();
		distance.addAll(valueExpression.interpret(context));
		context.getTurtle().move(distance);
		return distance;
	}

	@Override
	public void accept(TurtleVisitor visitor) {
		visitor.visit(this);		
	}
	
}
