package levels;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.util.ArrayList;

import GameState.Gamestate;
import Main.Game;
import Util.LoadSave;

public class LevelManager {
	
	private Game game;
	private BufferedImage[] levelSprite;
	private ArrayList<Level> levels;
	private static int lvlIndex = 0;
	
	public LevelManager(Game game) {
		this.game = game;
		importOutsideSprites();
		levels = new ArrayList<>();
		buildAllLevels();
	}
	
	public void loadNextLevel() {
		lvlIndex++;
		if(lvlIndex >= levels.size()) {
			lvlIndex = 0;
			System.out.println("No more lvlssss");
			Gamestate.state = Gamestate.MENU;
		}
		Level newLevel = levels.get(lvlIndex);
		game.getPlaying().getEnemyManager().loadEnemies(newLevel);
		game.getPlaying().getPlayer().loadLvlData(newLevel.getLevelData());
		game.getPlaying().setMaxLvlOffset(newLevel.getLvlOffset());
		game.getPlaying().getObjectManager().loadObjects(newLevel);
	}
	
	private void buildAllLevels() {
		String[] allLevels = LoadSave.importAllMap();
 		for(String txt : allLevels) {
 			levels.add(new Level(txt));
 		}
	}

	private void importOutsideSprites() {
		BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.TEST_MAP);
		levelSprite = new BufferedImage[247];
		for (int j = 0; j < 13; j++)
			for (int i = 0; i < 19; i++) {
				int index = j * 19 + i;
				//drawing sprite to map
				levelSprite[index] = img.getSubimage(i * 32, j * 32, 32, 32);
			}
	}
	
	public void draw(Graphics g, int lvlOffset) {
		
		for (int j = 0; j < Game.TILES_HEIGHT; j++)
			for (int i = 0; i < levels.get(lvlIndex).getLevelData()[0].length; i++) {
				int index = levels.get(lvlIndex).getSpriteIndex(i, j);
				g.drawImage(levelSprite[index], Game.TILES_SIZE * i - lvlOffset, Game.TILES_SIZE * j, Game.TILES_SIZE, Game.TILES_SIZE, null);
			}
		
		//CHECK PER-TILES
//		for(int j = 0; j < levelZero.getLevelData()[0].length; j++) {
//			for(int i = 0; i < Game.TILES_HEIGHT; i++) {
//				g.setColor(Color.MAGENTA);
//				g.drawRect((int) (j*32*Game.SCALE)-lvlOffset,(int) (i*32*Game.SCALE), Game.TILES_SIZE, Game.TILES_SIZE);
//			}
//		}
	}
	
	public void Update() {
		
	}
	
	public Level getCurrentLevel() {
		return levels.get(lvlIndex);
	}
	
	public int getAmountOfLevels() {
		return levels.size();
	}
	public int getLevelIndex() {
		return lvlIndex;
	}
	public void setLevelIndex(int lvlIndex) {
		this.lvlIndex = lvlIndex;
	}
	
}
