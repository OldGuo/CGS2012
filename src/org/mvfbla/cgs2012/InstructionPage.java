package org.mvfbla.cgs2012;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class InstructionPage extends BasicGameState{
	private int stateID = -1;
	private Image instructions;
	private InteractButton back;
	public InstructionPage(int stateID) {
		this.stateID = stateID;
		// TODO Auto-generated constructor stub
	}
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		back = new InteractButton("Back",255,490,300,75,0);
		instructions = new Image("data\\Instructions.png");
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.setColor(Color.gray);
		g.fillRect(0, 0, 800, 600);
		g.setColor(Color.black);
		instructions.draw();
		back.draw(g,0,0);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)throws SlickException {
		Input input = gc.getInput();
		back.update(gc,input);
		if(back.getAction().equals("Back")){
			back.clear();
			sbg.enterState(Game.MAIN_MENU_STATE);
		}
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return stateID;
	}

}
