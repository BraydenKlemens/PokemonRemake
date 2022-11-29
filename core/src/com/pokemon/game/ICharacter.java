package com.pokemon.game;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Batch;

public interface ICharacter {
	
	/*
	 * interface for all game characters
	 * force draw
	 * force speech
	 */
	
	public ArrayList<String> getText(Starters stater);
	
	public Batch draw(Batch batch, String location);

}
