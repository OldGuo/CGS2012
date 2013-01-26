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

public abstract class GameLevel extends BasicGameState{
	protected int bgOffsetX, bgNumRepeat;
	protected Map map;
	protected Player player;
	protected CameraObject cameraBox;
	protected Image background;
	protected Boolean lost = false;

	public void initStuff() throws SlickException {
		GameConstants.clear();
		GameConstants.currMap = map;
		GameConstants.collidableObjects.addAll(map.getBoxes());
		GameConstants.platforms = new ArrayList<MovingTile>();
		for(TiledObject to : map.getObjects()) {
			if(to.getType().equals("spawn"))
				GameConstants.enemies.add(enemyFromName(to.getProperty("var"), to.getX(), to.getY()));
			if(to.getType().equals("movingPlatform")) {
				MovingTile t = new MovingTile(to.getX(), to.getY(), to.getWidth(), to.getHeight(), to.getProperty("var"), to.getProperty("image"));
				GameConstants.platforms.add(t);
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
				Button b = new Button(to.getX(), to.getY(), new TempListener());
				GameConstants.interacts.add(b);
			}
		}
	}
	public class TempListener implements ButtonListener {
		@Override
		public void buttonPressed(boolean state) {
			GameConstants.platforms.get(0).setOn(state);
		}
	}
	public void updateMain(GameContainer container, StateBasedGame sbg,int delta) {
		if(!lost)
			player.update(container, delta);
		for(Characters guy:GameConstants.enemies){
			guy.update(container, delta);
			float tempX=player.getCenterX()-guy.getCenterX();//calculates distance between player and enemy
			double Xdist=Math.pow(tempX, 2);
			double Ydist=Math.pow(player.getCenterY()-guy.getCenterY(), 2);
			float totalDist=(float)Math.sqrt(Xdist+Ydist);
			String name=guy.getClass().toString();
			float hit=0;
			if(player.collides(guy)){
				if(name.equals("class org.mvfbla.cgs2012.BasicEnemy")||name.equals("class org.mvfbla.cgs2012.PlantedEnemy")){
					if(Math.abs(tempX)<20)
						player.setHealth(player.getHealth()-1);
					if(tempX>0)
						hit+=16;
				}
				else{
					if(Math.abs(tempX)<40)
						player.setHealth(player.getHealth()-1);
				}
			}
			if(tempX>0)
				hit+=(guy.getWidth());
			else
				hit+=(guy.getWidth()/8);
			if(name.equals("class org.mvfbla.cgs2012.PlantedEnemy")){
				if(totalDist<((PlantedEnemy)guy).getSight()){
					((PlantedEnemy)guy).changeSleep(true);
					((PlantedEnemy)guy).setDirection(Math.signum(tempX));
					((PlantedEnemy)guy).setSpeed(3*Math.signum(tempX));
					System.out.println(((PlantedEnemy)guy).getSpeed());
				}
				else
					((PlantedEnemy)guy).changeSleep(false);
			}
			if(player.isPunching()&&-1*Math.signum(tempX)==Math.signum(player.getRange())&&Math.abs(player.getCenterY()-guy.getCenterY())<guy.getHeight()){
				if(Math.abs(tempX)<Math.abs(player.getRange()+hit))
					guy.setHealth(guy.getHealth()-1);
			}
			/*if(!player.isAlive()){
				System.out.println("GG");
			}*/
		}
		for(MovingTile t : GameConstants.platforms)
			t.update(container, delta);
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
	public Enemy enemyFromName(String name, int x, int y) throws SlickException {
		Enemy out = null;
		switch(name) {
			case "BasicEnemy" :
				out = new BasicEnemy(x, y);
				break;
		}
		return out;
	}
	public void draw(Graphics g){
		g.setColor(new Color(58,58,58));
		for(int i = 0; i < bgNumRepeat; i++)
			background.draw((int)cameraBox.getOffsetX()+100*i + bgOffsetX,(int)cameraBox.getOffsetY()-176);
		map.getMap().render((int)cameraBox.getOffsetX(),(int)cameraBox.getOffsetY());
		cameraBox.draw(g);
		g.setColor(Color.white);
		//g.drawRect(player.getX(),player.getY(),player.getWidth(),player.getHeight());
		//g.drawRect(cameraBox.getX(),cameraBox.getY(),cameraBox.getWidth(),cameraBox.getHeight());
		for(Characters guy:GameConstants.enemies)
			guy.draw(g);
		for(MovingTile t : GameConstants.platforms)
			t.draw(g);
		//for(GameObject go : GameConstants.collidableObjects)
		//	g.draw(go);
		for(Trigger t : GameConstants.triggers)
			//g.draw(new Rectangle(t.getX(), t.getY(), t.getWidth(), t.getHeight()));
		for(InteractiveObject io : GameConstants.interacts)
			io.draw(g);
		g.draw(cameraBox);
		g.draw(player.getCollision());
		for(int i=1;i<=3;i++){
			if(i<=player.getHealth())
				g.setColor(Color.red);
			else
				g.setColor(Color.gray);
			g.fillRect(i*40-24-(int)cameraBox.getOffsetX(), 554, 32, 32);
		}
		player.draw(g);
	}
	public void setBackgroundInfo(int offset, int numRepeat){
		bgNumRepeat = numRepeat;
		bgOffsetX = offset;
	}
}
