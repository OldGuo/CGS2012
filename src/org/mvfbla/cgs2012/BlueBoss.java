package org.mvfbla.cgs2012;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class BlueBoss extends Boss{
	private boolean awake = false;
	private float sight = 450;
	private final int ATTACK_DELAY = 2000;
	private int time = ATTACK_DELAY;
	private int count = 0;
	
	public BlueBoss (int x, int y) throws SlickException{
		super(x,y);
		super.setHealth(1);
		addAnimation("BlueBoss", new Animation(new SpriteSheet("data\\BlueBossWalking.png", 128, 128), 150));
	}
	@Override
	public void update(GameContainer gc, int delta){
		super.update(gc, delta);
		time -= delta;
		if(time <= 0){
			count++;
			time = ATTACK_DELAY;
		}
	}
	public void draw(Graphics g){
		super.draw(g);
		for(int i = 0; i < count; i++){
			g.fillRect(50*i,20,40,40);
		}
		g.drawOval(this.getCenterX()-sight, this.getCenterY()-sight, sight*2, sight*2);
	}
	public void changeSleep(boolean inSight){
		awake=inSight;
	}
	public float getSight(){
		return sight;
	}
}
