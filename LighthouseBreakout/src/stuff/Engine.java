package stuff;

import java.awt.Color;
import java.awt.Point;

public class Engine implements Runnable {
	private boolean running;

	private Paddel pad;
	private Ball ball;

	private float newPaddelPosition;

	private Display.Input display;

	private Level level;

	public Engine() {

	}

	public Engine(Display.Input display, Level level) {
		this.display = display;
		this.level = level;
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
		pad = new Paddel(10, 13, 7);
		ball = new Ball(13.5f, 10);

		ball.setVelocity(0f, 1f);

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

		if (!Settings.GAME_RUNNING)
			return;

		pad.setPosition(pad.getPosition().getX() + newPaddelPosition);
		newPaddelPosition = 0;

		if (pad.getPosition().getX() < 0) {
			pad.setPosition(0);
			pad.setVelocity(0, 0);
		} else if (pad.getPosition().getX() + pad.getSize().getX() > 27) {
			pad.setPosition(28 - pad.getSize().getX());
			pad.setVelocity(0, 0);
		} else {
			pad.move();
		}
		
		Vector2f pos = ball.getPosition();
		Vector2f vel = ball.getVelocity();
		while (true) {
			Vector2f newPos = pos.add(vel);
			if (newPos.getX() < 0.5f) {
				pos = new Vector2f(0.5f, pos.getY());
				vel = new Vector2f(-vel.getX(), vel.getY());
			} else if (newPos.getX() > 27.5f) {
				pos = new Vector2f(27.5f, pos.getY());
				vel = new Vector2f(-vel.getX(), vel.getY());
			} else if (newPos.getY() < 0.5f) {
				pos = new Vector2f(pos.getX(), 0.5f);
				vel = new Vector2f(vel.getX(), -vel.getY());
			} else if (newPos.getY() > 12.5f) {
				if (pos.getX() >= pad.getPosition().getX()
						&& pos.getX() < pad.getPosition().getX() + pad.getSize().getX()) {
					pos = new Vector2f(pos.getX(), 12.5f);
					float rotation = (float) vel.angle(vel.rotate(Math.PI + vel.angle(new Vector2f(1, 0)) * 2));
					float scaling = (pos.getX() - pad.getPosition().getX() - pad.getSize().getX() / 2)
							/ pad.getSize().getX();
					if (vel.getX() < 0)
						rotation *= -1;
//					System.out.println(rotation + "+" + scaling);
//					System.out.println(vel);
//					System.out.println(ball.getVelocity());
					Vector2f tmp = vel.rotate(Math.PI + rotation + scaling);
					float angle = vel.angle(new Vector2f(1, 0));
					// System.out.println(angle);
					if (angle > 1 && angle < 2)
						vel = new Vector2f(tmp);
					else
						vel = vel.rotate(Math.PI + rotation);
				} else {
					pos = new Vector2f(13, 10);
					vel = new Vector2f(0, 1);
					level.reset();
				}
			} else if (testBrick(ball.getLocation().x + ball.getDirection().x, ball.getLocation().y + ball.getDirection().y)
					&& level.get(ball.getLocation().x, ball.getLocation().y).hit()) {
				if (testBrick(ball.getLocation().x - ball.getDirection().x, ball.getLocation().y))
					level.get(ball.getLocation().x - ball.getDirection().x, ball.getLocation().y).hit();
				if (testBrick(ball.getLocation().x, ball.getLocation().y - ball.getDirection().y))
					level.get(ball.getLocation().x, ball.getLocation().y - ball.getDirection().y).hit();
				if ((pos.getX() - ball.getLocation().x) - (pos.getY() - ball.getLocation().y) > 0) {
					pos = ball.getPosition();
					vel = new Vector2f(-vel.getX(), vel.getY());
					System.out.println("x");
				} else if ((pos.getX() - ball.getLocation().x) - (pos.getY() - ball.getLocation().y) < 0) {
					pos = ball.getPosition();
					vel = new Vector2f(vel.getX(), -vel.getY());
					System.out.println("y");
				} else {
					pos = ball.getPosition();
					vel = new Vector2f(-vel.getX(), -vel.getY());
					System.out.println("xy");
				}
				vel = new Vector2f(ball.getVelocity().rotate(Math.PI));
			} else {
				ball.setPosition(pos);
				ball.setVelocity(vel);
				ball.move();
				break;
			}

		}

		// System.out.println(ball.getPosition() + ":" + ball.getVelocity() + ":" +
		// ball.getVelocity().length());

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

		// draw ball
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

	private boolean collision(Entity a, Entity b) {
		if (a.getStart().length() > b.getEnd().length() || b.getStart().length() > a.getEnd().length())
			return false;
		return true;
	}

	private boolean testBrick(int x, int y) {
		if (x < 0 || y < 0 || x >= level.size.width || y >= level.size.height)
			return false;
		return level.get(x, y).getType() != 0;
	}

	// TODO:
	public void changePaddelPosition(float x) {
		newPaddelPosition += x;
	}

	public Ball getPosition() {
		return ball;
	}

	public Paddel getPaddel() {
		return pad;
	}

	public Ball getBall() {
		return ball;
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

	public void reset() {

	}

	public void debug() {

	}
}
