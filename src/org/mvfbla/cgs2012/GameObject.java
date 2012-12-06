package org.mvfbla.cgs2012;

import java.awt.geom.Rectangle2D;

import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

/**
 * @author PenguinToast
 * An abstract class to represent all game objects
 */
public abstract class GameObject extends Polygon{
	private static final long serialVersionUID = 5424643444518720876L;
	protected Shape collision;
	/**
	 * Creates a new empty GameObject
	 */
	public GameObject() {
		this(0, 0, 0, 0);
	}
	public GameObject(float x, float y, float width, float height) {
		super(new float[] {
			x, y,
			x+width, y,
			x+width, y+height,
			x, y+height
		});
		collision = new Rectangle(x, y, width, height);
	}
	public boolean intersects(GameObject r) {
		return getRect().intersects(r.getRect());
	}
	public Rectangle2D.Float getRect() {
		return new Rectangle2D.Float(x, y, getWidth(), getHeight());
	}
	public float getWidth() {
		return getMaxX()-getMinX();
	}
	public float getHeight() {
		return getMaxY()-getMinY();
	}
	public void setX(float x) {
		super.setX(x);
		collision.setX(x);
	}
	public void setY(float y) {
		super.setY(y);
		collision.setY(y);
	}
	public Shape getCollision() {
		return collision;
	}
	public void setCollision(Shape s) {
		collision = s;
	}
	public boolean collides(GameObject obj) {
		return obj.getCollision().intersects(collision);
	}
}
