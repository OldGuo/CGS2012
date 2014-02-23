package org.mvfbla.cgs2012.levels;

import org.mvfbla.cgs2012.base.GameLevel;
import org.mvfbla.cgs2012.base.Map;
import org.mvfbla.cgs2012.base.TiledObject;
import org.mvfbla.cgs2012.interactable.Button;
import org.mvfbla.cgs2012.interactable.ButtonListener;
import org.mvfbla.cgs2012.utils.GameConstants;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * @author Young
 * The Gravity Level
 * Centered around rotating gravity through technology
 */
public class GravityLevel extends GameLevel {
	/**
	 * @author Young
	 * Flips the gravity
	 */
	public class GravityListener implements ButtonListener{
		@Override
		public void buttonPressed(boolean state){
			unlockElev(0);
		}
	}
	private boolean waiting;

	/**
	 * Sets the ID of the level
	 * @param stateID - ID of the level
	 */
	public GravityLevel(int stateID) {
		this.stateID = stateID;
	}
	@Override
	public void enter(GameContainer container, StateBasedGame stateBasedGame) throws SlickException {
		initStuff(); //Initializes variables when entering a state
	}
	@Override
	public int getID(){ //returns the ID of the level
		return stateID;
	}
	@Override
	public void init(GameContainer container,StateBasedGame sbg) throws SlickException {
		//Initializes variables
		super.setBackgroundInfo(33, 19);
		map = new Map("data"+GameConstants.separatorChar+"Maps"+GameConstants.separatorChar+"GravityLevel_4.tmx","data"+GameConstants.separatorChar+"Maps");
		background = new Image("data"+GameConstants.separatorChar+"Level"+GameConstants.separatorChar+"Background.png");
	}

	@Override
	public void initObject(TiledObject to) throws SlickException {
		if(to.getType().equals("gravityButton")) { //Adds a gravity button from  the .tmx file
			Button b = new Button(to.getX(), to.getY(), new GravityListener());
			GameConstants.interacts.add(b);
		}
	}
	@Override
	public void leave(GameContainer container, StateBasedGame stateBasedGame) throws SlickException {} //When the state is left
	@Override
	public void render(GameContainer container,StateBasedGame sbg, Graphics g) throws SlickException  {
		draw(g); //Draws the level
	}
	@Override
	public void unlockElev(int src) { //Unlocks the elevator if the player is not waiting for questions
		waiting = true;
	}
	@Override
	public void update(GameContainer container, StateBasedGame sbg,int delta) throws SlickException {
		updateMain(container, sbg, delta);
		if(waiting && !questions.getAnswering()) { //Rotates gravity if the questions are finished answering
			waiting = false;
			GameConstants.flipGrav();
		}
	}
}
