package org.mvfbla.cgs2012;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class YellowBoss extends Boss{
	int time, location;
	boolean[]activated;
	public YellowBoss (int x, int y) throws SlickException{
		super(x,y);
		addAnimation("YellowBoss", new Animation(new SpriteSheet("data\\YellowBossWalking.png", 128, 128), 150));
		super.setSpeed(0);
		time=0;
		location=1;
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
			if(time<1000)
				time+=delta;
			else
				teleport();
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
		//animate
		time=0;
		do{
			location=(int)(Math.random()*3);
		}while(activated[location]);
	}
	public void activate(int platform, boolean on){
		activated[platform]=on;
	}
}

//locations: (330,100) (95,160) (565,160)