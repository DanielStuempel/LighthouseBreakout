package stuff;

import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class Window extends JFrame {

	Menu menu = new Menu();
	OptionsMenu optionsMenu = new OptionsMenu();
	Display display;
	LinkedList<MenuButton> button = new LinkedList<>();
	public static Window w;

	SimpleEngine engine;

	public Window(Display display, SimpleEngine engine) {
		this.display = display;
		this.engine = engine;
		this.w = this;
		init();
	}

	private void init() {
		SimplePaddel paddel = engine.getPaddel();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseMoved(MouseEvent e) {
				if (Settings.MOUSE_CONTROL && !Settings.HAX_ON) {
					int pos = e.getX() * 28 / display.getWidth() - (int) paddel.getSize().getX() / 2;
					engine.changePaddelPosition((int) (pos - engine.getPaddel().getPosition().getX()));
				}
			}

			@Override
			public void mouseDragged(MouseEvent e) { }
		});

		addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				int keyCode = arg0.getKeyCode();
				if (keyCode == Settings.Keys.PADDEL_LEFT.keyCode && !Settings.MOUSE_CONTROL)
					engine.changePaddelPosition(-1);
				else if (keyCode == Settings.Keys.PADDEL_RIGHT.keyCode && !Settings.MOUSE_CONTROL)
					engine.changePaddelPosition(1);
				else if (keyCode == Settings.Keys.SWITCH_FPS_DISPLAY.keyCode)
					Settings.SHOW_FPS_ON_DISPLAY ^= true;
				else if (keyCode == Settings.Keys.PAUSE_GAME.keyCode)
					Settings.GAME_RUNNING ^= true;
				else if (keyCode == Settings.Keys.SHOW_MENU.keyCode)
					switchView();
				else if (keyCode == Settings.Keys.HAX_SWITCH.keyCode)
					Settings.HAX_ON ^= true;
				else if (keyCode == Settings.Keys.CONTROL_SWITCH.keyCode)
					Settings.MOUSE_CONTROL ^= true;
				else if (keyCode == Settings.Keys.ENGINE_RESET.keyCode)
					engine.reset();
				else if (keyCode == Settings.Keys.DEBUG.keyCode)
					engine.debug();
				else if (keyCode == Settings.Keys.GAME_START.keyCode)
					Settings.GAME_RUNNING = true;

			}

			@Override
			public void keyReleased(KeyEvent arg0) {
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

		// MENU
		button.add(new MenuButton("START") {
			@Override
			public void onClick(ActionEvent e) {
				switchView();
			}
		});
		button.add( new MenuButton("OPTIONS") {
			@Override
			public void onClick(ActionEvent e) {
				switchMenu();
			}
		});
		button.add( new MenuButton("SCORES") {
			@Override
			public void onClick(ActionEvent e) {
				System.out.println("score");
			}
		});

		// OPTIONS MENU
		button.add( new MenuButton("STYLE: " + Settings.THEME.toString()) {
			@Override
			public void onClick(ActionEvent e) {
				switchTheme();
			}
		});
		button.add( new MenuButton("Controls") {
			@Override
			public void onClick(ActionEvent e) {
				switchControls();
			}
		});
		button.add( new MenuButton("BACK") {
			@Override
			public void onClick(ActionEvent e) {
				System.out.println("back");
				setContentPane(menu);
				Settings.OPTIONS_MENU = false;
				validate();
			}
		});

		menu.add(new JLabel(" "));
		menu.add(button.get(0));
		menu.add(button.get(1));
		menu.add(button.get(2));

		optionsMenu.add(new JLabel(""));
		optionsMenu.add(button.get(3));
		optionsMenu.add(button.get(4));
		optionsMenu.add(button.get(5));

		setSize(display.getPreferredSize());
		setLayout(null);

		switchView();

		setVisible(true);
	}
	
	private void switchTheme() {
		Style.loadTheme(Style.next());
		menu.reload();
		optionsMenu.reload();
		for (MenuButton b : button) {
			b.reload();
		}
		button.get(3).setText("STYLE: " + Settings.THEME.toString());
		validate();
	}

	private void switchMenu() {
		if (Settings.OPTIONS_MENU ^= true) {
			setContentPane(optionsMenu);
		}
		validate();
	}


	private void switchView() {
		if (!(Settings.MENU_VIEW ^= true)) {
			Settings.GAME_RUNNING = false;
			setContentPane(menu);
		} else {
			setContentPane(display);
		}
		validate();
	}

	private void switchControls() {
		ControlMenu controlMenu = new ControlMenu();
		setContentPane(controlMenu);
		controlMenu.add(button.get(5));
		validate();
	}
	public void Scoreboard() {
		Settings.GAME_RUNNING = false;
		EndScreen end = new EndScreen();
		setContentPane(end);
		end.add(new MenuButton("AGAIN") {
			
			@Override
			public void onClick(ActionEvent e) {
				Settings.SCORE = 0;
				engine.reset();
				setContentPane(display);
				validate();
			}
		});
		validate();
		
	}
}