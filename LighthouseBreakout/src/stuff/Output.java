package stuff;

import java.awt.Color;
import java.io.IOException;
import java.util.LinkedList;

import javax.rmi.ssl.SslRMIClientSocketFactory;

import de.cau.infprogoo.lighthouse.LighthouseDisplay;
import tokens.NoToken;

public class Output implements Runnable {
	private Display display;
	private byte[] data;
	private Level level;
	private SimplePaddel paddel;
	private SimpleBall ball;
	private LinkedList<Animation> eventList;
	private LinkedList<Integer> remove = new LinkedList<>();

	private LighthouseDisplay lighthouseDisplay;
	
	TickTimer outputTimer;

	public Output(Display display, byte[] data, Level level, SimplePaddel paddel, SimpleBall ball,
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
		Main.systemTimer.schedule(outputTimer, 0, Settings.GAME_TICK_MS);//Settings.GAME_TICK__MS);
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
		// animations
//		for (int x = remove.size()-1; x >= 0; x--) {
//			eventList.remove(x);
//			System.out.println(eventList.size());
//		}
//		remove.clear();		
		Color[][] c = null;
		for (int a = 0; a < eventList.size(); a++) {
			c = eventList.get(a).next();
			if (c == null) {
//				remove.add(a);
				eventList.remove(a);
				a--;
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
		if (Settings.CONNECT_TO_LIGHTHOUSE) {
			if (lighthouseDisplay.isConnected()) {
				try {
					lighthouseDisplay.send(data);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else { 
				try {
					lighthouseDisplay.connect();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
