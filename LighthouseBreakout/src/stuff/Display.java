package stuff;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.util.HashSet;

import javax.swing.JPanel;

public class Display extends JPanel implements Runnable {
	private int frameTime = 20_000_000;
	
	private Dimension size = new Dimension(28, 14);
	private Dimension scale = new Dimension (30, 50);
	
	private volatile byte[] data = new byte[size.width * size.height * 3];
	private volatile byte[] state = new byte[size.width * size.height * 3];
	
	private volatile boolean state_changed;
	
	HashSet<String> args = new HashSet<String>();
	
	private double fps = 0;
	
//	Graphics g;
	
//	Insets insets;
//	Dimension offset;
	
	public Display(String... args) {
		super();
		parseArguments(args);
	}
	
	@Override
	public void run() {
		init();
		main(-1);
	}
	
	public void init() {
		setPreferredSize(new Dimension(size.width * scale.width, size.height * scale.height));
		setBackground(Style.theme.background);
	}
	
	public void main(int frames) {
		long
		 startTime = System.nanoTime(),
		 lastFrame = 0,
		 frameCount = 0;
		do {
			//TODO: fix inconsistent frame rates due to imprecise nanoTime
//			long wait = frameTime - System.nanoTime() % frameTime;
//			System.out.println(wait);
//			sleep(wait);
			
			while (frameCount * frameTime > System.nanoTime() - startTime) {
//				//TODO: other stuff to do while display is waiting
//				if (!args.contains("rescalable")) {
//					if (getWidth() != getHeight() * wSize.height / wSize.width) {
//						setSize(getInnerHeight() * wSize.height / wSize.width, getInnerHeight());
//					}
//				}
			}
			frameCount++;
			
			long time = System.nanoTime();
			fps = 1_000_000_000d / (time - lastFrame);
			lastFrame = time;
			
			if (!state_changed)
				continue;
			state_changed = false;
			
			//TODO: improve
			state = data.clone();
			
			repaint();
		} while (frames == -1 || --frames > 0);
	}
	
	private void parseArguments(String[] args) {
		for (String s : args)
			this.args.add(s);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		for (int p = 0, y = 0; y < size.height; y++) {
			for (int x = 0; x < size.width; x++) {
				Color c = new Color(
						state[p++] & 0xff,
						state[p++] & 0xff,
						state[p++] & 0xff);
				//TODO: only draw changes
				drawRect(g, x, y, c);
			}
		}
		if (args.contains("fps")) {
			g.setColor(Color.WHITE);
			g.drawString(
					"fps:" + fps,
					0,
					getHeight());
		}
	}
	
	private void drawRect(Graphics g, int x, int y, Color c) {
		double
		 width = getWidth() / size.getWidth(),
		 height = getHeight() / size.getHeight();
		Point offset = getLocation();
		g.setColor(c);
		g.fillRect(
				offset.x + (int) (x * width),
				offset.y + (int) (y * height),
				(int) width + 1,
				(int) height + 1);
		if (args.contains("raster")) {
			g.setColor(Style.theme.background);
			g.drawRect(
					offset.x + (int) (x * width),
					offset.y + (int) (y * height),
					(int) width + 1,
					(int) height + 1);
		}
	}
	
	public void send(byte[] data) {
		//TODO: validation
		if (this.data.length != data.length)
			throw new IllegalArgumentException();
		this.data = data.clone();
		state_changed = true;
	}
	
	@SuppressWarnings("unused")
	private void sleep(long nanos) {
		try {
			Thread.sleep(
					(long) Math.floor(nanos / 1_000_000d),
					(int) (nanos % 1_000_000));
		} catch (Exception e) { }
	}
}