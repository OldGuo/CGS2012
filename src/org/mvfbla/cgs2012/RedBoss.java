package org.mvfbla.cgs2012;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class RedBoss extends Boss{
	private boolean attacking;

	public RedBoss (int x, int y) throws SlickException{
		super(x,y);
		addAnimation("RedBoss", new Animation(new SpriteSheet("data\\RedBossWalking.png", 128, 128), 150));
		super.setSpeed(3.7f);
	}
	@Override
	public void update(GameContainer gc, int delta){
		if(!died && !isAlive()) {
			GameConstants.playerMaxHealth++;
		}
		if(attacking)
			super.update(gc, delta);
	}
	public void setAttacking(boolean a){
		attacking = a;
	}
	public boolean getAttacking(){
		return attacking;
	}
}
//nerf this guy
