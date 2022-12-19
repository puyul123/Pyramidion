package Entity;

import java.awt.Graphics;


import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import GameState.Playing;
import Main.Game;
import Object.Gem;
import Util.LoadSave;
import levels.Level;


import static Util.Constants.EnemyConstants.*;
import static Util.Constants.ObjectConstants.*;

public class EnemyManager {
	
	private Playing playing;
	private BufferedImage[][] mummy;
	private BufferedImage[][] spider;
	private BufferedImage[][] rat;
	private ArrayList<Mummy> mummies = new ArrayList<>();
	private ArrayList<Spider> spiders = new ArrayList<>();
	private ArrayList<Rat> rats = new ArrayList<>();
	
	public EnemyManager(Playing playing) {
		this.playing = playing;
		loadEnemyImgs();
	}

	public void loadEnemies(Level level) {
		mummies = level.getMummy();
		spiders = level.getSpider();
		rats = level.getRat();
	}

	private void loadEnemyImgs() {
		mummy = new BufferedImage[5][6];
		spider = new BufferedImage[5][6];
		rat = new BufferedImage[5][6];
		
		try {
		///////////////////////////// MUMMY ////////////////////////////////	
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
			
		///////////////////////////// SPIDER ////////////////////////////////
			
			//IDLE
			spider[0][0] =  ImageIO.read(getClass().getResourceAsStream("/mob/spider/spider_move_1.png"));
			
			//RUNNING
			spider[1][0] =  ImageIO.read(getClass().getResourceAsStream("/mob/spider/spider_move_2.png"));
			spider[1][1] =  ImageIO.read(getClass().getResourceAsStream("/mob/spider/spider_move_1.png"));
			
			//ATTACK
			spider[2][0] =  ImageIO.read(getClass().getResourceAsStream("/mob/spider/spider_at1.png"));
			spider[2][1] =  ImageIO.read(getClass().getResourceAsStream("/mob/spider/spider_at2.png"));
			spider[2][2] =  ImageIO.read(getClass().getResourceAsStream("/mob/spider/spider_at3.png"));
			spider[2][3] =  ImageIO.read(getClass().getResourceAsStream("/mob/spider/spider_at4.png"));
			spider[2][4] =  ImageIO.read(getClass().getResourceAsStream("/mob/spider/spider_at5.png"));

			
			//HIT
			spider[3][0] =  ImageIO.read(getClass().getResourceAsStream("/mob/spider/spider_dead.png"));
			
			//DEAD
			for(int i = 0; i < 6; i++) {
				if(i % 2 == 0) spider[4][i] = ImageIO.read(getClass().getResourceAsStream("/mob/spider/spider_dead.png"));
				else spider[4][i] = ImageIO.read(getClass().getResourceAsStream("/mob/spider/spider_move_2.png"));
			}
			
		///////////////////////////// RAT ////////////////////////////////
			
			//IDLE
			rat[0][0] =  ImageIO.read(getClass().getResourceAsStream("/mob/rat/rat_move_2.png"));
			
			//RUNNING
			rat[1][0] =  ImageIO.read(getClass().getResourceAsStream("/mob/rat/rat_move_1.png"));
			rat[1][1] =  ImageIO.read(getClass().getResourceAsStream("/mob/rat/rat_move_2.png"));
			
			//ATTACK
			rat[2][0] =  ImageIO.read(getClass().getResourceAsStream("/mob/rat/rat_attack_1.png"));
			rat[2][1] =  ImageIO.read(getClass().getResourceAsStream("/mob/rat/rat_attack_2.png"));
			rat[2][2] =  ImageIO.read(getClass().getResourceAsStream("/mob/rat/rat_attack_3.png"));
			rat[2][3] =  ImageIO.read(getClass().getResourceAsStream("/mob/rat/rat_attack_4.png"));

			
			//HIT
			rat[3][0] =  ImageIO.read(getClass().getResourceAsStream("/mob/rat/rat_dead.png"));
			
			//DEAD
			for(int i = 0; i < 6; i++) {
				if(i % 2 == 0) rat[4][i] = ImageIO.read(getClass().getResourceAsStream("/mob/rat/rat_dead.png"));
				else rat[4][i] = ImageIO.read(getClass().getResourceAsStream("/mob/rat/rat_move_2.png"));
			}
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
	public void update(int[][] lvlData, Player player) {
		
		for(Mummy m : mummies) {
			if(m.isActive()) {
				m.update(lvlData, player);
			}

		}
		for(Spider s : spiders) {
			if(s.isActive()) {
				s.update(lvlData, player);
			}
		}
		for(Rat r : rats) {
			if(r.isActive()) {
				r.update(lvlData, player);
			}
		}

	}
	
	public void draw(Graphics g, int xLvlOffset) {
		drawMummy(g, xLvlOffset);
		drawRat(g, xLvlOffset);
		drawSpider(g, xLvlOffset);
	}

	private void drawSpider(Graphics g, int xLvlOffset) {
		for(Spider s : spiders) {
			if(s.isActive()) {
				g.drawImage(spider[s.getEnemyState()][s.getAniIndex()],
						(int) (s.getCollision().x - 55) - xLvlOffset + s.flipX(), 
						(int) (s.getCollision().y-15),
						(int) (100 * Game.SCALE) / s.flipW(),(int) (64 *Game.SCALE), null);
				s.draw(g, xLvlOffset);
			}
		}
	}

	private void drawRat(Graphics g, int xLvlOffset) {
		for(Rat r : rats) {
			if(r.isActive()) {
				g.drawImage(rat[r.getEnemyState()][r.getAniIndex()],
						(int) (r.getCollision().x - 45) - xLvlOffset + r.flipX(), 
						(int) (r.getCollision().y - 15),
						(int) (100 * Game.SCALE) / r.flipW(),(int) (64 *Game.SCALE), null);
				r.draw(g, xLvlOffset);
			}
		}
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
					if(m.getEnemyState()== DEAD)
						playing.getObjectManager().getGemObject().add(new Gem((int)(m.collision.x + m.collision.width/2 + 10), (int)(m.collision.y), RED_GEM));
					m.hurt(10);
					return;
				}
		for (Rat r : rats)
			if (r.isActive())
				if (attackBox.intersects(r.getCollision())) {
					if(r.getEnemyState()== DEAD)
						playing.getObjectManager().getGemObject().add(new Gem((int)(r.collision.x + r.collision.width/2 + 10), (int)(r.collision.y), BLUE_GEM));
					r.hurt(10);
					return;
				}
		for (Spider s : spiders)
			if (s.isActive())
				if (attackBox.intersects(s.getCollision())) {
					if(s.getEnemyState()== DEAD)
						playing.getObjectManager().getGemObject().add(new Gem((int)(s.collision.x + s.collision.width/2 + 10), (int)(s.collision.y), GREEN_GEM));
					s.hurt(10);
					return;
				}
	}

	public void resetAllEnemies() {
		for(Mummy m: mummies)
			m.resetEnemy();
		for(Rat r : rats)
			r.resetEnemy();
		for(Spider s : spiders)
			s.resetEnemy();
	}
	
}
