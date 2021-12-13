package cecs575.expressions;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cecs575.command.MoveCommand;
import cecs575.command.TurtleCommand;
import cecs575.etoyProgram.Turtle;
import cecs575.visitor.DistanceCalculator;

public class Main {
	static Reader reader;

	public static void main(String[] args) throws IOException, ParseException {
		reader = new Reader("input");
		try (BufferedReader br = new BufferedReader(new FileReader("input"))) {
			while ((br.readLine()) != null) {
				reader.interpret();
			}
			undoCommands();
			displayTotalDistance();
		}
	}

	private static void displayTotalDistance() throws IOException, ParseException {
		reader = new Reader("input");
		Context context = reader.getContext();
		DistanceCalculator visitor = new DistanceCalculator(context);

		List<TurtleExpression> expressions = reader.getExpressions();

		for (TurtleExpression expression : expressions) {
			expression.accept(visitor);
		}

		System.out.println("Total distance: " + visitor.getTotalDistance());
	}

	private static void undoCommands() throws IOException, ParseException {
		Turtle turtle = reader.getTurtle();
		ArrayList<Double> moveUndo = new ArrayList<>();
		
		moveUndo.add(10.0);
		TurtleCommand undocomm = new MoveCommand(turtle, moveUndo);
		turtle = undocomm.undo();
		
		moveUndo = new ArrayList<>();
		moveUndo.add(10.0);
		undocomm = new MoveCommand(turtle, moveUndo);
		undocomm.execute();
	}
}
