package org.mvfbla.cgs2012;

import java.util.ArrayList;


public class GameConstants {
	//public static Game game;
	// The acceleration of gravity
	public static float GRAVITY = 0.37f;
	public static ArrayList<GameObject> collidableObjects = new ArrayList<GameObject>();
	public static ArrayList<Trigger> triggers = new ArrayList<Trigger>();
	public static ArrayList<MovingTile> platforms = new ArrayList<MovingTile>();
	public static ArrayList<Characters> enemies = new ArrayList<Characters>();
	public static ArrayList<InteractiveObject> interacts = new ArrayList<InteractiveObject>();
	public static ArrayList<MotionSensor> sensors = new ArrayList<MotionSensor>();
	public static void clear() {
		collidableObjects.clear();
		sensors.clear();
		triggers.clear();
		platforms.clear();
		enemies.clear();
		interacts.clear();
	}
	public static void flipGrav(){
		GRAVITY = -GRAVITY;
	}
	public static float getGrav(){
		return GRAVITY;
	}
	public static Map currMap;
}
