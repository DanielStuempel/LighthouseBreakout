package stuff;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.util.HashSet;

import javax.swing.JFrame;

public class Display extends JFrame {
	private Dimension size = new Dimension(28, 14);
	private Dimension scale = new Dimension (20, 40);
	
	private volatile byte[] state = new byte[size.width * size.height * 3];
	
	private volatile boolean state_changed;
	
	HashSet<String> args = new HashSet<String>();
	
	Graphics g;
	
	Insets insets;
	Dimension offset;
	
	public Display() {
		super();
	}
	
	public void start(String[] args) {
		parseArguments(args);
		init();
		run();
	}
	
	public void main() {
		
	}
	
	public void init() {
		//TODO: make size a parameter
		setSize(size.width * scale.width, size.height * scale.height);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBackground(Style.theme.background);
		setVisible(true);
		insets = getInsets();
		offset = new Dimension(insets.right, insets.top);
		g = getGraphics();
		if (!args.contains("resizable"))
			setResizable(false);
		setSize(insets.right + getWidth() + insets.left, insets.top + getHeight() + insets.bottom);
	}
	
	public void run() {
		long
//		 startTime = System.nanoTime(),
		 lastFrame = 0;
		
		while (true) {
//			System.out.println(System.currentTimeMillis());
			long wait = 20_000_000 - System.nanoTime() % 20_000_000;
			System.out.println(wait);
			sleep(wait);
			
			if (!args.contains("rescalable") && getWidth() != getHeight()) {
				setSize(getInnerWidth(), getInnerWidth());
			}
			
			long time = System.nanoTime();
			double fps = 1_000_000_000d / (time - lastFrame);
			lastFrame = time;
			
//			System.out.println(fps);
			
			if (!state_changed)
				continue;
			state_changed = false;
			
			for (int p = 0, x = 0; x < size.width; x++) {
				for (int y = 0; y < size.height; y++) {
					Color c = new Color(
							state[p++] & 0xff,
							state[p++] & 0xff,
							state[p++] & 0xff);
					//TODO: only draw changes
					drawRect(x, y, c);
				}
			}
			if (args.contains("fps")) {
				g.setColor(Color.BLACK);
				g.drawString(String.valueOf(fps) + "fps", offset.width, offset.height + getInnerHeight());
			}
			validate();
		}
	}
	
	private void parseArguments(String[] args) {
		for (String s : args)
			this.args.add(s);
	}
	
	private void drawRect(int x, int y, Color c) {
		double
		 width = getInnerWidth() / size.getWidth(),
		 height = getInnerHeight() / size.getHeight();
		g.setColor(c);
		g.fillRect(
				offset.width + (int) (x * width),
				offset.height + (int) (y * height),
				(int) width + 1,
				(int) height + 1);
		if (args.contains("raster")) {
			g.setColor(Style.theme.background);
			g.drawRect(
					offset.width + (int) (x * width),
					offset.height + (int) (y * height),
					(int) width + 1,
					(int) height + 1);
		}
	}
	
	public void send(byte[] data) {
		//TODO: validation
		if (data.length != state.length)
			throw new IllegalArgumentException();
		this.state = data.clone();
		state_changed = true;
	}
	
//	private void sleep(long  millis) {
//		try {
//			Thread.sleep(millis);
//		} catch (Exception e) { }
//	}
	
	private void sleep(long nanos) {
		try {
			Thread.sleep((long) Math.floor(nanos / 1_000_000d), (int) (nanos % 1_000_000));
		} catch (Exception e) { }
	}
	
	private int getInnerWidth() {
		return getWidth() - insets.right - insets.left;
	}
	
	private int getInnerHeight() {
		return getHeight() - insets.top - insets.bottom;
	}
}