package Object;

import static Util.Constants.aniSpeed;

import Main.Game;

public class Door extends GameObject{

	public Door(int x, int y, int objType) {
		super(x, y, objType);
		
		initArea(32, 32);
		xDrawOffset = 0;
		yDrawOffset = (int)(Game.SCALE * 32);
		area.y += yDrawOffset;
		//PERLU DI RE-CHECK
	}

	public void updateDoor() {
		if(doAnimation) {
			aniTick++;
			if(aniTick >= aniSpeed && aniIndex < 4) {
				aniTick = 0;
				aniIndex++;
			}
		}
	}
	
}
