package stuff;

public class Main {
	static int length = 28 * 14 * 3;
	
	public static void main(String[] args) {
		
		
		Style.theme = Style.Theme.DARK;
		byte[] data = new byte[length];
		Display display = new Display("raster", "resizable", "fps");
		Thread displayThread = new Thread(display);
		displayThread.start();
		
		Engine engine = new Engine(data);
		Thread engineThread = new Thread(engine);
		engineThread.start();
		
		while(true) {
			sleep(20);
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