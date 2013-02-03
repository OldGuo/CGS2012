package org.mvfbla.cgs2012;

import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Key extends AnimatedObject implements InteractiveObject {
	private GameLevel level;
	private Trigger trigger;
	public Key(TiledObject to, GameLevel level) throws SlickException {
		super(to.getX(), to.getY(), 16, 32);
		this.level = level;
		trigger = new Trigger(to, new KeyListener());
		GameConstants.triggers.add(trigger);
		int[] one = {0,0};
		int[] two = {1,0};
		int[] dur = {150};
		addAnimation("off",new Animation(new SpriteSheet("data\\maps\\SpacebarNotif.png", 48, 48), one, dur));
		addAnimation("on",new Animation(new SpriteSheet("data\\maps\\SpacebarNotif.png", 48, 48), two, dur));
		playAnimation("on");
	}

	public class KeyListener implements TriggerListener {
		@Override
		public void onEnter(GameObject src) {
			if(src == level.player) {
				playAnimation("off");
				level.unlockElev();
			}
		}
		public void onExit(GameObject src) {}
		public void triggered(GameObject src) {}
	}
	public void interact(GameObject source) {
	}
	public boolean inRange(GameObject source) {
		return false;
	}
	public boolean isActive() {
		return trigger.isActive();
	}
}
