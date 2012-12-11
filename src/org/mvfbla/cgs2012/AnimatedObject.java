package org.mvfbla.cgs2012;

import java.util.HashMap;

import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class AnimatedObject extends GameObject {

	private Animation anim;
	private HashMap<String, int[]> animMap;
	SpriteSheet sheet;

	public AnimatedObject(int x, int y, int width, int height, String fileLoc) throws SlickException {
		super(x, y, width, height);
		setAnimation(width,height,fileLoc);
	}
	public void setAnimation(int width, int height, String fileName) throws SlickException{
		SpriteSheet sheet = new SpriteSheet(fileName, width, height);
		anim = new Animation(sheet, 150);
		anim.setAutoUpdate(false);
		for(int i = 0; i < sheet.getWidth()/width; i++) {
			for(int j = 0; j < sheet.getHeight()/height; j++) {
				anim.addFrame(sheet.getSprite(i, j), 150);
			}
		}
	}
	public void addAnimation(String name, int start, int end) {
		int[] t = {start, end};
		animMap.put(name, t);
	}
	public void playAnimation(String name) {
		anim.setCurrentFrame(animMap.get(name)[0]);
		anim.start();
		anim.stopAt(animMap.get(name)[1]);
	}
	public Animation getAnim() {
		return anim;
	}
}
