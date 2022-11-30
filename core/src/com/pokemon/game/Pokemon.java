package com.pokemon.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Timer;

public class Pokemon {
	
	//global variabes
	private Sprite sprite;
	public Texture back, front;
	private int health;
	private final float defence;
	private float attack;
	private final int maxHealth;
	private float attackMultiplier = 1;
	private final int level;
	private final Type type;
	private final String name;
	private Move[] moves = new Move[4];
	private final int w, h;

	// create static pokemon allows access to them - wild pokemon stats
	public static Pokemon bulbasaur = new Pokemon(new Texture("FSEStuff/BulbasaurFront.png"),
			new Texture("FSEStuff/BulbasaurBack.png"), 100, 90, Type.GRASS, "BULBASAUR", 24, 14, 14, 5);
	public static Pokemon charmander = new Pokemon(new Texture("FSEStuff/CharmanderFront.png"),
			new Texture("FSEStuff/CharmanderBack.png"), 100, 100, Type.FIRE, "CHARMANDER", 23, 13, 14, 5);
	public static Pokemon squirtle = new Pokemon(new Texture("FSEStuff/SquirtleFront.png"),
			new Texture("FSEStuff/SquirtleBack.png"), 110, 115, Type.WATER, "SQUIRTLE", 24, 16, 14, 5);

	/**
	 * @param front
	 * @param back
	 * @param w
	 * @param h
	 * @param type
	 * @param name
	 * @param health
	 * @param defence
	 * @param attack
	 * @param level
	 */
	public Pokemon(Texture front, Texture back, int w, int h, Type type, String name, int health, int defence,
			int attack, int level) {
		this.back = back;
		this.front = front;
		this.type = type;
		this.name = name;
		this.health = health;
		this.maxHealth = health;
		this.defence = defence;
		this.attack = attack;
		this.level = level;
		this.w = w;
		this.h = h;
		sprite = new Sprite(back);
	}

	/**
	 * @param texture
	 */
	public void setTexture(int texture) {
		//need for player vs attacker
		if (texture == 0)
			sprite = new Sprite(back);
		if (texture == 1)
			sprite = new Sprite(front);
	}

	/**
	 * 
	 */
	public void setSize() {
		sprite.setSize(w, h);
	}

	/**
	 * @param index
	 * @return
	 */
	public Move getMove(int index) {
		return moves[index];
	}

	/**
	 * @param amount
	 */
	public void damage(float amount) {
		//health decreases on a timer
		for (int i = 0; i < amount; i++) {
			Timer.schedule(new Timer.Task() {

				@Override
				public void run() {
					health--;
				}
			}, i * 0.08f + 0.08f);
		}
	}

	/**
	 * @param amount
	 */
	public void lowerStat(float amount) {
		attackMultiplier -= 0.1f;
	}

	/**
	 * @param batch
	 */
	public void draw(Batch batch) {
		sprite.draw(batch);
	}

	/**
	 * @param x
	 * @param y
	 */
	public void setPosition(float x, float y) {
		sprite.setPosition(x, y);
	}

	/**
	 * @param moves
	 */
	public void setMoves(Move[] moves) {
		this.moves = moves;
	}

	/**
	 * @return
	 */
	//return 2 unless 4
	public int getNumOfMoves() {
		for (int i = 0; i < moves.length; i++) {
			if (moves[i] == Move.nullMove)
				return i;
		}
		return 4;
	}

	/**
	 * @param health
	 *            the health to set
	 */
	public void setHealth(int health) {
		this.health = health;
	}

	/**
	 * @return the defence
	 */
	public float getDefence() {
		return defence;
	}

	/**
	 * @return the attack
	 */
	public float getAttack() {
		return attack * attackMultiplier;
	}

	/**
	 * @param attack
	 *            the attack to set
	 */
	public void setAttack(int attack) {
		this.attack = attack;
	}

	/**
	 * @return
	 */
	public float getAttackMultiplier() {
		return attackMultiplier;
	}

	/**
	 * @return the health
	 */
	public int getHealth() {
		return health;
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
	public Type getType() {
		return type;
	}

	/**
	 * @return
	 */
	public int getMaxHealth() {
		return maxHealth;
	}

	/**
	 * @return
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * @return
	 */
	public Move[] getMoves() {
		return moves;
	}

	/**
	 * @param attackMultiplier
	 */
	public void setAttackMultiplier(float attackMultiplier) {
		if (this.attackMultiplier >= 0.5f) {
			this.attackMultiplier = attackMultiplier;
		}
	}

	public void resetAttackMultiplier() {
		this.attackMultiplier = 1;
	}
}