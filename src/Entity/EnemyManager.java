package Entity;

import java.awt.Graphics;


import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import GameState.Playing;
import Main.Game;
import Util.LoadSave;

import static Util.Constants.EnemyConstants.*;

public class EnemyManager {
	
	private Playing playing;
	private BufferedImage[][] mummy;
	private ArrayList<Mummy> mummies = new ArrayList<>();
	
	public EnemyManager(Playing playing) {
		this.playing = playing;
		loadEnemyImgs();
		addEnemies();
	}

	private void addEnemies() {
		mummies = LoadSave.GetMummy();
		System.out.println("size of mum = " + mummies.size());
	}

	private void loadEnemyImgs() {
		mummy = new BufferedImage[5][5];
		
		try {
			//IDLE
			mummy[0][0] =  ImageIO.read(getClass().getResourceAsStream("/mob/mummy/mummy_1.png"));
			//RUNNING
			mummy[1][0] =  ImageIO.read(getClass().getResourceAsStream("/mob/mummy/mummy_3.png"));
			mummy[1][1] =  ImageIO.read(getClass().getResourceAsStream("/mob/mummy/mummy_1.png"));
			mummy[1][2] =  ImageIO.read(getClass().getResourceAsStream("/mob/mummy/mummy_2.png"));
			mummy[1][3] =  ImageIO.read(getClass().getResourceAsStream("/mob/mummy/mummy_1.png"));
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
	public void update(int[][] lvlData) {
		for(Mummy m : mummies) m.update(lvlData);
	}
	
	public void draw(Graphics g, int xLvlOffset) {
		drawMummy(g, xLvlOffset);
		
	}

	private void drawMummy(Graphics g, int xLvlOffset) {
		for(Mummy m : mummies) {
			g.drawImage(mummy[m.getEnemyState()][m.getAniIndex()],(int) (m.getCollision().x-25) - xLvlOffset, 
					(int) (m.getCollision().y-13),(int) (64 * Game.SCALE),(int) (64 *Game.SCALE), null);
			m.draw(g, xLvlOffset);
		}
	}
	
}
