package com.pokemon.actions;

public class RunnableAction extends Action {

	private boolean run = false;
	private Runnable runnable;
	
	public RunnableAction(Runnable runnable) {
		this.runnable = runnable;
	}

	@Override
	public boolean run() {
		if (!run) {
			runnable.run();
		}
		run = true;
		return run;
	}

}
