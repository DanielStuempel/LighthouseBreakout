package stuff;

import javax.swing.BorderFactory;

public class OptionsMenu extends Menu {
	public OptionsMenu() {
	}
	void reload(){
		setBackground(Style.background);
		setBorder(BorderFactory.createLineBorder(Style.border, 7));
	}
}
