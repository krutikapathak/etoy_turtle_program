package cecs575.expressions;

import java.util.ArrayList;
import java.util.HashMap;
import cecs575.etoyProgram.*;

public class Context {
	HashMap<String,ArrayList<Double>> values = new HashMap<>(); 
	private Turtle turtle;
	
	public Context(Turtle turtle) {
		this.turtle = turtle;
	}
	
	public ArrayList<Double> getVariableValue(String name) {
		return values.get(name);
	}

	public void setVariableValue(String name, ArrayList<Double> value) {
		values.put(name, value);
	}
	
	public Turtle getTurtle() {
		return turtle;
	}
	
	public void removeValues(String value) {
		values.remove(value);
	}
}
