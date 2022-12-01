package Entity;

//import static Util.Constants.Direction.*;

import static Util.Constants.EnemyConstants.*;
import static Util.Constants.Direction.*;

import static Util.HelpMethods.CanMoveHere;
import static Util.HelpMethods.GetEntityYPosUnderRoofOrAboveFloor;
import static Util.HelpMethods.IsEntityOnFloor;
import static Util.HelpMethods.IsFloor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import Main.Game;

public class Mummy extends Enemy{
	
	private Rectangle2D.Float attackBox;
	private int attackBoxOffsetX;
	
	public Mummy(float x, float y) {
		super(x, y, (int)(MUMMY_SIZE * Game.SCALE), (int)(MUMMY_SIZE * Game.SCALE), MUMMY);
		initCollision(x, y, 30.7f * Game.SCALE, 42 * Game.SCALE);
		initAttackBox();
	}
	
	
	private void initAttackBox() {
		attackBox = new Rectangle2D.Float(x, y, (int) (55 * Game.SCALE), (int)(42 * Game.SCALE));
		attackBoxOffsetX = (int) (Game.SCALE * 30);
	}


	public void draw(Graphics g, int xLvlOffset) {
		drawCollision(g, xLvlOffset);
		g.setColor(Color.RED);
		g.drawRect((int)(attackBox.x - xLvlOffset), (int)attackBox.y, (int)attackBox.width, (int)attackBox.height);
	}
	
	public void update(int[][] lvlData, Player player) {
		updateBehavior(lvlData, player);
		updateAnimationTick();
		updateAttackBox();
	}
	
	private void updateAttackBox() {
		attackBox.x = collision.x - attackBoxOffsetX + 25;
		attackBox.y = collision.y;		
	}


	private void updateBehavior(int[][] lvlData, Player player) {
		if(firstUpdate) 
			firstUpdateCheck(lvlData);
		if(inAir) 
			updateInAir(lvlData);
		
		else {
			switch(enemyState) {
			case IDLE:
				newState(RUNNING);
				break;
			case RUNNING:
				if (canSeePlayer(lvlData, player)) {
					turnTowardsPlayer(player);
				}
				if (isPlayerCloseForAttack(player))
					newState(ATTACK);
				move(lvlData);
				break;
			case ATTACK:
				if(aniIndex == 0)
					attackChecked = false;
				//CHECK BOSQ
				if(aniIndex == 2 && !attackChecked)
					checkPlayerHit(attackBox, player);
				break;
			case HIT:
				break;
			case DEAD:
				
			}
		}
		
	}

	public int flipX() {
		if(walkDir == RIGHT) 
			return 0;
		else return this.width;
	}
	public int flipW() {
		if(walkDir == RIGHT) 
			return 1;
		else return -1;
	}
}
