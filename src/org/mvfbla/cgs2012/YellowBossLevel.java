package org.mvfbla.cgs2012;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class YellowBossLevel extends GameLevel {

	private int stateID = -1;

	public YellowBossLevel(int stateID) {
		this.stateID = stateID;
		// TODO Auto-generated constructor stub
	}

	private Boss yellowBoss;
	private final static int MAP_WIDTH = 780;
	private final static int MAP_HEIGHT = 600;


	@Override
	public void init(GameContainer container,StateBasedGame sbg) throws SlickException {
		super.setBackgroundInfo(33, 8);
		player = new Player(300, 496);
		map = new Map("data\\Maps\\YellowBossLevel_5.tmx","data\\Maps");
		yellowBoss = new YellowBoss(150,150);
		cameraBox = new CameraObject(player,250,1000);
		background = new Image("data\\Background.png");
	}

	@Override
	public void update(GameContainer container, StateBasedGame sbg,int delta) throws SlickException {
		updateMain(container, sbg, delta);
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
		GameConstants.enemies.add(yellowBoss);
	}
	@Override
	public void leave(GameContainer container, StateBasedGame stateBasedGame) throws SlickException {
		System.out.println("Leaving state " + getID());
	}
}
