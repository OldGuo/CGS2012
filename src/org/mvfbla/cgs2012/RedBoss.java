package org.mvfbla.cgs2012;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class RedBoss extends Boss{
	public RedBoss (int x, int y) throws SlickException{
		super(x,y);
		addAnimation("RedBoss", new Animation(new SpriteSheet("data\\RedBossWalking.png", 256, 256), 150));
		super.setSpeed(10);
	}
	@Override
	public void update(GameContainer gc, int delta){
		super.update(gc, delta);
	}
}