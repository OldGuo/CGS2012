package org.mvfbla.cgs2012;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class YellowBoss extends Boss{
	public YellowBoss (int x, int y) throws SlickException{
		super(x,y);
		addAnimation("YellowBoss", new Animation(new SpriteSheet("data\\YellowBossWalking.png", 128, 128), 150));
	}
	@Override
	public void update(GameContainer gc, int delta){
		super.update(gc, delta);
	}
}
