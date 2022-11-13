package Inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import Main.GamePanel;

public class KeyHandler implements KeyListener{
	
	private GamePanel gp;
	private boolean jump, left, right;
	
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
		
		if(key == KeyEvent.VK_W || key == KeyEvent.VK_SPACE) {
			jump = true;
			gp.changePosY(-5);
			
		}
		if(key == KeyEvent.VK_A) {
			left = true;
			gp.changePosX(-5);
		}
		if(key == KeyEvent.VK_S) {
			gp.changePosY(5);
			
		}
		if(key == KeyEvent.VK_D) {
			right = true;
			gp.changePosX(5);
		}		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_W || key == KeyEvent.VK_SPACE) {
			jump = false;
			gp.changePosY(-3);
			System.out.println("W Pressed");
			
		}
		if(key == KeyEvent.VK_A) {
			left = false;
			gp.changePosX(-3);
			System.out.println("A Pressed");
		}
		if(key == KeyEvent.VK_S) {
			gp.changePosY(3);
			System.out.println("S Pressed");
		}
		if(key == KeyEvent.VK_D) {
			right = false;
			gp.changePosX(3);
			System.out.println("D Pressed");
		}	
	}

}
