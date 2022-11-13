package Main;

import javax.swing.JFrame;

public class Window {
	
	private JFrame window = new JFrame();
	
	public Window(GamePanel gp) {
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setTitle("Pyramidion");
		
		
		window.add(gp);
		window.setLocationRelativeTo(null);
		window.pack();
		window.setVisible(true);
	}
	
}
