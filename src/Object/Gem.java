package Object;

import Main.Game;

public class Gem extends GameObject {
	
	public Gem(int x, int y, int objType) {
		super(x, y, objType);
		doAnimation = true;
		initArea(7, 14);
		xDrawOffset = (int)(3 * Game.SCALE);
		yDrawOffset = (int)(2 * Game.SCALE);
	}
	
	public void updateGem() {
		updateAnimationTick();
	}

}
