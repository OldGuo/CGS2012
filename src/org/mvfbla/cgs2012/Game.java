package org.mvfbla.cgs2012;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class Game extends BasicGame {

	private Map map;
	private Character player,enemy;
	private AnimatedObject dust;
	private CameraObject cameraBox;
	private final static int MAP_WIDTH = 640;
	private final static int MAP_HEIGHT = 480;
	private final float speed = 0.5f;
	private static final float MAX_SPEED = 4;
	// The acceleration of gravity
	private final float gravity = 0.1f;
	// An integer to store the last intersection state
	private boolean movingRight = true;
	private boolean movingLeft = false;
	private Vector trans = new Vector();

	public Game() {
		super("Our Game");
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		container.setTargetFrameRate(30);
		map = new Map("data\\map02.tmx", "data");
		player = new Character(314, 316, 32, 32, "data\\CharacterRight.png");
		dust = new AnimatedObject(304,316,32,32, "data\\DustRight.png");
		enemy = new Character(336,316,32,32,"data\\Karbonator.png");
		cameraBox = new CameraObject(player.getX() - 50,player.getY() - 50,132,132);
		GameConstants.game = this;
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		player.update(container, delta);
/*		boolean movePressed = false;
		// Moving left/right/up
		if (container.getInput().isKeyDown(Input.KEY_LEFT)) {
			if(movingRight){
				player.setAnimation((int)player.getWidth(),(int)player.getHeight(),"data\\CharacterLeft.png");
				dust.setAnimation((int)player.getWidth(),(int)player.getHeight(),"data\\DustLeft.png");
				movingRight = false;
				movingLeft = true;
			}
			movePressed = true;
			player.setVelX(player.getVelX() - speed);
			if(player.getVelX() < -MAX_SPEED)
				player.setVelX(-MAX_SPEED);
			player.getAnim().update(delta);
			dust.getAnim().update(delta);
		}
		if (container.getInput().isKeyDown(Input.KEY_RIGHT)) {
			if(movingLeft){
				player.setAnimation((int)player.getWidth(),(int)player.getHeight(),"data\\CharacterRight.png");
				dust.setAnimation((int)player.getWidth(),(int)player.getHeight(),"data\\DustRight.png");
				movingRight = true;
				movingLeft = false;
			}
			movePressed = true;
			player.setVelX(player.getVelX() + speed);
			if(player.getVelX() > MAX_SPEED)
				player.setVelX(MAX_SPEED);
			player.getAnim().update(delta);
			dust.getAnim().update(delta);
		}
		if (!movePressed) {
			// Slow down the player
			player.setVelX(Math.signum(player.getVelX())*Math.max(0, (Math.abs(player.getVelX())-0.1f)));

//				player.setVelX(0);
				dust.setFrame(0);
				player.setFrame(0);
		}
		if (container.getInput().isKeyDown(Input.KEY_UP) || container.getInput().isKeyDown(Input.KEY_SPACE)) {
			// Jump
			if(trans != null && trans.y <= -0.09) {
				player.setVelY(-5);
			}
		}
		float xChange = player.getVelX();
		player.setForce(player.getForce().add(new Vector(0, gravity)));
		float yChange = player.getVelY();
		player.setX(player.getX() + xChange);
		player.setY(player.getY() + yChange);
		player.setX(player.getX());
		player.setY(player.getY());
		trans = checkCollision();
*/
/*		if(cameraBox.intersectedRight(player)){
			cameraBox.setX(player.getX() - 100);
			cameraBox.incOffsetX(-xChange);
		}
		if(cameraBox.intersectedLeft(player)){
			cameraBox.setX(player.getX());
			cameraBox.decOffsetX(xChange);
		}
		if(cameraBox.intersectedDown(player)){
			cameraBox.setY(player.getY() - 100);
			cameraBox.incOffsetY(-yChange);
		}
		if(cameraBox.intersectedUp(player)){
			cameraBox.setY(player.getY());
			cameraBox.decOffsetY(yChange);
		}*/
	}

	public Vector checkCollision() {
		Vector v = null;
		for(int i = 0; i < map.getBoxes().size(); i++) {
			GameObject obj = map.getBoxes().get(i);
			Vector t = player.newCollision(obj);
			if(v == null) v = t;
			if(t != null) v.add(t);
		}
		return v;
	}

	@Override
	public void render(GameContainer container, Graphics g)  {
		map.getMap().render((int)cameraBox.getOffsetX(),(int)cameraBox.getOffsetY());
		g.translate(cameraBox.getOffsetX(), cameraBox.getOffsetY());

		g.setColor(Color.white);
		g.drawRect(player.getX(),player.getY(),player.getWidth(),player.getHeight());
		g.drawAnimation(enemy.getAnim(),enemy.getX(),enemy.getY());
		g.drawAnimation(player.getAnim(), player.getX(), player.getY());
		g.drawAnimation(dust.getAnim(), player.getX(), player.getY());
		g.drawRect(cameraBox.getX(),cameraBox.getY(),cameraBox.getWidth(),cameraBox.getHeight());
		for(Tile t : map.getBoxes())
			g.draw(t.getCollision());
	}

	public static void main(String[] argv) throws SlickException {
		//AppGameContainer container = new AppGameContainer(new Game(), 1600, 800, false);
		AppGameContainer container = new AppGameContainer(new Game(), MAP_WIDTH, MAP_HEIGHT, false);
		container.start();
	}
}
