package com.pokemon.game;

public enum Names {
	BULBASAUR(Type.GRASS), SQUIRTLE(Type.WATER), CHARMANDER(Type.FIRE);

	private final Type type;

	/**
	 * @param type
	 */
	Names(Type type) {
		this.type = type;
	}

	/**
	 * @return the type
	 */
	public Type getType() {
		return type;
	}
}
