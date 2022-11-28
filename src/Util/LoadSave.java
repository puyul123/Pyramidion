package Util;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import Entity.Mummy;
import Main.Game;
import static Util.Constants.EnemyConstants.*;

public class LoadSave {
	
	
	public static final String LEVEL_ATLAS = "test_outside_sprites.png";
	public static final String LEVEL_ONE_DATA = "test_level_one_data.png";
	public static final String LEVEL_ONE_DATA_LONG = "level_one_data_long.png";
	public static final String MENU_BUTTON = "mainMenuButton.png";
	public static final String MENU_BACKGROUND = "mainMenuBackground.png";
	public static final String TEST_MAP = "test_outside_sprite_2.png";
	public static final String LEVEL_0 = "level1.txt";	
	public static final String TRAP_IMAGE = "trap_2.png";	
	
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

	public static BufferedReader GetMapTxt(String txtName) {
		BufferedReader br = null;
		InputStream is = LoadSave.class.getResourceAsStream("/maps/" + txtName);
		br = new BufferedReader(new InputStreamReader(is));
		return br;
	}
	
	public static ArrayList<Mummy> GetMummy(){
		BufferedReader br = GetMapTxt(LEVEL_0);
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
	
	public static int[][] GetLevelData() {
		int[][] lvlData = new int[14][60];
		BufferedReader br = GetMapTxt(LEVEL_0);
			
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
}
