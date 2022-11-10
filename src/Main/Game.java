package Main;

public class Game implements Runnable{
	
	private Window window;
	private GamePanel gp;
	private Thread gameThread;
	private final int FPS_SET = 120;
	
	public Game() {
		gp = new GamePanel();
		window = new Window(gp);
		gp.requestFocus();
		
		startGameThread();
	}
	
	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}

	@Override
	public void run() {
		double timePerFrame = 1000000000.0/FPS_SET;
		long lastFrame = System.nanoTime();
		long currentFrame = System.nanoTime();
		int frames = 0;
		long lastCheck = System.currentTimeMillis();
		
		while(true) {
			currentFrame = System.nanoTime();
			if(currentFrame - lastFrame >= timePerFrame) {
				gp.repaint();
				lastFrame = currentFrame;
				frames++;
			}
			
			if(System.currentTimeMillis() - lastCheck >= 1000) {
				lastCheck = System.currentTimeMillis();
				//System.out.println("FPS : " + frames);
				frames = 0;
			}
			
		}
		
	}
}
