package stuff;

import java.awt.event.KeyEvent;

class Settings {
	// display settings
	static int FRAME_TICK_MS = 20;
	static boolean SHOW_FPS_ON_DISPLAY = true;
	static boolean SHOW_RASTER_ON_DISPLAY = true;

	// game engine settings
	static int GAME_TICK__MS = 120;

	// animation settings
	static boolean DO_ANIMATIONS = true;
	static boolean PLAYER_INVINCEBLE = false;
	static boolean BRICK_COLLISION = true;

	// window settings
	static enum Keys {
		PADDEL_LEFT(KeyEvent.VK_LEFT),
		PADDEL_RIGHT(KeyEvent.VK_RIGHT),
		SWITCH_FPS_DISPLAY(KeyEvent.VK_F1);

		int keyCode;

		Keys(int keyCode) {
			this.keyCode = keyCode;
		}
	}
	
	// other settings
	public static final boolean CONNECT_TO_LIGHTHOUSE = false;
}
