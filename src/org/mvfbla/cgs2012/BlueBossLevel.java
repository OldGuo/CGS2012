package org.mvfbla.cgs2012;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class BlueBossLevel extends GameLevel {

	private int brokenCount = 3;
	private boolean platformBroken;
	private Tile platform;
	private int fallY =18*16;
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
		platform = new Tile(5*16,18*16,16*39,16*2);
	}

	@Override
	public void update(GameContainer container,StateBasedGame sbg,int delta) throws SlickException {
		updateMain(container, sbg, delta);
		GameConstants.collidableObjects.add(platform);
		if(platformBroken){
			fallY+=5;
		}
		for(Characters guy : GameConstants.enemies) {
			float tempX=player.getCenterX()-guy.getCenterX();//calculates distance between player and enemy
			double Xdist=Math.pow(tempX, 2);
			double Ydist=Math.pow(player.getCenterY()-guy.getCenterY(), 2);
			float totalDist=(float)Math.sqrt(Xdist+Ydist);
			String name=guy.getClass().toString();
			if(name.equals("class org.mvfbla.cgs2012.BlueBoss")){
				if(totalDist<((BlueBoss)guy).getSight()){
					((BlueBoss)guy).changeSleep(true);
					((BlueBoss)guy).setDirection(Math.signum(tempX));
					((BlueBoss)guy).setSpeed(4*Math.signum(tempX));
					//System.out.println(((BlueBoss)guy).getSpeed());
				}else{
					((BlueBoss)guy).changeSleep(false);

				}
				if(player.getCenterX() >= ((BlueBoss)guy).getStompX() && player.getCenterX() <= ((BlueBoss)guy).getStompX() + 64){
					if(player.getCenterY() >= ((BlueBoss)guy).getStompY() && player.getCenterY() <= ((BlueBoss)guy).getStompY() + 50){
						player.setHealth(player.getHealth() - 1);
					}
				}
				brokenCount = 0;
				for(int i = 0; i < GameConstants.pillars.size();i++){
					if(((BlueBoss)guy).getStompX() > GameConstants.pillars.get(i).getX() &&
							((BlueBoss)guy).getStompX() < GameConstants.pillars.get(i).getX() + GameConstants.pillars.get(i).getWidth()){
						if(GameConstants.pillars.get(i).isBroken() == false)	{
							((BlueBoss)guy).setHealth(((BlueBoss)guy).getHealth() - 1);
							GameConstants.pillars.get(i).setBroken(true);
						}
					}
					if(GameConstants.pillars.get(i).isBroken())
						brokenCount++;
					if(brokenCount == 3){
						platformBroken = true;
					}
				}
				if(platformBroken == true){
					platform.setY(-100);
					platform.setX(-100);
					((BlueBoss)guy).setFalling(true);
				}
			}
		}
	}

	@Override
	public void render(GameContainer container, StateBasedGame sbg,Graphics g)  {
		draw(g);
		g.setColor(Color.black);
		if(platformBroken == false)
			g.fillRect(5*16,18*16,16*39,16*2);
		else{
			g.fillRect(5*16, fallY, 7*39, 16*2);
			g.fillRect(26*16, fallY, 7*39, 16*2);
		}
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
