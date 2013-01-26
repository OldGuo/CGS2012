package org.mvfbla.cgs2012;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;


public class QuestionWindow{
	private final Color color = new Color(200,200,200,0.45f);
	Image button,buttonHover,buttonClick;
	ArrayList<QuestionButton> questions = new ArrayList<QuestionButton>(4);

	public QuestionWindow() throws SlickException{
		button = new Image("data\\QuestionButton.png");
		buttonHover = new Image("data\\QuestionButtonHover.png");
		buttonClick = new Image("data\\QuestionButtonDown.png");

		questions.add(new QuestionButton(button,buttonHover,buttonClick,255,150,300,75));
		questions.add(new QuestionButton(button,buttonHover,buttonClick,255,230,300,75));
		questions.add(new QuestionButton(button,buttonHover,buttonClick,255,310,300,75));
		questions.add(new QuestionButton(button,buttonHover,buttonClick,255,390,300,75));
	}
	public void draw(Graphics g,int x,int y) throws SlickException {
		g.setColor(color);
		g.fillRect(0,0,800 + x,600 + y);
		for(int i = 0; i < questions.size(); i++){
			if(questions.get(i).getHover() == true)
				questions.get(i).getHoverButton().draw(questions.get(i).getX() + x,questions.get(i).getY() + y);
			else
				questions.get(i).getNormalButton().draw(questions.get(i).getX() + x,questions.get(i).getY() + y);
			if(questions.get(i).getClick() == true)
				questions.get(i).getClickButton().draw(questions.get(i).getX() + x,questions.get(i).getY() + y);
		}
	}
	public void update(GameContainer container){
		 Input input = container.getInput();
		 for(int i = 0; i < questions.size(); i++){
			 if(input.getMouseX()>questions.get(i).getX() && input.getMouseX()<questions.get(i).getX() + questions.get(i).getLength()
					 && input.getMouseY() > questions.get(i).getY()  && input.getMouseY() < questions.get(i).getY() + questions.get(i).getHeight()){
				if(input.isMouseButtonDown(input.MOUSE_LEFT_BUTTON)){
					questions.get(i).setClick(true);
				}else{
					questions.get(i).setClick(false);
				}
				questions.get(i).setHover(true);
			 }else{
				 questions.get(i).setClick(false);
				 questions.get(i).setHover(false);
			 }
		 }
	}
}
