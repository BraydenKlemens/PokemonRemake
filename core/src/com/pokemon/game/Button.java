package com.pokemon.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Rectangle;

public abstract class Button {

	private Rectangle moveRect;
	private final BitmapFont text;
	private final String string;
	private final float x, y;

	/**
	 * @param text
	 * @param string
	 * @param x
	 * @param y
	 */
	public Button(BitmapFont text, String string, float x, float y) {
		this.text = text;
		this.string = string;
		this.x = x;
		this.y = y;
		if (text != null) {
			//globals
			GlyphLayout glyph = new GlyphLayout(text, string);
			moveRect = new Rectangle(x, y - glyph.height, glyph.width, glyph.height);
			text.setColor(0, 0, 0, 1);
		}
	}

	/**
	 * @return the moveRect
	 */
	public Rectangle getMoveRect() {
		return moveRect;
	}

	/**
	 * @param moveRect
	 *            the moveRect to set
	 */
	public void setMoveRect(Rectangle moveRect) {
		this.moveRect = moveRect;
	}
	
	//force function for clicking the glyph
	public abstract void onClicked();

	/**
	 * @param x
	 * @param y
	 */
	public void checkClick(float x, float y) {
		if (moveRect.contains(x, y)) {
			onClicked();
		}
	}

	/**
	 * @param batch
	 */
	public void draw(Batch batch) {
		text.draw(batch, string, x, y);
	}
}
