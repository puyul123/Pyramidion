package Entity;

import static Util.Constants.Direction.RIGHT;
import static Util.Constants.EnemyConstants.ATTACK;
import static Util.Constants.EnemyConstants.DEAD;
import static Util.Constants.EnemyConstants.HIT;
import static Util.Constants.EnemyConstants.IDLE;
import static Util.Constants.EnemyConstants.SPIDER;
import static Util.Constants.EnemyConstants.SPIDER_SIZE;
import static Util.Constants.EnemyConstants.RUNNING;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import Main.Game;

public class Spider extends Enemy{
	
	private Rectangle2D.Float attackBox;
	private int attackBoxOffsetX;
	
	public Spider(float x, float y) {
		super(x, y, (int)(SPIDER_SIZE * Game.SCALE), (int)(SPIDER_SIZE * Game.SCALE), SPIDER);
		initCollision(x, y, 36 * Game.SCALE, 42 * Game.SCALE);
		initAttackBox();
		setSpeed(1.5f);
	}
	
	private void initAttackBox() {
		attackBox = new Rectangle2D.Float(x, y, (int) (62 * Game.SCALE), (int)(42 * Game.SCALE));
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
			return this.width + 64;
		else return 0;
	}
	public int flipW() {
		if(walkDir == RIGHT) 
			return -1 ;
		else return 1;
	}
}
