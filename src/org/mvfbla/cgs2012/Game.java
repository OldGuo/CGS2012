package org.mvfbla.cgs2012;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class Game extends BasicGame {

	private Map map;
	private Character player;
	// Represents the acceleration from pressing a button
	private float speed = 0.5f;
	private static final float MAX_SPEED = 4;
	// The acceleration of gravity
	private float gravity = 0.1f;
	// An integer to store the last intersection state
	private int intersect = 0;
	private boolean inAir;
	
	public Game() {
		super("Our Game");
	}

	public void init(GameContainer container) throws SlickException {
		container.setTargetFrameRate(32); 
		map = new Map("data\\map01.tmx", "data");
		player = new Character(340, 240, 32, 32, "data\\karbonator.png");
		
	}

	public void update(GameContainer container, int delta) throws SlickException {
		boolean movePressed = false;
		// Moving left/right/up
		if (container.getInput().isKeyDown(Input.KEY_LEFT)) {
			movePressed = true;
			player.setVelX(player.getVelX() - speed);
			if(player.getVelX() < -MAX_SPEED)
				player.setVelX(-MAX_SPEED);
			player.getAnim().update(delta);
		} 
		if (container.getInput().isKeyDown(Input.KEY_RIGHT)) {
			movePressed = true;
			player.setVelX(player.getVelX() + speed);
			if(player.getVelX() > MAX_SPEED)
				player.setVelX(MAX_SPEED);
			player.getAnim().update(delta);
		}
		if (!movePressed) {
			// Slow down the player
			if(inAir)
				player.setVelX(Math.signum(player.getVelX())*Math.max(0, (Math.abs(player.getVelX())-0.1f)));
			else
				player.setVelX(0);
		}
		if (container.getInput().isKeyDown(Input.KEY_UP) || container.getInput().isKeyDown(Input.KEY_SPACE)) {
			// Jump
			if(intersect == 1)
				player.setVelY(5);
			//else 
			//	System.out.println(velY);
		}
		float xChange = player.getVelX();
		if(intersect == 3)
			player.setVelY(Math.min(0, player.getVelY()));
		if(intersect != 1){
			inAir = true;
			player.setVelY(player.getVelY() - gravity);
		} else {
			inAir = false;
			player.setVelY(Math.max(0, player.getVelY()));
		}
		float yChange = player.getVelY();
		player.setX(player.getX() + xChange);
		player.setY(player.getY() - yChange);
		player.setX(player.getX());
		player.setY(player.getY());
		//checkCollision();
		intersect = checkCollision();
	}
	
	public int checkCollision() {
		int out = 0;
		for(int i = 0; i < map.getBoxes().size(); i++) {
			GameObject obj = map.getBoxes().get(i);
			int c = player.doCollision(obj);
			if(out != 1 && c != 0)
				out = c;
		}
		return out;
	}

	public void render(GameContainer container, Graphics g)  {
		map.getMap().render(0, 0);
		g.drawAnimation(player.getAnim(), player.getX(), player.getY());
	}

	public static void main(String[] argv) throws SlickException {
		AppGameContainer container = 
				new AppGameContainer(new Game(), 640, 480, false);
		container.start();
	}
}