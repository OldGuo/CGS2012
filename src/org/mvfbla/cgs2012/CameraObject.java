package org.mvfbla.cgs2012;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

/**
 * @author Young
 * Camera
 * Scrolls the map according the players location on screen
 */
public class CameraObject extends GameObject {

	private float offsetX;
	private float offsetY;
	private final GameObject target;

	/**
	 * @param target - creates a camera for a target object
	 * @param width - width of the camera
	 * @param height - height of the camera
	 */
	public CameraObject(GameObject target, float width, float height) {
		super(target.getCenterX()-(width/2), target.getCenterY()-(height/2),width,height);
		this.target = target;
	}

	/**
	 * @return - The X offset for which the map will have to be translated
	 */
	public float getOffsetX() {
		return offsetX;
	}
	/**
	 * @return - The Y offset for which the map will have to be translated
	 */
	public float getOffsetY() {
		return offsetY;
	}
	@Override
	public void update(GameContainer gc, int delta) {
		//Updates the camera
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
		//draws the camera offset
		g.translate(offsetX, offsetY);
	}
}
