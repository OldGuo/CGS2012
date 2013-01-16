package org.mvfbla.cgs2012;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.Graphics;

public class Player extends Character{

	private final float speed = 10f;
	private static final float MAX_SPEED = 5;
	AnimatedObject dust;
	private float punchRange = 50; //if negative, means facing the other way
	private boolean punching = false;
	public Player(int x, int y) throws SlickException {
		super(x, y, 48, 48);
		addAnimation("walkRight", new Animation(new SpriteSheet("data\\PlayerRight.png", 48, 48), 150));
		addAnimation("walkLeft", new Animation(new SpriteSheet("data\\PlayerLeft.png", 48, 48), 150));
		//dust = new AnimatedObject(0, 0, 48, 48);
		//dust.addAnimation("right", new Animation(new SpriteSheet("data\\DustRight.png", 48, 48), 150));
		//dust.addAnimation("left", new Animation(new SpriteSheet("data\\DustLeft.png", 48, 48), 150));
		
		super.setHealth(3);
	//	addObject(dust);
	}
	@Override
	public void update(GameContainer gc, int delta) {
		boolean movePressed = false;
		// Moving left/right/up
		if (gc.getInput().isKeyDown(Input.KEY_LEFT)) {
			movePressed = true;
			this.setVelX(-MAX_SPEED);
			if(this.getVelX() < -MAX_SPEED)
				this.setVelX(-MAX_SPEED);
			playAnimation("walkLeft");
		//	dust.playAnimation("left");
			this.setRange(Math.abs(this.getRange())*-1);
		}
		if (gc.getInput().isKeyDown(Input.KEY_RIGHT)) {
			movePressed = true;
			this.setVelX(MAX_SPEED);
			if(this.getVelX() > MAX_SPEED)
				this.setVelX(MAX_SPEED);
			playAnimation("walkRight");
			//dust.playAnimation("right");
			this.setRange(Math.abs(this.getRange()));
		}
		if (!movePressed) {
			// Slow down the player
			//this.setVelX(Math.signum(this.getVelX())*Math.max(0, (Math.abs(this.getVelX())-0.1f)));
			this.setVelX(0);
			resetAnimation();
		//	dust.resetAnimation();
//				player.setVelX(0);
		}
		if (gc.getInput().isKeyDown(Input.KEY_UP) || gc.getInput().isKeyDown(Input.KEY_SPACE)) {
			// Jump
			if(trans != null && trans.y <= -0.09) {
				this.setVelY(-8);
			}
		}
		if(gc.getInput().isKeyDown(Input.KEY_A)){
			punching=true;
		}
		else
			punching=false;
		if(gc.getInput().isKeyDown(Input.KEY_S))
			System.out.println("action");
		super.update(gc, delta);
	}
	public float getRange(){
		return punchRange;
	}
	public void setRange(float whatRange){
		punchRange=whatRange;
	}
	public boolean isPunching(){
		return punching;
	}
	@Override
	public void draw(Graphics g){
		g.drawOval(this.getCenterX()-punchRange, this.getCenterY()-punchRange, punchRange*2, punchRange*2);
		super.draw(g);
	}
}
