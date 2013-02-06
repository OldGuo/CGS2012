package org.mvfbla.cgs2012;

import org.mvfbla.cgs2012.levels.BlackBossLevel;
import org.mvfbla.cgs2012.levels.BlueBossLevel;
import org.mvfbla.cgs2012.levels.ElevatorLevel;
import org.mvfbla.cgs2012.levels.GravityLevel;
import org.mvfbla.cgs2012.levels.MotionSensorLevel;
import org.mvfbla.cgs2012.levels.RedBossLevel;
import org.mvfbla.cgs2012.levels.TutorialLevel;
import org.mvfbla.cgs2012.levels.YellowBossLevel;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Game extends StateBasedGame{

	//Integer values for each State
	static final int MAIN_MENU_STATE            = 0;
	static final int TUTORIAL_STATE 			= 1;
	static final int ELEVATOR_STATE			    = 2;
	static final int MOTION_SENSOR_STATE        = 3;
	static final int GRAVITY_STATE              = 4;
	static final int BLUE_BOSS_STATE            = 5;
	static final int RED_BOSS_STATE             = 6;
	static final int YELLOW_BOSS_STATE          = 7;
	static final int BLACK_BOSS_STATE           = 8;
	static final int ABOUT_STATE                = 9;
	static final int INSTRUCTIONS_STATE         = 10;
	static final int PLOT_STATE                 = 11;
	static final int LOADING_STATE				= 12;
	

	public Game() throws SlickException{
		super("Tinge");
	}
	public static void main(String [] args) throws SlickException{
		AppGameContainer app = new AppGameContainer(new Game());
		app.setShowFPS(false);
		app.setTargetFrameRate(30); //Sets the fps but does not show
		app.setVSync(true); //Prevents screen tearing and caps frame rate
        app.setDisplayMode(800, 600, false);
        app.start(); //Starts the game
	}
	@Override
	//Initializes each level state.
	public void initStatesList(GameContainer g) throws SlickException{
		this.addState(new MainMenu(MAIN_MENU_STATE));
		this.addState(new TutorialLevel(TUTORIAL_STATE));
		this.addState(new ElevatorLevel(ELEVATOR_STATE));
		this.addState(new MotionSensorLevel(MOTION_SENSOR_STATE));
		this.addState(new GravityLevel(GRAVITY_STATE));
		this.addState(new BlueBossLevel(BLUE_BOSS_STATE));
		this.addState(new RedBossLevel(RED_BOSS_STATE));
		this.addState(new YellowBossLevel(YELLOW_BOSS_STATE));
		this.addState(new BlackBossLevel(BLACK_BOSS_STATE));
		this.addState(new AboutPage(ABOUT_STATE));
		this.addState(new InstructionPage(INSTRUCTIONS_STATE));
		this.addState(new PlotState(PLOT_STATE));
		this.addState(new LoadingState(LOADING_STATE));
		this.enterState(LOADING_STATE);
		Runnable r = new MusicThread();
		Thread t = new Thread(r);
		t.start();
        g.setIcons(new String[] {"data\\logo\\mvfbla_logo32.png", "data\\logo\\mvfbla_logo16.png"});
	}
	/**
	 * @author admin
	 * Thread to start the music running
	 */
	private class MusicThread implements Runnable {
		@Override
		public void run() {
			try {
				GameConstants.music = new Music("data\\Maps\\Outdated.ogg");
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}
	}
}
