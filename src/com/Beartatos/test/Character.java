
package com.Beartatos.test;

public class Character {

	private float xPos, yPos;
	private final boolean gravity;

	public Character(boolean hasGravity){
		gravity = hasGravity;
	}

	public float getxPos() { //returns x position
		return xPos;
	}
	public void setxPos(float xPos) { //sets x position
		this.xPos = xPos;
	}
	public float getyPos() { //returns y position
		return yPos;
	}
	public void setyPos(float yPos) { //sets y position
		this.yPos = yPos;
	}
}