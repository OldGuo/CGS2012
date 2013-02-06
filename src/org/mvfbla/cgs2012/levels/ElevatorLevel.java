package org.mvfbla.cgs2012.levels;

import org.mvfbla.cgs2012.GameConstants;
import org.mvfbla.cgs2012.GameLevel;
import org.mvfbla.cgs2012.GameObject;
import org.mvfbla.cgs2012.Map;
import org.mvfbla.cgs2012.TiledObject;
import org.mvfbla.cgs2012.interactable.Button;
import org.mvfbla.cgs2012.interactable.ButtonListener;
import org.mvfbla.cgs2012.interactable.Key;
import org.mvfbla.cgs2012.interactable.Trigger;
import org.mvfbla.cgs2012.interactable.TriggerListener;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * @author Young
 * The Elevator Level
 * Centered around access to the exit Elevator
 */
public class ElevatorLevel extends GameLevel {
	/**
	 * @author Young
	 * Activates the Elevator if you have the key
	 */
	public class ElevatorKeyListener implements TriggerListener {
		@Override
		public void onEnter(GameObject src) {
			elevator.getTrigger().setActive(true);
		}
		@Override
		public void onExit(GameObject src) {}
		@Override
		public void triggered(GameObject src) {}
	}
	/**
	 * @author Young
	 * Unlocks the Elevator if the switch is activated
	 */
	public class ElevButtonListener implements ButtonListener {
		@Override
		public void buttonPressed(boolean state) {
			if(state) {
				unlockElev(1);
			}
		}
	}

	private boolean waiting; //When waiting to finish answering questions

	/**
	 * Sets the ID of the level
	 * @param stateID - ID of the level
	 */
	public ElevatorLevel(int stateID) {
		this.stateID = stateID;
	}
	@Override
	public void enter(GameContainer container, StateBasedGame stateBasedGame) throws SlickException { //When teh state is entered
		initStuff(); //Initialize values
		elevator.getTrigger().setActive(false);
	}

	@Override
	public int getID(){
		return stateID; //Returns the ID of the state
	}
	@Override
	public void init(GameContainer container,StateBasedGame sbg) throws SlickException {
		super.setBackgroundInfo(33, 19);
		//Initializes variables
		map = new Map("data\\Maps\\ElevatorLevel_2.tmx","data\\Maps");
		background = new Image("data\\Level\\Background.png");
	}
	@Override
	public void initObject(TiledObject to) throws SlickException {
		if(to.getType().equals("key")) { //Creates a key in location specified by .tmx file
			Key key = new Key(to, this);
			GameConstants.interacts.add(key);
			Trigger keyTrigger = new Trigger((int)elevator.getX(), (int)elevator.getY(), (int)elevator.getWidth(), (int)elevator.getHeight(), new ElevatorKeyListener());
			keyTrigger.setActive(false);
			elevatorKeyTrigger = keyTrigger;
			GameConstants.triggers.add(keyTrigger);
		}
		if(to.getType().equals("elevButton")) { //Creates Elevator Button in location sepcified by .tmx file
			Button b = new Button(to.getX(), to.getY(), new ElevButtonListener());
			questionButton = b;
			GameConstants.interacts.add(b);
		}
	}
	@Override
	public void leave(GameContainer container, StateBasedGame stateBasedGame) throws SlickException {} //When the state is left

	@Override
	public void render(GameContainer container,StateBasedGame sbg, Graphics g) throws SlickException  {
		draw(g); //Renders the game
	}
	@Override
	public void unlockElev(int source) { //Unlocks the elevator
		if(source == 1)
			waiting = true;
		else {
			elevatorKeyTrigger.setActive(true); //Unlocks the elevator trigger
		}
	}
	@Override
	public void update(GameContainer container, StateBasedGame sbg,int delta) throws SlickException {
		//Updates the level
		updateMain(container, sbg, delta);
		if(waiting) {
			if(!questions.getAnswering()) { //Not answering questions
				waiting = false;
				questionButton.getTrigger().setActive(false);
				questionButton.getTrigger().exit(null);
				elevator.getTrigger().setActive(true); //Start moving
			}
		}
	}
}
