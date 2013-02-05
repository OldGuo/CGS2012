package org.mvfbla.cgs2012;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class PlotState extends BasicGameState{
	TypeWriter text;
	private int stateID = -1;

	public PlotState(int ID){
		stateID = ID;
	}
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		text = new TypeWriter();
	}
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.setColor(Color.white);
	}
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {

	}
	@Override
	public int getID() {
		return stateID;
	}
}
