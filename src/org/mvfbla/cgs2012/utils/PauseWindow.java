package org.mvfbla.cgs2012.utils;

import java.util.ArrayList;

import org.mvfbla.cgs2012.base.Game;
import org.mvfbla.cgs2012.interactable.InteractButton;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * @author Young
 * Window for pausing
 */
public class PauseWindow{

	ArrayList<InteractButton> pauseButtons; //Arraylist of pause buttons

	/**
	 * @param g - Graphics
	 * @param x - x position
	 * @param y - y position
	 */
	public void draw(Graphics g,int x,int y){
		g.setColor(new Color(20,20,20,0.3f));
		g.fillRect(0, 0, 800+x, 600+y);
		for(int i = 0; i < pauseButtons.size(); i++){
			pauseButtons.get(i).draw(g,x,y);
		}
	}
	/**
	 * Initializes questions
	 * @throws SlickException
	 */
	public void init() throws SlickException {
		pauseButtons = new ArrayList<InteractButton>(3);
		pauseButtons.add(new InteractButton("Resume",255,140,300,75,0));
		pauseButtons.add(new InteractButton("Main Menu",255,280,300,75,0));
		pauseButtons.add(new InteractButton("Quit",255,420,300,75,0));
	}
	/**
	 * Updates the question screen
	 * @param gc - Game Container
	 * @param sbg - State Based Game
	 */
	public void update(GameContainer gc,StateBasedGame sbg){
		// TODO Auto-generated method stub
		Input input = gc.getInput();
		for(int i = 0; i < pauseButtons.size(); i++){
			pauseButtons.get(i).update(gc,input);
			if(pauseButtons.get(i).getAction().equals("Resume")){
				pauseButtons.get(i).clear();
				GameConstants.flipPaused();
			}
			if(pauseButtons.get(i).getAction().equals("Main Menu")){
				try {
					GameConstants.mainMenu.init(gc, sbg);
				} catch (SlickException e) {
					e.printStackTrace();
				}
				pauseButtons.get(i).clear();
				sbg.enterState(Game.MAIN_MENU_STATE);
				//GameConstants.level.reset();
				//GameConstants.clear();
			}
			if(pauseButtons.get(i).getAction().equals("Quit")){
				gc.exit();
			}
		}
	}
}
