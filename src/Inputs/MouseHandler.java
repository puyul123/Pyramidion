package Inputs;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import GameState.Gamestate;
import Main.GamePanel;

public class MouseHandler implements MouseListener, MouseMotionListener{

	private GamePanel gp;
	
	public MouseHandler(GamePanel gp) {
		this.gp = gp;
	}
	
	
	@Override
	public void mouseDragged(MouseEvent e) {
		switch (Gamestate.state) {
		case PLAYING:
			gp.getGame().getPlaying().mouseDragged(e);
			break;
		default:
			break;

		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		switch (Gamestate.state) {
		case MENU:
			gp.getGame().getMenu().mouseMoved(e);
			break;
		case PLAYING:
			gp.getGame().getPlaying().mouseMoved(e);
			break;
		default:
			break;

		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		switch(Gamestate.state) {
			case MENU -> {
				gp.getGame().getMenu().mouseClicked(e);
			}
			case PLAYING ->{
				gp.getGame().getPlaying().mouseClicked(e);
			}
		}
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		switch(Gamestate.state) {
			case MENU -> {
				gp.getGame().getMenu().mousePressed(e);
			}
			case PLAYING ->{
				gp.getGame().getPlaying().mousePressed(e);
			}
		}
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		switch (Gamestate.state) {
		case MENU:
			gp.getGame().getMenu().mouseReleased(e);
			break;
		case PLAYING:
			gp.getGame().getPlaying().mouseReleased(e);
			break;
		default:
			break;

		}

		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
