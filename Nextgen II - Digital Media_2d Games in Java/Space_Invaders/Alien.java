package assignment_6_new;

import java.awt.Image;

public class Alien extends Sprite2D {
	
protected boolean isAlive = true;
protected Image bulletImage;
//////////////////////////////////////////////////////////
	public Alien(Image i1,Image i2,Image bullet) {
		super(i1,i2);
		bulletImage = bullet;
	}
	
	
	
//////////////////////////////////////////////////////////
	public boolean moveEnemy(){
		 x+=xSpeed;
			if(x>750||x<=40){
			return true;
			}
		return false;
		
	}

//////////////////////////////////////////////////////////
	public void reverseDirection(){
	
			y+=20;
		xSpeed = -xSpeed;
	
}

}