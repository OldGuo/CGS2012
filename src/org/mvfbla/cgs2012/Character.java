package org.mvfbla.cgs2012;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Line;

public class Character extends AnimatedObject {

	private Vector force;
	protected Vector trans = new Vector();
	private boolean alive;
	private float health;

	public Character(int x, int y, int width, int height) throws SlickException {
		super(x, y, width, height);
		force = new Vector(0,0);
		alive=true;
		health=1;
	}
	public Vector doCollision(GameObject obj) {
		Vector out = null;
		if(collides(obj)) {
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
				setVelX(Math.min(getVelX(), 0));
				vertical = false;
			}
			// Check distance between left of the character and right of the object
			if(-(getMinX() - obj.getMaxX()) < Math.abs(diff) && getMinX() - obj.getMaxX() <= 0) {
				diff = getMinX() - obj.getMaxX();
				setVelX(Math.max(0, getVelX()));
				vertical = false;
			}
			// Get the projection vector
			System.out.println("colliding: " + diff);
			if(vertical) {
				out = new Vector(0, -diff);
			} else {
				out = new Vector(-diff, 0);
			}
		}
		return out;
	}
	@Override
	public void update(GameContainer gc, int delta) {

		float xChange = this.getVelX();
		int direction = getDirection(trans);
		if(direction == 3 || direction == 1) {
			this.setForce(new Vector(this.getForce().getX(), 0));
		}
		if(direction != 1)
			this.setForce(this.getForce().add(new Vector(0, GameConstants.GRAVITY)));
		float yChange = this.getVelY();
		this.setX(this.getX() + xChange);
		this.setY(this.getY() + yChange);
		trans = checkCollision();
		if(health<=0){
			alive=false;
		}
	}
	public int getDirection(Vector v) {
		if(v == null || v.equals(new Vector(0,0)))
			return 0;
		v.normalise();
		if(v.dot(new Vector(0, -1)) == 1)
			return 1;
		if(v.dot(new Vector(0, 1)) == 1)
			return 3;
		if(v.dot(new Vector(-1, 0)) == 1)
			return 4;
		if(v.dot(new Vector(1, 0)) == 1)
			return 2;
		return -1;
	}
	public Vector checkCollision() {
		Vector v = null;
		for(int i = 0; i < GameConstants.collidableObjects.size(); i++) {
			GameObject obj = GameConstants.collidableObjects.get(i);
			Vector t = this.doCollision(obj);
			if(v == null) {
				v = t;
			}
			if(t != null) translate(t);
			if(v != null && t != null && !t.equals(new Vector(0,0))) {
				if(v.getY() >= 0)
					v = t;
			}
		}
		return v;
	}
	public Vector toVector(Line l) {
		return new Vector(l.getX2()-l.getX1(), l.getY2() - l.getY1());
	}
	public float getVelX() {
		return force.x;
	}
	public void setVelX(float velX) {
		force.x = velX;
	}
	public float getVelY() {
		return force.y;
	}
	public void setVelY(float velY) {
		force.y = velY;
	}
	public Vector getForce() {
		return force;
	}
	public void setForce(Vector v) {
		force = v;
	}
	public float getHealth(){
		return health;
	}
	public void setHealth(float howHealthy){
		health=howHealthy;
	}
	public boolean isAlive(){
		return alive;
	}
}
