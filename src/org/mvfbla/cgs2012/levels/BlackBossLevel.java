package org.mvfbla.cgs2012.levels;

import org.mvfbla.cgs2012.base.GameLevel;
import org.mvfbla.cgs2012.base.Map;
import org.mvfbla.cgs2012.base.TiledObject;
import org.mvfbla.cgs2012.characters.BlackBoss;
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
 * The Black Boss Level
 * Mimics the Player
 */
public class BlackBossLevel extends GameLevel {

	public class bossSyncListener implements ButtonListener{ //Syncronize switch for the boss
		@Override
		public void buttonPressed(boolean state){
			blackBoss.setVelX(0);
			blackBoss.stopAnimation();
			GameConstants.flipSync();
		}
	}


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
	public void enter(GameContainer container, StateBasedGame stateBasedGame) throws SlickException { //On enter of the state
		initStuff();
	}

	@Override
	public int getID(){ //Returns the ID of the level
		return stateID;
	}
	@Override
	public void init(GameContainer container,StateBasedGame sbg) throws SlickException {
		//Initializes variables
		super.setBackgroundInfo(33, 8);
		map = new Map("data"+GameConstants.separatorChar+"Maps"+GameConstants.separatorChar+"BlackBossLevel_5.tmx","data"+GameConstants.separatorChar+"Maps");
		background = new Image("data"+GameConstants.separatorChar+"Level"+GameConstants.separatorChar+"Background.png");
		blackBoss = new BlackBoss(735,416);
	}
	@Override
	public void initObject(TiledObject to) throws SlickException { //Initializes the button for the level
		if(to.getType().equals("blackBossButton")) {
			Button b = new Button(to.getX(), to.getY(), new bossSyncListener());
			GameConstants.interacts.add(b);
		}
	}
	@Override
	public void leave(GameContainer container, StateBasedGame stateBasedGame) throws SlickException {} //On exit of the state

	@Override
	public void render(GameContainer container,StateBasedGame sbg, Graphics g) throws SlickException  {
		draw(g);
		if(blackBoss.shouldDisplay()) //Draws the black boss
			blackBoss.draw(g);
		if(!blackBoss.isAlive()&&player.shouldDisplay()) //Draws the player
			player.draw(g);
		// Draw question window if needed
		if(questions.getAnswering() == true){
			questions.draw(g,-(int)cameraBox.getOffsetX(),-(int)cameraBox.getOffsetY());
		}
		// Draw pause window if needed
		if(GameConstants.getPaused() == true){
			pauseWindow.draw(g,-(int)cameraBox.getOffsetX(),-(int)cameraBox.getOffsetY());
		}
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
}
