package com.pokemon.game;

import java.util.LinkedList;
import java.util.Queue;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Timer;

public class StartScreen implements Screen, InputProcessor {

	// global variables
	private final GameFreak game;
	private Music music, music2;
	private Sprite sprite;
	private Sprite oak;
	private BitmapFont comm;
	private boolean draw = false;
	private Sprite textBubble;
	private String string;
	private int index;
	private boolean lockPrint = false;
	private String text = "";
	private Sound click;
	private boolean lock = false;
	private final Queue<String> words = new LinkedList<>();

	public StartScreen(GameFreak game) {
		this.game = game;
	}

	@Override
	public void show() {

		// audio
		music = Gdx.audio.newMusic(Gdx.files.internal("Music/MainTheme.mp3"));
		music2 = Gdx.audio.newMusic(Gdx.files.internal("Music/welcome.mp3"));
		click = Gdx.audio.newSound(Gdx.files.internal("Music/click.wav"));

		// sprite
		sprite = new Sprite(new Texture("FSEStuff/StartScreen.jpg"));
		sprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		oak = new Sprite(new Texture("FSEStuff/intro.png"));
		oak.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		textBubble = new Sprite(new Texture("FSEStuff/SpeachBubble.png"));
		textBubble.setPosition(10, 10);
		textBubble.setSize(700, 80);

		// bit map
		comm = new BitmapFont();
		comm.getData().scale(0.8f);
		comm.setColor(0, 0, 0, 1);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// switch music
		if (!draw) {
			music.play();
		} else {
			music2.play();
		}

		// print text on screen - handles all text
		if (lockPrint && !words.isEmpty()) {
			printText(words.remove());
		}

		// handles what is drawn
		if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
			if (!lock) {
				music.stop();
				addText();
				draw = true;
				lock = true;
			}
		} else if (Gdx.input.isKeyPressed(Input.Keys.B) && lock && words.isEmpty()) {
			game.setScreen(game.gameScreen);
			music2.stop();
		}

		// draw
		game.batch.begin();
		if (!draw) {
			sprite.draw(game.batch);
			comm.draw(game.batch, "Click SPACE", 200, 190);
		} else {
			oak.draw(game.batch);
			textBubble.draw(game.batch);
			comm.draw(game.batch, text, 40, 60);
		}
		game.batch.end();

	}

	public void printText(String strings) {
		// re fills text with new string one letter at a time
		text = "";
		string = strings;
		index = 0;
		lockPrint = false;

		Timer.schedule(new Timer.Task() {

			@Override
			public void run() {
				if (index < string.length()) {
					text += string.charAt(index);
					index += 1;
				} else if (Gdx.input.isKeyPressed(Input.Keys.B)) {
					// resets the print text method
					click.play();
					cancel();
					lockPrint = true;
					text = "";
				}
			}

		}, 0.03f, 0.03f);
	}

	public void addText() {
		lockPrint = true;
		words.add("Hello there, glad to meet you");
		words.add("Welcome to the world of POKeMON");
		words.add("My name is OAK");
		words.add("People refer to me as the Pokemon Professor");
		words.add("This world....");
		words.add("is inhabited far and wide by creatures called Pokemon");
		words.add("Some people have them as pets");
		words.add("Others use them to battle..");
		words.add("As for myself.. I study them as a profession!");
		words.add("Remember press SPACE to talk to someone..");
		words.add("Press B to keep talking...");
		words.add("Press ENTER to claim items");
		words.add(" ");
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(float amountX, float amountY) {
		return false;
	}

}
