package stuff;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class CoolLabel extends JLabel {
	private int size;
	public CoolLabel(String text, int size) {
		super(text, SwingConstants.CENTER);
		this.size = size;
		setFont(Style.font.deriveFont((float) size));
		setForeground(Style.menuButton);
	}
	
	public void reload() {
		setFont(Style.font.deriveFont((float) size));
		setForeground(Style.menuButton);
	}
}
