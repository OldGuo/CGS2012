package org.mvfbla.cgs2012;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class TutorialLevel extends GameLevel {

	private int stateID = -1;
	private Enemy enemy3;
	private final static int MAP_WIDTH = 800;
	private final static int MAP_HEIGHT = 600;
	private TypeWriterTest text;
	private QuestionWindow question;

	public TutorialLevel(int stateID) {
		this.stateID = stateID;
	}

	@Override
	public void init(GameContainer container,StateBasedGame sbg) throws SlickException {
		super.setBackgroundInfo(1600, 29);
		text = new TypeWriterTest();
		question = new QuestionWindow();
		map = new Map("data\\Maps\\TutorialLevel_1.tmx", "data\\Maps");
		player = new Player(300, 496);
		enemy3 = new PlantedEnemy(2000,396);
		cameraBox = new CameraObject(player,250,1000);
		background = new Image("data\\Background.png");
	}


	@Override
	public void update(GameContainer container,StateBasedGame sbg, int delta) throws SlickException {
		updateMain(container, sbg, delta);
		text.update(container,delta);
		question.update(container);
	}

	@Override
	public void render(GameContainer container, StateBasedGame sbg, Graphics g) throws SlickException  {
		draw(g);
		text.draw(g,-(int)cameraBox.getOffsetX(),-(int)cameraBox.getOffsetY());
		question.draw(g,-(int)cameraBox.getOffsetX(),-(int)cameraBox.getOffsetY());
	}
	@Override
	public int getID(){
		return stateID;
	}
	@Override
	public void enter(GameContainer container, StateBasedGame stateBasedGame) throws SlickException {
		System.out.println("Entering state " + getID());
		initStuff();
		GameConstants.enemies.add(enemy3);

	}
	@Override
	public void leave(GameContainer container, StateBasedGame stateBasedGame) throws SlickException {
		System.out.println("Leaving state " + getID());
	}
}
