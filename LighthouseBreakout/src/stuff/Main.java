package stuff;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Timer;

import javax.swing.JFrame;

public class Main {
	public static Timer systemTimer = new Timer();

	private static HashSet<String> args = new HashSet<String>();
	public static JFrame window = new MainWindow();
	
	private static Display display;
	private static Menu menu;
	
	public static void main(String[] args) {
		parseArguments(args);

		Style.loadTheme(Style.Theme.COLORFUL);

		LinkedList<Animation> eventList = new LinkedList<>();

		byte[] data = new byte[28 * 14 * 3];

		Level level = new Level(Map.FINAL);
		SimplePaddel paddel = new SimplePaddel(10, 8);
		SimpleBall ball = new SimpleBall(13, 11);

		display = new Display();
		Thread displayThread = new Thread(display, "displayThread");
		displayThread.start();
		
		Output output = new Output(display, data, level, paddel, ball, eventList);
		
		SimpleEngine engine = new SimpleEngine(level, paddel, ball, eventList);
		Thread gameEngineThread = new Thread(engine, "gameEngineThread");
		gameEngineThread.start();

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

		window.setSize(display.getPreferredSize());
		window.setLayout(null);
		
		window.setContentPane(menu);

		window.setVisible(true);
		
		window.addKeyListener(new KeyListener() {
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
			public void keyTyped(KeyEvent arg0) { }
		});

		window.requestFocus();
		
		output.run();
	}
	
	private static void switchView() {
		if (Settings.MENU_VIEW ^= true) {
			Settings.GAME_RUNNING = false;
			window.setContentPane(menu);
		} else {
			window.setContentPane(display);
			Settings.GAME_RUNNING = true;
		}
		window.validate();
	}

	private static void parseArguments(String[] args) {
		for (String s : args)
			Main.args.add(s);
	}
}