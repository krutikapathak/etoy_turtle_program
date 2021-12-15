package cecs575.JUnitTests;

import static org.junit.Assert.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import cecs575.command.*;
import cecs575.etoyProgram.Point;
import cecs575.etoyProgram.Turtle;
import cecs575.expressions.*;
import cecs575.parse.*;

public class CommandTest {

	private Turtle turtle;
	private TurtleCommand command;
	private Context context;
	private List<TurtleCommand> commandList;
	private CommandProcessor commandProcessor;

	@Test
	public void testExecute() {
		turtle = new Turtle();
		context = new Context(turtle);
		command = new AssignmentCommand(turtle, context, new AssignmentExpression("#a", new ConstantExpression(20.0)));
		command.execute();
		assertEquals(20.0, context.getVariableValue("#a").get(0), 0.0);
	}

	@Test
	public void testUndo() {
		turtle = new Turtle();
		context = new Context(turtle);
		command = new AssignmentCommand(turtle, context, new AssignmentExpression("#a", new ConstantExpression(20.0)));
		command.execute();

		TurtleCommand assignCommand = new AssignmentCommand(turtle, context,
				new AssignmentExpression("#a", new ConstantExpression(10.0)));
		assignCommand.execute();
		assertEquals(10.0, context.getVariableValue("#a").get(0), 0.0);

		assignCommand.undo();
		assertEquals(20.0, context.getVariableValue("#a").get(0), 0.0);

		command.undo();
		assertNull(context.getVariableValue("#a"));
	}

	@Test
	public void testAdd() {
		TurtleCommandManager commandManager = new TurtleCommandManager();
		ArrayList<Double> list = new ArrayList<>();
		list.add(10.0);
		assertTrue(commandManager.add(new MoveCommand(new Turtle(), list)));
	}

	@Test
	public void testAddAll() {
		TurtleCommandManager commandManager = new TurtleCommandManager();
		TurtleCommandManager commandManagerAppend = new TurtleCommandManager();

		Turtle turtle = new Turtle();
		ArrayList<Double> list = new ArrayList<>();
		list.add(20.0);
		commandManagerAppend.add(new MoveCommand(turtle, list));
		commandManagerAppend.add(new TurnCommand(turtle, list));
		assertTrue(commandManager.addAll(commandManagerAppend));
	}

	@Test
	public void testSize() {
		TurtleCommandManager commandManager = new TurtleCommandManager();
		ArrayList<Double> list = new ArrayList<>();
		list.add(20.0);
		Turtle turtle = new Turtle();
		commandManager.add(new MoveCommand(turtle, list));
		commandManager.add(new TurnCommand(turtle, list));
		assertEquals(2, commandManager.size());
	}

	public void setUpCommandProcessor() {
		turtle = new Turtle();
		context = new Context(turtle);
		commandProcessor = new CommandProcessor();
		commandList = new ArrayList<TurtleCommand>();
		commandList.add(
				new AssignmentCommand(turtle, context, new AssignmentExpression("#l", new ConstantExpression(10.0))));
		ArrayList<Double> value = new ArrayList<>();
		value.add(10.0);
		commandList.add(new MoveCommand(turtle, value));
		ArrayList<Double> degrees = new ArrayList<>();
		degrees.add(90.0);
		commandList.add(new TurnCommand(turtle, degrees));
		commandList.add(new MoveCommand(turtle, value));
		commandList.add(new TurnCommand(turtle, degrees));
		commandList.add(
				new AssignmentCommand(turtle, context, new AssignmentExpression("#l", new ConstantExpression(15.0))));
		ArrayList<Double> moveValue = new ArrayList<>();
		moveValue.add(15.0);
		commandList.add(new MoveCommand(turtle, moveValue));

	}

	@Test
	public void testDoIt() {
		setUpCommandProcessor();
		commandProcessor.getInstance().execute(commandList.get(0));
		assertEquals(10.0, context.getVariableValue("#l").get(0), 0.0);

		commandProcessor.getInstance().execute(commandList.get(1));
		assertEquals(10.0, turtle.location().getXCoordinate(), 0.0);
		assertEquals(0.0, turtle.location().getYCoordinate(), 0.0);
		assertEquals(0.0, turtle.direction(), 0.0);

		commandProcessor.getInstance().execute(commandList.get(2));
		assertEquals(10.0, turtle.location().getXCoordinate(), 0.0);
		assertEquals(0.0, turtle.location().getYCoordinate(), 0.0);
		assertEquals(90.0, turtle.direction(), 0.0);

		commandProcessor.getInstance().execute(commandList.get(3));
		assertEquals(10.0, turtle.location().getXCoordinate(), 0.0);
		assertEquals(10.0, turtle.location().getYCoordinate(), 0.0);
		assertEquals(90.0, turtle.direction(), 0.0);

		commandProcessor.getInstance().execute(commandList.get(4));
		assertEquals(10.0, turtle.location().getXCoordinate(), 0.0);
		assertEquals(10.0, turtle.location().getYCoordinate(), 0.0);
		assertEquals(180.0, turtle.direction(), 0.0);

		commandProcessor.getInstance().execute(commandList.get(5));
		assertEquals(15.0, context.getVariableValue("#l").get(0), 0.0);

		commandProcessor.getInstance().execute(commandList.get(6));
		assertEquals(-5.0, turtle.location().getXCoordinate(), 0.0);
		assertEquals(10.0, turtle.location().getYCoordinate(), 0.0);
		assertEquals(180, turtle.direction(), 0.0);
	}

	@Test
	public void undoTest() {
		setUpCommandProcessor();

		for (TurtleCommand command : commandList) {
			commandProcessor.getInstance().execute(command);
		}

		commandProcessor.getInstance().undo();
		assertEquals(10.0, turtle.location().getXCoordinate(), 0.0);
		assertEquals(10.0, turtle.location().getYCoordinate(), 0.0);
		assertEquals(180.0, turtle.direction(), 0.0);
		assertEquals(15.0, context.getVariableValue("#l").get(0), 0.0);

		commandProcessor.getInstance().undo();
		assertEquals(10.0, context.getVariableValue("#l").get(0), 0.0);

		commandProcessor.getInstance().undo();
		assertEquals(10.0, turtle.location().getXCoordinate(), 0.0);
		assertEquals(10.0, turtle.location().getYCoordinate(), 0.0);
		assertEquals(90.0, turtle.direction(), 0.0);

		commandProcessor.getInstance().undo();
		assertEquals(10.0, turtle.location().getXCoordinate(), 0.0);
		assertEquals(0, turtle.location().getYCoordinate(), 0.0);
		assertEquals(90, turtle.direction(), 0.0);

		commandProcessor.getInstance().undo();
		assertEquals(10.0, turtle.location().getXCoordinate(), 0.0);
		assertEquals(0.0, turtle.location().getYCoordinate(), 0.0);
		assertEquals(0.0, turtle.direction(), 0.0);

		commandProcessor.getInstance().undo();
		assertEquals(0.0, turtle.location().getXCoordinate(), 0.0);
		assertEquals(0.0, turtle.location().getYCoordinate(), 0.0);
		assertEquals(0.0, turtle.direction(), 0.0);
		assertEquals(10.0, context.getVariableValue("#l").get(0), 0.0);

		commandProcessor.getInstance().undo();
		assertNull(context.getVariableValue("#l"));
	}

	@Test
	public void redoTest() {
		setUpCommandProcessor();
		for (TurtleCommand command : commandList) {
			commandProcessor.getInstance().execute(command);
		}

		commandProcessor.getInstance().undo();
		commandProcessor.getInstance().redo();
		assertEquals(-5.0, turtle.location().getXCoordinate(), 0.0);
		assertEquals(10.0, turtle.location().getYCoordinate(), 0.0);
		assertEquals(180.0, turtle.direction(), 0.0);

		commandProcessor.getInstance().undo();
		commandProcessor.getInstance().undo();
		commandProcessor.getInstance().undo();
		commandProcessor.getInstance().redo();
		assertEquals(10.0, turtle.location().getXCoordinate(), 0.0);
		assertEquals(10.0, turtle.location().getYCoordinate(), 0.0);
		assertEquals(180.0, turtle.direction(), 0.0);

		commandProcessor.getInstance().undo();
		commandProcessor.getInstance().undo();
		commandProcessor.getInstance().undo();
		commandProcessor.getInstance().redo();
		assertEquals(10.0, turtle.location().getXCoordinate(), 0.0);
		assertEquals(0.0, turtle.location().getYCoordinate(), 0.0);
		assertEquals(90.0, turtle.direction(), 0.0);

		commandProcessor.getInstance().undo();
		commandProcessor.getInstance().undo();
		assertEquals(0.0, turtle.location().getXCoordinate(), 0.0);
		assertEquals(0.0, turtle.location().getYCoordinate(), 0.0);
		assertEquals(0.0, turtle.direction(), 0.0);
	}

	@Test
	public void testMoveCommandExecute() {
		turtle = new Turtle();
		ArrayList<Double> list = new ArrayList<>();
		list.add(20.0);
		command = new MoveCommand(turtle, list);
		command.execute();
		Point expectedLocation = new Point(20.0, 0.0);
		assertEquals(expectedLocation.getXCoordinate(), turtle.location().getXCoordinate(), 0.0);
		assertEquals(expectedLocation.getYCoordinate(), turtle.location().getYCoordinate(), 0.0);
	}

	@Test
	public void testMoveCommandUndo() {
		turtle = new Turtle();
		ArrayList<Double> list = new ArrayList<>();
		list.add(20.0);
		command = new MoveCommand(turtle, list);
		command.execute();
		command.undo();
		Point expectedLocation = new Point(0.0, 0.0);
		assertEquals(expectedLocation.getXCoordinate(), turtle.location().getXCoordinate(), 0.0);
		assertEquals(expectedLocation.getYCoordinate(), turtle.location().getYCoordinate(), 0.0);
	}

	@Test
	public void testTurnCommandExecute() {
		turtle = new Turtle();
		ArrayList<Double> list = new ArrayList<>();
		list.add(90.0);
		command = new TurnCommand(turtle, list);
		command.execute();
		assertEquals(90.0, turtle.direction(), 0.0);
	}

	@Test
	public void testTurnCommandUndo() {
		turtle = new Turtle();
		ArrayList<Double> list = new ArrayList<>();
		list.add(90.0);
		command = new TurnCommand(turtle, list);
		command.execute();
		command.undo();
		assertEquals(0.0, turtle.direction(), 0.0);
	}

	@Test
	public void testRepeatCommandExecute() throws ParseException, IOException {
		turtle = new Turtle();
		context = new Context(turtle);
		TurtleExpression numberOfRepetitionsExpression = new ConstantExpression(4.0);
		RepeatExpression repeatExpression = new RepeatExpression(numberOfRepetitionsExpression);
		List<String> newTokens = Lexer.tokenize("move 10");
		repeatExpression.addExpression(Parser.parse(newTokens, null));
		command = new RepeatCommand(turtle, context, repeatExpression);
		command.execute();
		assertEquals(40.0, turtle.location().getXCoordinate(), 0.0);
	}
}
