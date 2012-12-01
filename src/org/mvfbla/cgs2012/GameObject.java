package org.mvfbla.cgs2012;

public abstract class GameObject {
	private float xPos, yPos;
	private float width, height;
	
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
	public void setWidth(float w){
		width = w;
	}
	public float getWidth(){
		return width;
	}
	public void setHeight(float h){
		
	}
	public float getHeight(){
		return height;
	} 
}
