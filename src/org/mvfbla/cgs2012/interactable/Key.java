package org.mvfbla.cgs2012.interactable;

import org.mvfbla.cgs2012.base.GameLevel;
import org.mvfbla.cgs2012.base.GameObject;
import org.mvfbla.cgs2012.base.TiledObject;
import org.mvfbla.cgs2012.utils.GameConstants;
import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/**
 * @author Young
 * Key used for elevator level
 */
public class Key extends AnimatedObject implements InteractiveObject {
	/**
	 * @author PenguinToast
	 * Listener for when the player picks up the key
	 */
	public class KeyListener implements TriggerListener {
		@Override
		public void onEnter(GameObject src) {
			if(src == level.player) {
				playAnimation("off");
				level.unlockElev(0);
			}
		}
		@Override
		public void onExit(GameObject src) {}
		@Override
		public void triggered(GameObject src) {}
	}
	private final GameLevel level;
	private final Trigger trigger;

	/**
	 * Creates a key from the specified object
	 * @param to - The TiledObject with location data
	 * @param level - The level this key is in
	 * @throws SlickException
	 */
	public Key(TiledObject to, GameLevel level) throws SlickException {
		super(to.getX(), to.getY(), 16, 32);
		this.level = level;
		trigger = new Trigger(to, new KeyListener());
		GameConstants.triggers.add(trigger);
		int[] one = {0,0};
		int[] two = {1,0};
		int[] dur = {150};
		addAnimation("off",new Animation(new SpriteSheet("data"+GameConstants.separatorChar+"maps"+GameConstants.separatorChar+"SpacebarNotif.png", 48, 48), one, dur));
		addAnimation("on",new Animation(new SpriteSheet("data"+GameConstants.separatorChar+"Level"+GameConstants.separatorChar+"key.png", 32, 40), one, dur));
		playAnimation("on");
	}
	@Override
	public boolean inRange(GameObject source) {
		return false;
	}
	@Override
	public void interact(GameObject source) {
	}
	@Override
	public boolean isActive() {
		return trigger.isActive();
	}
}
