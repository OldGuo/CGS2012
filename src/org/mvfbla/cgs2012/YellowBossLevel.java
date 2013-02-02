package org.mvfbla.cgs2012;

import org.mvfbla.cgs2012.GameLevel.GravityListener;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.Color;

public class YellowBossLevel extends GameLevel {


	public YellowBossLevel(int stateID) {
		this.stateID = stateID;
		// TODO Auto-generated constructor stub
	}

	private YellowBoss yellowBoss;
	private final static int MAP_WIDTH = 780;
	private final static int MAP_HEIGHT = 600;
	private float fireX,fireY;


	@Override
	public void init(GameContainer container,StateBasedGame sbg) throws SlickException {
		super.setBackgroundInfo(33, 8);
		player = new Player(300, 496);
		map = new Map("data\\Maps\\YellowBossLevel_5.tmx","data\\Maps");
		yellowBoss = new YellowBoss(330,100);
		cameraBox = new CameraObject(player,250,1000);
		background = new Image("data\\Background.png");
	}

	@Override
	public void update(GameContainer container, StateBasedGame sbg,int delta) throws SlickException {
		updateMain(container, sbg, delta);
		if(yellowBoss.isAiming())
			yellowBoss.setReticle(player.getX());
		else if(yellowBoss.isFiring()){
			if(player.getCenterX()>=fireX&&player.getCenterX()<=fireX+yellowBoss.getReticleWidth()){
				if(player.getCenterY()>=fireY&&player.getCenterY()<=fireY+yellowBoss.getReticleWidth())
					player.setHealth(player.getHealth()-1);
			}
		}
	}
	public class yellowBossListener implements ButtonListener{
		int number;
		public yellowBossListener(int platform){
			number=platform;
		}
		@Override
		public void buttonPressed(boolean state) {
			((YellowBoss)yellowBoss).activate(number,state);
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
		Button b1 = new Button(125,500, new yellowBossListener(0));
		Button b2 = new Button(360,500, new yellowBossListener(1));
		Button b3 = new Button(595,500, new yellowBossListener(2));
		GameConstants.interacts.add(b1);
		GameConstants.interacts.add(b2);
		GameConstants.interacts.add(b3);
	}
	@Override
	public void leave(GameContainer container, StateBasedGame stateBasedGame) throws SlickException {
		System.out.println("Leaving state " + getID());
	}
}
