package Util;

public class Constants {
	
	public static class Direction{
		public static final int LEFT = 0;
		public static final int RIGHT = 1;
		public static final int UP = 2;
		public static final int DOWN = 3;
	}
	
	public static class PlayerConstants{
		public static final int IDLE = 0;
		public static final int RUNNING = 1;
		public static final int JUMP = 2;
		public static final int HIT = 3;
		public static final int ATTACK_1 = 4;
		public static final int ATTACK_JUMP = 5;
		
		public static int GetSpriteAmount(int player_act) {
			
			switch(player_act){
			
			case IDLE:
				return 1;
			case RUNNING:
				return 4;
			case ATTACK_1: //develop
			case ATTACK_JUMP: //develop
			default:
				return 1;
			}
		}
		
	}
}
