package Object;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import Main.Game;

public class GameObject {
	
	protected int x, y, objType;
	protected Rectangle2D.Float area;
	protected boolean doAnimation, active = true;
	protected int aniTick, aniIndex;
	protected int xDrawOffset, yDrawOffset;

	public GameObject(int x, int y, int objType) {
		this.x = x;
		this.y = y;
		this.objType = objType;
	}
	
	protected void initArea(int width, int height) {
		area = new Rectangle2D.Float(x, y, (int) (width * Game.SCALE), (int) (height * Game.SCALE));
	}
	
	public void drawArea(Graphics g, int xLvlOffset) {
		g.setColor(Color.GREEN);
		g.drawRect((int) area.x - xLvlOffset, (int) area.y, (int) area.width, (int) area.height);
	}
	
	public int getObjType() {return objType;}
	public Rectangle2D.Float getArea() {return area;}
	public boolean isActive() {return active;}
	public void setActive(boolean active) {this.active = active;}
	public void setAnimation(boolean doAnimation) {this.doAnimation = doAnimation;}
	public int getxDrawOffset() {return xDrawOffset;}
	public int getyDrawOffset() {return yDrawOffset;}
	public int getAniIndex() {return aniIndex;}
	
}
