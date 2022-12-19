package Inputs;

import java.awt.event.KeyEvent;

import java.awt.event.KeyListener;

import GameState.Gamestate;
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
		switch(Gamestate.state) {
			case MENU -> {
				gp.getGame().getMenu().keyPressed(e);
			}
			case PLAYING ->{
				gp.getGame().getPlaying().keyPressed(e);
			}
			case OPTION ->{
				gp.getGame().getGameOption().keyPressed(e);
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch(Gamestate.state) {
			case MENU -> {
				gp.getGame().getMenu().keyReleased(e);
			}
			case PLAYING ->{
				gp.getGame().getPlaying().keyReleased(e);
			}
		}
	}

}
