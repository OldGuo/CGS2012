package org.mvfbla.cgs2012.levels;

import org.mvfbla.cgs2012.Button;
import org.mvfbla.cgs2012.ButtonListener;
import org.mvfbla.cgs2012.GameConstants;
import org.mvfbla.cgs2012.GameLevel;
import org.mvfbla.cgs2012.Map;
import org.mvfbla.cgs2012.MovingTile;
import org.mvfbla.cgs2012.TiledObject;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class TutorialLevel extends GameLevel {

	private final static int MAP_WIDTH = 800;
	private final static int MAP_HEIGHT = 600;
	private boolean waiting;

	public TutorialLevel(int stateID) {
		this.stateID = stateID;
	}

	@Override
	public void init(GameContainer container,StateBasedGame sbg) throws SlickException {
		super.setBackgroundInfo(1600, 29);
		map = new Map("data\\Maps\\TutorialLevel_1.tmx", "data\\Maps");
	}
	@Override
	public void update(GameContainer container,StateBasedGame sbg, int delta) throws SlickException {
		updateMain(container, sbg, delta);
		if(waiting && !questions.getAnswering()) {
			waiting = false;
			GameConstants.platforms.get(0).setOn(!GameConstants.platforms.get(0).isOn());
		}
	}

	@Override
	public void unlockElev(int src) {
		waiting = true;
	}

	@Override
	public void render(GameContainer container, StateBasedGame sbg, Graphics g) throws SlickException  {
		draw(g);
		//question.draw(g,-(int)cameraBox.getOffsetX(),-(int)cameraBox.getOffsetY());
	}
	@Override
	public int getID(){
		return stateID;
	}
	@Override
	public void enter(GameContainer container, StateBasedGame stateBasedGame) throws SlickException {
		System.out.println("Entering state " + getID());
		initStuff();
	}
	@Override
	public void leave(GameContainer container, StateBasedGame stateBasedGame) throws SlickException {
		System.out.println("Leaving state " + getID());
		GameConstants.enemiesKilled = 0;
		GameConstants.techUsed = 0;
	}

	@Override
	public void initObject(TiledObject to) throws SlickException {

		if(to.getType().equals("movingPlatform")) {
			MovingTile t = new MovingTile(to.getX(), to.getY(), to.getWidth(), to.getHeight(), to.getProperty("var"), to.getProperty("image"));
			GameConstants.platforms.add(t);
			GameConstants.collidableObjects.add(t);
		}
		if(to.getType().equals("platformButton")) {
			Button b = new Button(to.getX(), to.getY(), new PlatformListener());
			GameConstants.interacts.add(b);
		}
	}
	public class PlatformListener implements ButtonListener {
		@Override
		public void buttonPressed(boolean state) {
			unlockElev(0);
		}
	}
}
