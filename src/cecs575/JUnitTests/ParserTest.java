package cecs575.JUnitTests;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import cecs575.etoyProgram.*;
import cecs575.expressions.*;
import cecs575.parse.*;

public class ParserTest {

	private final String variableName = "var";
	private final Double constantValue = 10.0;
	private final String assignment = "#var = 10";
	private final String moveConstant = "move 10";
	private final String moveVariable = "move #var";
	private final String turnConstant = "turn 10";
	private final String turnVariable = "turn #var";

	private final String repeatTimes = "repeat 10";
	private final String repeatBlock = moveConstant + "\n" + turnConstant + "\n" + "end";

	private final String repeatVariableTimes = "repeat #var";
	private final String repeatVariableBlock = moveVariable + "\n" + turnVariable + "\n" + "end";
	ArrayList<Double> value;
	private Context context;

	@Before
	public void setUp() {
		context = new Context(new Turtle());
		value = new ArrayList<>();
	}

	@Test
	public void emptyFileTest() throws IOException, ParseException {
		Reader reader = new Reader("input3");
		assertFalse(reader.interpret());
	}

	@Test
	public void nonEmptyFileTest() throws IOException, ParseException {
		Reader reader = new Reader("input4");
		assertTrue(reader.interpret());
		assertFalse(reader.interpret());
	}

	@Test
	public void constantTest() {
		ConstantExpression constantExpression = new ConstantExpression(constantValue);
		assertEquals(constantValue, constantExpression.interpret(context).get(0));
	}

	@Test
	public void variableTest() {
		VariableExpression variableExpression = new VariableExpression(variableName);
		value.add(constantValue);
		context.setVariableValue(variableName, value);

		assertEquals(variableName, variableExpression.getVariableName());
		assertEquals(constantValue, variableExpression.interpret(context).get(0));
	}

	@Test
	public void nonExistentVariableTest() {
		VariableExpression variableExpression = new VariableExpression(variableName);
		value.add(constantValue);
		context.setVariableValue("UnknownVariable", value);

		assertNull(variableExpression.interpret(context));
	}

	@Test
	public void tokenizeAssignmentTest() {
		List<String> tokens = Lexer.tokenize(assignment);

		assertEquals(4, tokens.size());
		assertEquals("#var", tokens.get(0));
		assertEquals("=", tokens.get(1));
		assertEquals("10", tokens.get(2));
		assertEquals("\n", tokens.get(3));
	}

	@Test
	public void parseAssignmentTest() throws IOException, ParseException {
		List<String> tokens = Lexer.tokenize(assignment);

		TurtleExpression expression = Parser.parse(tokens, null);
		assertTrue(expression instanceof AssignmentExpression);

		AssignmentExpression assignmentExpression = (AssignmentExpression) expression;

		assertEquals(variableName, assignmentExpression.getVariableName());
		assertEquals(constantValue, assignmentExpression.getConstantExpression().interpret(context).get(0));
		assertEquals(constantValue, assignmentExpression.interpret(context).get(0));
	}

	@Test
	public void tokenizeMoveConstantTest() {
		List<String> tokens = Lexer.tokenize(moveConstant);

		assertEquals(3, tokens.size());
		assertEquals("move", tokens.get(0));
		assertEquals(String.valueOf(constantValue.intValue()), tokens.get(1));
		assertEquals("\n", tokens.get(2));
	}

	@Test
	public void parseMoveConstantTest() throws IOException, ParseException {
		moveExpressionTest(moveConstant);
	}

	@Test
	public void tokenizeMoveVariableTest() {
		List<String> tokens = Lexer.tokenize(moveVariable);

		assertEquals(3, tokens.size());
		assertEquals("move", tokens.get(0));
		assertEquals("#" + variableName, tokens.get(1));
		assertEquals("\n", tokens.get(2));
	}

	@Test
	public void parseMoveVariableTest() throws IOException, ParseException {
		value.add(constantValue);
		context.setVariableValue(variableName, value);
		moveExpressionTest(moveVariable);
	}

	@Test
	public void tokenizeTurnVariableTest() {
		List<String> tokens = Lexer.tokenize(turnVariable);

		assertEquals(3, tokens.size());
		assertEquals("turn", tokens.get(0));
		assertEquals("#" + variableName, tokens.get(1));
		assertEquals("\n", tokens.get(2));
	}

	@Test
	public void parseTurnVariableTest() throws IOException, ParseException {
		value.add(constantValue);
		context.setVariableValue(variableName, value);
		turnExpressionTest(turnVariable);
	}

	@Test
	public void tokenizeTurnConstantTest() {
		List<String> tokens = Lexer.tokenize(turnConstant);

		assertEquals(3, tokens.size());
		assertEquals("turn", tokens.get(0));
		assertEquals(String.valueOf(constantValue.intValue()), tokens.get(1));
		assertEquals("\n", tokens.get(2));
	}

	@Test
	public void parseTurnConstantTest() throws IOException, ParseException {
		turnExpressionTest(turnConstant);
	}

	@Test
	public void tokenizeRepeatConstantTest() {
		List<String> tokens = Lexer.tokenize(repeatTimes);

		assertEquals(3, tokens.size());
		assertEquals("repeat", tokens.get(0));
		assertEquals(String.valueOf(constantValue.intValue()), tokens.get(1));
		assertEquals("\n", tokens.get(2));
	}

	@Test
	public void parseRepeatConstantTest() throws IOException, ParseException {
		repeatExpressionTest(repeatTimes, makeReaderFor(repeatBlock));
	}

	@Test
	public void tokenizeRepeatVariableTest() {
		List<String> tokens = Lexer.tokenize(repeatVariableTimes);

		assertEquals(3, tokens.size());
		assertEquals("repeat", tokens.get(0));
		assertEquals("#" + variableName, tokens.get(1));
		assertEquals("\n", tokens.get(2));
	}

	@Test
	public void parseRepeatVariableTest() throws IOException, ParseException {
		value.add(constantValue);
		context.setVariableValue(variableName, value);
		repeatExpressionTest(repeatVariableTimes, makeReaderFor(repeatVariableBlock));
	}

	@Test
	public void invalidTokenTest() {
		parseInvalidLineTest("invalid 10");
	}

	@Test
	public void invalidVariableAssignmentTest() {
		parseInvalidLineTest("#1var = 10");
	}

	@Test
	public void invalidTokenAssignmentTest() {
		parseInvalidLineTest("#var is = 10");
	}

	@Test
	public void invalidAssignmentWithoutEqualsTest() {
		parseInvalidLineTest("#var");
	}

	@Test
	public void invalidConstantAssignmentTest() {
		parseInvalidLineTest("#var = something");
	}

	@Test
	public void invalidAssignmentExtraCharactersTest() {
		parseInvalidLineTest("#var = 10 abc");
	}

	@Test
	public void invalidMoveNoValueTest() {
		parseInvalidLineTest("move");
	}

	@Test
	public void invalidMoveExtraCharactersTest() {
		parseInvalidLineTest("move 10 abc");
	}

	@Test
	public void invalidTurnNoValueTest() {
		parseInvalidLineTest("turn");
	}

	@Test
	public void invalidTurnExtraCharactersTest() {
		parseInvalidLineTest("turn 10 abc");
	}

	@Test
	public void invalidRepeatMissingRepetitionsTest() {
		parseInvalidLineTest("repeat");
	}

	@Test
	public void invalidRepeatExtraCharactersTest() {
		parseInvalidLineTest("repeat 10 abc");
	}

	@Test
	public void invalidConstantTest() {
		parseInvalidLineTest("move 10a");
	}

	@Test
	public void invalidVariableNameTest() {
		parseInvalidLineTest("move #10a");
	}

	private void moveExpressionTest(String moveExpressionString) throws ParseException, IOException {
		List<String> tokens = Lexer.tokenize(moveExpressionString);

		TurtleExpression expression = Parser.parse(tokens, null);
		assertMoveExpression(expression);
	}

	private void assertMoveExpression(TurtleExpression expression) {
		assertTrue(expression instanceof MoveExpression);

		MoveExpression moveExpression = (MoveExpression) expression;

		assertEquals(constantValue, moveExpression.interpret(context).get(0));
		assertEquals(constantValue, moveExpression.getValueExpression().interpret(context).get(0));
	}

	private void turnExpressionTest(String turnExpressionString) throws ParseException, IOException {
		List<String> tokens = Lexer.tokenize(turnExpressionString);

		TurtleExpression expression = Parser.parse(tokens, null);
		assertTurnExpression(expression);
	}

	private void assertTurnExpression(TurtleExpression expression) {
		assertTrue(expression instanceof TurnExpression);

		TurnExpression turnExpression = (TurnExpression) expression;

		assertEquals(constantValue, turnExpression.interpret(context).get(0));
		assertEquals(constantValue, turnExpression.getValueExpression().interpret(context).get(0));
	}

	private void parseInvalidLineTest(String invalidLine) {
		assertThrows(ParseException.class, () -> Parser.parse(Lexer.tokenize(invalidLine), null));
	}

	private void repeatExpressionTest(String repeatLine, BufferedReader reader) throws IOException, ParseException {

		List<String> tokens = Lexer.tokenize(repeatLine);

		TurtleExpression expression = Parser.parse(tokens, reader);
		assertTrue(expression instanceof RepeatExpression);

		RepeatExpression repeatExpression = (RepeatExpression) expression;

		Double numberOfRepetitions = repeatExpression.getNumberOfRepetitionsExpression().interpret(context).get(0);
		assertEquals(constantValue, numberOfRepetitions);

		List<TurtleExpression> expressions = repeatExpression.getExpressions();
		assertMoveExpression(expressions.get(0));
		assertTurnExpression(expressions.get(1));

		Double expectedFinalValue = (constantValue + constantValue) * numberOfRepetitions;

		assertEquals(expectedFinalValue, repeatExpression.interpret(context).get(0));
	}

	private BufferedReader makeReaderFor(String expression) {
		return new BufferedReader(new StringReader(expression));
	}
}
