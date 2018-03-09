package menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

import stuff.Style;

public abstract class MenuButton extends JButton {
	int fontSize;
	
	public MenuButton(String text) {
		this(text, Style.font.getSize());
	}

	public MenuButton(String text, int fontSize) {
		super(text);
		this.fontSize = fontSize;
		init();
	}

	private void init() {
		setFocusPainted(false);
		setBorderPainted(false);
		setContentAreaFilled(false);
		
		load();

		addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onClick(e);
			}
		});

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				setForeground(Style.menuButtonOn);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				setForeground(Style.menuButton);
			}
		});
	}

	public abstract void onClick(ActionEvent e);

	private void load() {
		setBackground(Style.background);
		setForeground(Style.menuButton);
		setFont(Style.font.deriveFont((float) fontSize));
	}

	public void reload() {
		load();
	}
}
