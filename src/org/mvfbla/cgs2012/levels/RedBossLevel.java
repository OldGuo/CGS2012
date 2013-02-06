package org.mvfbla.cgs2012.levels;

import org.mvfbla.cgs2012.base.GameLevel;
import org.mvfbla.cgs2012.base.Map;
import org.mvfbla.cgs2012.base.TiledObject;
import org.mvfbla.cgs2012.characters.Characters;
import org.mvfbla.cgs2012.characters.RedBoss;
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
 * Red Boss Level
 * Moves back and to defeat player.
 */
public class RedBossLevel extends GameLevel {

	private TypeWriter text;
	private QuestionWindow questions;
	private boolean beforeQuestions,needRestart,afterQuestions;

	/**
	 * Sets the ID of the level
	 * @param stateID - ID of the level
	 */
	public RedBossLevel(int stateID) {
		this.stateID = stateID;
	}

	@Override
	public void enter(GameContainer container, StateBasedGame stateBasedGame) throws SlickException { //When the level is entered
		initStuff();
	}

	@Override
	public int getID(){ //returns the ID of the level
		return stateID;
	}
	@Override
	public void init(GameContainer container,StateBasedGame sbg) throws SlickException {
		//Initializes Values
		super.setBackgroundInfo(33, 8);
		afterQuestions = false;
		beforeQuestions = true;
		map = new Map("data\\Maps\\RedBossLevel_5.tmx","data\\Maps");
		background = new Image("data\\Level\\Background.png");
		text = new TypeWriter();
		questions = new QuestionWindow();
	}
	@Override
	public void initObject(TiledObject to) throws SlickException {} //Initializes player specific objects, in this case there are none
	@Override
	public void leave(GameContainer container, StateBasedGame stateBasedGame) throws SlickException {} //When the level is exited
	@Override
	public void render(GameContainer container, StateBasedGame sbg,Graphics g) throws SlickException  {
		//Draws the level
		draw(g);
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
		// Draw question window if needed
		if(questions.getAnswering() == true){
			questions.draw(g,-(int)cameraBox.getOffsetX(),-(int)cameraBox.getOffsetY());
		}
		// Draw pause window if needed
		if(GameConstants.getPaused() == true){
			pauseWindow.draw(g,-(int)cameraBox.getOffsetX(),-(int)cameraBox.getOffsetY());
		}
	}

	@Override
	public void update(GameContainer container, StateBasedGame sbg,int delta) throws SlickException { //Updates the Game
		updateMain(container, sbg, delta);
		if(!GameConstants.getPaused()) {
			if(!afterQuestions){
				GameConstants.level.player.setControl(false); //Handles the pre-boss sequence
			}
			for(Characters guy : GameConstants.enemies) { //Determines endings and replay values
				String name=guy.getClass().toString();
				if(name.equals("class org.mvfbla.cgs2012.characters.RedBoss")){
					RedBoss boss = (RedBoss)guy;
					if(!boss.isAlive()){
						transState = 2;
						if((GameConstants.bossesDefeated & 2) != 2) {
							GameConstants.playNum++;
						}
						GameConstants.lastBoss = 2;
						GameConstants.bossesDefeated |= 2;
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
				if(name.equals("class org.mvfbla.cgs2012.characters.RedBoss")){
					RedBoss boss = (RedBoss)guy;
					if(afterQuestions == true){
						boss.setAttacking(true);
						GameConstants.level.player.setControl(true);
					}
				}
			}
		}
	}
}
