package Object;

import java.awt.geom.Rectangle2D;

import java.util.ArrayList;

import javax.imageio.ImageIO;

import Entity.Player;
import GameState.Playing;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;

import Util.LoadSave;
import static Util.Constants.ObjectConstants.*;
import levels.Level;


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
	private BufferedImage[] doorImage;
	private ArrayList<Door> doors;
	private BufferedImage[] leverImage;
	private ArrayList<Lever> levers;
	
	boolean isDoorClosed = false;
	public Font font;
	
	public ObjectManager(Playing playing) {
		this.playing = playing;
		importFont();
		loadTrapImgs();
		loadObjectImgs();

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
	
	public void checkTrapTouched(Player player) {
		for(Trap t : traps)
			if(t.getArea().intersects(player.getCollision()))
				player.hurt();
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
					potions.add(new Potion((int)(c.getArea().x + c.getArea().width/2), (int)(c.getArea().y), POTION));
					gems.add(new Gem((int)(c.getArea().x + c.getArea().width/2 + 10), (int)(c.getArea().y), GREEN_GEM));
					return;
				}
			}
	}
	
	public void doorTouched (Player player) {
		for(Door d : doors) {
			if(d.isActive()) {
				if(!d.isDoorClosed())
					d.setAnimation(true);	
				if(d.getArea().intersects(player.getCollision())) 
					d.command = true;
				else {
					d.command = false;
					playing.setPressed(false);
				}
			}
		}
	}
	
	public void leverTouched (Player player) {
		for(Lever l : levers) {
			if(l.isActive()) {
				if(l.getArea().intersects(player.getCollision())) {
					l.command = true;
					if(!l.isInteract())
						l.setAnimation(true);	
				}
				else l.command = false;
			}
		}
	}
	
	public void loadObjects(Level newLevel) {
		potions = new ArrayList<>(newLevel.getPotions());
		containers = new ArrayList<>(newLevel.getContainers());
		gems = new ArrayList<>(newLevel.getGems());
		traps = newLevel.getTraps();
		doors = newLevel.getDoor();
		levers = newLevel.getLever();
	}   
	
	private void loadObjectImgs() {
		potionImage = new BufferedImage[7];
		gemImage = new BufferedImage[3][4];
		containerImage = new BufferedImage[8];
		doorImage = new BufferedImage[5];
		leverImage = new BufferedImage[2];
		
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
		  	
		  	doorImage[0] = ImageIO.read(getClass().getResourceAsStream("/objects/door_1.png"));
		  	doorImage[1] = ImageIO.read(getClass().getResourceAsStream("/objects/door_2.png"));
		  	doorImage[2] = ImageIO.read(getClass().getResourceAsStream("/objects/door_3.png"));
		  	doorImage[3] = ImageIO.read(getClass().getResourceAsStream("/objects/door_4.png"));
		  	doorImage[4] = ImageIO.read(getClass().getResourceAsStream("/objects/door_5.png"));
		  	
		  	leverImage[0] = ImageIO.read(getClass().getResourceAsStream("/handle/handle_1.png"));
		  	leverImage[1] = ImageIO.read(getClass().getResourceAsStream("/handle/handle_2.png"));
		  	
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
	
	private void loadTrapImgs() {
		trapImage = LoadSave.GetSpriteAtlas(LoadSave.TRAP_IMAGE);
	}
	
	public void draw(Graphics graphic, int xLvlOffset) {
		drawTrap(graphic, xLvlOffset);
		drawPotions(graphic, xLvlOffset);
		drawGems(graphic, xLvlOffset);
		drawContainers(graphic, xLvlOffset);
		drawDoor(graphic, xLvlOffset);
		drawLever(graphic, xLvlOffset);
	}
	
	private void drawLever(Graphics graphic, int xLvlOffset) {
		for(Lever l : levers) {
			if(l.isActive()) {
				graphic.drawImage(leverImage[l.getAniIndex()], 
						(int)(l.getArea().x - l.getxDrawOffset() - xLvlOffset), 
						(int)(l.getArea().y - l.getxDrawOffset()), 
						LEVER_WIDTH, LEVER_HEIGHT, null);
			}
			if(l.command) {
				Graphics2D g2 = (Graphics2D)graphic;
				g2.setFont(font); 
				g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 17));
				g2.setColor(Color.white);
				g2.drawString("Press E to pull the lever",  465, 550); 
			}
		}
	}

	private void drawDoor(Graphics graphic, int xLvlOffset) {
		for(Door d : doors) {
			if(!d.interact)
				graphic.drawImage(doorImage[4], (int)(d.getArea().x - xLvlOffset-25), (int)(d.getArea().y - d.getyDrawOffset()), (int)(DOOR_WIDTH * 2), (int)(DOOR_HEIGHT * 2), null);
			graphic.drawImage(doorImage[d.getAniIndex()], (int)(d.getArea().x - xLvlOffset-25), (int)(d.getArea().y - d.getyDrawOffset()), (int)(DOOR_WIDTH * 2), (int)(DOOR_HEIGHT * 2), null);
			if(d.command) {
				Graphics2D g2 = (Graphics2D)graphic;
				g2.setFont(font); 
				g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 17));
				g2.setColor(Color.WHITE);
				g2.drawString("Press E to interact",  500, 550);
				if(playing.pressed() && isDoorClosed) {
					g2.setColor(Color.RED);
					g2.drawString("Find a way to open the door",  430, 200);
				}
			}
		}
		
	}

	private void drawContainers(Graphics graphic, int xLvlOffset) {
		for(Container c : containers) {
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
						(int)(g.getArea().x - 4 - xLvlOffset), 
						(int)(g.getArea().y - g.getyDrawOffset()), 
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
			graphic.drawImage(trapImage, (int)(t.getArea().x - xLvlOffset), (int)(t.getArea().y - t.getyDrawOffset()), TRAP_WIDTH, TRAP_HEIGHT, null);
		}
	}
	
	public void update() {
		isDoorClosed = false;
		
		for(Potion p : potions)
			if(p.isActive())
				p.updatePotion();
		for(Gem g : gems)
			if(g.isActive())
				g.updateGem();
		for(Container c : containers)
			if(c.isActive())
				c.updateObject();
		for(Door d : doors)
			if(d.isActive()) {
				d.updateDoor();
				
			}
		for(Lever l : levers)
			if(l.isActive()) {
				if(!l.isInteract()) {
					l.updateLever();
				}
				if(l.isInteract()) {
					isDoorClosed = true;
				}
			}
				
		if(!isDoorClosed) {
			setDoorClosed(false);
		}
	}
	
	private void importFont() {
		try {this.font = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/font/egypt1.ttf"));} 
		catch (FontFormatException e) {e.printStackTrace();} 
		catch (IOException e) {e.printStackTrace();}
	}

	public void resetAllObjects() {
		
		loadObjects(playing.getLevelManager().getCurrentLevel());
		
		for (Potion p : potions)
			p.resetObject();
		
		for (Gem g : gems)
			g.resetObject();
		
		for (Container c : containers)
			c.resetObject();
		
		for (Door d : doors)
			d.resetObject();
		
		for (Lever d : levers)
			d.resetObject();
	}
	
	public boolean isDoorClosed() {
		return isDoorClosed;
	}
	
	public void setInteract(boolean setInteract, Player player) {  //debug disini
		for (Lever l : levers)
			if(l.getArea().intersects(player.getCollision()))
				l.setInteract(setInteract);
	}
	public void setDoorClosed(boolean setDoorClosed) {
		for (Door d : doors)
			d.setDoorClosed(setDoorClosed);
	}
	
	public ArrayList<Door> getDoorObject(){
		return doors;
	}
	public ArrayList<Lever> getLeverObject(){
		return levers;
	}
	public ArrayList<Potion> getPotionObject(){
		return potions;
	}
	public ArrayList<Gem> getGemObject(){
		return gems;
	}
}
