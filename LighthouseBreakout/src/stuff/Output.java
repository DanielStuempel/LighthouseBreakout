package stuff;

import java.awt.Color;

public class Output implements Runnable {
	private Display display;
	private byte[] data;
	private Level level;
	private Paddel paddel;
	private Ball ball;
	
	public Output(Display display, byte[] data, Level level, Paddel paddel, Ball ball) {
		this.display = display;
		this.data = data;
		this.level = level;
		this.paddel = paddel;
		this.ball = ball;
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
		Output o = this;
		TickTimer outputTimer = new TickTimer() {
			@Override
			public void tick() {
				synchronized (o) {
					o.notify();
				}
			}
		};
		Main.timer.schedule(outputTimer, 0, 100);
	}
	
	private synchronized void main() throws InterruptedException {
		wait();
		for (int q = 0, y = 0; y < level.size.height; y++) {
			for (int x = 0; x < level.size.width; x++) {
				// draw block
				Color c = Style.brickColor[level.get(x, y).getType()];
				//Color Brick
				//data[q++] = data[q++] = data[q++] = (byte) (b == null ? 0 : 100 + b.getType() * 10);
				data[q++] = (byte) c.getRed();
				data[q++] = (byte) c.getGreen();
				data[q++] = (byte) c.getBlue();

			}
		}

		//draw ball
		int p = (ball.pos.x + ball.pos.y * 28) * 3;
		data[p++] = (byte) Style.ballColor.getRed();
		data[p++] = (byte) Style.ballColor.getGreen();
		data[p] = (byte) Style.ballColor.getBlue();
		
		//draw paddel
		p = ((level.size.height - 1) * level.size.width + paddel.pos) * 3;
		for (int i = 0; i < paddel.size; i++) {
			data[p++] = -1;
			data[p++] = -1;
			data[p++] = -1;
		}

		display.send(data);
	}
}
