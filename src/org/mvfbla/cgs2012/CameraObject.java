package org.mvfbla.cgs2012;

public class CameraObject extends GameObject {

	private float offsetX;
	private float offsetY;

	public CameraObject(float x, float y, float width, float height){
		super(x,y,width,height);
	}
	public boolean intersectedRight(Character p){
		if((p.getX() + p.getWidth()) - (getX() + getWidth()) > 1){ //right
			return true;
		}else{
			return false;
		}
	}
	public boolean intersectedLeft(Character p){
		if(p.getX() - getX() < -1){
			return true;
		}else{
			return false;
		}
	}
	public boolean intersectedUp(Character p){
		if(p.getY() - getY() < -1){
			return true;
		}else{
			return false;
		}
	}
	public boolean intersectedDown(Character p){
		if((p.getY() + p.getHeight()) - (getY() + getHeight()) > 1){ //down
			return true;
		}else{
			return false;
		}
	}
	public void setOffsetX(float offset){
		offsetX = offset;
	}
	public void incOffsetX(float value){
		offsetX+=value;
	}
	public void decOffsetX(float value){
		offsetX-=value;
	}
	public void setOffsetY(float offset){
		offsetY = offset;
	}
	public void incOffsetY(float value){
		offsetY+=value;
	}
	public void decOffsetY(float value){
		offsetY-=value;
	}

	public float getOffsetX(){
		return offsetX;
	}
	public float getOffsetY(){
		return offsetY;
	}
}
