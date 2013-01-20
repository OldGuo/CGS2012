package org.mvfbla.cgs2012;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class BlueBoss extends Boss{
	public BlueBoss (int x, int y) throws SlickException{
		super(x,y);
		addAnimation("BlueBoss", new Animation(new SpriteSheet("data\\BlueBossWalking.png", 256, 256), 150));
		super.setHealth(1);
		super.setSpeed(0);
	}
	@Override
	public void update(GameContainer gc, int delta){
		super.update(gc, delta);
	}

}
