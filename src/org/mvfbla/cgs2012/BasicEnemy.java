package org.mvfbla.cgs2012;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class BasicEnemy extends Enemy{
	public BasicEnemy (int x, int y) throws SlickException{
		super(x, y, 64, 64);
		addAnimation("BasicEnemyInvert", new Animation(new SpriteSheet("data\\SmallEnemyInverted.png", 64, 64), 150));
		addAnimation("BasicEnemy", new Animation(new SpriteSheet("data\\SmallEnemy.png", 64, 64), 150));
		super.setSpeed(-2);
	}
	@Override
	public void update(GameContainer gc, int delta){
		super.update(gc, delta);
		if(GameConstants.getGrav() > 0)
			playAnimation("BasicEnemy");
		else
			playAnimation("BasicEnemyInvert");
	}
}
