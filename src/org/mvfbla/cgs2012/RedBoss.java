// RedBoss class, extends Boss
// moves faster than previous enemies, takes 3 hits to defeat
package org.mvfbla.cgs2012;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class RedBoss extends Boss{
	private boolean attacking; //value for when questions are finished and the boss begins to attack

	public RedBoss (int x, int y) throws SlickException{
		super(x,y);
		addAnimation("RedBoss", new Animation(new SpriteSheet("data\\RedBossWalking.png", 128, 128), 150));
		super.setSpeed(3.7f); //faster speed
	}
	public boolean getAttacking(){ //returns whether or not it's attacking
		return attacking;
	}
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
