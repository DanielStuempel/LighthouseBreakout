package stuff;

import javax.swing.JFrame;

public class Main {
	private static int frameRate = 50;
	
	private static int length = 28 * 14 * 3;
	
	public static void main(String[] args) {
		Style.theme = Style.Theme.DARK;
		byte[] data = new byte[length];
		Level level = Level.buildLevel(0);
		
		Display display = new Display("raster", "resizable", "fps");
		Thread displayThread = new Thread(display, "displayThread");
		displayThread.start();
		
		Engine engine = new Engine(level, data);
		Thread gameEngineThread = new Thread(engine, "gameEngineThread");
		gameEngineThread.start();
		
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(display.getSize());
		System.out.println(display.getSize());
		System.out.println(frame.getSize());
		frame.setVisible(true);
		frame.add(display);
		frame.setSize(display.getPreferredSize());
		
		while(true) {
			//TODO: improve
			sleep(1000 / frameRate);
//			display.setSize(frame.getSize());
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