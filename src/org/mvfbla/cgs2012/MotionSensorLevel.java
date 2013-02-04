package org.mvfbla.cgs2012;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class MotionSensorLevel extends GameLevel {

	public MotionSensorLevel(int stateID) {
		this.stateID = stateID;
		// TODO Auto-generated constructor stub
	}
	private final static int MAP_WIDTH = 800;
	private final static int MAP_HEIGHT = 600;
	private int motionDelay = 0;

	@Override
	public void init(GameContainer container,StateBasedGame sbg) throws SlickException {
		super.setBackgroundInfo(33, 19);
		map = new Map("data\\Maps\\MotionSensorLevel_3.tmx","data\\Maps");
		player = new Player(300, 496);
		cameraBox = new CameraObject(player,250,1000);
		background = new Image("data\\Background.png");
	}

	@Override
	public void update(GameContainer container,StateBasedGame sbg, int delta) throws SlickException {
		updateMain(container, sbg, delta);
	}

	@Override
	public void render(GameContainer container, StateBasedGame sbg,Graphics g)  {
		draw(g);
	}
	@Override
	public int getID(){
		return stateID;
	}
	@Override
	public void enter(GameContainer container, StateBasedGame stateBasedGame) throws SlickException {
		System.out.println("Entering state " + getID());
		initStuff();
		//text.setText("OHMAHGERD ITS A MOTION SENSOR");
	}
	@Override
	public void leave(GameContainer container, StateBasedGame stateBasedGame) throws SlickException {
		System.out.println("Leaving state " + getID());
	}

	@Override
	public void initObject(TiledObject to) throws SlickException {

		if(to.getType().equals("motionButton")) {
			Button b = new Button(to.getX(), to.getY(), new MotionButtonListener());
			GameConstants.interacts.add(b);
		}
		if(to.getType().equals("motionSensor")) {
			MotionSensor ms = new MotionSensor(to, motionDelay);
			motionDelay += 500;
			GameConstants.sensors.add(ms);
		}
	}
	public class MotionButtonListener implements ButtonListener {
		@Override
		public void buttonPressed(boolean state) {
			if(state) {
				buttonQuestion = true;
				questions.setAnswering(true);
				for(MotionSensor ms : GameConstants.sensors)
					ms.setState((byte) 0);
			} else
				for(MotionSensor ms : GameConstants.sensors)
					ms.setState((byte) 1);
		}
	}
}
