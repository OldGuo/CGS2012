package org.mvfbla.cgs2012;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class Game extends BasicGame {

	private Map map;
	private Character player,enemy;
	private CameraObject cameraBox;
	private final static int MAP_WIDTH = 1280;
	private final static int MAP_HEIGHT = 720;

	public Game() {
		super("Our Game");
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		container.setTargetFrameRate(30);
		map = new Map("data\\TutorialLevel.tmx", "data");
		GameConstants.currMap = map;
		player = new Player(376, 585);
		cameraBox = new CameraObject(player,132,132);
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		player.update(container, delta);
		cameraBox.update(container, delta);
	}

	@Override
	public void render(GameContainer container, Graphics g)  {
		map.getMap().render((int)cameraBox.getOffsetX(),(int)cameraBox.getOffsetY());
		//map.getMap().render(0,0);
		cameraBox.draw(g);
		g.setColor(Color.white);
		g.drawRect(player.getX(),player.getY(),player.getWidth(),player.getHeight());
		player.draw(g);
		g.drawRect(cameraBox.getX(),cameraBox.getY(),cameraBox.getWidth(),cameraBox.getHeight());
	//	for(Tile t : map.getBoxes())
	//		g.draw(t.getCollision());
	}

	public static void main(String[] argv) throws SlickException {
		//AppGameContainer container = new AppGameContainer(new Game(), 1600, 800, false);
		AppGameContainer container = new AppGameContainer(new Game(), MAP_WIDTH, MAP_HEIGHT, false);
		container.start();
	}
}
