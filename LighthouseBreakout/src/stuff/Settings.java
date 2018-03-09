package stuff;

import java.awt.event.KeyEvent;

import model.Map;
import stuff.Style.Theme;

public class Settings {
	// display settings
	public static int FRAME_TICK_MS = 20;
	public static boolean SHOW_FPS_ON_DISPLAY = true;
	public static boolean SHOW_RASTER_ON_DISPLAY = true;
	public static Theme THEME = Style.Theme.SPOOKY;

	// game engine settings
	public static int GAME_TICK_MS = 100;
	public static int RESET_TIMEOUT = 300;
	public static boolean EXPERIMENTAL_ENGINE = true;
	public static Map LEVEL = Map.CAU;
	public static boolean GAME_WON = false;
	public static int SCORE = 0;

	// animation settings
	public static boolean DO_ANIMATIONS = true;
	public static boolean MENU_SHOWN = true;
	public static boolean HAX_ON = true;
	public static boolean OPTIONS_MENU = false;
	public static boolean MOUSE_CONTROL = false;
	public static boolean SOUND_XP_START = true;
	public static boolean SOUND_XP_SHUTDOWN = false;
	public static boolean CUSTOM_FONT = true;

	// planned
	public static boolean PLAYER_INVINCEBLE = false;
	public static boolean BRICK_COLLISION = true;

	// window settings
	public static enum Keys {
		PADDEL_LEFT(KeyEvent.VK_LEFT),
		PADDEL_RIGHT(KeyEvent.VK_RIGHT),
		SWITCH_FPS_DISPLAY(KeyEvent.VK_F),
		PAUSE_GAME(KeyEvent.VK_SPACE),
		SHOW_MENU(KeyEvent.VK_ESCAPE),
		HAX_SWITCH(KeyEvent.VK_C),
		CONTROL_SWITCH(KeyEvent.VK_M),
		ENGINE_RESET(KeyEvent.VK_R),
		DEBUG(KeyEvent.VK_D),
		GAME_START(KeyEvent.VK_SPACE);

		int keyCode;

		Keys(int keyCode) {
			this.keyCode = keyCode;
		}
	}

	// other settings
	public static boolean CONNECT_TO_LIGHTHOUSE = false;
	public static String PLAYER_NAME = "YOU";
}