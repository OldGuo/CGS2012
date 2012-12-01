package org.mvfbla.cgs2012;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class Game extends BasicGame {

	public Game() {
		super("one class barebone game");
	}

	public void init(GameContainer container) throws SlickException {
		container.setTargetFrameRate(60); 
	}

	public void update(GameContainer container, int delta) throws SlickException {
		
	}

	public void render(GameContainer container, Graphics g)  {
	}

	public static void main(String[] argv) throws SlickException {
		AppGameContainer container = 
				new AppGameContainer(new Game(), 640, 480, false);
		container.start();
	}
}
