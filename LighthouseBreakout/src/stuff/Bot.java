package stuff;

public class Bot implements Runnable {
	// SimpleEngine engine;
	Engine engine;

	public Bot(Engine engine) {
		// public Bot(SimpleEngine engine) {
		this.engine = engine;
	}

	@Override
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
		
		if (Settings.GAME_PAUSED)
			return;
		
		// SimpleBall ball = engine.getBall();
		// SimplePaddel paddel = engine.getPaddel();
		
		Ball ball = engine.getBall();
		Paddel paddel = engine.getPaddel();

		if (Settings.HAX_ON) {
			float f = (ball.getPosition().getX() + ball.getSize().getX() / 2)
					- (paddel.getPosition().getX() + paddel.getSize().getX() / 2);
			engine.changePaddelPosition(f);
		}
	}
}
