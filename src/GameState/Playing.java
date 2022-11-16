package GameState;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import Entity.Player;
import Main.Game;
import levels.LevelManager;

public class Playing extends State implements StateMethods{
	
	private Player player;
	private LevelManager lvlManager;

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

	}

	@Override
	public void draw(Graphics g) {
		lvlManager.draw(g);
		player.render(g);

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println("mouse clicked");
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
	
	public Player getPlayer() {return player;}

}
