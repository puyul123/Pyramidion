package Object;

import Main.Game;

public class Trap extends GameObject{

	public Trap(int x, int y, int objType) {
		super(x, y, objType);

		initArea(32, 16);
		xDrawOffset = 0;
		yDrawOffset = (int)(Game.SCALE * 16);
		area.y += yDrawOffset;
	}
	

}
