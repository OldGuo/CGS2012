package org.mvfbla.cgs2012;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class MainMenu extends BasicGameState{
	private int stateID = -1;
	private Image background;
	ArrayList<InteractButton> menuButtons;
	private final long fadeDur = 400;
	private long fadeTime = 0;
	private int fadeState = 0;
	private int nextState = 0;
	private int nextStateLoc = 0;

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
		menuButtons.add(new InteractButton("New Game",255,190,300,75,0));
		menuButtons.add(new InteractButton("Instructions",255,280,300,75,0));
		menuButtons.add(new InteractButton("About",255,370,300,75,0));
		menuButtons.add(new InteractButton("Quit",255,460,300,75,0));
		background = new Image("data\\background.png");
		fadeState = 1;
	}
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)throws SlickException {
		// TODO Auto-generated method stub
		g.setColor(Color.gray);
		g.fillRect(0, 0, 800, 600);
		for(int i = 0; i < 10; i++)
			background.draw(100*i,-120);

		for(int i = 0; i < menuButtons.size(); i++){
			menuButtons.get(i).draw(g,0,0);
		}
		g.setColor(Color.black);
		g.drawString("Insert Title Here",325,100);
		if(fadeState != 0) {
			g.setColor(new Color(0, 0, 0, 1f-(fadeTime/(float)fadeDur)));
			g.fillRect(0, 0, 100000, 100000);
		}
	}
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)throws SlickException {
		if(fadeState == 2) {
			fadeTime -= delta;
			if(fadeTime <= 0) {
				fadeState = 1;
				menuButtons.get(nextStateLoc).clear();
				sbg.enterState(nextState);
			}
			return;
		} else if(fadeState == 1) {
			fadeTime += delta;
			if(fadeTime >= fadeDur) {
				fadeState = 0;
			}
			return;
		}
		if(GameConstants.playNum == 2)
			menuButtons.get(0).setText("New Game++");
		else if(GameConstants.playNum == 1)
			menuButtons.get(0).setText("New Game+");
		else if(GameConstants.playNum == 0)
			menuButtons.get(0).setText("New Game");
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
		if (input.isKeyDown(Input.KEY_9))
			sbg.enterState(Game.PLOT_STATE);
		for(int i = 0; i < menuButtons.size(); i++){
			menuButtons.get(i).update(gc,input);
			if(menuButtons.get(i).getAction().equals("Play Game")){
				nextStateLoc = i;
				fadeState = 2;
				nextState = Game.TUTORIAL_STATE;
			}
			if(menuButtons.get(i).getAction().equals("Instructions")){
				nextStateLoc = i;
				fadeState = 2;
				nextState = Game.INSTRUCTIONS_STATE;
			}
			if(menuButtons.get(i).getAction().equals("About")){
				nextStateLoc = i;
				fadeState = 2;
				nextState = Game.ABOUT_STATE;
			}
			if(menuButtons.get(i).getAction().equals("Quit")){
				gc.exit();
			}
		}
	}
}
