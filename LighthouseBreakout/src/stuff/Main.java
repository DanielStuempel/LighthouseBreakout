package stuff;

import java.util.Timer;

import javax.swing.JFrame;

public class Main {
	private static int frameRate = 50;
	
	private static int length = 28 * 14 * 3;
	
	//TODO: maybe use separate timers as it's calls appear to be blocking
	public static Timer systemTimer = new Timer(true);
	
	public static void main(String[] args) {
		Style.theme = Style.Theme.DARK;
		byte[] data = new byte[length];
		Level level = Level.buildLevel(1);
		Ball ball = new Ball(13, 12);
		
		Display display = new Display("raster", "fps");
		Thread displayThread = new Thread(display, "displayThread");
		displayThread.start();
		
		Engine engine = new Engine(level, ball);
		Thread gameEngineThread = new Thread(engine, "gameEngineThread");
		gameEngineThread.start();
		
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(display.getSize());
		frame.setVisible(true);
		frame.add(display);
		frame.setSize(display.getPreferredSize());
		
		while(true) {
			//TODO: improve
			sleep(1000 / frameRate);
			for (int q = 0, y = 0; y < level.size.height; y++) {
				for (int x = 0; x < level.size.width; x++) {
					// draw block
					Brick b = level.get(x, y);
					//Color Brick
					//data[q++] = data[q++] = data[q++] = (byte) (b == null ? 0 : 100 + b.getType() * 10);
					data[q++] = (byte) (b == null ? 0 : Style.theme.brickStyle[b.getType()][0]);
					data[q++] = (byte) (b == null ? 0 : Style.theme.brickStyle[b.getType()][1]);
					data[q++] = (byte) (b == null ? 0 : Style.theme.brickStyle[b.getType()][2]);

				}
			}

			// draw ball
			int p = (ball.pos.x + ball.pos.y * 28) * 3;
			data[p++] = (byte) Style.theme.ballColor.getRed();
			data[p++] = (byte) Style.theme.ballColor.getGreen();
			data[p] = (byte) Style.theme.ballColor.getBlue();

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