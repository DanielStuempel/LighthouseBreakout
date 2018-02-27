package stuff;

public class Main {
	private static int frameRate = 50;
	
	private static int length = 28 * 14 * 3;
	
	public static void main(String[] args) {
		Style.theme = Style.Theme.DARK;
		byte[] data = new byte[length];
		Level level = Level.buildLevel(2);
		
		Display display = new Display("raster", "resizable", "fps");
		Thread displayThread = new Thread(display, "displayThread");
		displayThread.start();
		
		Engine engine = new Engine(level, data);
		Thread gameEngineThread = new Thread(engine, "gameEngineThread");
		gameEngineThread.start();
		
		while(true) {
			//TODO: improve
			sleep(1000 / frameRate);
			display.send(data);
		}
	}
	
	public static void sleep(int i) {
		try {
			Thread.sleep(i);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}