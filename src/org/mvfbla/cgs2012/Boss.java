//Boss class, extends Enemy
//subclasses: RedBoss, YellowBoss, BlueBoss
package org.mvfbla.cgs2012;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

public class Boss extends Enemy{
	public Boss (int x, int y) throws SlickException{
		super(x,y,128,128); //larger in size
		super.setInitialHealth(3); //takes three hits
	}
	@Override
	public void update(GameContainer gc, int delta){
		super.update(gc, delta);
	}
}
