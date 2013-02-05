package org.mvfbla.cgs2012;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class RedBossLevel extends GameLevel {

	private TypeWriter text;
	private QuestionWindow questions;
	private boolean beforeQuestions,needRestart,afterQuestions;

	public RedBossLevel(int stateID) {
		this.stateID = stateID;
	}
	private final static int MAP_WIDTH = 780;
	private final static int MAP_HEIGHT = 600;

	@Override
	public void init(GameContainer container,StateBasedGame sbg) throws SlickException {
		super.setBackgroundInfo(33, 8);
		beforeQuestions = true;
		map = new Map("data\\Maps\\RedBossLevel_5.tmx","data\\Maps");
		player = new Player(300, 496);
		cameraBox = new CameraObject(player,2000,1000);
		background = new Image("data\\Background.png");
		text = new TypeWriter();
		questions = new QuestionWindow();
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
					GameConstants.bossesDefeated |= 0b010;
				}
			}
		}
		if(beforeQuestions){
			text.setText("The moment I enter, I sense the air of superiority emanating from the figure in the room. " +
						 "I want to ask it so many questions. I want to understand.  A stream of " +
						 "questions pour from my mouth. But it only respond with questions of its own." +
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
			text.setText("I was done with its games. I want answers now. Who am I? Why am I here?" +
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
		}
		for(Characters guy : GameConstants.enemies) {
			String name=guy.getClass().toString();
			if(name.equals("class org.mvfbla.cgs2012.RedBoss")){
				RedBoss boss = (RedBoss)guy;
				if(afterQuestions == true){
					boss.setAttacking(true);
				}
			}
		}
	}

	@Override
	public void render(GameContainer container, StateBasedGame sbg,Graphics g)  {
		draw(g);
		if(beforeQuestions == true || afterQuestions == true){
			try {
				text.draw(g,0,0);
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}
		if(questions.getAnswering() == true){
			questions.draw(g,0,0);
		}
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

	}
}
