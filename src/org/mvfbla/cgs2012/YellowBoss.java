package org.mvfbla.cgs2012;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class YellowBoss extends Boss{
	int time, location;
	boolean[]activated;
	boolean aiming, firing, charging, teleporting;
	float reticle, reticleWidth;
	public YellowBoss (int x, int y) throws SlickException{
		super(x,y);
		addAnimation("YellowBoss", new Animation(new SpriteSheet("data\\YellowBossWalking.png", 128, 128), 150));
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
	@Override
	public void update(GameContainer gc, int delta){
		if(activated[0]&&activated[1]&&activated[2]){
			super.setHealth(0);
			System.out.println("GG");
		}
		else{
			if(time<5000){
				time+=delta;
				if(time>=1000&&time<=2000)
					aiming=true;
				else
					aiming=false;
				if(time>=2000&&time<=2500)
					charging=true;
				else
					charging=false;
				if(time>=2500&&time<=3500)
					firing=true;
				else
					firing=false;
				if(time>=4500&&time<=5000)
					teleporting=true;
				else
					teleporting=false;
			}
			else
				teleport();
			if(!teleporting&&activated[location])
				time=4500;
			if(location==0){
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
	}
	public void teleport(){
		teleporting=false;
		time=0;
		do{
			int temp = location;
			do{
				location=(int)(Math.random()*3);
			}while(temp == location);
		}while(activated[location]);
	}
	public void activate(int platform, boolean on){
		activated[platform]=on;
	}
	public boolean isActivated(int platform){
		return activated[platform];
	}
	public boolean isAiming(){
		return aiming;
	}
	public boolean isCharging(){
		return charging;
	}
	public boolean isFiring(){
		return firing;
	}
	public boolean isTeleporting(){
		return teleporting;
	}
	public float getReticle(){
		return reticle;
	}
	public void setReticle(float xValue){
		reticle=xValue;
	}
	public float getReticleWidth(){
		return reticleWidth;
	}
	public int getTime(){
		return time;
	}
}

//locations: (330,100) (95,160) (565,160)