package Main;

import java.awt.Graphics;

import Entity.Player;
import levels.LevelManager;

public class Game implements Runnable{
	
	private Window window;
	private GamePanel gp;
	private Thread gameThread;
	private final int FPS_SET = 120;
	private final int UPS_SET = 200;
	
	private Player player;
	private LevelManager lvlManager;
	
	public final static int TILES_DEFAULT_SIZE = 32;
	public final static float SCALE = 1.5f;
	public final static int TILES_WIDTH = 26;
	public final static int TILES_HEIGHT = 14;
	
	public final static int TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);
	public final static int WIDTH = TILES_SIZE * TILES_WIDTH;
	public final static int HEIGHT = TILES_SIZE * TILES_HEIGHT;
	
	
	
	public Game() {
		init();
		
		gp = new GamePanel(this);
		window = new Window(gp);
		gp.requestFocus();
		
		startGameThread();
	}
	
	protected void init() {
		lvlManager = new LevelManager(this);
		player = new Player(100, 200, (int)(64*SCALE), (int)(64*SCALE));
		player.loadLvlData(lvlManager.getCurrentLevel().getLevelData());
	}

	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	public void Update() {
		lvlManager.Update();
		player.Update();
	}
	
	public void render(Graphics g) {
		lvlManager.draw(g);
		player.render(g);
	}

	@Override
	public void run() {
		
//		double drawInterval = 8333333.333333333D; // 10^9 ns (1s) divided by FPS (120)
//        double deltaU = 0;
//        double deltaF = 0;
//        long previousTime = System.nanoTime();
//        long currentTime;
//        
//        int frames = 0;
//        int updates = 0;
        
        while (gameThread != null)
        {
        	
        	Update();
        	gp.repaint();
        	try {
				Thread.sleep(1000/FPS_SET);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
      
        }
	}
	
	public void windowFocusLost() {
		player.resetDirBool();
		
	}
	
	public Player getPlayer() {return player;}
	

// RUNN
//  currentTime = System.nanoTime();
//  deltaU += (currentTime - previousTime) / drawInterval;
//  deltaF += (currentTime - previousTime) / drawInterval;
//  previousTime = currentTime;
//  if(deltaU >= 1)
//  {
//      Update();
//      deltaU--;
//  }
//  if(deltaF >= 1) {
//  	gp.repaint();
//		frames++;
//		deltaF--;
//  }

}
