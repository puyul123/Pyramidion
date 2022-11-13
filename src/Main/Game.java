package Main;

public class Game implements Runnable{
	
	private Window window;
	private GamePanel gp;
	private Thread gameThread;
	private final int FPS_SET = 120;
	private final int UPS_SET = 200;
	
	public Game() {
		gp = new GamePanel();
		window = new Window(gp);
		gp.requestFocus();
	}
	
	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	public void Update() {
		gp.Update();
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
	
//	public void run() {
//		double timePerFrame = 1000000000.0/FPS_SET;
//		long lastFrame = System.nanoTime();
//		long currentFrame = System.nanoTime();
//		int frames = 0;
//		long lastCheck = System.currentTimeMillis();
//		
//		while(true) {
//			currentFrame = System.nanoTime();
//			if(currentFrame - lastFrame >= timePerFrame) {
//				gp.repaint();
//				lastFrame = currentFrame;
//				frames++;
//			}
//			
//			if(System.currentTimeMillis() - lastCheck >= 1000) {
//				lastCheck = System.currentTimeMillis();
//				//System.out.println("FPS : " + frames);
//				frames = 0;
//			}
//			
//		}
//		
//	}
}
