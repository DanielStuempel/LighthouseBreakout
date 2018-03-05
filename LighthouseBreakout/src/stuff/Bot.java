package stuff;

public class Bot implements Runnable {
	SimplePaddel paddel = null;
	SimpleBall ball = null;

	public void run() {
		init();
		while (true) {
			try {
				main();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void init() {

		Bot bot = this;
		TickTimer timer = new TickTimer() {
			@Override
			public void tick() {
				synchronized (bot) {
					bot.notify();
				}
			}
		};
		Main.systemTimer.schedule(timer, 0, Settings.GAME_TICK_MS);
	}

	private synchronized void main() throws InterruptedException {
		wait();
		if (Settings.HAX_ON) {
			if (ball.pos.x < paddel.pos + paddel.size / 2) {
				paddel.vel = -1;
			} else if (ball.pos.x > paddel.pos + paddel.size / 2) {
				paddel.vel = 1;
			}
		}
	}

	public void set(SimplePaddel paddel, SimpleBall ball) {
		this.paddel = paddel;
		this.ball = ball;
	}
}
