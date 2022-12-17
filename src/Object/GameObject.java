package Object;

import static Util.Constants.EnemyConstants.ATTACK;
import static Util.Constants.EnemyConstants.DEAD;
import static Util.Constants.EnemyConstants.GetSpriteAmount;
import static Util.Constants.EnemyConstants.HIT;
import static Util.Constants.EnemyConstants.IDLE;

import static Util.Constants.ObjectConstants.*;

import static Util.Constants.aniSpeed;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import Main.Game;

public class GameObject {
	
	protected int x, y, objType;
	protected Rectangle2D.Float area;
	protected boolean doAnimation, active = true;
	protected boolean interact = true; 
	protected boolean doorClosed = true;
	protected boolean command;
	protected int aniTick, aniIndex;
	protected int xDrawOffset, yDrawOffset;

	public GameObject(int x, int y, int objType) {
		this.x = x;
		this.y = y;
		this.objType = objType;
	}
	
	protected void updateAnimationTick() {
		aniTick++;
		if(aniTick >= aniSpeed) {
			aniTick = 0;
			aniIndex++;
			if(aniIndex >= GetSpriteAmount(objType)){
				aniIndex = 0;
				if(objType == CONTAINER) {
					doAnimation = false;
					active = false;
				}
			}	
		}
	}
	
	public void resetObject() {
		aniIndex = 0;
		aniTick = 0;
		active = true;
		doorClosed = true;
		interact = true;

		if(objType == CONTAINER) {
			doAnimation = false;
		}
		else if(objType == DOOR) {
//			interact = false;
			doAnimation = false;
		}
		else {
			doAnimation = true;
		}
		
		//doAnimation = true;
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
	public boolean isInteract() {return interact;}
	public boolean isDoorClosed() {return doorClosed;}
	public void setActive(boolean active) {this.active = active;}
	public void setInteract(boolean interact) {this.interact = interact;}
	public void setDoorClosed(boolean doorClosed) {this.doorClosed = doorClosed;}
	public void setAnimation(boolean doAnimation) {this.doAnimation = doAnimation;}
	public int getxDrawOffset() {return xDrawOffset;}
	public int getyDrawOffset() {return yDrawOffset;}
	public int getAniIndex() {return aniIndex;}
	
}
