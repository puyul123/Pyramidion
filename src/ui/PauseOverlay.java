package ui;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import GameState.Gamestate;
import GameState.Playing;
import Main.Game;
import Util.Constants;
import Util.LoadSave;
import static Util.Constants.UI.PauseButtons.*;
import static Util.Constants.UI.URMButtons.*;
import static Util.Constants.UI.VolumeButtons.*;

public class PauseOverlay {

	private Playing playing;
	private BufferedImage backgroundImg;
	private int bgX, bgY, bgW, bgH;
//	private SoundButton musicButton, sfxButton;
	private UrmButton menuB, replayB, unpauseB;
//	private VolumeButton volumeButton;
	private AudioOption audioOption;
	
	public PauseOverlay(Playing playing) {
		this.playing = playing;
		loadBackground();
		audioOption = playing.getGame().getAudioOption();
		
		//createSoundButtons();
		createUrmButtons();
		//createVolumeButton();

	}

	private void createUrmButtons() {
		int menuX = (int) (313 * Game.SCALE);
		int replayX = (int) (387 * Game.SCALE);
		int unpauseX = (int) (462 * Game.SCALE);
		int bY = (int) (300 * Game.SCALE);

		menuB = new UrmButton(menuX, bY, URM_SIZE, URM_SIZE, 2);
		replayB = new UrmButton(replayX, bY, URM_SIZE, URM_SIZE, 1);
		unpauseB = new UrmButton(unpauseX, bY, URM_SIZE, URM_SIZE, 0);

	}

	private void loadBackground() {
		backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.PAUSE_BACKGROUND);
		bgW = (int) (backgroundImg.getWidth() / 2 * Game.SCALE);
		bgH = (int) (backgroundImg.getHeight() / 2 * Game.SCALE);
		bgX = Game.WIDTH / 2 - bgW / 2;
		bgY = (int) (33 * Game.SCALE);

	}

	public void update() {
		
		menuB.update();
		replayB.update();
		unpauseB.update();

		audioOption.update();

	}

	public void draw(Graphics g) {
		// Background
		g.drawImage(backgroundImg, bgX, bgY, bgW, bgH, null);

		// UrmButtons
		menuB.draw(g);
		replayB.draw(g);
		unpauseB.draw(g);

		audioOption.draw(g);
	}

	public void mouseDragged(MouseEvent e) {
//		if (volumeButton.isMousePressed()) {
//			volumeButton.changeX(e.getX());
//		}

		audioOption.mouseDragged(e);
	}

	public void mousePressed(MouseEvent e) {
//		if (isIn(e, musicButton))
//			musicButton.setMousePressed(true);
//		else if (isIn(e, sfxButton))
//			sfxButton.setMousePressed(true);
		if (isIn(e, menuB))
			menuB.setMousePressed(true);
		else if (isIn(e, replayB))
			replayB.setMousePressed(true);
		else if (isIn(e, unpauseB))
			unpauseB.setMousePressed(true);
		else
			audioOption.mousePressed(e);
//		else if (isIn(e, volumeButton))
//			volumeButton.setMousePressed(true);
	}

	public void mouseReleased(MouseEvent e) {
//		if (isIn(e, musicButton)) {
//			if (musicButton.isMousePressed())
//				musicButton.setMuted(!musicButton.isMuted());
//
//		} else if (isIn(e, sfxButton)) {
//			if (sfxButton.isMousePressed())
//				sfxButton.setMuted(!sfxButton.isMuted());
		if (isIn(e, menuB)) {
			if (menuB.isMousePressed()) {
				playing.setGameState(Gamestate.MENU);
				playing.unpauseGame();
			}
		} 
		else if (isIn(e, replayB)) {
			if (replayB.isMousePressed()) {
				playing.resetAll();
				playing.unpauseGame();
			}
		} 
		else if (isIn(e, unpauseB)) {
			if (unpauseB.isMousePressed())
				playing.unpauseGame();
		}
		else
			audioOption.mouseReleased(e);

//		musicButton.resetBools();
//		sfxButton.resetBools();
		menuB.resetBools();
		replayB.resetBools();
		unpauseB.resetBools();
//		volumeButton.resetBools();

	}

	public void mouseMoved(MouseEvent e) {
//		musicButton.setMouseOver(false);
//		sfxButton.setMouseOver(false);
		menuB.setMouseOver(false);
		replayB.setMouseOver(false);
		unpauseB.setMouseOver(false);
//		volumeButton.setMouseOver(false);

//		if (isIn(e, musicButton))
//			musicButton.setMouseOver(true);
//		else if (isIn(e, sfxButton))
//			sfxButton.setMouseOver(true);
		if (isIn(e, menuB))
			menuB.setMouseOver(true);
		else if (isIn(e, replayB))
			replayB.setMouseOver(true);
		else if (isIn(e, unpauseB))
			unpauseB.setMouseOver(true);
		else
			audioOption.mouseMoved(e);
//		else if (isIn(e, volumeButton))
//			volumeButton.setMouseOver(true);

	}

	private boolean isIn(MouseEvent e, PauseButton b) {
		return b.getBounds().contains(e.getX(), e.getY());
	}

}