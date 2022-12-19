package ui;

import static Util.Constants.UI.PauseButtons.SOUND_SIZE;
import static Util.Constants.UI.VolumeButtons.SLIDER_WIDTH;
import static Util.Constants.UI.VolumeButtons.VOLUME_HEIGHT;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

import GameState.Gamestate;
import Main.Game;

public class AudioOption {
	
	private VolumeButton volumeButton;
	private SoundButton musicButton;
	private Game game;
	
	public AudioOption(Game game) {
		this.game = game;
		createSoundButtons();
		createVolumeButton();
	}
	
	private void createSoundButtons() {
		int soundX = (int) (470 * Game.SCALE);
		int musicY = (int) (154 * Game.SCALE);
		musicButton = new SoundButton(soundX, musicY, SOUND_SIZE, SOUND_SIZE);

	}

	private void createVolumeButton() {
		int vX = (int) (309 * Game.SCALE);
		int vY = (int) (250 * Game.SCALE);
		volumeButton = new VolumeButton(vX, vY, SLIDER_WIDTH, VOLUME_HEIGHT);
	}
	
	public void update() {
		musicButton.update();
		volumeButton.update();
	}
	
	public void draw(Graphics g) {
		// Sound buttons
		musicButton.draw(g);
		
		// Volume Button
		volumeButton.draw(g);
	}
	
	public void mouseDragged(MouseEvent e) {
		if (volumeButton.isMousePressed()) {
			float valueBefore = volumeButton.getFloatValue();
			volumeButton.changeX(e.getX());
			float valueAfter = volumeButton.getFloatValue();
			if (valueBefore != valueAfter) 
				game.getAudioPlayer().setVolume(valueAfter);
		}
	}

	public void mousePressed(MouseEvent e) {
		if (isIn(e, musicButton))
			musicButton.setMousePressed(true);
		else if (isIn(e, volumeButton))
	  		volumeButton.setMousePressed(true);
	}

	public void mouseReleased(MouseEvent e) {
		if (isIn(e, musicButton)) {
			if (musicButton.isMousePressed()) {
				musicButton.setMuted(!musicButton.isMuted());
				game.getAudioPlayer().toggleSongMute();
			}
		} 
		
		musicButton.resetBools();
		volumeButton.resetBools();

	}

	public void mouseMoved(MouseEvent e) {
		musicButton.setMouseOver(false);
		volumeButton.setMouseOver(false);

		if (isIn(e, musicButton))
			musicButton.setMouseOver(true);
		else if (isIn(e, volumeButton))
			volumeButton.setMouseOver(true);
	}

	private boolean isIn(MouseEvent e, PauseButton b) {
		return b.getBounds().contains(e.getX(), e.getY());
	}
}
