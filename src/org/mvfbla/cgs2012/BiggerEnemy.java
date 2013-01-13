package org.mvfbla.cgs2012;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class BiggerEnemy extends Enemy{
	public BiggerEnemy (int x, int y) throws SlickException{
		super(x, y, 96, 96);
		addAnimation("BiggerEnemy", new Animation(new SpriteSheet("data\\LargeEnemy.png", 96, 96), 150));
		super.setSpeed(-1);
	}
	@Override
	public void update(GameContainer gc, int delta){
		super.update(gc, delta);
	}

}
