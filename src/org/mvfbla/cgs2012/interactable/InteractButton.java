package org.mvfbla.cgs2012.interactable;

import org.mvfbla.cgs2012.utils.GameConstants;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 * @author Young Buttons the user will interact with to navigate menus
 */
public class InteractButton {
	private final Image buttonNormal, buttonHover, buttonClick;
	private int x, y;
	private final int length;
	private final int height;
	private boolean hover, clickDown;
	private final int PADDING = 30;
	private String words;
	private String action;
	protected int selectionState = -1;

	/**
	 * @param text
	 *            - Text of the button
	 * @param xPos
	 *            - X position of the button
	 * @param yPos
	 *            - Y position of the button
	 * @param l
	 *            - Length
	 * @param h
	 *            - Height
	 * @param id
	 *            - ID
	 * @throws SlickException
	 */
	public InteractButton(String text, int xPos, int yPos, int l, int h, int id) throws SlickException {
		words = text;
		action = " ";
		buttonNormal = new Image("data" + GameConstants.separatorChar + "Questions" + GameConstants.separatorChar
				+ "QuestionButton.png");
		buttonHover = new Image("data" + GameConstants.separatorChar + "Questions" + GameConstants.separatorChar
				+ "QuestionButtonHover.png");
		buttonClick = new Image("data" + GameConstants.separatorChar + "Questions" + GameConstants.separatorChar
				+ "QuestionButtonDown.png");
		x = xPos;
		y = yPos;
		length = l;
		height = h;
	}

	/**
	 * Clears the text on the button
	 */
	public void clear() {
		action = " ";
	}

	/**
	 * @param g
	 *            - Graphics
	 * @param offsetX
	 *            - X offset from the camera, centers the Buttons on screen
	 * @param offsetY
	 *            - Y offset from the camera, centers the Buttons on screen
	 */
	public void draw(Graphics g, int offsetX, int offsetY) {
		if (getHover() == true)
			g.drawImage(buttonHover, getX() + offsetX, getY() + offsetY);
		else
			g.drawImage(buttonNormal, getX() + offsetX, getY() + offsetY);
		if (getClick() == true)
			g.drawImage(buttonClick, getX() + offsetX, getY() + offsetY);
		g.setColor(Color.black);
		g.drawString(words, getX() + offsetX + PADDING, getY() + offsetY + PADDING);
	}

	/**
	 * @return returns the action the button will perform
	 */
	public String getAction() {
		return action;
	}

	/**
	 * @return - Clicked or not
	 */
	public boolean getClick() {
		return clickDown;
	}

	/**
	 * @return - Image in clicked state
	 */
	public Image getClickButton() {
		return buttonClick;
	}

	/**
	 * @return - height of the button
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @return - Hovering or not
	 */
	public boolean getHover() {
		return hover;
	}

	/**
	 * @return - Image in hover state
	 */
	public Image getHoverButton() {
		return buttonHover;
	}

	/**
	 * @return - length of the button
	 */
	public int getLength() {
		return length;
	}

	/**
	 * @return - Image in normal state
	 */
	public Image getNormalButton() {
		return buttonNormal;
	}

	/**
	 * @return - text on the button
	 */
	public String getText() {
		return words;
	}

	/**
	 * @return - X position of the button
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return - Y position of the button
	 */
	public int getY() {
		return y;
	}

	/**
	 * @param click
	 *            - Set whether clicked or not
	 */
	public void setClick(boolean click) {
		clickDown = click;
	}

	/**
	 * @param hovering
	 *            - Set whether hovering or not
	 */
	public void setHover(boolean hovering) {
		hover = hovering;
	}

	/**
	 * Sets the text for the question
	 * 
	 * @param text
	 *            - Text for the question
	 */
	public void setText(String text) {
		words = text;
	}

	/**
	 * @param xPos
	 *            - sets the x position
	 */
	public void setX(int xPos) {
		x = xPos;
	}

	/**
	 * @param yPos
	 *            - sets the y position
	 */
	public void setY(int yPos) {
		y = yPos;
	}

	/**
	 * Sets the selection state of this button
	 * 
	 * @param s
	 *            - Selection state
	 */
	public void setSelectionState(int s) {
		selectionState = s;
	}

	/**
	 * @param gc
	 *            - GameContainer
	 * @param input
	 *            - Gets user input
	 */
	public void update(GameContainer gc, Input input) {
		if (input.getMouseX() > getX() && input.getMouseX() < getX() + getLength() && input.getMouseY() > getY()
				&& input.getMouseY() < getY() + getHeight()) {
			if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
				setClick(true);
				if (getText().matches("New Game[+]*")) {
					action = "Play Game";
				} else if (getText().equals("Instructions")) {
					action = "Instructions";
				} else if (getText().equals("About")) {
					action = "About";
				} else if (getText().equals("Quit")) {
					action = "Quit";
				} else if (getText().equals("Back")) {
					action = "Back";
				} else if (getText().equals("Resume")) {
					action = "Resume";
				} else if (getText().equals("Main Menu")) {
					action = "Main Menu";
				}
			} else {
				setClick(false);
			}
			setHover(true);
		} else {
			setClick(false);
			setHover(false);
		}
	}
}
