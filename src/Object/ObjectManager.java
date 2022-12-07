package Object;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import Entity.Mummy;
import Entity.Player;
import GameState.Playing;

import java.awt.Graphics;

import Util.HelpMethods;
import Util.LoadSave;
import static Util.Constants.ObjectConstants.*;
import levels.Level;
import Entity.Player;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class ObjectManager {

	private Playing playing;
	private BufferedImage trapImage, potionImage;
	private ArrayList<Trap> traps;
	private BufferedImage[] containerImage;
	private BufferedImage[][] gemImage;
	private ArrayList<Potion> potions;
	private ArrayList<Gem> gems;
	private ArrayList<Container> containers;
	
	public ObjectManager(Playing playing) {
		this.playing = playing;
		loadTrapImgs();
		addTraps();
		loadObjectImgs();
		addObjects();
		
		potions = new ArrayList<>();
		potions.add(new Potion(300, 100, POTION));
		potions.add(new Potion(400, 100, POTION));
		
		containers = new ArrayList<>();
		containers.add(new Container(600, 100, CONTAINER));
	}
	
	public void trapTouched(Player player) {
		for(Trap t : traps) {
			if(t.getArea().intersects(player.getCollision())) {
				//panggil fungsi yg ngurangin health/bunuh player
				player.die();
			}
		}
	}
	
//	public void loadObjects(Level newLvl) {
//		traps = newLvl.getTraps();
//	}
	
	private void addObjects() {
		
	}
	
	private void loadObjectImgs() {
		try {
//			BufferedImage potionSprite = LoadSave.GetSpriteAtlas(LoadSave.POTION_IMAGE);
//			potionImage = new BufferedImage;
//			
//			//potion image
//			//potionImage[0][0] = ImageIO.read(getClass().getResourceAsStream("/char_1.png"));
//			
//			potionImage = LoadSave.GetSpriteAtlas(LoadSave.POTION_IMAGE).getSubimage(12, 32, 12, 16); 
			//BufferedImage potionSprite = LoadSave.GetSpriteAtlas(LoadSave.POTION_IMAGE);
			//potionImage = potionSprite.getSubimage(12, 32, 12, 16);
		  	potionImage = ImageIO.read(getClass().getResourceAsStream("/potions_sprite.png"));
//			
			BufferedImage containerSprite = LoadSave.GetSpriteAtlas(LoadSave.CONTAINER_IMAGE);
			containerImage = new BufferedImage[8];
			for(int i = 0; i < containerImage.length; i++) {
				containerImage[i] = containerSprite.getSubimage(40*i, 0, 40, 30);
			}
//			
//			//BufferedImage gemSprite = LoadSave.GetSpriteAtlas(LoadSave.GEM_IMAGE);
//			//gemImage = new BufferedImage[3][5];
//			//masukin asset game nya satusatu
//			
			}
			catch (IOException e) {
				e.printStackTrace();
			}
//		
	}
	
	private void addTraps() {
		traps = HelpMethods.setTraps();
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
		// TODO Auto-generated method stub
		
	}

	private void drawPotions(Graphics graphic, int xLvlOffset) {
		for(Potion p : potions)
			if(p.isActive()) {
				if(p.getObjType() == POTION) {
					graphic.drawImage(potionImage, 
							(int)(p.getArea().x - p.getxDrawOffset() - xLvlOffset), 
							(int)(p.getArea().y - p.getxDrawOffset()), 
							POTION_WIDTH, 
							POTION_HEIGHT, 
							null);
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
//		for(Gem g : gems)
//			if(g.isActive())
//				g.updateGem();
		for(Container c : containers)
			if(c.isActive())
				c.updateObject();
	}
	
	
}
