package Entity;

import static Util.Constants.EnemyConstants.*;

import java.awt.Graphics;

import Main.Game;

public class Mummy extends Enemy{

	public Mummy(float x, float y) {
		super(x, y, (int)(MUMMY_SIZE * Game.SCALE), (int)(MUMMY_SIZE * Game.SCALE), MUMMY);
		initCollision(x, y, 30.7f * Game.SCALE, 42 * Game.SCALE);
		
	}
	
	public void draw(Graphics g, int xLvlOffset) {
		drawCollision(g, xLvlOffset);
	}

}
