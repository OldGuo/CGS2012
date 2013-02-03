package org.mvfbla.cgs2012;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class MainMenu extends BasicGameState{
	private int stateID = -1;
	ArrayList<InteractButton> menuButtons;
	
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
		menuButtons = new ArrayList<InteractButton>();
		menuButtons.add(new InteractButton("Play Game",255,220,300,75,0));
		menuButtons.add(new InteractButton("Instructions",255,310,300,75,0));
		menuButtons.add(new InteractButton("About",255,400,300,75,0));
		menuButtons.add(new InteractButton("Quit",255,490,300,75,0));
	}
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)throws SlickException {
		// TODO Auto-generated method stub
		g.setColor(Color.gray);
		g.fillRect(0, 0, 800, 600);
		
		for(int i = 0; i < menuButtons.size(); i++){
			menuButtons.get(i).draw(g,0,0);
		}
		g.setColor(Color.black);
		g.drawString("Insert Title Here",325,100);
	}
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int g)throws SlickException {
		// TODO Auto-generated method stub
		Input input = gc.getInput();
		if (input.isKeyDown(Input.KEY_0))
			sbg.enterState(Game.MAIN_MENU_STATE);
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
		for(int i = 0; i < menuButtons.size(); i++){
			menuButtons.get(i).update(gc,input);
			if(menuButtons.get(i).getAction().equals("Play Game")){
				menuButtons.get(i).clear();
				sbg.enterState(Game.TUTORIAL_STATE);
			}
			if(menuButtons.get(i).getAction().equals("Instructions")){
				menuButtons.get(i).clear();
				sbg.enterState(Game.INSTRUCTIONS_STATE);
			}
			if(menuButtons.get(i).getAction().equals("About")){
				menuButtons.get(i).clear();
				sbg.enterState(Game.ABOUT_STATE);
			}
			if(menuButtons.get(i).getAction().equals("Quit")){
				gc.exit();
			}
		}
	}
}
