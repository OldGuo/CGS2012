package org.mvfbla.cgs2012;

import java.util.HashMap;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class AnimatedObject extends GameObject {

	protected HashMap<String, Animation> animMap;
	private Animation current;

	public AnimatedObject(int x, int y, int width, int height) throws SlickException {
		super(x, y, width, height);
		animMap = new HashMap<String, Animation>();
	}
	public void addAnimation(String name, Animation anim) {
		animMap.put(name, anim);
		current = anim;
	}
	public void playAnimation(String name) {
		current = animMap.get(name);
		current.start();
	}
	public void resetAnimation() {
		current.setCurrentFrame(0);
		current.stop();
	}
	public void stopAnimation() {
		current.stop();
	}
	public Animation getCurrentAnimation() {
		return current;
	}
	@Override
	public void update(GameContainer gc, int delta) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void draw(Graphics g) {
		g.drawAnimation(current, x, y);
		g.translate(x, y);
		for(GameObject obj : objects) {
			obj.draw(g);
		}
		g.translate(-x, -y);
	}
}
