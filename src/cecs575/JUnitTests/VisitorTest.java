package cecs575.JUnitTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

import cecs575.command.*;
import cecs575.etoyProgram.*;
import cecs575.expressions.*;
import cecs575.visitor.*;

public class VisitorTest {

	@Test
	public void testGetTotalDistance() throws IOException, ParseException {
		Reader reader = new Reader("input1");
		Context context = reader.getContext();
		DistanceCalculator visitor = new DistanceCalculator(context);

		List<TurtleExpression> expressions = reader.getExpressions();

		for (TurtleExpression expression : expressions) {
			expression.accept(visitor);
		}
		assertEquals(45.0, visitor.getTotalDistance(), 0.0);
	}

	@Test
	public void testGetCommandList() throws IOException, ParseException {
		Reader reader = new Reader("input2");
		Context context = reader.getContext();
		Turtle turtle = new Turtle();
		VisitorCommandProcessor visitor = new VisitorCommandProcessor(turtle, context);

		List<TurtleExpression> expressions = reader.getExpressions();

		for (TurtleExpression expression : expressions) {
			expression.accept(visitor);
		}

		TurtleCommandManager commandList = visitor.getTurtleCommandManager();
		assertNotNull(commandList);
		assertEquals(8, commandList.size());
		assertTrue(commandList.next() instanceof AssignmentCommand);
		assertTrue(commandList.next() instanceof MoveCommand);
		assertTrue(commandList.next() instanceof TurnCommand);
		assertTrue(commandList.next() instanceof MoveCommand);
		assertTrue(commandList.next() instanceof TurnCommand);
		assertTrue(commandList.next() instanceof MoveCommand);
		assertTrue(commandList.next() instanceof AssignmentCommand);
		assertTrue(commandList.next() instanceof MoveCommand);
	}
}
