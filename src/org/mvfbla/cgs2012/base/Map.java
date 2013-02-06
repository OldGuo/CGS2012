package org.mvfbla.cgs2012.base;

import java.util.ArrayList;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

/**
 * @author PenguinToast
 * Class to represent a Tiled map
 */
public class Map {
	private TiledMap map;
	private ArrayList<Tile> boxes = new ArrayList<Tile>();
	private ArrayList<TiledObject> objects = new ArrayList<TiledObject>();
	/**
	 * Creates a new Map
	 * @param tiledFile - Tiled file with map data
	 * @param data - Location of map data files
	 * @throws SlickException
	 */
	public Map(String tiledFile, String data) throws SlickException{
		map = new TiledMap(tiledFile, data);
		// Initialize map tiles
		for (int x = 0; x < map.getWidth(); x++) {
			for (int y = 0; y < map.getHeight(); y++) {
				int tileID = map.getTileId(x, y, 0);
				if (tileID != 0) {
					Tile t = new Tile(x*16, y*16, 16, 16);
					boxes.add(t);
				}
			}
		}
		// Initialize map objects
		for(int i = 0; i < map.getObjectGroupCount(); i++) {
			for(int j = 0; j < map.getObjectCount(i); j++) {
				TiledObject to = new TiledObject(map.getObjectX(i, j), map.getObjectY(i, j), map.getObjectWidth(i, j), map.getObjectHeight(i, j), map.getObjectType(i, j));
				to.addProperty("var", map.getObjectProperty(i, j, "var", null));
				to.addProperty("image", map.getObjectProperty(i, j, "image", null));
				objects.add(to);
			}
		}
	}
	/**
	 * Gets the tiles in this map
	 * @return An arraylist with the tiles in this map
	 */
	public ArrayList<Tile> getBoxes() {
		return boxes;
	}
	/**
	 * Returns the Tiled map
	 * @return The Tiled map
	 */
	public TiledMap getMap() {
		return map;
	}
	/**
	 * Gets the Tiled objects
	 * @return Arraylist with Tiled objects
	 */
	public ArrayList<TiledObject> getObjects() {
		return objects;
	}
}
