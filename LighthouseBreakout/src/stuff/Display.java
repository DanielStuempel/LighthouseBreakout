package stuff;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.util.HashSet;
import java.util.TimerTask;

import javax.swing.JPanel;
import java.util.Timer;

public class Display extends JPanel implements Runnable {
	private Dimension size = new Dimension(28, 14);
	private Dimension scale = new Dimension (30, 50);
	
	private volatile byte[] state = new byte[size.width * size.height * 3];
	
	private volatile boolean state_changed;
	
	HashSet<String> args = new HashSet<String>();
	private double fps = 0;
	
	public Display(String... args) {
		super();
		parseArguments(args);
	}
	
	@Override
	public void run() {
		init();
	}
	
	public void init() {
		setPreferredSize(new Dimension(size.width * scale.width, size.height * scale.height));
		setBackground(Style.theme.background);
		
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				tick();
			}
		};
		
		Timer t = new Timer(true);
		t.schedule(task, 0, 20);
	}
	
	public void tick() {
		if (!state_changed)
			return;
			state_changed = false;
			repaint();
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
		if (state.length != data.length)
			throw new IllegalArgumentException();
		state = data.clone();
		state_changed = true;
	}
}