package assignment_6_new;
import java.awt.*;

import javax.swing.ImageIcon;

public class Sprite2D {

	//member data
	protected double x,y;
	protected double xSpeed=0;
	protected Image img1;
	protected Image img2;
	int winWidth;
	int framesDrawn = 0;
	
	
	//constructor
	public Sprite2D(Image i1,Image i2){
		img1 = i1;
		img2 = i2;
		
		
	}
//////////////////////////////////////////////////////////
	public void setPosition(double xx, double yy){
		this.x = xx;
		this.y = yy;
	}
	
//////////////////////////////////////////////////////////
	
	public void setXSpeed(double dx){
		xSpeed = dx;
	}
//////////////////////////////////////////////////////////
	public void paint(Graphics g){
		
		framesDrawn++;
		if ( framesDrawn%100<50 )
			g.drawImage(img1, (int)x, (int)y,30,30, null);
			else
			g.drawImage(img2, (int)x, (int)y,30,30, null);
	}
//////////////////////////////////////////////////////////

}
