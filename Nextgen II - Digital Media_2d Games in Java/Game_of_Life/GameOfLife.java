package game_of_life;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.Iterator;


import javax.swing.JFrame;




public class GameOfLife extends JFrame implements MouseListener, Runnable,MouseMotionListener{
	//member data
	private static final Dimension WindowSize = new Dimension(800,800);
	private BufferStrategy strategy;
	private static boolean GraphicsInit = false; 
	int numCellsd = 0;
	//arrays
	boolean[][][] cell = new boolean[40][40][2];
	//cell buffer

	
	int Frontbuffer =0;
	//game states
	private enum STATE{
		NOTPLAYING,
		PLAYING
	};
	private STATE State = STATE.NOTPLAYING;
	
	public GameOfLife(){
		
		// set title
		// set how to close
		this.setTitle("Game Of Life");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Display the window, centred on the screen
		Dimension screensize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		int x = screensize.width / 2 - WindowSize.width / 2;
		int y = screensize.height / 2 - WindowSize.height / 2;
		setBounds(x, y, WindowSize.width, WindowSize.height);
		setResizable(true);
		setVisible(true);
		///initialising cell values
		for(int i = 0; i < 40;i++){
			for(int j = 0; i < 40;i++){
				cell[i][j][0] = cell[i][j][1] =false;
			}
		}
		
		//////////////////////////////////////create and start our animation thread
		Thread t = new Thread(this);
		t.start();
		//////////////////////////send mouse events arriving into this jframe back to its own event handlers
		addMouseListener(this);
		addMouseMotionListener(this);
		////////////////////initialise double buffering
		createBufferStrategy(2);
		strategy = getBufferStrategy();



		GraphicsInit = true;
	}
//end constructor
	
	
	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		//2. Animate Game objects
		
			//loop through all cells 
			if(State==STATE.PLAYING){
		
					doOneGeneration();
					
				}	
				
		
		//3. force application repaint
		this.repaint();
			}
	}
	//end run method

		

	public void paint(Graphics g){
			if(!GraphicsInit)
				return;
			g = strategy.getDrawGraphics();
			
			//DRAWING NEW BLACK FRAME SO IT DOESNT PAINT OVER EACHOTHER,
			Color c = Color.black;
			g.setColor(c);
			g.fillRect(0, 0, WindowSize.width,WindowSize.height);
			
			if(State == STATE.NOTPLAYING){
			//drawing buttons
			g.setColor(Color.GREEN);
			//green button for start
			//dimensions
			//100x30
			//x -> 20-120 ,y -> 40-80
			g.fillRect(20, 40, 100, 40);
			//green button for random
			//dimensions -> 100x30
			// x-> 140 -> 240 , y -> 40 - 80
			g.fillRect(140, 40, 100, 40);
			
			g.fillRect(340, 40, 100, 40);
			g.fillRect(460, 40, 100, 40);
			g.fillRect(580, 40, 100, 40);
	
			
			//drawing strings
			Font fnt1 = new Font("arial", Font.BOLD,25);
			g.setFont(fnt1);
			g.setColor(Color.BLACK);
			g.drawString("Start", 20+15, 40+25);
			g.drawString("Random", 140, 40+25);
			g.drawString("Load", 340+20, 40+25);
			g.drawString("Save", 460+20, 40+25);
			g.drawString("Clear", 580+15, 40+25);
			}
			
			//DRAWING GRID LINES
			/*
			int red = 96;
			int green = 96;
			int blue = 96;
			Color grid = new Color(red,green,blue);
			g.setColor(grid);
		
			
			for(int i = 0;i<40;i++){
				for(int j = 0; j < 40; j ++){
					g.drawRect(i*20,j*20,20,20);
				}
				
			}*/
		
			
			for(int i = 0; i < 40 ; i++){
				for(int j = 0; j < 40 ; j++){
					if(cell[i][j][Frontbuffer] == true){
						
						g.setColor(Color.white);
						g.fillRect(i*20, j*20, 20, 20);
					}
				}
			}
		
			
			
			g.dispose();
			//flip the buffers
			strategy.show();
			
			
		}
	
	public int[] readFile(){
		String line = null;
		int []i = null;
		String filename = "C:\\Users\\ajguy\\workspace\\NextGen Assignments\\savefile.txt";
		try {
		BufferedReader reader = new BufferedReader(new FileReader(filename));
		String [] s = reader.readLine().split(",");
		i =  new int[s.length];
		for(int k =0;k<s.length;k++){
		    i[k]= Integer.parseInt(s[k]);
		 }
		reader.close();
	
		} catch (IOException e) { }
		return i;
		
	}

	public void writeFile(){
		
		String filename = "C:\\Users\\ajguy\\workspace\\NextGen Assignments\\savefile.txt";
		String superString = "";
		try {
		BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
		for(int i=0;i<cell.length;i++){
			for(int j=0;j<cell.length;j++){
				if(cell[i][j][Frontbuffer] == true){
					String temp = i +","+j+",";
					superString+=temp;
				}
					
			}
			
		}
		writer.write(superString);
		System.out.println(superString);
		writer.close();
		}
		catch (IOException e) { }

	}
	
	public void doOneGeneration(){
		
		int frontbuffer = Frontbuffer;
		
		int backbuffer = ((frontbuffer+1)%2);
		
		 for(int x = 0; x < 40; x++)
	        {
	            for(int y = 0; y < 40; y++)
	            {
	            	int numN = 0 ;
	            	  for(int xx = -1; xx <= 1; xx++)
	                  {
	                      for(int yy = -1; yy <= 1; yy++)
	                      {
	                          if(xx!=0 || yy!=0)
	                          {
	                              int xxx = x+xx;
	                              if(xxx < 0)
	                                  xxx=39;
	                              else if(xxx > 39)
	                                  xxx = 0;
	                              int yyy = y + yy;
	                              if(yyy < 0)
	                                  yyy=39;
	                              else if (yyy>39)
	                                  yyy=0;
	                              if(cell[xxx][yyy][frontbuffer])
	                                numN++;
	                          }
	                      }
	                  }
	                 
	                  if(cell[x][y][frontbuffer])
	                  {
	                      if(numN < 2)
	                         cell[x][y][backbuffer] =false;
	                      else if(numN < 4)
	                          cell[x][y][backbuffer] =true;
	                      else
	                          cell[x][y][backbuffer] =false;
	                  }
	                  else
	                  {
	                      if(numN == 3)
	                          cell[x][y][backbuffer] =true;
	                      else
	                        cell[x][y][backbuffer] =false;
	                  }
	              }
	          }
	            
		
		Frontbuffer=backbuffer;
	
	}
	
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if(State==STATE.NOTPLAYING){
		int x = (int) Math.floor(e.getX());
		int y = (int) Math.floor(e.getY());
		int cellx = x/20;
		int celly = y/20;
		
		if((x>=20&&x<=120)&&(y>=40&&y<=80)){
			//System.out.println("Pressed start button");
			State = STATE.PLAYING;
		}
		else if((x>=140&&x<=240)&&(y>=40&&y<=80)){
			//System.out.println("Pressed random button");
			for(int i = 0; i < 100 ; i++){
				int xx = (int)(Math.random() * (40));
				int yy = (int)(Math.random() * (40));
				
				cell[xx][yy][Frontbuffer] = true;
				
			
				//State = STATE.PLAYING;
			}
		}
		else if((x>=340&&x<=440)&&(y>=40&&y<=80)){
			
			int[]numArray = readFile();
			int xx = 0;
			int yy = 1;
			
			while(xx!=numArray.length||yy!=numArray.length){
			cell[numArray[xx]][numArray[yy]][Frontbuffer] = true;
			xx+=2;
			yy+=2;
			}
			
		}
		
		else if((x>=460&&x<=560)&&(y>=40&&y<=80)){
			writeFile();
		}
		else if((x>=580&&x<=680)&&(y>=40&&y<=80)){
			
			for(int ii = 0; ii < 40;ii++){
				for(int jj = 0; jj < 40;jj++){
					cell[ii][jj][Frontbuffer] = false;
				}
		
			}
		}
		
		else{
			cell[cellx][celly][Frontbuffer] ^= true;
		}
	
		}	
	}
	
	

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	


	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		int x = (int) Math.floor(e.getX());
		int y = (int) Math.floor(e.getY());
		int cellx = x/20;
		int celly = y/20;
		
		if(State==STATE.NOTPLAYING){
			cell[cellx][celly][Frontbuffer] ^= true;
		}
		
	}


	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	public static void main(String[] args){
		
		GameOfLife test = new GameOfLife();
		}
}
