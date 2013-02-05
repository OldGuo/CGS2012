package org.mvfbla.cgs2012;

import java.util.ArrayList;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public class Map {
	private TiledMap map;
	private ArrayList<Tile> boxes = new ArrayList<Tile>();
	private ArrayList<TiledObject> objects = new ArrayList<TiledObject>();
	public Map(String tiledFile, String data) throws SlickException{
		map = new TiledMap(tiledFile, data);
		for (int x = 0; x < map.getWidth(); x++) {
			for (int y = 0; y < map.getHeight(); y++) {
				int tileID = map.getTileId(x, y, 0);
				if (tileID != 0) {
					Tile t = new Tile(x*16, y*16, 16, 16);
					boxes.add(t);
				}
			}
		}
		for(int i = 0; i < map.getObjectGroupCount(); i++) {
			for(int j = 0; j < map.getObjectCount(i); j++) {
				TiledObject to = new TiledObject(map.getObjectX(i, j), map.getObjectY(i, j), map.getObjectWidth(i, j), map.getObjectHeight(i, j), map.getObjectType(i, j));
				to.addProperty("var", map.getObjectProperty(i, j, "var", null));
				to.addProperty("image", map.getObjectProperty(i, j, "image", null));
				objects.add(to);
			}
		}
	}
	public ArrayList<TiledObject> getObjects() {
		return objects;
	}
	public ArrayList<Tile> getBoxes() {
		return boxes;
	}
	public TiledMap getMap() {
		return map;
	}
}
