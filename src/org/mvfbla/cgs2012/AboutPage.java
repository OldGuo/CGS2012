package org.mvfbla.cgs2012;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * @author Young
 * About Page
 */
public class AboutPage extends BasicGameState{
	private int stateID = -1;
	private InteractButton back;
	private Image about;
	private final long fadeDur = 400;
	private long fadeTime = 0;
	private int fadeState = 0;

	/**
	 * Sets the ID of the game state
	 * @param stateID - ID of the game state
	 */
	public AboutPage(int stateID) {
		this.stateID = stateID;
	}
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		//initializes variables
		back = new InteractButton("Back",255,490,300,75,0);
		about = new Image("data\\About.png");
		fadeState = 1;
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		//draws the state
		g.setColor(Color.gray);
		g.fillRect(0, 0, 800, 600);
		g.setColor(Color.black);
		about.draw();
		back.draw(g,0,0);
		if(fadeState != 0) {
			g.setColor(new Color(0, 0, 0, 1f-(fadeTime/(float)fadeDur)));
			g.fillRect(0, 0, 100000, 100000);
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)throws SlickException {
		//updates the state
		if(fadeState == 2) { //fading
			fadeTime -= delta;
			if(fadeTime <= 0) {
				fadeState = 1;
				back.clear();
				sbg.enterState(Game.MAIN_MENU_STATE);
			}
			return;
		} else if(fadeState == 1) {
			fadeTime += delta;
			if(fadeTime >= fadeDur) {
				fadeState = 0;
			}
			return;
		}
		Input input = gc.getInput();
		back.update(gc, input);
		if(back.getAction().equals("Back")){
			fadeState = 2;
		}
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return stateID;
	}

}
