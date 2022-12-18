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

public class GameWinOverlay {

	private Playing playing;
	private UrmButton menu;
	private BufferedImage img;
	private int bgX, bgY, bgW, bgH;

	public GameWinOverlay(Playing playing) {
		this.playing = playing;
		initImg();
		initButtons();
	}

	private void initButtons() {
		int menuX = (int) (393 * Game.SCALE);
		int y = (int) (195 * Game.SCALE);
		menu = new UrmButton(menuX, y, URM_SIZE, URM_SIZE, 2);
	}
	
	private void initImg() {
		img = LoadSave.GetSpriteAtlas(LoadSave.GAMECOMPLETED_IMG);
		bgW = (int) (img.getWidth() * Game.SCALE);
		bgH = (int) (img.getHeight() * Game.SCALE);
		bgX = Game.WIDTH / 2 - bgW / 2;
		bgY = (int) (75 * Game.SCALE);
	}
	
	public void draw(Graphics g) {
		//g.setColor(new Color(0, 0, 0, 200));
		//g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);

		g.drawImage(img, bgX, bgY, bgW, bgH, null);
		menu.draw(g);
	}
	
	public void update() {
		menu.update();
	}

	private boolean isIn(UrmButton b, MouseEvent e) {
		return b.getBounds().contains(e.getX(), e.getY());
	}

	public void mouseMoved(MouseEvent e) {
		menu.setMouseOver(false);

		if (isIn(menu, e))
			menu.setMouseOver(true);
	}

	public void mouseReleased(MouseEvent e) {
		if (isIn(menu, e)) {
			if (menu.isMousePressed()) {
				playing.resetAll();
				playing.setGameState(Gamestate.MENU);
			}
		}
		menu.resetBools();
	}

	public void mousePressed(MouseEvent e) {
		if (isIn(menu, e))
			menu.setMousePressed(true);
	}

//	public void draw(Graphics g) {
//		g.setColor(new Color(0, 0, 0, 200));
//		g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
//
//		g.setColor(Color.white);
//		g.drawString("Game Over", Game.WIDTH / 2, 150);
//		g.drawString("Press esc to enter Main Menu!", Game.WIDTH / 2, 300);
//
//	}

//	public void keyPressed(KeyEvent e) {
//		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
//			playing.resetAll();
//			Gamestate.state = Gamestate.MENU;
//		}
//	}
}