package org.mvfbla.cgs2012;

import java.util.ArrayList;


public class GameConstants {
	//public static Game game;
	// The acceleration of gravity
	public static final float GRAVITY = 0.37f;
	public static ArrayList<GameObject> collidableObjects = new ArrayList<GameObject>();
	public static ArrayList<Trigger> triggers = new ArrayList<Trigger>();
	public static ArrayList<MovingTile> platforms = new ArrayList<MovingTile>();
	public static ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	public static ArrayList<InteractiveObject> interacts = new ArrayList<InteractiveObject>();
	public static void clear() {
		collidableObjects.clear();
		triggers.clear();
		platforms.clear();
		enemies.clear();
		interacts.clear();
	}
	public static Map currMap;
}
