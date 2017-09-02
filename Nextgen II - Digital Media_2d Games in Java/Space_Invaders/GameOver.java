package assignment_6_new;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class GameOver {

		public void paint(Graphics g){
			Font fnt0 = new Font("Arial", Font.BOLD, 50);
			
			g.setFont(fnt0);
			g.setColor(Color.WHITE);
			g.drawString("SpaceInvaders or Something..", 50, 100);
			Font fnt2 = new Font("arial",Font.BOLD,60);
			g.setFont(fnt2);
			g.drawString("Game Over", 240 , 150+40);
			Font fnt1 = new Font("arial", Font.BOLD,30);
			g.setFont(fnt1);
			g.drawString("Press Enter to Play again",230, 250+40);
			g.drawString("Press Q to Quit",250, 350+40);
			g.drawString("(Arrow Keys to move, Space to fire)",150, 450+40);
		
			
		}
	}


