package stuff;

import java.awt.CardLayout;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.net.URL;
import java.util.LinkedList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import controller.Engine;
import menu.ControlMenu;
import menu.EndScreen;
import menu.MainMenu;
import menu.Menu;
import view.Display;

public class Window extends JFrame {

	private MainMenu menu;
	private EndScreen endScreen;
	private ControlMenu controlMenu;
	private Display display;

	CardLayout layout;
	MainPanel contentPane;

	private Engine engine;

	public class MainPanel extends JPanel {
		private LinkedList<Menu> menus;

		private MainPanel(CardLayout l) {
			super(l);
			menus = new LinkedList<>();
		}

		public void add(Menu m) {
			super.add(m);
			menus.add(m);
		}

		public void reload() {
			for (Menu m : menus)
				m.reload();
		}
	}

	public Window(Display display, Engine engine) {
		this.display = display;
		this.engine = engine;
		init();
	}

	private void init() {
		layout = new CardLayout();
		contentPane = new MainPanel(layout);
		setContentPane(contentPane);

		URL iconURL = getClass().getResource("/icon.png");
		ImageIcon icon = new ImageIcon(iconURL);
		setIconImage(icon.getImage());

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Settings.MOUSE_CONTROL = true;
			}
		});

		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				if (Settings.MOUSE_CONTROL && !Settings.HAX_ON) {
					int pos = e.getX() * 28 / display.getWidth() - (int) engine.getPaddel().getSize().getX() / 2;
					engine.movePaddel((int) (pos - engine.getPaddel().getPosition().getX()));
				}
			}
		});

		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				int keyCode = arg0.getKeyCode();
				if (keyCode == Settings.Keys.PADDEL_LEFT.keyCode) {
					Settings.MOUSE_CONTROL = false;
					engine.movePaddel(-1);
				} else if (keyCode == Settings.Keys.PADDEL_RIGHT.keyCode) {
					Settings.MOUSE_CONTROL = false;
					engine.movePaddel(1);
				} else if (keyCode == Settings.Keys.SWITCH_FPS_DISPLAY.keyCode)
					Settings.SHOW_FPS_ON_DISPLAY ^= true;
				else if (keyCode == Settings.Keys.PAUSE_GAME.keyCode)
					if (engine.isPaused())
						engine.unpause();
					else
						engine.pause();
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
			}
		});

		addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				requestFocus();
			}
		});
		
		engine.addEventGameListener(new Engine.GameEventListener() {
			@Override
			public void gameWon() {
				endScreen.reload();
				layout.show(contentPane, "endScreen");
			}
			@Override
			public void gameLost() {
				endScreen.reload();
				layout.show(contentPane, "endScreen");
			}
		});

		setSize(display.getPreferredSize());

		menu = new MainMenu(layout, contentPane);
		endScreen = new EndScreen(layout, contentPane);
		controlMenu = new ControlMenu(layout, contentPane);

		contentPane.add(endScreen);
		contentPane.add(display);

		layout.addLayoutComponent(endScreen, "endScreen");
		layout.addLayoutComponent(menu, "menu");
		layout.addLayoutComponent(display, "display");
		layout.addLayoutComponent(controlMenu, "controlMenu");
		setVisible(true);
	}

	public void showEndScreen() {
		layout.show(contentPane, "endScreen");
	}

	private void switchView() {
		if (Settings.MENU_SHOWN ^= true) {
			engine.pause();
			layout.show(contentPane, "menu");
		} else {
			layout.show(contentPane, "display");
		}
		validate();
	}
}