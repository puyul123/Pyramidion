package Object;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import Entity.Mummy;
import Entity.Player;
import GameState.Playing;

import java.awt.Graphics;
import java.awt.Event.*;

import Util.HelpMethods;
import Util.LoadSave;
import static Util.Constants.ObjectConstants.*;
import levels.Level;
import Entity.Player;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class ObjectManager {

	private Playing playing;
	private BufferedImage trapImage;
	private ArrayList<Trap> traps;
	private BufferedImage[] containerImage, potionImage;
	private BufferedImage[][] gemImage;
	private ArrayList<Potion> potions;
	private ArrayList<Gem> gems;
	private ArrayList<Container> containers;
	
	public ObjectManager(Playing playing) {
		this.playing = playing;
		loadTrapImgs();
		addTraps();
		loadObjectImgs();
//		addObjects();
	}
	
	public void checkObjectTouched(Rectangle2D.Float area) {
		for(Potion p : potions)
			if(p.isActive()) {
				if(area.intersects(p.getArea())) {
					p.setActive(false);
					applyPotionToPlayer(p);
				}
			}
		for(Gem g : gems)
			if(g.isActive()) {
				if(area.intersects(g.getArea())) {
					g.setActive(false);
					addPoint(g);
				}
			}
	}
	
	public void applyPotionToPlayer(Potion p) {
		if(p.getObjType() == POTION)
			playing.getPlayer().changeHealth(POTION_VALUE);
	}
	
	public void addPoint(Gem g) {
		if(g.getObjType() == GREEN_GEM)
			playing.getPlayer().changePoint(GREEN_GEM_VALUE);
		else if(g.getObjType() == BLUE_GEM)
			playing.getPlayer().changePoint(BLUE_GEM_VALUE);
		else if(g.getObjType() == RED_GEM)
			playing.getPlayer().changePoint(RED_GEM_VALUE);
	}
	
	public void checkObjectHit(Rectangle2D.Float area) {
		for(Container c : containers)
			if(c.isActive()) {
				if(c.getArea().intersects(area)) {
					c.setAnimation(true);
					//potions = HelpMethods.po.add(new Potion((int)(c.getArea().x + c.getArea().width/2), (int)(c.getArea().y), CONTAINER));
					potions.add(new Potion((int)(c.getArea().x + c.getArea().width/2), (int)(c.getArea().y), POTION));
					return;
				}
			}
	}
	
	public void loadObjects(Level newLevel) {
		potions = newLevel.getPotions();
		containers = newLevel.getContainers();
		gems = newLevel.getGems();
	}   
	
	public void trapTouched(Player player) {
		for(Trap t : traps) {
			if(t.getArea().intersects(player.getCollision())) {
				//panggil fungsi yg ngurangin health/bunuh player
				player.die();
			}
		}
	}
	
//	private void addObjects() {
//		potions = HelpMethods.GetPotions();
//		System.out.println("size of potion = " + potions.size());		
//		
//		gems = HelpMethods.GetGems();
//		//System.out.println("size of gems = " + gems.size());
//		
//		containers = HelpMethods.GetContainers();
//		System.out.println("size of container = " + containers.size());
//	}
	
	private void loadObjectImgs() {
		potionImage = new BufferedImage[7];
		gemImage = new BufferedImage[3][4];
		containerImage = new BufferedImage[8];
		
		try {
		  	potionImage[0] = ImageIO.read(getClass().getResourceAsStream("/potion/01.png"));
		  	potionImage[1] = ImageIO.read(getClass().getResourceAsStream("/potion/02.png"));
		  	potionImage[2] = ImageIO.read(getClass().getResourceAsStream("/potion/03.png"));
		  	potionImage[3] = ImageIO.read(getClass().getResourceAsStream("/potion/04.png"));
		  	potionImage[4] = ImageIO.read(getClass().getResourceAsStream("/potion/05.png"));
		  	potionImage[5] = ImageIO.read(getClass().getResourceAsStream("/potion/06.png"));
		  	potionImage[6] = ImageIO.read(getClass().getResourceAsStream("/potion/07.png"));
		
		  	gemImage[0][0] = ImageIO.read(getClass().getResourceAsStream("/treasure/common/01.png"));
		  	gemImage[0][1] = ImageIO.read(getClass().getResourceAsStream("/treasure/common/02.png"));
		  	gemImage[0][2] = ImageIO.read(getClass().getResourceAsStream("/treasure/common/03.png"));
		  	gemImage[0][3] = ImageIO.read(getClass().getResourceAsStream("/treasure/common/04.png"));
		  	
		  	gemImage[1][0] = ImageIO.read(getClass().getResourceAsStream("/treasure/uncommon/01.png"));
		  	gemImage[1][1] = ImageIO.read(getClass().getResourceAsStream("/treasure/uncommon/02.png"));
		  	gemImage[1][2] = ImageIO.read(getClass().getResourceAsStream("/treasure/uncommon/03.png"));
		  	gemImage[1][3] = ImageIO.read(getClass().getResourceAsStream("/treasure/uncommon/04.png"));
		  	
		  	gemImage[2][0] = ImageIO.read(getClass().getResourceAsStream("/treasure/rare/01.png"));
		  	gemImage[2][1] = ImageIO.read(getClass().getResourceAsStream("/treasure/rare/02.png"));
		  	gemImage[2][2] = ImageIO.read(getClass().getResourceAsStream("/treasure/rare/03.png"));
		  	gemImage[2][3] = ImageIO.read(getClass().getResourceAsStream("/treasure/rare/04.png"));
		  	
			BufferedImage potionSprite = LoadSave.GetSpriteAtlas(LoadSave.POTION_IMAGE);
			potionImage = new BufferedImage[7];
			for(int i = 0; i < potionImage.length; i++) {
				potionImage[i] = potionSprite.getSubimage(12*i, 0, 12, 16);
			}

			BufferedImage containerSprite = LoadSave.GetSpriteAtlas(LoadSave.CONTAINER_IMAGE);
			containerImage = new BufferedImage[8];
			for(int i = 0; i < containerImage.length; i++) {
				containerImage[i] = containerSprite.getSubimage(40*i, 0, 40, 30);
			}
			
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		
	}
	
	private void addTraps() {
		traps = HelpMethods.GetTraps();
		System.out.println("size of trap = " + traps.size());
	}
	
	private void loadTrapImgs() {
		trapImage = LoadSave.GetSpriteAtlas(LoadSave.TRAP_IMAGE);
	}
	
	public void draw(Graphics graphic, int xLvlOffset) {
		drawTrap(graphic, xLvlOffset);
		drawPotions(graphic, xLvlOffset);
		drawGems(graphic, xLvlOffset);
		drawContainers(graphic, xLvlOffset);
	}
	
	private void drawContainers(Graphics graphic, int xLvlOffset) {
		for(Container c : containers)
			if(c.isActive()) {
				if(c.getObjType() == CONTAINER) {
					graphic.drawImage(containerImage[c.getAniIndex()], 
							(int)(c.getArea().x - c.getxDrawOffset() - xLvlOffset), 
							(int)(c.getArea().y - c.getxDrawOffset()), 
							CONTAINER_WIDTH, 
							CONTAINER_HEIGHT, 
							null);
				}
			}
		
	}

	private void drawGems(Graphics graphic, int xLvlOffset) {
		for(Gem g : gems) {
			if(g.isActive()) {
				int type;
				if(g.getObjType() == GREEN_GEM) 
					type = 0;
				else if(g.getObjType() == BLUE_GEM) 
					type = 1;
				else
					type = 2;
				
				graphic.drawImage(gemImage[type][g.getAniIndex()], 
						(int)(g.getArea().x - g.getxDrawOffset() - xLvlOffset), 
						(int)(g.getArea().y - g.getxDrawOffset()), 
						GEM_WIDTH, 
						GEM_HEIGHT, 
						null);
				
			}
		}
		
	}

	private void drawPotions(Graphics graphic, int xLvlOffset) {
		for(Potion p : potions) {
			if(p.isActive()) {
				if(p.getObjType() == POTION) {
					graphic.drawImage(potionImage[p.getAniIndex()], 
							(int)(p.getArea().x - p.getxDrawOffset() - xLvlOffset), 
							(int)(p.getArea().y - p.getxDrawOffset()), 
							POTION_WIDTH, 
							POTION_HEIGHT, 
							null);
				}
			}
		}
	}

	private void drawTrap(Graphics graphic, int xLvlOffset) {
		for(Trap t : traps) {
			graphic.drawImage(trapImage, (int)(t.getArea().x), (int)(t.getArea().y), TRAP_WIDTH, TRAP_HEIGHT, null);
			//graphic.drawImage(trapImage, (int)(t.getArea().x - xLvlOffset), (int)(t.getArea().y - t.getyDrawOffset()), TRAP_WIDTH, TRAP_HEIGHT, null);
		
		}
	}
	
	public void update() {
		for(Potion p : potions)
			if(p.isActive())
				p.updatePotion();
		for(Gem g : gems)
			if(g.isActive())
				g.updateGem();
		for(Container c : containers)
			if(c.isActive())
				c.updateObject();
	}

	public void resetAllObjects() {
		for (Potion p : potions)
			p.resetObject();
		
		for (Gem g : gems)
			g.resetObject();
		
		for (Container c : containers)
			c.resetObject();
		
	}

	
	
	
}
