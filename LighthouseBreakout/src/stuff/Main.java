package stuff;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import java.util.Timer;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class Main {
	public static Timer timer = new Timer();

	public static void main(String[] args) {
		Style.loadTheme(Style.Theme.COLORFUL);
		
		LinkedList<Animation> eventList = new LinkedList<>();		
		final int KeyCodePaddelLeft = KeyEvent.VK_LEFT;
		final int KeyCodePaddelRight = KeyEvent.VK_RIGHT;
		
		byte[] data = new byte[28 * 14 * 3];
		
		Level level = new Level(Map.CAU);
		Paddel paddel = new Paddel(10, 8);
		Ball ball = new Ball(13, 11);
		
		Display display = new Display("raster", "fps");
		Thread displayThread = new Thread(display, "displayThread");
		displayThread.start();
		
		Output output = new Output(display, data, level, paddel, ball, eventList);
		
		SimpleEngine engine = new SimpleEngine(level, paddel, ball, eventList);
		Thread gameEngineThread = new Thread(engine, "gameEngineThread");
		gameEngineThread.start();
		
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(display.getPreferredSize());
		window.setSize(600, 450);
		window.add(display);
		window.setVisible(true);
		
		JLabel frameRateDisplay = new JLabel();
		frameRateDisplay.setForeground(Color.WHITE);
		
		window.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				switch (arg0.getKeyCode()) {
				case KeyCodePaddelLeft:
					paddel.vel = -1;
					break;
				case KeyCodePaddelRight:
					paddel.vel = 1;
					break;
				}
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				switch (arg0.getKeyCode()) {
				case KeyCodePaddelLeft:
					if (paddel.vel == -1) paddel.vel = 0;
					break;
				case KeyCodePaddelRight:
					if (paddel.vel == 1) paddel.vel = 0;
					break;
				}
			}

			@Override
			public void keyTyped(KeyEvent arg0) { }
		});
		
		//main thread becomes output thread
		output.run();
	}
}