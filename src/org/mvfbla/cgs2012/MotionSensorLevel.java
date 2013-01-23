package org.mvfbla.cgs2012;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class MotionSensorLevel extends BasicGameState {

	private int stateID = -1;
	public MotionSensorLevel(int stateID) {
		this.stateID = stateID;
		// TODO Auto-generated constructor stub
	}

	private Map map;
	private Characters player;
	private Enemy BiggerEnemy;
	private CameraObject cameraBox;
	private final static int MAP_WIDTH = 800;
	private final static int MAP_HEIGHT = 600;
	private Image background;

	private final boolean lost=false;
	private ArrayList<Enemy> enemies;

	@Override
	public void init(GameContainer container,StateBasedGame sbg) throws SlickException {
		container.setTargetFrameRate(30);
		map = new Map("data\\Maps\\MotionSensorLevel_3.tmx","data\\Maps");
		GameConstants.collidableObjects.addAll(map.getBoxes());
		GameConstants.currMap = map;
		player = new Player(300, 496);
		BiggerEnemy = new BiggerEnemy(150,448);
		enemies = new ArrayList<Enemy>();
		enemies.add(BiggerEnemy);
		cameraBox = new CameraObject(player,250,1000);
		background = new Image("data\\Background.png");
	}

	@Override
	public void update(GameContainer container,StateBasedGame sbg, int delta) throws SlickException {
		if(!lost)
			player.update(container, delta);
		for(Enemy guy:enemies){
			guy.update(container, delta);
			if(guy.getClass().toString().equals("class org.mvfbla.cgs2012.PlantedEnemy")){
				float tempX=player.getCenterX()-guy.getCenterX();
				double Xdist=Math.pow(tempX, 2);
				double Ydist=Math.pow(player.getCenterY()-guy.getCenterY(), 2);
				if((float)Math.sqrt(Xdist+Ydist)<((PlantedEnemy)guy).getSight()/2){
					((PlantedEnemy)guy).changeSleep(true);
					((PlantedEnemy)guy).setDirection(Math.signum(tempX));
				}
				else
					((PlantedEnemy)guy).changeSleep(false);
			}
		}
		cameraBox.update(container, delta);
	}

	@Override
	public void render(GameContainer container, StateBasedGame sbg,Graphics g)  {
		g.setColor(new Color(58,58,58));
		for(int i = 0; i < 19; i++)
			background.draw((int)cameraBox.getOffsetX()+100*i+35,(int)cameraBox.getOffsetY()-176);
		map.getMap().render((int)cameraBox.getOffsetX(),(int)cameraBox.getOffsetY());
		cameraBox.draw(g);
		g.setColor(Color.white);
		//g.drawRect(player.getX(),player.getY(),player.getWidth(),player.getHeight());
		player.draw(g);
		//g.drawRect(cameraBox.getX(),cameraBox.getY(),cameraBox.getWidth(),cameraBox.getHeight());
		for(Enemy guy:enemies)
			guy.draw(g);
	}
	@Override
	public int getID(){
		return stateID;
	}
}
