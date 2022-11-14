package Inputs;

import java.awt.event.KeyEvent;

import java.awt.event.KeyListener;

import Main.GamePanel;
import static Util.Constants.Direction.*;

public class KeyHandler implements KeyListener{
	
	private GamePanel gp;
	
	public KeyHandler(GamePanel gp) {
		this.gp = gp;
	}
	
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		System.out.println("W");
		switch(key) {
		case KeyEvent.VK_W:
			gp.getGame().getPlayer().setUp(true);
			break;
		case KeyEvent.VK_A:
			gp.getGame().getPlayer().setLeft(true);
			break;
		case KeyEvent.VK_S:
			gp.getGame().getPlayer().setDown(true);
			break;
		case KeyEvent.VK_D:
			gp.getGame().getPlayer().setRight(true);
			break;
		case KeyEvent.VK_ENTER:
			gp.getGame().getPlayer().setAttack(true);
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		
		switch(key) {
		case KeyEvent.VK_W:
			gp.getGame().getPlayer().setUp(false);
			System.out.println("W");
			break;
		case KeyEvent.VK_A:
			gp.getGame().getPlayer().setLeft(false);
			break;
		case KeyEvent.VK_S:
			gp.getGame().getPlayer().setDown(false);
			break;
		case KeyEvent.VK_D:
			gp.getGame().getPlayer().setRight(false);
			break;
		}
	}

}
