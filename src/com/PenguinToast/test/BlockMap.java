package com.PenguinToast.test;

import java.util.ArrayList;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public class BlockMap {
	public static TiledMap tmap;
	public static int mapWidth;
	public static int mapHeight;
	public static ArrayList<Rectangle> entities;

	public BlockMap(String ref) throws SlickException {
		entities = new ArrayList<Rectangle>();
		tmap = new TiledMap(ref, "data");
		mapWidth = tmap.getWidth() * tmap.getTileWidth();
		mapHeight = tmap.getHeight() * tmap.getTileHeight();

		for (int x = 0; x < tmap.getWidth(); x++) {
			for (int y = 0; y < tmap.getHeight(); y++) {
				int tileID = tmap.getTileId(x, y, 0);
				if (tileID == 1) {
					entities.add(new Rectangle(x*16, y*16, 15, 15));
				}
			}
		}
	}
}