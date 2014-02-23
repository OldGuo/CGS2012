package org.mvfbla.cgs2012.interactable;

import java.util.EventListener;

/**
 * @author William Sheu
 * EventListener for buttons
 */
public interface ButtonListener extends EventListener{
	public abstract void buttonPressed(boolean state);
}
