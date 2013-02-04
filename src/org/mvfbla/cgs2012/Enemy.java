package org.mvfbla.cgs2012;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

public class Enemy extends Characters{;
	private float speed, direction;
	private float lastX;
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
				GameConstants.enemiesKilled++;
			died = true;
			super.resetAnimation();
			super.stopAnimation();
			super.update(gc, delta);
		}
	}
	public float getSpeed(){
		return speed;
	}
	public void setSpeed(float howFast){
		speed=howFast;
		this.setVelX(speed);
	}
	public float getEnemyDirection(){
		return direction;
	}
	public void setDirection(float whichWay){
		direction=whichWay;
		if(Math.signum(speed)!=whichWay)
			speed*=-1;
	}
}
