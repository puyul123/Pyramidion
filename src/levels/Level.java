package levels;

import java.util.ArrayList;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;

import Object.Trap;
import Util.HelpMethods;

public class Level {
	
	private BufferedReader txt;
	private int[][] lvlData;
	private ArrayList<Trap> traps;
	
	public Level(int[][] lvlData) {
		this.lvlData = lvlData;
		createTraps();
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
	
}
