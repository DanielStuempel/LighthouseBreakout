package stuff;

import java.awt.Color;
import java.io.IOException;
import java.util.LinkedList;

import de.cau.infprogoo.lighthouse.LighthouseDisplay;
import tokens.NoToken;

public class Output implements Runnable {
	private Display.Input display;
	private Level level;
	private Engine engine;
	private LinkedList<Animation> eventList;

	private LighthouseDisplay lighthouseDisplay;
	
	TickTimer outputTimer;

	public Output(Engine engine, Display.Input display, Level level, LinkedList<Animation> eventList) {
		this.display = display;
		this.level = level;
		this.engine = engine;
		this.eventList = eventList;
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
		
		if (Settings.CONNECT_TO_LIGHTHOUSE) {
			lighthouseDisplay = new LighthouseDisplay(NoToken.USER, NoToken.TOKEN);
			try {
				lighthouseDisplay.connect();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		Output o = this;
		outputTimer = new TickTimer() {
			@Override
			public void tick() {
				synchronized (o) {
					o.notify();
				}
			}
		};
		Main.systemTimer.schedule(outputTimer, 0, Settings.FRAME_TICK_MS);//Settings.GAME_TICK__MS);
	}

	private synchronized void main() throws InterruptedException {
		wait();
		
		Ball ball = engine.getBall();
		Paddel paddel = engine.getPaddel();
		Level level = engine.getLevel();
		
		byte[] data = new byte[28 * 14 * 3];
		
		for (int q = 0, y = 0; y < level.size.height; y++) {
			for (int x = 0; x < level.size.width; x++) {
				// draw brick
				Color c = Style.brickColor[level.get(x, y).getType()];
				data[q++] = (byte) c.getRed();
				data[q++] = (byte) c.getGreen();
				data[q++] = (byte) c.getBlue();
			}
		}
		
		Color[][] c = null;
		for (int i = 0; i < eventList.size(); i++) {
			if (i >= eventList.size()) {
				System.out.println("caught a null pointer!");
				eventList.clear();
				break;
			}
			try {
				c = eventList.get(i).next();
			} catch (NullPointerException e) {
				e.printStackTrace();
				eventList.clear();
				continue;
			}
			if (c == null) {
				eventList.remove(i);
				i--;
				continue;
			}
			for (int q = 0, y = 0; y < level.size.height; y++) {
				for (int x = 0; x < level.size.width; x++) {
					// draw block
					if (c[x][y] == null) {
						q += 3;
						continue;
					}
					data[q++] = (byte) (c[x][y].getRed());
					data[q++] = (byte) (c[x][y].getGreen());
					data[q++] = (byte) (c[x][y].getBlue());
				}
			}
		}

		// draw ball
		int p = ((int) ball.getPosition().getX() + (int) ball.getPosition().getY() * 28) * 3;
		data[p++] = (byte) Style.ballColor.getRed();
		data[p++] = (byte) Style.ballColor.getGreen();
		data[p] = (byte) Style.ballColor.getBlue();

		// draw paddel
		p = ((level.size.height - 1) * level.size.width + (int) paddel.getPosition().getX()) * 3;
		for (int i = 0; i < paddel.getSize().getX(); i++) {
			data[p++] = (byte)Style.paddel.getRed();
			data[p++] = (byte)Style.paddel.getGreen();
			data[p++] = (byte)Style.paddel.getBlue();
		}

		display.send(data);
		
		if (Settings.CONNECT_TO_LIGHTHOUSE && lighthouseDisplay.isConnected())
				try {
					lighthouseDisplay.send(data);
				} catch (IOException e) {
					e.printStackTrace();
				}
	}
}
