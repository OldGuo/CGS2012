package org.mvfbla.cgs2012;

import org.newdawn.slick.Image;

public class QuestionButton {
	private final Image buttonNormal,buttonHover,buttonClick;
	private int x,y;
	private final int length;
	private final int height;
	private boolean hover, clickDown;
	public QuestionButton(Image button,Image hover,Image click,int xPos,int yPos,int l, int h){
		x = xPos;
		y = yPos;
		length = l;
		height = h;
		buttonNormal = button;
		buttonHover = hover;
		buttonClick = click;
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
}
