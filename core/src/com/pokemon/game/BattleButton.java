package com.pokemon.game;

import com.badlogic.gdx.math.Rectangle;

public abstract class BattleButton extends Button {

	public BattleButton(float x, float y, float width, float height) {
		super(null, "", x, y);
		setMoveRect(new Rectangle(x, y, width, height));
	}

	public abstract void onClicked();
}
