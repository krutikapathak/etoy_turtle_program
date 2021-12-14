package cecs575.command;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class TurtleCommandManager implements Iterator<TurtleCommand> {
	private int currentIndex;
	private List<TurtleCommand> commandList;

	public TurtleCommandManager() {
		currentIndex = 0;
		this.commandList = new ArrayList<TurtleCommand>();
	}

	public boolean add(TurtleCommand command) {
		return commandList.add(command);
	}

	public boolean addAll(TurtleCommandManager commandListManager) {
		return this.commandList.addAll(commandListManager.commandList);
	}

	public TurtleCommand get(int index) {
		TurtleCommand command = commandList.get(index);
		return command;
	}

	public int size() {
		return commandList.size();
	}

	@Override
	public boolean hasNext() {
		return currentIndex < commandList.size();
	}

	@Override
	public TurtleCommand next() {
		if (!hasNext())
			throw new NoSuchElementException();
		return get(currentIndex++);
	}
}
