package org.mvfbla.cgs2012;

import java.util.ArrayList;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.tiled.TiledMap;

public class Map {
	private TiledMap map;
	private ArrayList<Tile> boxes = new ArrayList<Tile>();
	public Map(String tiledFile, String data) throws SlickException{
		map = new TiledMap(tiledFile, data);
		for (int x = 0; x < map.getWidth(); x++) {
			for (int y = 0; y < map.getHeight(); y++) {
				int tileID = map.getTileId(x, y, 0);
				if (tileID != 0) {
					Tile t = new Tile(x*16, y*16, 15, 15);
					boxes.add(t);
				}
			}
		}
	}
	public ArrayList<Tile> getBoxes() {
		return boxes;
	}
	public TiledMap getMap() {
		return map;
	}
}
