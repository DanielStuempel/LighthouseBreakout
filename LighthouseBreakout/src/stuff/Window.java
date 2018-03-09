package stuff;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Window extends JFrame {

	private MainMenu menu;
	private EndScreen endScreen;
	private ControlMenu2 controlMenu;
	private Display display;
	
	CardLayout layout;
	JPanel contentPane;

	private Engine engine;

	public Window(Display display, Engine engine) {
		this.display = display;
		this.engine = engine;
		init();
	}

	private void init() {
		layout = new CardLayout();
		contentPane = new JPanel(layout);
		setContentPane(contentPane);
		
		Paddel paddel = engine.getPaddel();

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
					int pos = e.getX() * 28 / display.getWidth() - (int) paddel.getSize().getX() / 2;
					engine.changePaddelPosition((int) (pos - engine.getPaddel().getPosition().getX()));
				}
			}
		});

		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				int keyCode = arg0.getKeyCode();
				if (keyCode == Settings.Keys.PADDEL_LEFT.keyCode) {
					Settings.MOUSE_CONTROL = false;
					engine.changePaddelPosition(-1);
				} else if (keyCode == Settings.Keys.PADDEL_RIGHT.keyCode) {
					Settings.MOUSE_CONTROL = false;
					engine.changePaddelPosition(1);
				} else if (keyCode == Settings.Keys.SWITCH_FPS_DISPLAY.keyCode)
					Settings.SHOW_FPS_ON_DISPLAY ^= true;
				else if (keyCode == Settings.Keys.PAUSE_GAME.keyCode)
					Settings.GAME_PAUSED ^= true;
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
		
		menu = new MainMenu(layout, contentPane);
		endScreen = new EndScreen(layout, contentPane);
		controlMenu = new ControlMenu2(layout, contentPane);
		
		
		setSize(display.getPreferredSize());
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
			Settings.GAME_PAUSED = true;
			layout.show(contentPane, "menu");
		} else {
			layout.show(contentPane, "display");
		}
		validate();
	}
}