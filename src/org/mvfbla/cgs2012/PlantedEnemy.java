package org.mvfbla.cgs2012;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class PlantedEnemy extends Enemy{
	private boolean awake = false;
	private float sight = 150;
	private String current;

	public PlantedEnemy (int x, int y) throws SlickException{
		super(x, y, 64, 64);
		addAnimation("PlantedEnemyInvert", new Animation(new SpriteSheet("data\\SmallEnemyInverted.png", 64, 64), 150));
		addAnimation("PlantedEnemy", new Animation(new SpriteSheet("data\\SmallEnemy.png", 64, 64), 150));
		current = "PlantedEnemy";
		super.setSpeed(0);
	}
	@Override
	public void update(GameContainer gc, int delta){
		super.playAnimation(current);
		if(GameConstants.getGrav() > 0)
			current = "PlantedEnemy";
		else
			current = "PlantedEnemyInvert";

		if(!awake){
			super.resetAnimation();
			super.setSpeed(0);
			super.setDirection(0);
		}else{
			super.setSpeed(3*super.getEnemyDirection());
		}
		super.update(gc, delta);
	}
	@Override
	public void draw(Graphics g){
		g.drawOval(this.getCenterX()-sight, this.getCenterY()-sight, sight*2, sight*2);
		super.draw(g);
	}
	public void changeSleep(boolean inSight){
		awake=inSight;
	}
	public float getSight(){
		return sight;
	}
	public void setSight(int size){
		sight = size;
	}
}
