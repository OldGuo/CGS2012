package org.mvfbla.cgs2012;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class BlueBoss extends Boss{
	private boolean awake = false;
	private final float sight = 1000;
	private final int ATTACK_DELAY = 2500;
	private int time = 12000;
	//private int time = ATTACK_DELAY;
	private boolean stomping = false;
	private int count;
	private int stompX,stompY;
	private final Image stomp;
	private boolean falling;

	public BlueBoss (int x, int y) throws SlickException{
		super(x,y);
		super.setInitialHealth(4);
		addAnimation("BlueBoss", new Animation(new SpriteSheet("data\\BlueBossWalking.png", 128, 128), 150));
		stomp = new Image("data\\Stomp.png");
	}
	@Override
	public void update(GameContainer gc, int delta){
		if(!died && !isAlive()) {
			GameConstants.playerMaxSpeed++;
		}
		super.update(gc, delta);
		time -= delta;
		if(time <= 0 && !falling){
			stomp();
			time = ATTACK_DELAY;
		}
		if(stomping && super.getVelY() == 0){
			stompX = (int) super.getX() + 32;
			stompY =  (int) super.getY() + 320;
			stomping = false;
		}else{
			stompX = -100;
			stompY = -100;
		}
		if(getY() > 400 && isAlive()){
			setHealth(getHealth() - 1);
		}
	}
	@Override
	public void draw(Graphics g){
		super.draw(g);
		g.drawOval(this.getCenterX()-sight, this.getCenterY()-sight, sight*2, sight*2);
		//g.fillRect(stompX, stompY, 64, 64);
		stomp.draw(stompX,stompY);
	}
	public void stomp(){
		stomping = true;
		trans = new Vector(0,0);
		super.setVelY(-10);
	}
	public void changeSleep(boolean inSight){
		awake=inSight;
	}
	public float getSight(){
		return sight;
	}
	public int getStompX(){
		return stompX + 32;
	}
	public int getStompY(){
		return stompY + 32;
	}
	public void setFalling(boolean f){
		falling = f;
	}
	public boolean getFalling(){
		return falling;
	}
}
