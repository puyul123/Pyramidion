package Entity;

import static Util.HelpMethods.*;

import static Util.Constants.Direction.LEFT;
import static Util.Constants.Direction.RIGHT;
import static Util.Constants.PlayerConstants.*;

import static Util.HelpMethods.CanMoveHere;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import GameState.Playing;
import Main.Game;
import Util.LoadSave;
import static Util.Constants.UI.PlayingUI.*;

public class Player extends Entity{
	
	Graphics2D g2;
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
	private int playerDir = RIGHT;
	
	private int playerSpeed = (int) (2.5 * Game.SCALE);
	private float airSpeed = 0f;
	private float jumpSpeed = -2.75f * Game.SCALE;
	private float fallSpeedAfterCollision = 0.5f * Game.SCALE;
	private boolean inAir = false;
	
	private Playing playing;
	
	
	//ATTACK COLLISION
	private Rectangle2D.Float attackCol, sword_on, sword_off;
	
	
	//mirror
	private int flipX = 0;
	private int flipW = 1;
	
	//StatusBarUI
	private BufferedImage statusBarImg, scoreImg;

	private int maxHealth = 100;
	private int currentHealth = maxHealth;
	private int healthWidth = healthBarWidth;

//	private int minPoint = 0;
	private int currentPoint = 0;
	private int currentPointLevel = 0;
	
	//COMBAT
	private boolean attackChecked;
	
	public Font font;
	
	public Player(float x, float y, int height, int width, Playing playing) {
		super(x, y, height, width);
		this.playing = playing;
		
		//Player Image Import
		importImg();
		importFont();
		initCollision(x, y, 2 * 14 * Game.SCALE-5, 2 * 20 * Game.SCALE);
		initAttackColl();
	}
	
	private void initAttackColl() {
		attackCol = new Rectangle2D.Float(x, y, (int) (20*Game.SCALE), (int) (27*Game.SCALE));
		sword_on = attackCol;	
		sword_off = new Rectangle2D.Float(0, 0, 0, 0);
	}

	private void importImg() {	
			anim = new BufferedImage[8][5];
						
			//IDLE NO SWORD
			anim[0][0] =  LoadSave.GetSpriteAtlas("Char/char_1.png");
	
			//RUNNING NO SWORD
			anim[1][0] = LoadSave.GetSpriteAtlas("Char/char_3.png");
			anim[1][1] = LoadSave.GetSpriteAtlas("Char/char_1.png");
			anim[1][2] = LoadSave.GetSpriteAtlas("Char/char_2.png");
			anim[1][3] = LoadSave.GetSpriteAtlas("Char/char_1.png");
			
			//ATTACK WITH SWORD
			anim[3][0] = LoadSave.GetSpriteAtlas("Char/char_Attack_1.png");
			anim[3][1] = LoadSave.GetSpriteAtlas("Char/char_Attack_2.png");
			anim[3][2] = LoadSave.GetSpriteAtlas("Char/char_Attack_3.png");
			
			//IDLE WITH SWORD
			anim[4][0] = LoadSave.GetSpriteAtlas("Char/char_Sword_1.png");
			
			//RUNNING WITH SWORD
			anim[5][0] = LoadSave.GetSpriteAtlas("Char/char_sword_3.png");
			anim[5][1] = LoadSave.GetSpriteAtlas("Char/char_sword_1.png");
			anim[5][2] = LoadSave.GetSpriteAtlas("Char/char_sword_2.png");
			anim[5][3] = LoadSave.GetSpriteAtlas("Char/char_sword_1.png");
			
			
			//STATUSBAR
			statusBarImg = LoadSave.GetSpriteAtlas(LoadSave.STATUS_BAR);
			
			//SCORE
			scoreImg = LoadSave.GetSpriteAtlas("score_bg.png"); 
	}
	
	private void importFont() {
		try {this.font = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/font/egypt1.ttf"));} 
		catch (FontFormatException e) {e.printStackTrace();} 
		catch (IOException e) {e.printStackTrace();}
	}
	
	private void updateAnimationTick() {
		aniTick++;
		if(aniTick >= aniSpeed) {
			aniTick = 0;
			aniIndex++;
			if(aniIndex >= GetSpriteAmount(playerAction)) {
				aniIndex = 0;
				attack = false;
				attackChecked = false;
			}
		}
	}
	
	
	
	private void setAnimation() {
		int startAni = playerAction;
		if(onSword) {
			attackCol = sword_on;
			if(move) {
				playerAction = RUNNING_SWORD;
			}else {
				playerAction = IDLE_SWORD;
			}
			if(attack) {
				playerAction = ATTACK;
				if(startAni != ATTACK) {
					aniIndex = 1;
					aniTick = 0;
					return;
				}
			}
		}
		else {
			attackCol = sword_off;
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
			flipX = this.width;
			flipW = -1;
			playerDir = LEFT;
		}
		if(right) {
			xSpeed += playerSpeed;
			flipX = 0;
			flipW = 1;
			playerDir = RIGHT;
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
	
	public void changeHealth(int value) {
		currentHealth += value;

		if (currentHealth <= 0)
			currentHealth = 0;
		else if (currentHealth >= maxHealth)
			currentHealth = maxHealth;
	}
	
	public void changePoint(int value) {
		currentPoint += value;
	}
	
	public void Update() {
		if(currentHealth <= 0) {
			playing.setGameOver(true);
			return;
		}
		
		updateHealthBar();
		updatePlayerPos();
		
		if(attack) checkAttack();
		
		if(move) {
			checkPotionTouched();
			checkGemTouched();
			checkTrapTouched();
			checkDoorTouched();
			checkLeverTouched();
		}
		
		updateAnimationTick();
		setAnimation();
		updateAttackBox();
		
		//if() { // if (moving)
		//	checkTrapTouched();
		//}
		
	}
	
	private void checkAttack() {
		if(attackChecked || aniIndex != 1) {
			return;
		}
		attackChecked = true;
		playing.checkEnemyHit(attackCol);
		playing.checkObjectHit(attackCol);
	}
	
	private void checkLeverTouched() {
		playing.checkLeverTouched(this);
		
	}
	
	private void checkDoorTouched() {
		playing.checkDoorTouched(this);
		
	}
	
	private void checkPotionTouched() {
		playing.checkPotionTouched(collision);
	}

	private void checkGemTouched() {
		playing.checkGemTouched(collision);
	}
	
	private void checkTrapTouched() {
		playing.trapTouched(this);
	}

	
	private void updateHealthBar() {
		healthWidth = (int) ((currentHealth / (float) maxHealth) * healthBarWidth);
	}

	private void updateAttackBox() {
		if(right) {
			attackCol.x = collision.x + collision.width + (int)(Game.SCALE * 10);
		}
		else if(left) {
			attackCol.x = collision.x - collision.width - (int)(Game.SCALE * 10);
		}
		attackCol.y = collision.y + (Game.SCALE * 10);
	}

	public void loadLvlData(int[][] lvlData) {
		this.lvlData = lvlData;
		if(!IsEntityOnFloor(collision, lvlData)) {
			inAir = true;
		}
	}
	
	public void render(Graphics g, int lvlOffset) {

//		drawCollision(g, lvlOffset);
		if(playerAction == 0 || playerAction == 1)
		g.drawImage(anim[playerAction][aniIndex], 
				(int)(collision.x - xDrawOffset)-lvlOffset + flipX, 
				(int)(collision.y - yDrawOffset), 
				width * flipW, height ,null);
		else if(playerAction >= 3) {
			if(playerDir == RIGHT)	
				g.drawImage(anim[playerAction][aniIndex], 
						(int)(collision.x - xDrawOffset - 25)-lvlOffset + flipX, 
						(int)(collision.y - yDrawOffset), 
						(width + 50) * flipW, height ,null);
			else if(playerDir == LEFT)
				g.drawImage(anim[playerAction][aniIndex], 
						(int)(collision.x - xDrawOffset + 25)-lvlOffset + flipX, 
						(int)(collision.y - yDrawOffset), 
						(width + 50) * flipW, height ,null);
		}
		drawAttackBox(g, lvlOffset);
		drawUI(g);
	}
	
	private void drawUI(Graphics g) {
		g.drawImage(statusBarImg, statusBarX, statusBarY, statusBarWidth, statusBarHeight, null);
		g.drawImage(scoreImg, ScoreX, ScoreY, ScoreWidth, ScoreHeight, null);
		g.setColor(Color.red);
		g.fillRect(healthBarXStart + statusBarX, healthBarYStart + statusBarY, healthWidth, healthBarHeight);
		
		this.g2 = (Graphics2D)g;
		g2.setFont(font); 
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 30));
		
		g2.setColor(Color.white);
		g2.drawString("Score:", ScoreStringX, ScoreStringY1);
		g2.drawString(""+currentPoint  , ScoreStringX, ScoreStringY2);
	}

	private void drawAttackBox(Graphics g, int lvlOffset) {
		g.setColor(Color.green);
		g.drawRect((int) attackCol.x - lvlOffset,(int) attackCol.y,(int) attackCol.height,(int) attackCol.width);
		
	}

	public void resetDirBool() {
		left = false;
		right = false;
	}
	
	public void hurt() {
		currentHealth -= 2;
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
	

	public int getCurrentPoint() {
		return currentPoint;
	}

	public void setCurrentPoint(int currentPoint) {
		this.currentPoint = currentPoint;
	}

	public int getCurrentPointLevel() {
		return currentPointLevel;
	}

	public void setCurrentPointLevel(int currentPointLevel) {
		this.currentPointLevel = currentPointLevel;
	}

	public void resetAll() {
		resetDirBool();
		inAir = false;
		attack = false;
		move = false;
		playerAction = IDLE;
		currentHealth = maxHealth;
		
		collision.x = x;
		collision.y = y;
		
		if(!IsEntityOnFloor(collision, lvlData))
			inAir = true;
	}
	
	
}
