package Entity;

import static Util.Constants.Direction.DOWN;
import static Util.HelpMethods.*;
import static Util.Constants.*;

import static Util.Constants.Direction.LEFT;
import static Util.Constants.Direction.RIGHT;
import static Util.Constants.Direction.UP;
import static Util.Constants.PlayerConstants.*;

import static Util.HelpMethods.CanMoveHere;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import GameState.Playing;
import Main.Game;
import Util.LoadSave;

public class Player extends Entity{
	
	private BufferedImage[][] anim;
	private int aniTick, aniIndex, aniSpeed = 10;
	private int playerAction = IDLE;
	private boolean onSword = false;
	private boolean move = false;
	private boolean attack = false;
	
	private boolean left, right, jump = false;
	private int[][] lvlData;
	private float xDrawOffset = 20 * Game.SCALE;
	private float yDrawOffset = 12 * Game.SCALE;
	
	private int playerSpeed = (int) (2.5 * Game.SCALE);
	private float airSpeed = 0f;
	private float jumpSpeed = -2.75f * Game.SCALE;
	private float fallSpeedAfterCollision = 0.5f * Game.SCALE;
	private boolean inAir = false;
	
	private Playing playing;
	

	public Player(float x, float y, int height, int width) {
		super(x, y, height, width);
		
		//Player Image Import
		importImg();
		initCollision(x, y, 2 * 14 * Game.SCALE-5, 2 * 20 * Game.SCALE);
		// TODO Auto-generated constructor stub
	}
	
	private void importImg() {	
		try {
			anim = new BufferedImage[8][5];
			
			//IDLE NO SWORD
			anim[0][0] = ImageIO.read(getClass().getResourceAsStream("/char_1.png"));
			
			//RUNNING NO SWORD
			anim[1][0] = ImageIO.read(getClass().getResourceAsStream("/char_3.png"));
			anim[1][1] = ImageIO.read(getClass().getResourceAsStream("/char_1.png"));
			anim[1][2] = ImageIO.read(getClass().getResourceAsStream("/char_2.png"));
			anim[1][3] = ImageIO.read(getClass().getResourceAsStream("/char_1.png"));
			
			//ATTACK WITH SWORD
			anim[3][0] = ImageIO.read(getClass().getResourceAsStream("/Char/char_Attack_1.png"));
			anim[3][1] = ImageIO.read(getClass().getResourceAsStream("/Char/char_Attack_2.png"));
			anim[3][2] = ImageIO.read(getClass().getResourceAsStream("/Char/char_Attack_3.png"));
			
			//IDLE WITH SWORD
			anim[4][0] = ImageIO.read(getClass().getResourceAsStream("/Char/char_Sword_1.png"));
			
			//RUNNING WITH SWORD
			anim[5][0] = ImageIO.read(getClass().getResourceAsStream("/Char/char_sword_3.png"));
			anim[5][1] = ImageIO.read(getClass().getResourceAsStream("/Char/char_sword_1.png"));
			anim[5][2] = ImageIO.read(getClass().getResourceAsStream("/Char/char_sword_2.png"));
			anim[5][3] = ImageIO.read(getClass().getResourceAsStream("/Char/char_sword_1.png"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private void updateAnimationTick() {
		aniTick++;
		if(aniTick >= aniSpeed) {
			aniTick = 0;
			aniIndex++;
			if(aniIndex >= GetSpriteAmount(playerAction)) {
				aniIndex = 0;
				attack = false;
			}
		}
	}
	
	
	
	private void setAnimation() {
		int startAni = playerAction;
		if(onSword) {
			if(move) {
				playerAction = RUNNING_SWORD;
			}else {
				playerAction = IDLE_SWORD;
			}
			if(attack) {
				playerAction = ATTACK;
			}
		}
		else {
			if(move) {
				playerAction = RUNNING;
			}else {
				playerAction = IDLE;
			}
		}
		
		if(startAni != playerAction) {
			resetAniTick();
		}
	}
	
	private void resetAniTick() {
		aniTick = 0;
		aniIndex = 0;
	}

	private void updatePlayerPos() {
		
		move = false;
		
		if(jump) Jump();
		
		if (!left && !right && !inAir)
			return;
		
		float xSpeed = 0;
		
		if(left) {
			xSpeed -= playerSpeed;

		}
		if(right) {
			xSpeed += playerSpeed;
			
		}
		
		if (!inAir)
			if (!IsEntityOnFloor(collision, lvlData))
				inAir = true;
		
		if (inAir) {
			move = false;
			if (CanMoveHere(collision.x, collision.y + airSpeed, collision.width, collision.height, lvlData)) {
				collision.y += airSpeed;
				airSpeed += gravity;
				updateXPos(xSpeed);
			} else {
				collision.y = GetEntityYPosUnderRoofOrAboveFloor(collision, airSpeed);
				if (airSpeed > 0)
					resetInAir();
				else
					airSpeed = fallSpeedAfterCollision;
				updateXPos(xSpeed);
			}

		} else
			updateXPos(xSpeed);
			move = true;
	
	}
	
	private void Jump() {
		if(jump) {
			if (inAir)
				return;
			inAir = true;
			airSpeed = jumpSpeed;
		}
	}
	
	private void resetInAir() {
		inAir = false;
		airSpeed = 0;

	}
	
	private void updateXPos(float xSpeed) {
		if (CanMoveHere(collision.x + xSpeed, collision.y, collision.width, collision.height, lvlData)) {
			collision.x += xSpeed;
		} else {
			collision.x = GetEntityXPosNextToWall(collision, xSpeed);
		}

	}
	
	public void Update() {
		updatePlayerPos();
		updateAnimationTick();
		setAnimation();
		
		//if() { // if (moving)
		//	checkTrapTouched();
		//}
		
	}
	
	public void loadLvlData(int[][] lvlData) {
		this.lvlData = lvlData;
		if(!IsEntityOnFloor(collision, lvlData)) {
			inAir = true;
		}
	}
	
	public void render(Graphics g, int lvlOffset) {

		drawCollision(g, lvlOffset);
//		g.drawRect((int)x, (int)y, 96, 96);
		g.drawImage(anim[playerAction][aniIndex], (int)(collision.x - xDrawOffset)-lvlOffset, (int)(collision.y - yDrawOffset), width , height ,null);
	}
	
	public void resetDirBool() {
		left = false;
		right = false;
	}
	
	public void die() {
		System.out.println("trap touched");
		// currentHealth = 0;
	}
	
	private void checkTrapTouched() {
		playing.trapTouched(this);
	}

	public boolean isLeft() {return left;}
	public void setLeft(boolean left) {this.left = left;}
	public boolean isRight() {return right;}
	public void setRight(boolean right) {this.right = right;}
	public boolean isJump() {return jump;}
	public void setJump(boolean jump) {this.jump = jump;}
	public boolean isAttack() {return attack;}
	public void setAttack(boolean attack) {this.attack = attack;}
	public boolean isOnSword() {return onSword;}
	public void setOnSword(boolean onSword) {this.onSword = onSword;}
	
	
}
