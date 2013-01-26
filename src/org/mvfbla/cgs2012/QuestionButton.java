package org.mvfbla.cgs2012;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;

public class QuestionButton {
	private final Image buttonNormal,buttonHover,buttonClick;
	private int x,y;
	private final int length;
	private final int height;
	private boolean hover, clickDown;
	private final int PADDING = 25;
	private final String answerString;
	private final int buttonID;
	public QuestionButton(String answer,Image button,Image hover,Image click,int xPos,int yPos,int l, int h,int id){
		answerString = answer;
		x = xPos;
		y = yPos;
		length = l;
		height = h;
		buttonID = id;
		buttonNormal = button;
		buttonHover = hover;
		buttonClick = click;
	}
	public void draw(Graphics g,int offsetX,int offsetY){
		if(getHover() == true)
			g.drawImage(buttonHover,getX() + offsetX,getY() + offsetY);
		else
			g.drawImage(buttonNormal,getX() + offsetX,getY() + offsetY);
		if(getClick() == true)
			g.drawImage(buttonClick,getX() + offsetX,getY() + offsetY);
		g.drawString(answerString, getX() + PADDING + offsetX, getY() + PADDING + offsetY);
	}
	public void update(GameContainer gc, Input input,int correctAnswer){
		if(input.getMouseX()>getX() && input.getMouseX()<getX() + getLength() && input.getMouseY() > getY()  && input.getMouseY() < getY() + getHeight()){
			if(input.isMouseButtonDown(input.MOUSE_LEFT_BUTTON)){
				setClick(true);
				if(correctAnswer == getID()){
					System.out.println("UR A BEAST");
				}else{
					System.out.println("U SUK");
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
	public Image getNormalButton(){
		return buttonNormal;
	}
	public Image getHoverButton(){
		return buttonHover;
	}
	public Image getClickButton(){
		return buttonClick;
	}
	public boolean getHover(){
		return hover;
	}
	public boolean getClick(){
		return clickDown;
	}
	public void setHover(boolean hovering){
		hover = hovering;
	}
	public void setClick(boolean click){
		clickDown = click;
	}
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	public void setX(int xPos){
		x = xPos;
	}
	public void setY(int yPos){
		y = yPos;
	}
	public int getLength(){
		return length;
	}
	public int getHeight(){
		return height;
	}
	public int getID(){
		return buttonID;
	}
}