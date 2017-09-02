package assignment_6_new;

import java.awt.Image;


public class Spaceship extends Sprite2D {
	protected boolean isAlive = true;
	private Image bulletImage;

	public Spaceship(Image i1,Image bullet) {
		super(i1,i1);
		bulletImage = bullet;
		
	
	}
//////////////////////////////////////////////////////////
	public void movePlayer(){
		if(x>5 && xSpeed<0|| x<750&&xSpeed>0){
			x=x+5*xSpeed;
		}
	}
//////////////////////////////////////////////////////////
	public PlayerBullet shootBullet(){
		PlayerBullet b = new PlayerBullet(bulletImage);
		b.setPosition(this.x+12.5, this.y);
		return b;
		
	}
	
	
}
