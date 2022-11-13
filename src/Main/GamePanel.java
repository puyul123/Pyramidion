package Main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import Inputs.KeyHandler;
import Inputs.MouseHandler;

public class GamePanel extends JPanel{
	
	private KeyHandler KeyH;
	private MouseHandler MouseH;
	private float velX = 0, velY = 0;
	private BufferedImage[][] anim;
	private int aniTick, aniIndex, aniSpeed = 25;
	
	public GamePanel() {
		
		//init Input
		KeyH = new KeyHandler(this);
		MouseH = new MouseHandler(this);
		
		//Image
		importImg();
		loadAnimations();
		
		//Frame Game
		setPanelSize();
		
		addKeyListener(KeyH);
		addMouseListener(MouseH);
		addMouseMotionListener(MouseH);
		
	}
	

	private void loadAnimations() {
		
		
	}


	private void importImg() {	
		try {
			anim = new BufferedImage[3][5];
			
			anim[0][0] = ImageIO.read(getClass().getResourceAsStream("/pixil-frame-0.png"));
			anim[1][0] = ImageIO.read(getClass().getResourceAsStream("/pixil-frame-1.png"));
			anim[1][1] = ImageIO.read(getClass().getResourceAsStream("/pixil-frame-2.png"));
			anim[1][2] = ImageIO.read(getClass().getResourceAsStream("/pixil-frame-3.png"));
			anim[1][3] = ImageIO.read(getClass().getResourceAsStream("/pixil-frame-2.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	private void setPanelSize() {
		Dimension size = new Dimension(1280, 720);
		setMinimumSize(size);
		setPreferredSize(size);
		setMaximumSize(size);
		
	}

	private void updateAnimationTick() {
		aniTick++;
		if(aniTick >= aniSpeed) {
			aniTick = 0;
			aniIndex++;
			if(aniIndex >= anim.length) {
				aniIndex = 0;
			}
		}
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
		
		updateAnimationTick();
		
		g.drawImage(anim[1][aniIndex], (int)velX, (int)velY, null);
	}

}
