package stuff;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Menu extends JPanel {
	private static LinkedList<JButton> button = new LinkedList<>();
	private static boolean init = true;
	private int width;
	private int height;
	private JButton start = new JButton("START");
	private JButton options = new JButton("OPTIONS");
	private JButton style = new JButton("STYLE");
	static Menu m;


	Menu() {
		this.m = this;

		setBackground(Style.background);
		setBorder(BorderFactory.createLineBorder(Style.border, 7));
		setLayout(null);
		System.out.println(this.getSize());

		start.setBounds(width / 2 - 100, height / 4, 200, 50);
		options.setBounds(width / 2 - 100, height / 4 + 100, 200, 50);
		style.setBounds(width / 2 - 100, height / 4 + 200, 200, 50);

		button.add(start);
		button.add(options);
		button.add(style);

		btnstyle();
		init = false;
		add(start);
		add(options);
		add(style);
		this.addComponentListener(new ComponentListener() {

			@Override
			public void componentHidden(ComponentEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void componentMoved(ComponentEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void componentResized(ComponentEvent arg0) {
				start.setBounds(m.getWidth() / 2 - 250, m.getHeight() / 4, 500, 50);
				options.setBounds(m.getWidth() / 2 - 250, m.getHeight() / 4 + 100, 500, 50);
				style.setBounds(m.getWidth() / 2 - 250, m.getHeight() / 4 + 200, 500, 50);
			}

			@Override
			public void componentShown(ComponentEvent arg0) {
				// TODO Auto-generated method stub

			}

		});

	}
	private void clicked(JButton b){
		switch(b.getText()){
		case "START":
			Settings.GAME_RUNNING = true;
			break;
		}
	}
	private static void btnstyle() {
		for (JButton b : button) {
			try {
				if (init) {
					b.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							if (e.getSource().equals(b)) {
								m.clicked(b);
							}
						}
					});
					b.addMouseListener(new java.awt.event.MouseAdapter() {

						@Override
						public void mouseEntered(java.awt.event.MouseEvent e) {
							if (e.getSource().equals(b)) {
								b.setForeground(Style.menuButtonOn);
							}
						}

						public void mouseExited(java.awt.event.MouseEvent e) {
							if (e.getSource().equals(b)) {
								b.setForeground(Style.menuButton);

							}
						}
					});
				}
				b.setFont(new Font("DS-Digital", Font.BOLD, 80));
				b.setForeground(Style.menuButton);
				b.setBorderPainted(false);
				b.setBackground(Style.background);
				b.setFocusPainted(false);
				b.setContentAreaFilled(false);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
