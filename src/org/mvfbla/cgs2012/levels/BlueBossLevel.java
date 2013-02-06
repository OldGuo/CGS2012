package org.mvfbla.cgs2012.levels;

import org.mvfbla.cgs2012.base.GameLevel;
import org.mvfbla.cgs2012.base.Map;
import org.mvfbla.cgs2012.base.Tile;
import org.mvfbla.cgs2012.base.TiledObject;
import org.mvfbla.cgs2012.characters.BlueBoss;
import org.mvfbla.cgs2012.characters.Characters;
import org.mvfbla.cgs2012.interactable.Pillar;
import org.mvfbla.cgs2012.interactable.QuestionWindow;
import org.mvfbla.cgs2012.utils.GameConstants;
import org.mvfbla.cgs2012.utils.TypeWriter;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * @author Young
 * The Blue Boss Level
 * Attempts to follow the player and "Stomp" on player.
 */
public class BlueBossLevel extends GameLevel {

	private int brokenCount = 3;
	private boolean platformBroken;
	private Tile platform;
	private int fallY = 18*16;

	private TypeWriter text;
	private QuestionWindow questions;
	private boolean beforeQuestions,needRestart,afterQuestions;

	/**
	 * Sets the ID of the level
	 * @param stateID - ID of the level
	 */
	public BlueBossLevel(int stateID) {
		this.stateID = stateID;
	}
	@Override
	public void enter(GameContainer container, StateBasedGame stateBasedGame) throws SlickException { //When the state is entered
		initStuff();
	}

	@Override
	public int getID() { //returns the ID of the level
		return stateID;
	}

	@Override
	public void init(GameContainer container,StateBasedGame sbg) throws SlickException {
		super.setBackgroundInfo(33, 8);
		//Initializes variables for the level
		afterQuestions = false;
		beforeQuestions = true;
		needRestart = false;
		platformBroken = false;
		map = new Map("data"+GameConstants.separatorChar+"Maps"+GameConstants.separatorChar+"BlueBossLevel_5.tmx","data"+GameConstants.separatorChar+"Maps");
		background = new Image("data"+GameConstants.separatorChar+"Level"+GameConstants.separatorChar+"Background.png");
		platform = new Tile(5*16,18*16,16*39,16*2);
		text = new TypeWriter();
		questions = new QuestionWindow();
	}
	@Override
	public void initObject(TiledObject to) throws SlickException { //Creates level specific objects for the Blue Boss Level
		if(to.getType().equals("pillar")){ //Pillar for interacting with the Blue Boss
			Pillar pillar = new Pillar(to.getX(),to.getY(),48,224);
			GameConstants.pillars.add(pillar);
		}
	}
	@Override
	public void leave(GameContainer container, StateBasedGame stateBasedGame) throws SlickException {} //When the state is left
	@Override
	public void render(GameContainer container, StateBasedGame sbg,Graphics g) throws SlickException{ //Draws the level
		draw(g);
		g.setColor(Color.black);
		if(platformBroken == false) //Draws the platform unless broken
			g.fillRect(5*16,18*16,16*39,16*2);
		else{
			g.fillRect(5*16, fallY, 6*39, 16*2);
			g.fillRect(27*16, fallY, 6*39, 16*2);
		}
		if(player.shouldDisplay()) //Player blinking
			player.draw(g);
		if(transState != 0) { //Fade time
			g.setColor(new Color(0, 0, 0, 1f-(transTime/(float)transLength)));
			g.fillRect(0, 0, 100000, 100000);
		}
		if(deathTime > 0) { //Player death
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
		if(beforeQuestions == true || afterQuestions == true){ //Handles the pre-boss sequence
			try {
				text.draw(g,0,0,720,80);
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}
		if(questions.getAnswering() == true){ //Draws question screen
			questions.draw(g,0,0);
		}
		// Draw pause window if needed
		if(GameConstants.getPaused() == true){ //If paused
			pauseWindow.draw(g,-(int)cameraBox.getOffsetX(),-(int)cameraBox.getOffsetY());
		}
	}
	@Override
	public void update(GameContainer container,StateBasedGame sbg,int delta) throws SlickException {
		//Updates the level
		updateMain(container, sbg, delta);
		if(!GameConstants.getPaused()) {
			if(!afterQuestions){ //Handles the pre-boss sequence
				GameConstants.level.player.setControl(false);
			}
			GameConstants.collidableObjects.add(platform);
			if(platformBroken){
				fallY+=7;
			}
			for(Characters guy : GameConstants.enemies) { //Handles the updating of the Blue Boss
				float tempX=player.getCenterX()-guy.getCenterX();//calculates distance between player and enemy
				double Xdist=Math.pow(tempX, 2);
				double Ydist=Math.pow(player.getCenterY()-guy.getCenterY(), 2);
				float totalDist=(float)Math.sqrt(Xdist+Ydist);
				String name=guy.getClass().toString();
				if(name.equals("class org.mvfbla.cgs2012.characters.BlueBoss")){
					BlueBoss boss = (BlueBoss)guy;
					if(totalDist<boss.getSight()){
						boss.changeSleep(true);
						boss.setDirection(Math.signum(tempX));
						boss.setSpeed(1*Math.signum(tempX));
						
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
			if(beforeQuestions){ //Handles the pre-boss sequence
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
			if(questions.getAnswering() == false && !beforeQuestions){ //Handles the pre-boss sequence
				afterQuestions = true;
			}
			if(afterQuestions == true){ //Handles the pre-boss sequence
				text.setText("I am done with its games. I want answers now. Who am I? Why am I here?" +
						" But there is no answer, this only" +
						" seems to infuriate the figure...   ...   ...   ...   ...   " +
						"                                       ");
				if(needRestart){
					text.restart();
					needRestart = false;
				}
			}
			if(beforeQuestions == true || afterQuestions == true) //Handles the pre-boss sequence
				text.update(container, delta);
			if(questions.getAnswering()){ //Handles the pre-boss sequence
				questions.update(container);
				GameConstants.level.player.setControl(false);
			}
			for(Characters guy : GameConstants.enemies) { //Handles the pre-boss sequence
				String name=guy.getClass().toString();
				if(name.equals("class org.mvfbla.cgs2012.characters.BlueBoss")){
					BlueBoss boss = (BlueBoss)guy;
					if(afterQuestions == true){
						boss.setAttacking(true);
						GameConstants.level.player.setControl(true);
					}
				}
			}
		}
	}
}
