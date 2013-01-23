package org.mvfbla.cgs2012;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class TutorialLevel extends BasicGameState {

	private int stateID = -1;

	private Map map;
	private Player player;
	private Enemy enemy3;
	private CameraObject cameraBox;
	private final static int MAP_WIDTH = 800;
	private final static int MAP_HEIGHT = 600;
	private Image background;
	private final boolean lost=false;
	private ArrayList<Enemy> enemies;
	private ArrayList<MovingTile> platforms;
	private Button b;
	private TypeWriterTest text;

	public TutorialLevel(int stateID) {
		this.stateID = stateID;
	}

	@Override
	public void init(GameContainer container,StateBasedGame sbg) throws SlickException {
		container.setTargetFrameRate(30);
		text = new TypeWriterTest();
		map = new Map("data\\Maps\\TutorialLevel_1.tmx", "data\\Maps");
		player = new Player(300, 496);
		enemies = new ArrayList<Enemy>();
		enemy3 = new PlantedEnemy(2000,396);
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
	public void update(GameContainer container,StateBasedGame sbg, int delta) throws SlickException {
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
		text.update(container,delta);
		cameraBox.update(container, delta);
		for(MovingTile t : platforms)
			t.update(container, delta);

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
	public void render(GameContainer container, StateBasedGame sbg,Graphics g) throws SlickException  {
		g.setColor(new Color(58,58,58));
		for(int i = 0; i < 29; i++)
			background.draw((int)cameraBox.getOffsetX()+100*i+1600,(int)cameraBox.getOffsetY()-176);
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
	//	for(GameObject go : GameConstants.collidableObjects)
	//		g.draw(go);
		for(Trigger t : GameConstants.triggers)
			g.draw(new Rectangle(t.getX(), t.getY(), t.getWidth(), t.getHeight()));
		g.draw(player.getCollision());
		b.draw(g);
		text.draw(g,-(int)cameraBox.getOffsetX(),-(int)cameraBox.getOffsetY());
	}
	@Override
	public int getID(){
		return stateID;
	}
	@Override
	public void enter(GameContainer container, StateBasedGame stateBasedGame) throws SlickException {
		System.out.println("Entering state " + getID());
		GameConstants.currMap = map;
		GameConstants.collidableObjects = new ArrayList<GameObject>();
		GameConstants.collidableObjects.addAll(map.getBoxes());
		platforms = new ArrayList<MovingTile>();
		for(TiledObject to : map.getObjects()) {
			if(to.getType().equals("spawn"))
				enemies.add(enemyFromName(to.getProperty("var"), to.getX(), to.getY()));
			if(to.getType().equals("movingPlatform")) {
				MovingTile t = new MovingTile(to.getX(), to.getY(), to.getWidth(), to.getHeight(), to.getProperty("var"), to.getProperty("image"));
				platforms.add(t);
				GameConstants.collidableObjects.add(t);
			}
			if(to.getType().equals("trigger")) {
				Trigger t = new Trigger(to, new TriggerListener() {
					@Override
					public void triggered(GameObject src) {
						System.out.println("");
					}

					@Override
					public void onEnter(GameObject src) {
						System.out.println("I WAS ENTEREDDDD");
					}

					@Override
					public void onExit(GameObject src) {
						System.out.println("I WAS EXITTEDDDDD");
					}
				});
				GameConstants.triggers.add(t);
			}
			if(to.getType().equals("button")) {
				Button b = new Button(to.getX(), to.getY(), new ButtonListener() {

					@Override
					public void buttonPressed() {
						System.out.println("WHEEEEEEEEEE");
					}

				});
				this.b = b;
			}
		}

	}
	@Override
	public void leave(GameContainer container, StateBasedGame stateBasedGame) throws SlickException {
		System.out.println("Leaving state " + getID());
	}
}
