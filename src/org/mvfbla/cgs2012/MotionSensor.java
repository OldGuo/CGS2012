package org.mvfbla.cgs2012;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class MotionSensor extends GameObject{
	private int delay = 2500;
	private int counter = 0;
	// 0 : deactivated
	// 1 : off
	// 2 : on
	private byte state = 1;
	private Trigger trigger;
	public MotionSensor(TiledObject source, int delay) {
		super(source.getX(), source.getY(), source.getWidth(), source.getHeight());
		counter -= delay;
		trigger = new Trigger(source, new MotionListener());
		GameConstants.triggers.add(trigger);
	}
	public void update(GameContainer gc, int delta) {
		counter += delta;
		if(counter >= delay) {
			counter = 0;
			if(state == 1)
				state = 2;
			else if(state == 2)
				state = 1;
		}
	}
	public void draw(Graphics g) {
		if(state == 2) {
			Color c = g.getColor();
			g.setColor(Color.red);
			g.fill(this);
			g.setColor(c);
		}
	}
	private class MotionListener implements TriggerListener {
		public void onEnter(GameObject src) { }
		public void onExit(GameObject src) { }
		public void triggered(GameObject src) {
			if(state == 2) {
				((Characters)src).setHealth(((Characters)src).getHealth()-1);
			}
		}
	}
}
