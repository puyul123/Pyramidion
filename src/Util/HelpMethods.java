package Util;

import java.awt.geom.Rectangle2D;


import java.awt.Color;
import java.util.ArrayList;

import Entity.Mummy;
import Entity.Rat;
import Entity.Spider;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;

import Main.Game;
import Object.Container;
import Object.Door;
import Object.Gem;
import Object.Lever;
import Object.Potion;
import Object.Trap;
import static Util.LoadSave.GetMapTxt;

import static Util.LoadSave.LEVEL_0;
import static Util.LoadSave.LEVEL_1;
import static Util.LoadSave.LEVEL_2;
import static Util.Constants.EnemyConstants.MUMMY;
import static Util.Constants.EnemyConstants.SPIDER;
import static Util.Constants.EnemyConstants.RAT;
import static Util.Constants.ObjectConstants.*;


public class HelpMethods {
	
	//public static ArrayList<Potion> po;
	
	public static boolean CanMoveHere(float x, float y, float width, float height, int[][] lvlData) {
		if (!IsSolid(x, y, lvlData))
			if (!IsSolid(x + width, y + height, lvlData))
				if (!IsSolid(x + width, y, lvlData))
					if (!IsSolid(x, y + height, lvlData))
						if (!IsSolid(x, y + height/2, lvlData))
							if (!IsSolid(x + width, y + height/2, lvlData))
								return true;
		return false;
	}

	private static boolean IsSolid(float x, float y, int[][] lvlData) {
		int maxWidth = lvlData[0].length * Game.TILES_SIZE;
		if (x < 0 || x >= maxWidth)
			return true;
		if (y < 0 || y >= Game.HEIGHT)
			return true;

		float xIndex = x / Game.TILES_SIZE;
		float yIndex = y / Game.TILES_SIZE;

		return IsTileSolid((int)xIndex, (int)yIndex, lvlData);
	}
	
	public static boolean IsTileSolid(int xTile, int yTile, int[][] lvlData) {
		int value = lvlData[yTile][xTile];

		if (value>=247||value<0||value!=0&&value!=MUMMY&&value!=SPIDER&&value!=RAT&&
				value!=45&&value!= 46&&value!=101&&value!=102&&value!=105&&value!=52&& 
				value!=108&&value!=111&&value!=49&&value!=30&&value!=32&&value!=51&&
				value!=29&&value!=33&&value!=35&&value!=55&&value!=103&&value!=106&&value!=109&&value!=112
				&&value!=DOOR&&value!=POTION&&value!=RED_GEM&&value!=GREEN_GEM&&value != LEVER
				&&value!=BLUE_GEM&&value!=CONTAINER)
			return true;
		return false;
	}
	
	public static float GetEntityXPosNextToWall(Rectangle2D.Float collision, float xSpeed) {
		int currentTile = (int) (collision.x / Game.TILES_SIZE);
		if (xSpeed > 0) {
			// Right
			int tileXPos = currentTile * Game.TILES_SIZE;
			int xOffset = (int) (Game.TILES_SIZE - collision.width);
			return tileXPos + xOffset - 1;
		} else
			// Left
			return currentTile * Game.TILES_SIZE;
	}

	public static float GetEntityYPosUnderRoofOrAboveFloor(Rectangle2D.Float collision, float airSpeed) {
		int currentTile = (int) (collision.y / Game.TILES_SIZE);
		if (airSpeed > 0) {
			// Falling - touching floor
			int tileYPos = currentTile * Game.TILES_SIZE;
			int yOffset = (int) (Game.TILES_SIZE - collision.height);

			return tileYPos + yOffset + 47;
		} else
			// Jumping
			return currentTile * Game.TILES_SIZE;
	}
	
	public static boolean IsEntityOnFloor(Rectangle2D.Float hitbox, int[][] lvlData) {
		// Check the pixel below bottomleft and bottomright
		if (!IsSolid(hitbox.x, hitbox.y + hitbox.height + 1, lvlData))
			if (!IsSolid(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1, lvlData))
				return false;

		return true;
	}
	
	//DEBUGGGG
	public static boolean IsFloor(Rectangle2D.Float collision, float xSpeed, int[][] lvlData) {
		if(xSpeed > 0)
			return IsSolid(collision.x + collision.width + xSpeed, collision.y + collision.height + 1, lvlData);
		return IsSolid(collision.x + xSpeed, collision.y + collision.height + 1, lvlData);
	}
	
	
	public static int[][] GetLevelData(String txt) {
		BufferedReader br = GetMapTxt(txt);
		int[][] lvlData = new int[17][60];
			
			try {
				int col = 0;
				int row = 0;
				while(col < 60 && row < Game.TILES_HEIGHT) {
					String line = br.readLine();
					
					while(col < 60) {
						String numbers[] = line.split(" ");
						int num = Integer.parseInt(numbers[col]);
						lvlData[row][col] = num;
						col++;
					}
					if(col == Game.TILES_HEIGHT);
						col = 0;
						row++;
				}
		//		br.close();
			}catch(IOException e) {
				e.printStackTrace();
			}finally {
				try {
					br.close();
				}catch(IOException e) {
					e.printStackTrace();
				}
			}
		return lvlData;
	}
	
	public static ArrayList<Mummy> GetMummy(String txt){
		
		BufferedReader br = GetMapTxt(txt);
		ArrayList<Mummy> mummy = new ArrayList<>();
		
		try {
			int col = 0;
			int row = 0;
			while(col < 60 && row < Game.TILES_HEIGHT) {
				String line = br.readLine();
				
				while(col < 60) {
					String numbers[] = line.split(" ");
					int num = Integer.parseInt(numbers[col]);
					if(num == MUMMY) 
						mummy.add(new Mummy((int)(col * Game.TILES_SIZE - 15), (int)(row * Game.TILES_SIZE - 30)));
					col++;
				}
				if(col == Game.TILES_HEIGHT);
					col = 0;
					row++;
			}
			br.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		return mummy;
	}
	
	public static ArrayList<Spider> GetSpider(String txt){
		
		BufferedReader br = GetMapTxt(txt);
		ArrayList<Spider> spider = new ArrayList<>();
		
		try {
			int col = 0;
			int row = 0;
			while(col < 60 && row < Game.TILES_HEIGHT) {
				String line = br.readLine();
				
				while(col < 60) {
					String numbers[] = line.split(" ");
					int num = Integer.parseInt(numbers[col]);
					if(num == SPIDER) 
						spider.add(new Spider((int)(col * Game.TILES_SIZE - 15), (int)(row * Game.TILES_SIZE - 30)));
					col++;
				}
				if(col == Game.TILES_HEIGHT);
					col = 0;
					row++;
			}
			br.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		return spider;
	}
	
	public static ArrayList<Rat> GetRat(String txt){
		
		BufferedReader br = GetMapTxt(txt);
		ArrayList<Rat> rat = new ArrayList<>();
		
		try {
			int col = 0;
			int row = 0;
			while(col < 60 && row < Game.TILES_HEIGHT) {
				String line = br.readLine();
				
				while(col < 60) {
					String numbers[] = line.split(" ");
					int num = Integer.parseInt(numbers[col]);
					if(num == RAT) 
						rat.add(new Rat((int)(col * Game.TILES_SIZE - 15), (int)(row * Game.TILES_SIZE - 30)));
					col++;
				}
				if(col == Game.TILES_HEIGHT);
					col = 0;
					row++;
			}
			br.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		return rat;
	}
	
	
	public static ArrayList<Trap> GetTraps(String txt){
		BufferedReader br = GetMapTxt(txt);
		ArrayList<Trap> tr = new ArrayList<>();
		
		try {
			
			int col = 0;
			int row = 0;
			while(col < 60 && row < Game.TILES_HEIGHT) {
				String line = br.readLine();
				
				while(col < 60) {
					String numbers[] = line.split(" ");
					int num = Integer.parseInt(numbers[col]);
					if(num == TRAP) 
						tr.add(new Trap((int)(col * Game.TILES_SIZE), (int)(row * Game.TILES_SIZE), TRAP));
					col++;
				}
				if(col == Game.TILES_HEIGHT);
					col = 0;
					row++;
			}
			br.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		return tr;
	}
	
	public static ArrayList<Potion> GetPotions(String txt){
		BufferedReader br = GetMapTxt(txt);
		ArrayList<Potion> po = new ArrayList<>();
		
		try {
			
			int col = 0;
			int row = 0;
			while(col < 60 && row < Game.TILES_HEIGHT) {
				String line = br.readLine();
				
				while(col < 60) {
					String numbers[] = line.split(" ");
					int num = Integer.parseInt(numbers[col]);
					if(num == POTION) 
						po.add(new Potion((int)(col * Game.TILES_SIZE - 15), (int)(row * Game.TILES_SIZE - 30), POTION));
					col++;
				}
				if(col == Game.TILES_HEIGHT);
					col = 0;
					row++;
			}
			br.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		return po;
	}

	public static ArrayList<Gem> GetGems(String txt) {
		BufferedReader br = GetMapTxt(txt);
		ArrayList<Gem> gem = new ArrayList<>();
		
		try {
			
			int col = 0;
			int row = 0;
			while(col < 60 && row < Game.TILES_HEIGHT) {
				String line = br.readLine();
				
				while(col < 60) {
					String numbers[] = line.split(" ");
					int num = Integer.parseInt(numbers[col]);
					if(num == RED_GEM) 
						gem.add(new Gem((int)(col * Game.TILES_SIZE + 18), (int)(row * Game.TILES_SIZE), RED_GEM));
					else if(num == BLUE_GEM)
						gem.add(new Gem((int)(col * Game.TILES_SIZE + 18), (int)(row * Game.TILES_SIZE), BLUE_GEM));
					else if(num == GREEN_GEM)
						gem.add(new Gem((int)(col * Game.TILES_SIZE + 18), (int)(row * Game.TILES_SIZE), GREEN_GEM));
					
					col++;
				}
				if(col == Game.TILES_HEIGHT);
					col = 0;
					row++;
			}
			br.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		return gem;
	}

	public static ArrayList<Container> GetContainers(String txt) {
		BufferedReader br = GetMapTxt(txt);
		ArrayList<Container> con = new ArrayList<>();
		
		try {
			
			int col = 0;
			int row = 0;
			while(col < 60 && row < Game.TILES_HEIGHT) {
				String line = br.readLine();
				
				while(col < 60) {
					String numbers[] = line.split(" ");
					int num = Integer.parseInt(numbers[col]);
					if(num == CONTAINER) 
						con.add(new Container((int)(col * Game.TILES_SIZE + 1), (int)(row * Game.TILES_SIZE - 30), CONTAINER));
					col++;
				}
				if(col == Game.TILES_HEIGHT);
					col = 0;
					row++;
			}
			br.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		return con;
	}

	public static ArrayList<Door> GetDoors(String txt) {
		BufferedReader br = GetMapTxt(txt);
		ArrayList<Door> door = new ArrayList<>();
		
		try {
			
			int col = 0;
			int row = 0;
			while(col < 60 && row < Game.TILES_HEIGHT) {
				String line = br.readLine();
				
				while(col < 60) {
					String numbers[] = line.split(" ");
					int num = Integer.parseInt(numbers[col]);
					if(num == DOOR) 
						door.add(new Door((int)(col * Game.TILES_SIZE), (int)(row * Game.TILES_SIZE), DOOR));
					col++;
				}
				if(col == Game.TILES_HEIGHT);
					col = 0;
					row++;
			}
			br.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		return door;
	}
	
	public static ArrayList<Lever> GetLever(String txt) {
		BufferedReader br = GetMapTxt(txt);
		ArrayList<Lever> lever = new ArrayList<>();
		
		try {
			
			int col = 0;
			int row = 0;
			while(col < 60 && row < Game.TILES_HEIGHT) {
				String line = br.readLine();
				
				while(col < 60) {
					String numbers[] = line.split(" ");
					int num = Integer.parseInt(numbers[col]);
					if(num == LEVER) 
						lever.add(new Lever((int)(col * Game.TILES_SIZE), (int)(row * Game.TILES_SIZE)+9, DOOR));
					col++;
				}
				if(col == Game.TILES_HEIGHT);
					col = 0;
					row++;
			}
			br.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		return lever;
	}
	
} 
