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
		text = new TypeWriter();
		if(killedRedBoss){
			finalPlot = "As the red figure fell to the ground. Waves of guilt pounded my conscience.  " +
						"But this is not the end. I will keep searching until I find the answers. I know they are out there   ..." +
						"   ...   ...   ...   Somewhere.";
		}
		if(killedBlueBoss){

		}
		if(killedYellowBoss){

		}
		if(killedBlackBoss){

		}
	}
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.setColor(Color.white);
	}
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {

	}
	@Override
	public int getID() {
		return stateID;
	}
}
