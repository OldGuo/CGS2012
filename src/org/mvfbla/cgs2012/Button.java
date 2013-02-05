package org.mvfbla.cgs2012;

import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Button extends AnimatedObject implements InteractiveObject {
	protected Trigger trigger;
	public Trigger getTrigger() {
		return trigger;
	}
	protected AnimatedObject notif;
	protected ButtonListener listener;
	public void setListener(ButtonListener listener) {
		this.listener = listener;
	}
	protected long lastPress = 0;
	protected long cooldown = 500;
	protected boolean on;
	protected boolean used = false;
	protected Button(int x, int y) throws SlickException {
		super(x, y, 96, 85);
	}
	public Button(int x, int y, ButtonListener bl) throws SlickException {
		super(x, y, 32, 32);
		listener = bl;
		addAnimation("off", new Animation(new SpriteSheet("data\\maps\\ButtonOff.png", 32, 32), 150));
		addAnimation("on", new Animation(new SpriteSheet("data\\maps\\ButtonOn.png", 32, 32), 150));
		notif = new AnimatedObject(-8,-40,48,48);
		int[] one = {0,0};
		int[] two = {1,0};
		int[] dur = {150};
		notif.addAnimation("near",new Animation(new SpriteSheet("data\\maps\\SpacebarNotif.png", 48, 48), one, dur));
		notif.addAnimation("far",new Animation(new SpriteSheet("data\\maps\\SpacebarNotif.png", 48, 48), two, dur));
		addObject(notif);

		trigger = new Trigger(x-16, y-16, 64, 64, new myListener());
		GameConstants.triggers.add(trigger);
		playAnimation("off");
		notif.playAnimation("near");
	}

	@Override
	public void interact(GameObject source) {
		if(!used) {
			GameConstants.techUsed++;
			GameConstants.level.buttonQuestion = true;
			GameConstants.level.questions.setAnswering(true);
		}
		used = true;
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

	protected class myListener implements TriggerListener {
		@Override
		public void onEnter(GameObject src) {
			notif.playAnimation("far");
		}

		@Override
		public void onExit(GameObject src) {
			notif.playAnimation("near");
		}

		@Override
		public void triggered(GameObject src) {

		}
	}

	@Override
	public boolean inRange(GameObject source) {
		return trigger.collides(source);
	}
	public void setState(boolean on) {
		this.on = on;
	}
	public boolean getState() {
		return on;
	}
	@Override
	public boolean isActive() {
		return trigger.isActive();
	}

}
