package Entity;

import static Util.Constants.EnemyConstants.*;
import static Util.Constants.aniSpeed;

import static Util.HelpMethods.*;
import static Util.Constants.Direction.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import Main.Game;

public abstract class Enemy extends Entity{

	protected int aniIndex, aniTick;
	protected int enemyState, enemyType;
	protected boolean firstUpdate = true;
	protected boolean inAir;
	protected float fallSpeed;
	protected float mummySpeed = 0.3f * Game.SCALE;
	protected int walkDir = LEFT;
	protected int tileY;
	protected float attackDistance = Game.TILES_SIZE;
	
	protected int maxHealth;
	protected int currentHealth;
	protected boolean active = true;
	protected boolean attackChecked;
	
	public Enemy(float x, float y, int height, int width, int enemyType) {
		super(x, y, height, width);
		this.enemyType = enemyType;
		initCollision(x, y, width, height);
		maxHealth = GetMaxHealth(enemyType);
		currentHealth = maxHealth;
	}
	
	public void hurt(int amount) {
		currentHealth -= amount;
		if (currentHealth <= 0) {
			newState(DEAD);
		}
		else
			newState(HIT);
	}
	
	protected void checkPlayerHit(Rectangle2D.Float attackBox, Player player) {
		if(attackBox.intersects(player.collision))
			player.changeHealth(-GetEnemyDmg(enemyType));
		attackChecked = true;
	}
	
	protected void updateAnimationTick() {
		aniTick++;
		if(aniTick >= aniSpeed) {
			aniTick = 0;
			aniIndex++;
			if(aniIndex >= GetSpriteAmount(enemyType, enemyState)){
				aniIndex = 0;
				
				switch(enemyState) {
					case ATTACK, HIT -> 
						enemyState = IDLE;
					case DEAD -> 
						active = false;
					
				}
			}	
		}
	}
	
	protected void firstUpdateCheck(int[][]lvlData) {
		if(!IsEntityOnFloor(collision, lvlData)) {
			inAir = true;
		}
		firstUpdate = false;
	}
	
	protected void updateInAir(int[][]lvlData) {
		if(CanMoveHere(collision.x, collision.y + fallSpeed, collision.height, collision.width, lvlData)) {
			collision.y += fallSpeed;
			fallSpeed += gravity;
		}
		else {
			inAir = false;
			collision.y = GetEntityYPosUnderRoofOrAboveFloor(collision, fallSpeed);
			tileY = (int) (collision.y / Game.TILES_SIZE);
		}
	}
	
	protected void move(int[][]lvlData) {
		float xSpeed = 0;
		if(walkDir == LEFT) 
			xSpeed = -mummySpeed;
		else 
			xSpeed = mummySpeed;
		
		if(CanMoveHere(collision.x, collision.y, collision.width, collision.height, lvlData)) 
			if(IsFloor(collision, xSpeed, lvlData)) {
				collision.x += xSpeed;
				return;
			}
			
		changeWalkDir();
	}
	
	protected void turnTowardsPlayer(Player player) {
		if (player.collision.x > collision.x)
			walkDir = RIGHT;
		else
			walkDir = LEFT;
	}

	protected boolean canSeePlayer(int[][] lvlData, Player player) {
		int playerTileY = (int) (player.getCollision().y / Game.TILES_SIZE);
		if (playerTileY == tileY)
			if (isPlayerInRange(player)) {			
				if (IsSightClear(lvlData, collision, player.collision, tileY))
					return true;
			}

		return false;
	}

	protected boolean isPlayerInRange(Player player) {
		int absValue = (int) Math.abs(player.collision.x - collision.x);
		return absValue <= attackDistance * 5;
	}

	protected boolean isPlayerCloseForAttack(Player player) {
		int absValue = (int) Math.abs(player.collision.x - collision.x);
		return absValue <= attackDistance;
	}
	
	protected void newState(int enemyState) {
		this.enemyState = enemyState;
		aniTick = 0;
		aniIndex = 0;
	}
	
	protected void changeWalkDir() {
		if(walkDir == LEFT) {
			walkDir = RIGHT;
		}else
			walkDir = LEFT;
		
	}

	public int getAniIndex() {return aniIndex;}
	public void setAniIndex(int aniIndex) {this.aniIndex = aniIndex;}
	public int getEnemyState() {return enemyState;}
	public void setEnemyState(int enemyState) {this.enemyState = enemyState;}
	public boolean isActive() {return active;}
	public void setActive(boolean active) {this.active = active;}
	
	public void resetEnemy() {
		inAir = true;
		collision.x = x;
		collision.y = y;
		firstUpdate = false;
		currentHealth = maxHealth;
		newState(IDLE);
		active = true;
		fallSpeed = 0;
	}
	
}
