package org.mvfbla.cgs2012.levels;

import org.mvfbla.cgs2012.Button;
import org.mvfbla.cgs2012.CameraObject;
import org.mvfbla.cgs2012.Characters;
import org.mvfbla.cgs2012.GameConstants;
import org.mvfbla.cgs2012.GameLevel;
import org.mvfbla.cgs2012.GameObject;
import org.mvfbla.cgs2012.Map;
import org.mvfbla.cgs2012.Player;
import org.mvfbla.cgs2012.QuestionWindow;
import org.mvfbla.cgs2012.TiledObject;
import org.mvfbla.cgs2012.TypeWriter;
import org.mvfbla.cgs2012.YellowBoss;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.state.StateBasedGame;

public class YellowBossLevel extends GameLevel {


	public YellowBossLevel(int stateID) {
		this.stateID = stateID;
	}

	private YellowBoss yellowBoss;
	private final static int MAP_WIDTH = 780;
	private final static int MAP_HEIGHT = 600;
	private float fireX,fireY;
	private final YellowButton[] buttons = new YellowButton[3];
	private Animation lightning;

	private TypeWriter text;
	private QuestionWindow questions;
	private boolean beforeQuestions,needRestart,afterQuestions;

	@Override
	public void init(GameContainer container,StateBasedGame sbg) throws SlickException {
		super.setBackgroundInfo(33, 8);
		beforeQuestions = true;
		player = new Player(300, 496);
		map = new Map("data\\Maps\\YellowBossLevel_5.tmx","data\\Maps");
		yellowBoss = new YellowBoss(330,100);
		cameraBox = new CameraObject(player,2000,1000);
		background = new Image("data\\Background.png");
		lightning = new Animation(new SpriteSheet("data\\Lightning.png", 144, 48), 500);
		lightning.start();
		text = new TypeWriter();
		questions = new QuestionWindow();
	}

	@Override
	public void update(GameContainer container, StateBasedGame sbg,int delta) throws SlickException {
		updateMain(container, sbg, delta);
		if(!yellowBoss.isAlive()) {
			yellowBoss.aiming = yellowBoss.charging = yellowBoss.firing = yellowBoss.teleporting = false;
			transState = 2;
			if((GameConstants.bossesDefeated & 0b001) != 0b001) {
				GameConstants.playNum++;
				System.out.println(GameConstants.playNum);
			}
			GameConstants.lastBoss = 3;
			GameConstants.bossesDefeated |= 0b001;
		}
		if(yellowBoss.isAiming())
			yellowBoss.setReticle(player.getX());
		else if(yellowBoss.isFiring()) {
			if(player.getCenterX()>=fireX&&player.getCenterX()<=fireX+yellowBoss.getReticleWidth()){
				if(player.getCenterY()>=fireY&&player.getCenterY()<=fireY+yellowBoss.getReticleWidth())
					player.setHealth(player.getHealth()-1);
			}
		}
		lightning.update(delta);
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
			questions.update(container);
			GameConstants.level.player.setControl(false);
		}
		for(Characters guy : GameConstants.enemies) {
			String name=guy.getClass().toString();
			if(name.equals("class org.mvfbla.cgs2012.YellowBoss")){
				YellowBoss boss = (YellowBoss)guy;
				if(afterQuestions == true){
					boss.setAttacking(true);
					GameConstants.level.player.setControl(true);
				}
			}
		}
	}
	@Override
	public void render(GameContainer container,StateBasedGame sbg, Graphics g)  {
		draw(g);
	}
	@Override
	public void draw(Graphics g){
		super.draw(g);
		g.setColor(Color.red);
		if(yellowBoss.isAiming()){
			fireX=yellowBoss.getReticle();
			fireY=player.getY();
			g.drawOval(fireX,fireY, yellowBoss.getReticleWidth(), yellowBoss.getReticleWidth());
			g.drawLine(fireX, fireY+yellowBoss.getReticleWidth()/2, fireX+yellowBoss.getReticleWidth(), fireY+yellowBoss.getReticleWidth()/2);
			g.drawLine(fireX+yellowBoss.getReticleWidth()/2, fireY, fireX+yellowBoss.getReticleWidth()/2, fireY+yellowBoss.getReticleWidth());
			g.drawLine(yellowBoss.getCenterX(), yellowBoss.getCenterY()-40, fireX+yellowBoss.getReticleWidth()/2, fireY+player.getHeight()/2);
		}
		else if(yellowBoss.isCharging()){
			g.drawOval(fireX, fireY, yellowBoss.getReticleWidth(), yellowBoss.getReticleWidth());
			g.drawLine(fireX, fireY+yellowBoss.getReticleWidth()/2, fireX+yellowBoss.getReticleWidth(), fireY+yellowBoss.getReticleWidth()/2);
			g.drawLine(fireX+yellowBoss.getReticleWidth()/2, fireY, fireX+yellowBoss.getReticleWidth()/2, fireY+yellowBoss.getReticleWidth());
		}
		else if(yellowBoss.isFiring()){
			for(float i=0;i<=8;i++){
				g.drawLine(yellowBoss.getCenterX()-8, yellowBoss.getCenterY()-40, fireX+i, fireY+player.getHeight());
				g.drawLine(yellowBoss.getCenterX()+8, yellowBoss.getCenterY()-40, fireX+yellowBoss.getReticleWidth()+i, fireY+player.getHeight());
			}
		}
		else if(yellowBoss.isTeleporting()){
			float opacity=(float)((yellowBoss.getTime()-4500)*.001);
			g.setColor(new Color(0,255,0,opacity));
			g.fillOval(yellowBoss.getCenterX()-yellowBoss.getHeight()/2, yellowBoss.getCenterY()-yellowBoss.getWidth()/2, yellowBoss.getHeight(), yellowBoss.getWidth());
		}
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
		player.draw(g);
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
	}
	public void handleButton(int id) {
		if(yellowBoss.location == id)
			buttons[id].setStateNum(1);
		else {
			buttons[id].setStateNum(0);
			buttons[id].notif.playAnimation("near");
		}
	}
	public class YellowButton extends Button {
		public int number;
		private int state;
		protected YellowButton(int x, int y, int num) throws SlickException {
			super(x, y, null);
			number = num;
			addAnimation("broke", new Animation(new SpriteSheet("data\\maps\\ButtonBroke.png", 32, 32), 150));
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
		public int getStateNum() {
			return state;
		}
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
	@Override
	public int getID(){
		return stateID;
	}
	@Override
	public void enter(GameContainer container, StateBasedGame stateBasedGame) throws SlickException {
		System.out.println("Entering state " + getID());
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
	}
	@Override
	public void leave(GameContainer container, StateBasedGame stateBasedGame) throws SlickException {
		System.out.println("Leaving state " + getID());
	}

	@Override
	public void initObject(TiledObject to) throws SlickException {
		// TODO Auto-generated method stub

	}
}
