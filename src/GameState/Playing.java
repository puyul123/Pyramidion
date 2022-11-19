package GameState;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import Entity.Player;
import Main.Game;
import Util.LoadSave;
import levels.LevelManager;

public class Playing extends State implements StateMethods{
	
	private Player player;
	private LevelManager lvlManager;
	
	//CAMERA
	private int xLvlOffset = 0;
	private int border = (int) (Game.WIDTH/2);
	private int lvlTilesWide = LoadSave.GetLevelData()[0].length;
	private int maxTilesOffset = lvlTilesWide - Game.TILES_WIDTH;
	private int maxLvlOffsetX = maxTilesOffset * Game.TILES_SIZE;
	
	public Playing(Game game) {
		super(game);
		init();
	}

	private void init() {
		lvlManager = new LevelManager(game);
		player = new Player(100, 200, (int)(64*Game.SCALE), (int)(64*Game.SCALE));
		player.loadLvlData(lvlManager.getCurrentLevel().getLevelData());
	}

	@Override
	public void update() {
		lvlManager.Update();
		player.Update();
		checkCloseToBorder();
	}

	@Override
	public void draw(Graphics g) {
		lvlManager.draw(g, xLvlOffset);
		player.render(g, xLvlOffset);

	}
	
	private void checkCloseToBorder() {
		int playerX = (int) player.getCollision().x;
		int diff = playerX - xLvlOffset;
		
		//MIDDLE FOLLOW CAMERA
		if (diff > border || diff < border)
			xLvlOffset += diff - border;

		if (xLvlOffset > maxLvlOffsetX)
			xLvlOffset = maxLvlOffsetX;
		
		else if (xLvlOffset < 0)
			xLvlOffset = 0;

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println("mouse clicked");
		if(e.getButton() == MouseEvent.BUTTON1)
			player.setAttack(true);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();

		switch(key) {
			case KeyEvent.VK_W -> {
				player.setJump(true);
			}
			case KeyEvent.VK_A ->{
				player.setLeft(true);
			}
			case KeyEvent.VK_D ->{
				player.setRight(true);
			}
			case KeyEvent.VK_ENTER ->{
				player.setAttack(true);
			}
			case KeyEvent.VK_ESCAPE ->{
				Gamestate.state = Gamestate.MENU;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();

		switch(key) {
			case KeyEvent.VK_W -> {
				player.setJump(false);
			}
			case KeyEvent.VK_A ->{
				player.setLeft(false);
			}
			case KeyEvent.VK_D ->{
				player.setRight(false);
			}
			case KeyEvent.VK_ENTER ->{
				player.setAttack(false);
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		System.out.println("mouse pressed");

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void windowFocusLost() {
		player.resetDirBool();
		
	}
	
	public Player getPlayer() {
		return player;
	}

}