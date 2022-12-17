package Object;

import static Util.Constants.aniSpeed;

import Main.Game;

public class Lever extends GameObject{
	public Lever(int x, int y, int objType) {
		super(x, y, objType);
		
		initArea(32, 32);
		xDrawOffset = 0;
		yDrawOffset = (int)(Game.SCALE * 29);
		area.y += yDrawOffset;
		//PERLU DI RE-CHECK
	}

	public void updateLever() {
		if(doAnimation) {
			aniTick++;
			if(aniTick >= aniSpeed && aniIndex < 1) {
				aniTick = 0;
				aniIndex++;
			}
		}
	}
}
