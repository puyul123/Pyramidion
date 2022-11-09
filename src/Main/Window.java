package Main;

import javax.swing.JFrame;

public class Window {
	
	private JFrame window = new JFrame();
	
	public Window(GamePanel gp) {
		window.setSize(600, 400);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		window.add(gp);
		window.setVisible(true);
		window.setTitle("Pyramidion");
	}
	
}
