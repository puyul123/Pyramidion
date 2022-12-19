package GameState;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;

import Main.Game;
import Util.LoadSave;
import ui.AudioOption;
import ui.PauseButton;
import ui.UrmButton;
import static Util.Constants.UI.URMButtons.*;

public class GameOption extends State implements StateMethods{

	private AudioOption audioOption;
	private BufferedImage bgImage, optionBgImage;
	private int bgX, bgY, bgW, bgH;
	private UrmButton menuB;
	
	public GameOption(Game game) {
		super(game);
		loadImages();
		loadButtons();
		audioOption = game.getAudioOption();
	}
	
	private void loadImages() {
		bgImage = LoadSave.GetSpriteAtlas(LoadSave.PAUSE_BACK);
		optionBgImage = LoadSave.GetSpriteAtlas(LoadSave.PAUSE_BACKGROUND);
	
		bgW = (int)(optionBgImage.getWidth() / 2 * Game.SCALE);
		bgH = (int)(optionBgImage.getHeight() / 2 * Game.SCALE);
		bgX = Game.WIDTH / 2 - bgW / 2;
		bgY = (int) (33 * Game.SCALE);
	}
	
	private void loadButtons() {
		int menuX = (int)(387 * Game.SCALE);
		int menuY = (int)(300 * Game.SCALE);
		
		menuB = new UrmButton(menuX, menuY, URM_SIZE, URM_SIZE, 2);
	}
	
	@Override
	public void update() {
		menuB.update();
		audioOption.update();
	}
	
	@Override
	public void draw(Graphics g) {
		g.drawImage(bgImage, 0, 0, Game.WIDTH, Game.HEIGHT, null);
		g.drawImage(optionBgImage, bgX, bgY, bgW, bgH, null);
	
		menuB.draw(g);
		audioOption.draw(g);
	}
	
	public void mouseDragged(MouseEvent e) {
		audioOption.mouseDragged(e);
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		if(isIn(e, menuB)) 
			menuB.setMousePressed(true);
		else 
			audioOption.mousePressed(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(isIn(e, menuB)) {
			if(menuB.isMousePressed())
				Gamestate.state = Gamestate.MENU;
		}
		else 
			audioOption.mouseReleased(e);
		
		menuB.resetBools();
	}

	@Override
	public void mouseMoved(MouseEvent e)  {
		menuB.setMouseOver(false);
		
		if(isIn(e, menuB))
			menuB.setMouseOver(true);
		else
			audioOption.mouseMoved(e);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
			Gamestate.state = Gamestate.MENU;
	} 

	@Override
	public void keyReleased(KeyEvent e) {
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}
	
	private boolean isIn(MouseEvent e, PauseButton b) {
		return b.getBounds().contains(e.getX(), e.getY());
	} 
	
}
