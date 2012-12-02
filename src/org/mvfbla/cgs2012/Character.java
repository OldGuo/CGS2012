package org.mvfbla.cgs2012;

import org.newdawn.slick.SlickException;

public class Character extends AnimatedObject {

	private float velX, velY;

	public Character(int x, int y, int width, int height, String fileLoc) throws SlickException {
		super(x, y, width, height, fileLoc);
	}
	public int doCollision(GameObject obj) {
		int out = 0;
		if(intersects(obj)) {
			float diff = getWidth() + getHeight();
			boolean vertical = false;
			// Check distance between bottom of the character and top of the object
			if(getMaxY() - obj.getMinY()  < Math.abs(diff) && getMaxY() - obj.getMinY() >= 0) {
				diff = getMaxY() - obj.getMinY();
				vertical = true;
			}
			// Check distance between top of the character and bottom of the object
			if(-(getMinY() - obj.getMaxY())  < Math.abs(diff) && getMinY() - obj.getMaxY() <= 0) {
				diff = getMinY() - obj.getMaxY();
				vertical = true;
			}
			// Check distance between right of the character and left of the object
			if(getMaxX() - obj.getMinX() < Math.abs(diff) && getMaxX() - obj.getMinX() >= 0) {
				diff = getMaxX() - obj.getMinX();
				velX = Math.min(velX, 0);
				vertical = false;
			}
			// Check distance between left of the character and right of the object
			if(-(getMinX() - obj.getMaxX()) < Math.abs(diff) && getMinX() - obj.getMaxX() <= 0) {
				diff = getMinX() - obj.getMaxX();
				velX = Math.max(0, velX);
				vertical = false;
			}
			
			// Shunt the player
			if(vertical) {
				setY(getY()-diff);
				out = diff > 0 ? 1 : 3;
			} else {
				setX(getX()-diff);
				out = diff > 0 ? 4 : 2;
			}
		}
		return out;
	}
	public float getVelX() {
		return velX;
	}
	public void setVelX(float velX) {
		this.velX = velX;
	}
	public float getVelY() {
		return velY;
	}
	public void setVelY(float velY) {
		this.velY = velY;
	}

}
