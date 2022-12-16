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
	private SoundButton musicButton, sfxButton;
	
	public AudioOption() {
		createSoundButtons();
		createVolumeButton();
	}
	
	private void createSoundButtons() {
		int soundX = (int) (470 * Game.SCALE);
		int musicY = (int) (155 * Game.SCALE - 18);
		int sfxY = (int) (186 * Game.SCALE - 21);
		musicButton = new SoundButton(soundX, musicY, SOUND_SIZE, SOUND_SIZE);
		sfxButton = new SoundButton(soundX, sfxY, SOUND_SIZE, SOUND_SIZE);

	}

	private void createVolumeButton() {
		int vX = (int) (309 * Game.SCALE);
		int vY = (int) (250 * Game.SCALE);
		volumeButton = new VolumeButton(vX, vY, SLIDER_WIDTH, VOLUME_HEIGHT);
	}
	
	public void update() {
		musicButton.update();
		sfxButton.update();
		volumeButton.update();
	}
	
	public void draw(Graphics g) {
		// Sound buttons
		musicButton.draw(g);
		sfxButton.draw(g);
		
		// Volume Button
		volumeButton.draw(g);
	}
	
	public void mouseDragged(MouseEvent e) {
		if (volumeButton.isMousePressed()) {
			volumeButton.changeX(e.getX());
		}

	}

	public void mousePressed(MouseEvent e) {
		if (isIn(e, musicButton))
			musicButton.setMousePressed(true);
		else if (isIn(e, sfxButton))
			sfxButton.setMousePressed(true);
		else if (isIn(e, volumeButton))
	  		volumeButton.setMousePressed(true);
	}

	public void mouseReleased(MouseEvent e) {
		if (isIn(e, musicButton)) {
			if (musicButton.isMousePressed())
				musicButton.setMuted(!musicButton.isMuted());

		} else if (isIn(e, sfxButton)) {
			if (sfxButton.isMousePressed())
				sfxButton.setMuted(!sfxButton.isMuted());
		} 

		musicButton.resetBools();
		sfxButton.resetBools();
		volumeButton.resetBools();

	}

	public void mouseMoved(MouseEvent e) {
		musicButton.setMouseOver(false);
		sfxButton.setMouseOver(false);
		volumeButton.setMouseOver(false);

		if (isIn(e, musicButton))
			musicButton.setMouseOver(true);
		else if (isIn(e, sfxButton))
			sfxButton.setMouseOver(true);
		else if (isIn(e, volumeButton))
			volumeButton.setMouseOver(true);
	}

	private boolean isIn(MouseEvent e, PauseButton b) {
		return b.getBounds().contains(e.getX(), e.getY());
	}
}
