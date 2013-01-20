package org.mvfbla.cgs2012;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Enemy extends Character{;
	private float speed, direction;
	public Enemy (int x, int y, int width, int height) throws SlickException{
		super(x, y, width, height);
		speed=direction=-1;
	}
	@Override
	public void update(GameContainer gc, int delta){
		if(super.isAlive()){
			if(Math.abs(this.getVelX())<Math.abs(speed)){
				direction*=-1;
					this.setVelX(direction*speed);
			}//if something reduces its speed (collision), change direction
			super.update(gc, delta);
		}
		else{
			super.resetAnimation();
		}
	}
	public float getSpeed(){
		return speed;
	}
	public void setSpeed(float howFast){
		speed=howFast;
		this.setVelX(speed);
	}
	public float getDirection(){
		return direction;
	}
	public void setDirection(float whichWay){
		direction=whichWay;
		if(Math.signum(speed)!=whichWay)
			speed*=-1;
	}
}
