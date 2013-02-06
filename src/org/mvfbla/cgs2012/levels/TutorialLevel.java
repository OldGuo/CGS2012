package org.mvfbla.cgs2012.levels;

import org.mvfbla.cgs2012.base.GameLevel;
import org.mvfbla.cgs2012.base.Map;
import org.mvfbla.cgs2012.base.TiledObject;
import org.mvfbla.cgs2012.interactable.Button;
import org.mvfbla.cgs2012.interactable.ButtonListener;
import org.mvfbla.cgs2012.interactable.MovingTile;
import org.mvfbla.cgs2012.utils.GameConstants;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * @author Young
 * Tutorial Level
 * Teaches the player core aspects of gameplay.
 */
public class TutorialLevel extends GameLevel {

	/**
	 * @author Young
	 * Unlocks the platform if switch is activated
	 */
	public class PlatformListener implements ButtonListener {
		@Override
		public void buttonPressed(boolean state) {
			unlockElev(0);
		}
	}

	private boolean waiting;

	/**
	 * Sets the ID lf the level
	 * @param stateID - ID of the level
	 */
	public TutorialLevel(int stateID) {
		this.stateID = stateID;
	}
	@Override
	public void enter(GameContainer container, StateBasedGame stateBasedGame) throws SlickException {
		//Initializes values
		initStuff();
	}

	@Override
	public int getID(){ //returns the ID of the level
		return stateID;
	}
	@Override
	public void init(GameContainer container,StateBasedGame sbg) throws SlickException {
		//Initializes values
		super.setBackgroundInfo(1600, 29);
		map = new Map("data"+GameConstants.separatorChar+"Maps"+GameConstants.separatorChar+"TutorialLevel_1.tmx", "data"+GameConstants.separatorChar+"Maps");
	}
	@Override
	public void initObject(TiledObject to) throws SlickException { //Initializes level specific objects
		if(to.getType().equals("movingPlatform")) { //Moving platform
			MovingTile t = new MovingTile(to.getX(), to.getY(), to.getWidth(), to.getHeight(), to.getProperty("var"), to.getProperty("image"));
			GameConstants.platforms.add(t);
			GameConstants.collidableObjects.add(t);
		}
		if(to.getType().equals("platformButton")) { //Button for the moving platform
			Button b = new Button(to.getX(), to.getY(), new PlatformListener());
			GameConstants.interacts.add(b);
		}
	}
	@Override
	public void leave(GameContainer container, StateBasedGame stateBasedGame) throws SlickException {
		GameConstants.enemiesKilled = 0;
		GameConstants.techUsed = 0;
		//Resets the values used for Boss choosing
		//Tutorial Level does not count towards those values
	}
	@Override
	public void render(GameContainer container, StateBasedGame sbg, Graphics g) throws SlickException  {
		//Draws the level
		draw(g);
	}
	@Override
	public void unlockElev(int src) { //Unlocks the elevator
		waiting = true;
	}
	@Override
	public void update(GameContainer container,StateBasedGame sbg, int delta) throws SlickException {
		//Updates level
		updateMain(container, sbg, delta);
		if(waiting && !questions.getAnswering()) {
			waiting = false;
			GameConstants.platforms.get(0).setOn(!GameConstants.platforms.get(0).isOn());
		}
	}
}
