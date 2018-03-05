package stuff;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Timer;

import javax.swing.JFrame;

public class Main {
	public static Timer systemTimer = new Timer();

	private static HashSet<String> args = new HashSet<String>();
	
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
		
		Bot bot = new Bot();
		Thread botThread = new Thread(bot, "botThread");
		bot.set(paddel,ball);
		botThread.start();
		
		Output output = new Output(display, data, level, paddel, ball, eventList);
		
//		SimpleEngine engine = new SimpleEngine(level, paddel, ball, eventList);
		Engine engine = new Engine(display);
		Thread gameEngineThread = new Thread(engine, "gameEngineThread");
		gameEngineThread.start();

		JFrame window = new MainWindow(menu, display, paddel);
		
		window.requestFocus();
		
//		output.run();
	}
	
	private static void parseArguments(String[] args) {
		for (String s : args)
			Main.args.add(s);
	}
}