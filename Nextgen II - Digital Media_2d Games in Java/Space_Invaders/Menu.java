package assignment_6_new;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;


public class Menu {
	

	public void paint(Graphics g){
		Font fnt0 = new Font("Arial", Font.BOLD, 50);
		
		g.setFont(fnt0);
		g.setColor(Color.WHITE);
		g.drawString("SpaceInvaders or Something..", 50, 100);
		
		Font fnt1 = new Font("arial", Font.BOLD,30);
		g.setFont(fnt1);
		g.drawString("Press Enter to Play",250, 150+40);
		g.drawString("Press Q to Quit",250, 250+40);
		g.drawString("(Arrow Keys to move, Space to fire)",150, 350+40);
		g.drawString("Andre Godinez", 10, 580);
	
		
	}
}

