package stuff;

public class Engine implements Runnable {
	private byte[] data;
	
	public Engine(byte[] data) {
		this.data = data;
	}
	@Override
	public void run() {
		int p = 0;
		Level l = new Level(2);
		Brick[][] m = l.getMap();
		
		while(true) {
			Main.sleep(20);
			
			//byte[] data = new byte[28 * 14 * 3];
			
			m[p % 28][p / 28].hit();
			
			for (int q = 0; q < data.length; q++) {
				int t = m[q / 3 % 28][q / 3 / 28].getType();
				data[q] = (byte) (t == 0 ? 0 : 100 + t * 10);
			}
			
			data[p * 3] = -1;
			data[p * 3 + 1] = 0;
			data[p * 3 + 2] = 0;
			
			//p = (int) (Math.random() * 28 * 14);
			p = Ball.getPos();
			
			//System.out.println(p);
			//display.send(data);
		}
		
	}

}
