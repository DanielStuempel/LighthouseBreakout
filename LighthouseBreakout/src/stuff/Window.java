package stuff;

import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class Window extends JFrame {
	
	Menu menu = new Menu();
	OptionsMenu optionsMenu = new OptionsMenu(); 
	Display display;
	
	SimpleEngine engine;
	
	public Window(Display display, SimpleEngine engine) {
		this.display = display;
		this.engine = engine;
		init();
	}

	private void init() {
		SimplePaddel paddel = engine.getPaddel();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseMoved(MouseEvent e) {
				if (Settings.MOUSE_CONTROL && !Settings.HAX_ON) {
					int pos = e.getX()*28/display.getWidth();
					if (pos >= paddel.size/2 && pos <= 28-paddel.size/2 ) {
						paddel.pos = pos-paddel.size/2;
					}
				}
				
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
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
		
		//MENU 
		MenuButton btn_start = new MenuButton("START") {
			@Override
			public void onClick(ActionEvent e) {
				switchView();
			}
		};
		MenuButton btn_options = new MenuButton("OPTIONS") {
			@Override
			public void onClick(ActionEvent e) {
				switchMenu();
			}
		};
		MenuButton btn_score = new MenuButton("SCORES") {
			@Override
			public void onClick(ActionEvent e) {
				System.out.println("score");
			}
		};
		
		//OPTIONS MENU
		MenuButton btn_style = new MenuButton("STYLE: "+ Settings.THEME.toString()) {
			@Override
			public void onClick(ActionEvent e) {
				switchTheme();
			}
		};
		MenuButton btn_back = new MenuButton("BACK") {
			@Override
			public void onClick(ActionEvent e) {
				System.out.println("back");
				setContentPane(menu);
				Settings.OPTIONS_MENU = false;
				validate();
			}
		};
		
		menu.add(new JLabel(" "));
		menu.add(btn_start);
		menu.add(btn_options);
		menu.add(btn_score);
		
		optionsMenu.add(new JLabel(""));
		optionsMenu.add(btn_style);
		optionsMenu.add(btn_back);

		setSize(display.getPreferredSize());
		setLayout(null);

		switchView();

		setVisible(true);
	}
	
	private void switchTheme() {
		System.out.println("only one Theme available right now");
	}
	
	private void switchMenu() {
		if(Settings.OPTIONS_MENU ^= true) {
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
			Settings.GAME_RUNNING = true;
		}
		validate();
	}
}