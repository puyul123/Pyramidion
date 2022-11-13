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
		
		switch(key) {
		case KeyEvent.VK_W:
			gp.setDirect(UP);
			break;
		case KeyEvent.VK_A:
			gp.setDirect(LEFT);
			break;
		case KeyEvent.VK_S:
			gp.setDirect(DOWN);
			break;
		case KeyEvent.VK_D:
			gp.setDirect(RIGHT);
			break;
		}
		
//		if(key == KeyEvent.VK_W || key == KeyEvent.VK_SPACE) {
//			gp.setDirect(UP);
//			
//		}
//		if(key == KeyEvent.VK_A) {
//			gp.setDirect(LEFT);
//		}
//		if(key == KeyEvent.VK_S) {
//			gp.setDirect(DOWN);
//			
//		}
//		if(key == KeyEvent.VK_D) {
//			gp.setDirect(RIGHT);
//		}		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		
		switch(key) {
		case KeyEvent.VK_W:
		case KeyEvent.VK_A:
		case KeyEvent.VK_S:
		case KeyEvent.VK_D:
			gp.setMove(false);
			break;
		}
		
//		if(key == KeyEvent.VK_W || key == KeyEvent.VK_SPACE) {
//			gp.setDirect(UP);
//			
//		}
//		if(key == KeyEvent.VK_A) {
//			gp.setDirect(LEFT);
//		}
//		if(key == KeyEvent.VK_S) {
//			gp.setDirect(DOWN);
//			
//		}
//		if(key == KeyEvent.VK_D) {
//			gp.setDirect(RIGHT);
//		}	
	}

}
