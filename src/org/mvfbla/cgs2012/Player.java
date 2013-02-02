package org.mvfbla.cgs2012;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Player extends Characters{

	private final float speed = 10f;
	private static final float MAX_SPEED = 5;
	private String left,right;
	private String current;
	private int punchTime=0;
	private float punchRange = 40; //if negative, means facing the other way
	private boolean punching = false;
	private boolean cooldown = false;
	private final AnimatedObject arm;
	public Player(int x, int y) throws SlickException {
		super(x, y, 48, 48);
		addAnimation("walkLeftInvert", new Animation(new SpriteSheet("data\\PlayerLeftInverted.png", 48, 48), 150));
		addAnimation("walkRightInvert", new Animation(new SpriteSheet("data\\PlayerRightInverted.png", 48, 48), 150));
		addAnimation("walkLeft", new Animation(new SpriteSheet("data\\PlayerLeft.png", 48, 48), 150));
		addAnimation("walkRight", new Animation(new SpriteSheet("data\\PlayerRight.png", 48, 48), 150));

		arm = new AnimatedObject(0,0,48,48);
		arm.addAnimation("right",new Animation(new SpriteSheet("data\\PlayerAttackRight.png", 48, 48), 750));
		arm.addAnimation("left",new Animation(new SpriteSheet("data\\PlayerAttackLeft.png", 48, 48), 750));
		addObject(arm);
		arm.stopAnimation();

		current = "walkRight";
		super.setHealth(3);
		super.setBlink(2000);
	}
	@Override
	public void update(GameContainer gc, int delta) {
		if(!isAlive())
			return;
		boolean movePressed = false;
		if(GameConstants.getGrav() > 0){
			left = "walkLeft";
			right = "walkRight";
		}else{
			left = "walkLeftInvert";
			right = "walkRightInvert";
		}
		if(getVelX() < 0){
			current = left;
		}else{
			current = right;
		}
		playAnimation(current);
		// Moving left/right/up
		if (gc.getInput().isKeyDown(Input.KEY_LEFT)) {
			movePressed = true;
			setVelX(-MAX_SPEED);
			if(this.getVelX() < -MAX_SPEED)
				this.setVelX(-MAX_SPEED);
			//playAnimation(left);
			this.setRange(Math.abs(this.getRange())*-1);
		}
		if (gc.getInput().isKeyDown(Input.KEY_RIGHT)) {
			movePressed = true;
			this.setVelX(MAX_SPEED);
			if(this.getVelX() > MAX_SPEED)
				this.setVelX(MAX_SPEED);
			//playAnimation(right);
			this.setRange(Math.abs(this.getRange()));
		}
		if (!movePressed) {
			// Slow down the player
			this.setVelX(0);
			resetAnimation();
		}
		if (gc.getInput().isKeyDown(Input.KEY_UP)) {
			// Jump
			if(GameConstants.getGrav() > 0 ){
				if(getDirection(trans) == 1) {
					trans = new Vector(0,0);
					this.setVelY(-9);
				}
			}else{
				if(getDirection(trans) == 3) {
					trans = new Vector(0,0);
					this.setVelY(9);
				}
			}
		}
		if(gc.getInput().isKeyDown(Input.KEY_SPACE)){
			boolean interacting = false;
			for(InteractiveObject io : GameConstants.interacts) {
				if(io.inRange(this)) {
					interacting = true;
					io.interact(this);
				}
			}
			// do da punches
			if(!interacting&&!cooldown&&!punching) {
				punching=true;
				punchTime=0;
				if(punchRange > 0)
					arm.playAnimation("right");
				else
					arm.playAnimation("left");
				arm.setFrame(1);
				arm.stopAnimation();
			}
		}
		if(punching){
			punchTime+=delta;
			arm.setFrame(1);
			arm.stopAnimation();
		}
		if(cooldown){
			punchTime-=delta;
		}
		if(punchTime<=-500){
			punchTime=0;
			cooldown=false;
		}
		if(punchTime>=300){
			punchTime=0;
			punching=false;
			cooldown=true;
			arm.setFrame(0);
			arm.stopAnimation();
		}
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
		//g.drawOval(this.getCenterX()-punchRange, this.getCenterY()-punchRange, punchRange*2, punchRange*2);
		super.draw(g);
	}
}
