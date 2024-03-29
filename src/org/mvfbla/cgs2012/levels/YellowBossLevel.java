package org.mvfbla.cgs2012.levels;

import org.mvfbla.cgs2012.base.GameLevel;
import org.mvfbla.cgs2012.base.GameObject;
import org.mvfbla.cgs2012.base.Map;
import org.mvfbla.cgs2012.base.TiledObject;
import org.mvfbla.cgs2012.characters.Characters;
import org.mvfbla.cgs2012.characters.YellowBoss;
import org.mvfbla.cgs2012.interactable.Button;
import org.mvfbla.cgs2012.interactable.QuestionWindow;
import org.mvfbla.cgs2012.utils.GameConstants;
import org.mvfbla.cgs2012.utils.TypeWriter;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.state.StateBasedGame;

/**
 * @author Alex Wang
 * Yellow Boss Level
 * Dodge lasers and activate platforms to defeat the boss.
 */
public class YellowBossLevel extends GameLevel {

	private YellowBoss yellowBoss;
	private final static int MAP_WIDTH = 780;
	private final static int MAP_HEIGHT = 600;
	private final YellowButton[] buttons = new YellowButton[3];
	private Animation lightning;

	private TypeWriter text;
	private QuestionWindow questions;
	private boolean beforeQuestions,needRestart,afterQuestions;

	//The Yellow Boss Level
	//Sets the ID of the level
	public YellowBossLevel(int stateID) {
		this.stateID = stateID;
	}

	@Override
	public void draw(Graphics g){
		super.draw(g);
		//animates the broken platforms
		g.setColor(Color.green);
		if(yellowBoss.isActivated(0)){
			g.drawAnimation(lightning, 96-8, 288-8);
			buttons[0].setStateNum(2);
		} else {
			handleButton(0);
		}
		if(yellowBoss.isActivated(1)){
			g.drawAnimation(lightning, 320, 224-8);
			buttons[1].setStateNum(2);
		} else {
			handleButton(1);
		}
		if(yellowBoss.isActivated(2)){
			g.drawAnimation(lightning, 560-8, 288-8);
			buttons[2].setStateNum(2);
		} else {
			handleButton(2);
		}
		if(!yellowBoss.isAlive()) {
			for(int i = 0; i < 3; i++) {
				g.drawAnimation(lightning, yellowBoss.getX()-10, yellowBoss.getY()+(i*48));
			}
		}
		if(player.shouldDisplay())
			player.draw(g);
		//changes opacity during transition state
		if(transState != 0) {
			g.setColor(new Color(0, 0, 0, 1f-(transTime/(float)transLength)));
			g.fillRect(0, 0, 100000, 100000);
		}
		//processes display after death
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
		if(beforeQuestions == true || afterQuestions == true){
			try {
				text.draw(g,0,0,720,80);
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}
		if(questions.getAnswering() == true){
			questions.draw(g,0,0);
		}
		// Draw pause window if needed
		if(GameConstants.getPaused() == true){
			pauseWindow.draw(g,-(int)cameraBox.getOffsetX(),-(int)cameraBox.getOffsetY());
		}
	}
	@Override
	public void enter(GameContainer container, StateBasedGame stateBasedGame) throws SlickException {
		initStuff();
		GameConstants.enemies.add(yellowBoss);
		YellowButton b1 = new YellowButton(125,500, 0);
		buttons[0] = b1;
		YellowButton b2 = new YellowButton(360,500, 1);
		buttons[1] = b2;
		YellowButton b3 = new YellowButton(595,500, 2);
		buttons[2] = b3;
		GameConstants.interacts.add(b1);
		GameConstants.interacts.add(b2);
		GameConstants.interacts.add(b3);
		yellowBoss.setPlayer(player);
	}
	@Override
	public int getID(){
		return stateID;
	}
	/**
	 * @param id button for each platform
	 */
	public void handleButton(int id) {
		if(yellowBoss.location == id)
			buttons[id].setStateNum(1);
		else {
			buttons[id].setStateNum(0);
			buttons[id].notif.playAnimation("near");
		}
	}
	@Override
	public void init(GameContainer container,StateBasedGame sbg) throws SlickException {
		super.setBackgroundInfo(33, 8);
		afterQuestions = false;
		beforeQuestions = true;
		map = new Map("data"+GameConstants.separatorChar+"Maps"+GameConstants.separatorChar+"YellowBossLevel_5.tmx","data"+GameConstants.separatorChar+"Maps");
		yellowBoss = new YellowBoss(330,100);
		background = new Image("data"+GameConstants.separatorChar+"Level"+GameConstants.separatorChar+"Background.png");
		lightning = new Animation(new SpriteSheet("data"+GameConstants.separatorChar+"Level"+GameConstants.separatorChar+"Lightning.png", 144, 48), 500);
		lightning.start();
		text = new TypeWriter();
		questions = new QuestionWindow();
	}
	@Override
	public void initObject(TiledObject to) throws SlickException {
		// no special objects

	}
	@Override
	public void leave(GameContainer container, StateBasedGame stateBasedGame) throws SlickException {}
	@Override
	public void render(GameContainer container,StateBasedGame sbg, Graphics g) throws SlickException  {
		draw(g);
	}

	@Override
	public void update(GameContainer container, StateBasedGame sbg,int delta) throws SlickException {
		//upate method for processing battle with YellowBoss
		updateMain(container, sbg, delta);
		if(!GameConstants.getPaused()) {
			if(!afterQuestions){
				GameConstants.level.player.setControl(false);
			}
			if(!yellowBoss.isAlive()) {
				yellowBoss.aiming = yellowBoss.charging = yellowBoss.firing = yellowBoss.teleporting = false;
				transState = 2;
				if((GameConstants.bossesDefeated & 1) != 1) {
					GameConstants.playNum++;
					System.out.println(GameConstants.playNum);
				}
				GameConstants.lastBoss = 3;
				GameConstants.bossesDefeated |= 1;
			}
			lightning.update(delta);
			//plot text before the battle
			if(beforeQuestions){
				text.setText("The moment I enter, I sense the air of superiority emanating from the figure in the room. " +
						"I want to ask it so many questions. I want to understand.  A stream of " +
						"questions pour from my mouth. But it only responds with questions of its own." +
						"                                       ");
				if(text.isFinished() && beforeQuestions){
					beforeQuestions = false;
					needRestart = true;
					questions.setAnswering(true);
				}
			}
			if(questions.getAnswering() == false && !beforeQuestions){
				afterQuestions = true;
			}
			//plot text after battle
			if(afterQuestions == true){
				text.setText("I am done with its games. I want answers now. Who am I? Why am I here?" +
						" But there is no answer, this only" +
						" seems to infuriate the figure...   ...   ...   ...   ...   " +
						"                                       ");
				if(needRestart){
					text.restart();
					needRestart = false;
				}
			}
			if(beforeQuestions == true || afterQuestions == true)
				text.update(container, delta);
			if(questions.getAnswering()){
				questions.update(container, delta);
			}
			for(Characters guy : GameConstants.enemies) {
				if(guy instanceof YellowBoss){
					YellowBoss boss = (YellowBoss)guy;
					if(afterQuestions == true){
						boss.setAttacking(true);
						GameConstants.level.player.setControl(true);
					}
				}
			}
		}
	}
	/**
	 * @author Alex Wang
	 * Buttons in Yellow Boss Level to activate platforms
	 */
	public class YellowButton extends Button {
		public int number;
		private int state;
		protected YellowButton(int x, int y, int num) throws SlickException {
			super(x, y, null);
			number = num;
			addAnimation("broke", new Animation(new SpriteSheet("data"+GameConstants.separatorChar+"maps"+GameConstants.separatorChar+"ButtonBroke.png", 32, 32), 150));
		}
		public int getStateNum() {
			return state;
		}
		@Override
		public void interact(GameObject source) {
			long time = System.currentTimeMillis();
			if(time-lastPress >= cooldown) {
				lastPress = time;
				if(state == 1) {
					setStateNum(2);
					yellowBoss.activate(number);
					playAnimation("broke");
				}
			}
		}
		/**
		 * @param state sets the states for the platform switches
		 */
		public void setStateNum(int state) {
			if(state == 2) {
				trigger.setActive(false);
				notif.playAnimation("near");
				playAnimation("broke");
			}
			if(state == 1) {
				trigger.setActive(true);
				playAnimation("on");
			}
			if(state == 0) {
				trigger.setActive(false);
				playAnimation("off");
			}
			this.state = state;
		}
	}
}
