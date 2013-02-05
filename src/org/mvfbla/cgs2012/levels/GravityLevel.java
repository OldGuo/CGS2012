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

public class GravityLevel extends GameLevel {


	public GravityLevel(int stateID) {
		this.stateID = stateID;
		// TODO Auto-generated constructor stub
	}
	private final static int MAP_WIDTH = 800;
	private final static int MAP_HEIGHT = 600;
	private boolean waiting;

	@Override
	public void init(GameContainer container,StateBasedGame sbg) throws SlickException {
		super.setBackgroundInfo(33, 19);
		map = new Map("data\\Maps\\GravityLevel_4.tmx","data\\Maps");
		background = new Image("data\\Background.png");
	}
	@Override
	public void initObject(TiledObject to) throws SlickException {
		if(to.getType().equals("gravityButton")) {
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
		if(waiting && !questions.getAnswering()) {
			waiting = false;
			GameConstants.flipGrav();
		}
	}

	@Override
	public void render(GameContainer container,StateBasedGame sbg, Graphics g) throws SlickException  {
		draw(g);
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
	}
	public class GravityListener implements ButtonListener{
		@Override
		public void buttonPressed(boolean state){
			//Blargh using random method
			unlockElev(0);
		}
	}
}
