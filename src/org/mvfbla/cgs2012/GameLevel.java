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
	private byte transState = 0;
	public long transLength = 1200;
	public Elevator elevator;
	private String textChoice = " ";

	public void initStuff() throws SlickException {
		GameConstants.clear();
		GameConstants.currMap = map;
		GameConstants.collidableObjects.addAll(map.getBoxes());
		GameConstants.platforms = new ArrayList<MovingTile>();
		questions = new QuestionWindow();
		text = new TypeWriter();
		done = false;
		int motionDelay = 0;
		for(final TiledObject to : map.getObjects()) {
			if(to.getType().equals("spawn")){
				GameConstants.enemies.add(enemyFromName(to.getProperty("var"), to.getX(), to.getY()));
			}
			if(to.getType().equals("BlackBoss"))
				GameConstants.enemies.add(new BlackBoss(to.getX(), to.getY()));
			if(to.getType().equals("movingPlatform")) {
				MovingTile t = new MovingTile(to.getX(), to.getY(), to.getWidth(), to.getHeight(), to.getProperty("var"), to.getProperty("image"));
				GameConstants.platforms.add(t);
				GameConstants.collidableObjects.add(t);
			}
			if(to.getType().equals("trigger")) {
				Trigger t = new Trigger(to, new TriggerListener() {
					@Override
					public void triggered(GameObject src) {}
					@Override
					public void onEnter(GameObject src) {
						textChoice = to.getProperty("var");
						switch(textChoice){
							case "intro":
								text.setText("I awoke to find myself in a sea of darkness. " +
											 "I tried to recall the events prior, but every strand of thought escaped my grasps. " +
											 "I knew nothing except that I must move forward." +
											 "                                       ");
								text.restart();
							break;
							case "firstEnemy":
								text.setText("As I came into the bright light, I saw something ahead. " +
											 "A furious rage built up inside me. Perhaps I should use [SPACE] to defeat" +
											 " this enemy. " +
											 "                                       ");
								text.restart();
								break;
							case "firstQuestion":
								text.setText("After clearing the ledge, I was filled with a strong sense of achievement." +
											 "  The blur in my mind began to clear up a bit. " +
											 "My feeling of accomplishment was quickly drowned out by the pangs of regret. " +
											 " Did I have to defeat that enemy? " +
											 "Perhaps I should refrain from physical conflicts in the future.                    ");
								text.restart();
								break;
							case "longJump":
								text.setText("This jump was longer and wider than the previous.  I wanted to turn back." +
											 " But a relenteless driving force drove me to push forward" +
											 "  Clearing this jump required a leap of faith." +
											 "                                       ");
								text.restart();
								break;
							case "tutorialEnd":
								text.setText("Almost in disbelief, I looked down at my suit" +
											 " and saw a badge.  EMPLOYEE NAME: it said. However the text below it seemed to " +
											 "have been intentionally scratched out. " +
											 "So many questions filled my mind, but all the answers lie ahead. " +
											 "                                      ");
								text.restart();
								break;
						}
					}
					@Override
					public void onExit(GameObject src) {}
				});
				GameConstants.triggers.add(t);
			}
			if(to.getType().equals("platformButton")) {
				Button b = new Button(to.getX(), to.getY(), new PlatformListener());
				GameConstants.interacts.add(b);
			}
			if(to.getType().equals("gravityButton")) {
				Button b = new Button(to.getX(), to.getY(), new GravityListener());
				GameConstants.interacts.add(b);
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
			if(to.getType().equals("key")) {
				Key key = new Key(to, this);
				GameConstants.interacts.add(key);
			}
			if(to.getType().equals("pillar")){
				Pillar pillar = new Pillar(to.getX(),to.getY(),48,224);
				GameConstants.pillars.add(pillar);
			}
			if(to.getType().equals("finish")) {
				Elevator e = new Elevator(to.getX(), to.getY(), this);
				GameConstants.interacts.add(e);
				elevator = e;
			}
		}
		background = new Image("data\\Background.png");
		transState = 1;
	}
	public class GravityListener implements ButtonListener{
		@Override
		public void buttonPressed(boolean state){
			//player.rotateAnimation();
			GameConstants.flipGrav();
		}
	}
	public class PlatformListener implements ButtonListener {
		@Override
		public void buttonPressed(boolean state) {
			GameConstants.platforms.get(0).setOn(state);
		}
	}
	public class MotionButtonListener implements ButtonListener {
		@Override
		public void buttonPressed(boolean state) {
			if(state)
				for(MotionSensor ms : GameConstants.sensors)
					ms.setState((byte) 0);
			else
				for(MotionSensor ms : GameConstants.sensors)
					ms.setState((byte) 1);
		}
	}
	public void unlockElev() {}
	public void updateMain(GameContainer container, StateBasedGame sbg,int delta) {
		if(transState == 1) {
			transTime += delta;
			if(transTime >= transLength) {
				transState = 0;
				transTime = 2*transLength;
			}
		} else if(transState == 2) {
			transTime -= delta;
			if(transTime <= 0) {
				sbg.enterState(stateID + 1);
			}
		}
		if(done && questions.getAnswering() == false && stateID != 8) {
			player.setHealth(0);
			transState = 2;
			//sbg.enterState(stateID + 1);
		}
		questions.update(container);
		if(!lost)
			player.update(container, delta);
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
					if(tempX>0)
						hit+=16;
				}
				else{
					if(Math.abs(tempX)<40)
						player.setHealth(player.getHealth()-1);
				}
			}
			if(tempX>0)
				hit+=(guy.getWidth());
			else
				hit+=(guy.getWidth()/8);
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
				if(Math.abs(tempX)<Math.abs(player.getRange()+hit))
					guy.setHealth(guy.getHealth()-1);
			}
			/*if(!player.isAlive()){
				System.out.println("GG");
			}*/
			text.update(container,delta);
		}
		for(MovingTile t : GameConstants.platforms)
			t.update(container, delta);
		for(MotionSensor m : GameConstants.sensors)
			m.update(container, delta);
		cameraBox.update(container, delta);

		//testing
		Input input = container.getInput();
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
		//g.drawRect(player.getX(),player.getY(),player.getWidth(),player.getHeight());
		//g.drawRect(cameraBox.getX(),cameraBox.getY(),cameraBox.getWidth(),cameraBox.getHeight());
		for(Characters guy:GameConstants.enemies){
			if(guy.shouldDisplay())
				guy.draw(g);
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
		//g.draw(cameraBox);
		//g.draw(player.getCollision());
		for(int i=1;i<=3;i++){
			if(i<=player.getHealth())
				g.setColor(Color.red);
			else
				g.setColor(Color.gray);
			g.fillRect(i*40-24-(int)cameraBox.getOffsetX(), 554, 32, 32);
		}
		if(questions.getAnswering() == true){
			questions.draw(g,-(int)cameraBox.getOffsetX(),-(int)cameraBox.getOffsetY());
		}
		if(transState != 2&&player.shouldDisplay())
			player.draw(g);
		if(transState != 0) {
			g.setColor(new Color(0, 0, 0, 1f-(transTime/(float)transLength)));
			g.fillRect(0, 0, 100000, 100000);
		}
		try {
			text.draw(g,-(int)cameraBox.getOffsetX(),-(int)cameraBox.getOffsetY());
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	public void setBackgroundInfo(int offset, int numRepeat){
		bgNumRepeat = numRepeat;
		bgOffsetX = offset;
	}
}
