package menu;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import stuff.Style;

public class MenuLabel extends JLabel {
	private int size;
	public MenuLabel(String text, int size) {
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
