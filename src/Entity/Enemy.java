package Entity;

import static Util.Constants.EnemyConstants.*;

import static Util.HelpMethods.*;
import static Util.Constants.Direction.*;

import java.awt.Color;
import java.awt.Graphics;

import Main.Game;

public abstract class Enemy extends Entity{

	private int aniIndex, aniTick, aniSpeed = 25;
	private int enemyState = IDLE, enemyType;
	private boolean firstUpdate = true;
	private boolean inAir;
	private float fallSpeed;
	private float mummySpeed = 1.0f * Game.SCALE;
	private int walkDir = LEFT;
	
	public Enemy(float x, float y, int height, int width, int enemyType) {
		super(x, y, height, width);
		this.enemyType = enemyType;
		initCollision(x, y, width, height);
		
	}
	
	private void updateAnimationTick() {
		aniTick++;
		if(aniTick >= aniSpeed) {
			aniTick = 0;
			aniIndex++;
			if(aniIndex >= GetSpriteAmount(enemyType, enemyState)){
				aniIndex = 0;
			}
		}
	}
	
	public void update(int[][] lvlData) {
		updateAnimationTick();
		updateMove(lvlData);
	}
	
	private void updateMove(int[][] lvlData) {
		if(firstUpdate) {
			if(!IsEntityOnFloor(collision, lvlData)) {
				inAir = true;
			}
			firstUpdate = false;
		}
		
		if(inAir) {
			if(CanMoveHere(collision.x, collision.y + fallSpeed, collision.height, collision.width, lvlData)) {
				collision.y += fallSpeed;
				fallSpeed += gravity;
			}
			else {
				inAir = false;
				collision.y = GetEntityYPosUnderRoofOrAboveFloor(collision, fallSpeed);
			}
		}
		else {
			switch(enemyState) {
			case IDLE:
				enemyState = RUNNING;
			case RUNNING:
				float xSpeed = 0;
				if(walkDir == LEFT) 
					xSpeed = -mummySpeed;
				else 
					xSpeed = mummySpeed;
				
				if(CanMoveHere(collision.x, collision.y, collision.width, collision.height, lvlData)) {
					if(IsFloor(collision, xSpeed, lvlData)) {
						collision.x += xSpeed;
						return;
					}
				}
				
				changeWalkDir();
				break;
			}
		}
		
	}


	private void changeWalkDir() {
		if(walkDir == LEFT) {
			walkDir = RIGHT;
		}else
			walkDir = LEFT;
		
	}

	public int getAniIndex() {return aniIndex;}
	public void setAniIndex(int aniIndex) {this.aniIndex = aniIndex;}
	public int getEnemyState() {return enemyState;}
	public void setEnemyState(int enemyState) {this.enemyState = enemyState;}
	
}
