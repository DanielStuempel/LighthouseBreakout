package stuff;

import java.awt.Color;

public class ExperimentalEngine extends Engine {
	private Paddel paddel;
	private Ball ball;

	private float newPaddelPosition;

	private Display.Input display;

	private Level level;

	public ExperimentalEngine(Display.Input display) {
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
		level = new Level(Map.CAU);
		ball = new Ball(13, 11);
		paddel = new Paddel(11, 5);
		reset();
		ExperimentalEngine e = this;
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

		if (Settings.GAME_PAUSED || isPaused())
			return;

		paddel.setPosition(paddel.getPosition().getX() + newPaddelPosition);
		newPaddelPosition = 0;

		if (paddel.getPosition().getX() < 0) {
			paddel.setPosition(0);
			paddel.setVelocity(0, 0);
		} else if (paddel.getPosition().getX() + paddel.getSize().getX() > 27) {
			paddel.setPosition(28 - paddel.getSize().getX());
			paddel.setVelocity(0, 0);
		} else {
			paddel.move();
		}

		Vector2f pos = ball.getPosition();
		Vector2f vel = ball.getVelocity();

		// boolean a, b;
		while (true) {
			Vector2f newPos = pos.add(vel);
			if (newPos.getX() < 0)
				vel = new Vector2f(-vel.getX(), vel.getY());
			else if (newPos.getX() > level.size.width)
				vel = new Vector2f(-vel.getX(), vel.getY());
			else if (newPos.getY() < 0)
				vel = new Vector2f(vel.getX(), -vel.getY());
			else if (newPos.getY() > level.size.height - 1)
				if (collision(ball, paddel)) {
					float scaling = ((pos.getX() + ball.getSize().getX() / 2)
							- (paddel.getPosition().getX() + paddel.getSize().getX() / 2)) / (paddel.getSize().getX() * 10);
					Vector2f tmp = vel.rotate(-vel.angle(new Vector2f(1, 0)) * 2 + scaling);
					if (tmp.angle(new Vector2f(1, 0)) > Math.PI / 4 && tmp.angle(new Vector2f(1, 0)) < Math.PI / 4 * 3)
						vel = tmp;
					else
						vel = vel.rotate(-vel.angle(new Vector2f(1, 0)) * 2);
				} else {
					reset();
					break;
				}
			else {
				int x = (int) pos.getX();
				int y = (int) pos.getY();
				int nx = (int) newPos.getX();
				int ny = (int) newPos.getY();

				boolean c = collisionBrick((int) newPos.getX(), (int) newPos.getY());
				boolean a = c && y == ny || collisionBrick((int) newPos.getX(), (int) pos.getY());
				boolean b = c && x == nx || collisionBrick((int) pos.getX(), (int) newPos.getY());

				if (a && !b)
					vel = new Vector2f(-vel.getX(), vel.getY());
				else if (b && !a)
					vel = new Vector2f(vel.getX(), -vel.getY());
				else if (!a && !b && !c) {
					ball.setPosition(pos);
					ball.setVelocity(vel);
					ball.move();
					break;
				} else {
					vel = new Vector2f(-vel.getX(), vel.getY());
					vel = new Vector2f(vel.getX(), -vel.getY());
				}
			}
		}

//		byte[] data = new byte[28 * 14 * 3];
//
//		for (int q = 0, y = 0; y < level.size.height; y++) {
//			for (int x = 0; x < level.size.width; x++) {
//				// draw brick
//				Color c = Style.brickColor[level.get(x, y).getType()];
//				data[q++] = (byte) c.getRed();
//				data[q++] = (byte) c.getGreen();
//				data[q++] = (byte) c.getBlue();
//			}
//		}
//
//		// draw ball
//		int p = ((int) ball.getPosition().getX() + (int) ball.getPosition().getY() * 28) * 3;
//		data[p++] = (byte) Style.ballColor.getRed();
//		data[p++] = (byte) Style.ballColor.getGreen();
//		data[p] = (byte) Style.ballColor.getBlue();
//
//		// draw paddel
//		p = (int) (paddel.getPosition().getX() + paddel.getPosition().getY() * 28) * 3;
//		for (int i = 0; i < paddel.getSize().getX(); i++) {
//			data[p++] = (byte) Style.paddel.getRed();
//			data[p++] = (byte) Style.paddel.getGreen();
//			data[p++] = (byte) Style.paddel.getBlue();
//		}
//
//		display.send(data);
	}

	private boolean collision(Entity a, Entity b) {
		if (a.getStart().getX() > b.getEnd().getX() || a.getStart().getY() > b.getEnd().getY()
				|| b.getStart().getX() > a.getEnd().getX() || b.getStart().getY() > a.getEnd().getY())
			return false;
		return true;
	}

	private boolean collisionBrick(int x, int y) {
		if (x < 0 || y < 0 || x >= level.size.width || y >= level.size.height)
			return false;
		return level.get(x, y).hit();
	}

	@Override
	public void movePaddel(float x) {
		newPaddelPosition = x;
	}
	
	@Override
	public void reset() {
		Settings.GAME_PAUSED = true;
		paddel.setPosition(11, 13);
		ball.setPosition(13, 11);
		ball.setVelocity(new Vector2f(0.2f, 0.8f).normalize());
		level.reset();
		System.out.println("reset");
	}

	@Override
	public void debug() {

	}

	@Override
	public Level getLevel() {
		return level;
	}

	@Override
	public Paddel getPaddel() {
		return paddel;
	}

	@Override
	public Ball getBall() {
		return ball;
	}
}
