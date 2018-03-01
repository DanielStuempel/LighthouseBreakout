package stuff;

import java.awt.Color;
import java.util.Timer;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class Main {
	private static Timer timer = new Timer();

	public static void main(String[] args) {
		Style.loadTheme(Style.Theme.COLORFUL);
		
		byte[] data = new byte[28 * 14 * 3];
		
		Level level = Level.buildLevel(1);
		Ball ball = new Ball();
		
		Display display = new Display("raster");
		Thread displayThread = new Thread(display, "displayThread");
		displayThread.start();
		
		Engine engine = new Engine(level, ball);
		Thread gameEngineThread = new Thread(engine, "gameEngineThread");
		gameEngineThread.start();
		
		TickTimer frameRateTimer = new TickTimer() {
			@Override
			public void tick() {
				synchronized (display) {
					display.notify();
				}
			}
		};
		timer.schedule(frameRateTimer, 0, 20);
		
		TickTimer gameTickTimer = new TickTimer() {
			@Override
			public void tick() {
				synchronized (engine) {
					engine.notify();
				}
			}
		};
		timer.schedule(gameTickTimer, 0, 100);
		
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(display.getPreferredSize());
		window.setSize(600, 450);
		window.add(display);
		window.setVisible(true);
		
		JLabel frameRateDisplay = new JLabel();
		frameRateDisplay.setForeground(Color.WHITE);
		
		while(true) {
			//TODO: improve
			sleep(20);
			for (int q = 0, y = 0; y < level.size.height; y++) {
				for (int x = 0; x < level.size.width; x++) {
					// draw block
					Color c = Style.brickColor[level.get(x, y).getType()];
					//Color Brick
					//data[q++] = data[q++] = data[q++] = (byte) (b == null ? 0 : 100 + b.getType() * 10);
					data[q++] = (byte) c.getRed();
					data[q++] = (byte) c.getGreen();
					data[q++] = (byte) c.getBlue();

				}
			}

			// draw ball
			int p = (ball.pos.x + ball.pos.y * 28) * 3;
			data[p++] = (byte) Style.ballColor.getRed();
			data[p++] = (byte) Style.ballColor.getGreen();
			data[p] = (byte) Style.ballColor.getBlue();

			display.send(data);
			
			window.getGraphics().drawString("fps:" + frameRateTimer.getCurrentFPS(), 0, window.getHeight());
			
			frameRateDisplay.setText("fps:" + frameRateTimer.getCurrentFPS());
			
//			System.out.println(frameRateTimer.getCurrentFPS());
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