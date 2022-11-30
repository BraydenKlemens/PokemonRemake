package com.pokemon.game;

public class Move {

	// globals
	private final Type type;
	private Category category;
	private float power;
	private String name;

	// create static moves allow access
	public static Move nullMove = new Move("", Type.NORMAL, Category.PASSIVE, 0);
	public static Move VineWhip = new Move("VINEWHIP", Type.GRASS, Category.PHYSICAL, 40f);
	public static Move Ember = new Move("EMBER", Type.FIRE, Category.PHYSICAL, 40f);
	public static Move Bubble = new Move("BUBBLE", Type.WATER, Category.PHYSICAL, 40f);
	public static Move Growl = new Move("GROWL", Type.NORMAL, Category.PASSIVE, 0f);
	public static Move WaterPulse = new Move("WATER PULSE", Type.WATER, Category.PHYSICAL, 60f);
	public static Move Screech = new Move("SCREECH", Type.NORMAL, Category.PASSIVE, 0f);
	public static Move Tackle = new Move("TACKLE", Type.NORMAL, Category.PHYSICAL, 40f);


	/**
	 * @param name
	 * @param type
	 * @param category
	 * @param power
	 */
	public Move(String name, Type type, Category category, float power) {
		this.category = category;
		this.type = type;
		this.power = power;
		this.name = name;
	}

	/**
	 * @return the category
	 */
	public Category getCategory() {
		return category;
	}

	/**
	 * @param category
	 *            the category to set
	 */
	public void setCategory(Category category) {
		this.category = category;
	}

	/**
	 * @return the power
	 */
	public float getPower() {
		return power;
	}

	/**
	 * @param power
	 *            the power to set
	 */
	public void setPower(float power) {
		this.power = power;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return
	 */
	public Type getType() {
		return type;
	}

	/**
	 * @author KLEM
	 *
	 */
	public enum Category {
		PHYSICAL, PASSIVE
	}
}
