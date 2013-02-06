package org.mvfbla.cgs2012.levels;

import org.mvfbla.cgs2012.BlackBoss;
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
 * The Black Boss Level
 */
public class BlackBossLevel extends GameLevel {

	private BlackBoss blackBoss;


	/**
	 * Sets the ID of the level
	 * @param stateID - ID of the level
	 */
	public BlackBossLevel(int stateID) {
		this.stateID = stateID;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init(GameContainer container,StateBasedGame sbg) throws SlickException {
		//Initializes variables
		super.setBackgroundInfo(33, 8);
		map = new Map("data\\Maps\\BlackBossLevel_5.tmx","data\\Maps");
		background = new Image("data\\Background.png");
		blackBoss = new BlackBoss(735,416);
	}

	@Override
	public void update(GameContainer container, StateBasedGame sbg,int delta) throws SlickException {
		//Update method
		updateMain(container, sbg, delta);
		blackBoss.update(container,delta);
		if(!blackBoss.isAlive()) { //Ends the level
			done = true;
			transState = 2;
		}
	}

	@Override
	public void render(GameContainer container,StateBasedGame sbg, Graphics g) throws SlickException  {
		draw(g);
		if(blackBoss.shouldDisplay()) //Draws the black boss
			blackBoss.draw(g);
		if(!blackBoss.isAlive()&&player.shouldDisplay()) //Draws the player
			player.draw(g);
	}
	@Override
	public int getID(){ //Returns the ID of the level
		return stateID;
	}
	@Override
	public void enter(GameContainer container, StateBasedGame stateBasedGame) throws SlickException { //On enter of the state
		initStuff();
	}
	@Override
	public void leave(GameContainer container, StateBasedGame stateBasedGame) throws SlickException {} //On exit of the state

	@Override
	public void initObject(TiledObject to) throws SlickException { //Initializes the button for the level
		if(to.getType().equals("blackBossButton")) {
			Button b = new Button(to.getX(), to.getY(), new bossSyncListener());
			GameConstants.interacts.add(b);
		}
	}
	public class bossSyncListener implements ButtonListener{ //Syncronize switch for the boss
		@Override
		public void buttonPressed(boolean state){
			GameConstants.flipSync();
		}
	}
}
