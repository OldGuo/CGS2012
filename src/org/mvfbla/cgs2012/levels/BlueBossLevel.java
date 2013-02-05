package org.mvfbla.cgs2012.levels;

import org.mvfbla.cgs2012.BlueBoss;
import org.mvfbla.cgs2012.CameraObject;
import org.mvfbla.cgs2012.Characters;
import org.mvfbla.cgs2012.GameConstants;
import org.mvfbla.cgs2012.GameLevel;
import org.mvfbla.cgs2012.Map;
import org.mvfbla.cgs2012.Pillar;
import org.mvfbla.cgs2012.Player;
import org.mvfbla.cgs2012.QuestionWindow;
import org.mvfbla.cgs2012.Tile;
import org.mvfbla.cgs2012.TiledObject;
import org.mvfbla.cgs2012.TypeWriter;
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
	private int fallY = 18*16;

	private TypeWriter text;
	private QuestionWindow questions;
	private boolean beforeQuestions,needRestart,afterQuestions;

	public BlueBossLevel(int stateID) {
		this.stateID = stateID;
		// TODO Auto-generated constructor stub
	}
	private final static int MAP_WIDTH = 800;
	private final static int MAP_HEIGHT = 600;

	@Override
	public void init(GameContainer container,StateBasedGame sbg) throws SlickException {
		super.setBackgroundInfo(33, 8);
		afterQuestions = false;
		beforeQuestions = true;
		map = new Map("data\\Maps\\BlueBossLevel_5.tmx","data\\Maps");
		player = new Player(300, 496);
		cameraBox = new CameraObject(player,2000,1300);
		background = new Image("data\\Background.png");
		platform = new Tile(5*16,18*16,16*39,16*2);
		text = new TypeWriter();
		questions = new QuestionWindow();
	}

	@Override
	public void update(GameContainer container,StateBasedGame sbg,int delta) throws SlickException {
		updateMain(container, sbg, delta);
		if(!afterQuestions){
			GameConstants.level.player.setControl(false);
		}
		GameConstants.collidableObjects.add(platform);
		if(platformBroken){
			fallY+=7;
		}
		for(Characters guy : GameConstants.enemies) {
			float tempX=player.getCenterX()-guy.getCenterX();//calculates distance between player and enemy
			double Xdist=Math.pow(tempX, 2);
			double Ydist=Math.pow(player.getCenterY()-guy.getCenterY(), 2);
			float totalDist=(float)Math.sqrt(Xdist+Ydist);
			String name=guy.getClass().toString();
			if(name.equals("class org.mvfbla.cgs2012.BlueBoss")){
				BlueBoss boss = (BlueBoss)guy;
				if(totalDist<boss.getSight()){
					boss.changeSleep(true);
					boss.setDirection(Math.signum(tempX));
					boss.setSpeed(1*Math.signum(tempX));
					//System.out.println(boss.getSpeed());
				}else{
					boss.changeSleep(false);

				}
				if(player.getCenterX() >= boss.getStompX() && player.getCenterX() <= boss.getStompX() + 64){
					if(player.getCenterY() >= boss.getStompY() && player.getCenterY() <= boss.getStompY() + 50){
						player.setHealth(player.getHealth() - 1);
					}
				}
				brokenCount = 0;
				for(int i = 0; i < GameConstants.pillars.size();i++){
					if(boss.getStompX() > GameConstants.pillars.get(i).getX() &&
							boss.getStompX() < GameConstants.pillars.get(i).getX() + GameConstants.pillars.get(i).getWidth()){
						if(GameConstants.pillars.get(i).isBroken() == false)	{
							boss.setHealth(boss.getHealth() - 1);
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
					boss.setFalling(true);
					transState = 2;
					if((GameConstants.bossesDefeated & 4) != 4) {
						GameConstants.playNum++;
					}
					GameConstants.lastBoss = 1;
					GameConstants.bossesDefeated |= 4;
				}
			}
		}
		if(beforeQuestions){
			text.setText("The moment I enter, I sense the air of superiority emanating from the figure in the room. " +
						 "I want to ask it so many questions. I want to understand.  A stream of " +
						 "questions pour from my mouth. But it only responds with questions of its own." +
						 "                                       ");
			if(text.isFinished() && beforeQuestions){
				beforeQuestions = false;
				needRestart = true;
				questions.setAnswering(true);
			}
		}
		if(questions.getAnswering() == false && !beforeQuestions){
			afterQuestions = true;
		}
		if(afterQuestions == true){
			text.setText("I am done with its games. I want answers now. Who am I? Why am I here?" +
					 " But there is no answer, this only" +
					 " seems to infuriate the figure...   ...   ...   ...   ...   " +
					 "                                       ");
			if(needRestart){
				text.restart();
				needRestart = false;
			}
		}
		if(beforeQuestions == true || afterQuestions == true)
			text.update(container, delta);
		if(questions.getAnswering()){
			questions.update(container);
			GameConstants.level.player.setControl(false);
		}
		for(Characters guy : GameConstants.enemies) {
			String name=guy.getClass().toString();
			if(name.equals("class org.mvfbla.cgs2012.BlueBoss")){
				BlueBoss boss = (BlueBoss)guy;
				if(afterQuestions == true){
					boss.setAttacking(true);
					GameConstants.level.player.setControl(true);
				}
			}
		}
	}

	@Override
	public void render(GameContainer container, StateBasedGame sbg,Graphics g) throws SlickException  {
		draw(g);
		g.setColor(Color.black);
		if(platformBroken == false)
			g.fillRect(5*16,18*16,16*39,16*2);
		else{
			g.fillRect(5*16, fallY, 6*39, 16*2);
			g.fillRect(27*16, fallY, 6*39, 16*2);
		}
		if(player.shouldDisplay())
			player.draw(g);
		if(transState != 0) {
			g.setColor(new Color(0, 0, 0, 1f-(transTime/(float)transLength)));
			g.fillRect(0, 0, 100000, 100000);
		}
		if(deathTime > 0) {
			player.stopAnimation();
			player.draw(g);
			long time = deathTime % deathDelay;
			float prog = time/(float)deathDelay;
			if(prog > 0.5f)
				prog = 1-prog;
			Color c = new Color(0, 0, 0, prog);
			g.setColor(c);
			g.fillRect(0, 0, 100000, 100000);
		}
		if(beforeQuestions == true || afterQuestions == true){
			try {
				text.draw(g,0,0,720,80);
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}
		if(questions.getAnswering() == true){
			questions.draw(g,0,0);
		}
		// Draw pause window if needed
		if(GameConstants.getPaused() == true){
			pauseWindow.draw(g,-(int)cameraBox.getOffsetX(),-(int)cameraBox.getOffsetY());
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

	@Override
	public void initObject(TiledObject to) throws SlickException {
		if(to.getType().equals("pillar")){
			Pillar pillar = new Pillar(to.getX(),to.getY(),48,224);
			GameConstants.pillars.add(pillar);
		}
	}
}
