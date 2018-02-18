package stuff;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.HashSet;

import javax.swing.JFrame;

public class Display extends JFrame {
	private Dimension size = new Dimension(28, 14);
	private Dimension scale = new Dimension (20, 40);
	
	private byte[] state = new byte[size.width * size.height * 3];
	
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
		setBackground(Style.BACKGROUND);
		setVisible(true);
		insets = getInsets();
		offset = new Dimension(insets.right, insets.top);
		g = getGraphics();
		if (!args.contains("scalable"))
			addComponentListener();
		setSize(insets.right + getWidth() + insets.left, insets.top + getHeight() + insets.bottom);
	}
	
	public void run() {
		while (true) {
			//TODO: improve frame rate precision and state change detection
			do {
				sleep(20);
			} while (!state_changed);
			state_changed = false;
//			System.out.println(System.nanoTime());
			for (int p = 0, x = 0; x < size.width; x++) {
				for (int y = 0; y < size.height; y++) {
					Color c = new Color(
							state[p++] & 0xff,
							state[p++] & 0xff,
							state[p++] & 0xff);
					drawRect(x, y, c);
				}
			}
			validate();
		}
	}
	
	private void parseArguments(String[] args) {
		for (String s : args)
			this.args.add(s);
	}
	
	private void drawRect(int x, int y, Color c) {
		double width, height;
		if (args.contains("resizable")) {
			width = getInnerWidth() / size.getWidth();
			height = getInnerHeight() / size.getHeight();
		} else {
			width = scale.width;
			height = scale.height;
		}
		g.setColor(c);
		g.fillRect(
				offset.width + (int) (x * width),
				offset.height + (int) (y * height),
				(int) width,
				(int) height);
		if (args.contains("raster")) {
			g.setColor(Color.BLACK);
			g.drawRect(
					offset.width + (int) (x * width),
					offset.height + (int) (y * height),
					(int) width,
					(int) height);
		}
	}
	
	public void send(byte[] data) {
		//TODO: validation
		if (data.length != state.length)
			throw new IllegalArgumentException();
		this.state = data;
		state_changed = true;
	}
	
	private void sleep(int millis) {
		try {
			Thread.sleep(millis);
		} catch (Exception e) { }
	}
	
	private int getInnerWidth() {
		return getWidth() - insets.right - insets.left;
	}
	
	private int getInnerHeight() {
		return getHeight() - insets.top - insets.bottom;
	}
	
	private void onResize() {
		if (getWidth() != getHeight()) {
			setSize(getInnerWidth(), getInnerWidth());
		}
	}
	private void addComponentListener() {
		addComponentListener(new ComponentListener() {
			private String lastEvent = "";
			
			@Override
			public void componentHidden(ComponentEvent arg0) { }
			
			@Override
			public void componentMoved(ComponentEvent arg0) { }

			@Override
			public void componentResized(ComponentEvent arg0) {
				if (lastEvent.equals(lastEvent = arg0.paramString()))
					onResize();
//				System.out.println(lastEvent);
			}

			@Override
			public void componentShown(ComponentEvent arg0) { }
		});
	}
}