package GameState;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

import Entity.EnemyManager;
import Entity.Mummy;
import Entity.Player;
import Main.Game;
import Util.LoadSave;
import levels.LevelManager;
import ui.PauseOverlay;
import Object.ObjectManager;
import Object.Trap;

public class Playing extends State implements StateMethods{
	
	private PauseOverlay pauseOverlay;
	private ObjectManager objectManager;
	private Player player;
	private LevelManager lvlManager;
	private EnemyManager enemyMan;
	private ObjectManager objMan;
	private Trap trap;
	
	//CAMERA
	public int xLvlOffset = 0;
	private int border = (int) (Game.WIDTH/2);
	private int lvlTilesWide = LoadSave.GetLevelData()[0].length;
	private int maxTilesOffset = lvlTilesWide - Game.TILES_WIDTH;
	private int maxLvlOffsetX = maxTilesOffset * Game.TILES_SIZE;
	
	private boolean paused = false;
	
	public Playing(Game game) {
		super(game);
		init();
	}

	private void init() {
		pauseOverlay = new PauseOverlay(this);
		lvlManager = new LevelManager(game);
		enemyMan = new EnemyManager(this);
		objMan = new ObjectManager(this);
		player = new Player(150, 200, (int)(64*Game.SCALE), (int)(64*Game.SCALE), this);
		player.loadLvlData(lvlManager.getCurrentLevel().getLevelData());
	}

	@Override
	public void update() {
		if(!paused) {
			lvlManager.Update();
			player.Update();
			enemyMan.update(lvlManager.getCurrentLevel().getLevelData(), player);
			checkCloseToBorder();
		}
		else {
			pauseOverlay.update();
		}
	}

	@Override
	public void draw(Graphics g) {
		lvlManager.draw(g, xLvlOffset);
		enemyMan.draw(g, xLvlOffset);
		player.render(g, xLvlOffset);
		objMan.draw(g, xLvlOffset);
		
		if(paused)
			pauseOverlay.draw(g);
	//	mummy.draw(g);
		
	}
	
	public void resetAll() {
		//todo
	}
	
	public void checkEnemyHit(Rectangle2D.Float attackCol) {
//		System.out.println("Yes Hit");
		enemyMan.checkEnemyHit(attackCol);
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
	//	System.out.println("mouse clicked");
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
				if(player.isOnSword() == true) player.setOnSword(false);
				else player.setOnSword(true);
			}
			case KeyEvent.VK_ESCAPE ->{
				paused = !paused;
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
		}
	}
	
	public void mouseDragged(MouseEvent e) {
		if (paused)
			pauseOverlay.mouseDragged(e);
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		if (paused)
			pauseOverlay.mousePressed(e);

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (paused)
			pauseOverlay.mouseReleased(e);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (paused)
			pauseOverlay.mouseMoved(e);

	}

	public void windowFocusLost() {
		player.resetDirBool();
		
	}
	
	public void unpauseGame() {
		paused = false;
	}
	
	public Player getPlayer() {
		return player;
	}

	public void trapTouched(Player player) {
		objectManager.trapTouched(player);
	}
	
	public ObjectManager getObjectManager() {
		return objectManager;
	}

}
