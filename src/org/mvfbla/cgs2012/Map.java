package org.mvfbla.cgs2012;

import java.util.ArrayList;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public class Map {
	private TiledMap map;
	private ArrayList<Block> boxes = new ArrayList<Block>();
	public Map(String tiledFile, String data) throws SlickException{
		map = new TiledMap(tiledFile, data);
		for (int x = 0; x < map.getWidth(); x++) {
			for (int y = 0; y < map.getHeight(); y++) {
				int tileID = map.getTileId(x, y, 0);
				if (tileID == 1) {
					boxes.add(new Block(x*16, y*16, 15, 15));
				}
			}
		}
	}
	public ArrayList<Block> getBoxes() {
		return boxes;
	}
	public TiledMap getMap() {
		return map;
	}
}
