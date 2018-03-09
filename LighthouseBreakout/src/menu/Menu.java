package menu;

import java.awt.CardLayout;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import stuff.Style;
import stuff.Window;

public abstract class Menu extends JPanel {
	LinkedList<MenuButton> btns;
	LinkedList<MenuLabel> lbls;
	
	public Menu(CardLayout layout, Window.MainPanel contentPane, String name) {
		super();
		btns = new LinkedList<>();
		lbls = new LinkedList<>();
		
		layout.addLayoutComponent(this, name);
		contentPane.add(this);
		
		load();
	}

	public void add(MenuButton b) {
		super.add(b);
		btns.add(b);
	}
	
	public void add(MenuLabel l) {
		super.add(l);
		lbls.add(l);
	}
	
	private void load() {
		setBackground(Style.background);
		setBorder(BorderFactory.createLineBorder(Style.border, 7));
	}
	
	public void reload() {
		load();

		for (MenuButton b : btns)
			b.reload();
		for (MenuLabel l : lbls)
			l.reload();
	}
}