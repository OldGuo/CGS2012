package org.mvfbla.cgs2012;

import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/**
 * @author Young
 * Key used for elevator level
 */
public class Key extends AnimatedObject implements InteractiveObject {
	private final GameLevel level;
	private final Trigger trigger;
	public Key(TiledObject to, GameLevel level) throws SlickException {
		super(to.getX(), to.getY(), 16, 32);
		this.level = level;
		trigger = new Trigger(to, new KeyListener());
		GameConstants.triggers.add(trigger);
		int[] one = {0,0};
		int[] two = {1,0};
		int[] dur = {150};
		addAnimation("off",new Animation(new SpriteSheet("data\\maps\\SpacebarNotif.png", 48, 48), one, dur));
		addAnimation("on",new Animation(new SpriteSheet("data\\key.png", 32, 40), one, dur));
		playAnimation("on");
	}

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
	@Override
	public void interact(GameObject source) {
	}
	@Override
	public boolean inRange(GameObject source) {
		return false;
	}
	@Override
	public boolean isActive() {
		return trigger.isActive();
	}
}
