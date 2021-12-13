package cecs575.expressions;

import java.io.BufferedReader;
import cecs575.visitor.*;
import cecs575.etoyProgram.*;
import cecs575.parse.Lexer;
import cecs575.parse.Parser;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


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
	 public void interpret() throws IOException, ParseException {
	        if(!isAlive) {
	            System.out.println("The program has finished executing every line!");
	        }

	        String line = fileReader.readLine();
	        if(line != null) {
	        	ArrayList<Double> values = parseLine(line).interpret(context);
	        	if(line.contains("distance") || line.contains("bearing")) {
	        		Point point = new Point(values.get(0).doubleValue(), values.get(1).doubleValue());
	        		double dist = 0.0;
	        		if(line.contains("distance")) {
	        			dist = context.getTurtle().distanceTo(point);
	        			System.out.println("distance " + dist);
	        		} else {
	        			dist = context.getTurtle().bearingTo(point);
	        			System.out.println("bearing " + dist);
	        		}
	        	}
	        } else {
	            isAlive = false;
	        }
	    }
	 
	 /*
	     * Parses all the lines in the source code and returns a list of the
	     * parsed expressions instead of executing them. Useful to apply visitors
	      * on the source code.
	     */
	    public List<TurtleExpression> getExpressions()
	            throws IOException, ParseException {
	        List<TurtleExpression> expressions = new ArrayList<>();
	        String line;

	        while((line = fileReader.readLine()) != null) {
	            expressions.add(parseLine(line));
	        }

	        return expressions;
	    }
	    private TurtleExpression parseLine(String line)
	            throws IOException, ParseException {
	        try {
	            return Parser.parse(Lexer.tokenize(line), fileReader);
	        } catch (ParseException e) {
	            throw new ParseException("Error parsing line: " + line, e);
	        }
	    }
}
