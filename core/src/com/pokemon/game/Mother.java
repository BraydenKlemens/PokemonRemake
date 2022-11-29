package com.pokemon.game;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public class Mother implements ICharacter {

	// globals
	private Sprite sprite;

	public Mother() {
		// sprite creation
		sprite = new Sprite(new Texture("FSEStuff/mother.png"));
		sprite.setPosition(186, 315);
	}

	/**
	 * @return
	 */
	public Rectangle getBoundingRectangle() {
		return sprite.getBoundingRectangle();
	}

	@Override
	public ArrayList<String> getText(Starters starter) {
		//moms text before and after getting a pokemon
		ArrayList<String> text = new ArrayList<String>();
		if (!starter.hasPokemon()) {
			text.add("Morning, Welcome to the World of POKeMON");
			text.add("You are finally old enough to get your");
			text.add("own Pokemon and battle gyms!");
			text.add("You should seak Professor Oak for in his lab.");
			text.add("Rememeber that you cannot leave town...");
			text.add("untill you have a Pokemon!");
		} else {
			text.add("Im going to miss you Klem!");
			text.add("try to visit once and a while...");
			text.add("I will give your POKeMON a rest!");
		}
		return text;
	}

	@Override
	public Batch draw(Batch batch, String location) {
		if (location.equals("home")) {
			sprite.draw(batch);
		}
		return null;
	}

}
