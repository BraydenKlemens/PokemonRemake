package com.pokemon.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Player {

	// static
	public Sprite sprite;
	private TextureRegion currentFrame;
	private float stateTime;
	private String stance = "";
	// Row -
	// Column |

	// Up
	private TextureRegion[] walkUpFrames;
	private Animation walkUpAnimation;
	private Texture textureUp;

	// Down
	private TextureRegion[] walkDownFrames;
	private Animation walkDownAnimation;
	private Texture textureDown;

	// Right
	private TextureRegion[] walkRightFrames;
	private Animation walkRightAnimation;
	private Texture textureRight;

	// Left
	private TextureRegion[] walkLeftFrames;
	private Animation walkLeftAnimation;
	private Texture textureLeft;
	
	float frames = 0.15f;
	
	//players pokemon
	private ArrayList<Pokemon> pokemon = new ArrayList<Pokemon>();

	/**
	 * @return the pokemon
	 */
	public ArrayList<Pokemon> getPokemon() {
		return pokemon;
	}

	public Player() {

		sprite = new Sprite();
		// walk Up Frame Split
		textureUp = new Texture("Player/red4.png");
		TextureRegion[][] up = TextureRegion.split(textureUp, textureUp.getWidth() / 4, textureUp.getHeight() / 1);
		walkUpFrames = new TextureRegion[4];
		int index = 0;
		for (int i = 0; i < 1; i++) {
			for (int j = 0; j < 4; j++) {
				walkUpFrames[index++] = up[i][j];
			}
		}

		// Walk Down Frame Split
		textureDown = new Texture("Player/red1.png");
		TextureRegion[][] down = TextureRegion.split(textureDown, textureDown.getWidth() / 4,
				textureDown.getHeight() / 1);
		walkDownFrames = new TextureRegion[4];
		int index2 = 0;
		for (int i = 0; i < 1; i++) {
			for (int j = 0; j < 4; j++) {
				walkDownFrames[index2++] = down[i][j];
			}
		}

		// Walk Right Frame Split
		textureRight = new Texture("Player/red3.png");
		TextureRegion[][] right = TextureRegion.split(textureRight, textureRight.getWidth() / 4,
				textureRight.getHeight() / 1);
		walkRightFrames = new TextureRegion[4];
		int index3 = 0;
		for (int i = 0; i < 1; i++) {
			for (int j = 0; j < 4; j++) {
				walkRightFrames[index3++] = right[i][j];
			}
		}

		// Walk Left Frame Split
		textureLeft = new Texture("Player/red2.png");
		TextureRegion[][] left = TextureRegion.split(textureLeft, textureLeft.getWidth() / 4,
				textureLeft.getHeight() / 1);
		walkLeftFrames = new TextureRegion[4];
		int index4 = 0;
		for (int i = 0; i < 1; i++) {
			for (int j = 0; j < 4; j++) {
				walkLeftFrames[index4++] = left[i][j];
			}
		}
		
		//set frames
		walkUpAnimation = new Animation(frames, walkUpFrames);
		walkDownAnimation = new Animation(frames, walkDownFrames);
		walkRightAnimation = new Animation(frames, walkRightFrames);
		walkLeftAnimation = new Animation(frames, walkLeftFrames);
		
		//get the frame
		stateTime = 0f;
		currentFrame = (TextureRegion) walkUpAnimation.getKeyFrame(stateTime);
		sprite = new Sprite(currentFrame);

	}

	/**
	 * @param batch
	 * @param stance
	 */
	public void drawPlayer(SpriteBatch batch, String stance) {
		this.stance = stance;
		updatePlayer();
		sprite.draw(batch);
	}

	public void updatePlayer() {
		//set the frame based on the key pressed and state time
		if (stance == "walkUp" && Gdx.input.isKeyPressed(Input.Keys.W)) {
			stateTime += Gdx.graphics.getDeltaTime();
			if (stateTime > walkUpAnimation.getAnimationDuration()) {
				stateTime -= walkUpAnimation.getAnimationDuration();
			}
			currentFrame = (TextureRegion) walkUpAnimation.getKeyFrame(stateTime);

		} else if (stance == "walkDown" && Gdx.input.isKeyPressed(Input.Keys.S)) {
			stateTime += Gdx.graphics.getDeltaTime();
			if (stateTime > walkDownAnimation.getAnimationDuration()) {
				stateTime -= walkDownAnimation.getAnimationDuration();
			}
			currentFrame = (TextureRegion) walkDownAnimation.getKeyFrame(stateTime);

		} else if (stance == "walkRight" && Gdx.input.isKeyPressed(Input.Keys.D)) {
			stateTime += Gdx.graphics.getDeltaTime();
			if (stateTime > walkRightAnimation.getAnimationDuration()) {
				stateTime -= walkRightAnimation.getAnimationDuration();
			}
			currentFrame = (TextureRegion) walkRightAnimation.getKeyFrame(stateTime);

		} else if (stance == "walkLeft" && Gdx.input.isKeyPressed(Input.Keys.A)) {
			stateTime += Gdx.graphics.getDeltaTime();
			if (stateTime > walkLeftAnimation.getAnimationDuration()) {
				stateTime -= walkLeftAnimation.getAnimationDuration();
			}
			currentFrame = (TextureRegion) walkLeftAnimation.getKeyFrame(stateTime);
		}
		sprite.setRegion(currentFrame);
	}

	/**
	 * @param f
	 */
	public void translateX(float f) {
		sprite.translateX(f);
	}

	/**
	 * @param f
	 */
	public void translateY(float f) {
		sprite.translateY(f);
	}

	/**
	 * @return
	 */
	public int getX() {
		return (int) sprite.getX();
	}

	/**
	 * @return
	 */
	public int getY() {
		return (int) sprite.getY();
	}

	/**
	 * @return
	 */
	public Rectangle getBoundingRectangle() {
		return sprite.getBoundingRectangle().setSize(14, 19);
	}

	/**
	 * @param i
	 * @param j
	 */
	public void setPosition(int i, int j) {
		sprite.setPosition(i, j);

	}
}
