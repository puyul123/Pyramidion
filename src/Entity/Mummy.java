package Entity;

//import static Util.Constants.Direction.*;

import static Util.Constants.EnemyConstants.*;
import static Util.Constants.Direction.*;

import static Util.HelpMethods.CanMoveHere;
import static Util.HelpMethods.GetEntityYPosUnderRoofOrAboveFloor;
import static Util.HelpMethods.IsEntityOnFloor;
import static Util.HelpMethods.IsFloor;

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
	
	public void update(int[][] lvlData, Player player) {
		updateAnimationTick();
		updateMove(lvlData, player);
	}
	
	private void updateMove(int[][] lvlData, Player player) {
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
					System.out.println("TURN");
					turnTowardsPlayer(player);
				}
				if (isPlayerCloseForAttack(player))
					newState(ATTACK);
				move(lvlData);
				break;
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
