package assignment_6_new;

import java.awt.Graphics;
import java.awt.Image;

public class PlayerBullet extends Sprite2D {

	public PlayerBullet(Image i1) {
		super(i1, i1);
		
	}
	public void paint(Graphics g){
		g.drawImage(img1, (int)x, (int)y,6,10, null);
	}
	public boolean move(){
		y-=10;
		return (y<0);
		// return true if bullet is off the screen and needs destroying
	}

}
