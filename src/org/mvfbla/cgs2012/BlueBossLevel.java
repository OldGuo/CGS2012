package org.mvfbla.cgs2012;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class BlueBossLevel extends GameLevel {

	public BlueBossLevel(int stateID) {
		this.stateID = stateID;
		// TODO Auto-generated constructor stub
	}
	private final static int MAP_WIDTH = 800;
	private final static int MAP_HEIGHT = 600;

	@Override
	public void init(GameContainer container,StateBasedGame sbg) throws SlickException {
		super.setBackgroundInfo(33, 8);
		map = new Map("data\\Maps\\BlueBossLevel_5.tmx","data\\Maps");
		player = new Player(300, 496);
		cameraBox = new CameraObject(player,250,1300);
		background = new Image("data\\Background.png");
	}

	@Override
	public void update(GameContainer container,StateBasedGame sbg,int delta) throws SlickException {
		updateMain(container, sbg, delta);
		for(Characters guy : GameConstants.enemies) {
			float tempX=player.getCenterX()-guy.getCenterX();//calculates distance between player and enemy
			double Xdist=Math.pow(tempX, 2);
			double Ydist=Math.pow(player.getCenterY()-guy.getCenterY(), 2);
			float totalDist=(float)Math.sqrt(Xdist+Ydist);
			if(guy == blueBoss){
				if(totalDist<((BlueBoss)guy).getSight()){
					((BlueBoss)guy).changeSleep(true);
					((BlueBoss)guy).setDirection(Math.signum(tempX));
					((BlueBoss)guy).setSpeed(1*Math.signum(tempX));
					//System.out.println(((BlueBoss)guy).getSpeed());
				}else{
					((BlueBoss)guy).changeSleep(false);

				}
				if(player.getCenterX() >= ((BlueBoss)guy).getStompX() && player.getCenterX() <= ((BlueBoss)guy).getStompX() + 64){
					if(player.getCenterY() >= ((BlueBoss)guy).getStompY() && player.getCenterY() <= ((BlueBoss)guy).getStompY() + 50){
						player.setHealth(player.getHealth() - 1);
					}
				}
				for(int i = 0; i < GameConstants.pillars.size();i++){
					if(((BlueBoss)guy).getStompX() > GameConstants.pillars.get(i).getX() &&
							((BlueBoss)guy).getStompX() < GameConstants.pillars.get(i).getX() + GameConstants.pillars.get(i).getWidth()){
						if(GameConstants.pillars.get(i).isBroken() == false)	{
							((BlueBoss)guy).setHealth(((BlueBoss)guy).getHealth() - 1);
							GameConstants.pillars.get(i).setBroken(true);
						}
					}
				}
			}
		}
	}

	@Override
	public void render(GameContainer container, StateBasedGame sbg,Graphics g)  {
		draw(g);
	}
	@Override
	public int getID() {
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
}
