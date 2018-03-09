package stuff;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.InputStream;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class CoolLabel extends JLabel {
	Font font = null;
	public CoolLabel(String text, int size) {
		super(text, SwingConstants.CENTER);
		if (Settings.CUSTOM_FONT) {
			GraphicsEnvironment ge = null;
			try {
				ClassLoader classloader = Thread.currentThread().getContextClassLoader();
				InputStream f = classloader.getResourceAsStream("DS-DIGIB.ttf");
				ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
				font = Font.createFont(Font.TRUETYPE_FONT, f).deriveFont(Font.BOLD, size);
				ge.registerFont(font);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			font = new Font("Comic Sans MS", Font.BOLD, size);
		}
		setFont(font);
		setForeground(Style.menuButton);
	}
}
