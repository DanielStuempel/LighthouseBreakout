package stuff;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

public abstract class MenuButton extends JButton {
	public MenuButton() {
		super();
		init();
	}
	
	public MenuButton(String text) {
		super(text);
		setName(text);
		init();
	}
	
	private void init() {
		setFont(new Font("DS-Digital", Font.BOLD, 80));
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
