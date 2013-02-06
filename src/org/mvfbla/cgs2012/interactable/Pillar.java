package org.mvfbla.cgs2012.interactable;

import org.mvfbla.cgs2012.GameObject;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Pillar extends GameObject {

	private boolean broken = false;
	private final Image unbrokenPillar;
	private final Image brokenPillar;

	public Pillar(int x, int y, int width, int height) throws SlickException {
		super(x, y, width, height);
		broken = false;
		unbrokenPillar = new Image("data\\Maps\\pillarUnbroken.png");
		brokenPillar = new Image("data\\Maps\\pillarBroken.png");
	}
	@Override
	public void draw(Graphics g){
		if(broken){
			brokenPillar.draw(getX()-24,getY());
		}else{
			unbrokenPillar.draw(getX(),getY());
		}
	}
	public boolean isBroken(){
		return broken;
	}
	public void setBroken(boolean b){
		broken = b;
	}
	@Override
	public void update(GameContainer gc, int delta) {

	}
}
