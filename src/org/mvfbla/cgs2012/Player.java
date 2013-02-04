package org.mvfbla.cgs2012;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Player extends Characters{

	private static final float MAX_SPEED = 5;
	private String left,right;
	private String current;
	private int punchTime=0;
	private boolean punching = false;
	private boolean cooldown = false;
	private boolean wasMovingRight = true;
	private final AnimatedObject arm;
	private boolean control;
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
		super.setInitialHealth(3);
		super.setBlink(2000);
	}
	@Override
	public void update(GameContainer gc, int delta) {
		if(!isAlive())
			return;
		if(!getControl()) {
			super.update(gc, delta);
			return;
		}
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
			wasMovingRight = false;
		}else if(getVelX() > 0){
			current = right;
			wasMovingRight = true;
		}
		if(getVelX() == 0){
			if(wasMovingRight)
				current = right;
			else
				current = left;
		}
		super.playAnimation(current);
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
				if(io.inRange(this) && io.isActive()) {
					interacting = true;
					io.interact(this);
				}
			}
			// do da punches
			if(!interacting&&!cooldown&&!punching) {
				punching=true;
				punchTime=0;
				if(GameConstants.punchRange > 0)
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
		super.update(gc, delta);
	}
	public float getRange(){
		return GameConstants.punchRange;
	}
	public void setRange(float whatRange){
		GameConstants.punchRange=(int) whatRange;
	}
	public boolean isPunching(){
		return punching;
	}
	@Override
	public void draw(Graphics g){
		super.draw(g);
		if(punchTime > 0) {
			float prog = punchTime/300f;
			g.translate(getCenterX(), getCenterY());
			try {
				Color c = new Color(Color.white);
				c.a = 1-prog;
				int extra = 5;
				if(current.equals(right)) {
					int off = -1;
					g.drawImage(new Image("data\\punchright.png"), off + ((GameConstants.punchRange+off-32+3+extra)*prog), -13, c);
				} else if(current.equals(left)) {
					int off = -30;
					g.drawImage(new Image("data\\punchleft.png"), off + ((GameConstants.punchRange-off-1-extra)*prog), -13, c);
				}
			} catch (SlickException e) {
				e.printStackTrace();
			}
			g.translate(-getCenterX(), -getCenterY());
		}
	}
	public boolean getControl() {
		return control;
	}
	public void setControl(boolean control) {
		this.control = control;
	}
}
