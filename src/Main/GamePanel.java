package Main;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;
import Inputs.KeyHandler;
import Inputs.MouseHandler;

public class GamePanel extends JPanel{
	
	private KeyHandler KeyH;
	private MouseHandler MouseH;
	private float velX = 100, velY = 100;
	private float xDir = 1f, yDir = 1f;
	
	public GamePanel() {
		
		//init Input
		KeyH = new KeyHandler(this);
		MouseH = new MouseHandler(this);
		
		addKeyListener(KeyH);
		addMouseListener(MouseH);
		addMouseMotionListener(MouseH);
		
	}
	
	
	
	public void changePosX(int val) {
		this.velX += val;
		repaint();
	}
	public void changePosY(int val) {
		this.velY += val;
		repaint();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		updateRectangle();
		g.fillRect((int)velX, (int)velY, 200, 50);
			
	}

	private void updateRectangle() {
		velX += xDir;
		if(velX < 0 || velX > 400) {
			xDir *= -1;
		}
		velY += yDir;
		if(velY < 0 || velY > 400) {
			yDir *= -1;
		}
	}
	
	
}
