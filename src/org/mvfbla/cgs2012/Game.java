package org.mvfbla.cgs2012;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class Game extends BasicGame {

	private Map map;
	
	public Game() {
		super("Our Game");
	}

	public void init(GameContainer container) throws SlickException {
		container.setTargetFrameRate(60); 
		map = new Map("data\\map01.tmx", "data");
		
	}

	public void update(GameContainer container, int delta) throws SlickException {
		
	}

	public void render(GameContainer container, Graphics g)  {
		map.getMap().render(0, 0);
	}

	public static void main(String[] argv) throws SlickException {
		AppGameContainer container = 
				new AppGameContainer(new Game(), 640, 480, false);
		container.start();
	}
}
