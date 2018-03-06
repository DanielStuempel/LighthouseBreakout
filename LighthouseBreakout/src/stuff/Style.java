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
	
	public static enum Theme {
		DEFAULT(null),
		DARK(DEFAULT),
		LIGHT(DEFAULT),
		COLORFUL(DEFAULT);
		
		private Theme parent;

		private Theme(Theme parent) {
			this.parent = parent;
		}
		
		private Theme getParent() {
			return parent;
		}
	}
	
	public static void loadTheme(Theme t) {
		//TODO: improve
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
			background = Color.GRAY;
			break;
		case COLORFUL:
			background = Color.BLACK;
			ballColor = Color.MAGENTA;
			brickColor[1] = Color.GREEN;
			brickColor[2] = Color.BLUE;
			brickColor[3] = Color.RED;
			menuButton = Color.RED;
			menuButtonOn = Color.WHITE;
			border = Color.RED;
			break;
		default:
			break;
		}
		brickColor[0] = background;
	}
}