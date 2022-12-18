package Util;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import Entity.Mummy;
import Main.Game;
import static Util.Constants.EnemyConstants.*;

public class LoadSave {
	
	
	public static BufferedReader[] mapReader;
	
	public static final String LEVEL_ATLAS = "test_outside_sprites.png";
//	public static final String LEVEL_ONE_DATA = "test_level_one_data.png";
//	public static final String LEVEL_ONE_DATA_LONG = "level_one_data_long.png";
	
	public static final String MENU_BUTTON = "mainMenuButton.png";
	public static final String MENU_BACKGROUND = "mainMenubackground.png";
	public static final String PAUSE_BACKGROUND = "option_UI.png";
	public static final String PAUSE_BACK = "options_background.png";
	public static final String SOUND_BUTTONS = "sound_button.png";
	public static final String URM_BUTTONS = "urm_buttons.png";
	public static final String VOLUME_BUTTONS = "volume_buttons.png";
	public static final String COMPLETED_IMG = "winPanel.png";
	public static final String GAMEOVER_IMG = "losePanel.png";
	public static final String PLAYING_BACKGROUND = "playingBackground.png";
	
	public static final String TEST_MAP = "test_outside_sprite_2.png";
	public static final String LEVEL_0 = "level0.txt";
	public static final String LEVEL_1 = "level1.txt";	
	public static final String LEVEL_2 = "level2.txt";	
	
	public static final String TRAP_IMAGE = "trap_2.png";
	public static final String STATUS_BAR = "health_power_bar.png";
	
	public static final String POTION_IMAGE = "potions_sprites.png";
	public static final String CONTAINER_IMAGE = "objects_sprites.png";
	
	
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
	
	public static String[] importAllMap() {
		
		String[] mapReader = new String[3];
		
		try {	
			
			mapReader[0] = LEVEL_0;
			mapReader[1] = LEVEL_1;
			mapReader[2] = LEVEL_2;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return mapReader;
	}	
}
