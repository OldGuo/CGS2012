package org.mvfbla.cgs2012.interactable;

import org.mvfbla.cgs2012.base.GameObject;
import org.mvfbla.cgs2012.utils.GameConstants;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/**
 * @author PenguinToast
 * Class to represent end-of-level elevator
 */
public class Elevator extends Button {
	/**
	 * @author PenguinToast
	 * Class to trigger end of level animation
	 */
	private class FinishListener implements TriggerListener {
		@Override
		public void onEnter(GameObject src) {
			//bring up question screen
			GameConstants.level.done = true;
			GameConstants.level.questions.setAnswering(true);
		}
		@Override
		public void onExit(GameObject src) {
			end.setActive(false);
			GameConstants.level.done = false;
			GameConstants.level.questions.setAnswering(false);
		}
		@Override
		public void triggered(GameObject src) {
		}
	}
	private Trigger end;
	private int startY = 0;
	/**
	 * Constructs a new Elevator with the specified coordinates
	 * @param x - X coordinate of the elevator
	 * @param y - Y coordinate of the elevator
	 * @throws SlickException
	 */
	public Elevator(int x, int y) throws SlickException {
		super(x, y);
		startY = y;
		// Initialize the end of level trigger
		end = new Trigger(x+40, y, 6, 80, new FinishListener());
		end.setActive(false);
		GameConstants.triggers.add(end);
		// Initialize the button press trigger
		trigger = new Trigger(x+40, y, 6, 80, new myListener());
		GameConstants.triggers.add(trigger);
		// Initialize the notification animation
		notif = new AnimatedObject(25, -40, 48, 48);
		int[] one = {0,0};
		int[] two = {1,0};
		int[] dur = {150};
		notif.addAnimation("near",new Animation(new SpriteSheet("data\\maps\\SpacebarNotif.png", 48, 48), one, dur));
		notif.addAnimation("far",new Animation(new SpriteSheet("data\\maps\\SpacebarNotif.png", 48, 48), two, dur));
		addObject(notif);
		notif.playAnimation("near");
		addAnimation("off", new Animation(new SpriteSheet("data\\maps\\ButtonOff.png", 32, 32), 150));
		addAnimation("on", new Animation(new SpriteSheet("data\\maps\\ButtonOn.png", 32, 32), 150));
		playAnimation("off");
	}
	@Override
	public void draw(Graphics g) {
		Color orig = g.getColor();
		if(GameConstants.level.done && GameConstants.level.questions.getAnswering() == false) {
			boolean behind = true;
			// Progress of end of level animation
			float prog = (GameConstants.level.transTime/(float)GameConstants.level.transLength);
			// Draw elevator
			if(prog >= 1) {
				g.setColor(Color.black);
				g.fill(this);
				g.translate(x, y);
				g.setColor(Color.white);
				g.fillRect(5, 5, 86, 75);
				g.setColor(Color.gray);
				prog -= 1;
				if(prog > 0.5) {
					prog = 1-prog;
					behind = false;
				}
				// Draw the player in the correct place
				if(behind) {
					g.translate(-x, -y);
					GameConstants.level.player.draw(g);
					g.translate(x, y);
				}
				g.fillRect(5-(43*prog), 5, 43, 75);
				g.fillRect(48+(43*prog), 5, 43, 75);
				if(!behind) {
					g.translate(-x, -y);
					GameConstants.level.player.draw(g);
					g.translate(x, y);
				}
			} else {
				int dist = 500;
				prog = 1-prog;
				setY(startY-prog*dist);
				g.setColor(Color.black);
				g.fill(this);
				g.translate(x, y);
				g.setColor(Color.white);
				g.fillRect(5, 5, 86, 75);
				g.setColor(Color.gray);
				g.fillRect(5, 5, 43, 75);
				g.fillRect(48, 5, 43, 75);
			}
		} else {
			// Draw non-moving elevator
			g.setColor(Color.black);
			g.fill(this);
			g.translate(x, y);
			g.setColor(Color.white);
			g.fillRect(5, 5, 86, 75);
			g.setColor(Color.gray);
			g.fillRect(5, 5, 43, 75);
			g.fillRect(48, 5, 43, 75);
			// Draw lock is elevator is inactive
			if(!isActive()) {
				try {
					g.drawImage(new Image("data\\Level\\lock.png"), 27, 25);
				} catch (SlickException e) {
					e.printStackTrace();
				}
			}
			for(GameObject obj : objects) {
				obj.draw(g);
			}
		}
		g.setColor(orig);
		g.translate(-x, -y);
	}
	@Override
	public void interact(GameObject source) {
		end.setActive(true);
	}
}
