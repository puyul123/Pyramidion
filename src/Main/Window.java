package Main;

import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

import javax.swing.JFrame;

public class Window {
	
	private JFrame window = new JFrame();
	
	public Window(GamePanel gp) {
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setTitle("Pyramidion");
		
		window.add(gp);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		
		window.addWindowFocusListener(new WindowFocusListener() {

			@Override
			public void windowGainedFocus(WindowEvent e) {
				
			}

			@Override
			public void windowLostFocus(WindowEvent e) {
				gp.getGame().windowFocusLost();
				
			}
		});
	}
	
}
