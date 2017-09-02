package assignment_6_new;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


import javax.swing.*;
import java.awt.image.*;
import java.util.ArrayList;
import java.util.Iterator;





public class SpaceInvadersApp extends JFrame implements Runnable, KeyListener{
	private static final long serialVersionUID = 1L;
	//member data
	////////////////////////////////////////////////////////////////////
	private static final Dimension WindowSize = new Dimension(800,600);
	private static final int NUMALIENS = 30;
	private static String workingDirectory;
	private BufferStrategy strategy;
	private static boolean GraphicsInit = false; 
	
	//////////////////////////////////////////////////////
	private Alien[] AliensArray = new Alien[NUMALIENS];
	private Spaceship PlayerShip;
	private Image bulletImage;
	private Image alienImage1;
	private Image alienImage2;
	private Image background;
	private ArrayList<PlayerBullet> bulletsList = new ArrayList<PlayerBullet>();
	private ArrayList<PlayerBullet> alienBullet = new ArrayList<PlayerBullet>();
	/////////////////////////////////////////////////////////
	private enum STATE{
		MENU,
		GAME,
		GAMEOVER
	};
	
	private STATE State = STATE.MENU;
	Menu menu = new Menu();
	GameOver gameover = new GameOver();
	///////////////////////////////////////////////////////
	boolean collision = false;
	boolean playerCollision = false;
	//////////////////////////////////////////////////////////
	private int score = 0;
	private int highscore = 0;
	private int numAliens = NUMALIENS;
	private int AlienSpeed = 4;
	 int framesDrawn = 0;
	/////////////////////////////////////////////////////////
	//constructor
	public SpaceInvadersApp(){
		// set title
				// set how to close
				this.setTitle("Assignment3-SpaceInvaders");
				setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

				// Display the window, centred on the screen
				Dimension screensize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
				int x = screensize.width / 2 - WindowSize.width / 2;
				int y = screensize.height / 2 - WindowSize.height / 2;
				setBounds(x, y, WindowSize.width, WindowSize.height);
				setResizable(true);
				setVisible(true);
				
				ImageIcon bg = new ImageIcon(workingDirectory+"//bg.png");
				background = bg.getImage();
				
				ImageIcon alien1 = new ImageIcon(workingDirectory+"//alien_ship_1.png");
				ImageIcon alien2 = new ImageIcon(workingDirectory+"//alien_ship_2.png");
				 alienImage1 = alien1.getImage();
				alienImage2 = alien2.getImage();
				ImageIcon bulletimg = new ImageIcon(workingDirectory + "//bullet.png");
				bulletImage = bulletimg.getImage();
				
				
				startNewWave();
				
				
				ImageIcon Player = new ImageIcon(workingDirectory+"//player_ship.png");
				Image PlayerImg = Player.getImage();
				
				PlayerShip = new Spaceship(PlayerImg,bulletImage);
				PlayerShip.setPosition(380, 500);
				//////////////////////////////////////create and start our animation thread
				Thread t = new Thread(this);
				t.start();
				//////////////////////////send keyboard events arriving into this jframe back to its own event handlers
				addKeyListener(this);
				////////////////////initialise double buffering
				createBufferStrategy(2);
				strategy = getBufferStrategy();
				
				
					
				GraphicsInit = true;
					

	}

	//application's paint method.
	/////////////////////////////////////////////////////////////////////////////////////
	@Override
	public void run() {
		
		while (true) {
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(State == STATE.GAME){
				if(numAliens!=0){
						for (int i = 0; i <NUMALIENS; i++) {
							if(AliensArray[i].isAlive)
							if(AliensArray[i].moveEnemy())
								collision = true;	
						}
								for (int j = 0; j < NUMALIENS; j++){
									if(AliensArray[j].isAlive)
									if(collision){
									AliensArray[j].reverseDirection();
									}
										}
										collision = false;
															}
									else{
										System.out.println(" no more aliens");
										startNewWave();
									}
			
			PlayerShip.movePlayer();
			//////////////////////////////////////////////
				for(int j = 0; j < NUMALIENS;j++){
					if(AliensArray[j].isAlive)
					if ( ((AliensArray[j].x<PlayerShip.x && AliensArray[j].x+30>PlayerShip.x) ||(PlayerShip.x<AliensArray[j].x && PlayerShip.x+6>AliensArray[j].x) )&&((AliensArray[j].y<PlayerShip.y && AliensArray[j].y+30>PlayerShip.y) ||(PlayerShip.y<AliensArray[j].y && PlayerShip.y+10>AliensArray[j].y))){
						playerCollision=true;
						State = STATE.GAMEOVER;
						startNewWave();
						startNewGame();
					}
					}
			//////////////////////////////////////////////
			
			Iterator<PlayerBullet> iterator = bulletsList.iterator();
			
			while(iterator.hasNext()){
			PlayerBullet b = (PlayerBullet) iterator.next();
			if(b.move()){
				iterator.remove();
				//true returned by move method if bullet needs destroying due to going offscreen
				//iterator.remove() is a safe way to remove from the arraylist while iterating through it
			}
			else{
				for(int j = 0; j < NUMALIENS;j++)
					if(AliensArray[j].isAlive)
					if ( ((AliensArray[j].x<b.x && AliensArray[j].x+30>b.x) ||(b.x<AliensArray[j].x && b.x+6>AliensArray[j].x) )&&((AliensArray[j].y<b.y && AliensArray[j].y+30>b.y) ||(b.y<AliensArray[j].y && b.y+10>AliensArray[j].y))){
						//destroy alien and bullet
						AliensArray[j].isAlive=false;
						score+=10;
						numAliens--;
					
						iterator.remove();
						break;
					}
				}
			}
			
		
			
			}
			this.repaint();
		}

}

/////////////////////////////////////////////////////////////////////////////////////	
	
	public void paint(Graphics g){
		if(!GraphicsInit)
			return;
		
		g = strategy.getDrawGraphics();
		
		Color c = Color.black;
		g.setColor(c);
		g.fillRect(0, 0, WindowSize.width,WindowSize.height);
		g.drawImage(background,0,0,800,600, null);
		
		if(State == STATE.GAME){
		Color d = Color.white;
		g.setColor(d);
		Font fnt0 = new Font("arial", Font.BOLD,15);
		g.setFont(fnt0);
		g.drawString("Score : "+score,250, 50);
		g.drawString("Highscore : "+highscore,400, 50);
		
		}
	
		
	
		//redraw all game objects
		if(State == STATE.GAME){
		for (int i = 0; i <NUMALIENS; i++) {
			if(AliensArray[i].isAlive)
			AliensArray[i].paint(g);
		}
		/////////////////////////////////////////////
		PlayerShip.paint(g);
		//
		
		Iterator<PlayerBullet> iterator = bulletsList.iterator();
		while(iterator.hasNext()){
			PlayerBullet b = iterator.next();
			b.paint(g);
		}
		}
		else if(State == STATE.MENU){
			menu.paint(g);
		}
		else if(State == STATE.GAMEOVER){
			gameover.paint(g);
		}
		
		
		g.dispose();
		//flip the buffers
		strategy.show();
		
		
	}
	/////////////////////////////////////////////////////////////////////////////////////
	@Override
	public void keyPressed(KeyEvent e) {
		if(State == STATE.GAME||State == STATE.GAMEOVER){
		if(e.getKeyCode()==KeyEvent.VK_RIGHT){
			PlayerShip.setXSpeed(1);
		}
		if(e.getKeyCode()==KeyEvent.VK_LEFT){
			PlayerShip.setXSpeed(-1);
		}
		if(e.getKeyCode()==KeyEvent.VK_SPACE){
			bulletsList.add(PlayerShip.shootBullet());
		}
		}
		if(e.getKeyCode()==KeyEvent.VK_ENTER){
			State = STATE.GAME;
		}
		if(e.getKeyCode()==KeyEvent.VK_Q){
			System.exit(getDefaultCloseOperation());
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		PlayerShip.setXSpeed(0);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
		
	}
	///////////////////////////////////////////////////////////////////////////////////////
	public void shootBullet(){
		
		PlayerBullet bullet = new PlayerBullet(bulletImage);
		
		bulletsList.add(bullet);
		
		
	}
	
	public void startNewWave(){
		numAliens = NUMALIENS;
		AlienSpeed+=1;
		int count = 0;
		
		for(int rows = 1; rows <7 ; rows++){
			for(int col = 1 ; col <6 ; col++){
		AliensArray[count] = new Alien(alienImage1,alienImage2,bulletImage);
		AliensArray[count].setPosition((int)rows*50, (int)col*50);
		AliensArray[count].setXSpeed(AlienSpeed);
		count++;
		
			}
		}
		
	}
	
	public void startNewGame(){
		if(highscore == 0){
		highscore = score;
		}
		else if(score>highscore){
		highscore = score;
		}
		score = 0;
		AlienSpeed = 4;
		PlayerShip.setPosition(380, 500);
	}


		
	/////////////////////////////////////////////////////////////////////////////////////
	public static void main(String[] args){
		workingDirectory = System.getProperty("user.dir");
		System.out.println(workingDirectory);
		SpaceInvadersApp w = new SpaceInvadersApp();
	}
}
