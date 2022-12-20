package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import GameState.Gamestate;
import GameState.Playing;
import Main.Game;
import Util.LoadSave;
import static Util.Constants.UI.URMButtons.*;

public class LevelCompletedOverlay {

	private Playing playing;
	private UrmButton menu, next, retry;
	private BufferedImage img;
	private int bgX, bgY, bgW, bgH;

	public LevelCompletedOverlay(Playing playing) {
		this.playing = playing;
		initImg();
		initButtons();
	}

	private void initButtons() {
		int menuX = (int) (340 * Game.SCALE);
		int retryX = (int) (393 * Game.SCALE);
		int nextX = (int) (446 * Game.SCALE);
		int y = (int) (195 * Game.SCALE);
		next = new UrmButton(nextX, y, URM_SIZE, URM_SIZE, 0);
		retry = new UrmButton(retryX, y, URM_SIZE, URM_SIZE, 1);
		menu = new UrmButton(menuX, y, URM_SIZE, URM_SIZE, 2);
	}

	private void initImg() {
		img = LoadSave.GetSpriteAtlas(LoadSave.COMPLETED_IMG);
		bgW = (int) (img.getWidth() * Game.SCALE);
		bgH = (int) (img.getHeight() * Game.SCALE);
		bgX = Game.WIDTH / 2 - bgW / 2;
		bgY = (int) (75 * Game.SCALE);
	}

	public void draw(Graphics g) {
		g.setColor(new Color(0, 0, 0, 200));
		g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);

		g.drawImage(img, bgX, bgY, bgW, bgH, null);
		next.draw(g);
		menu.draw(g);
		retry.draw(g);
	}

	public void update() {
		next.update();
		menu.update();
		retry.update();
	}

	private boolean isIn(UrmButton b, MouseEvent e) {
		return b.getBounds().contains(e.getX(), e.getY());
	}

	public void mouseMoved(MouseEvent e) {
		next.setMouseOver(false);
		menu.setMouseOver(false);
		retry.setMouseOver(false);

		if (isIn(menu, e))
			menu.setMouseOver(true);
		else if (isIn(next, e))
			next.setMouseOver(true);
		else if (isIn(retry, e))
			retry.setMouseOver(true);
	}

	public void mouseReleased(MouseEvent e) {
		if (isIn(menu, e)) {
			if (menu.isMousePressed()) {
				playing.resetAll();
				playing.setGameState(Gamestate.MENU);
			}
		} else if (isIn(next, e)) {
			if (next.isMousePressed()) {
				playing.loadNextLevel();
				playing.getPlayer().setCurrentPointLevel(playing.getPlayer().getCurrentPoint());
				//System.out.println("next: " +playing.getPlayer().getCurrentPointLevel());
			}
		}
		else if (isIn(retry, e)) {
			playing.resetAll();
			playing.getGame().getAudioPlayer().setLevelSong(playing.getLevelManager().getLevelIndex());
			playing.getPlayer().setCurrentPoint(playing.getPlayer().getCurrentPointLevel());
			//System.out.println("retry : " +playing.getPlayer().getCurrentPointLevel());
			
		}
		menu.resetBools();
		next.resetBools();
		retry.resetBools();
	}

	public void mousePressed(MouseEvent e) {
		if (isIn(menu, e))
			menu.setMousePressed(true);
		else if (isIn(next, e))
			next.setMousePressed(true);
		else if (isIn(retry, e))
			retry.setMousePressed(true);
	}


}