package org.mvfbla.cgs2012;

import java.util.ArrayList;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Game extends BasicGame {

	private Map map;
	private Player player;
	private Enemy enemy1,enemy2,enemy3;
	private CameraObject cameraBox;
	private final static int MAP_WIDTH = 800;
	private final static int MAP_HEIGHT = 600;
	private Image background;
	private Image star, red,blue,yellow,black;
	private final boolean lost=false;
	private ArrayList<Enemy> enemies;
	private ArrayList<MovingTile> platforms;

	public Game() {
		super("Our Game");
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		container.setTargetFrameRate(30);
		map = new Map("data\\Maps\\TutorialLevel_1.tmx", "data\\Maps");
		//map = new Map("data\\Maps\\ElevatorLevel_2.tmx","data\\Maps");
		GameConstants.currMap = map;
		GameConstants.collidableObjects.addAll(map.getBoxes());
		platforms = new ArrayList<MovingTile>();
		enemies = new ArrayList<Enemy>();
		for(TiledObject to : map.getObjects()) {
			if(to.getType().equals("spawn"))
				enemies.add(enemyFromName(to.getProperty("var"), to.getX(), to.getY()));
			if(to.getType().equals("movingPlatform")) {
				MovingTile t = new MovingTile(to.getX(), to.getY(), to.getWidth(), to.getHeight(), to.getProperty("var"), to.getProperty("image"));
				platforms.add(t);
				GameConstants.collidableObjects.add(t);
			}
		}
		player = new Player(300, 496);
		enemy1= new BasicEnemy(2200,300);
		enemy2 = new BiggerEnemy(2400,300);
		enemy3 = new PlantedEnemy(2400,496);
		enemies.add(enemy1);
		enemies.add(enemy2);
		enemies.add(enemy3);
		cameraBox = new CameraObject(player,250,1000);
		background = new Image("data\\Background.png");
	}
	public Enemy enemyFromName(String name, int x, int y) throws SlickException {
		Enemy out = null;
		switch(name) {
			case "BasicEnemy" :
				out = new BasicEnemy(x, y);
				break;
			
		}
		return out;
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		if(!lost)
			player.update(container, delta);
		for(Enemy guy:enemies){
			guy.update(container, delta);
			float tempX=player.getCenterX()-guy.getCenterX();//calculates distance between player and enemy
			double Xdist=Math.pow(tempX, 2);
			double Ydist=Math.pow(player.getCenterY()-guy.getCenterY(), 2);
			float totalDist=(float)Math.sqrt(Xdist+Ydist);
			if(guy.getClass().toString().equals("class org.mvfbla.cgs2012.PlantedEnemy")){
				if(totalDist<((PlantedEnemy)guy).getSight()){
					((PlantedEnemy)guy).changeSleep(true);
					((PlantedEnemy)guy).setDirection(Math.signum(tempX));
					((PlantedEnemy)guy).setSpeed(3*Math.signum(tempX));
				}
				else
					((PlantedEnemy)guy).changeSleep(false);
			}
			if(player.isPunching()&&Math.abs(tempX)<Math.abs(player.getRange())&&-1*Math.signum(tempX)==Math.signum(player.getRange())){
				guy.setHealth(guy.getHealth()-1);
			}
			if(player.collides(guy)){
				player.setHealth(guy.getHealth()-1);
				/*if(!player.isAlive()){
					System.out.println("GG");
				}*/
			}
		}
		cameraBox.update(container, delta);
		for(MovingTile t : platforms)
			t.update(container, delta);
	}

	@Override
	public void render(GameContainer container, Graphics g)  {
		g.setColor(new Color(58,58,58));
		for(int i = 0; i < 28; i++)
			background.draw((int)cameraBox.getOffsetX()+100*i+1617,(int)cameraBox.getOffsetY()-176);
		map.getMap().render((int)cameraBox.getOffsetX(),(int)cameraBox.getOffsetY());
		cameraBox.draw(g);
		g.setColor(Color.white);
		//g.drawRect(player.getX(),player.getY(),player.getWidth(),player.getHeight());
		player.draw(g);
		//g.drawRect(cameraBox.getX(),cameraBox.getY(),cameraBox.getWidth(),cameraBox.getHeight());
		for(Enemy guy:enemies)
			guy.draw(g);
		for(MovingTile t : platforms)
			t.draw(g);
		for(GameObject go : GameConstants.collidableObjects)
			g.draw(go);
		g.draw(player.getCollision());
		/*star.draw(0,0);
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
