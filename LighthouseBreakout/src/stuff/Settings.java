package stuff;

import java.awt.event.KeyEvent;

import stuff.Style.Theme;

class Settings {
	// display settings
	static int FRAME_TICK_MS = 20;
	static boolean SHOW_FPS_ON_DISPLAY = true;
	static boolean SHOW_RASTER_ON_DISPLAY = true;
	static Theme THEME = Style.Theme.COLORFUL;

	// game engine settings
	static int GAME_TICK_MS = 20;

	// animation settings
	static boolean DO_ANIMATIONS = true;
	static boolean PLAYER_INVINCEBLE = false;
	static boolean BRICK_COLLISION = true;
	static boolean GAME_RUNNING = false;
	static boolean MENU_VIEW = true;
	static boolean HAX_ON = true;
	static boolean OPTIONS_MENU = false;

	// window settings
	static enum Keys {
		PADDEL_LEFT(KeyEvent.VK_LEFT),
		PADDEL_RIGHT(KeyEvent.VK_RIGHT),
		SWITCH_FPS_DISPLAY(KeyEvent.VK_F1),
		PAUSE_GAME(KeyEvent.VK_P),
		SHOW_MENU(KeyEvent.VK_ESCAPE),
		HAX_SWITCH(KeyEvent.VK_C);

		int keyCode;

		Keys(int keyCode) {
			this.keyCode = keyCode;
		}
	}
	
	// other settings
	public static final boolean CONNECT_TO_LIGHTHOUSE = true;
}
