//YellowBoss class, extends Boss
//targets player, charges laser, shoots, then teleports
//defeated by activating all 3 platforms
package org.mvfbla.cgs2012.characters;

import org.mvfbla.cgs2012.utils.GameConstants;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/**
 * @author Alex Wang
 * Yellow Boss
 * targets player, charges laser, shoots, then teleports
 * defeated by activating all 3 platforms
 */
public class YellowBoss extends Boss{
	int time;
	public int location;
	boolean[] activated;
	public boolean aiming, firing, charging, teleporting;
	float reticle, reticleWidth;
	private float fireX,fireY;
	private boolean attacking;
	private Player player;
	/**
	 * @param x - initial x position
	 * @param y - initial y position
	 * @throws SlickException
	 */
	public YellowBoss (int x, int y) throws SlickException{
		super(x,y);
		addAnimation("YellowBoss", new Animation(new SpriteSheet("data"+GameConstants.separatorChar+"CharAnim"+GameConstants.separatorChar+"YellowBossWalking.png", 128, 128), 150));
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
	/**
	 * Sets the reference to the player
	 * @param p - Player
	 */
	public void setPlayer(Player p) {
		player = p;
	}
	/**
	 * @param platform - activates platform
	 */
	public void activate(int platform){
		setHealth(getHealth()-1);
		activated[platform]=true;
	}
	/**
	 * @return true when plot text is finished and boss is attacking
	 */
	public boolean getAttacking(){
		return attacking;
	}
	/**
	 * @return x coordinate of aiming reticle
	 */
	public float getReticle(){
		return reticle;
	}
	/**
	 * @return returns width of reticle
	 */
	public float getReticleWidth(){
		return reticleWidth;
	}
	/**
	 * @return time elapsed during 5 second cycle
	 */
	public int getTime(){
		return time;
	}
	/**
	 * @param platform - one of three platforms
	 * @return true if platform is activated
	 */
	public boolean isActivated(int platform){
		return activated[platform];
	}
	/**
	 * @return true if it's aiming
	 */
	public boolean isAiming(){
		return aiming;
	}
	/**
	 * @return true if it's charging
	 */
	public boolean isCharging(){
		return charging;
	}
	/**
	 * @return true if it's firing
	 */
	public boolean isFiring(){
		return firing;
	}
	/**
	 * @return true if it's teleporting
	 */
	public boolean isTeleporting(){
		return teleporting;
	}
	/**
	 * @param a sets if it's attacking
	 */
	public void setAttacking(boolean a){
		attacking = a;
	}
	/**
	 * @param xValue - x coordinate of reticle
	 */
	public void setReticle(float xValue){
		reticle=xValue;
	}
	/**
	 * chooses another random platform to teleport to
	 */
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
		//sets reticle on player
		if(isAiming())
			setReticle(player.getX());
		//sets area where laser hits to damage player
		else if(isFiring()) {
			if(player.getCenterX()>=fireX&&player.getCenterX()<=fireX+getReticleWidth()){
				if(player.getCenterY()>=fireY&&player.getCenterY()<=fireY+getReticleWidth())
					player.setHealth(player.getHealth()-1);
			}
		}
	}
	@Override
	public void draw(Graphics g) {
		super.draw(g);
		g.setColor(Color.red);
		//reticle follows player
		if(isAiming()){
			fireX=getReticle();
			fireY=player.getY();
			g.drawOval(fireX,fireY, getReticleWidth(), getReticleWidth());
			g.drawLine(fireX, fireY+getReticleWidth()/2, fireX+getReticleWidth(), fireY+getReticleWidth()/2);
			g.drawLine(fireX+getReticleWidth()/2, fireY, fireX+getReticleWidth()/2, fireY+getReticleWidth());
			g.drawLine(getCenterX(), getCenterY()-40, fireX+getReticleWidth()/2, fireY+player.getHeight()/2);
		}
		//reticle stays in position where the player last was
		else if(isCharging()){
			g.drawOval(fireX, fireY, getReticleWidth(), getReticleWidth());
			g.drawLine(fireX, fireY+getReticleWidth()/2, fireX+getReticleWidth(), fireY+getReticleWidth()/2);
			g.drawLine(fireX+getReticleWidth()/2, fireY, fireX+getReticleWidth()/2, fireY+getReticleWidth());
		}
		//boss fires laser at where reticle aimed
		else if(isFiring()){
			for(float i=0;i<=8;i++){
				g.drawLine(getCenterX()-8, getCenterY()-40, fireX+i, fireY+player.getHeight());
				g.drawLine(getCenterX()+8, getCenterY()-40, fireX+getReticleWidth()+i, fireY+player.getHeight());
			}
		}
		//boss teleports to another platform
		else if(isTeleporting()){
			float opacity=(float)((getTime()-4500)*.001);
			g.setColor(new Color(0,255,0,opacity));
			g.fillOval(getCenterX()-getHeight()/2, getCenterY()-getWidth()/2, getHeight(), getWidth());
		}
	}
}

//locations of platforms: (330,100) (95,160) (565,160)
