//Boss class, extends Enemy
//subclasses: RedBoss, YellowBoss, BlueBoss
package org.mvfbla.cgs2012.characters;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

/**
 * @author Young
 * Boss Class
 * Sets health to three and size of 128x128
 */
public class Boss extends Enemy{
	/**
	 * @param x - x position
	 * @param y - y position
	 * @throws SlickException
	 */
	public Boss (int x, int y) throws SlickException{
		super(x,y,128,128); //larger in size
		super.setInitialHealth(3); //takes three hits
	}
	@Override
	public void update(GameContainer gc, int delta){
		//Updates the boss
		super.update(gc, delta);
	}
}
