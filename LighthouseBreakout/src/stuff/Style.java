package stuff;

import java.awt.Color;

public class Style {
	public static Theme theme = Theme.DEFAULT;

	public static enum Theme {
		ROOT(null), DARK(ROOT), LIGHT(ROOT);

		public static final Theme DEFAULT = DARK;

		public Color background;
		public byte[][] BrickStyle = new byte[10][3];
		public byte[] BallStyle = new byte[3];
		
		private Theme(Theme parent) {
			background = new Color(0, 0, 0);
			
			BrickStyle[1][0] = 0;
			BrickStyle[1][1] = -1;
			BrickStyle[1][2] = 0;
			
			BrickStyle[2][0] = -1;
			BrickStyle[2][1] = -1;
			BrickStyle[2][2] = 0;
			
			BrickStyle[3][0] = -1;
			BrickStyle[3][1] = 0;
			BrickStyle[3][2] = 0;
			
			
			BallStyle[0] = -1;
			BallStyle[1] = 0;
			BallStyle[2] = -1;
		}
	}
}