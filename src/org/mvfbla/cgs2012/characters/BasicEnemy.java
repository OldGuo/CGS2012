//BasicEnemy class, extends Enemy
//takes one hit to defeat, speed is medium
package org.mvfbla.cgs2012.characters;

import org.mvfbla.cgs2012.GameConstants;
import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/**
 * @author Young
 * Basic Enemy class
 * Moves forward until hits a wall then turns around
 */
public class BasicEnemy extends Enemy{
	/**
	 * @param x - x position
	 * @param y - y position
	 * @throws SlickException
	 */
	public BasicEnemy (int x, int y) throws SlickException{
		super(x, y, 64, 64);
		addAnimation("BasicEnemyInvert", new Animation(new SpriteSheet("data\\SmallEnemyInverted.png", 64, 64), 150));
		addAnimation("BasicEnemy", new Animation(new SpriteSheet("data\\SmallEnemy.png", 64, 64), 150));
		super.setSpeed(-2);
	}
	@Override
	public void update(GameContainer gc, int delta){
		//Updates the Enemy
		super.update(gc, delta);
		if(isAlive()){
			if(GameConstants.getGrav() > 0) //flips for gravity level
				playAnimation("BasicEnemy");
			else
				playAnimation("BasicEnemyInvert");
		}
	}
}
