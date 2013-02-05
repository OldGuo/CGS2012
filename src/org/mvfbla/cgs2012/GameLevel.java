package org.mvfbla.cgs2012;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public abstract class GameLevel extends BasicGameState{
	protected int bgOffsetX, bgNumRepeat;
	public QuestionWindow questions;
	public PauseWindow pauseWindow;
	protected Map map;
	public Player player;
	public BlackBoss blackBoss;
	protected CameraObject cameraBox;
	protected Image background;
	protected Boolean lost = false;
	protected int stateID = -1;
	private TypeWriter text;
	protected boolean done = false;
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

	public void initStuff() throws SlickException {
		GameConstants.clear();
		GameConstants.currMap = map;
		GameConstants.collidableObjects.addAll(map.getBoxes());
		GameConstants.platforms = new ArrayList<MovingTile>();
		GameConstants.level = this;
		questions = new QuestionWindow();
		pauseWindow = new PauseWindow();
		pauseWindow.init();
		text = new TypeWriter();
		done = false;
		int motionDelay = 0;
		for(TiledObject to : map.getObjects()) {
			if(to.getType().equals("spawn")){
				GameConstants.enemies.add(enemyFromName(to.getProperty("var"), to.getX(), to.getY()));
			}
			if(to.getType().equals("trigger")) {
				PlotListener pl = new PlotListener();
				Trigger t = new Trigger(to, pl);
				pl.init(t, to.getProperty("var"));
				GameConstants.triggers.add(t);
			}
			if(to.getType().equals("finish")) {
				Elevator e = new Elevator(to.getX(), to.getY(), this);
				GameConstants.interacts.add(e);
				elevator = e;
			}
			if(to.getType().equals("motionButton")) {
				Button b = new Button(to.getX(), to.getY(), new MotionButtonListener());
				GameConstants.interacts.add(b);
			}
			if(to.getType().equals("motionSensor")) {
				MotionSensor ms = new MotionSensor(to, motionDelay);
				motionDelay += 500;
				GameConstants.sensors.add(ms);
			}
			initObject(to);
		}
		background = new Image("data\\Background.png");
		transState = 1;
		player.setInitialHealth(GameConstants.playerMaxHealth);
	}
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
	public abstract void initObject(TiledObject to) throws SlickException;
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
	public void unlockElev(int source) {}
	public void updateMain(GameContainer container, StateBasedGame sbg,int delta) throws SlickException{
		Input input = container.getInput();
		if(GameConstants.getPaused() == false){
			if(transState == 1) {
				transTime += delta;
				if(transTime >= transLength) {
					transState = 0;
					transTime = 2*transLength;
				}
			} else if(transState == 2) {
				transTime -= delta;
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
							sbg.enterState(Game.MAIN_MENU_STATE);
						}
					}
					init(container, sbg);
					enter(container, sbg);
				}
			}
			if(!player.isAlive()) {
				deathTime += delta;
				if(deathTime >= deathDur) {
					transTime = 0;
					deathTime = 0;
					questionCount = 0;
					init(container, sbg);
					enter(container, sbg);
				}
			}
			if(done && questions.getAnswering() == false && questionCount >= 4) {
				player.setControl(false);
				transState = 2;
			}
			if(questions.getAnswering()) {
				questions.update(container);
				player.setControl(false);
			}else{
				player.setControl(true);
			}if(!lost){
				player.update(container, delta);
			}
			for(Characters guy:GameConstants.enemies){
				guy.update(container, delta);
				float tempX=player.getCenterX()-guy.getCenterX();//calculates distance between player and enemy
				double Xdist=Math.pow(tempX, 2);
				double Ydist=Math.pow(player.getCenterY()-guy.getCenterY(), 2);
				float totalDist=(float)Math.sqrt(Xdist+Ydist);
				String name=guy.getClass().toString();
				float hit=0;
				if(player.collides(guy)&&guy.isAlive()){
					if(name.equals("class org.mvfbla.cgs2012.BasicEnemy")||name.equals("class org.mvfbla.cgs2012.PlantedEnemy")){
						if(Math.abs(tempX)<20)
							player.setHealth(player.getHealth()-1);
					}
					else{
						if(Math.abs(tempX)<40)
							player.setHealth(player.getHealth()-1);
					}
				}
				hit+=(guy.getWidth()/2);
				if(name.equals("class org.mvfbla.cgs2012.PlantedEnemy")){
					if(totalDist<((PlantedEnemy)guy).getSight()&&totalDist>9){
						((PlantedEnemy)guy).changeSleep(true);
						((PlantedEnemy)guy).setDirection(Math.signum(tempX));
						((PlantedEnemy)guy).setSpeed(3*Math.signum(tempX));
					}
					else
						((PlantedEnemy)guy).changeSleep(false);
				}

				if(player.isPunching()&&-1*Math.signum(tempX)==Math.signum(player.getRange())&&Math.abs(player.getCenterY()-guy.getCenterY())<guy.getHeight()){
					if(Math.abs(tempX)<(Math.abs(player.getRange())+hit)){
						if(guy.isAlive())
							guy.setHealth(guy.getHealth()-1);
					}
				}
				/*if(!player.isAlive()){
					System.out.println("GG");
				}*/
			}
			for(MovingTile t : GameConstants.platforms)
				t.update(container, delta);
			for(MotionSensor m : GameConstants.sensors)
				m.update(container, delta);
			cameraBox.update(container, delta);

			//testing
			if (input.isKeyDown(Input.KEY_0))
				sbg.enterState(Game.MAIN_MENU_STATE);
			if (input.isKeyDown(Input.KEY_1))
				sbg.enterState(Game.TUTORIAL_STATE);
			if (input.isKeyDown(Input.KEY_2))
				sbg.enterState(Game.ELEVATOR_STATE);
			if (input.isKeyDown(Input.KEY_3))
				sbg.enterState(Game.MOTION_SENSOR_STATE);
			if (input.isKeyDown(Input.KEY_4))
				sbg.enterState(Game.GRAVITY_STATE);
			if (input.isKeyDown(Input.KEY_5))
				sbg.enterState(Game.BLUE_BOSS_STATE);
			if (input.isKeyDown(Input.KEY_6))
				sbg.enterState(Game.RED_BOSS_STATE);
			if (input.isKeyDown(Input.KEY_7))
				sbg.enterState(Game.YELLOW_BOSS_STATE);
			if (input.isKeyDown(Input.KEY_8))
				sbg.enterState(Game.BLACK_BOSS_STATE);
			text.update(container,delta);
		}else{
			player.stopAnimation();
			for(Characters guy:GameConstants.enemies){
				if(guy.shouldDisplay())
					guy.stopAnimation();
			}
		}
		if(input.isKeyPressed(Input.KEY_ESCAPE)){
			GameConstants.flipPaused();
		}
		if(GameConstants.getPaused())
			pauseWindow.update(container,sbg);
	}
	public Enemy enemyFromName(String name, int x, int y) throws SlickException {
		Enemy out = null;
		switch(name) {
		case "BasicEnemy" :
			out = new BasicEnemy(x, y);
			break;
		case "PlantedEnemy" :
			out = new PlantedEnemy(x, y);
			break;
		case "BiggerEnemy" :
			out = new BiggerEnemy(x, y);
			break;
		case "RedBoss" :
			out = new RedBoss(x, y);
			break;
		case "BlueBoss" :
			out = new BlueBoss(x, y);
			break;
		case "YellowBoss" :
			out = new YellowBoss(x, y);
			break;
		}
		return out;
	}
	public void draw(Graphics g){
		g.setColor(new Color(58,58,58));
		for(int i = 0; i < bgNumRepeat; i++)
			background.draw((int)cameraBox.getOffsetX()+100*i + bgOffsetX,(int)cameraBox.getOffsetY()-176);
		map.getMap().render((int)cameraBox.getOffsetX(),(int)cameraBox.getOffsetY());
		cameraBox.draw(g);
		g.setColor(Color.white);
		for(Characters guy:GameConstants.enemies){
			if(guy.shouldDisplay()){
				guy.draw(g);
				//g.drawLine(guy.getCenterX()-guy.getWidth()/2-Math.abs(player.getRange()),guy.getCenterY(),guy.getCenterX()+guy.getWidth()/2+Math.abs(player.getRange()),guy.getCenterY());
			}
		}
		for(MovingTile t : GameConstants.platforms)
			t.draw(g);
		for(MotionSensor m : GameConstants.sensors)
			m.draw(g);
		for(Pillar p : GameConstants.pillars)
			p.draw(g);
		//for(GameObject go : GameConstants.collidableObjects)
		//	g.draw(go);
		//for(Trigger t : GameConstants.triggers)
		//g.draw(new Rectangle(t.getX(), t.getY(), t.getWidth(), t.getHeight()));
		for(InteractiveObject io : GameConstants.interacts)
			io.draw(g);
		for(int i=1;i<=GameConstants.playerMaxHealth;i++){
			if(i<=player.getHealth())
				g.setColor(Color.red);
			else
				g.setColor(Color.gray);
			g.fillRect(i*40-24-(int)cameraBox.getOffsetX(), 554, 32, 32);
		}
		if(transState != 2&&player.shouldDisplay()){
			player.draw(g);
		}
		try {
			text.draw(g,-(int)cameraBox.getOffsetX(),-(int)cameraBox.getOffsetY());
		} catch (SlickException e) {
			e.printStackTrace();
		}
		if(questions.getAnswering() == true){
			questions.draw(g,-(int)cameraBox.getOffsetX(),-(int)cameraBox.getOffsetY());
		}
		if(transState != 0) {
			g.setColor(new Color(0, 0, 0, 1f-(transTime/(float)transLength)));
			g.fillRect(0, 0, 100000, 100000);
		}
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
		if(GameConstants.getPaused() == true){
			pauseWindow.draw(g,-(int)cameraBox.getOffsetX(),-(int)cameraBox.getOffsetY());
		}
	}
	public void setBackgroundInfo(int offset, int numRepeat){
		bgNumRepeat = numRepeat;
		bgOffsetX = offset;
	}
	public void changeText(String textChoice){
		String textString = null;
		switch(textChoice){
		case "intro":
			textString = "I saw nothing around me but darkness. " +
					"I try to recall the events prior, but every strand of thought escapes my grasps. " +
					"I know nothing except that I must move forward." +
					"                                       ";
			break;
		case "firstEnemy":
			textString = "As I come into the bright light, I see something ahead. " +
					"A furious rage builds up inside me. I want to use [SPACE] to defeat" +
					" this enemy. " +
					"                                       ";
			break;
		case "firstTechnology":
			textString = "A strange piece of technology stands before of me. Although it seems alien to me, a voice " +
					"inside me tells me to use [SPACE] to active the platform ahead. I feeled rushed" +
					" for time but luckly I can press [ESC] to pause." +
					"                                       ";
			break;
		case "firstQuestion":
			textString = "After clearing the ledge, I am filled with a strong sense of achievement." +
					"  The blur in my mind begins to clear up a bit. " +
					"But this feeling of accomplishment is quickly drowned out by the pangs of regret. " +
					" Did I have to defeat that enemy? " +
					"Perhaps I should refrain from physical conflicts in the future." +
					"                                       ";
			break;
		case "firstJump":
			textString = "Almost effortlessly, I make each jump. " +
					"These jumps almost seemed familiar to me. " +
					"Like I had done them countless times in the past." +
					"                                       ";
			break;
		case "longJump":
			textString = "The next jump is longer and wider than the previous.  I want to turn back." +
					" But a relenteless force drives me to push forward" +
					"  Clearing this jump requires a leap of faith." +
					"                                       ";
			break;
		case "tutorialEnd":
			textString = "Almost in disbelief, I look down at my suit" +
					" and see a badge.  EMPLOYEE NAME: it says. However the text below it seems to " +
					"have been intentionally scratched out. " +
					"So many questions filled my mind, but all the answers lie ahead. " +
					"                                       ";
			break;
		case "elevatorLevelStart":
			textString = "The elevator feels like it has brought me up a few floors. " +
					"My head begins to spin.  I have so many questions.  " +
					"Where am I? Who am I? Why am I here?" +
					" All I want were the answers.  ...   ...   ..." +
					"                                       ";
			break;
		case "anotherEnemy":
			textString = "There is a key up above, but another one of those enemies blocks my path.  " +
					"Something tells me I don't need the key, but I desperately want it anyways. Perhaps" +
					" this time I should use the [UP] arrow key to jump over the enemy without harm." +
					"                                       ";
			break;
		case "lockedElevator":
			textString = "Locked!? It seems like that key would be useful here. But I wonder about the " +
					" technology beyond the elevator..." +
					"                                       ";
			break;
		case "motionLevelStart":
			textString = "The elevator brings me up another few floors. The haze in my mind" +
					" seems to have cleared up a bit.  My name   ...   Alex Wang.  My job   ...   " +
					"...   ...   ...   ...   nothing. Hopefully, more will clear up as time progresses." +
					"                                       ";
			break;
		case "turnOffSensor":
			textString = "It seems really dangerous up ahead, but something tells me whats beyond will pay off." +
					" It is either this or the motion sensors   ...   " +
					"                                       ";
			break;
		case "accomplishment":
			textString = "I feel proud and accomplished having conquered the motion sensors." +
					"                                       ";
			break;
		case "beatMotionLevel":
			textString = "Something clicks inside my head as I see the elevator." +
					"  There I felt something up ahead. I have to keep going.  I have to" +
					" find the answers.  " +
					"                                       ";
			break;
		case "gravityLevelStart":
			textString = "Rising higher and higher in the buildling, I almost feel anxious. " +
					"My questions will soon be answered.  I can feel it.  " +
					"                                       ";
			break;
		case "flippedGrav":
			textString = "A feeling of complete exhiliration comes over me.  All the enemies are on the other" +
					" side of that wall. I can almost feel their desperation as " +
					" they attempt to reach me.  I smile at my superior intellect." +
					"                                       ";
			break;
		case "beforeBoss":
			textString = "This is it   ...   I can feel it. Whatever is beyond that elevator calls to me.  " +
					" The answers lie ahead   ...   ...   ...   ...   ..." +
					"                                       ";
			break;
		case "notBlackBoss":
			textString = "But there was no answer, this only" +
					" seemed to infuriate the figure...   ...   ...   ...   ...   " +
					"                                       ";
			break;
		case "blackBoss":
			textString = "...   ...   ...   ...   ...   ...   ...   ...   ...   ...   ...   ...   ...    " +
					"A figure stands before me.  It copies my every move down to a wire. " +
					"Is it a friend? A foe? I ask it what it is, and it replies in its saccharine tone.                    " +
					"                  \"I am Perfection\"" +
					"                                                                              ";
			break;
		}
		text.setText(textString);
		text.restart();
	}
}
