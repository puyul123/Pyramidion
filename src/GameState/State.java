package GameState;

//import org.w3c.dom.events.MouseEvent;
import java.awt.event.MouseEvent;

import ui.MenuButton;
import Main.Game;

public class State {
	protected Game game;

	public State(Game game) {
		this.game = game;
	}
	
	public boolean inGame(MouseEvent e, MenuButton menuButton) {
		return menuButton.getBounds().contains(e.getX(), e.getY());
	}

	public Game getGame() {
		return game;
	}
}
