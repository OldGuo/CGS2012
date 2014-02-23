package org.mvfbla.cgs2012.base;

import java.util.ArrayList;

import org.mvfbla.cgs2012.characters.BasicEnemy;
import org.mvfbla.cgs2012.characters.BiggerEnemy;
import org.mvfbla.cgs2012.characters.BlackBoss;
import org.mvfbla.cgs2012.characters.BlueBoss;
import org.mvfbla.cgs2012.characters.Characters;
import org.mvfbla.cgs2012.characters.Enemy;
import org.mvfbla.cgs2012.characters.PlantedEnemy;
import org.mvfbla.cgs2012.characters.Player;
import org.mvfbla.cgs2012.characters.RedBoss;
import org.mvfbla.cgs2012.characters.YellowBoss;
import org.mvfbla.cgs2012.interactable.Button;
import org.mvfbla.cgs2012.interactable.ButtonListener;
import org.mvfbla.cgs2012.interactable.Elevator;
import org.mvfbla.cgs2012.interactable.InteractiveObject;
import org.mvfbla.cgs2012.interactable.MotionSensor;
import org.mvfbla.cgs2012.interactable.MovingTile;
import org.mvfbla.cgs2012.interactable.Pillar;
import org.mvfbla.cgs2012.interactable.QuestionWindow;
import org.mvfbla.cgs2012.interactable.Trigger;
import org.mvfbla.cgs2012.interactable.TriggerListener;
import org.mvfbla.cgs2012.utils.CameraObject;
import org.mvfbla.cgs2012.utils.GameConstants;
import org.mvfbla.cgs2012.utils.PauseWindow;
import org.mvfbla.cgs2012.utils.TypeWriter;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public abstract class GameLevel extends BasicGameState{
	// Listener for the motion sensor button
	public class MotionButtonListener implements ButtonListener {
		@Override
		public void buttonPressed(boolean state) {
			if(state) {
				for(MotionSensor ms : GameConstants.sensors)
					ms.setState((byte) 0);
			} else
				for(MotionSensor ms : GameConstants.sensors)
					ms.setState((byte) 1);
		}
	}
	// Listener for plot text
	public class PlotListener implements TriggerListener {
		private Trigger parent;
		private String choice;
		public void init(Trigger t, String choice) {
			parent = t;
			this.choice = choice;
		}
		@Override
		public void onEnter(GameObject src) {
			if(src == player) {
				textChoice = choice;
				changeText(textChoice);
				parent.setActive(false);
			}
		}
		@Override
		public void onExit(GameObject src) {}
		@Override
		public void triggered(GameObject src) {}
	}
	// Initialize lots of variables
	protected int bgOffsetX, bgNumRepeat;
	public QuestionWindow questions;
	public PauseWindow pauseWindow;
	protected Map map;
	public Player player;
	public BlackBoss blackBoss;
	protected CameraObject cameraBox;
	protected Image background;
	protected boolean lost = false;
	protected int stateID = -1;
	protected TypeWriter text;
	public boolean done = false;
	protected float time=0;
	public long transTime = 0;
	protected byte transState = 0;
	public long transLength = 1200;
	public Elevator elevator;
	private String textChoice = " ";
	protected long deathTime = 0;
	private final long deathDur = 1000;
	protected final long deathDelay = 2000;
	public int questionCount = 0;
	public boolean buttonQuestion = false;
	protected Button questionButton;

	protected Trigger elevatorKeyTrigger;
	public int wrongCount = 0;
	// Changes the plot text based on player locations
	public void changeText(String textChoice){
		String textString = null;
		if(textChoice.equals("intro")) {
			textString = "I see nothing around me but a sea of darkness. " +
					"I try to recall the events prior, but every strand of thought escapes my grasps. " +
					"I know nothing except that I must move forward." +
					"                                                                                       " +
                    "                                                                                       ";
		}
		if(textChoice.equals("firstEnemy")) {
			textString = "As I come into the bright light, I see something ahead. " +
					"A furious rage builds up inside me. I want to use [SPACE] to defeat" +
					" this enemy. " +
					"                                                                                       " +
                    "                                                                                       ";
		}
		if(textChoice.equals("firstTechnology")) {
			textString = "A strange piece of technology stands before of me. Although it seems alien to me, a voice " +
					"inside me tells me to use [SPACE] to active the platform ahead. I feeled rushed" +
					" for time but luckly I can press [ESC] to pause." +
					"                                                                                       " +
                    "                                                                                       ";
		}
		if(textChoice.equals("firstQuestion")) {
			textString = "I feel with a strong sense of achievement." +
					"  The blur in my mind begins to clear up a bit. " +
					"But my feeling of accomplishment is quickly drowned out by the pangs of regret. " +
					" Did I have to defeat that enemy? " +
					"Perhaps I should refrain from physical conflicts in the future." +
					"                                                                                       " +
                    "                                                                                       ";
		}
		if(textChoice.equals("firstJump")) {
			textString = "Almost effortlessly, I make each jump. " +
					"These jumps almost seemed familiar to me. " +
					"Like I have done them countless times in the past." +
					"                                                                                       " +
                    "                                                                                       ";
		}
		if(textChoice.equals("longJump")) {
			textString = "The next jump is longer and wider than the previous.  I want to turn back." +
					" But a relenteless force drives me to push forward" +
					"  Clearing this jump requires a leap of faith." +
					"                                                                                       " +
                    "                                                                                       ";
		}
		if(textChoice.equals("tutorialEnd")) {
			textString = "Almost in disbelief, I look down at my suit" +
					" and see a badge.  \"EMPLOYEE NAME\" it says. However the text below it seems to " +
					"have been intentionally scratched out. " +
					"So many questions filled my mind, but all the answers lie ahead. " +
					"                                                                                       " +
                    "                                                                                       ";
		}
		if(textChoice.equals("elevatorLevelStart")) {
			textString = "The elevator feels like it has brought me up a few floors. " +
					"My head begins to spin.  I have so many questions.  " +
					"Where am I? Who am I? Why am I here?" +
					" All I want are the answers.  ...   ...   ..." +
					"                                                                                       " +
                    "                                                                                       ";
		}
		if(textChoice.equals("anotherEnemy")) {
			textString = "There is a key up above, but another one of those enemies blocks my path.  " +
					"Something tells me I do not need the key, but I desperately want it anyways. Perhaps" +
					" this time I will use the [UP] arrow key to jump over the enemy without harm." +
					"                                                                                       " +
                    "                                                                                       ";
		}
		if(textChoice.equals("lockedElevator")) {
			textString = "Locked!? It seems like a key will be useful here. But I wonder about the " +
					" technology beyond the elevator..." +
					"                                                                                       " +
                    "                                                                                       ";
		}
		if(textChoice.equals("motionLevelStart")) {
			textString = "The elevator brings me up another few floors. The haze in my mind" +
					" seems to have cleared up a bit.  My name   ...   Alex Wang.  My job   ...   " +
					"...   ...   ...   ...   nothing. Hopefully, more will clear up as time progresses." +
					"                                                                                       " +
                    "                                                                                       ";
		}
		if(textChoice.equals("turnOffSensor")) {
			textString = "It seems dangerous up ahead, but something tells me whats beyond will pay off." +
					" It is either this or the motion sensors   ...   " +
					"                                                                                       " +
                    "                                                                                       ";
		}
		if(textChoice.equals("accomplishment")) {
			textString = "I feel proud and accomplished having conquered the motion sensors." +
					"                                                                                       " +
                    "                                                                                       ";
		}
		if(textChoice.equals("beatMotionLevel")) {
			textString = "Something clicks inside my head as I see the elevator." +
					"  There I felt something up ahead. I have to keep going.  I have to" +
					" find the answers.  " +
					"                                                                                       " +
                    "                                                                                       ";
		}
		if(textChoice.equals("gravityLevelStart")) {
			textString = "Rising higher and higher in the buildling, I almost feel anxious. " +
					"My questions will soon be answered.  I can feel it.  " +
					"                                                                                       " +
                    "                                                                                       ";
		}
		if(textChoice.equals("flippedGrav")) {
			textString = "A feeling of complete exhiliration comes over me.  All the enemies are on the other" +
					" side of the wall. I can almost feel their desperation as " +
					" they attempt to reach me.  I smile at my superior intellect." +
					"                                                                                       " +
                    "                                                                                       ";
		}
		if(textChoice.equals("beforeBoss")) {
			textString = "This is it   ...   I can feel it. Whatever is beyond that elevator calls to me.  " +
					" The answers lie ahead   ...   ...   ...   ...   ..." +
					"                                                                                       " +
                    "                                                                                       ";
		}
		if(textChoice.equals("blackBoss")) {
			textString = "...   ...   ...   ...   ...   ...   ...   ...   ...   ...   ...   ...   ...    " +
					"A figure stands before me.  It copies my every move down to a wire. " +
					"Is it a friend? A foe? I ask it what it is, and it replies in its saccharine tone.                    " +
					"                  \"I am Perfection\"" +
					"                                                                                                                              " +
                    "                                                                                       ";
        }
		text.setText(textString);
		text.restart();
	}
	/**
	 * Main draw method of every level
	 * @param g - Graphics object
	 */
	public void draw(Graphics g){
		g.setColor(new Color(58,58,58));
		// Draw background
		for(int i = 0; i < bgNumRepeat; i++)
			background.draw((int)cameraBox.getOffsetX()+100*i + bgOffsetX,(int)cameraBox.getOffsetY()-176);
		map.getMap().render((int)cameraBox.getOffsetX(),(int)cameraBox.getOffsetY());
		cameraBox.draw(g);
		// Draw enemies
		g.setColor(Color.white);
		for(Characters guy:GameConstants.enemies){
			if(guy.shouldDisplay()){
				guy.draw(g);
			}
		}
		// Draw Tiled objects
		for(MovingTile t : GameConstants.platforms)
			t.draw(g);
		for(MotionSensor m : GameConstants.sensors)
			m.draw(g);
		for(Pillar p : GameConstants.pillars)
			p.draw(g);
		for(InteractiveObject io : GameConstants.interacts)
			io.draw(g);
		// Draw Player health bar
		for(int i=1;i<=GameConstants.playerMaxHealth;i++){
			if(i<=player.getHealth())
				g.setColor(Color.red);
			else
				g.setColor(Color.gray);
			g.fillRect(i*40-24-(int)cameraBox.getOffsetX(), 554, 32, 32);
		}
		// Make sure player needs to be draw
		if(transState != 2&&player.shouldDisplay()){
			player.draw(g);
		}
		try {
			text.draw(g,-(int)cameraBox.getOffsetX(),-(int)cameraBox.getOffsetY(),720,80);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		// Draw question window if needed
		if(questions.getAnswering() == true){
			questions.draw(g,-(int)cameraBox.getOffsetX(),-(int)cameraBox.getOffsetY());
		}
		// Draw transition if needed
		if(transState != 0) {
			g.setColor(new Color(0, 0, 0, 1f-(transTime/(float)transLength)));
			g.fillRect(0, 0, 100000, 100000);
		}
		// Draw death transition if needed
		if(deathTime > 0) {
			player.stopAnimation();
			player.draw(g);
			long time = deathTime % deathDelay;
			float prog = time/(float)deathDelay;
			if(prog > 0.5f)
				prog = 1-prog;
			Color c = new Color(0, 0, 0, prog);
			g.setColor(c);
			g.fillRect(0, 0, 100000, 100000);
		}
		// Draw pause window if needed
		if(GameConstants.getPaused() == true){
			pauseWindow.draw(g,-(int)cameraBox.getOffsetX(),-(int)cameraBox.getOffsetY());
		}
	}
	/**
	 * Returns an Enemy object from a given name
	 * @param name - Name of enemy to be generated
	 * @param x - X location of enemy
	 * @param y - Y location of enemy
	 * @return An enemy from the given name
	 * @throws SlickException
	 */
	public Enemy enemyFromName(String name, int x, int y) throws SlickException {
		Enemy out = null;
		if(name.equals("BasicEnemy")) {
			out = new BasicEnemy(x, y);
		}
		if(name.equals("PlantedEnemy")) {
			out = new PlantedEnemy(x, y);
		}
		if(name.equals("BiggerEnemy")) {
			out = new BiggerEnemy(x, y);
		}
		if(name.equals("RedBoss")) {
			out = new RedBoss(x, y);
		}
		if(name.equals("BlueBoss")) {
			out = new BlueBoss(x, y);
		}
		if(name.equals("YellowBoss")) {
			out = new YellowBoss(x, y);
		}
		return out;
	}
	// Method for every class to initialize the tiled objects
	public abstract void initObject(TiledObject to) throws SlickException;
	public void initStuff() throws SlickException {
		// Clears constants
		GameConstants.clear();
		GameConstants.currMap = map;
		GameConstants.collidableObjects.addAll(map.getBoxes());
		GameConstants.platforms = new ArrayList<MovingTile>();
		GameConstants.level = this;
		boolean flag=false;
		// Make questions and pause window
		questions = new QuestionWindow();
		pauseWindow = new PauseWindow();
		pauseWindow.init();
		text = new TypeWriter();
		done = false;
		questionCount = 0;
		int motionDelay = 0;
		// Initialize the Tiled objects
		for(TiledObject to : map.getObjects()) {
			// Enemy spawns
			if(to.getType().equals("spawn")){
				GameConstants.enemies.add(enemyFromName(to.getProperty("var"), to.getX(), to.getY()));
			}
			// Trigger for plot text
			if(to.getType().equals("trigger")) {
				PlotListener pl = new PlotListener();
				Trigger t = new Trigger(to, pl);
				pl.init(t, to.getProperty("var"));
				GameConstants.triggers.add(t);
			}
			// Trigger for level finish
			if(to.getType().equals("finish")) {
				Elevator e = new Elevator(to.getX(), to.getY());
				GameConstants.interacts.add(e);
				elevator = e;
			}
			// Button to toggle motion sensor
			if(to.getType().equals("motionButton")) {
				Button b = new Button(to.getX(), to.getY(), new MotionButtonListener());
				GameConstants.interacts.add(b);
			}
			// Initialize motion sensors
			if(to.getType().equals("motionSensor")) {
				MotionSensor ms = new MotionSensor(to, motionDelay);
				motionDelay += 500;
				GameConstants.sensors.add(ms);
			}
			if(to.getType().equals("player")) {
				player = new Player(to.getX(),to.getY());
				cameraBox = new CameraObject(player,250,1300);
			}
			if(to.getType().equals("playerBoss")) {
				player = new Player(to.getX(),to.getY());
				cameraBox = new CameraObject(player,2000,1300);
			}
			// Call each GameLevel's own object initialization methods
			initObject(to);
		}
		background = new Image("data"+GameConstants.separatorChar+"Level"+GameConstants.separatorChar+"Background.png");
		transState = 1;
		// Reset player hp
		player.setInitialHealth(GameConstants.playerMaxHealth);
	}
	// Reset the level
	public void reset() {
		try {
			init(null, null);
			enter(null, null);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	// Sets the length of the background, and offset
	public void setBackgroundInfo(int offset, int numRepeat){
		bgNumRepeat = numRepeat;
		bgOffsetX = offset;
	}
	// Unused method
	public void unlockElev(int source) {}
	// Main update method every level calls every frame
	public void updateMain(GameContainer container, StateBasedGame sbg,int delta) throws SlickException{
		Input input = container.getInput();
		if(GameConstants.getPaused() == false){
			text.update(container,delta);
			// Do transitions
			if(transState == 1) {
				transTime += delta;
				if(transTime >= transLength) {
					transState = 0;
					transTime = 2*transLength;
				}
			} else if(transState == 2) {
				transTime -= delta;
				// Transition to next state
				if(transTime <= 0) {
					if(stateID == 4) {
						if(GameConstants.enemiesKilled == 0) {
							sbg.enterState(5);
						} else if(GameConstants.enemiesKilled > GameConstants.techUsed) {
							sbg.enterState(6);
						} else {
							sbg.enterState(7);
						}
					} else if(stateID < 4) {
						sbg.enterState(stateID + 1);
					} else if(stateID > 4 && stateID <=7) {
						if(GameConstants.bossesDefeated == 7) {
							sbg.enterState(Game.BLACK_BOSS_STATE);
						} else {
							sbg.enterState(Game.PLOT_STATE);
						}
					} else if(stateID == 8) {
						GameConstants.bossesDefeated |= 8;
						sbg.enterState(Game.PLOT_STATE);
					}
					init(container, sbg);
					enter(container, sbg);
				}
			}
			// Resume all animations
			for(Characters guy:GameConstants.enemies){
				if(guy.shouldDisplay())
					guy.resumeAnimation();
			}
			// Handle player death
			if(!player.isAlive()) {
				deathTime += delta;
				if(deathTime >= deathDur) {
					transTime = 0;
					deathTime = 0;
					questionCount = 0;
					reset();
				}
			}
			// Disable player controls while answering questions
			if(questions.getAnswering()) {
				questions.update(container, delta);
				player.setControl(false);
			}else{
				if(stateID < 5 || stateID >= 8)
					player.setControl(true);
			}
			// Finish the level
			if(done && questions.getAnswering() == false && questionCount >= 4) {
				player.setControl(false);
				transState = 2;
			}

			if(!lost){
				player.update(container, delta);
			}
			// Handle the enemies
			for(Characters guy:GameConstants.enemies){
				guy.update(container, delta);
				// Calculate player taking damage
				float tempX=player.getCenterX()-guy.getCenterX();//calculates distance between player and enemy
				double Xdist=Math.pow(tempX, 2);
				double Ydist=Math.pow(player.getCenterY()-guy.getCenterY(), 2);
				float totalDist=(float)Math.sqrt(Xdist+Ydist);
				float hit=0;
				if(player.collides(guy)&&guy.isAlive()){
					if(guy instanceof BasicEnemy || guy instanceof PlantedEnemy){
						if(Math.abs(tempX)<20)
							player.setHealth(player.getHealth()-1);
					}
					else{
						if(Math.abs(tempX)<40)
							player.setHealth(player.getHealth()-1);
					}
				}
				// Make PlantedEnemy walk towards the player
				hit+=(guy.getWidth()/2);
				if(guy instanceof PlantedEnemy){
					if(totalDist<((PlantedEnemy)guy).getSight()&&totalDist>9){
						((PlantedEnemy)guy).changeSleep(true);
						((PlantedEnemy)guy).setDirection(Math.signum(tempX));
						//((PlantedEnemy)guy).setSpeed(3*Math.signum(tempX));
					}
					else
						((PlantedEnemy)guy).changeSleep(false);
				}
				// Handle the punches
				if(player.isPunching()&&-1*Math.signum(tempX)==Math.signum(player.getRange())&&Math.abs(player.getCenterY()-guy.getCenterY())<guy.getHeight()){
					if(Math.abs(tempX)<(Math.abs(player.getRange())+hit)){
						if(guy.isAlive())
							guy.setHealth(guy.getHealth()-1);
					}
				}
			}
			// Update moving platforms
			for(MovingTile t : GameConstants.platforms)
				t.update(container, delta);
			// Update motion sensors
			for(MotionSensor m : GameConstants.sensors)
				m.update(container, delta);
			// Update camera location
			cameraBox.update(container, delta);


			// Update the text box
			text.update(container,delta);
		}else{
			// Stop player and enemy animations is paused
			player.stopAnimation();
			for(Characters guy:GameConstants.enemies){
				if(guy.shouldDisplay())
					guy.stopAnimation();
			}
		}
		if(!container.hasFocus()) {
			GameConstants.paused = true;
		}
		// Pause the game
		if(input.isKeyPressed(Input.KEY_ESCAPE)){
			GameConstants.flipPaused();
		}
		if(GameConstants.getPaused())
			pauseWindow.update(container,sbg);
	}
}
