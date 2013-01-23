package org.mvfbla.cgs2012;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class GravityLevel extends BasicGameState {

	private int stateID = -1;

	public GravityLevel(int stateID) {
		this.stateID = stateID;
		// TODO Auto-generated constructor stub
	}

	private Map map;
	private Player player;
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
		map = new Map("data\\Maps\\GravityLevel_4.tmx","data\\Maps");
		GameConstants.collidableObjects.addAll(map.getBoxes());
		GameConstants.currMap = map;
		player = new Player(300, 496);
		enemies = new ArrayList<Enemy>();
		cameraBox = new CameraObject(player,250,1000);
		background = new Image("data\\Background.png");
	}

	@Override
	public void update(GameContainer container, StateBasedGame sbg,int delta) throws SlickException {
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

		//testing
		Input input = container.getInput();
		if (input.isKeyDown(Input.KEY_1))
			sbg.enterState(Game.TUTORIAL_STATE);
		if (input.isKeyDown(Input.KEY_2))
			sbg.enterState(Game.ELEVATOR_STATE);
		if (input.isKeyDown(Input.KEY_3))
			sbg.enterState(Game.MOTION_SENSOR_STATE);
		if (input.isKeyDown(Input.KEY_4))
			sbg.enterState(Game.GRAVITY_STATE);
		if (input.isKeyDown(Input.KEY_5))
			sbg.enterState(Game.BLUE_BOSS_STATE);
		if (input.isKeyDown(Input.KEY_6))
			sbg.enterState(Game.RED_BOSS_STATE);
		if (input.isKeyDown(Input.KEY_7))
			sbg.enterState(Game.YELLOW_BOSS_STATE);
		if (input.isKeyDown(Input.KEY_8))
			sbg.enterState(Game.BLACK_BOSS_STATE);
	}

	@Override
	public void render(GameContainer container,StateBasedGame sbg, Graphics g)  {
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
	@Override
	public void enter(GameContainer container, StateBasedGame stateBasedGame) throws SlickException {
		System.out.println("Entering state " + getID());
		GameConstants.collidableObjects = new ArrayList<GameObject>();
		GameConstants.currMap = map;
		GameConstants.collidableObjects.addAll(map.getBoxes());
	}
	@Override
	public void leave(GameContainer container, StateBasedGame stateBasedGame) throws SlickException {
		System.out.println("Leaving state " + getID());
	}
}
