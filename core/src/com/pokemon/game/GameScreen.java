package com.pokemon.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import com.pokemon.game.Player;

public class GameScreen implements Screen, InputProcessor {

	// BUGS && updates
	/*
	 * play new songs when entering new towns/routes - update increase battle
	 * music length -update battle screen action indexes cause crash sometimes -
	 * bug more: Pokemon, moves, battle stats - update battle: commentator:
	 * critical, defense - update battle: sounds, levels, animations, bag and
	 * Pokemon option - update go in more buildings w/ different music - update
	 * increase map size and story line - update add wild trainers to defeat -
	 * update find Pokemon flute before moving into first town - update healing
	 * pokemon sounds - update the speech box blips on last sentence drawn
	 * deleted then re drawn - bug
	 */
	
	//Config.width = 750;
	

	// SetUp
	private GameFreak game;
	private SpriteBatch world;
	private OrthographicCamera camera;
	private Viewport gamePort;
	private MapLoader loadMap;
	private HashMap<String, MapLoader> load = new HashMap<String, MapLoader>();

	// player
	public Player player;
	private int speed = 1;
	private float speedMultiplier = 1;
	private boolean moveUp;
	private boolean moveDown;
	private boolean moveLeft;
	private boolean moveRight;
	public String stance = "";
	private String location = "home";
	private boolean returnHome = false;
	private boolean fight;
	private boolean mistyTalk = false;

	// Words
	private BitmapFont word;
	private String text = "";
	private String newString;
	private int index;
	private boolean lockPrint = true;
	private Queue<String> words = new LinkedList<String>();

	// sprite etc
	private Starters starter = new Starters();
	private Mother mother = new Mother();
	private Oak oak = new Oak();
	private NurseJoy nurse = new NurseJoy();
	private Sprite speach;
	private PokeBall Bulbasaur, Charmander, Squirtle;
	private Misty misty = new Misty();
	private ArrayList<PokeBall> pokeBalls = new ArrayList<PokeBall>();
	private Pokemon pokemon;

	// sounds/music

	private Music worldMusic, pokeCenter, lab;
	private Sound click, door;

	public GameScreen(GameFreak game, Player player) {

		// updates pos between screens
		this.game = game;
		player.setPosition(100, 610);
		this.player = player;

		world = new SpriteBatch();

		// Camera and map
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.zoom = (float) 0.4f;
		gamePort = new ScreenViewport(camera);

		// load maps
		load.put("Map/PokemonLG.tmx", new MapLoader("Map/PokemonLG.tmx"));
		load.put("Map/PokeCenter.tmx", new MapLoader("Map/PokeCenter.tmx"));
		load.put("Map/PokeLab.tmx", new MapLoader("Map/PokeLab.tmx"));
		load.put("Map/house.tmx", new MapLoader("Map/house.tmx"));
		loadMap = load.get("Map/house.tmx");

		// speech bubble
		speach = new Sprite(new Texture("FSEStuff/SpeachBubble.png"));
		speach.setSize(250, 30);

		// starters
		Bulbasaur = new PokeBall(Names.BULBASAUR);
		Bulbasaur.setPosition(228, 230);
		Charmander = new PokeBall(Names.SQUIRTLE);
		Charmander.setPosition(243, 230);
		Squirtle = new PokeBall(Names.CHARMANDER);
		Squirtle.setPosition(258, 230);
		pokeBalls.add(Bulbasaur);
		pokeBalls.add(Charmander);
		pokeBalls.add(Squirtle);

		// words
		word = new BitmapFont();
	}

	@Override
	public void show() {
		worldMusic = Gdx.audio.newMusic(Gdx.files.internal("Music/worldMusic.mp3"));
		lab = Gdx.audio.newMusic(Gdx.files.internal("Music/Lab.mp3"));
		pokeCenter = Gdx.audio.newMusic(Gdx.files.internal("Music/PokeCenter.mp3"));

		click = Gdx.audio.newSound(Gdx.files.internal("Music/click.wav"));
		door = Gdx.audio.newSound(Gdx.files.internal("Music/door.wav"));

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// audio
		playMusic();

		// update sprite positions with player
		starter.updatePos(player);
		speach.setPosition(player.getX() - 105, player.getY() - 60);

		// prof oaks lab stuff
		starter.viewStarters(pokeBalls, words, text, player, starter);
		starter.choosePokemon(player, pokeBalls, words, oak, starter);

		// method calls
		pollKeyboard();
		move();
		MapCollisions();

		// re spawn if loses battle
		if (starter.hasPokemon()) {
			respawn();
		}

		// Display
		word.setColor(Color.BLACK);
		word.getData().setScale(0.6f, 0.6f);

		// print text on screen - handles all text
		if (lockPrint == true && !words.isEmpty()) {
			printText(words.remove());
		}

		// lock player in position when speaking
		if (!words.isEmpty() || !text.isEmpty()) {
			speed = 0;
			stance = " ";
		} else {
			speed = 1;
		}
		draw();
	}

	public void playMusic() {
		// Muisc
		if (location.equals("world") || location.equals("home")) {
			worldMusic.play();
		} else {
			worldMusic.stop();
		}
		if (location.equals("lab")) {
			lab.play();
		} else {
			lab.stop();
		}
		if (location.equals("PokeCenter")) {
			pokeCenter.play();
		} else {
			pokeCenter.stop();
		}
	}

	public void draw() {
		// View
		// update camera movement
		loadMap.setCamera(camera);
		loadMap.movecamera(camera, player);
		loadMap.updateCamera(camera);
		world.setProjectionMatrix(camera.combined);

		// render world layers - only multiple layers on "world"
		if (location.equalsIgnoreCase("world")) {
			loadMap.drawBackgroundLayers();
		} else {
			loadMap.renderTiledMap();
		}

		// 1st batch for players etc
		world.begin();
		if (location.equals("lab")) {
			for (int i = 0; i < pokeBalls.size(); i++) {
				pokeBalls.get(i).draw(world);
			}
		}
		oak.draw(world, location);
		mother.draw(world, location);
		misty.draw(world, location);
		player.drawPlayer(world, stance);
		nurse.draw(world, location);
		world.end();

		// render world layers - infront of player
		if (location.equalsIgnoreCase("world")) {
			loadMap.drawForegroundLayers();
		}

		// 2nd batch for drawing over top of everything
		world.begin();
		if (!words.isEmpty() || !(text.isEmpty())) {
			speach.draw(world);
		}
		starter.draw(world, location);
		word.draw(world, text, player.getX() - 90, player.getY() - 40);

		world.end();
	}

	public void respawn() {
		 //loads home, re spawns player with fully healed pokemon
		if (player.getPokemon().get(0).getHealth() < 1) {
			loadMap = load.get("Map/house.tmx");
			location = "home";
			player.setPosition(100, 610);
			player.getPokemon().get(0).setHealth(0);
			words.add("You blacked out...");
			lockPrint = true;
		}
	}

	public void move() {
		// player movement
		if (moveUp) {
			player.translateY(speed * speedMultiplier);
			stance = "walkUp";
		} else if (moveDown) {
			player.translateY(-speed * speedMultiplier);
			stance = "walkDown";
		} else if (moveRight) {
			player.translateX(speed * speedMultiplier);
			stance = "walkRight";
		} else if (moveLeft) {
			player.translateX(-speed * speedMultiplier);
			stance = "walkLeft";
		}
	}

	private void pollKeyboard() {
		// player movement
		if (Gdx.input.isKeyPressed(Input.Keys.W)) {
			moveUp = true;
		} else {
			moveUp = false;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.S)) {
			moveDown = true;
		} else {
			moveDown = false;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.D)) {
			moveRight = true;
		} else {
			moveRight = false;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			moveLeft = true;
		} else {
			moveLeft = false;
		}
	}

	private void hitwall() {
		// stops the player when hitting walls
		if (moveUp) {
			player.translateY(-speed * speedMultiplier);
		} else if (moveDown) {
			player.translateY(speed * speedMultiplier);
		} else if (moveRight) {
			player.translateX(-speed * speedMultiplier);
		} else if (moveLeft) {
			player.translateX(speed * speedMultiplier);
		}
	}

	private void MapCollisions() {

		// all wall collisions
		MapObjects wall = loadMap.getTiledMap().getLayers().get("walls").getObjects();
		Rectangle playerRect = player.getBoundingRectangle();
		if (wall != null) {
			for (int i = 0; i < wall.getCount(); i++) {
				MapObject object = wall.get(i);
				if (object instanceof RectangleMapObject) {
					Rectangle wallRect = ((RectangleMapObject) object).getRectangle();
					if (Intersector.overlaps(playerRect, wallRect)) {
						if (object.getName().equalsIgnoreCase("wall")) {
							hitwall();
						}
					}
				}
			}
		}

		// event trigger rectangles
		MapObjects triggers = loadMap.getTiledMap().getLayers().get("triggers").getObjects();
		if (triggers != null) {
			for (int i = 0; i < triggers.getCount(); i++) {
				MapObject object = triggers.get(i);
				if (object instanceof RectangleMapObject) {
					Rectangle triggerRect = ((RectangleMapObject) object).getRectangle();
					if (Intersector.overlaps(playerRect, triggerRect)) {
						// loads correct map for player location, sets player
						if (object.getName().equalsIgnoreCase("pokeCenter")) {
							door.play();
							loadMap = load.get("Map/PokeCenter.tmx");
							player.setPosition(194, 157);
							location = "PokeCenter";
						} else if (object.getName().equalsIgnoreCase("lab")) {
							door.play();
							loadMap = load.get("Map/PokeLab.tmx");
							player.setPosition(193, 109);
							location = "lab";
						} else if (object.getName().equalsIgnoreCase("home")) {
							door.play();
							loadMap = load.get("Map/house.tmx");
							player.setPosition(123, 260);
							location = "home";
						} else if (object.getName().equalsIgnoreCase("leave")) {
							door.play();
							loadMap = load.get("Map/PokemonLG.tmx");
							if (location.equals("home")) {
								player.setPosition(305, 536);
							} else if (location.equals("lab")) {
								player.setPosition(865, 424);
							} else if (location.equals("PokeCenter")) {
								player.setPosition(113, 2811);
							}
							location = "world";
							// upstairs
						} else if (object.getName().equalsIgnoreCase("up") && location.equals("home")) {
							door.play();
							player.setPosition(230, 666);
							// downstairs
						} else if (object.getName().equalsIgnoreCase("down") && location.equals("home")) {
							door.play();
							player.setPosition(216, 346);
						} else if (object.getName().equalsIgnoreCase("Bushes") && location.equals("world")) {
							// 0.15% chance of running into wild pokemon, pick
							// random, trigger battle screen
							if (foundPokemon()) {
								pokemon = generatePokemon();
								lockPrint = true;
								words.add("You found a wild " + pokemon.getName());
								Timer.schedule(new Timer.Task() {

									@Override
									public void run() {
										game.setScreen(
												new BattleScreen(game, player.getPokemon().get(0), pokemon));
									}
								}, 2f);
							}
						} else if (object.getName().equalsIgnoreCase("leaveTown") && location.equals("world")) {
							returnHome = true;
							// cant leave town yet
						} else if (object.getName().equals("noPokemon")) {
							if (!starter.hasPokemon()) {
								hitwall();
							}
							// revive pokemon at pokeCenter
						} else if (object.getName().equalsIgnoreCase("Revive") && location.equals("PokeCenter")) {
							if (words.isEmpty() && Gdx.input.isKeyPressed(Input.Keys.SPACE) && text.isEmpty()) {
								click.play();
								words.addAll(nurse.getText(starter));
								pokeCenter();
							}
						}
					}
				}
			}
		}
		// triggers for speech
		if (triggers != null) {
			for (int i = 0; i < triggers.getCount(); i++) {
				MapObject object = triggers.get(i);
				if (object instanceof RectangleMapObject) {
					Rectangle triggerRect = ((RectangleMapObject) object).getRectangle();
					if (Intersector.overlaps(playerRect, triggerRect)) {
						if (object.getName().equalsIgnoreCase("speach")) {
							// when pressing space trigger text for mom and oak
							if (words.isEmpty() && Gdx.input.isKeyPressed(Input.Keys.SPACE) && location.equals("home")
									&& text.isEmpty()) {
								click.play();
								if (returnHome == false) {
									if (!starter.hasPokemon()) {
										words.addAll(mother.getText(starter));
									} else {
										words.addAll(mother.getText(starter));
									}
								} else {
									revivePokemon();
								}
								// oak says new things depending on your journey
								// depth
							} else if (words.isEmpty() && Gdx.input.isKeyPressed(Input.Keys.SPACE)
									&& location.equals("lab") && text.isEmpty()) {
								click.play();
								if (returnHome == false) {
									words.addAll(oak.getText(starter));
								} else {
									Oak();
								}
							} else if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && location.equals("world")) {
								if (!fight) {
									words.add("Lets Battle!");
									Timer.schedule(new Timer.Task() {

										@Override
										public void run() {
											fight = true;  
											game.setScreen(new BattleScreen(game, player.getPokemon().get(0),
													misty.getPokemon()));
										}
									}, 2f);
								}
								if (!mistyTalk) {
									Timer.schedule(new Timer.Task() {

										@Override
										public void run() {
											if (player.getPokemon().get(0).getHealth() > 0) {
												words.add("congratulations! you beat me!");
												words.add("Here take this");
												words.add("...");
												words.add("Klem obtained the Rain Badge!");
												words.add("You are now a Pokemon Master!");
												mistyTalk = true;
											}
										}
									}, 3f);
								}
							}
						}
					}
				}
			}
		}

		// players collision with other players
		Rectangle motherRect = mother.getBoundingRectangle().setSize(16, 16);
		Rectangle oakRect = oak.getBoundingRectangle().setSize(16, 16);
		Rectangle mistyRect = misty.getBoundingRectangle(16, 16);
		if (Intersector.overlaps(motherRect, playerRect)) {
			hitwall();
		} else if (Intersector.overlaps(oakRect, playerRect)) {
			hitwall();
		} else if (Intersector.overlaps(mistyRect, playerRect)) {
			hitwall();
		}
	}

	/**
	 * @return
	 */
	public Pokemon generatePokemon() {
		// picks random number return given pokemon - wild pokemon
		int rand = (int) Math.floor(Math.random() * 3);
		switch (rand) {
		case 0:
			Pokemon pokemon = Pokemon.bulbasaur;
			pokemon.setMoves(new Move[] { Move.VineWhip, Move.Growl, Move.nullMove, Move.nullMove });
			return pokemon;
		case 1:
			Pokemon pokemon1 = Pokemon.charmander;
			pokemon1.setMoves(new Move[] { Move.Ember, Move.Growl, Move.nullMove, Move.nullMove });
			return pokemon1;

		case 2:
			Pokemon pokemon2 = Pokemon.squirtle;
			pokemon2.setMoves(new Move[] { Move.Bubble, Move.Growl, Move.nullMove, Move.nullMove });
			return pokemon2;
		default:
			return null;
		}
	}

	/**
	 * @param string
	 */
	public void printText(String string) {
		// re fills text with new string one letter at a time
		text = "";
		newString = string;
		index = 0;
		lockPrint = false;

		Timer.schedule(new Timer.Task() {

			@Override
			public void run() {
				if (index < newString.length()) {
					text += newString.charAt(index);
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

	/**
	 * @return
	 */
	public boolean foundPokemon() {
		// find random pokemon 3/1800 chance
		int random = (int) (Math.random() * 500);
		if (random == 13) {
			random = 0;
			return true;
		}
		return false;
	}

	public void revivePokemon() {
		// mom will revive pokemon once left town and returned
		if (starter.hasPokemon()) {
			if (returnHome) {
				player.getPokemon().get(0).setHealth(player.getPokemon().get(0).getMaxHealth());
				player.getPokemon().get(0).setAttackMultiplier(1);
				words.add("Welcome home Klem, you and your pokemon look tired..");
				words.add("Ill rest your pokemon for you!");
				words.add("...........");
				words.add("Your pokemon are fully revived and ready to battle");
				words.add("see you soon!");
			}
		}
	}

	public void pokeCenter() {
		// revives pokemon
		player.getPokemon().get(0).setHealth(player.getPokemon().get(0).getMaxHealth());
		player.getPokemon().get(0).setAttackMultiplier(1);
	}

	public void Oak() {
		// once left town and returned :)
		words.add("Hey there Klem!");
		words.add("Looks like you and your pokemon have gotten much stronger!");
		words.add("GoodLuck defeating the Gym in the big City!");
		words.add("Misty is a tough gym leader!");
	}

	@Override
	public void resize(int width, int height) {
		gamePort.update(width, height);

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
		worldMusic.stop();
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
