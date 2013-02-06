//BlackBoss class, extends Characters
//this boss is unique because it mirrors the player
//code is taken from Player class
//reached when all 3 other bosses have been defeated
//defeated by deactivating the mirror motion, and forcing it into the motion sensor
package org.mvfbla.cgs2012;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class BlackBoss extends Characters{

	private int punchTime=0;
	private float punchRange = 40; //if negative, means facing the other way
	private boolean punching = false;
	private boolean cooldown = false;
	private final AnimatedObject arm;
	public BlackBoss(int x, int y) throws SlickException {
		super(x, y, 48, 48);
		addAnimation("walkLeft", new Animation(new SpriteSheet("data\\BlackBossLeft.png", 48, 48), 150));
		addAnimation("walkRight", new Animation(new SpriteSheet("data\\BlackBossRight.png", 48, 48), 150));
		arm = new AnimatedObject(0,0,48,48);
		arm.addAnimation("right",new Animation(new SpriteSheet("data\\BlackBossAttackRight.png", 48, 48), 750));
		arm.addAnimation("left",new Animation(new SpriteSheet("data\\BlackBossAttackLeft.png", 48, 48), 750));
		addObject(arm);
		arm.stopAnimation();

		super.setInitialHealth(GameConstants.playerMaxHealth);
		super.setBlink(2000);
	}
	@Override
	public void update(GameContainer gc, int delta) {
		if(!isAlive())
			return;
		boolean movePressed = false;
		// Moving left/right/up
		if(GameConstants.getSync() == true){
			if (gc.getInput().isKeyDown(Input.KEY_LEFT)) {
				movePressed = true;
				this.setVelX(GameConstants.playerMaxSpeed);
				if(this.getVelX() < -GameConstants.playerMaxSpeed)
					this.setVelX(-GameConstants.playerMaxSpeed);
				playAnimation("walkRight");
			//	dust.playAnimation("left");
				this.setRange(Math.abs(this.getRange())*1);
			}
			if (gc.getInput().isKeyDown(Input.KEY_RIGHT)) {
				movePressed = true;
				this.setVelX(-GameConstants.playerMaxSpeed);
				if(this.getVelX() > GameConstants.playerMaxSpeed)
					this.setVelX(GameConstants.playerMaxSpeed);
				playAnimation("walkLeft");
				//dust.playAnimation("right");
				this.setRange(Math.abs(this.getRange())*-1);
			}
			if (!movePressed) {
				// Slow down the player
				//this.setVelX(Math.signum(this.getVelX())*Math.max(0, (Math.abs(this.getVelX())-0.1f)));
				this.setVelX(0);
				resetAnimation();
			//	dust.resetAnimation();
	//				player.setVelX(0);
			}
			if (gc.getInput().isKeyDown(Input.KEY_UP)) {
				// Jump
				if(getDirection(trans) == 1) {
					trans = new Vector(0,0);
					this.setVelY(-9);
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
		}
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
		super.draw(g);
	}
}
