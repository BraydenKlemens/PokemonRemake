package com.pokemon.actions;

import java.util.ArrayList;
import java.util.List;

public class ActionHandler {

	List<Action> actions = new ArrayList<>();

	private int index = 0;

	public ActionHandler() {
	}

	public boolean run() {
		if (index >= actions.size())
			return true;
		if (actions.get(index).run()) {
			index++;
			return index >= actions.size();
		}
		return false;
	}

	public void restart() {
		index = 0;
	}

	public void reset() {
		actions.clear();
		index = 0;
	}

	public void addAction(Action action) {
		actions.add(action);
	}

	public void addAction(Action action, int index) {
		actions.add(index, action);
	}

	public int getIndex() {
		return index;
	}
}
