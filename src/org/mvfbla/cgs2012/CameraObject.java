package org.mvfbla.cgs2012;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class CameraObject extends GameObject {

	private float offsetX;
	private float offsetY;
	private GameObject target;

	public CameraObject(GameObject target, float width, float height) {
		super(target.getCenterX()-(width/2), target.getCenterY()-(height/2),width,height);
		this.target = target;
	}

	public float getOffsetX() {
		return offsetX;
	} 
	public float getOffsetY() {
		return offsetY;
	}
	@Override
	public void update(GameContainer gc, int delta) {
		//System.out.println(getMinX());
		if(target.getMinX() < getMinX()) {
			offsetX += getMinX()-target.getMinX();
			setX(getX() - (getMinX()-target.getMinX()));
		}
		if(target.getMaxX() > getMaxX()) {
			offsetX -= target.getMaxX() - getMaxX();
			setX(getX() + (target.getMaxX() - getMaxX()));
		}
		if(target.getMinY() < getMinY()) {
			offsetY += getMinY()-target.getMinY();
			setY(getY() - (getMinY()-target.getMinY()));
		}
		if(target.getMaxY() > getMaxY()) {
			offsetY -= target.getMaxY() - getMaxY();
			setY(getY() + (target.getMaxY() - getMaxY()));
		}
	}
	@Override
	public void draw(Graphics g) {
		g.translate(offsetX, offsetY);
	}
}
