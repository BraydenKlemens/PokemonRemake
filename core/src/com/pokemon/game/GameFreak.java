package com.pokemon.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameFreak extends Game {
	//launches game
	public SpriteBatch batch;
	public GameScreen gameScreen;

	@Override
	public void create() {
		//game batch start screen
		batch = new SpriteBatch();
		gameScreen = new GameScreen(this, new Player());
		setScreen(gameScreen);
	}

	@Override
	public void render() {
		super.render();
	}
}
