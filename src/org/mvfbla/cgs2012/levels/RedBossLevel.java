package org.mvfbla.cgs2012.levels;

import org.mvfbla.cgs2012.CameraObject;
import org.mvfbla.cgs2012.Characters;
import org.mvfbla.cgs2012.GameConstants;
import org.mvfbla.cgs2012.GameLevel;
import org.mvfbla.cgs2012.Map;
import org.mvfbla.cgs2012.Player;
import org.mvfbla.cgs2012.RedBoss;
import org.mvfbla.cgs2012.TiledObject;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class RedBossLevel extends GameLevel {


	public RedBossLevel(int stateID) {
		this.stateID = stateID;
	}
	private final static int MAP_WIDTH = 780;
	private final static int MAP_HEIGHT = 600;

	@Override
	public void init(GameContainer container,StateBasedGame sbg) throws SlickException {
		super.setBackgroundInfo(33, 8);
		map = new Map("data\\Maps\\RedBossLevel_5.tmx","data\\Maps");
		player = new Player(300, 496);
		cameraBox = new CameraObject(player,250,1000);
		background = new Image("data\\Background.png");
	}

	@Override
	public void update(GameContainer container, StateBasedGame sbg,int delta) throws SlickException {
		updateMain(container, sbg, delta);
		for(Characters guy : GameConstants.enemies) {
			String name=guy.getClass().toString();
			if(name.equals("class org.mvfbla.cgs2012.RedBoss")){
				RedBoss boss = (RedBoss)guy;
				if(!boss.isAlive()){
					transState = 2;
					if((GameConstants.bossesDefeated & 0b010) != 0b010) {
						GameConstants.playNum++;
					}
					GameConstants.playerMaxHealth++;
					GameConstants.bossesDefeated |= 0b010;
				}
			}
		}
	}

	@Override
	public void render(GameContainer container, StateBasedGame sbg,Graphics g)  {
		draw(g);
		player.draw(g);
	}
	@Override
	public int getID(){
		return stateID;
	}
	@Override
	public void enter(GameContainer container, StateBasedGame stateBasedGame) throws SlickException {
		System.out.println("Entering state " + getID());
		initStuff();
	}
	@Override
	public void leave(GameContainer container, StateBasedGame stateBasedGame) throws SlickException {
		System.out.println("Leaving state " + getID());
	}

	@Override
	public void initObject(TiledObject to) throws SlickException {
		// TODO Auto-generated method stub
		
	}
}
