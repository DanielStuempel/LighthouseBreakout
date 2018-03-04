package stuff;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class Menu extends JPanel {
	Menu m = this;
	
	public Menu() {
		setBackground(Style.background);
		setBorder(BorderFactory.createLineBorder(Style.border, 7));
		setLayout(new GridLayout(5, 1));
	}
}