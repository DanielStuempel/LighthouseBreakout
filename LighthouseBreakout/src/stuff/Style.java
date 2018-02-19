package stuff;

import java.awt.Color;

public class Style {
	public final static Color BACKGROUND = Color.BLACK;
	
	public static enum Theme {
		DARK(Color.BLACK),
		LIGHT(Color.GRAY);
		
		public static final Theme DEFAULT = DARK;
		private static Theme current = DEFAULT;
		
		public static Theme getCurrent() {
			return current;
		}
		
		public static void setCurrent(Theme theme) {
			current = theme;
		}
		
		public Color background = Color.BLACK;
		
		private Theme(Color bg) {
			this.background = bg;
		}
	}
}