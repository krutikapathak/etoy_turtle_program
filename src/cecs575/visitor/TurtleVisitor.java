package cecs575.visitor;
import cecs575.expressions.*;

public interface TurtleVisitor {
	public void visit(MoveExpression moveExpression);
	public void visit(TurnExpression turnExpression);
	public void visit(AssignmentExpression assignmentExpression);
	public void visit(RepeatExpression repeatExpression);
	public void visit(VariableExpression variableExpression);
	public void visit(ConstantExpression constantExpression);
}
