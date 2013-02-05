package org.mvfbla.cgs2012;

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

	@Override
	public void init(GameContainer container,StateBasedGame sbg) throws SlickException {
		super.setBackgroundInfo(33, 8);
		player = new Player(300, 496);
		map = new Map("data\\Maps\\YellowBossLevel_5.tmx","data\\Maps");
		yellowBoss = new YellowBoss(330,100);
		cameraBox = new CameraObject(player,2000,1000);
		background = new Image("data\\Background.png");
	}

	@Override
	public void update(GameContainer container, StateBasedGame sbg,int delta) throws SlickException {
		updateMain(container, sbg, delta);
		if(!yellowBoss.isAlive()) {
			yellowBoss.aiming = yellowBoss.charging = yellowBoss.firing = yellowBoss.teleporting = false;
			transState = 2;
		}
		if(yellowBoss.isAiming())
			yellowBoss.setReticle(player.getX());
		else if(yellowBoss.isFiring()) {
			if(player.getCenterX()>=fireX&&player.getCenterX()<=fireX+yellowBoss.getReticleWidth()){
				if(player.getCenterY()>=fireY&&player.getCenterY()<=fireY+yellowBoss.getReticleWidth())
					player.setHealth(player.getHealth()-1);
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
			g.fillRect(96,288,128,32);
			buttons[0].setStateNum(2);
		} else {
			handleButton(0);
		}
		if(yellowBoss.isActivated(1)){
			g.fillRect(320,224,144,32);
			buttons[1].setStateNum(2);
		} else {
			handleButton(1);
		}
		if(yellowBoss.isActivated(2)){
			g.fillRect(560,288,128,32);
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
