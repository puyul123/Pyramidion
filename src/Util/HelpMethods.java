package Util;

import java.awt.geom.Rectangle2D;

import java.awt.Color;
import java.util.ArrayList;

import Entity.Mummy;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;

import Main.Game;
import Object.Trap;
import static Util.LoadSave.GetMapTxt;

import static Util.LoadSave.LEVEL_0;
import static Util.LoadSave.LEVEL_1;
import static Util.LoadSave.LEVEL_2;
import static Util.Constants.EnemyConstants.MUMMY;
import static Util.Constants.ObjectConstants.*;


public class HelpMethods {
	
	public static boolean CanMoveHere(float x, float y, float width, float height, int[][] lvlData) {
		if (!IsSolid(x, y, lvlData))
			if (!IsSolid(x + width, y + height, lvlData))
				if (!IsSolid(x + width, y, lvlData))
					if (!IsSolid(x, y + height, lvlData))
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

		if (value >= 247 || value < 0 || value != 0 && value != 1)
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
		return IsSolid(collision.x + xSpeed, collision.y + collision.height + 1, lvlData);
	}
	
	public static boolean IsAllTilesWalkable(int xStart, int xEnd, int y, int[][] lvlData) {
		for (int i = 0; i < xEnd - xStart; i++) {
			if (IsTileSolid(xStart + i, y, lvlData))
				return false;
			if (!IsTileSolid(xStart + i, y + 5, lvlData))
				return false;
		}

		return true;
	}

	public static boolean IsSightClear(int[][] lvlData, Rectangle2D.Float firstCollision, Rectangle2D.Float secondCollision, int yTile) {
		int firstXTile = (int) (firstCollision.x / Game.TILES_SIZE);
		int secondXTile = (int) (secondCollision.x / Game.TILES_SIZE);

		if (firstXTile > secondXTile)
			return IsAllTilesWalkable(secondXTile, firstXTile, yTile, lvlData);
		else
			return IsAllTilesWalkable(firstXTile, secondXTile, yTile, lvlData);

	}
	
	
	public static int[][] GetLevelData(BufferedReader br) {
		int[][] lvlData = new int[14][60];
			
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
				br.close();
			}catch(IOException e) {
				e.printStackTrace();
			}
		return lvlData;
	}
	
	public static ArrayList<Mummy> GetMummy(BufferedReader br, int lvlIndex){
		
		if(lvlIndex == 0) br = GetMapTxt(LEVEL_0);
		else if(lvlIndex == 1) br = GetMapTxt(LEVEL_1);
		else if(lvlIndex == 2) br = GetMapTxt(LEVEL_2);
		
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
		//	br.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		return mummy;
	}
	
	
	public static ArrayList<Trap> setTraps(){
		BufferedReader br = GetMapTxt(LEVEL_0);
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
						tr.add(new Trap((int)(col * Game.TILES_SIZE - 15), (int)(row * Game.TILES_SIZE - 30), TRAP));
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
	
} 
