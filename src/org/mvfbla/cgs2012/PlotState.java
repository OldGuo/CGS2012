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
	public static boolean killedRedBoss = false;
	public static boolean killedBlueBoss = false;
	public static boolean killedYellowBoss = false;
	public static boolean killedBlackBoss = false;

	private int stateID = -1;

	public PlotState(int ID){
		stateID = ID;
	}
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		finalPlot = " ";
		text = new TypeWriter();
		if(killedBlackBoss){
			finalPlot = "";
		}else{
			if(GameConstants.lastBoss == 1){ // blue
				finalPlot = "As the blue figure falls to the floor, it utters a few final words," +
						"                                                \"Be Quick Alex\"";
			}
			if(GameConstants.lastBoss == 2){ //red
				finalPlot = "As the red figure falls to the floor, it utters a few final words," +
							"                                                \"Be Strong Alex\"";
			}
			if(GameConstants.lastBoss == 3){ //yellow
				finalPlot = "As the yellow figure falls to the floor, it utters a few final words," +
						"                                                \"Be Wise Alex\"";
			}
			finalPlot += "                           Suddenly my mind is pounded with waves of guilt. " +
					 "I have done it again." +
					 " Countless new questions fill my mind, none of my previous questions answered. I sit and weep.  " +
					 "...   ...   ...   ...   ...   ...   ...   ...   ...   ...   ";
			if(killedRedBoss == false){
				finalPlot += " Perhaps if I had defeated more enemies...   ";
			}
			if(killedBlueBoss == false){
				finalPlot += " Perhaps if I refrained from killing eniemies...   ";
			}
			if(killedYellowBoss == false){
				finalPlot += " Perhaps if I had used more technology...   ";
			}
			finalPlot += "...   ...   ...   ...   ...   ...There is nothing around me but a sea of darkness...   ...   ...   " +
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
	}
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		text.update(gc,delta);
	}
	@Override
	public int getID() {
		return stateID;
	}
}
