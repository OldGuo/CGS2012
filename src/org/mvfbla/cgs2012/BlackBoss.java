package org.mvfbla.cgs2012;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class BlackBoss extends Boss{
	public BlackBoss (int x, int y) throws SlickException{
		super(x,y);
		//addAnimation("BlackBoss", new Animation(new SpriteSheet("data\\BlackBossWalking.png", 64, 64), 150));
	}
	@Override
	public void update(GameContainer gc, int delta){
		super.update(gc, delta);
	}
}