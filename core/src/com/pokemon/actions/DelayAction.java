package com.pokemon.actions;

import com.badlogic.gdx.Gdx;

public class DelayAction extends Action {
	
boolean run = false;
	
	private float time = 0;
	private final float duration;

	public DelayAction(float duration) {
		this.duration = duration;
	}

	@Override
	public boolean run() {
		float delta = Gdx.graphics.getDeltaTime();
		if (time < duration) {
			time += delta;
			if (time < duration) run = false;
		} else {
			run = true;
		}
		
		return run;
	}

}
