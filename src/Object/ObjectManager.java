package Object;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

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

public class ObjectManager {

	private Playing playing;
	private BufferedImage trapImage;
	private ArrayList<Trap> traps;
	
	public ObjectManager(Playing playing) {
		this.playing = playing;
		loadTrapImgs();
		addTraps();
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
	
	private void addTraps() {
		traps = HelpMethods.setTraps();
		System.out.println("size of trap = " + traps.size());
	}
	
	private void loadTrapImgs() {
		trapImage = LoadSave.GetSpriteAtlas(LoadSave.TRAP_IMAGE);
	}
	
	public void draw(Graphics graphic, int xLvlOffset) {
		drawTrap(graphic, xLvlOffset);
	}
	
	private void drawTrap(Graphics graphic, int xLvlOffset) {
		for(Trap t : traps) {
			graphic.drawImage(trapImage, (int)(t.getArea().x), (int)(t.getArea().y), TRAP_WIDTH, TRAP_HEIGHT, null);
			//graphic.drawImage(trapImage, (int)(t.getArea().x - xLvlOffset), (int)(t.getArea().y - t.getyDrawOffset()), TRAP_WIDTH, TRAP_HEIGHT, null);
		
		}
	}
	
	
}
