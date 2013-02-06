package org.mvfbla.cgs2012.menu;

import org.mvfbla.cgs2012.base.Game;
import org.mvfbla.cgs2012.utils.GameConstants;
import org.mvfbla.cgs2012.utils.PauseWindow;
import org.mvfbla.cgs2012.utils.TypeWriter;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * @author Young
 * Displays plot text determine b y the users actions throughout the game
 */
public class PlotState extends BasicGameState{

	private String finalPlot;
	//String for the final plot
	TypeWriter text;

	//for fading
	private int stateID = -1;
	private final long fadeDur = 400;
	private long fadeTime = 0;
	private int fadeState = 0;
	private PauseWindow pauseWindow;

	/**
	 * Sets the ID of the state
	 * @param ID - ID of the state
	 */
	public PlotState(int ID){
		stateID = ID;
	}
	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException {
		pauseWindow = new PauseWindow();
		pauseWindow.init();
		finalPlot = " ";
		text = new TypeWriter();
		text.setDelay(10);
		fadeState = 1;
		if((GameConstants.bossesDefeated & 8) == 8) { //If the Black Boss was defeated
			finalPlot = "\"I am proud of you Alex\"" +
					"\n\"I am sorry if I have hurt you.\"\n\"I am sorry.\"\n\"I am sorry.\"" +
					"\n\"You are here because you are part of an experiment.\n\"Our goal: discover the extent of the" +
					"deeply rooted teachings of FBLA in an individual.\"\n\"Your memory was cleared to allow you to " +
					"discover things on your own. To explore the crevices of your conscience.\"\n" +
					"\n\"You were able to conquer every floor of this building unharmed, and defeat me.\"" +
					"                                 \"You are Perfect\"" +
					"                                                                                         ";
		} else {
			finalPlot = "As the blue figure falls to the floor, it utters a few final words," +
					"                                                \"Be Quick Alex\"\n" +
					"Strangely, I already feel quicker. ";
		}
		if(GameConstants.lastBoss == 2){ // If the Red Boss was defeated
			finalPlot = "As the red figure falls to the floor, it utters a few final words," +
					"                                                \"Be Strong Alex\"\n" +
					"Strangely, I already feel stronger. ";
		}
		if(GameConstants.lastBoss == 3){ // If the Yellow Boss was defeated
			finalPlot = "As the yellow figure falls to the floor, it utters a few final words," +
					"                                                \"Be Wise Alex\"\n" +
					"Strangely, I already feel wiser.";
		}
		finalPlot += "Suddenly my mind is pounded with waves of guilt. " +
				"I have done it again." +
				" Countless new questions fill my mind, none of my previous questions answered. I sit and weep.  " +
				"...   ...   ...   ...   ...   ...   ...   ...   ...   ...   ";
		if((GameConstants.bossesDefeated & 2) == 0){ // If the player has not beaten the Red Boss
			finalPlot += " Perhaps if I had defeated more enemies...   ";
		}
		if((GameConstants.bossesDefeated & 4) == 0){ // If the player has not beaten the Blue Boss
			finalPlot += " Perhaps if I refrained from killing eniemies...   ";
		}
		if((GameConstants.bossesDefeated & 1) == 0){ // If the player has not beaten the Yellow Boss
			finalPlot += " Perhaps if I had used more technology...   ";
		}
		//Plot that displays no matter which boss was defeated
		finalPlot += "...   ...   ...   ...   ...   ... There is nothing around me but a sea of darkness...   ...   ...   " +
				"But this is not the end. I will keep searching until I find the answers. " +
				"I know they are out there   ...   ...   ...   ..." +
				"   ...   ...   ...   ...   ...   ...   Somewhere." +
				"                                                                                         ";
		text.setText(finalPlot);
	}
	@Override
	public int getID() {
		return stateID;
	}
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		//initializes variables
		finalPlot = " ";
		text = new TypeWriter();
	}
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		//Renders the state
		g.setColor(Color.white);
		text.draw(g,0,150,720,240);
		if(fadeState != 0) {
			g.setColor(new Color(0, 0, 0, 1f-(fadeTime/(float)fadeDur)));
			g.fillRect(0, 0, 100000, 100000);
		}
		// Draw pause window if needed
		if(GameConstants.getPaused() == true){
			pauseWindow.draw(g, 0, 0);
		}
	}
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		if(!GameConstants.getPaused()) {
			//Updates the state
			text.update(gc,delta);
			if(fadeState == 2) {
				fadeTime -= delta;
				if(fadeTime <= 0) {
					fadeState = 1;
					if((GameConstants.bossesDefeated & 8) == 8) {
						GameConstants.bossesDefeated = 0;
						GameConstants.playNum = 0;
					}
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
			if(text.isFinished())
				fadeState = 2;
		}
		if(!gc.hasFocus()) {
			GameConstants.paused = true;
		}
		// Pause the game
		if(gc.getInput().isKeyPressed(Input.KEY_ESCAPE)){
			GameConstants.flipPaused();
		}
		if(GameConstants.getPaused())
			pauseWindow.update(gc,sbg);
	}
}
