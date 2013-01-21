package org.mvfbla.cgs2012;

import java.util.EventListener;

public interface TriggerListener extends EventListener{
	public abstract void onEnter(GameObject src);
	public abstract void onExit(GameObject src);
	public abstract void triggered(GameObject src);
}
