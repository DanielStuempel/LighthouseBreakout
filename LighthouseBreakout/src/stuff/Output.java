package stuff;

import java.awt.Color;
import java.io.IOException;
import java.util.LinkedList;

import de.cau.infprogoo.lighthouse.LighthouseDisplay;
import tokens.NoToken;

public class Output implements Runnable {
	private Display display;
	private byte[] data;
	private Level level;
	private Paddel paddel;
	private Ball ball;
	private LinkedList<Animation> eventList;
	
	private LighthouseDisplay lighthouseDisplay;

	public Output(Display display, byte[] data, Level level, Paddel paddel, Ball ball,
			LinkedList<Animation> eventList) {
		this.display = display;
		this.data = data;
		this.level = level;
		this.paddel = paddel;
		this.ball = ball;
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
		lighthouseDisplay = new LighthouseDisplay(NoToken.USER, NoToken.TOKEN);
		try {
			lighthouseDisplay.connect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Output o = this;
		TickTimer outputTimer = new TickTimer() {
			@Override
			public void tick() {
				synchronized (o) {
					o.notify();
				}
			}
		};
		Main.systemTimer.schedule(outputTimer, 0, 100);
	}

	private synchronized void main() throws InterruptedException {
		wait();
		
		for (int q = 0, y = 0; y < level.size.height; y++) {
			for (int x = 0; x < level.size.width; x++) {
				// draw brick
				Color c = Style.brickColor[level.get(x, y).getType()];
				data[q++] = (byte) c.getRed();
				data[q++] = (byte) c.getGreen();
				data[q++] = (byte) c.getBlue();

			}
		}

		//animations
		Color[][] c = null;
		for (int a = 0; a < eventList.size(); a++) {
			c = eventList.get(a).next();
			if (c == null) {
				eventList.remove(a);
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
		int p = (ball.pos.x + ball.pos.y * 28) * 3;
		data[p++] = (byte) Style.ballColor.getRed();
		data[p++] = (byte) Style.ballColor.getGreen();
		data[p] = (byte) Style.ballColor.getBlue();

		// draw paddel
		p = ((level.size.height - 1) * level.size.width + paddel.pos) * 3;
		for (int i = 0; i < paddel.size; i++) {
			data[p++] = -1;
			data[p++] = -1;
			data[p++] = -1;
		}

		display.send(data);

		if (lighthouseDisplay.isConnected())
			try {
				lighthouseDisplay.send(data);
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
}
