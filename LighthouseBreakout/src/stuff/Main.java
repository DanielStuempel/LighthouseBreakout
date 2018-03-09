package stuff;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.io.InputStream;
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
		
		try {
			InputStream r = Thread.currentThread().getContextClassLoader().getResourceAsStream("DS-DIGIB.ttf");
			Font f = Font.createFont(Font.TRUETYPE_FONT, r).deriveFont(Style.fontStyle, Style.fontSize);
			GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(f);
			Style.customFont = f;
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if (Style.customFont == null)
			Style.customFont = Style.defaultFont;
		
		if (Settings.CUSTOM_FONT)
			Style.font = Style.customFont;
		else
			Style.font = Style.defaultFont;
		
		LinkedList<Animation> eventList = new LinkedList<>();
		byte[] data = new byte[28 * 14 * 3];

		Level level = new Level(Map.newCAU);

		Display display = new Display();
		Thread displayThread = new Thread(display, "displayThread");
		displayThread.start();
		
//		SimpleEngine engine = new SimpleEngine(level, eventList);
		Engine engine = new Engine(display.getInput());
		Thread gameEngineThread = new Thread(engine, "gameEngineThread");
		gameEngineThread.start();
		
//		Output output = new Output(engine, display.getWriter(), data, level, eventList);
		
		Bot bot = new Bot(engine);
		Thread botThread = new Thread(bot, "botThread");
		botThread.start();
		
		JFrame window = new Window(display, engine);
		window.setLocationRelativeTo(null);
		window.requestFocus();
		
//		output.run();
	}
	
	private static void parseArguments(String[] args) {
		for (String s : args)
			Main.args.add(s);
	}
}