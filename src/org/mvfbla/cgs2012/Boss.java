package org.mvfbla.cgs2012;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Boss extends Enemy{
	public Boss (int x, int y) throws SlickException{
		super(x,y,256,256);
		super.setHealth(3);
	}
	@Override
	public void update(GameContainer gc, int delta){
		super.update(gc, delta);
	}
}