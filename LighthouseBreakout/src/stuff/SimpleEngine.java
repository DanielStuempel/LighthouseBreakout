package stuff;

import java.awt.Color;
import java.awt.Point;
import java.util.LinkedList;

public class SimpleEngine implements Runnable {
	Level level;
	SimplePaddel paddel;
	SimpleBall ball;
	LinkedList<Animation> eventList;

	public SimpleEngine(Level level, SimplePaddel paddel, SimpleBall ball, LinkedList<Animation> eventList,
			String... args) {
		this.level = level;
		this.paddel = paddel;
		this.ball = ball;
		this.eventList = eventList;
	}

	@Override
	public void run() {
		init();

		try {
			while (true) {
				main();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void init() {
		ball.vel.x = 1;
		ball.vel.y = 1;

		SimpleEngine e = this;
		TickTimer gameTickTimer = new TickTimer() {
			@Override
			public void tick() {
				synchronized (e) {
					// pause function
					if (Settings.GAME_RUNNING) {
						e.notify();
					}
				}
			}
		};
		Main.systemTimer.schedule(gameTickTimer, 0, Settings.GAME_TICK__MS);
	}

	public synchronized void main() throws InterruptedException {
		wait();

		Animation tail = new Animation(new Point(ball.pos), Color.MAGENTA, Animation.Type.TAIL);
		eventList.add(tail);

		// update paddel
		if (paddel.pos + paddel.vel >= 0 && paddel.pos + paddel.size + paddel.vel <= level.size.width)
			paddel.pos += paddel.vel;

		Point newPos = new Point(ball.pos.x + ball.vel.x, ball.pos.y + ball.vel.y);

		boolean x, y, z;
		int hitCount = 0;

		while (true) {
			newPos = new Point(ball.pos.x + ball.vel.x, ball.pos.y + ball.vel.y);
			// test walls
			if (newPos.x < 0 || newPos.x >= level.size.width) {
				ball.vel.x *= -1;
			}
			// test ground
			else if (newPos.y >= level.size.height - 1) {
				if (newPos.x < paddel.pos || newPos.x > paddel.pos + paddel.size) {
					ball.pos.x = 13;
					ball.pos.y = 12;
					ball.vel.x = 1;
					ball.vel.y = 1;
					paddel.pos = 11;
					level.reset();
					return;
				} else if (paddel.vel != 0)
					ball.vel.x = paddel.vel;
				ball.vel.y *= -1;
				// test ceiling
			} else if (newPos.y < 0)
				ball.vel.y *= -1;

			else if (newPos.x == ball.pos.x && hitBrick(ball.pos.x, newPos.y)) {
				hitCount = 1;
				if (hitBrick(ball.pos.x - 1, newPos.y))
					hitCount++;
				if (hitBrick(ball.pos.x + 1, newPos.y))
					hitCount++;
				ball.vel.y *= -1;

			} else if ((x = hitBrick(newPos.x, ball.pos.y)) | (y = hitBrick(ball.pos.x, newPos.y))
					| (z = hitBrick(newPos.x, newPos.y))) {
				if (x)
					hitCount++;
				if (y)
					hitCount++;
				if (z)
					hitCount++;
				if (x && !y)
					ball.vel.x *= -1;
				else if (y && !x)
					ball.vel.y *= -1;
				else {
					ball.vel.x *= -1;
					ball.vel.y *= -1;
				}
			} else
				break;

		}
		ball.pos.x += ball.vel.x;
		ball.pos.y += ball.vel.y;

		// hitCount
		// eventList.add(new Animation(new Point(ball.pos.x,ball.pos.y),
		// Style.brickColor[type], Animation.Type.EXPLOSION));
	}

	private boolean hitBrick(int x, int y) {
		if (x < 0 || y < 0 || x >= level.size.width || y >= level.size.height - 1)
			return false;
		Brick b = level.get(x, y);
		if (b.getType() == 0)
			return false;
		eventList.add(new Animation(new Point(x, y), b, Animation.Type.BRICKHIT));
		b.hit();
		return true;
	}
}