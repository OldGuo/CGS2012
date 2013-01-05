package org.mvfbla.cgs2012;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Shape;

/**
 * @author PenguinToast
 * An abstract class to represent all game objects
 */
public abstract class GameObject extends Polygon{
	private static final long serialVersionUID = 5424643444518720876L;
	protected Shape collision;
	protected ArrayList<GameObject> objects;
	/**
	 * Creates a new empty GameObject
	 */
	public GameObject() {
		this(0, 0, 0, 0);
	}
	/**
	 * Creates a new GameObject with the specified x, y, width height
	 * @param x - The X value of the new GameObject
	 * @param y - The Y value of the new GameObject
	 * @param width - The width of the new GameObject
	 * @param height - THe height of the new GameObject
	 */
	public GameObject(float x, float y, float width, float height) {
		super(new float[] {
				x, y,
				x, y+height,
				x+width, y+height,
				x+width, y
		});
		collision = new Polygon(new float[] {
				x, y,
				x, y+height,
				x+width, y+height,
				x+width, y
		});
		objects = new ArrayList<GameObject>();
	}
	/**
	 * Checks for intersection between the bounding boxes of the two GameObjects
	 * @param r - The GameObject to be tested for intersection
	 * @return A boolean representing whether or not the two objects intersect
	 */
	public boolean intersects(GameObject r) {
		return getRect().intersects(r.getRect());
	}
	/**
	 * Gets the rectangle encompassing this GameObject
	 * @return The Rectangle2D.Float surrounding this GameObject
	 */
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
	/**
	 * Translates the object in the direction and magnitude of the given vector
	 * @param v - The Vector to shift the object by
	 */
	public void translate(Vector v) {
		setX(getX() + v.getX());
		setY(getY() + v.getY());
	}
	/**
	 * Gets the collision shape of this GameObject
	 * @return A Shape representing the collision of this object
	 */
	public Shape getCollision() {
		return collision;
	}
	/**
	 * Sets the collision shape of this GameObject
	 * @param s - Shape to be set as the collision shape
	 */
	public void setCollision(Shape s) {
		collision = s;
	}
	/**
	 * Checks if this GameObject is colliding with another GameObject
	 * @param obj - Object to be tested
	 * @return A boolean representing whether or not the two objects are colliding
	 */
	public boolean collides(GameObject obj) {
		return obj.getCollision().intersects(collision);
	}
	public void addObject(GameObject obj) {
		objects.add(obj);
	}
	public abstract void update(GameContainer gc, int delta);
	public abstract void draw(Graphics g);
}
