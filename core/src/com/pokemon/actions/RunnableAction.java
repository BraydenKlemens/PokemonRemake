package com.pokemon.actions;

public class RunnableAction extends Action {

	private final Runnable runnable;
	
	public RunnableAction(Runnable runnable) {
		this.runnable = runnable;
	}

	@Override
	public boolean run() {
		runnable.run();
		return true;
	}

}
