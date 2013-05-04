package org.mvfbla.cgs2012.interactable;

import org.mvfbla.cgs2012.base.GameObject;
import org.newdawn.slick.Graphics;

/**
 * @author William Sheu
 * Interface representing an object that the player can interact with
 */
public interface InteractiveObject {
	/**
	 * Draw the object
	 * @param g - The graphics context to draw with
	 */
	public abstract void draw(Graphics g);
	public abstract boolean inRange(GameObject source);
	public abstract void interact(GameObject source);
	public abstract boolean isActive();
}
