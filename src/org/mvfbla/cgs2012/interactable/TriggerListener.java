package org.mvfbla.cgs2012.interactable;

import java.util.EventListener;

import org.mvfbla.cgs2012.GameObject;

public interface TriggerListener extends EventListener{
	public abstract void onEnter(GameObject src);
	public abstract void onExit(GameObject src);
	public abstract void triggered(GameObject src);
}
