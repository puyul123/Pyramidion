package Main;

import javax.swing.JFrame;

public class Window {
	
	private JFrame window = new JFrame();
	
	public Window(GamePanel gp) {
		window.setSize(400, 400);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setTitle("Pyramidion");
		
		
		window.add(gp);
		window.setLocationRelativeTo(null);
		window.setVisible(true);
	}
	
}
