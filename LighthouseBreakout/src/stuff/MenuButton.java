package stuff;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JButton;

public abstract class MenuButton extends JButton {
	int size = 80;
	public MenuButton() {
		super();
		init();
	}

	public MenuButton(String text) {
		super(text);
		setName(text);
		init();
	}
	public MenuButton(String text, int size) {
		super(text);
		setName(text);
		this.size = size;
		init();
	}

	private void init() {
		if (Settings.FONT_CUSTOM) {
			GraphicsEnvironment ge = null;
			Font font = null;
			try {
				ClassLoader classloader = Thread.currentThread().getContextClassLoader();
				InputStream f = classloader.getResourceAsStream("DS-DIGIB.ttf");
				ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
				font = Font.createFont(Font.TRUETYPE_FONT, f).deriveFont(Font.BOLD, size);
				ge.registerFont(font);
			} catch (Exception e) {
				e.printStackTrace();
			}
			setFont(font);
		} else {
			setFont(new Font("Comic Sans MS", Font.BOLD, size));
		}
		setForeground(Style.menuButton);
		setBorderPainted(false);
		setBackground(Style.background);
		setFocusPainted(false);
		setContentAreaFilled(false);

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

	public void reload() {
		setBackground(Style.background);
		setForeground(Style.menuButton);

	}
}
