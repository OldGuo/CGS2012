package org.mvfbla.cgs2012;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class PlotState extends BasicGameState{

	private String finalPlot;
	TypeWriter text;

	private int stateID = -1;
	private final long fadeDur = 400;
	private long fadeTime = 0;
	private int fadeState = 0;

	public PlotState(int ID){
		stateID = ID;
	}
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		finalPlot = " ";
		text = new TypeWriter();
	}
	@Override
	public void enter(GameContainer container, StateBasedGame game)
			throws SlickException {
		text.setDelay(10);
		fadeState = 1;
		if((GameConstants.bossesDefeated & 8) == 8){
			finalPlot = "\"I am proud of you Alex\"" +
					"\n\"I am sorry if I have hurt you.\"\n\"I am sorry.\"\n\"I am sorry.\"" +
					"\n\"You are here because you are part of an experiment.\n\"Our goal: discover the extent of the" +
					"deeply rooted teachings of FBLA in an individual.\"\n\"Your memory was cleared to allow you to " +
					"discover things on your own. To explore the crevices of your conscience.\"\n" +
					"\n\"You were able to conquer every floor of this building unharmed, and defeat me.\"" +
					"                                 \"You are Perfect\"" +
					"                                                                                         ";
		}else{
			if(GameConstants.lastBoss == 1){ // blue
				finalPlot = "As the blue figure falls to the floor, it utters a few final words," +
							"                                                \"Be Quick Alex\"                           " +
							"Strangely, I already feel quicker. ";
			}
			if(GameConstants.lastBoss == 2){ //red
				finalPlot = "As the red figure falls to the floor, it utters a few final words," +
							"                                                \"Be Strong Alex\"                      " +
							"Strangely, I already feel stronger. ";
			}
			if(GameConstants.lastBoss == 3){ //yellow
				finalPlot = "As the yellow figure falls to the floor, it utters a few final words," +
							"                                                \"Be Wise Alex\"                            " +
							"Strangely, I already feel wiser.";
			}
			finalPlot += "Suddenly my mind is pounded with waves of guilt. " +
					 "I have done it again." +
					 " Countless new questions fill my mind, none of my previous questions answered. I sit and weep.  " +
					 "...   ...   ...   ...   ...   ...   ...   ...   ...   ...   ";
			if((GameConstants.bossesDefeated & 2) == 0){
				finalPlot += " Perhaps if I had defeated more enemies...   ";
			}
			if((GameConstants.bossesDefeated & 4) == 0){
				finalPlot += " Perhaps if I refrained from killing eniemies...   ";
			}
			if((GameConstants.bossesDefeated & 1) == 0){
				finalPlot += " Perhaps if I had used more technology...   ";
			}
			finalPlot += "...   ...   ...   ...   ...   ... There is nothing around me but a sea of darkness...   ...   ...   " +
					"But this is not the end. I will keep searching until I find the answers. " +
					"I know they are out there   ...   ...   ...   ..." +
					"   ...   ...   ...   ...   ...   ...   Somewhere." +
					"                                                                                         ";
		}
		text.setText(finalPlot);
	}
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.setColor(Color.white);
		text.draw(g,0,150,720,240);
		if(fadeState != 0) {
			g.setColor(new Color(0, 0, 0, 1f-(fadeTime/(float)fadeDur)));
			g.fillRect(0, 0, 100000, 100000);
		}
	}
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
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
	@Override
	public int getID() {
		return stateID;
	}
}
