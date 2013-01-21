package org.mvfbla.cgs2012;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class BlueBoss extends Boss{
	public BlueBoss (int x, int y) throws SlickException{
		super(x,y);
		super.setHealth(1);
		super.setSpeed(0);
		addAnimation("BlueBoss", new Animation(new SpriteSheet("data\\BlueBossWalking.png", 128, 128), 150));
	}
	@Override
	public void update(GameContainer gc, int delta){
		super.update(gc, delta);
	}

}
