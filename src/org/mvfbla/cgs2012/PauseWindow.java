package org.mvfbla.cgs2012;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class PauseWindow{

	ArrayList<InteractButton> pauseButtons;

	public void init() throws SlickException {
		pauseButtons = new ArrayList<InteractButton>(3);
		pauseButtons.add(new InteractButton("Resume",255,190,300,75,0));
		pauseButtons.add(new InteractButton("Main Menu",255,280,300,75,0));
		pauseButtons.add(new InteractButton("Quit",255,370,300,75,0));
	}
	public void draw(Graphics g,int x,int y){
		g.setColor(new Color(20,20,20,0.3f));
		g.fillRect(0, 0, 800+x, 600+y);
		for(int i = 0; i < pauseButtons.size(); i++){
			pauseButtons.get(i).draw(g,x,y);
		}
	}
	public void update(GameContainer gc,StateBasedGame sbg){
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
		for(int i = 0; i < pauseButtons.size(); i++){
			pauseButtons.get(i).update(gc,input);
			if(pauseButtons.get(i).getAction().equals("Resume")){
				pauseButtons.get(i).clear();
				GameConstants.flipPaused();
			}
			if(pauseButtons.get(i).getAction().equals("Main Menu")){
				pauseButtons.get(i).clear();
				GameConstants.clear();
				sbg.enterState(Game.MAIN_MENU_STATE);
			}
			if(pauseButtons.get(i).getAction().equals("Quit")){
				gc.exit();
			}
		}
	}
}
