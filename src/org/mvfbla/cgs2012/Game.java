package org.mvfbla.cgs2012;

import org.newdawn.slick.*;
import org.newdawn.slick.Animation;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Game extends BasicGame {

	private Map map;
	private Character player,enemy;
	private CameraObject cameraBox;
	private final static int MAP_WIDTH = 1280;
	private final static int MAP_HEIGHT = 720;
	private Image background;
	private Image star, red,blue,yellow,black;

	public Game() {
		super("Our Game");
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		container.setTargetFrameRate(30);
		map = new Map("data\\TutorialLevelBlackScheme.tmx", "data");
		GameConstants.currMap = map;
		player = new Player(500, 496);
		enemy = new Character(2100,508,32,32);
		enemy.addAnimation("Enemy", new Animation(new SpriteSheet("data\\Bot.png", 32, 32), 150));
		cameraBox = new CameraObject(player,250,1000);
		background = new Image("data\\Background.png");
		star = new Image("data\\StarScreen.png");
		red = new Image("data\\StarScreenRed.png");
		black = new Image("data\\StarScreenBlack.png");
		yellow = new Image("data\\StarScreenYellow.png");
		blue = new Image("data\\StarScreenBlue.png");
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		player.update(container, delta);
		cameraBox.update(container, delta);
	}

	@Override
	public void render(GameContainer container, Graphics g)  {
		g.setColor(new Color(58,58,58));
		for(int i = 0; i < 100; i++)
			background.draw((int)cameraBox.getOffsetX()+100*i+1617,(int)cameraBox.getOffsetY()-176);
		map.getMap().render((int)cameraBox.getOffsetX(),(int)cameraBox.getOffsetY());
		cameraBox.draw(g);
		g.setColor(Color.white);
		//g.drawRect(player.getX(),player.getY(),player.getWidth(),player.getHeight());
		player.draw(g);
		g.drawRect(cameraBox.getX(),cameraBox.getY(),cameraBox.getWidth(),cameraBox.getHeight());
		/*enemy.draw(g);
		star.draw(0,0);
		red.draw(0,0);
		blue.draw(0,0);
		yellow.draw(0,0);
		black.draw(0,0);*/
	}

	public static void main(String[] argv) throws SlickException {
		//AppGameContainer container = new AppGameContainer(new Game(), 1600, 800, false);
		AppGameContainer container = new AppGameContainer(new Game(), MAP_WIDTH, MAP_HEIGHT, false);
		container.start();
	}
}
