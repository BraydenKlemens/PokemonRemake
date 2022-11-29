package com.pokemon.game;

public enum Type {
	FIRE, WATER, GRASS, NORMAL;

	public static float checkEffectivness(Type attackType, Type defenseType) {
		// Type.FIRE.Effectivness(Type.GRASS);

		// returns an effectivness based on what type its up against
		switch (attackType) {
		case FIRE:
			switch (defenseType) {
			case GRASS:
				return 1.5f;
			case FIRE:
				return 0.5f;
			case WATER:
				return 0.5f;
			case NORMAL:
				return 1;
			default:
				return 1;
			}
		case GRASS:
			switch (defenseType) {
			case GRASS:
				return 0.5f;
			case FIRE:
				return 0.5f;
			case WATER:
				return 1.5f;
			case NORMAL:
				return 1;
			default:
				return 1;
			}
		case WATER:
			switch (defenseType) {
			case GRASS:
				return 0.5f;
			case FIRE:
				return 1.5f;
			case WATER:
				return 0.5f;
			case NORMAL:
				return 1;
			default:
				return 1;
			}
		case NORMAL:
			switch (defenseType) {
			case GRASS:
				return 1f;
			case FIRE:
				return 1f;
			case WATER:
				return 1f;
			case NORMAL:
				return 1;
			default:
				return 1;
			}
		}
		return 0;
	}
}
