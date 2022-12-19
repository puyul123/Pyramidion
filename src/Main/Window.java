package Main;

import java.awt.event.WindowEvent;

import java.awt.event.WindowFocusListener;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import Util.LoadSave;

public class Window {
	
	private JFrame window = new JFrame();
	private BufferedImage icon;
	
	public Window(GamePanel gp) {
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setTitle("Pyramidion");
		icon = LoadSave.GetSpriteAtlas("game_icon.png");
		
		window.setIconImage(icon);
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
