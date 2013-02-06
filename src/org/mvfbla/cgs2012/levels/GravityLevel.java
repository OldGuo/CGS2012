package org.mvfbla.cgs2012.levels;

import org.mvfbla.cgs2012.Button;
import org.mvfbla.cgs2012.ButtonListener;
import org.mvfbla.cgs2012.GameConstants;
import org.mvfbla.cgs2012.GameLevel;
import org.mvfbla.cgs2012.Map;
import org.mvfbla.cgs2012.TiledObject;
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
	 * Sets the ID of the level
	 * @param stateID - ID of the level
	 */
	public GravityLevel(int stateID) {
		this.stateID = stateID;
	}
	private boolean waiting;

	@Override
	public void init(GameContainer container,StateBasedGame sbg) throws SlickException {
		//Initializes variables
		super.setBackgroundInfo(33, 19);
		map = new Map("data\\Maps\\GravityLevel_4.tmx","data\\Maps");
		background = new Image("data\\Background.png");
	}
	@Override
	public void initObject(TiledObject to) throws SlickException {
		if(to.getType().equals("gravityButton")) { //Adds a gravity button from  the .tmx file
			Button b = new Button(to.getX(), to.getY(), new GravityListener());
			GameConstants.interacts.add(b);
		}
	}
	@Override
	public void unlockElev(int src) {
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

	@Override
	public void render(GameContainer container,StateBasedGame sbg, Graphics g) throws SlickException  {
		draw(g); //Draws the level
	}
	@Override
	public int getID(){ //returns the ID of the level
		return stateID;
	}
	@Override
	public void enter(GameContainer container, StateBasedGame stateBasedGame) throws SlickException {
		initStuff(); //Initializes variables when entering a state
	}
	@Override
	public void leave(GameContainer container, StateBasedGame stateBasedGame) throws SlickException {} //When the state is left
	public class GravityListener implements ButtonListener{
		@Override
		public void buttonPressed(boolean state){
			//Blargh using random method
			unlockElev(0);
		}
	}
}
