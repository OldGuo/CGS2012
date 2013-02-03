package org.mvfbla.cgs2012;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Line;

public class Characters extends AnimatedObject {

	private Vector force;
	protected Vector trans = new Vector();
	private boolean alive, blinking, display;
	private float health;
	private int time, blinkTime;

	public Characters(int x, int y, int width, int height) throws SlickException {
		super(x, y, width, height-1);
		force = new Vector(0,0);
		alive=display=true;
		blinking=false;
		health=1;
		time=0;
		blinkTime=550;
	}
	public Vector doCollision(GameObject obj) {
		Vector out = null;
		if(collides(obj)) {
			while(getVelY() > 7) {
				setVelY(getVelY()-1);
				setY(getY()-1);
			}
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
				//setVelX(Math.min(getVelX(), 0));
				vertical = false;
			}
			// Check distance between left of the character and right of the object
			if(-(getMinX() - obj.getMaxX()) < Math.abs(diff) && getMinX() - obj.getMaxX() <= 0) {
				diff = getMinX() - obj.getMaxX();
				//setVelX(Math.max(0, getVelX()));
				vertical = false;
			}
			// Get the projection vector
			//System.out.println("colliding: " + diff);
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
		if(alive){
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
		}
		trans = checkCollision();
		for(Trigger t : GameConstants.triggers) {
			if(t.isActive())
				if(t.collides(this))
					t.hit(this);
				else
					if(t.contains(this))
						t.exit(this);
		}
		if(health<=0){
			alive=false;
		}
		if(blinking){
			time+=delta;
			for(int i=0;i<=blinkTime;i+=220){
				if(time>=i&&time<=i+110){
					display=false;
					break;
				}
			}
			for(int i=110;i<=blinkTime;i+=220){
				if(time>=i&&time<=i+110){
					display=true;
					break;
				}
			}
			if(time>=blinkTime){
				blinking=false;
				if(alive)
					display=true;
				else
					display=false;
				time=0;
			}
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
		Vector max = new Vector();
		for(int i = 0; i < GameConstants.collidableObjects.size(); i++) {
			GameObject obj = GameConstants.collidableObjects.get(i);
			Vector t = this.doCollision(obj);
			if(v == null) {
				v = t;
			}
			if(t != null) {
				translate(t);
				if(t.length() >= max.length())
					max = t;
			}
			if(v != null && t != null && !t.equals(new Vector(0,0))) {
				if(v.getY() >= 0)
					v = t;
			}
		}
		//if(!max.equals(new Vector()))
		//	return max;
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
	public void setInitialHealth(float howHealthy){
		health=howHealthy;
	}
	public void setHealth(float howHealthy){
		if(/*howHealth<health&&*/!blinking){
			health=howHealthy;
			blinking=true;
		}
	}
	public boolean isAlive(){
		return alive;
	}
	public void setBlink(int howLong){
		blinkTime=howLong;
	}
	public boolean isBlinking(){
		return blinking;
	}
	public boolean shouldDisplay(){
		return display;
	}
	@Override
	public void draw(Graphics g){
		Color orig = g.getColor();
		for(int i=1;i<=health;i++){
			g.setColor(Color.red);
			g.fillRect(this.getCenterX()+i*16-this.getWidth()*3/4,this.getCenterY()-this.getHeight()*3/4, 8, 8);
		}
		g.setColor(orig);
		super.draw(g);
	}
}
