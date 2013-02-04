package org.mvfbla.cgs2012;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class ElevatorLevel extends GameLevel {

	public ElevatorLevel(int stateID) {
		this.stateID = stateID;
		// TODO Auto-generated constructor stub
	}
	private final static int MAP_WIDTH = 800;
	private final static int MAP_HEIGHT = 600;
	private boolean waiting;


	@Override
	public void init(GameContainer container,StateBasedGame sbg) throws SlickException {
		super.setBackgroundInfo(33, 19);
		map = new Map("data\\Maps\\ElevatorLevel_2.tmx","data\\Maps");
		player = new Player(300, 496);
		cameraBox = new CameraObject(player,250,1000);
		background = new Image("data\\Background.png");
	}

	@Override 
	public void unlockElev(int source) {
		if(source == 1)
			waiting = true;
		else {
			elevatorKeyTrigger.setActive(true);
		}
	}
	@Override
	public void update(GameContainer container, StateBasedGame sbg,int delta) throws SlickException {
		updateMain(container, sbg, delta);
		if(waiting) {
			if(!questions.getAnswering()) {
				waiting = false;
				questionButton.getTrigger().setActive(false);
				questionButton.getTrigger().exit(null);
				elevator.getTrigger().setActive(true);
			}
		}
	}

	@Override
	public void render(GameContainer container,StateBasedGame sbg, Graphics g)  {
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
		elevator.getTrigger().setActive(false);
	}
	@Override
	public void leave(GameContainer container, StateBasedGame stateBasedGame) throws SlickException {
		System.out.println("Leaving state " + getID());
	}
}
