package Util;

import java.awt.Color;
import java.awt.image.BufferedImage;

import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import Main.Game;

public class LoadSave {
	
	
	public static final String LEVEL_ATLAS = "test_outside_sprites.png";
	public static final String LEVEL_ONE_DATA = "test_level_one_data.png";
	public static final String LEVEL_ONE_DATA_LONG = "level_one_data_long.png";

	public static BufferedImage GetSpriteAtlas(String fileName) {
		BufferedImage img = null;
		InputStream is = LoadSave.class.getResourceAsStream("/" + fileName);
		try {
			img = ImageIO.read(is);

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return img;
	}

	public static int[][] GetLevelData() {
//		int[][] lvlData = new int[Game.HEIGHT][Game.WIDTH];
		BufferedImage img = GetSpriteAtlas(LEVEL_ONE_DATA_LONG);
		int[][] lvlData = new int[img.getHeight()][img.getWidth()];
		
		System.out.println("height = " + img.getHeight());
		System.out.println(" width = " + img.getWidth());
		
		for (int j = 0; j < img.getHeight(); j++)
			for (int i = 0; i < img.getWidth(); i++) {
				Color color = new Color(img.getRGB(i, j));
				int value = color.getRed();
				if (value >= 48)
					value = 0;
				lvlData[j][i] = value;
				
			}
		
		for (int j = 0; j < img.getHeight(); j++) {
			for (int i = 0; i < img.getWidth(); i++) {
				System.out.println(lvlData[j][i]);
			}
			System.out.print("\n");
		}
		
		return lvlData;
	}
}
