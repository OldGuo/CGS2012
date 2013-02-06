package org.mvfbla.cgs2012.interactable;

import org.mvfbla.cgs2012.base.Tile;
import org.mvfbla.cgs2012.utils.GameConstants;
import org.mvfbla.cgs2012.utils.Vector;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.tiled.TileSet;
import org.newdawn.slick.tiled.TiledMap;

/**
 * @author William
 * Represents a moving platform
 */
public class MovingTile extends Tile {
	private boolean movement;
	private Image image;
	private float[] move;
	private int nX, nY, nWidth, nHeight;
	private boolean on;
	/**
	 * Creates a new moving platform with specified attributes
	 * @param x - X location of platform
	 * @param y - Y location of platform
	 * @param width - Width of platform
	 * @param height - Height of platform
	 * @param movement - Movement data for the platform
	 * @param imageData - Image data for the platform
	 */
	public MovingTile(int x, int y, int width, int height, String movement, String imageData) {
		super(x, y, width, height);
		nX = x;
		nY = y;
		nWidth = width;
		nHeight = height;
		move = new float[3];
		// Get movement data
		String[] temp = movement.split(",");
		for(int i = 0; i < move.length; i++)
			move[i] = Float.parseFloat(temp[i]);
		TiledMap map = GameConstants.currMap.getMap();
		// Get image data
		String[] imageArray = imageData.split(",");
		for(int k = 0; k < map.getTileSetCount(); k++) {
			TileSet ts = map.getTileSet(k);
			if(k == Integer.parseInt(imageArray[0])) {
				image = ts.tiles.getSprite(Integer.parseInt(imageArray[1]), Integer.parseInt(imageArray[2]));
			}
		}
	}
	@Override
	public void draw(Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.red);
		for(int x = 0; x < nWidth/16; x++) {
			for(int y = 0; y < nHeight/16; y++) {
				g.drawImage(image, getX()+x*16, getY()+y*16);
			}
		}
		g.setColor(c);
	}
	/**
	 * Checks if the platform is on
	 * @return If the platform is on
	 */
	public boolean isOn() {
		return on;
	}
	/**
	 * Sets the state of the platform
	 * @param on - New state
	 */
	public void setOn(boolean on) {
		this.on = on;
	}
	@Override
	public void update(GameContainer gc, int delta) {
		// Update movement
		if(on) {
			Vector v = new Vector();
			if(getX() == nX && getY() == nY)
				movement = false;
			if(getX() == nX + move[0] && getY() == nY + move[1])
				movement = true;
			if(!movement) {
				v.set((nX + move[0])-getX(), (nY + move[1])-getY());
			} else {
				v.set((nX)-getX(), (nY)-getY());
			}
			v.normalise().scale(move[2]);
			translate(v);
		}
	}
}
