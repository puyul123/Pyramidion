package Object;

import static Util.Constants.ObjectConstants.*;

import Main.Game;

public class Container extends GameObject {

	public Container(int x, int y, int objType) {
		super(x, y, objType);
		createArea();
	}

	private void createArea() {
		if(objType == CONTAINER) {
			initArea(23, 25);

			xDrawOffset = (int)(8 * Game.SCALE);
			yDrawOffset = (int)(5 * Game.SCALE);
		}
		
		area.y += yDrawOffset + (int)(Game.SCALE * 26);
		area.x += xDrawOffset / 2;
	}
	
	public void updateObject() {
		if(doAnimation) {
			updateAnimationTick();
		}
	}

}
