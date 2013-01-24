package org.mvfbla.cgs2012;

import org.newdawn.slick.Graphics;

public interface InteractiveObject {
	public abstract void interact(GameObject source);
	public abstract boolean inRange(GameObject source);
	public abstract void draw(Graphics g);
}
