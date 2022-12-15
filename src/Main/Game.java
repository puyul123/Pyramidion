package Main;

import java.awt.Color;
import java.awt.Graphics;

import Audio.AudioPlayer;
import GameState.GameOption;
import GameState.Gamestate;
import GameState.MainMenu;
import GameState.Playing;
import Util.LoadSave;
import ui.AudioOption;
import GameState.Gamestate;

public class Game implements Runnable{
	
	private Window window;
	private GamePanel gp;
	private Thread gameThread;
	private final int FPS_SET = 120;
	private final int UPS_SET = 200;
	
	private Playing playing;
	private MainMenu menu;
	private GameOption gameOption;
	private AudioPlayer audioPlayer;
	private AudioOption audioOption;
	
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
		audioOption = new AudioOption();
		menu = new MainMenu(this);
		playing = new Playing(this);
		audioPlayer = new AudioPlayer();
		gameOption = new GameOption(this);
	}

	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	public void Update() {
	
		switch(Gamestate.state) {
			case MENU ->{
				menu.update();
				//break;
			}
			case PLAYING ->{
				playing.update();
				//break;
			}
			case OPTION -> {
				gameOption.update();
			}
			case QUIT -> {
				
			}
//			default -> {
//				System.exit(0);
//				break;
//			}
		}
	}
	
	public void render(Graphics g) {
		
		switch(Gamestate.state) {
			case MENU ->{
				menu.draw(g);
			}
			case PLAYING ->{

				playing.draw(g);
				g.setColor(Color.BLACK);
			}
			case OPTION ->{
				gameOption.draw(g);
			}
		}
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
		if (Gamestate.state == Gamestate.PLAYING)
			playing.getPlayer().resetDirBool();
	}
	
	public MainMenu getMenu() {
		return menu;
	}

	public Playing getPlaying() {
		return playing;
	}
	
	public GameOption getGameOption() {
		return gameOption;
	}
	
	public AudioPlayer getAudioPlayer() {
		return audioPlayer;
	}

	public AudioOption getAudioOption() {
		return audioOption;
	}
}

//RUNN
//currentTime = System.nanoTime();
//deltaU += (currentTime - previousTime) / drawInterval;
//deltaF += (currentTime - previousTime) / drawInterval;
//previousTime = currentTime;
//if(deltaU >= 1)
//{
//  Update();
//  deltaU--;
//}
//if(deltaF >= 1) {
//	gp.repaint();
//	frames++;
//	deltaF--;
//}
