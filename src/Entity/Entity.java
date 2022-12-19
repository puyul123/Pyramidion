package Entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

import Main.Game;

public abstract class Entity {
	protected float x, y;
	protected int width, height;
	protected Rectangle2D.Float collision;
	public float gravity = 0.05f * Game.SCALE;
	
	public Entity(float x, float y, int height, int width) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	protected void drawCollision(Graphics g, int xLvlOffset) {
		g.setColor(Color.BLUE);
		g.drawRect((int)collision.x - xLvlOffset, (int)collision.y, (int)collision.width, (int)collision.height);
	}

	protected void initCollision(float x, float y, float width, float height) {
		collision = new Rectangle2D.Float(x, y, width, height);
		
	}
//	public void updateCollision() {
//		collision.x = (int)x;
//		collision.y = (int)y;
//	}
	public Rectangle2D.Float getCollision() {
		return collision;
	}
	
}
