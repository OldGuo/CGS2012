package org.mvfbla.cgs2012.levels;

import org.mvfbla.cgs2012.base.GameLevel;
import org.mvfbla.cgs2012.base.Map;
import org.mvfbla.cgs2012.base.TiledObject;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * @author Young
 * Motion Sensor Level
 * Centered around bypassing the Motion Sensors
 */
public class MotionSensorLevel extends GameLevel {

	/**
	 * Sets the ID of the level
	 * @param stateID - ID of the level
	 */
	public MotionSensorLevel(int stateID) {
		this.stateID = stateID;
		// TODO Auto-generated constructor stub
	}
	@Override
	public void enter(GameContainer container, StateBasedGame stateBasedGame) throws SlickException {
		initStuff(); //When the level is entered
	}

	@Override
	public int getID(){ //returns the ID of the level
		return stateID;
	}

	@Override
	public void init(GameContainer container,StateBasedGame sbg) throws SlickException {
		//Initializes values
		super.setBackgroundInfo(33, 19);
		map = new Map("data\\Maps\\MotionSensorLevel_3.tmx","data\\Maps");
		background = new Image("data\\Level\\Background.png");
	}
	@Override
	public void initObject(TiledObject to) throws SlickException {} //Initializes level specific objects, in this case there are none
	@Override
	public void leave(GameContainer container, StateBasedGame stateBasedGame) throws SlickException {} //When the level is exited
	@Override
	public void render(GameContainer container, StateBasedGame sbg,Graphics g) throws SlickException  {
		//Draws level
		draw(g);
	}

	@Override
	public void update(GameContainer container,StateBasedGame sbg, int delta) throws SlickException {
		//Updates level
		updateMain(container, sbg, delta);
	}
}
