package org.mvfbla.cgs2012;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class LoadingState extends BasicGameState {
	private int stateID = -1;
	private Image[] logos;
	private int transState = 0;
	private int transTime = 0;
	private int transStay = 1000;
	private int transFade = 500;
	
	public LoadingState(int stateID) {
		this.stateID = stateID;
	}
	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		logos = new Image[2];
		logos[0] = new Image("data\\logo\\fblalogo.png");
		logos[1] = new Image("data\\logo\\slicklogo.png");
		transState = 1;
	}
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		
	}
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		
	}
	@Override
	public int getID() {
		return 0;
	}
}
