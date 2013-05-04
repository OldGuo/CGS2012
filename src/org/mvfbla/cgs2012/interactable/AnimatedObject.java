package org.mvfbla.cgs2012.interactable;

import java.util.HashMap;

import org.mvfbla.cgs2012.base.GameObject;
import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 * @author William Sheu
 * Represents all objects with animations
 */
public class AnimatedObject extends GameObject {

	protected HashMap<String, Animation> animMap;
	private Animation current;
	private int currentFrame;

	/**
	 * Makes a new object with an Animation.
	 * @param x - X location of the object
	 * @param y - Y location of the object
	 * @param width - Width of the object
	 * @param height - Height of the object
	 * @throws SlickException
	 */
	public AnimatedObject(int x, int y, int width, int height) throws SlickException {
		super(x, y, width, height);
		animMap = new HashMap<String, Animation>();
	}
	/**
	 * Adds a new animation to this object
	 * @param name - Name of the animation
	 * @param anim - Animation to store
	 */
	public void addAnimation(String name, Animation anim) {
		animMap.put(name, anim);
		current = anim;
	}
	@Override
	public void draw(Graphics g) {
		// Draw the current animation
		g.drawAnimation(current, x, y);
		// Draws all objects attached to this one
		g.translate(x, y);
		for(GameObject obj : objects) {
			obj.draw(g);
		}
		g.translate(-x, -y);
	}
	/**
	 * Returns the current playing animation
	 * @return The animation that is currently playing
	 */
	public Animation getCurrentAnimation() {
		return current;
	}
	/**
	 * Returns the current frame of the current animation
	 * @return
	 */
	public int getFrame(){
		return current.getFrame();
	}
	/**
	 * Plays the named animation
	 * @param name - Name of animation to play
	 */
	public void playAnimation(String name) {
		current = animMap.get(name);
		current.start();
	}
	/**
	 * Resets the current animation and stops it
	 */
	public void resetAnimation() {
		current.setCurrentFrame(0);
		current.stop();
	}
	/**
	 * Resumes current animation if it has been stopped
	 */
	public void resumeAnimation() {
		if(current.isStopped())
			current.start();
	}
	/**
	 * Sets the frame of the current animation
	 * @param frame - Frame number to set to
	 */
	public void setFrame(int frame){
		current.setCurrentFrame(frame);
	}
	/**
	 * Stops the current animation
	 */
	public void stopAnimation() {
		current.stop();
	}
	@Override
	public void update(GameContainer gc, int delta) {
		// TODO Auto-generated method stub

	}
}
