package org.mvfbla.cgs2012.interactable;

import org.mvfbla.cgs2012.base.GameObject;
import org.mvfbla.cgs2012.utils.GameConstants;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * @author Young
 * Pillar for the Blue Boss
 */
public class Pillar extends GameObject {

	private boolean broken = false;
	private final Image unbrokenPillar;
	private final Image brokenPillar;

	/**
	 * @param x - x position
	 * @param y - y position
	 * @param width - width
	 * @param height - height
	 * @throws SlickException
	 */
	public Pillar(int x, int y, int width, int height) throws SlickException {
		super(x, y, width, height);
		broken = false;
		unbrokenPillar = new Image("data"+GameConstants.separatorChar+"Maps"+GameConstants.separatorChar+"pillarUnbroken.png");
		brokenPillar = new Image("data"+GameConstants.separatorChar+"Maps"+GameConstants.separatorChar+"pillarBroken.png");
	}
	@Override
	public void draw(Graphics g){
		//Draws the pillar to the screen
		if(broken){
			brokenPillar.draw(getX()-24,getY());
		}else{
			unbrokenPillar.draw(getX(),getY());
		}
	}
	/**
	 * @return - whether the pillar is broken or not
	 */
	public boolean isBroken(){
		return broken;
	}
	/**
	 * @param b - sets the pillar to broken or not broken
	 */
	public void setBroken(boolean b){
		broken = b;
	}
	@Override
	public void update(GameContainer gc, int delta) {

	}
}
