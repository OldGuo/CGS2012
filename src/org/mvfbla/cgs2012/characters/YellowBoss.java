//YellowBoss class, extends Boss
//targets player, charges laser, shoots, then teleports
//defeated by activating all 3 platforms
package org.mvfbla.cgs2012.characters;

import org.mvfbla.cgs2012.utils.GameConstants;
import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class YellowBoss extends Boss{
	int time;
	public int location;
	boolean[] activated;
	public boolean aiming, firing, charging, teleporting;
	float reticle, reticleWidth;
	private boolean attacking;
	public YellowBoss (int x, int y) throws SlickException{
		super(x,y);
		addAnimation("YellowBoss", new Animation(new SpriteSheet("data\\CharAnim\\YellowBossWalking.png", 128, 128), 150));
		super.setSpeed(0);
		time=0;
		location=1;
		reticle=0;
		reticleWidth=48;
		aiming=firing=charging=teleporting=false;
		activated=new boolean[3];
		for(int i=0;i<3;i++)
			activated[i]=false;
	}
	public void activate(int platform){ //activates a platform
		setHealth(getHealth()-1);
		activated[platform]=true;
	}
	public boolean getAttacking(){
		return attacking;
	}
	public float getReticle(){ //returns x coordinate of aiming reticle
		return reticle;
	}
	public float getReticleWidth(){ //returns width of reticle
		return reticleWidth;
	}
	public int getTime(){ //returns time elapsed during cycle
		return time;
	}
	public boolean isActivated(int platform){ //returns if a platform is activated
		return activated[platform];
	}
	public boolean isAiming(){ //returns if it's aiming
		return aiming;
	}
	public boolean isCharging(){ //returns if it's charging
		return charging;
	}
	public boolean isFiring(){ //returns if it's firing
		return firing;
	}
	public boolean isTeleporting(){ //returns if it's teleporting
		return teleporting;
	}
	public void setAttacking(boolean a){
		attacking = a;
	}
	public void setReticle(float xValue){ //sets x coordinate of reticle
		reticle=xValue;
	}
	public void teleport(){
		teleporting=false;
		time=0;
		do{ //chooses another random platform to teleport to
			int temp = location;
			do{
				location=(int)(Math.random()*3);
			}while(temp == location);
		}while(activated[location]);
	}
	@Override
	public void update(GameContainer gc, int delta){
		if(attacking){
			if(activated[0]&&activated[1]&&activated[2]){
				if(isAlive() && getHealth() == 0) {
					GameConstants.wrongCountMax+=2; //defeating this boss grants more allowed incorrect answers when answering questions
				}
				alive = false;
			}
			else{
				if(time<5000){ //cycles every 5 seconds
					time+=delta;
					if(time>=1000&&time<=2000) //between 1st and 2nd second, it aims laser
						aiming=true;
					else
						aiming=false;
					if(time>=2000&&time<=2500) //between 2nd and 2.5 seconds, it charges laser
						charging=true;
					else
						charging=false;
					if(time>=2500&&time<=3500) //between 2.5 and 3.5 seconds, it fires laser
						firing=true;
					else
						firing=false;
					if(time>=4500) //between 4.5 and 5 seconds, it teleports
						teleporting=true;
					else
						teleporting=false;
				}
				else
					teleport();
				if(!teleporting&&activated[location]) //if you activate the platform, a teleport is forced
					time=4500;
				if(location==0){ //locations of the 3 platforms to teleport between
					this.setX(95);
					this.setY(160);
				}
				else if(location==1){
					this.setX(330);
					this.setY(100);
				}
				else if(location==2){
					this.setX(565);
					this.setY(160);
				}
				super.update(gc, delta);
			}
			if(!attacking){
				super.setHealth(3);
			}
		}
	}
}

//locations of platforms: (330,100) (95,160) (565,160)
