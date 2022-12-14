package Entity;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import GameState.Playing;
import Main.Game;
import Util.LoadSave;
import levels.Level;

import static Util.Constants.EnemyConstants.*;

public class EnemyManager {
	
	private Playing playing;
	private BufferedImage[][] mummy;
	private ArrayList<Mummy> mummies = new ArrayList<>();
	
	public EnemyManager(Playing playing) {
		this.playing = playing;
		loadEnemyImgs();
	}

	public void loadEnemies(Level level) {
		mummies = level.getMummy();
		System.out.println("size of mum = " + mummies.size());
	}

	private void loadEnemyImgs() {
		mummy = new BufferedImage[5][6];
		
		try {
			//IDLE
			mummy[0][0] =  ImageIO.read(getClass().getResourceAsStream("/mob/mummy/mummy_1.png"));
			//RUNNING
			mummy[1][0] =  ImageIO.read(getClass().getResourceAsStream("/mob/mummy/mummy_3.png"));
			mummy[1][1] =  ImageIO.read(getClass().getResourceAsStream("/mob/mummy/mummy_1.png"));
			mummy[1][2] =  ImageIO.read(getClass().getResourceAsStream("/mob/mummy/mummy_2.png"));
			mummy[1][3] =  ImageIO.read(getClass().getResourceAsStream("/mob/mummy/mummy_1.png"));
			
			//ATTACK
			mummy[2][0] =  ImageIO.read(getClass().getResourceAsStream("/mummy_attack_1.png"));
			mummy[2][1] =  ImageIO.read(getClass().getResourceAsStream("/mummy_attack_2.png"));
			mummy[2][2] =  ImageIO.read(getClass().getResourceAsStream("/mummy_attack_3.png"));
			
			//HIT
			mummy[3][0] = ImageIO.read(getClass().getResourceAsStream("/mob/mummy/mummy_dead.png"));
			
			//DEADD
			for(int i = 0; i < 6; i++) {
				if(i % 2 == 0) mummy[4][i] = ImageIO.read(getClass().getResourceAsStream("/mob/mummy/mummy_dead.png"));
				else mummy[4][i] = ImageIO.read(getClass().getResourceAsStream("/mob/mummy/mummy_1.png"));
			}
//			mummy[4][0] = ImageIO.read(getClass().getResourceAsStream("/mob/mummy/mummy_dead.png"));
//			mummy[4][1] = ImageIO.read(getClass().getResourceAsStream("/mob/mummy/mummy_1.png"));
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
	public void update(int[][] lvlData, Player player) {
		boolean isAnyActive = false;
		
		for(Mummy m : mummies) 
			if(m.isActive()) {
				m.update(lvlData, player);
				isAnyActive = true;
			}
		if(!isAnyActive)
			playing.setLevelCompleted(true);
	}
	
	public void draw(Graphics g, int xLvlOffset) {
		drawMummy(g, xLvlOffset);
	}

	private void drawMummy(Graphics g, int xLvlOffset) {
		for(Mummy m : mummies) {
			if(m.isActive()) {
				g.drawImage(mummy[m.getEnemyState()][m.getAniIndex()],
						(int) (m.getCollision().x-25) - xLvlOffset + m.flipX(), 
						(int) (m.getCollision().y-13),
						(int) (64 * Game.SCALE) / m.flipW(),(int) (64 *Game.SCALE), null);
				m.draw(g, xLvlOffset);
			}
		}
	}
	
	public void checkEnemyHit(Rectangle2D.Float attackBox) {
		for (Mummy m : mummies)
			if (m.isActive())
				if (attackBox.intersects(m.getCollision())) {
					m.hurt(10);
					return;
				}
	}

	public void resetAllEnemies() {
		for(Mummy m: mummies)
			m.resetEnemy();
	}
	
}
