package stuff;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Timer;

import javax.swing.JFrame;

public class Main {
	public static Timer systemTimer = new Timer();

	private static HashSet<String> args = new HashSet<String>();

	public static void main(String[] args) {
		parseArguments(args);

		Style.loadTheme(Style.Theme.COLORFUL);

		LinkedList<Animation> eventList = new LinkedList<>();

		byte[] data = new byte[28 * 14 * 3];

		Level level = new Level(Map.CAU);
		SimplePaddel paddel = new SimplePaddel(10, 8);
		SimpleBall ball = new SimpleBall(13, 11);

		Display display = new Display();
		Thread displayThread = new Thread(display, "displayThread");
		displayThread.start();

		SimpleEngine engine = new SimpleEngine(level, paddel, ball, eventList);
		Thread gameEngineThread = new Thread(engine, "gameEngineThread");
		gameEngineThread.start();

		Output output = new Output(display, data, level, paddel, ball, eventList);
		JFrame window = new MainWindow();

		Menu menu = new Menu();

		window.setSize(display.getPreferredSize());

		window.add(display);

		System.out.println(window.getComponentCount());

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
				else if (keyCode == Settings.Keys.SWITCH_GAME_RUNNING.keyCode)
					Settings.GAME_RUNNING ^= true;

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
		window.setVisible(true);

		output.run();

	}

	private static void parseArguments(String[] args) {
		for (String s : args)
			Main.args.add(s);
	}
}