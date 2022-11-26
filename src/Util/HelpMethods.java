package Util;

import java.awt.geom.Rectangle2D;
import java.awt.Color;
import java.util.ArrayList;
import java.awt.image.BufferedImage;

import Main.Game;
import Object.Trap;

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

		int value = lvlData[(int) yIndex][(int) xIndex];

		if (value >= 48 || value < 0)
			return true;
		if(value != 0 && value != 1)
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
	
	public static ArrayList<Trap> setTraps(BufferedImage image){
		ArrayList<Trap> list = new ArrayList<>();
//		
//		for (int j = 0; j < image.getHeight(); j++)
//			for (int i = 0; i < image.getWidth(); i++) {
//				Color color = new Color(image.getRGB(i, j));
//				int value = color.getBlue();
//				if (value == TRAP)
//					list.add(new Trap(i * Game.TILES_SIZE, j * Game.TILES_SIZE, TRAP));
//			}
//		
		return list;
	}
} 
