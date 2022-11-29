package com.pokemon.game;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public class Oak implements ICharacter {
	
	//globals
	private Sprite sprite;

	public Oak() {
		//sprite creation
		sprite = new Sprite(new Texture("FSEStuff/oak.png"));
		sprite.setPosition(194, 248);
	}

	/**
	 * @return
	 */
	public Rectangle getBoundingRectangle() {
		return sprite.getBoundingRectangle();
	}

	/*
	 * once pokemon selected say something once leave town and come back say
	 * something different
	 */

	@Override
	public ArrayList<String> getText(Starters starter) {
		ArrayList<String> text = new ArrayList<String>();
		if (!starter.hasPokemon()) {
			text.add("Hello Klem! nice to see you again...");
			text.add("I have 3 Pokemon here for you");
			text.add("The choice is yours!");
		} else {
			text.add("Klem obtained the Pokeball from professor Oak.");
			text.add("Wonderful choice, " + starter.getName() + " will be a joy to raise!");
			text.add("Now that you have your first POKeMON");
			text.add("You should go see your mom before you leave!");
		}
		return text;
	}

	@Override
	public Batch draw(Batch batch, String location) {
		if (location.equals("lab")) {
			sprite.draw(batch);
		}
		return null;
	}

}
