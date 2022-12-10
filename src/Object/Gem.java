package Object;

import Main.Game;

public class Gem extends GameObject {

	private float hoverOffset;
	private int maxHoverOffset, hoverDirection = 1;
	
	public Gem(int x, int y, int objType) {
		super(x, y, objType);
		doAnimation = true;
		initArea(7, 14);
		xDrawOffset = (int)(3 * Game.SCALE);
		yDrawOffset = (int)(2 * Game.SCALE);
		
		maxHoverOffset = (int)(8 * Game.SCALE);
	}
	
	public void updateGem() {
		updateAnimationTick();
		updateHover();
	}

	private void updateHover() {
		hoverOffset += (0.07f * Game.SCALE * hoverDirection);
		
		if(hoverOffset >= maxHoverOffset)
			hoverDirection = -1;
		else if(hoverOffset < 0)
			hoverDirection = 1;
	
		area.y = y + hoverOffset;
	}
}
