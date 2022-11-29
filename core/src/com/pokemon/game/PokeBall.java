package com.pokemon.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class PokeBall {

	//gloabls
	private Sprite sprite;
	private Names names;

	/**
	 * @param names
	 */
	public PokeBall(Names names) {
		this.names = names;
		sprite = new Sprite(new Texture("FSEStuff/pokeball.png"));
	}

	/**
	 * @return
	 */
	public Names getName() {
		return names;
	}

	/**
	 * @param x
	 * @param y
	 */
	public void setPosition(int x, int y) {
		sprite.setPosition(x, y);
	}

	/**
	 * @return
	 */
	public Rectangle getBoundingRectangle() {
		return sprite.getBoundingRectangle();
	}

	/**
	 * @param batch
	 */
	public void draw(SpriteBatch batch) {
		sprite.draw(batch);
	}
}
