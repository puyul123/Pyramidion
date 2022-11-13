package Main;

import java.awt.Graphics;

import Entity.Player;

public class Game implements Runnable{
	
	private Window window;
	private GamePanel gp;
	private Thread gameThread;
	private final int FPS_SET = 120;
	private final int UPS_SET = 200;
	
	private Player player;
	
	public Game() {
		init();
		
		gp = new GamePanel(this);
		window = new Window(gp);
		gp.requestFocus();
		
		startGameThread();
	}
	
	protected void init() {
		player = new Player(50, 50);
		
	}

	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	public void Update() {
		player.Update();
	}
	
	public void render(Graphics g) {
		player.render(g);
	}

	@Override
	public void run() {
		
		double drawInterval = 8333333.333333333D; // 10^9 ns (1s) divided by FPS (120)
        double deltaU = 0;
        double deltaF = 0;
        long previousTime = System.nanoTime();
        long currentTime;
        
        int frames = 0;
        int updates = 0;
        
        while (gameThread != null)
        {
            currentTime = System.nanoTime();
            deltaU += (currentTime - previousTime) / drawInterval;
            deltaF += (currentTime - previousTime) / drawInterval;
            previousTime = currentTime;
            if(deltaU >= 1)
            {
                Update();
                deltaU--;
            }
            if(deltaF >= 1) {
            	gp.repaint();
				frames++;
				deltaF--;
            }
        }
	}
	
	public void windowFocusLost() {
		player.resetDirBool();
		
	}
	
	public Player getPlayer() {return player;}

}
