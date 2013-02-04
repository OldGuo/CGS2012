package org.mvfbla.cgs2012;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Elevator extends Button {
	private GameLevel level;
	private Trigger end;
	public Elevator(int x, int y, GameLevel level) throws SlickException {
		super(x, y);
		end = new Trigger((int)x+40, (int)y, 6, 80, new FinishListener());
		end.setActive(false);
		GameConstants.triggers.add(end);
		this.level = level;
		trigger = new Trigger(x+40, y, 6, 80, new myListener());
		GameConstants.triggers.add(trigger);
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
	public void interact(GameObject source) {
		end.setActive(true);
	}
	@Override
	public void draw(Graphics g) {
		Color orig = g.getColor();
		if(level.done && level.questions.getAnswering() == false) {
			boolean behind = true;
			float prog = (level.transTime/(float)level.transLength);
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
				if(behind) {
					g.translate(-x, -y);
					level.player.draw(g);
					g.translate(x, y);
				}
				g.fillRect(5-(43*prog), 5, 43, 75);
				g.fillRect(48+(43*prog), 5, 43, 75);
				if(!behind) {
					g.translate(-x, -y);
					level.player.draw(g);
					g.translate(x, y);
				}
			} else {
				int dist = 50;
				prog = 1-prog;
				setY(getY()-prog*dist);
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
			g.setColor(Color.black);
			g.fill(this);
			g.translate(x, y);
			g.setColor(Color.white);
			g.fillRect(5, 5, 86, 75);
			g.setColor(Color.gray);
			g.fillRect(5, 5, 43, 75);
			g.fillRect(48, 5, 43, 75);
			if(!isActive()) {
				try {
					g.drawImage(new Image("data\\lock.png"), 27, 25);
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
	private class FinishListener implements TriggerListener {
		@Override
		public void onEnter(GameObject src) {
			//bring up question screen
			level.done = true;
			level.questions.setAnswering(true);
		}
		@Override
		public void onExit(GameObject src) {
			end.setActive(false);
			level.done = false;
			level.questions.setAnswering(false);
		}
		@Override
		public void triggered(GameObject src) {
		}
	}
}
