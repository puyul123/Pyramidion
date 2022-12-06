package levels;

import java.util.ArrayList;

import Entity.Mummy;
import Main.Game;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;

import Object.Trap;
import Util.HelpMethods;
import static Util.HelpMethods.GetLevelData;
import static Util.HelpMethods.GetMummy;
import levels.LevelManager;

public class Level {
	
	private LevelManager lvlManager;
	private BufferedReader txt;
	private int[][] lvlData;
	private ArrayList<Mummy> mummy;
	private ArrayList<Trap> traps;
	private int lvlTilesWide;
	private int maxTilesOffset; 
	private int maxLvlOffsetX;
	
	public Level(BufferedReader txt) {
		this.txt = txt;
		
		createLevelData();
		createEnemies();
		calcLvlOffsets();
		
		
		createTraps();
	}
	
	private void calcLvlOffsets() {
		lvlTilesWide = 60;
		maxTilesOffset = lvlTilesWide - Game.TILES_WIDTH;
		maxLvlOffsetX = Game.TILES_SIZE * maxTilesOffset;
	}

	private void createEnemies() {
		mummy = GetMummy(txt, 0);
		//HARUS DI DEBUGGGG
	}

	private void createLevelData() {
		lvlData = GetLevelData(txt);
	}

	private void createTraps() {
		traps = HelpMethods.setTraps();	
	}
	
	public ArrayList<Trap> getTraps() {
		return traps;
	}

	public int getSpriteIndex(int x, int y){
		return lvlData[y][x];
	}
	
	public int[][] getLevelData(){
		return lvlData;
	}
	
	public int getLvlOffset() {
		return maxLvlOffsetX;
	}
	
	public ArrayList<Mummy> getMummy(){
		return mummy;
	}
}
