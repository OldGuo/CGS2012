//PlantedEnemy class, extends Enemy
//same as BasicEnemy, except stands still on guard
//when player is within range, it will chase the player
//chasing speed is faster than BasicEnemy
package org.mvfbla.cgs2012;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class PlantedEnemy extends Enemy{
	private boolean awake = false; //when player is not in range, PlantedEnemy is "asleep"
	private float sight = 150; //range for PlantedEnemy to see player
	private String current;

	public PlantedEnemy (int x, int y) throws SlickException{
		super(x, y, 64, 64);
		addAnimation("PlantedEnemyInvert", new Animation(new SpriteSheet("data\\SmallEnemyInverted.png", 64, 64), 150));
		addAnimation("PlantedEnemy", new Animation(new SpriteSheet("data\\SmallEnemy.png", 64, 64), 150));
		current = "PlantedEnemy";
		super.setSpeed(0);
	}
	public void changeSleep(boolean inSight){ //changes sleep state
		awake=inSight;
	}
	@Override
	public void draw(Graphics g){
		//g.drawOval(this.getCenterX()-sight, this.getCenterY()-sight, sight*2, sight*2);
		super.draw(g);
	}
	public float getSight(){ //returns range of sight
		return sight;
	}
	public void setSight(int size){ //sets range of sight
		sight = size;
	}
	@Override
	public void update(GameContainer gc, int delta){
		super.playAnimation(current);

		if(GameConstants.getGrav() > 0) //flips for gravity level
			current = "PlantedEnemy";
		else
			current = "PlantedEnemyInvert";

		if(!awake){ // stays still when sleeping
			super.resetAnimation();
			super.setSpeed(0);
			super.setDirection(0);
		}else{ //chases player when awake
			super.setSpeed(3*super.getEnemyDirection());
		}
		super.update(gc, delta);
	}
}
