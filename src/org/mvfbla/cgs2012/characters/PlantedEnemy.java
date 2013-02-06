//PlantedEnemy class, extends Enemy
//same as BasicEnemy, except stands still on guard
//when player is within range, it will chase the player
//chasing speed is faster than BasicEnemy
package org.mvfbla.cgs2012.characters;

import org.mvfbla.cgs2012.GameConstants;
import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/**
 * @author Young
 * Planted Enemy moves towards player if the player gets within the sight range
 */
public class PlantedEnemy extends Enemy{
	private boolean awake = false; //when player is not in range, PlantedEnemy is "asleep"
	private float sight = 150; //range for PlantedEnemy to see player
	private String current;

	/**
	 * @param x - x position
	 * @param y - y position
	 * @throws SlickException
	 */
	public PlantedEnemy (int x, int y) throws SlickException{
		super(x, y, 64, 64);
		addAnimation("PlantedEnemyInvert", new Animation(new SpriteSheet("data\\CharAnim\\SmallEnemyInverted.png", 64, 64), 150));
		addAnimation("PlantedEnemy", new Animation(new SpriteSheet("data\\CharAnim\\SmallEnemy.png", 64, 64), 150));
		current = "PlantedEnemy";
		super.setSpeed(0);
	}
	/**
	 * @param inSight - whether or not the player is in the enemies sight range
	 */
	public void changeSleep(boolean inSight){ //changes sleep state
		awake=inSight;
	}
	@Override
	public void draw(Graphics g){
		//g.drawOval(this.getCenterX()-sight, this.getCenterY()-sight, sight*2, sight*2);
		super.draw(g);
	}
	/**
	 * @return - returns the size of the enemies sight range
	 */
	public float getSight(){ //returns range of sight
		return sight;
	}
	/**
	 * @param size - sets the size of the enemies sight range
	 */
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
