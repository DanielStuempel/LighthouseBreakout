package stuff;

public class Bot implements Runnable {
	SimpleEngine engine;
	
	public Bot(SimpleEngine engine) {
		this.engine = engine;
	}
	
	public void run() {
		init();
		while (true) {
			try {
				main();
			} catch (InterruptedException e) {
				System.err.println("bot crashed unexpectedly");
				e.printStackTrace();
				break;
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
		
		SimpleBall ball = engine.getBall();
		SimplePaddel paddel = engine.getPaddel();
		
		if (Settings.HAX_ON) {
			if (ball.getPosition().getX() > paddel.getPosition().getX() + paddel.getSize().getX() / 2)
				engine.changePaddelPosition(1);
			else if (ball.getPosition().getX() < paddel.getPosition().getX() + paddel.getSize().getX() / 2)
				engine.changePaddelPosition(-1);
		}
	}
}
