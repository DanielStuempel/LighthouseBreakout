package stuff;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.util.HashSet;

import javax.swing.JFrame;

public class Display extends JFrame implements Runnable {
	private int frameTime = 20_000_000;
	
	private Dimension size = new Dimension(28, 14);
	private Dimension scale = new Dimension (15, 40);
	private Dimension wSize = new Dimension(
			size.width * scale.width,
			size.height * scale.height);
	
	private volatile byte[] state = new byte[size.width * size.height * 3];
	
	private volatile boolean state_changed;
	
	HashSet<String> args = new HashSet<String>();
	
	Graphics g;
	
	Insets insets;
	Dimension offset;
	
	public Display(String... args) {
		super();
		parseArguments(args);
	}
	
	@Override
	public void run() {
		init();
		main();
	}
	
	public void init() {
		//TODO: make size a parameter
		setSize(wSize);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBackground(Style.theme.background);
		setVisible(true);
		insets = getInsets();
		offset = new Dimension(insets.right, insets.top);
		g = getGraphics();
		if (!args.contains("resizable"))
			setResizable(false);
	}
	
	public void main() {
		long lastFrame = 0;
		while (true) {
			//TODO: fix inconsistent frame rates due to imprecise nanoTime
			long wait = frameTime - System.nanoTime() % frameTime;
//			System.out.println(wait);
			sleep(wait);
			
			if (!args.contains("rescalable")) {
				if (getWidth() != getHeight() * wSize.height / wSize.width) {
					setSize(
							getInnerHeight() * wSize.height / wSize.width,
							getInnerHeight());
				}
			}
			
			long time = System.nanoTime();
			double fps = 1_000_000_000d / (time - lastFrame);
			lastFrame = time;
			
//			System.out.println(fps);
			
			if (!state_changed)
				continue;
			state_changed = false;
			
			for (int p = 0, y = 0; y < size.height; y++) {
				for (int x = 0; x < size.width; x++) {
					Color c = new Color(
							state[p++] & 0xff,
							state[p++] & 0xff,
							state[p++] & 0xff);
					//TODO: only draw changes
					drawRect(x, y, c);
				}
			}
			if (args.contains("fps")) {
				g.setColor(Color.WHITE);	
				g.drawString(
						"fps:" + fps,
						offset.width,
						offset.height + getInnerHeight());
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
	
	private void sleep(long nanos) {
		try {
			Thread.sleep(
					(long) Math.floor(nanos / 1_000_000d),
					(int) (nanos % 1_000_000));
		} catch (Exception e) { }
	}
	
	private int getInnerWidth() {
		return getWidth() - insets.right - insets.left;
	}
	
	private int getInnerHeight() {
		return getHeight() - insets.top - insets.bottom;
	}
}