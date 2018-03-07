package stuff;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class CoolLabel extends JLabel {

	public CoolLabel(String text, int size) {
		super(text, SwingConstants.CENTER);
		setFont(new Font("DS-Digital", Font.BOLD, size));
		setForeground(Style.menuButton);
	}
}
