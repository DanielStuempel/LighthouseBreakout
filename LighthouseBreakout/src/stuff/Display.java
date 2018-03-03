package stuff;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public class Display extends JPanel implements Runnable {
	private Dimension size = new Dimension(28, 14);
	private Dimension scale = new Dimension (30, 50);
	
	private volatile byte[] state = new byte[size.width * size.height * 3];
	
	private volatile boolean state_changed;
	
	TickTimer frameRateTimer;
	
	public Display() {
		super();
	}
	
	@Override
	public void run() {
		init();
		try {
			while (true)
				main();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void init() {
		setPreferredSize(new Dimension(size.width * scale.width, size.height * scale.height));
		setBackground(Style.background);
		
		Display d = this;
		
		frameRateTimer = new TickTimer() {
			@Override
			public void tick() {
				synchronized (d) {
					d.notify();
				}
			}
		};
		Main.systemTimer.schedule(frameRateTimer, 0, Settings.FRAME_TICK_MS);
	}
	
	public synchronized void main() throws InterruptedException {
		this.wait();
		if (!state_changed)
			return;
			state_changed = false;
			repaint();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (int p = 0, y = 0; y < size.height; y++) {
			for (int x = 0; x < size.width; x++) {
				Color c = new Color(
						state[p++] & 0xff,
						state[p++] & 0xff,
						state[p++] & 0xff);
				drawRect(g, x, y, c);
			}
		}
		if (Settings.SHOW_FPS_ON_DISPLAY) {
			g.setColor(Color.WHITE);
			g.drawString("fps:" + frameRateTimer.getCurrentFPS(), 0, getHeight());
		}
	}
	
	private void drawRect(Graphics g, int x, int y, Color c) {
		double
		 width = getWidth() / size.getWidth(),
		 height = getHeight() / size.getHeight();
		g.setColor(c);
		g.fillRect(
				(int) (x * width),
				(int) (y * height),
				(int) width + 1,
				(int) height + 1);
		if (Settings.SHOW_RASTER_ON_DISPLAY) {
			g.setColor(Style.background);
			g.drawRect(
					(int) (x * width),
					(int) (y * height),
					(int) width + 1,
					(int) height + 1);
		}
	}
	
	public void send(byte[] data) {
		if (state.length != data.length)
			throw new IllegalArgumentException();
		state = data.clone();
		state_changed = true;
	}
}