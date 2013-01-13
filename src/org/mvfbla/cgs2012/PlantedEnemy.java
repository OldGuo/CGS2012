package org.mvfbla.cgs2012;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

private boolean awake = false;
public class PlantedEnemy extends Enemy{
	public PlantedEnemy (int x, int y) throws SlickException{
		super(x, y, 30, 64);
		addAnimation("PlantedEnemy", new Animation(new SpriteSheet("data\\SmallEnemy.png", 32, 32), 150));
		super.setSpeed(0);
	}
	@Override
	public void update(GameContainer gc, int delta){
		super.stopAnimation();
		super.update(gc, delta);
	}
}
