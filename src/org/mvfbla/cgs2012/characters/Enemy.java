//Enemy class, extends Characters
//subclasses: Boss, BasicEnemy, BiggerEnemy, PlantedEnemy
package org.mvfbla.cgs2012.characters;

import org.mvfbla.cgs2012.utils.GameConstants;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

/**
 * @author Young
 * Enemies
 */
public class Enemy extends Characters{;
	private float speed, direction; //direction is -1 for facing left, 1 for facing right
	private float lastX; //last x location enemy was in
	public boolean died = false;
	/**
	 * @param x - x position
	 * @param y - y position
	 * @param width - width
	 * @param height - height
	 * @throws SlickException
	 */
	public Enemy (int x, int y, int width, int height) throws SlickException{
		super(x, y, width, height);
		speed=direction=-1;
	}
	/**
	 * @return - direction of the enemy
	 */
	public float getEnemyDirection(){ //returns the enemy's direction
		return direction;
	}
	/**
	 * @return - speed of the enemy
	 */
	public float getSpeed(){ //returns the speed of the enemy
		return speed;
	}
	/**
	 * @param whichWay - direction the enemy is facing
	 */
	public void setDirection(float whichWay){// sets the enemy's direction
		direction=whichWay;
		if(Math.signum(speed)!=whichWay)
			speed*=-1;
	}
	/**
	 * @param howFast - sets the speed of the enemy
	 */
	public void setSpeed(float howFast){ //sets the speed of the enemy
		speed=howFast;
		this.setVelX(speed);
	}
	@Override
	public void update(GameContainer gc, int delta){
		if(super.isAlive()){
			if(lastX == getX()){
				direction*=-1;
				this.setVelX(direction*speed);
			}//if something reduces its speed (collision), change direction
			lastX = getX();
			super.update(gc, delta);
		} else {
			if(!died)
				GameConstants.enemiesKilled++; //counts the number of enemies killed
			died = true;
			super.resetAnimation(); //stops the enemy from moving when dead
			super.stopAnimation();
			super.update(gc, delta);
		}
	}
}
