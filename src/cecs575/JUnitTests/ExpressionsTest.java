package cecs575.JUnitTests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import cecs575.etoyProgram.*;
import cecs575.parse.*;
import cecs575.expressions.*;

public class ExpressionsTest {

	@Test
	public void testAssignment() {
		Turtle turtle = new Turtle();
		Context context = new Context(turtle);
		ArrayList<Double> value = new ArrayList<>();
		value.add(10.0);
		context.setVariableValue("#length", value);
		AssignmentExpression assign = new AssignmentExpression("#key",
				new ConstantExpression(Double.valueOf(context.getVariableValue("#length").get(0))));
		assign.interpret(context);

		assertEquals(10.0, context.getVariableValue(assign.getVariableName()).get(0), 0.0);
	}

	@Test
	public void testMove() {
		Turtle turtle = new Turtle();
		Context context = new Context(turtle);
		MoveExpression move = new MoveExpression(new ConstantExpression(10.0));
		assertEquals(10.0, move.interpret(context).get(0), 0.0);
	}

	@Test
	public void testTurnForward() {
		Point point = new Point(50.0, 50.0);
		Turtle turtle = new Turtle(point, 90);
		Context context = new Context(turtle);
		TurnExpression turn = new TurnExpression(new ConstantExpression(180.0));
		turn.interpret(context);
		assertEquals(270.0, turtle.direction(), 0.0);
	}

	@Test
	public void testTurnReverse() {
		Point point = new Point(50.0, 50.0);
		Turtle turtle = new Turtle(point, 180);
		Context context = new Context(turtle);
		TurnExpression turn = new TurnExpression(new ConstantExpression(-180.0));
		turn.interpret(context);
		assertEquals(0.0, turtle.direction(), 0.0);
	}

	@Test
	public void testTurnTo0AfterCompleteRound() {
		Point point = new Point(50.0, 50.0);
		Turtle turtle = new Turtle(point, 180);
		Context context = new Context(turtle);
		TurnExpression turn = new TurnExpression(new ConstantExpression(180.0));
		turn.interpret(context);
		assertEquals(0.0, turtle.direction(), 0.0);
	}

	@Test
	public void testRepeat() throws ParseException, IOException {
		Turtle turtle = new Turtle();
		Context context = new Context(turtle);
		TurtleExpression numberOfRepetitionsExpression = new ConstantExpression(4.0);
		RepeatExpression repeatExpression = new RepeatExpression(numberOfRepetitionsExpression);
		List<String> newTokens = Lexer.tokenize("move 10");
		repeatExpression.addExpression(Parser.parse(newTokens, null));

		assertEquals(40.0, repeatExpression.interpret(context).get(0), 0.0);
	}

	@Test
	public void testConstant() {
		Turtle turtle = new Turtle();
		Context context = new Context(turtle);
		TurtleExpression constant = new ConstantExpression(10.0);

		assertEquals(10.0, constant.interpret(context).get(0), 0.0);
	}

	@Test
	public void testVariable() {
		Turtle turtle = new Turtle();
		Context context = new Context(turtle);
		ArrayList<Double> value = new ArrayList<>();
		value.add(10.0);
		context.setVariableValue("#length", value);
		TurtleExpression variable = new VariableExpression("#length");

		assertEquals(10.0, variable.interpret(context).get(0), 0.0);
	}
}
