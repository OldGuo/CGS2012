//BlueBoss class, extends Boss
//jumps and stomps on the ground, causing explosions
//explosions will destroy pillars
package org.mvfbla.cgs2012;


import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class BlueBoss extends Boss{
	private boolean awake = false;
	private final float sight = 1000;
	private final int ATTACK_DELAY = 2500;
	private int time = 1000;
	//private int time = ATTACK_DELAY;
	private boolean stomping = false;
	private int count;
	private int stompX,stompY;
	private final Image stomp;
	private boolean falling;
	private final long animLength = 500;
	private long animTime = -1;
	private boolean attacking;

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
		if(attacking){
			super.update(gc, delta);
			time -= delta;
			if(time <= 0 && !falling){
				stomp();
				time = ATTACK_DELAY;
			}
			if(stomping && super.getVelY() == 0){
				stompX = (int) super.getX() + 32;
				stompY =  (int) super.getY() + 320;
				animTime = 0;
				stomping = false;
			}else{
				if(animTime < 0) {
					stompX = -100;
					stompY = -100;
				}
			}
			if(getY() > 400 && isAlive()){
				setHealth(getHealth() - 1);
			}
			if(animTime >= 0)
				animTime+=delta;
			if(animTime >= animLength)
				animTime = -1;
		}if(!attacking){
			super.setHealth(3);
		}
	}
	@Override
	public void draw(Graphics g){
		super.draw(g);
		g.drawOval(this.getCenterX()-sight, this.getCenterY()-sight, sight*2, sight*2);
		//g.fillRect(stompX, stompY, 64, 64);
		if(animTime >= 0) {
			float prog = animTime/(float)animLength;
			float scale = prog+1f;
			Color alpha = new Color(Color.white);
			alpha.a = 1-prog;
			int x = (int) (stompX-(((stomp.getWidth()*scale)-stomp.getWidth())/2));
			int y = (int) (stompY-(((stomp.getHeight()*scale)-stomp.getHeight())/2));
			stomp.getScaledCopy(scale).draw(x,y, alpha);
		}
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
	public void setAttacking(boolean a){
		attacking = a;
	}
	public boolean getAttacking(){
		return attacking;
	}
}
