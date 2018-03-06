package stuff;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Timer;

import javax.swing.JFrame;

public class Main {
	public static Timer systemTimer = new Timer(true);

	private static HashSet<String> args = new HashSet<String>();

	public static void main(String[] args) {
		parseArguments(args);

		Style.loadTheme(Settings.THEME);

		LinkedList<Animation> eventList = new LinkedList<>();
		byte[] data = new byte[28 * 14 * 3];

		Level level = new Level(Map.CAU);

		Display display = new Display();
		Thread displayThread = new Thread(display, "displayThread");
		displayThread.start();
		
		SimpleEngine engine = new SimpleEngine(level, eventList);
//		Engine engine = new Engine(display, level);
		Thread gameEngineThread = new Thread(engine, "gameEngineThread");
		gameEngineThread.start();
		
		Output output = new Output(engine, display.getWriter(), data, level, eventList);

		Bot bot = new Bot(engine);
		Thread botThread = new Thread(bot, "botThread");
		botThread.start();
		
		JFrame window = new Window(display, engine);
		window.setLocationRelativeTo(null);
		window.requestFocus();
		
		output.run();
	}
	
	private static void parseArguments(String[] args) {
		for (String s : args)
			Main.args.add(s);
	}
}