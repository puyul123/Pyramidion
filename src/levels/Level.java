package levels;

import java.util.ArrayList;


import Entity.Mummy;
import Entity.Rat;
import Entity.Spider;
import Main.Game;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;

import Object.Container;
import Object.Door;
import Object.Gem;
import Object.Lever;
import Object.Potion;
import Object.Trap;
import Util.HelpMethods;
import static Util.HelpMethods.GetLevelData;
import static Util.HelpMethods.GetSpider;
import static Util.HelpMethods.GetMummy;
import static Util.HelpMethods.GetRat;
import levels.LevelManager;

public class Level{
	
	private String txt;
	private int[][] lvlData;
	private ArrayList<Mummy> mummy;
	private ArrayList<Rat> rat;
	private ArrayList<Spider> spider;
	
	private ArrayList<Trap> traps;
	ArrayList<Potion> potions;
	ArrayList<Container> containers;
	ArrayList<Gem> gems;
	ArrayList<Door> doors;
	private ArrayList<Lever> levers;
	
	private int lvlTilesWide;
	private int maxTilesOffset; 
	private int maxLvlOffsetX;
	
	public Level(String txt) {
		this.txt = txt;
		
		createLevelData();
		createEnemies();
		calcLvlOffsets();
		
		
		createTraps();
		createPotions();
		createGems();
		createContainers();
		createDoor();
		createLever();
	}
	
	private void calcLvlOffsets() {
		lvlTilesWide = 60;
		maxTilesOffset = lvlTilesWide - Game.TILES_WIDTH;
		maxLvlOffsetX = Game.TILES_SIZE * maxTilesOffset;
	}

	private void createEnemies() {
		mummy = GetMummy(txt);
		spider = GetSpider(txt);
		rat = GetRat(txt);
		//HARUS DI DEBUGGGG
	}

	private void createLevelData() {
		lvlData = GetLevelData(txt);
	}
	
	private void createDoor() {
		doors = HelpMethods.GetDoors(txt);
	}
	
	private void createLever() {
		levers = HelpMethods.GetLever(txt);
	}
	
	private void createTraps() {
		traps = HelpMethods.GetTraps(txt);	
	}//DEBUG
	
	private void createPotions() {
		potions = HelpMethods.GetPotions(txt);
	}
	
	private void createGems() {
		gems = HelpMethods.GetGems(txt);
	}
	
	private void createContainers() {
		containers = HelpMethods.GetContainers(txt);
	}
	
	public ArrayList<Trap> getTraps() {
		return traps;
	}
	
	public ArrayList<Potion> getPotions() {
		return potions;
	}
	
	public ArrayList<Gem> getGems() {
		return gems;
	}
	
	public ArrayList<Container> getContainers() {
		return containers;
	}
	
	public ArrayList<Door> getDoor(){
		return doors;
	}
	
	public ArrayList<Lever> getLever(){
		return levers;
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
	public ArrayList<Spider> getSpider(){
		return spider;
	}
	public ArrayList<Rat> getRat(){
		return rat;
	}
}
