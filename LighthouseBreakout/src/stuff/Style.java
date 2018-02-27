package stuff;

import java.awt.Color;

public class Style {
	public static Theme theme = Theme.DEFAULT;
	
	public static enum Theme {
		ROOT(null),
		DARK(ROOT),
		LIGHT(ROOT);
		
		public static final Theme DEFAULT = DARK;
		
		public Color background;
		
		private Theme(Theme parent) {
			background = new Color(0, 0, 0);
		}
	}
}