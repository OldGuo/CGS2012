// RedBoss class, extends Boss
// moves faster than previous enemies, takes 3 hits to defeat
package org.mvfbla.cgs2012.characters;

import org.mvfbla.cgs2012.GameConstants;
import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/**
 * @author Young
 * Red Boss
 * Turns around upon meeting a wall
 */
public class RedBoss extends Boss{
	private boolean attacking; //value for when questions are finished and the boss begins to attack

	/**
	 * @param x - x position
	 * @param y - y position
	 * @throws SlickException
	 */
	public RedBoss (int x, int y) throws SlickException{
		super(x,y);
		addAnimation("RedBoss", new Animation(new SpriteSheet("data\\CharAnim\\RedBossWalking.png", 128, 128), 150));
		super.setSpeed(3.7f); //faster speed
	}
	/**
	 * @return - whether attacking or not
	 */
	public boolean getAttacking(){ //returns whether or not it's attacking
		return attacking;
	}
	/**
	 * @param a - sets attacking
	 */
	public void setAttacking(boolean a){ //sets attacking
		attacking = a;
	}
	@Override
	public void update(GameContainer gc, int delta){
		if(!died && !isAlive()) {
			GameConstants.playerMaxHealth++; //defeating red boss grants an additional health
		}
		if(attacking) //control statements to define attacking and not attacking
			super.update(gc, delta);
		if(!attacking)
			super.setHealth(3);
	}
}
