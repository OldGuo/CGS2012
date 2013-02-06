package org.mvfbla.cgs2012.interactable;

import org.mvfbla.cgs2012.utils.GameConstants;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 * @author Young
 * More specific type of InteractButton
 * Has right and wrong answers
 */
public class QuestionButton extends InteractButton{
	private final int PADDING = 30;
	private final int buttonID;
	private final Image buttonWrong;
	private boolean correct,wrongPressed;
	private final String questionAnswer;

	/**
	 * @param answer - text on the button
	 * @param xPos - X position
	 * @param yPos - Y position
	 * @param l - length
	 * @param h - height
	 * @param id - ID of the button
	 * @throws SlickException
	 */
	public QuestionButton(String answer,int xPos,int yPos,int l, int h,int id) throws SlickException{
		super(answer,xPos,yPos,l,h,id);
		questionAnswer = answer;
		buttonID = id;
		correct = false;
		buttonWrong = new Image("data"+GameConstants.separatorChar+"Questions"+GameConstants.separatorChar+"QuestionButtonWrong.png");
	}
	@Override
	public void draw(Graphics g,int offsetX,int offsetY){
		//Draws the button on screen
		if(pressedAndWrong() == true){
			g.drawImage(buttonWrong,getX() + offsetX,getY() + offsetY);
			g.setColor(Color.gray);
			g.drawString(questionAnswer,getX() + offsetX + PADDING,getY() + offsetY + PADDING);
		}else{
			super.draw(g,offsetX,offsetY);
		}
	}
	/**
	 * @return - returns the ID of the button
	 */
	public int getID(){
		return buttonID;
	}
	/**
	 * @return - returns whether correct or not
	 */
	public boolean isCorrect(){
		return correct;
	}
	/**
	 * @return - if pressed, but incorrect
	 */
	public boolean pressedAndWrong(){
		return wrongPressed;
	}
	/**
	 * @param c - Whether correct or not
	 */
	public void setCorrect(boolean c){
		correct = c;
	}
	public void update(GameContainer gc, Input input,int correctAnswer){
		//Updates the button
		if(input.getMouseX()>getX() && input.getMouseX()<getX() + getLength() && input.getMouseY() > getY()  && input.getMouseY() < getY() + getHeight()){
			if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON) && wrongPressed == false){
				setClick(true);
				if(correctAnswer == getID()){ //Checks whether the choice is right or wrong
					correct = true;
				}else{
					correct = false;
					wrongPressed = true;
					GameConstants.level.wrongCount++;
					if(GameConstants.level.wrongCount % GameConstants.wrongCountMax == 0) {
						GameConstants.level.player.setInitialHealth(GameConstants.level.player.getHealth()-1);
					}
				}
			}else{
				setClick(false);
			}
			setHover(true);
		 }else{
			 setClick(false);
			 setHover(false);
		 }
	}
}
