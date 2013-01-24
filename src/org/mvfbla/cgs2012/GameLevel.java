package org.mvfbla.cgs2012;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;

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
				Button b = new Button(to.getX(), to.getY(), new ButtonListener() {

					@Override
					public void buttonPressed() {
						System.out.println("WHEEEEEEEEEE");
					}

				});
				GameConstants.interacts.add(b);
			}
		}
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
	public void draw(Graphics g) {
		g.setColor(new Color(58,58,58));
		for(int i = 0; i < bgNumRepeat; i++)
			background.draw((int)cameraBox.getOffsetX()+100*i + bgOffsetX,(int)cameraBox.getOffsetY()-176);
		map.getMap().render((int)cameraBox.getOffsetX(),(int)cameraBox.getOffsetY());
		cameraBox.draw(g);
		g.setColor(Color.white);
		//g.drawRect(player.getX(),player.getY(),player.getWidth(),player.getHeight());
		player.draw(g);
		//g.drawRect(cameraBox.getX(),cameraBox.getY(),cameraBox.getWidth(),cameraBox.getHeight());
		for(Enemy guy:GameConstants.enemies)
			guy.draw(g);
		for(MovingTile t : GameConstants.platforms)
			t.draw(g);
		//for(GameObject go : GameConstants.collidableObjects)
		//	g.draw(go);
		for(Trigger t : GameConstants.triggers)
			g.draw(new Rectangle(t.getX(), t.getY(), t.getWidth(), t.getHeight()));
		for(InteractiveObject io : GameConstants.interacts)
			io.draw(g);
		g.draw(cameraBox);
		g.draw(player.getCollision());
	}
	public void setBackgroundInfo(int offset, int numRepeat){
		bgNumRepeat = numRepeat;
		bgOffsetX = offset;
	}
}
