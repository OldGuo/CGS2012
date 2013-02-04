package org.mvfbla.cgs2012;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class BlackBossLevel extends GameLevel {

	private BlackBoss blackBoss;

	public BlackBossLevel(int stateID) {
		this.stateID = stateID;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init(GameContainer container,StateBasedGame sbg) throws SlickException {
		super.setBackgroundInfo(33, 8);
		map = new Map("data\\Maps\\BlackBossLevel_5.tmx","data\\Maps");
		player = new Player(17, 416);
		cameraBox = new CameraObject(player,2000,2000);
		background = new Image("data\\Background.png");
		blackBoss = new BlackBoss(735,416);
	}

	@Override
	public void update(GameContainer container, StateBasedGame sbg,int delta) throws SlickException {
		updateMain(container, sbg, delta);
		blackBoss.update(container,delta);
	}

	@Override
	public void render(GameContainer container,StateBasedGame sbg, Graphics g)  {
		draw(g);
		if(blackBoss.shouldDisplay())
			blackBoss.draw(g);
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
		if(to.getType().equals("blackBossButton")) {
			Button b = new Button(to.getX(), to.getY(), new bossSyncListener());
			GameConstants.interacts.add(b);
		}
	}
	public class bossSyncListener implements ButtonListener{
		@Override
		public void buttonPressed(boolean state){
			GameConstants.flipSync();
		}
	}
}
