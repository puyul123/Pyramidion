package Entity;

import static Util.Constants.Direction.DOWN;
import static Util.Constants.Direction.LEFT;
import static Util.Constants.Direction.RIGHT;
import static Util.Constants.Direction.UP;
import static Util.Constants.PlayerConstants.*;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Player extends Entity{
	
	private BufferedImage[][] anim;
	private int aniTick, aniIndex, aniSpeed = 25;
	private int playerAction = IDLE;
	private boolean move = false;
	private boolean attack = false;
	
	private boolean left, right, up, down;

	public Player(float x, float y) {
		super(x, y);
		
		//Player Image Import
		importImg();
		// TODO Auto-generated constructor stub
	}
	
	private void importImg() {	
		try {
			anim = new BufferedImage[8][5];
			
			anim[0][0] = ImageIO.read(getClass().getResourceAsStream("/pixil-frame-0.png"));
			anim[1][0] = ImageIO.read(getClass().getResourceAsStream("/pixil-frame-1.png"));
			anim[1][1] = ImageIO.read(getClass().getResourceAsStream("/pixil-frame-2.png"));
			anim[1][2] = ImageIO.read(getClass().getResourceAsStream("/pixil-frame-3.png"));
			anim[1][3] = ImageIO.read(getClass().getResourceAsStream("/pixil-frame-2.png"));
			//anim for attack [3]
			//anim for hit [2]
		} catch (IOException e) {
			// TODO Auto-generated catch block
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
		
		if(move) {
			playerAction = RUNNING;
		}else {
			playerAction = IDLE;
		}
		
		if(attack) {
			playerAction = ATTACK;
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
		
		if(left) {
			x -= 3;
			move = true;
		}
		if(right) {
			x += 3;
			move = true;
		}
		if(up) {
			y -= 3;
			move = true;
		}
		if(down) {
			y += 3;
			move = true;
		}
	}
	
	public void Update() {
		updateAnimationTick();
		setAnimation();
		updatePlayerPos();
	}
	
	public void render(Graphics g) {

		
//		g.drawRect((int)x, (int)y, 96, 96);
		g.drawImage(anim[playerAction][aniIndex], (int)x, (int)y, 96, 96 ,null);
	}
	
	public void resetDirBool() {
		left = false;
		right = false;
		up = false;
		down = false;
	}

	public boolean isLeft() {return left;}
	public void setLeft(boolean left) {this.left = left;}
	public boolean isRight() {return right;}
	public void setRight(boolean right) {this.right = right;}
	public boolean isUp() {return up;}
	public void setUp(boolean up) {this.up = up;}
	public boolean isDown() {return down;}
	public void setDown(boolean down) {this.down = down;}
	public boolean isAttack() {return attack;}
	public void setAttack(boolean attack) {this.attack = attack;}
	
	
}
