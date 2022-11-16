package levels;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import Main.Game;
import Util.LoadSave;

public class LevelManager {
	
	private Game game;
	private BufferedImage[] levelSprite;
	private Level levelZero;
	
	public LevelManager(Game game) {
		this.game = game;
		importOutsideSprites();
		levelZero = new Level(LoadSave.GetLevelData());
	}
	
	private void importOutsideSprites() {
		BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.LEVEL_ATLAS);
		levelSprite = new BufferedImage[48];
		for (int j = 0; j < 4; j++)
			for (int i = 0; i < 12; i++) {
				int index = j * 12 + i;
				//drawing sprite to map
				levelSprite[index] = img.getSubimage(i * 32, j * 32, 32, 32);
			}
	}
	
	public void draw(Graphics g) {
		for (int j = 0; j < Game.TILES_HEIGHT; j++)
			for (int i = 0; i < levelZero.getLevelData()[0].length; i++) {
				int index = levelZero.getSpriteIndex(i, j);
				
				g.drawImage(levelSprite[index], Game.TILES_SIZE * i, Game.TILES_SIZE * j, Game.TILES_SIZE, Game.TILES_SIZE, null);
			}
	}
	
	public void Update() {
		
	}
	
	public Level getCurrentLevel() {
		return levelZero;
	}
	
}
