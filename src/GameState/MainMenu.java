package GameState;

import static Util.Constants.UI.PlayingUI.ScoreStringX;
import static Util.Constants.UI.PlayingUI.ScoreStringY1;
import static Util.Constants.UI.PlayingUI.ScoreStringY2;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import Main.Game;
import ui.MenuButton;
import Util.LoadSave;

public class MainMenu extends State implements StateMethods{
	
	private MenuButton[] buttons = new MenuButton[3];
	private BufferedImage bgImage;
	private int menuX, menuY, menuW, menuH;
	private int highScore;
	Font font;
	
	public MainMenu(Game game) {
		super(game);
		importFont();
		loadMenuButton();
		loadMenuBackground();
	}
	
	private void loadMenuButton() {
		buttons[0] = new MenuButton(Game.WIDTH / 2, (int) (150 * Game.SCALE), 0, Gamestate.PLAYING);
		buttons[1] = new MenuButton(Game.WIDTH / 2, (int) (220 * Game.SCALE), 1, Gamestate.OPTION);
		buttons[2] = new MenuButton(Game.WIDTH / 2, (int) (290 * Game.SCALE), 2, Gamestate.QUIT);
	}
	
	private void loadMenuBackground() {
		bgImage = LoadSave.GetSpriteAtlas(LoadSave.MENU_BACKGROUND);
		menuW = (int) (bgImage.getWidth() * Game.SCALE / 2);
		menuH = (int) (bgImage.getHeight() * Game.SCALE / 2);
		menuX = Game.WIDTH / 2 - menuW / 2;
		menuY = (int) (Game.SCALE);
	}
	

	@Override
	public void update() {
		// TODO Auto-generated method stub
		for (MenuButton menuButton : buttons)
			menuButton.update();
		
	}

	@Override
	public void draw(Graphics g) {
		
		g.drawImage(bgImage, menuX, menuY, menuW, menuH, null);
		for (MenuButton mb : buttons)
			mb.draw(g);
		
		Graphics2D g2 = (Graphics2D)g;
		g2.setFont(font); 
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40));
		
		g2.setColor(Color.WHITE);
		g2.drawString("Score:", 15, 607);
		g2.drawString(""+highScore  , 15, 647);
		//g.setColor(Color.BLACK);
		//g.drawString("MAIN MENU", Game.WIDTH/2, Game.HEIGHT/2);
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		for (MenuButton mb : buttons) {
			if(inGame(e, mb)) {
				Gamestate.state = Gamestate.PLAYING;				
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		for (MenuButton mb : buttons) {
			if(inGame(e, mb)) {
				mb.setMousePressed(true);
				
			}
		}		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		for (MenuButton mb : buttons) {
			if(inGame(e, mb)) {
				if (mb.isMousePressed()) {
					mb.applyGamestate();
				}
				if(mb.getState() == Gamestate.PLAYING)
					game.getAudioPlayer().setLevelSong(game.getPlaying().getLevelManager().getLevelIndex());
				break;
			}
		}
		resetButtons();
	}
	
	private void resetButtons() {
		for (MenuButton mb : buttons)
			mb.resetBools();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		for (MenuButton mb : buttons)
			mb.setMouseOver(false);
		
		for(MenuButton mb : buttons)
			if(inGame(e, mb)) {
				mb.setMouseOver(true);
				break;
			}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			//Gamestate.state = Gamestate.PLAYING;
			
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	public int getHighScore() {
		return highScore;
	}

	public void setHighScore(int highScore) {
		this.highScore = highScore;
	}
	private void importFont() {
		try {this.font = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/font/egypt1.ttf"));} 
		catch (FontFormatException e) {e.printStackTrace();} 
		catch (IOException e) {e.printStackTrace();}
	}
	
}
