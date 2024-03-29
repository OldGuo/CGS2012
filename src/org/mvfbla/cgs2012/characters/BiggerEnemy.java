//BiggerEnemy class, extends Enemy
//bigger, slower, takes 2 hits to defeat
package org.mvfbla.cgs2012.characters;

import org.mvfbla.cgs2012.utils.GameConstants;
import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/**
 * @author Young
 * Bigger Enemy
 * A BasicEenemy which requires two hits to defeat
 */
public class BiggerEnemy extends Enemy{
	/**
	 * @param x - x position
	 * @param y - y position
	 * @throws SlickException
	 */
	public BiggerEnemy (int x, int y) throws SlickException{
		super(x, y, 96, 96);
		addAnimation("BiggerEnemyInvert", new Animation(new SpriteSheet("data"+GameConstants.separatorChar+"CharAnim"+GameConstants.separatorChar+"LargeEnemyInverted.png", 96, 96), 150));
		addAnimation("BiggerEnemy", new Animation(new SpriteSheet("data"+GameConstants.separatorChar+"CharAnim"+GameConstants.separatorChar+"LargeEnemy.png", 96, 96), 150));
		super.setSpeed(-1);
		super.setInitialHealth(2);
	}
	@Override
	public void update(GameContainer gc, int delta){
		//Updates the BiggerEnemy
		super.update(gc, delta);
		if(isAlive()){
			if(GameConstants.getGrav() > 0) //flips for gravity level
				playAnimation("BiggerEnemy");
			else
				playAnimation("BiggerEnemyInvert");
		}
	}
}
