package com.pokemon.game;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class NurseJoy implements ICharacter{
	
	//globals
	private final Sprite sprite;
	
	public NurseJoy(){
		//sprite creation
		sprite = new Sprite(new Texture("FSEStuff/NurseJoy.png"));
		sprite.setPosition(200, 245);
	}

	@Override
	public ArrayList<String> getText(Starters stater) {
		ArrayList<String> text = new ArrayList<>();
		text.add("Welcome to the Pokemon Center!");
		text.add("Would you like me to heal you Pokemon");
		text.add("back to perfect health?");
		text.add("Okay I'll take your pokemon for a few seconds!");
		text.add("..............");
		text.add("We've restored your Pokemon back to perfect health!");
		text.add("We hope to see you again!");
		return text;
	}

	@Override
	public Batch draw(Batch batch, String location) {
		if(location.equals("PokeCenter")){
			sprite.draw(batch);
		}
		return null;
	}
	
	

}
