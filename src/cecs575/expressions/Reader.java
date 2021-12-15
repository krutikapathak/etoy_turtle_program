package cecs575.expressions;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import cecs575.etoyProgram.Point;
import cecs575.etoyProgram.Turtle;
import cecs575.parse.Lexer;
import cecs575.parse.Parser;

public class Reader {

	private BufferedReader fileReader;
	private Turtle turtle = new Turtle();
	private Context context = new Context(turtle);
	private boolean isAlive = true;

	public Reader(String fileName) throws IOException {
		URL resourceUrl = ClassLoader.getSystemResource(fileName);
		Path filePath = null;

		if (resourceUrl != null) {
			try {
				filePath = Paths.get(resourceUrl.toURI());
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
		} else {
			filePath = Paths.get(fileName);
		}

		fileReader = Files.newBufferedReader(filePath);
	}

	public Context getContext() {
		return context;
	}

	public Turtle getTurtle() {
		return turtle;
	}

	public boolean isAlive() {
		return isAlive;
	}

	public boolean interpret() throws IOException, ParseException {
		if (!isAlive) {
			System.out.println("The program has finished executing every line!");
		}

		String line = fileReader.readLine();
		if (line != null) {
			parseLine(line).interpret(context);
		} else {
			isAlive = false;
		}
		return isAlive;
	}

	/*
	 * Parses all the lines in the source code and returns a list of the parsed
	 * expressions instead of executing them. Useful to apply visitors on the source
	 * code.
	 */
	public List<TurtleExpression> getExpressions() throws IOException, ParseException {
		List<TurtleExpression> expressions = new ArrayList<>();
		String line;

		while ((line = fileReader.readLine()) != null) {
			expressions.add(parseLine(line));
		}

		return expressions;
	}

	private TurtleExpression parseLine(String line) throws IOException, ParseException {
		try {
			return Parser.parse(Lexer.tokenize(line), fileReader);
		} catch (ParseException e) {
			throw new ParseException("Error parsing line: " + line, e);
		}
	}
}
