package org.mvfbla.cgs2012.interactable;

import org.mvfbla.cgs2012.base.GameObject;
import org.mvfbla.cgs2012.utils.GameConstants;
import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Button extends AnimatedObject implements InteractiveObject {
	/**
	 * @author PenguinToast
	 * Listener to display the spacebar notification as the player apporaches
	 */
	protected class myListener implements TriggerListener {
		@Override
		public void onEnter(GameObject src) {
			if(src == GameConstants.level.player)
				notif.playAnimation("far");
		}

		@Override
		public void onExit(GameObject src) {
			if(src == GameConstants.level.player)
				notif.playAnimation("near");
		}

		@Override
		public void triggered(GameObject src) {

		}
	}
	protected Trigger trigger;
	public AnimatedObject notif;
	protected ButtonListener listener;
	protected long lastPress = 0;
	protected long cooldown = 500;
	protected boolean on;

	protected boolean used = false;
	/**
	 * Creates a new button at the specified location(Mainly for elevator)
	 * @param x - X location to create the button at
	 * @param y - Y location to create the button at
	 * @throws SlickException
	 */
	protected Button(int x, int y) throws SlickException {
		super(x, y, 96, 85);
	}
	/**
	 * Creates a generic button with the specified coordinates and listener
	 * @param x - X location to create the button at
	 * @param y - Y location to create the button at
	 * @param bl - ButtonListener for this button to call when it is pressed
	 * @throws SlickException
	 */
	public Button(int x, int y, ButtonListener bl) throws SlickException {
		super(x, y, 32, 32);
		listener = bl;
		// Create button on and off buttons
		addAnimation("off", new Animation(new SpriteSheet("data\\maps\\ButtonOff.png", 32, 32), 150));
		addAnimation("on", new Animation(new SpriteSheet("data\\maps\\ButtonOn.png", 32, 32), 150));
		// Create notification near and far images
		notif = new AnimatedObject(-8,-40,48,48);
		int[] one = {0,0};
		int[] two = {1,0};
		int[] dur = {150};
		notif.addAnimation("near",new Animation(new SpriteSheet("data\\maps\\SpacebarNotif.png", 48, 48), one, dur));
		notif.addAnimation("far",new Animation(new SpriteSheet("data\\maps\\SpacebarNotif.png", 48, 48), two, dur));
		addObject(notif);

		// Makes a trigger for showing the notification when the player is near
		trigger = new Trigger(x-16, y-16, 64, 64, new myListener());
		GameConstants.triggers.add(trigger);
		playAnimation("off");
		notif.playAnimation("near");
	}

	/**
	 * Returns the state of this button
	 * @return The state of this button
	 */
	public boolean getState() {
		return on;
	}
	/**
	 * Returns the trigger that displays the notification
	 * @return - The trigger the displays the notification
	 */
	public Trigger getTrigger() {
		return trigger;
	}

	@Override
	public boolean inRange(GameObject source) {
		return trigger.collides(source);
	}

	@Override
	public void interact(GameObject source) {
		// Increase tech used variable, and display a question
		if(!used) {
			GameConstants.techUsed++;
			GameConstants.level.buttonQuestion = true;
			GameConstants.level.questions.setAnswering(true);
		}
		used = true;
		// Make sure the player cannot hit the button more that one time per cooldown milliseconds
		long time = System.currentTimeMillis();
		if(time-lastPress >= cooldown) {
			lastPress = time;
			if(on) {
				playAnimation("off");
			} else {
				playAnimation("on");
			}
			on = !on;
			listener.buttonPressed(on);
		}
	}
	@Override
	public boolean isActive() {
		return trigger.isActive();
	}
	/**
	 * Sets the ButtonListener for this button
	 * @param listener - The new ButtonListener
	 */
	public void setListener(ButtonListener listener) {
		this.listener = listener;
	}
	/**
	 * Sets the state of this button(on/off)
	 * @param on - State to set this button to
	 */
	public void setState(boolean on) {
		this.on = on;
	}

}
