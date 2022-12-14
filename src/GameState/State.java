package GameState;

//import org.w3c.dom.events.MouseEvent;
import java.awt.event.MouseEvent;

import Audio.AudioPlayer;
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
	public void setGameState(Gamestate state) {
		switch(state) {
		case MENU -> game.getAudioPlayer().playSong(AudioPlayer.MAIN_MENU);
		case PLAYING -> game.getAudioPlayer().setLevelSong(game.getPlaying().getLevelManager().getLevelIndex());
		}
		
		Gamestate.state = state;
	}
}
