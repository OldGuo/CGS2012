package org.mvfbla.cgs2012.menu;

import org.mvfbla.cgs2012.Game;
import org.newdawn.slick.Color;
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
	private final int transStay = 1000;
	private final int transFade = 500;
	private int totalTrans;
	private int currLogo = 0;
	private float currAlpha;

	public LoadingState(int stateID) {
		this.stateID = stateID;
	}
	@Override
	public int getID() {
		return stateID;
	}
	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		// Initialize the logos
		logos = new Image[2];
		logos[0] = new Image("data\\logo\\fblalogo.png");
		logos[1] = new Image("data\\logo\\slicklogo.png");
		transState = 1;
		// Total length of the logos
		totalTrans = logos.length*(transStay + 2 * transFade);
	}
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		// Draw the logo in the center of the screen
		g.drawImage(logos[currLogo], 400-logos[currLogo].getWidth()/2, 300-logos[currLogo].getHeight()/2);
		// Draw the fade
		if(transState != 0) {
			g.setColor(new Color(0, 0, 0, currAlpha));
			g.fillRect(0, 0, 100000, 100000);
		}
	}
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		// Update the transitions
		transTime += delta;
		if(transTime >= totalTrans) {
			game.enterState(Game.MAIN_MENU_STATE);
		}
		int logoDur = transStay + transFade*2;
		int currDur = transTime % logoDur;
		currLogo = (int) Math.floor(transTime/(float)logoDur);
		if(currLogo >= logos.length)
			currLogo = logos.length-1;
		if(currDur <= transFade) {
			transState = 1;
			currAlpha = 1-(currDur/(float)transFade);
		} else if(currDur >= logoDur-transFade) {
			transState = 2;
			currAlpha = (currDur-(transStay+transFade))/(float)transFade;
		} else {
			transState = 0;
		}
	}
}
