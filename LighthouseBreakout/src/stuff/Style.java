package stuff;

import java.awt.Color;
import java.util.Arrays;

public class Style {
	
	public static Color background;
	public static Color ballColor;
	public static Color[] brickColor = new Color[4];
	public static Color menuButton;
	public static Color menuButtonOn;
	public static Color border;
	public static Color paddel;
	
	public static enum Theme {
		DEFAULT(null),
		DARK(DEFAULT),
		LIGHT(DEFAULT),
		COLORFUL(DEFAULT),
		SPOOKY(DEFAULT);
		
		private Theme parent;

		private Theme(Theme parent) {
			this.parent = parent;
		}
		
		private Theme getParent() {
			return parent;
		}
	}
	public static void loadTheme(Theme t) {
		if (t == null)
			return;
		loadTheme(t.getParent());
		switch (t) {
		case DEFAULT:
			background = Color.BLACK;
			ballColor = Color.RED;
			Arrays.fill(brickColor, Color.WHITE);
			break;
		case DARK:
			Arrays.fill(brickColor, Color.GRAY);
			break;
		case LIGHT:
			background = Color.WHITE;
			ballColor = Color.GREEN;
			brickColor[1] = Color.YELLOW;
			brickColor[3] = Color.BLUE;
			brickColor[2] = new Color(255,96,96);
			menuButton = Color.GREEN;
			menuButtonOn = Color.LIGHT_GRAY;
			border = Color.GREEN;
			paddel = Color.RED;
			break;
		case COLORFUL:
			background = Color.BLACK;
			ballColor = Color.MAGENTA;
			brickColor[1] = Color.GREEN;
			brickColor[2] = Color.YELLOW;
			brickColor[3] = Color.RED;
			menuButton = Color.RED;
			menuButtonOn = Color.WHITE;
			border = Color.RED;
			paddel = Color.WHITE;
			break;
		case SPOOKY:
			background = Color.BLACK;
			ballColor = Color.WHITE;
			brickColor[1] = new Color(69,100,150);
			brickColor[2] = new Color(30,54,94);
			brickColor[3] = new Color(5,21,48);
			menuButton = Color.WHITE;
			menuButtonOn = Color.RED;
			border = Color.RED;
			paddel = new Color(109,0,0);
			break;
		default:
			break;
		}
		brickColor[0] = background;
	}
	public static Theme next() {
		switch (Settings.THEME) {
		case COLORFUL:
			Settings.THEME = Theme.LIGHT;
			break;
		case DARK:
			break;
		case DEFAULT:
			break;
		case LIGHT:
			Settings.THEME = Theme.SPOOKY;
			break;
		case SPOOKY:
			Settings.THEME = Theme.COLORFUL;
			break;
		default:
			break;
		
		}
		return Settings.THEME;
	}
}