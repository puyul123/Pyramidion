package Main;

import java.awt.Color;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import Entity.Player;
import Inputs.KeyHandler;
import Inputs.MouseHandler;
import static Util.Constants.PlayerConstants.*;
import static Util.Constants.Direction.*;

public class GamePanel extends JPanel{
	
	private KeyHandler KeyH;
	private MouseHandler MouseH;
	private Game game;
	
	
	public GamePanel(Game game) {
		
		//init Input
		KeyH = new KeyHandler(this);
		MouseH = new MouseHandler(this);
		this.game = game;

		
		//Frame Game
		setPanelSize();
		
		addKeyListener(KeyH);
		addMouseListener(MouseH);
		addMouseMotionListener(MouseH);
		
	}

	private void setPanelSize() {
		Dimension size = new Dimension(1280, 720);
		setMinimumSize(size);
		setPreferredSize(size);
		setMaximumSize(size);
		
	}

	public void Update() {
		
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		game.render(g);
		
	}
	public Game getGame() {return game;}

}
