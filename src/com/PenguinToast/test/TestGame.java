package com.PenguinToast.test;

import org.newdawn.slick.Animation;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class TestGame extends BasicGame {

	private float playerX=340;
	private float playerY=240;
	private Animation playerAnim;
	private Rectangle player;
	public BlockMap map;
	// Represents the acceleration from pressing a button
	private double speed = 0.5;
	// Represents the X component of the player's velocity
	private double velX;
	// Represents the Y component of the player's velocity
	private double velY;
	private static final double MAX_SPEED = 4;
	// The acceleration of gravity
	private double gravity = 0.1;
	// An integer to store the last intersection state
	private int intersect = 0;

	public TestGame() {
		super("one class barebone game");
	}

	public void init(GameContainer container) throws SlickException {
		container.setVSync(true);
		SpriteSheet sheet = new SpriteSheet("karbonator.png",32,32);
		map = new BlockMap("\\map01.tmx");		
		playerAnim = new Animation();
		playerAnim.setAutoUpdate(false);
		for (int frame=0;frame<3;frame++) {
			playerAnim.addFrame(sheet.getSprite(frame,0), 150);
		}
		player = new Rectangle(playerX, playerY, 32, 32);
	}

	public void update(GameContainer container, int delta) throws SlickException {
		boolean movePressed = false;
		// Moving left/right/up
		if (container.getInput().isKeyDown(Input.KEY_LEFT)) {
			movePressed = true;
			velX -= speed;
			if(velX < -MAX_SPEED)
				velX = -MAX_SPEED;
			playerAnim.update(delta);
		} 
		if (container.getInput().isKeyDown(Input.KEY_RIGHT)) {
			movePressed = true;
			velX += speed;
			if(velX > MAX_SPEED)
				velX = MAX_SPEED;	
			playerAnim.update(delta);
		}
		if (!movePressed) {
			// Slow down the player
			velX = Math.signum(velX)*Math.max(0, (Math.abs(velX)-0.1));
		}
		if (container.getInput().isKeyDown(Input.KEY_UP) || container.getInput().isKeyDown(Input.KEY_SPACE)) {
			// Jump
			if(intersect == 1)
				velY += 5;
			//else 
			//	System.out.println(velY);
		}
		double xChange = velX;
		if(intersect == 3)
			velY = 0;
		if(intersect != 1)
			velY -= gravity;
		else {
			velY = Math.max(0, velY);
		}
		double yChange = velY;
		playerX += xChange;
		playerY -= yChange;
		//if(yChange < 0 && intersect != 0)
			//System.out.println(intersect);
		player.setX((float) playerX);
		player.setY((float) playerY);
		//checkCollision();
		intersect = checkCollision();
	}

	// Method for handling collision - Returns the direction the player was shunted in
	public int checkCollision() throws SlickException {
		int out = 0;
		for (int i = 0; i < BlockMap.entities.size(); i++) {
			Rectangle ent = BlockMap.entities.get(i);
			// Make sure there's an intersection - if not, no need to check everything
			if(player.intersects(ent)) {
				// Float to store the least overlap
				float overlap = 100;
				// The direction the player needs to be shunted needs to be in:
				/*
				 *      1
				 *   /`````\
				 * 4 |     | 2 
				 *   \_____/
				 *      3 
				 */
				byte direction = -1;
				// Check the distance between the top of the player and the bottom of the object
				if(ent.getMaxY() - player.getMinY() < overlap && ent.getMaxY() - player.getMinY() >= 0) {
					overlap = ent.getMaxY() - player.getMinY();
					direction = 3;
				}
				// Check the distance between the bottom of the player and the top of the object
				if(player.getMaxY() - ent.getMinY()  < overlap && player.getMaxY() - ent.getMinY() >= 0) {
					overlap = player.getMaxY() - ent.getMinY();
					direction = 1;
				}
				// Check the distance between the right of the player and the left of the object
				if(player.getMaxX() - ent.getMinX() < overlap && player.getMaxX() - ent.getMinX() >= 0) {
					overlap = player.getMaxX() - ent.getMinX();
					velX = Math.min(velX, 0);
					direction = 4;
				}
				// Check the distance between the left of the player and the right of the object
				if(ent.getMaxX() - player.getMinX() < overlap && ent.getMaxX() - player.getMinX() >= 0) {
					overlap = ent.getMaxX() - player.getMinX();
					velX = Math.max(0, velX);
					direction = 2;
				}
				// Shunt the player
				if(direction == 1) {
					playerY -= overlap;
					player.setY(playerY);
				} else if(direction == 2) {
					playerX += overlap;
					player.setX(playerX);
				} else if(direction == 3) {
					playerY += overlap;
					player.setY(playerY);
				} else if(direction == 4) {
					playerX -= overlap;
					player.setX(playerX);
				} else {
					System.err.println("WAT THE GAY");
				}
				if(out != 1)
					out = direction;
			}
		}       
		return out;
	}

	public void render(GameContainer container, Graphics g)  {
		BlockMap.tmap.render(0, 0);
		g.drawAnimation(playerAnim, playerX, playerY);
		g.draw(player);
	}

	public static void main(String[] argv) throws SlickException {
		AppGameContainer container = 
				new AppGameContainer(new TestGame(), 640, 480, false);
		container.start();
	}
}