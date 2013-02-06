package org.mvfbla.cgs2012;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;


/**
 * @author Young
 * Window that shows on screen for user to answer questions
 */
public class QuestionWindow{
	private final Color color = new Color(200,200,200,0.45f);
	private boolean answering;
	ArrayList<QuestionButton> questions;
	QuestionReader reader;
	private int randQuestion;
	private int randCorrect;

	/**
	 * Initializes the question window
	 * @throws SlickException
	 */
	public QuestionWindow() throws SlickException{
		init();
	}
	/**
	 * Initializes values
	 * @throws SlickException
	 */
	public void init() throws SlickException{
		do {
			randQuestion = (int)(Math.random()*35); //Chooses a random question from the list
		} while(isUsed(randQuestion));
		GameConstants.usedQuestions |= powerTwo(randQuestion);
		randCorrect = (int)(Math.random()*4);
		//randCorrect = 0;
		reader = new QuestionReader("data\\questions.txt");
		questions = new ArrayList<QuestionButton>(4);

		switch(randCorrect){
		case 0: //first answer right
			questions.add(new QuestionButton(reader.getCorrectAns().get(randQuestion),255,150,300,75,0));
			questions.add(new QuestionButton(reader.getWrongAns1().get(randQuestion),255,230,300,75,1));
			questions.add(new QuestionButton(reader.getWrongAns2().get(randQuestion),255,310,300,75,2));
			questions.add(new QuestionButton(reader.getWrongAns3().get(randQuestion),255,390,300,75,3));
			break;
		case 1: //second answer right
			questions.add(new QuestionButton(reader.getWrongAns1().get(randQuestion),255,150,300,75,0));
			questions.add(new QuestionButton(reader.getCorrectAns().get(randQuestion),255,230,300,75,1));
			questions.add(new QuestionButton(reader.getWrongAns2().get(randQuestion),255,310,300,75,2));
			questions.add(new QuestionButton(reader.getWrongAns3().get(randQuestion),255,390,300,75,3));
			break;
		case 2: //third answer right
			questions.add(new QuestionButton(reader.getWrongAns1().get(randQuestion),255,150,300,75,0));
			questions.add(new QuestionButton(reader.getWrongAns2().get(randQuestion),255,230,300,75,1));
			questions.add(new QuestionButton(reader.getCorrectAns().get(randQuestion),255,310,300,75,2));
			questions.add(new QuestionButton(reader.getWrongAns3().get(randQuestion),255,390,300,75,3));
			break;
		case 3: //fourth answer right
			questions.add(new QuestionButton(reader.getWrongAns1().get(randQuestion),255,150,300,75,0));
			questions.add(new QuestionButton(reader.getWrongAns2().get(randQuestion),255,230,300,75,1));
			questions.add(new QuestionButton(reader.getWrongAns3().get(randQuestion),255,310,300,75,2));
			questions.add(new QuestionButton(reader.getCorrectAns().get(randQuestion),255,390,300,75,3));
			break;
		}
	}
	/**
	 * @param num - number of questions used
	 * @return - returns whether all questions are used or not
	 */
	private boolean isUsed(int num) {
		if(GameConstants.usedQuestions == GameConstants.allUsed) {
			GameConstants.usedQuestions = 0;
			return false;
		}
		long power = powerTwo(num);
		return (GameConstants.usedQuestions & power) == power;
	}
	private long powerTwo(int num) {
		if(num == 0)
			return 1;
		long out = 2;
		for(int i = 1; i < num; i++) {
			out*=2;
		}
		return out;
	}
	/**
	 * @param g - Graphics
	 * @param x - x position
	 * @param y - y position
	 */
	public void draw(Graphics g,int x,int y){
		g.setColor(color);
		g.fillRect(-300,-300,1100 + x,900 + y);
		g.setColor(Color.black);
		String question = reader.getQuestions().get(randQuestion);
		String[] split = question.split(" - ");
		g.drawString(split[0], 400 + x - g.getFont().getWidth(split[0])/2,60 + y);
		int questionLength = g.getFont().getWidth(split[1]);
		g.drawString(split[1],400 + x - questionLength/2,100 + y);
		for(int i = 0; i < questions.size(); i++){
			questions.get(i).draw(g,x,y);
		}
		g.setColor(Color.black);
		int max = GameConstants.wrongCountMax;
		int current = GameConstants.level.wrongCount;
		g.drawString(max - current % max + " more incorrect questions before damage will be taken.",160+x,500+y);
	}
	/**
	 * Updates the QuestionWindow
	 * @param container - GameContainer
	 */
	public void update(GameContainer container){
		Input input = container.getInput();
		for(int i = 0; i < questions.size(); i++){
			questions.get(i).update(container,input,randCorrect);
			if(questions.get(i).isCorrect() == true){
				try {
					init();
				} catch (SlickException e) {
					e.printStackTrace();
				}
				if(GameConstants.level.questionCount >= 4 || GameConstants.level.buttonQuestion) {
					GameConstants.level.buttonQuestion = false;
					GameConstants.level.questionCount++;
					answering = false;
				} else {
					GameConstants.level.questionCount++;
				}
			}
		}
	}
	/**
	 * @return - Whether the player is answering questions
	 */
	public boolean getAnswering(){
		return answering;
	}
	/**
	 * @param a - Sets whether the user is answering questions
	 */
	public void setAnswering(boolean a){
		answering = a;
	}
	/**
	 * @return - returns which question is currently being asked
	 */
	public int whichQuestion(){
		return randQuestion;
	}
}
