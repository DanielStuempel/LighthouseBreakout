package stuff;

import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

public class MainWindow extends JFrame {
	
	Menu menu;
	Display display;

	public MainWindow(Menu menu, Display display, SimplePaddel paddel) {
		this.menu = menu;
		this.display = display;
		init(paddel);
	}

	private void init(SimplePaddel paddel) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				int keyCode = arg0.getKeyCode();
				if (keyCode == Settings.Keys.PADDEL_LEFT.keyCode)
					paddel.vel = -1;
				else if (keyCode == Settings.Keys.PADDEL_RIGHT.keyCode)
					paddel.vel = 1;
				else if (keyCode == Settings.Keys.SWITCH_FPS_DISPLAY.keyCode)
					Settings.SHOW_FPS_ON_DISPLAY ^= true;
				else if (keyCode == Settings.Keys.PAUSE_GAME.keyCode)
					Settings.GAME_RUNNING ^= true;
				else if (keyCode == Settings.Keys.SHOW_MENU.keyCode) {
					switchView();
				}
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				int keyCode = arg0.getKeyCode();
				if (keyCode == Settings.Keys.PADDEL_LEFT.keyCode && paddel.vel == -1)
					paddel.vel = 0;
				else if (keyCode == Settings.Keys.PADDEL_RIGHT.keyCode && paddel.vel == 1)
					paddel.vel = 0;
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
			}
		});
		
		addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent arg0) {
			}

			@Override
			public void focusLost(FocusEvent arg0) {
				requestFocus();
			}
		});
		menu = new Menu();
		MenuButton btn_start = new MenuButton("START") {
			@Override
			public void onClick(ActionEvent e) {
				switchView();
			}
		};
		MenuButton btn_options = new MenuButton("OPTIONS") {
			@Override
			public void onClick(ActionEvent e) {
				System.out.println("options");
			}
		};
		MenuButton btn_style = new MenuButton("STYLE") {
			@Override
			public void onClick(ActionEvent e) {
				System.out.println("style");
			}
		};
		menu.add(btn_start);
		menu.add(btn_options);
		menu.add(btn_style);

		setSize(display.getPreferredSize());
		setLayout(null);

		setContentPane(menu);

		setVisible(true);
	}

	private void switchView() {
		if (Settings.MENU_VIEW ^= true) {
			Settings.GAME_RUNNING = false;
			setContentPane(menu);
		} else {
			setContentPane(display);
			Settings.GAME_RUNNING = true;
		}
		validate();
	}

}
