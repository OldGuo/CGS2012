//BlueBoss class, extends Boss
//jumps and stomps on the ground, causing explosions
//explosions will destroy pillars
//when all 3 pillars are destroyed, boss is defeated
package org.mvfbla.cgs2012.characters;


import org.mvfbla.cgs2012.utils.GameConstants;
import org.mvfbla.cgs2012.utils.Vector;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/**
 * @author Young
 * Blue Boss
 */
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

	/**
	 * @param x - initial x position
	 * @param y - initial y position
	 * @throws SlickException
	 */
	public BlueBoss (int x, int y) throws SlickException{
		super(x,y);
		super.setInitialHealth(4);
		addAnimation("BlueBoss", new Animation(new SpriteSheet("data\\CharAnim\\BlueBossWalking.png", 128, 128), 150));
		stomp = new Image("data\\CharAnim\\Stomp.png");
	}
	/**
	 * @param inSight - If the player is within the bosses sight range
	 */
	public void changeSleep(boolean inSight){ //taken from PlantedEnemy class to follow player
		awake=inSight;
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
			int x = (int) (stompX-(((stomp.getWidth()*scale)-stomp.getWidth())/2)); //this animates the explosion
			int y = (int) (stompY-(((stomp.getHeight()*scale)-stomp.getHeight())/2));
			stomp.getScaledCopy(scale).draw(x,y, alpha);
		}
	}
	/**
	 * @return - Whether the boss is attacking or not
	 */
	public boolean getAttacking(){
		return attacking;
	}
	/**
	 * @return - Whether the boss is falling
	 */
	public boolean getFalling(){
		return falling;
	}
	/**
	 * @return - The sight range of the Blue Boss
	 */
	public float getSight(){
		return sight;
	}
	/**
	 * @return - X position of the boss stomp
	 */
	public int getStompX(){ //coordinates for explosion
		return stompX + 32;
	}
	/**
	 * @return - Y position of the boss stomp
	 */
	public int getStompY(){
		return stompY + 32;
	}
	/**
	 * @param a - sets the boss to either start attacking or stop attacking
	 */
	public void setAttacking(boolean a){
		attacking = a;
	}
	public void setFalling(boolean f){ //when defeated, the platform falls
		falling = f;
	}
	public void stomp(){ //method for stomping
		stomping = true;
		trans = new Vector(0,0);
		super.setVelY(-10);
	}
	@Override
	public void update(GameContainer gc, int delta){
		if(!died && !isAlive()) {
			GameConstants.playerMaxSpeed++; //defeating this boss grants movement speed
		}
		if(attacking){
			super.update(gc, delta);
			time -= delta;
			if(time <= 0 && !falling){ //during this time the boss will stomp
				stomp();
				time = ATTACK_DELAY;
			}
			if(stomping && super.getVelY() == 0){ //this calculates where the explosion will be
				stompX = (int) super.getX() + 32;
				stompY =  (int) super.getY() + 320;
				animTime = 0;
				stomping = false;
			}else{ //when it isn't stomping, the explosion hitbox is placed outside the map
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
}
