package stuff;

public class Main {
	static int length = 28 * 14 * 3;

	public static void main(String[] args) {
		Style.theme = Style.Theme.DARK;
		
		Display display = new Display("raster", "resizable", "fps");
		Thread displayThread = new Thread(display);
		displayThread.start();
		
		int p = 0;
		Level l = new Level();
		Brick[][] m = l.getMap();
		
		while(true) {
			sleep(20);
			byte[] data = new byte[28 * 14 * 3];
			
			m[p % 28][p / 28].hit();
			
			for (int q = 0; q < data.length; q++) {
				int t = m[q / 3 % 28][q / 3 / 28].getType();
				data[q] = (byte) (t == 0 ? 0 : 100 + t * 10);
			}
			
			data[p * 3] = -1;
			data[p * 3 + 1] = 0;
			data[p * 3 + 2] = 0;
			
			p = (int) (Math.random() * 28 * 14);

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