//Player class, extends Characters
//sprite controlled by user
package org.mvfbla.cgs2012.characters;

import org.mvfbla.cgs2012.GameConstants;
import org.mvfbla.cgs2012.Vector;
import org.mvfbla.cgs2012.interactable.AnimatedObject;
import org.mvfbla.cgs2012.interactable.InteractiveObject;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Player extends Characters{
	private String left,right;
	private String current;
	private int punchTime=0;
	private boolean punching = false;
	private boolean cooldown = false;
	private boolean wasMovingRight = true;
	private final AnimatedObject arm;
	private boolean control;
	public Player(int x, int y) throws SlickException {
		super(x, y, 48, 48); //loads sprite sheets for animations of actions
		addAnimation("walkLeftInvert", new Animation(new SpriteSheet("data\\PlayerLeftInverted.png", 48, 48), 150)); 
		addAnimation("walkRightInvert", new Animation(new SpriteSheet("data\\PlayerRightInverted.png", 48, 48), 150));
		addAnimation("walkLeft", new Animation(new SpriteSheet("data\\CharAnim\\PlayerLeft.png", 48, 48), 150));
		addAnimation("walkRight", new Animation(new SpriteSheet("data\\CharAnim\\PlayerRight.png", 48, 48), 150));

		arm = new AnimatedObject(0,0,48,48);
		arm.addAnimation("right",new Animation(new SpriteSheet("data\\PlayerAttackRight.png", 48, 48), 750));
		arm.addAnimation("left",new Animation(new SpriteSheet("data\\PlayerAttackLeft.png", 48, 48), 750));
		addObject(arm);
		arm.stopAnimation();

		current = "walkRight";
		super.setInitialHealth(GameConstants.playerMaxHealth);
		super.setBlink(2000); //when hit, invulnerable for 2 seconds
	}
	@Override
	public void draw(Graphics g){
		super.draw(g);
		if(punchTime > 0) { //draws the arm punching
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
	public boolean getControl() { //returns if the player is controllable
		return control;
	}
	public float getRange(){ //returns range of punching
		return GameConstants.punchRange;
	}
	public boolean isPunching(){ //returns if player is punching
		return punching;
	}
	public void setControl(boolean control) { //sets controls on or off
		this.control = control;
	}
	public void setRange(float whatRange){ //sets range of punching
		GameConstants.punchRange=(int) whatRange;
	}
	@Override
	public void update(GameContainer gc, int delta) { 
		if(!isAlive()) 
			return;
		if(!getControl()) { //during some parts of the game, the player cannot move
			setVelX(0);
			stopAnimation();
			super.update(gc, delta);
			return;
		}
		boolean movePressed = false;
		if(GameConstants.getGrav() > 0){ //when gravity is flipped, so are controls for symmetry
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
			setVelX(-GameConstants.playerMaxSpeed);
			if(this.getVelX() < -GameConstants.playerMaxSpeed)
				this.setVelX(-GameConstants.playerMaxSpeed);
			//playAnimation(left);
			this.setRange(Math.abs(this.getRange())*-1);
		}
		if (gc.getInput().isKeyDown(Input.KEY_RIGHT)) {
			movePressed = true;
			this.setVelX(GameConstants.playerMaxSpeed);
			if(this.getVelX() > GameConstants.playerMaxSpeed)
				this.setVelX(GameConstants.playerMaxSpeed);
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
		if(punching){ //punching has a set duration
			punchTime+=delta;
			arm.setFrame(1);
			arm.stopAnimation();
		}
		if(cooldown){ //after punching, player cannot punch for a certain time to avoid spamming
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
}
