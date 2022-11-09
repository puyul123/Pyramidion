package Main;

public class Game {
	
	private Window window;
	private GamePanel gp;
	
	public Game() {
		gp = new GamePanel();
		window = new Window(gp);
	}
}
