//Enemy class, extends Characters
//subclasses: Boss, BasicEnemy, BiggerEnemy, PlantedEnemy
package org.mvfbla.cgs2012;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

public class Enemy extends Characters{;
	private float speed, direction; //direction is -1 for facing left, 1 for facing right
	private float lastX; //last x location enemy was in
	public boolean died = false;
	public Enemy (int x, int y, int width, int height) throws SlickException{
		super(x, y, width, height);
		speed=direction=-1;
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
	/**
	 * @return the speed of the enemy
	 */
	public float getSpeed(){
		return speed;
	}
	/**
	 * @param howFast sets the speed of the enemy
	 */
	public void setSpeed(float howFast){
		speed=howFast;
		this.setVelX(speed);
	}
	/**
	 * @return the direction the enemy is facing
	 */
	public float getEnemyDirection(){
		return direction;
	}
	/**
	 * @param whichWay sets the direction the enemy is facing
	 */
	public void setDirection(float whichWay){
		direction=whichWay;
		if(Math.signum(speed)!=whichWay)
			speed*=-1;
	}
}
