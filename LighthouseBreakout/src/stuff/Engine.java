package stuff;

public class Engine implements Runnable {
	private boolean running;

	private Paddel pad;
	private Ball ball;

	private int newPaddelVelocity;
	
	private Display display;

	public Engine() {

	}

	public Engine(Display display, Level level) {
		this.display = display;
	}

	@Override
	public void run() {
		init();
		while (true) {
			try {
				main();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void init() {
		pad = new Paddel(9, 13, 10);
		ball = new Ball(0, 0);

		ball.setVelocity(0.3f, 0.8f);

		Engine e = this;
		TickTimer gameTickTimer = new TickTimer() {
			@Override
			public void tick() {
				synchronized (e) {
					e.notify();
				}
			}
		};
		Main.systemTimer.schedule(gameTickTimer, 0, Settings.GAME_TICK_MS);
	}

	private synchronized void main() throws InterruptedException {
		wait();

		if (pad.getPosition().getX() + pad.getVelocity().getX() < 0) {
			pad.setPosition(0);
			pad.setVelocity(0, 0);
		} else if (pad.getPosition().getX() + pad.getSize().getX() + pad.getVelocity().getX() > 27) {
			pad.setPosition(28 - pad.getSize().getX());
			pad.setVelocity(0, 0);
		} else {
			pad.move();
		}
		pad.setVelocity(newPaddelVelocity, 0);

		ball.move();
		Vector2f pos = ball.getPosition();
		Vector2f vel = ball.getVelocity();
		Vector2f newPos = pos.add(vel);
		if (newPos.getX() < 0) {
			ball.setPosition(0, pos.getY());
			ball.setVelocity(-vel.getX(), vel.getY());
		} else if (newPos.getX() > 27) {
			ball.setPosition(27, pos.getY());
			ball.setVelocity(-vel.getX(), vel.getY());
		}
		if (newPos.getY() < 0) {
			ball.setPosition(pos.getX(), 0);
			ball.setVelocity(vel.getX(), -vel.getY());
		} else if (newPos.getY() >= 13) {
			if (pos.getX() >= pad.getPosition().getX() && pos.getX() < pad.getPosition().getX() + pad.getSize().getX()) {
				ball.setPosition(pos.getX(), 12);
				float rotation = (float) vel.angle(vel.rotate(Math.PI + vel.angle(new Vector2f(1, 0)) * 2));
				float scaling = (ball.getPosition().getX() - pad.getPosition().getX() - pad.getSize().getX() / 2) / pad.getSize().getX();
				if (vel.getX() < 0)
					rotation *= -1;
				System.out.println(rotation + "+" + scaling);
				ball.setVelocity(vel.rotate(Math.PI + rotation + scaling));
			} else {
				// loose
				ball.setPosition(0, 0);
			}
		}
		
		
		
//		System.out.println(ball.getPosition() + ":" + ball.getVelocity() + ":" + ball.getVelocity().length());

		byte[] data = new byte[28 * 14 * 3];

		int p = ((int) ball.getPosition().getX() + (int) ball.getPosition().getY() * 28) * 3;
		data[p++] = (byte) Style.ballColor.getRed();
		data[p++] = (byte) Style.ballColor.getGreen();
		data[p] = (byte) Style.ballColor.getBlue();

		// draw paddel
		p = (int) (pad.getPosition().getX() + pad.getPosition().getY() * 28) * 3;
		for (int i = 0; i < pad.getSize().getX(); i++) {
			data[p++] = -1;
			data[p++] = -1;
			data[p++] = -1;
		}

		display.send(data);
	}

	// TODO:
	public void changePaddelVelocity(int x) {
		newPaddelVelocity = x;
	}

	public Ball getPosition() {
		return ball;
	}

	public Paddel getPaddel() {
		return pad;
	}

	public void pause() {
		running = false;
	}

	public void unpause() {
		running = true;
	}

	public boolean isPaused() {
		return !running;
	}
}
