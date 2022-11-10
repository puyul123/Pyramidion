package Inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import Main.GamePanel;

public class KeyHandler implements KeyListener{
	
	private GamePanel gp;
	
	public KeyHandler(GamePanel gp) {
		this.gp = gp;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_W) {
			gp.changePosY(-3);
//			System.out.println("W Pressed");
			
		}
		if(key == KeyEvent.VK_A) {
			gp.changePosX(-3);
//			System.out.println("A Pressed");
		}
		if(key == KeyEvent.VK_S) {
			gp.changePosY(3);
//			System.out.println("S Pressed");
		}
		if(key == KeyEvent.VK_D) {
			gp.changePosX(3);
//			System.out.println("D Pressed");
		}		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}

}
