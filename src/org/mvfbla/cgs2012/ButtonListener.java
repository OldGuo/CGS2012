package org.mvfbla.cgs2012;

import java.util.EventListener;

/**
 * @author PenguinToast
 * EventListener for buttons
 */
public interface ButtonListener extends EventListener{
	public abstract void buttonPressed(boolean state);
}
