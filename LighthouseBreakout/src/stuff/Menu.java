package stuff;

import java.awt.CardLayout;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public abstract class Menu extends JPanel {
	private CardLayout layout;
	private JPanel contentPane;
	
	public Menu(CardLayout layout, JPanel contentPane, String name) {
		super();
		this.layout = layout;
		this.contentPane = contentPane;
		layout.addLayoutComponent(this, name);
		contentPane.add(this);

		setBackground(Style.background);

		setBorder(BorderFactory.createLineBorder(Style.border, 7));
	}
	
//	public void addMenu(Component comp, String name) {
//		layout.addLayoutComponent(comp, name);
//		contentPane.add(comp);
//	}
	
	@Override
	public void repaint() {
		super.repaint();
		setBackground(Style.background);
	}
}