package org.mvfbla.cgs2012;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Game extends StateBasedGame{

	static final int MAIN_MENU_STATE            = 0;
	static final int TUTORIAL_STATE 			= 1;
	static final int ELEVATOR_STATE			    = 2;
	static final int MOTION_SENSOR_STATE        = 3;
	static final int GRAVITY_STATE              = 4;
	static final int BLUE_BOSS_STATE            = 5;
	static final int RED_BOSS_STATE             = 6;
	static final int YELLOW_BOSS_STATE          = 7;
	static final int BLACK_BOSS_STATE           = 8;

	public Game(){
		super("FBLA CGS 2012");
	}
	public static void main(String [] args) throws SlickException{
		AppGameContainer app = new AppGameContainer(new Game());
        app.setDisplayMode(800, 600, false);
        app.start();
	}
	@Override
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
	}
}
