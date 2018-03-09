package stuff;

import java.awt.CardLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public abstract class Menu extends JPanel {
	public Menu(CardLayout layout, JPanel contentPane, String name) {
		super();
		layout.addLayoutComponent(this, name);
		contentPane.add(this);

		setBackground(Style.background);

		setBorder(BorderFactory.createLineBorder(Style.border, 7));
	}
	
	@Override
	public void repaint() {
		super.repaint();
		setBackground(Style.background);
	}
}