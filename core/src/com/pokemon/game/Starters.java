package com.pokemon.game;

import java.util.ArrayList;
import java.util.Queue;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public class Starters implements ICharacter {
	
	//global variables
	private final Sprite sprite;
	private final Texture img1, img2, img3;
	private boolean draw;
	private boolean hasStarter = false;
	private String name = "";
	private final Sound sound;
	private final Sound click;

	public Starters() {
		//sprite creation
		sprite = new Sprite(new Texture("FSEStuff/bulb.png"));
		img1 = new Texture("FSEStuff/bulb.png");
		img2 = new Texture("FSEStuff/char.png");
		img3 = new Texture("FSEStuff/squirt.png");
		sound = Gdx.audio.newSound(Gdx.files.internal("Music/item.mp3"));
		click = Gdx.audio.newSound(Gdx.files.internal("Music/click.wav"));
	}

	/**
	 * @param pokeBalls
	 * @param words
	 * @param text
	 * @param player
	 * @param starter
	 */
	public void viewStarters(ArrayList<PokeBall> pokeBalls, Queue<String> words, String text, Player player,
			Starters starter) {
		Rectangle playerRect = player.getBoundingRectangle();
		//if player collides with ball and clicks space, displays image over top for each ball
		for (int i = 0; i < pokeBalls.size() - 2; i++) {
			if (playerRect.overlaps(pokeBalls.get(i).getBoundingRectangle().setSize(1, 1))) {
				if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && words.isEmpty() && text.isEmpty()) {
					click.play();
					sprite.setTexture(img1);
					words.add(starter.getText(starter).get(0));
					draw = true;
				} else if (Gdx.input.isKeyJustPressed(Input.Keys.B) && words.isEmpty()) {
					click.play();
					draw = false;
				}
			} else if (playerRect.overlaps(pokeBalls.get(i + 1).getBoundingRectangle().setSize(1, 1))) {
				if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && words.isEmpty() && text.isEmpty()) {
					click.play();
					sprite.setTexture(img3);
					words.add(starter.getText(starter).get(1));
					draw = true;
				} else if (Gdx.input.isKeyJustPressed(Input.Keys.B)) {
					click.play();
					draw = false;
				}
			} else if (playerRect.overlaps(pokeBalls.get(i + 2).getBoundingRectangle().setSize(1, 1))) {
				if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && words.isEmpty() && text.isEmpty()) {
					click.play();
					sprite.setTexture(img2);
					words.add(starter.getText(starter).get(2));
					draw = true;
				} else if (Gdx.input.isKeyJustPressed(Input.Keys.B)) {
					click.play();
					draw = false;
				}
			}
		}
	}

	/**
	 * @param player
	 * @param pokeBalls
	 * @param words
	 * @param oak
	 * @param starter
	 */
	public void choosePokemon(Player player, ArrayList<PokeBall> pokeBalls, Queue<String> words, Oak oak,
			Starters starter) {
		Rectangle playerRect = player.getBoundingRectangle();
		if (!hasStarter && !draw) {
			for (int i = 0; i < pokeBalls.size() - 2; i++) {
				if (playerRect.overlaps(pokeBalls.get(i).getBoundingRectangle().setSize(1, 1))) {
					if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
						/*upon clicking enter, player collects ball corresponding pokemon attributes
						 * sets texture to owner pokemon
						 * removes ball from screen
						 */
						sound.play();
						Pokemon pokemon = new Pokemon(Pokemon.bulbasaur.front, Pokemon.bulbasaur.back, 100, 90,
								Type.GRASS, "BULBASAUR", 25, 18, 20, 5);
						pokemon.setMoves(new Move[] { Move.VineWhip, Move.Growl, Move.Tackle, Move.nullMove });
						pokemon.setTexture(0);
						player.getPokemon().add(pokemon);
						name = pokeBalls.get(i).getName().toString();
						pokeBalls.remove(i);
						hasStarter = true;
						words.addAll(oak.getText(starter));
					}
				} else if (playerRect.overlaps(pokeBalls.get(i + 1).getBoundingRectangle().setSize(1, 1))) {
					if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
						sound.play();
						Pokemon pokemon = new Pokemon(Pokemon.squirtle.front, Pokemon.squirtle.back, 110, 115,
								Type.WATER, "SQUIRTLE", 25, 20, 18, 5);
						pokemon.setMoves(new Move[] { Move.Bubble, Move.Growl, Move.Tackle, Move.nullMove });
						pokemon.setTexture(0);
						player.getPokemon().add(pokemon);
						name = pokeBalls.get(i + 1).getName().toString();
						pokeBalls.remove(i + 1);
						hasStarter = true;
						words.addAll(oak.getText(starter));
					}
				} else if (playerRect.overlaps(pokeBalls.get(i + 2).getBoundingRectangle().setSize(1, 1))) {
					if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
						sound.play();
						Pokemon pokemon = new Pokemon(Pokemon.charmander.front, Pokemon.charmander.back, 100, 100,
								Type.FIRE, "CHARMANDER", 25, 18, 21, 5);
						pokemon.setMoves(new Move[] { Move.Ember, Move.Growl, Move.Tackle, Move.nullMove});
						pokemon.setTexture(0);
						player.getPokemon().add(pokemon);
						name = pokeBalls.get(i + 2).getName().toString();
						pokeBalls.remove(i + 2);
						hasStarter = true;
						words.addAll(oak.getText(starter));
					}
				}
			}
		}
	}

	/**
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return
	 */
	public boolean hasPokemon() {
		return hasStarter;
	}

	/**
	 * @param player
	 */
	public void updatePos(Player player) {
		sprite.setPosition(player.getX() - 50, 200);
	}

	@Override
	public ArrayList<String> getText(Starters starter) {
		ArrayList<String> text = new ArrayList<>();
		text.add("I see! BULBASAUR the grass-type is your choice?");
		text.add("Hmm! SQUIRTLE the water-type is your choice?");
		text.add("Ah! CHARMANDER the fire-type is your choice?");
		return text;
	}

	@Override
	public Batch draw(Batch batch, String location) {
		if (location.equals("lab") && draw) {
			sprite.draw(batch);
		}
		return null;
	}

}
