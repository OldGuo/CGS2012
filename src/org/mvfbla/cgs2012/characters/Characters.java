//Character class, extends AnimatedObject
//subclasses: Player, Enemy, BlackBoss
//physics for all active entities of the game
package org.mvfbla.cgs2012.characters;

import org.mvfbla.cgs2012.GameConstants;
import org.mvfbla.cgs2012.GameObject;
import org.mvfbla.cgs2012.Vector;
import org.mvfbla.cgs2012.interactable.AnimatedObject;
import org.mvfbla.cgs2012.interactable.Trigger;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Line;

public class Characters extends AnimatedObject {

	private Vector force;
	protected Vector trans = new Vector();
	protected boolean alive;
	private boolean blinking;
	private boolean display;
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
	public Vector checkCollision() { //checks for collisions of characters
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
	public Vector doCollision(GameObject obj) { //collisions for characters
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
	public void draw(Graphics g){
		/*Color orig = g.getColor(); //testing purposes, showed how much health above respective characters
		for(int i=1;i<=health;i++){
			g.setColor(Color.red);
			g.fillRect(this.getCenterX()+i*16-this.getWidth()*3/4,this.getCenterY()-this.getHeight()*3/4, 8, 8);
		}
		g.setColor(orig);*/
		super.draw(g);
	}
	public int getDirection(Vector v) { //returns the direction of a vector
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
	public Vector getForce() { //returns force vector
		return force;
	}
	public float getHealth(){ //returns health of character
		return health;
	}
	public float getVelX() { //returns x component of velocity
		return force.x;
	}
	public float getVelY() { //returns y component of velocity
		return force.y;
	}
	public boolean isAlive(){ //returns if the character is alive
		return alive;
	}
	public boolean isBlinking(){ //returns if the character is blinking
		return blinking;
	}
	public void setBlink(int howLong){ //sets the duration of the blinking and invulnerability
		blinkTime=howLong;
	}
	public void setForce(Vector v) { //sets force vector
		force = v;
	}
	public void setHealth(float howHealthy){ //sets health
		if(/*howHealth<health&&*/!blinking){
			health=howHealthy;
			blinking=true; //after the character is hit, it is invulnerable for a period of time and it blinks
		}
	}
	public void setInitialHealth(float howHealthy){ //sets initial health, doesn't cause blinking
		health=howHealthy;
	}
	public void setVelX(float velX) { //sets x component of velocity
		force.x = velX;
	}
	public void setVelY(float velY) { //sets y component of velocity
		force.y = velY;
	}
	public boolean shouldDisplay(){ //returns if the character should be displayed
		return display;
	}
	public Vector toVector(Line l) { //sets a line to a vector
		return new Vector(l.getX2()-l.getX1(), l.getY2() - l.getY1());
	}
	@Override
	public void update(GameContainer gc, int delta) {
		if(alive){ //if the character is alive, it will move and is affected by game physics
			float xChange = this.getVelX();
			int direction = getDirection(trans);
			if(direction == 3 || direction == 1) {
				this.setForce(new Vector(this.getForce().getX(), 0));
			}
			if(direction != 1)
				this.setForce(this.getForce().add(new Vector(0, GameConstants.GRAVITY)));
			if(getVelY() > 9)
				setVelY(9);
			if(getVelY() < -9)
				setVelY(-9);
			float yChange = this.getVelY();
			this.setX(this.getX() + xChange);
			this.setY(this.getY() + yChange);
		}
		trans = checkCollision();
		for(Trigger t : GameConstants.triggers) { //checks if a character enters a trigger area
			if(t.isActive())
				if(t.collides(this))
					t.hit(this);
				else
					if(t.contains(this))
						t.exit(this);
		}
		if(health<=0){ //if health is less than zero, the character is dead
			alive=false;
		}
		if(blinking){ //during the period of time after a character has been hit, he blinks
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
			if(time>=blinkTime){ //processes after the blinking is over
				blinking=false;
				if(alive)
					display=true;
				else
					display=false;
				time=0;
			}
		}
	}
}