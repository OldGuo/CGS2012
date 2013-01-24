package org.mvfbla.cgs2012;

import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Button extends AnimatedObject implements InteractiveObject {
	private Trigger trigger;
	private AnimatedObject arm;
	private ButtonListener listener;
	private long lastPress = 0;
	private long cooldown = 1000;
	public Button(int x, int y, ButtonListener bl) throws SlickException {
		super(x, y, 32, 32);
		listener = bl;
		addAnimation("off", new Animation(new SpriteSheet("data\\maps\\LargeBlackSquare.png", 32, 32), 150));
		addAnimation("on", new Animation(new SpriteSheet("data\\maps\\Tile.png", 32, 32), 150));
		arm = new AnimatedObject(0,0,48,48);
		int[] one = {0,0};
		int[] two = {1,0};
		int[] dur = {150};
		arm.addAnimation("near",new Animation(new SpriteSheet("data\\PlayerAttackRight.png", 48, 48), one, dur));
		arm.addAnimation("far",new Animation(new SpriteSheet("data\\PlayerAttackRight.png", 48, 48), two, dur));
		addObject(arm);
		trigger = new Trigger(x-16, y-16, 64, 64, new myListener());
		GameConstants.triggers.add(trigger);
		playAnimation("off");
		arm.playAnimation("near");
	}

	@Override
	public void interact(GameObject source) {
		long time = System.currentTimeMillis();
		if(time-lastPress >= cooldown) {
			lastPress = time;
			listener.buttonPressed();
		}
	}
	
	private class myListener implements TriggerListener {

		@Override
		public void onEnter(GameObject src) {
			playAnimation("on");
			arm.playAnimation("far");
		}

		@Override
		public void onExit(GameObject src) {
			playAnimation("off");
			arm.playAnimation("near");
		}

		@Override
		public void triggered(GameObject src) {
			
		}
		
	}

	@Override
	public boolean inRange(GameObject source) {
		return trigger.collides(source);
	}

}
