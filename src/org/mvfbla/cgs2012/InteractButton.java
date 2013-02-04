package org.mvfbla.cgs2012;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class InteractButton {
	private final Image buttonNormal,buttonHover,buttonClick;
	private int x,y;
	private final int length;
	private final int height;
	private boolean hover, clickDown;
	private final int PADDING = 30;
	private final String words;
	private String action;

	public InteractButton(String text,int xPos,int yPos,int l, int h,int id) throws SlickException{
		words = text;
		action = " ";
		buttonNormal = new Image("data\\QuestionButton.png");
		buttonHover = new Image("data\\QuestionButtonHover.png");
		buttonClick = new Image("data\\QuestionButtonDown.png");
		x = xPos;
		y = yPos;
		length = l;
		height = h;
	}
	public void draw(Graphics g,int offsetX,int offsetY){
		if(getHover() == true)
			g.drawImage(buttonHover,getX() + offsetX,getY() + offsetY);
		else
			g.drawImage(buttonNormal,getX() + offsetX,getY() + offsetY);
		if(getClick() == true)
			g.drawImage(buttonClick,getX() + offsetX,getY() + offsetY);
		g.setColor(Color.black);
		g.drawString(words,getX() + offsetX + PADDING,getY() + offsetY + PADDING);
	}
	public void update(GameContainer gc, Input input){
		if(input.getMouseX()>getX() && input.getMouseX()<getX() + getLength() && input.getMouseY() > getY()  && input.getMouseY() < getY() + getHeight()){
			if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON)){
				setClick(true);
				if(getText().equals("Play Game")){
					action = "Play Game";
				}else if(getText().equals("Instructions")){
					action = "Instructions";
				}else if(getText().equals("About")){
					action = "About";
				}else if(getText().equals("Quit")){
					action = "Quit";
				}else if(getText().equals("Back")){
					action = "Back";
				}else if(getText().equals("Resume")){
					action = "Resume";
				}else if(getText().equals("Main Menu")){
					action = "Main Menu";
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
	public void clear(){
		action = " ";
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
	public String getText(){
		return words;
	}
	public String getAction(){
		return action;
	}
}
