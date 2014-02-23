package org.mvfbla.cgs2012.interactable;

import java.util.EventListener;

import org.mvfbla.cgs2012.base.GameObject;

/**
 * @author William Sheu
 * Listener for trigger areas
 */
public interface TriggerListener extends EventListener{
	/**
	 * Called when the trigger is entered
	 * @param src - The GameObject that entered the trigger
	 */
	public abstract void onEnter(GameObject src);
	/**
	 * Called when the trigger is exited
	 * @param src - The GameObject that exited the trigger
	 */
	public abstract void onExit(GameObject src);
	/**
	 * Called every frame an object is inside the trigger
	 * @param src - The GameObject inside thr trigger
	 */
	public abstract void triggered(GameObject src);
}
