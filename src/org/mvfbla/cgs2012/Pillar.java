package org.mvfbla.cgs2012;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class Pillar extends GameObject {

	private boolean broken = false;

	public Pillar(int x, int y, int width, int height) throws SlickException {
		super(x, y, width, height);
		broken = false;
	}
	public boolean isBroken(){
		return broken;
	}
	public void setBroken(boolean b){
		broken = b;
	}
	@Override
	public void draw(Graphics g){
		if(broken){
			g.setColor(Color.red);
		}else{
			g.setColor(Color.white);
		}
		g.fillRect(getX(),getY(),getWidth(),getHeight());
	}
	@Override
	public void update(GameContainer gc, int delta) {

	}
}
