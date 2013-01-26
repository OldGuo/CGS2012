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
		g.drawString("MAIN MENU (TO MAKE PRETTY LATER)",250,30);
		g.drawString("Press 1 for Tutorial", 50, 100);
		g.drawString("Press 2 for Elevator", 50, 120);
		g.drawString("Press 3 for Motion Sensor", 50, 140);
		g.drawString("Press 4 for Gravity", 50, 160);
		g.drawString("Press 5 for Blue Boss", 50, 180);
		g.drawString("Press 6 for Red Boss", 50, 200);
		g.drawString("Press 7 for Yellow Boss", 50, 220);
		g.drawString("Press 8 for Black Boss", 50, 240);
		g.drawString("Press 9 for Question Screen", 50, 260);
	}
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int g)throws SlickException {
		// TODO Auto-generated method stub
		Input input = gc.getInput();
		if (input.isKeyDown(Input.KEY_1))
			sbg.enterState(Game.TUTORIAL_STATE);
		if (input.isKeyDown(Input.KEY_2))
			sbg.enterState(Game.ELEVATOR_STATE);
		if (input.isKeyDown(Input.KEY_3))
			sbg.enterState(Game.MOTION_SENSOR_STATE);
		if (input.isKeyDown(Input.KEY_4))
			sbg.enterState(Game.GRAVITY_STATE);
		if (input.isKeyDown(Input.KEY_5))
			sbg.enterState(Game.BLUE_BOSS_STATE);
		if (input.isKeyDown(Input.KEY_6))
			sbg.enterState(Game.RED_BOSS_STATE);
		if (input.isKeyDown(Input.KEY_7))
			sbg.enterState(Game.YELLOW_BOSS_STATE);
		if (input.isKeyDown(Input.KEY_8))
			sbg.enterState(Game.BLACK_BOSS_STATE);
	}
}
