//Character class, extends AnimatedObject
//subclasses: Player, Enemy, BlackBoss
//physics for all active entities of the game
package org.mvfbla.cgs2012.characters;

import org.mvfbla.cgs2012.base.GameObject;
import org.mvfbla.cgs2012.interactable.AnimatedObject;
import org.mvfbla.cgs2012.interactable.Trigger;
import org.mvfbla.cgs2012.utils.GameConstants;
import org.mvfbla.cgs2012.utils.Vector;
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

	/**
	 * Creates a new Characater with the specified location and dimensions
	 * @param x - X location of character
	 * @param y - Y location of character
	 * @param width - Width of character
	 * @param height - Height of character
	 * @throws SlickException
	 */
	public Characters(int x, int y, int width, int height) throws SlickException {
		super(x, y, width, height-1);
		force = new Vector(0,0);
		alive=display=true;
		blinking=false;
		health=1;
		time=0;
		blinkTime=550;
	}
	/**
	 * Moves this character out of collision with other GameObjects
	 * @return The vector that the character was moved by to avoid collision
	 */
	public Vector checkCollision() {
		Vector v = null;
		// Loop through each collidable object
		for(int i = 0; i < GameConstants.collidableObjects.size(); i++) {
			GameObject obj = GameConstants.collidableObjects.get(i);
			// Get the projection vector
			Vector t = this.doCollision(obj);
			if(v == null) {
				v = t;
			}
			// Translate this character
			if(t != null) {
				translate(t);
			}
			if(v != null && t != null && !t.equals(new Vector(0,0))) {
				if(v.getY() >= 0)
					v = t;
			}
		}
		return v;
	}
	/**
	 * Gets the projection vector between this character an a gameobject
	 * @param obj - The GameObject to check collision with
	 * @return - The project vector
	 */
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
				vertical = false;
			}
			// Check distance between left of the character and right of the object
			if(-(getMinX() - obj.getMaxX()) < Math.abs(diff) && getMinX() - obj.getMaxX() <= 0) {
				diff = getMinX() - obj.getMaxX();
				vertical = false;
			}
			// Get the projection vector
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
		super.draw(g);
	}
	/**
	 * Returns the direction of a vector
	 * @param v - The Vector to direction check
	 * @return An integer representing the direction of the vector:
	 * 	1: up
	 * 	2: right
	 * 	3: down
	 * 	4: left
	 */
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
	/**
	 * Gets the current velocity vector of the character
	 * @return A Vector representing the velocity of the character
	 */
	public Vector getForce() {
		return force;
	}
	/**
	 * Returns the current health of the player
	 * @return The current health of the player
	 */
	public float getHealth(){
		return health;
	}
	/**
	 * Returns the X velocity of this character
	 * @return The X velocity of this character
	 */
	public float getVelX() {
		return force.x;
	}
	/**
	 * Returns the Y velocity of this character
	 * @return - The Y velocity of this character
	 */
	public float getVelY() {
		return force.y;
	}
	/**
	 * Returns whether or not this character is alive
	 * @return Whether or not this character is alive
	 */
	public boolean isAlive(){
		return alive;
	}
	/**
	 * Returns whether or not this character is blinking
	 * @return Boolean showing whether or not this character is blinking
	 */
	public boolean isBlinking() {
		return blinking;
	}
	/**
	 * Sets the duration of the blinking invulnerability
	 * @param howLong - Duration
	 */
	public void setBlink(int howLong){
		blinkTime=howLong;
	}
	/**
	 * Sets the velocity of this character
	 * @param v - Force to be set
	 */
	public void setForce(Vector v) {
		force = v;
	}
	/**
	 * Sets the health of the character
	 * @param howHealthy - New health of the character
	 */
	public void setHealth(float howHealthy){
		if(!blinking){
			health=howHealthy;
			blinking=true; //after the character is hit, it is invulnerable for a period of time and it blinks
		}
	}
	/**
	 * Sets the initial health of the character
	 * @param howHealthy - The new initial health of the character
	 */
	public void setInitialHealth(float howHealthy){
		health=howHealthy;
	}
	/**
	 * Sets the x component of the velocity
	 * @param velX - New x component
	 */
	public void setVelX(float velX) {
		force.x = velX;
	}
	/**
	 * Sets the y component of the velocity
	 * @param velY - New y components
	 */
	public void setVelY(float velY) {
		force.y = velY;
	}
	/**
	 * Returns whether or not this character should be displayed
	 * @return display - display boolean
	 */
	public boolean shouldDisplay(){
		return display;
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
