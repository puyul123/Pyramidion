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
import ui.GameOverOverlay;
import ui.LevelCompletedOverlay;
import ui.PauseOverlay;
import Object.ObjectManager;
import Object.Trap;

public class Playing extends State implements StateMethods{
	
	private PauseOverlay pauseOverlay;
	private LevelCompletedOverlay lvlCompletedOverlay;
	private GameOverOverlay gameoverOverlay;
	//private ObjectManager objectManager;
	private Player player;
	private LevelManager lvlManager;
	private EnemyManager enemyMan;
	private ObjectManager objMan;
	private Trap trap;
	
	//CAMERA
	public int xLvlOffset = 0;
	private int border = (int) (Game.WIDTH/2);
	private int maxLvlOffsetX;
	
	private boolean paused = false;
	private boolean gameOver = false;
	private boolean isLvlCompleted = false;
	
	public Playing(Game game) {
		super(game);
		init();
		
		calcLvlOffset();
		loadStartLevel();
	}
	
	public void loadNextLevel() {
		resetAll();
		lvlManager.loadNextLevel();
	}
	
	private void loadStartLevel() {
		enemyMan.loadEnemies(lvlManager.getCurrentLevel());
		objMan.loadObjects(lvlManager.getCurrentLevel());
	}

	private void calcLvlOffset() {
		maxLvlOffsetX = lvlManager.getCurrentLevel().getLvlOffset();
	}

	private void init() {
		pauseOverlay = new PauseOverlay(this);
		lvlCompletedOverlay = new LevelCompletedOverlay(this);
		gameoverOverlay = new GameOverOverlay(this);
		lvlManager = new LevelManager(game);
		enemyMan = new EnemyManager(this);
		objMan = new ObjectManager(this);
		player = new Player(150, 200, (int)(64*Game.SCALE), (int)(64*Game.SCALE), this);
		player.loadLvlData(lvlManager.getCurrentLevel().getLevelData());
	}

	@Override
	public void update() {
		
		if(paused) {
			pauseOverlay.update();
		}
		else if(isLvlCompleted) {
			lvlCompletedOverlay.update();
		}
		else if(!gameOver){
			lvlManager.Update();
			player.Update();
			objMan.update();
			enemyMan.update(lvlManager.getCurrentLevel().getLevelData(), player);
			checkCloseToBorder();
		}
	}

	@Override
	public void draw(Graphics g) {
		lvlManager.draw(g, xLvlOffset);
		enemyMan.draw(g, xLvlOffset);
		objMan.draw(g, xLvlOffset);
		player.render(g, xLvlOffset);
		
		if(paused)
			pauseOverlay.draw(g);
		else if(gameOver) {
			gameoverOverlay.draw(g);
		}
		else if(isLvlCompleted) {
			lvlCompletedOverlay.draw(g);
		}
		
	//	lvlCompletedOverlay.draw(g);
	//	mummy.draw(g);
		
	}
	
	public void resetAll() {
		gameOver = false;
		paused = false;
		isLvlCompleted = false;
		player.resetAll();
		enemyMan.resetAllEnemies();
		objMan.resetAllObjects();
	}
	
	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}
	
	public void checkEnemyHit(Rectangle2D.Float attackCol) {
//		System.out.println("Yes Hit");
		enemyMan.checkEnemyHit(attackCol);
	}
	
	public void checkObjectHit(Rectangle2D.Float attackCol) {
		System.out.println("mukul kotak");
		objMan.checkObjectHit(attackCol);
	}
	
	public void checkDoorTouched(Player player) {       
		objMan.doorTouched(player);
		
	}
	
	public void checkPotionTouched(Rectangle2D.Float area) {
		objMan.checkObjectTouched(area);
	}
	
	public void checkGemTouched(Rectangle2D.Float area) {
		objMan.checkObjectTouched(area);
	}
	
	public void checkTrapTouched(Player player) {
		objMan.checkTrapTouched(player);
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
		if(!gameOver) 
			if(e.getButton() == MouseEvent.BUTTON1)
				player.setAttack(true);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(gameOver)
			gameoverOverlay.keyPressed(e);
		else {
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
				case KeyEvent.VK_E ->{				
					if(objMan.getCommand())
						objMan.setInteract(false);	
//					if(!objMan.getInteract())
//						setLevelCompleted(true);
				}
				case KeyEvent.VK_ESCAPE ->{
					paused = !paused;
				}
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(!gameOver) {
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
	}
	
	public void mouseDragged(MouseEvent e) {
		if(!gameOver)
			if (paused)
				pauseOverlay.mouseDragged(e);
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		if(!gameOver) {
			if (paused)
				pauseOverlay.mousePressed(e);
			else if(isLvlCompleted)
				lvlCompletedOverlay.mousePressed(e);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(!gameOver) {
			if (paused)
				pauseOverlay.mouseReleased(e);
			else if(isLvlCompleted)
				lvlCompletedOverlay.mouseReleased(e);
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if(!gameOver) {
			if (paused)
				pauseOverlay.mouseMoved(e);
			else if(isLvlCompleted)
				lvlCompletedOverlay.mouseMoved(e);
		}
	}
	
	public void setLevelCompleted(boolean levelCompleted) {
		this.isLvlCompleted = levelCompleted;
	}
	
	public void setMaxLvlOffset(int lvlOffset) {
		this.maxLvlOffsetX = lvlOffset;
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
		objMan.checkTrapTouched(player);
	}
	
	public ObjectManager getObjectManager() {
		return objMan;
	}
	
	public EnemyManager getEnemyManager() {
		return enemyMan;
	}
	
	public LevelManager getLevelManager() {
		return lvlManager;
	}
	
}
