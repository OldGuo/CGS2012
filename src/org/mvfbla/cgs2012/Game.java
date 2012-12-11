package org.mvfbla.cgs2012;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class Game extends BasicGame {

	private Map map;
	private Character player,enemy;
	private CameraObject cameraBox;
	private final static int MAP_WIDTH = 640;
	private final static int MAP_HEIGHT = 480;
	private final float speed = 0.5f;
	private static final float MAX_SPEED = 3;
	private final float gravity = 0.1f;
	private int intersect = 0;
	private boolean inAir;
	private boolean movingRight = true;
	private boolean movingLeft = false;

	public Game() {
		super("Our Game");
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		container.setTargetFrameRate(60);
		map = new Map("data\\largemap.tmx", "data");
		player = new Character(304, 316, 32, 32, "data\\PlayerRight.png");
		enemy = new Character(336,316,32,32,"data\\Karbonator.png");
		cameraBox = new CameraObject(player.getX() - 50,player.getY() - 50,132,132);
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		boolean movePressed = false;
		// Moving left/right/up
		if (container.getInput().isKeyDown(Input.KEY_LEFT)) {
			if(movingRight){
				player.setAnimation((int)player.getWidth(),(int)player.getHeight(),"data\\PlayerLeft.png");
				movingRight = false;
				movingLeft = true;
			}
			movePressed = true;
			player.setVelX(player.getVelX() - speed);
			if(player.getVelX() < -MAX_SPEED)
				player.setVelX(-MAX_SPEED);
			player.getAnim().update(delta);
		}
		if (container.getInput().isKeyDown(Input.KEY_RIGHT)) {
			if(movingLeft){
				player.setAnimation((int)player.getWidth(),(int)player.getHeight(),"data\\PlayerRight.png");
				movingRight = true;
				movingLeft = false;
			}
			movePressed = true;
			player.setVelX(player.getVelX() + speed);
			if(player.getVelX() > MAX_SPEED)
				player.setVelX(MAX_SPEED);
			player.getAnim().update(delta);
		}
		if (!movePressed) {
			// Slow down the player
			if(inAir){
				player.setVelX(Math.signum(player.getVelX())*Math.max(0, (Math.abs(player.getVelX())-0.1f)));
			}else{
				player.setVelX(0);
			}
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

		if(cameraBox.intersectedLeft(player)){
			cameraBox.setX(player.getX());
			cameraBox.decOffsetX(xChange);
		}
		if(cameraBox.intersectedRight(player)){
			cameraBox.setX(player.getX() - 100);
			cameraBox.incOffsetX(-xChange);
		}
		if(cameraBox.intersectedDown(player)){
			cameraBox.setY(player.getY() - 100);
				cameraBox.incOffsetY(yChange);
		}
		if(cameraBox.intersectedUp(player)){
			cameraBox.setY(player.getY());
			cameraBox.decOffsetY(-yChange);
		}
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

	@Override
	public void render(GameContainer container, Graphics g)  {
		map.getMap().render((int)cameraBox.getOffsetX(),(int)cameraBox.getOffsetY());
		g.translate(cameraBox.getOffsetX(),cameraBox.getOffsetY());
		g.drawRect(player.getX(),player.getY(),player.getWidth(),player.getHeight());
		g.drawAnimation(enemy.getAnim(),enemy.getX(),enemy.getY());
		g.drawAnimation(player.getAnim(), player.getX(), player.getY());
		g.drawRect(cameraBox.getX(),cameraBox.getY(),cameraBox.getWidth(),cameraBox.getHeight());
	}

	public static void main(String[] argv) throws SlickException {
		//AppGameContainer container = new AppGameContainer(new Game(), 1600, 800, false);
		AppGameContainer container = new AppGameContainer(new Game(), MAP_WIDTH, MAP_HEIGHT, false);
		container.start();
	}
}
