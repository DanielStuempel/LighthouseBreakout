package stuff;

//testing...

public class Main {
	static int test;
	static int length = 28 * 14 * 3;

	public static void main(String[] args) {
		Style.theme = Style.Theme.DARK;
		
		Display display = new Display();
		DisplayThread displayThread = new DisplayThread(display);
		displayThread.start("raster resizable fps");
		
		byte r, g, b;
		r = g = b = 0;
		b = -127;
		
		while(true) {
			sleep(20);
			byte[] data = new byte[length];
			for (int p = 0; p < length;) {
				data[p++] = r;
				data[p++] = g;
				data[p++] = b;
			}
			g += 5;
			display.send(data);
		}
	}
	
	private static void sleep(int i) {
		try {
			Thread.sleep(i);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}