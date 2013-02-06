package org.mvfbla.cgs2012;

import java.util.ArrayList;

import org.mvfbla.cgs2012.characters.Characters;
import org.mvfbla.cgs2012.interactable.InteractiveObject;
import org.mvfbla.cgs2012.interactable.MotionSensor;
import org.mvfbla.cgs2012.interactable.MovingTile;
import org.mvfbla.cgs2012.interactable.Pillar;
import org.mvfbla.cgs2012.interactable.Trigger;
import org.mvfbla.cgs2012.menu.MainMenu;
import org.newdawn.slick.Music;


/**
 * @author Young
 * Constants for game values
 */
public class GameConstants {
	//public static Game game;
	// The acceleration of gravity
	public static float GRAVITY = 0.37f;
	public static boolean bossSync = true;
	public static boolean paused = false;

	public static GameLevel level;
	public static MainMenu mainMenu;
	public static ArrayList<GameObject> collidableObjects = new ArrayList<GameObject>();
	public static ArrayList<Trigger> triggers = new ArrayList<Trigger>();
	public static ArrayList<MovingTile> platforms = new ArrayList<MovingTile>();
	public static ArrayList<Characters> enemies = new ArrayList<Characters>();
	public static ArrayList<InteractiveObject> interacts = new ArrayList<InteractiveObject>();
	public static ArrayList<MotionSensor> sensors = new ArrayList<MotionSensor>();
	public static ArrayList<Pillar> pillars = new ArrayList<Pillar>();
	public static int enemiesKilled = 0;
	public static int techUsed = 0;
	public static int bossesDefeated = 0;
	public static int playNum = 0;
	public static int punchRange = 36;

	public static long usedQuestions = 0;
	public static long allUsed = 34359738368L-1;
	public static Map currMap;
	public static int playerMaxHealth = 3;
	public static int playerMaxSpeed = 5;
	public static int wrongCountMax = 5;
	public static int lastBoss = -1;
	//1 blue
	//2 red
	//3 yellow
	public static Music music;
	public static void clear() { //clears arraylists
		collidableObjects.clear();
		sensors.clear();
		triggers.clear();
		platforms.clear();
		enemies.clear();
		interacts.clear();
		pillars.clear();
		paused = false;
		bossSync = true;
		GRAVITY = 0.37f;
		level = null;
	}
	/**
	 * Flips the gravity constant
	 */
	public static void flipGrav(){
		GRAVITY = -GRAVITY;
	}
	/**
	 * Changes between paused and unpaused
	 */
	public static void flipPaused(){
		paused = !paused;
	}
	/**
	 * Flips synchronization with the Black Boss
	 */
	public static void flipSync(){
		bossSync = !bossSync;
	}
	/**
	 * @return - The gravity constant
	 */
	public static float getGrav(){
		return GRAVITY;
	}
	/**
	 * @return - whether the game is paused or not
	 */
	public static boolean getPaused(){
		return paused;
	}
	/**
	 * @return - Whether the player is synced with the Black Boss
	 */
	public static boolean getSync(){
		return bossSync;
	}
}
