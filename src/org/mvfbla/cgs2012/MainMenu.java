package org.mvfbla.cgs2012;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class MainMenu extends BasicGameState{
	private int stateID = -1;

	public MainMenu(int stateID){
		this.stateID = stateID;
	}
	@Override
	public int getID(){
		return stateID;
	}
	@Override
	public void init(GameContainer gc, StateBasedGame sbg)throws SlickException {
		// TODO Auto-generated method stub

	}
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)throws SlickException {
		// TODO Auto-generated method stub
		g.setColor(Color.black);
		g.fillRect(0, 0, 800, 600);
		g.setColor(Color.white);
		g.drawString("CLICK TO ENTER THE STATE", 50, 50);
	}
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int g)throws SlickException {
		// TODO Auto-generated method stub
		Input input = gc.getInput();
		if ( input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) )
			sbg.enterState(Game.ELEVATOR_STATE);
	}
}
