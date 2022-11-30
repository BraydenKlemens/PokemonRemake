package com.pokemon.game;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public class Misty implements ICharacter {

	// globals
	private final Sprite sprite;
	private final Pokemon pokemon;

	public Misty() {
		sprite = new Sprite(new Texture("FSEStuff/Misty.png"));
		sprite.setPosition(1277, 2932);
		pokemon = new Pokemon(new Texture("Battle/star.png"), new Texture("Battle/star.png"), 100, 100, Type.WATER,
				"STARMIE", 40, 15, 15, 10, "trainer");
		pokemon.setMoves(new Move[] { Move.WaterPulse, Move.Screech, Move.nullMove, Move.nullMove });
	}

	@Override
	public ArrayList<String> getText(Starters stater) {
		ArrayList<String> text = new ArrayList<>();
		text.add("lets Battle!");
		return text;
	}

	@Override
	public Batch draw(Batch batch, String location) {
		if (location.equalsIgnoreCase("World")) {
			sprite.draw(batch);
		}
		return null;
	}

	/**
	 * @return the pokemon
	 */
	public Pokemon getPokemon() {
		return pokemon;
	}

	/**
	 * @param i
	 * @param j
	 * @return
	 */
	public Rectangle getBoundingRectangle(int i, int j) {
		return sprite.getBoundingRectangle().setSize(i, j);
	}
}
