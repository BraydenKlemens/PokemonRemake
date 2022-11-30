package com.pokemon.game;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Timer;

import com.pokemon.actions.Action;
import com.pokemon.actions.ActionHandler;
import com.pokemon.actions.DelayAction;
import com.pokemon.actions.RunnableAction;

public class BattleScreen implements Screen, InputProcessor {
 
	// Objects
	private Sprite backGround, textBubble, ownerHealth, defenderHealth, fightInterface, movesInterface, bar1, bar2;
	private String commentator = "";
	private String newString = "";
	private BitmapFont text;
	private BitmapFont comm;
	private Button button1, button2, button3, button4;
	private final Pokemon poke1, poke2;
	private ExpBar expBar;

	// Lists
	private final Queue<String> words = new LinkedList<>();
	private final ArrayList<BattleButton> fight = new ArrayList<>();
	private final ArrayList<HealthBar> healthBars = new ArrayList<>();

	// booleans ints
	private boolean moveScreenOpen = false;
	private boolean useAttack = false;
	private boolean play = false;
	private boolean lockPrint = true;
	private boolean trainerBattle = false;
	private int index = 0;
	private final int healthBarWidth = 145;

	private final int expBarWidth = 195;

	// audio
	private Music battle, wild;
	private Sound click, run;

	// events
	private final ActionHandler actionHandler = new ActionHandler();
	private boolean doEvents = false;

	// game
	private final GameFreak game;

	public BattleScreen(GameFreak game, Pokemon poke1, Pokemon poke2) {
		this.game = game;
		this.poke1 = poke1;
		this.poke2 = poke2;
		setupBattle();
	}

	public void setupBattle() {

		// sets correct pokemon textures
		poke1.setTexture(0);
		poke1.setPosition(160, 130);
		poke1.setSize();
		poke2.setTexture(1);
		poke2.setPosition(500, 255);
		poke2.setSize();

		// sprite background creation
		backGround = new Sprite(new Texture("FSEStuff/backgrounds.png"));
		backGround.setSize(750, 350);
		backGround.setPosition(0, 130);

		textBubble = new Sprite(new Texture("Battle/Battlewords.png"));
		textBubble.setSize(750, 130);

		ownerHealth = new Sprite(new Texture("Battle/levels.png"));
		ownerHealth.setPosition(410, 130);
		ownerHealth.setSize(320, 120);

		defenderHealth = new Sprite(new Texture("Battle/attacker.png"));
		defenderHealth.setPosition(40, 340);
		defenderHealth.setSize(300, 100);

		fightInterface = new Sprite(new Texture("Battle/options.png"));
		fightInterface.setSize(330, 130);
		fightInterface.setPosition(420, 0);

		movesInterface = new Sprite(new Texture("Battle/Moves.png"));
		movesInterface.setSize(750, 130);

		// health bars
		bar1 = new Sprite(new Texture("Battle/healthBarBackground.png"));
		bar1.setPosition(560, 158);
		bar1.setSize(145, 50);
		bar2 = new Sprite(new Texture("Battle/healthBarBackground.png"));
		bar2.setPosition(160, 344);
		bar2.setSize(145, 50);

		// text
		text = new BitmapFont();
		text.getData().scale(0.5f);
		comm = new BitmapFont();
		comm.getData().scale(1f);
		words.add("What will " + poke1.getName() + " do?");

		// sets up moves
		loadMoves();

		// input processor
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void show() {

		// buttons and health bars
		runBattleButtons();
		setHealthBars();
		setExpBar();

		// audio
		click = Gdx.audio.newSound(Gdx.files.internal("Music/click.wav"));
		battle = Gdx.audio.newMusic(Gdx.files.internal("Music/Battle.mp3"));
		wild = Gdx.audio.newMusic(Gdx.files.internal("Music/wild.mp3"));
		run = Gdx.audio.newSound(Gdx.files.internal("Music/run.wav"));

		// choose random battle song
		int rand = (int) Math.floor(Math.random() * 3);
		play = rand == 1;
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// play music
		if (play) {
			battle.play();
		} else {
			wild.play();
		}

		// Checks for who wins battle
		BattleResult();

		// every time lock print is true this prints the text
		if (lockPrint && !words.isEmpty()) {
			printText(words.remove());
		}

		// health bars
		updateOwnerHealthBars(poke1);
		updateDefenderHealthBars(poke2);
		updateExpBar(poke1);

		// if true events it runs the events
		draw();
		doTurn();
	}

	public void BattleResult() {
		// win/lose battles set screen game screen, say who wins / lost with
		// delays
		if (poke1.getHealth() < 1) {
			poke1.setHealth(0);
			actionHandler.addAction(new RunnableAction(new Runnable() {
				@Override
				public void run() {
					useAttack = true;
					lockPrint = true;
					words.add(poke1.getName() + " fainted... ");
					assert words.peek() != null;
					actionHandler.addAction(new DelayAction(words.peek().length() * 0.035f + 3f),
							actionHandler.getIndex() + 1);
					actionHandler.addAction(new RunnableAction(new Runnable() {
						@Override
						public void run() {
							game.setScreen(game.gameScreen);
						}
					}), actionHandler.getIndex() + 2);
				}
			}));
			doEvents = true;
		} else if (poke2.getHealth() < 1) {
			poke2.setHealth(0);
			actionHandler.addAction(new RunnableAction(new Runnable() {
				@Override
				public void run() {
					useAttack = true;
					lockPrint = true;
					words.add(poke2.getName() + " fainted... " + poke1.getName() + " wins!");
					assert words.peek() != null;
					actionHandler.addAction(new DelayAction(words.peek().length() * 0.035f + 3f),
							actionHandler.getIndex() + 1);
					actionHandler.addAction(new RunnableAction(new Runnable() {
						@Override
						public void run() {
							poke1.incLevel();
							game.setScreen(game.gameScreen);
						}
					}), actionHandler.getIndex() + 2);
				}
			}));
			doEvents = true;
		}
	}

	public void doTurn() {
		//If true events it runs the events, false at the end of events
		if (doEvents) {
			if (actionHandler.run()) {
				doEvents = false;
				useAttack = false;
			}
		}
	}

	public void draw() {
		// draws everything
		game.batch.begin();

		// backgrounds
		backGround.draw(game.batch);
		textBubble.draw(game.batch);
		bar1.draw(game.batch);
		bar2.draw(game.batch);
		for (HealthBar healthbar : healthBars) {
			healthbar.draw(game.batch);
		}
		// health bars
		ownerHealth.draw(game.batch);
		defenderHealth.draw(game.batch);
		expBar.draw(game.batch);

		// move/fight interfaces
		if (!useAttack) {
			fightInterface.draw(game.batch);
			if (moveScreenOpen) {
				movesInterface.draw(game.batch);
			}
		}

		// buttons
		if (moveScreenOpen && !useAttack) {
			button1.draw(game.batch);
			button2.draw(game.batch);
			button3.draw(game.batch);
			button4.draw(game.batch);
		}
		// pokemon
		poke1.draw(game.batch);
		poke2.draw(game.batch);
		text.draw(game.batch, poke1.getName(), 460, 228);
		text.draw(game.batch, Integer.toString(poke1.getLevel()), 690, 228);
		text.draw(game.batch, poke2.getName(), 55, 415);
		text.draw(game.batch, Integer.toString(poke1.getLevel()), 285, 415);

		text.draw(game.batch, poke1.getHealth() + " / " + poke1.getMaxHealth(), 610,
				175);
		// text
		if (!moveScreenOpen || useAttack) {
			comm.draw(game.batch, commentator, 30, 80);
		}
		game.batch.end();

	}

	public void loadMoves() {
		// access the size of the move array creates up to four buttons 2 are
		// always null
		Move[] moves = poke1.getMoves();
		// first move in the array
		button1 = new Button(text, moves[0].getName(), 50, 100) {
			@Override
			public void onClicked() {
				click.play();
				RunnableAction action = new RunnableAction(new Runnable() {
					@Override
					public void run() {
						// starts actions
						if (poke1.getHealth() > 0) {
							useMove(poke1, poke2, poke1.getMove(0));
						}
					}
				});
				setUpTurn(action);
			}
		};
		// 2nd move in the array
		button2 = new Button(text, moves[1].getName(), 300, 100) {
			@Override
			public void onClicked() {
				click.play();
				RunnableAction action = new RunnableAction(new Runnable() {
					@Override
					public void run() {
						// starts actions
						if (poke1.getHealth() > 0) {
							useMove(poke1, poke2, poke1.getMove(1));
						}
					}
				});
				setUpTurn(action);
			}
		};
		// null
		button3 = new Button(text, moves[2].getName(), 50, 50) {
			@Override
			public void onClicked() {
				click.play();
				RunnableAction action = new RunnableAction(new Runnable() {
					@Override
					public void run() {
						// starts actions
						if (poke1.getHealth() > 0) {
							useMove(poke1, poke2, poke1.getMove(2));
						}
					}
				});
				setUpTurn(action);
			}
		};
		// null
		button4 = new Button(text, moves[3].getName(), 300, 50) {
			@Override
			public void onClicked() {
				RunnableAction action = new RunnableAction(new Runnable() {
					@Override
					public void run() {
						// starts actions
						useMove(poke1, poke2, poke1.getMove(3));
					}
				});
				setUpTurn(action);
			}
		};
	}

	public void runBattleButtons() {
		// Makes 4 different buttons (fight, run, pokemon, bag)
		// fight open move screen
		fight.add(new BattleButton(460f, 65f, 100f, 40f) {
			@Override
			public void onClicked() {
				click.play();
				moveScreenOpen = true;
			}
		});

		fight.add(new BattleButton(620f, 25f, 50f, 25f) {
			// run sets screen back to game screen

			@Override
			public void onClicked() {
				if(poke2.getOwner().equals("trainer")) trainerBattle = true;

				if(trainerBattle) {
					click.play();
					useAttack = true;
					lockPrint = true;
					words.add("You cant run from a trainer!");
					assert words.peek() != null;
					Timer.schedule(new Timer.Task() {

						@Override
						public void run() {
							useAttack = false;
							lockPrint = true;
							words.add("what will " + poke1.getName() + " do?");
						}
						// waits until text is done
					}, words.peek().length() * 0.035f + 0.1f);
				}else{
					actionHandler.addAction(new RunnableAction(new Runnable() {
						@Override
						public void run() {
							// play words
							lockPrint = true;
							useAttack = words.add(poke1.getName() + " got away safely!");
						}
					}));

					// delay
					actionHandler.addAction(new DelayAction(3));
					run.play();
					actionHandler.addAction(new RunnableAction(new Runnable() {
						@Override
						public void run() {
							// set back
							game.setScreen(game.gameScreen);
						}
					}));
					doEvents = true;
				}
			}
		});

		// pokemon button simply says you have no pokemon
		fight.add(new BattleButton(460f, 25f, 120f, 25f) {

			@Override
			public void onClicked() {
				click.play();
				useAttack = true;
				lockPrint = true;
				words.add("You don't have any other Pokemon!");
				assert words.peek() != null;
				Timer.schedule(new Timer.Task() {

					@Override
					public void run() {
						useAttack = false;
						lockPrint = true;
						words.add("what will " + poke1.getName() + " do?");
					}
					// waits until text is done
				}, words.peek().length() * 0.035f + 0.1f);
			}
		});
		// bag button simply says you have no items
		fight.add(new BattleButton(620f, 65f, 50f, 25f) {

			@Override
			public void onClicked() {
				click.play();
				useAttack = true;
				lockPrint = true;
				words.add("you don't have any items!");
				assert words.peek() != null;
				Timer.schedule(new Timer.Task() {

					@Override
					public void run() {
						useAttack = false;
						lockPrint = true;
						words.add("what will " + poke1.getName() + " do?");
					}
					// waits until text is done
				}, words.peek().length() * 0.035f + 0.1f);
			}
		});
	}

	/**
	 * @param string
	 */
	// explained in game screen same concept
	public void printText(String string) {
		commentator = "";
		newString = string;
		index = 0;
		lockPrint = false;

		Timer.schedule(new Timer.Task() {

			@Override
			public void run() {
				if (index < newString.length()) {
					commentator += newString.charAt(index);
					index += 1;
				} else if (lockPrint) {
					cancel();
					lockPrint = true;
					commentator = "";
				}
			}

		}, 0.035f, 0.035f);
	}

	public void setHealthBars() {
		// adds new health bars for each pokemon
		healthBars.add(new HealthBar(560, 158, healthBarWidth, 50));
		healthBars.add(new HealthBar(160, 344, healthBarWidth, 50));
	}

	public void setExpBar() {
		expBar = new ExpBar(510, 136, 0, 7);
	}

	private void updateExpBar(Pokemon poke1) {
		double width = poke1.getWidthPercent() * expBarWidth;
		expBar.setWidth((int) width);
	}


	/**
	 * @param owner
	 */
	public void updateOwnerHealthBars(Pokemon owner) {
		// health percentage = width percentage,sets width
		double maxOwnerHealth = owner.getMaxHealth();
		double ownerHealths = owner.getHealth();
		double difference = maxOwnerHealth - ownerHealths;
		double percentage = (difference / maxOwnerHealth);
		double width = percentage * healthBarWidth;
		healthBars.get(0).setWidth((int) (healthBarWidth - width));
	}

	/**
	 * @param defender
	 */
	public void updateDefenderHealthBars(Pokemon defender) {
		// health percentage = width percentage, sets width
		double maxOwnerHealth = defender.getMaxHealth();
		double ownerHealths = defender.getHealth();
		double difference = maxOwnerHealth - ownerHealths;
		double percentage = (difference / maxOwnerHealth);
		double width = percentage * healthBarWidth;
		healthBars.get(1).setWidth((int) (healthBarWidth - width));
	}


	/**
	 * @param playerAction
	 */
	public void setUpTurn(RunnableAction playerAction) {

		// Reset action queue
		actionHandler.reset();

		// empty commentator, close fight options
		useAttack = true;
		commentator = "";

		// Add player action
		actionHandler.addAction(playerAction);

		// wait a second then
		actionHandler.addAction(new DelayAction(0.5f));

		// add computer action
		actionHandler.addAction(getComputerAction());

		// Reset function
		actionHandler.addAction(new RunnableAction(new Runnable() {
			@Override
			public void run() {
				lockPrint = true;
				words.add("What will " + poke1.getName() + " do?");
				moveScreenOpen = false;
			}
		}));

		Timer.schedule(new Timer.Task() {

			@Override
			public void run() {
				doEvents = true;
			}
		}, 0.5f);
	}

	/**
	 * @return
	 */
	public Action getComputerAction() {
		// computer uses a random move based of what it has
		return new RunnableAction(new Runnable() {
			@Override
			public void run() {
				int computerMove = (int) Math.floor(Math.random() * poke2.getNumOfMoves());
				if(poke2.getHealth() < (poke2.getMaxHealth() / 2) && poke2.getOwner().equals("none")) computerMove = 0;
				if(poke2.getHealth() < 10 && poke2.getOwner().equals("trainer")) computerMove = 0;
				Move move = poke2.getMove(computerMove);
				if (poke2.getHealth() > 0) {
					useMove(poke2, poke1, move);
				}
			}
		});
	}

	/**
	 * @param attackingPokemon
	 * @param defendingPokemon
	 * @param move
	 */
	public void useMove(Pokemon attackingPokemon, final Pokemon defendingPokemon, Move move) {
		// displays used move due to clicking on move earlier
		lockPrint = true;
		words.add(attackingPokemon.getName() + " used " + move.getName() + "!");
		assert words.peek() != null;
		actionHandler.addAction(new DelayAction(words.peek().length() * 0.035f + 0.5f), actionHandler.getIndex() + 1);

		switch (move.getCategory()) {
		case PHYSICAL:
			// does damage to defender
			float effective = Type.checkEffectivness(move.getType(), defendingPokemon.getType());
			float damage = getDamage(attackingPokemon.getAttack(), effective, defendingPokemon.getDefence(),
					move.getPower(), attackingPokemon.getLevel());
			if (defendingPokemon.getHealth() >= 0) {
				defendingPokemon.damage(damage);
			}

			// checks and displays the effectiveness
			if (effective == 1.5) {
				actionHandler.addAction(new RunnableAction(new Runnable() {

					@Override
					public void run() {
						lockPrint = true;
						words.add("It's super effective!");
						assert words.peek() != null;
						actionHandler.addAction(new DelayAction(words.peek().length() * 0.035f + 0.5f),
								actionHandler.getIndex() + 3);
					}
				}), actionHandler.getIndex() + 2);
			} else if (effective == 0.5) {
				actionHandler.addAction(new RunnableAction(new Runnable() {

					@Override
					public void run() {
						lockPrint = true;
						words.add("It's not very effective...");
						assert words.peek() != null;
						actionHandler.addAction(new DelayAction(words.peek().length() * 0.035f + 0.5f),
								actionHandler.getIndex() + 3);
					}
				}), actionHandler.getIndex() + 2);
			} else if (effective == 1) {
				actionHandler.addAction(new RunnableAction(new Runnable() {

					@Override
					public void run() {
						lockPrint = true;
						words.add(defendingPokemon.getName() + " was hurt");
						assert words.peek() != null;
						actionHandler.addAction(new DelayAction(words.peek().length() * 0.035f + 0.5f),
								actionHandler.getIndex() + 3);
					}
				}), actionHandler.getIndex() + 2);
			}
			break;

		case PASSIVE:
			// lowers attack state of defender by multiply - 10%
			if (move.getName().equals("GROWL") || move.getName().equals("SCREECH")) {
				defendingPokemon.setAttackMultiplier(defendingPokemon.getAttackMultiplier() - 0.1f);

				// delay before commentator
				actionHandler.addAction(new RunnableAction(new Runnable() {

					@Override
					public void run() {
						lockPrint = true;
						words.add(defendingPokemon.getName() + "'s attack fell!");
						assert words.peek() != null;
						actionHandler.addAction(new DelayAction(words.peek().length() * 0.035f + 0.5f),
								actionHandler.getIndex() + 3);
					}

				}), actionHandler.getIndex() + 2);
			}
			break;
		default:
			break;
		}
	}

	/**
	 * @param attack
	 * @param effectiveness
	 * @param defense
	 * @param power
	 * @param level
	 * @return float
	 */
	public float getDamage(float attack, float effectiveness, float defense, float power, float level) {
		// damage ratios
		float critical = (float) Math.floor(((Math.random() + 1.1)));
		float range = (float) (Math.random() * 0.15 + 0.85);
		float modifier = effectiveness * critical * range;
		return (((2 * level + 10) / 250) * (attack / defense) * power + 2) * modifier;
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
		// resets health / attack when battle is over
		if(poke2.getOwner().equals("none")){
			poke2.setHealth(poke2.getMaxHealth());
			poke2.setAttackMultiplier(1);
		}
		battle.stop();
		wild.stop();
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
		// checks all button clicks
		button1.checkClick(screenX, Gdx.graphics.getHeight() - screenY);
		button2.checkClick(screenX, Gdx.graphics.getHeight() - screenY);
		button3.checkClick(screenX, Gdx.graphics.getHeight() - screenY);
		button4.checkClick(screenX, Gdx.graphics.getHeight() - screenY);

		for (Button f : fight) {
			f.checkClick(screenX, Gdx.graphics.getHeight() - screenY);
		}
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
