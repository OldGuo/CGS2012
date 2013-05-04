package org.mvfbla.cgs2012.base;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;


/**
 * @author William Sheu
 * Represents a game tile
 */
public class Tile extends GameObject{
	private static final long serialVersionUID = -3752843176428390925L;

	/**
	 * Constructs a new tile with the specified location and dimension
	 * @param x - X location of tile
	 * @param y - Y location of tile
	 * @param width - width of tile
	 * @param height - height of tile
	 */
	public Tile(int x, int y, int width, int height) {
		super(x, y, width, height);
	}

	@Override
	public void draw(Graphics g) {
	}

	@Override
	public void update(GameContainer gc, int delta) {
	}

}
