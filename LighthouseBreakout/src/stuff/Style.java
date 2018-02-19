package stuff;

import java.awt.Color;

public class Style {
	public static Theme theme = Theme.DEFAULT;
	
	public static enum Theme {
		DARK(Color.BLACK),
		LIGHT(Color.GRAY);
		
		public static final Theme DEFAULT = DARK;
		
		public Color background = Color.BLACK;
		
		private Theme(Color bg) {
			this.background = bg;
		}
	}
}