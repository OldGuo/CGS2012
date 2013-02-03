package org.mvfbla.cgs2012;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class QuestionButton extends InteractButton{
	private final int PADDING = 25;
	private final int buttonID;
	private boolean correct;

	public QuestionButton(String answer,int xPos,int yPos,int l, int h,int id) throws SlickException{
		super(answer,xPos,yPos,l,h,id);
		buttonID = id;
		correct = false;
	}
	public void draw(Graphics g,int offsetX,int offsetY){
		super.draw(g,offsetX,offsetY);
	}
	public void update(GameContainer gc, Input input,int correctAnswer){
		if(input.getMouseX()>getX() && input.getMouseX()<getX() + getLength() && input.getMouseY() > getY()  && input.getMouseY() < getY() + getHeight()){
			if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON)){
				setClick(true);
				if(correctAnswer == getID()){
					correct = true;
					System.out.println("correct");
				}else{
					correct = false;
					System.out.println("wrong");
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
	public void setCorrect(boolean c){
		correct = c;
	}
	public boolean isCorrect(){
		return correct;
	}
	public int getID(){
		return buttonID;
	}
}
