package Util;

import Main.Game;

public class Constants {	
	 
	public static class ObjectConstants {
		public static final int TRAP = 101;
		public static final int TRAP_WIDTH_DEFAULT = 32;
		public static final int TRAP_HEIGHT_DEFAULT = 32;
		public static final int TRAP_WIDTH = (int) (Game.SCALE * TRAP_WIDTH_DEFAULT);
		public static final int TRAP_HEIGHT = (int) (Game.SCALE * TRAP_HEIGHT_DEFAULT);
	}
	
	public static class EnemyConstants {
		public static final int MUMMY = 1;
		
		public static final int MUMMY_SIZE = 64;
		
		//DEVELOP
		public static final int MUMMY_DRAWOFFSET_X = (int) (7 * Game.SCALE);
		public static final int MUMMY_DRAWOFFSET_Y = (int) (6 * Game.SCALE);
		
		public static final int IDLE = 0;
		public static final int RUNNING = 1;
		public static final int ATTACK = 2;
		public static final int HIT = 3;
		public static final int DEAD = 4;
		
		
		
		public static int GetSpriteAmount(int enemyType, int enemyState) {
			switch(enemyType) {
				case MUMMY ->{
					switch(enemyState) {
					case IDLE: return 1;
					case RUNNING: return 2;
					case ATTACK: return 2;
					case HIT: return 1;
					case DEAD: return 3;
					default: return 1;
					}
				}
			}
			return 0;
		}
	}
	
	public static class UI {
		public static class Buttons {
			public static final int B_WIDTH_DEFAULT = 140;
			public static final int B_HEIGHT_DEFAULT = 56;
			public static final int B_WIDTH = (int) (B_WIDTH_DEFAULT * Game.SCALE);
			public static final int B_HEIGHT = (int) (B_HEIGHT_DEFAULT * Game.SCALE);
		}
	}
	
	public static class Direction{
		public static final int LEFT = 0;
		public static final int RIGHT = 1;
		public static final int UP = 2;
		public static final int DOWN = 3;
	}
	
	public static class PlayerConstants{
		public static final int IDLE = 0;
		public static final int RUNNING = 1;
		public static final int HIT = 2;
		public static final int ATTACK = 3;
		public static final int IDLE_SWORD = 4;
		public static final int RUNNING_SWORD = 5;
		
		public static int GetSpriteAmount(int player_act) {
			
			switch(player_act){
			
			case IDLE:
				return 1;
			case RUNNING:
				return 4;
			case ATTACK: 
				return 3;
			case IDLE_SWORD: 
				return 1;
			case RUNNING_SWORD:
				return 4;
			default:
				return 1;
			}
		}
		
	}
}
