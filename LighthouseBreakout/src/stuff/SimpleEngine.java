package stuff;

import java.awt.Point;
import java.io.InputStream;
import java.util.LinkedList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SimpleEngine implements Runnable {
	Level level;
	SimplePaddel paddel;
	SimpleBall ball;
	int points;
	LinkedList<Animation> eventList;
	private int timeout = Settings.RESET_TIMEOUT;

	public SimpleEngine(Level level, LinkedList<Animation> eventList, String... args) {
		this.level = level;
		paddel = new SimplePaddel(0, 7);
		ball = new SimpleBall(0, 0);
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
		reset();

		SimpleEngine e = this;
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

	public void reset() {
		ball.pos.x = level.size.width / 2 - 1;
		ball.pos.y = level.size.height - 2;
		ball.vel.x = 1;
		ball.vel.y = 1;
		points = 0;
		paddel.pos = (level.size.width - paddel.size) / 2;
		level.reset();

		if (Settings.SOUND_XP_START) {
			try {
				ClassLoader classloader = Thread.currentThread().getContextClassLoader();
				InputStream is = classloader.getResourceAsStream("XP_START.wav");
				AudioInputStream audioIn = AudioSystem.getAudioInputStream(is);
				Clip clip = AudioSystem.getClip();
				clip.open(audioIn);
				clip.start();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public synchronized void main() throws InterruptedException {
		wait();

		if (Settings.GAME_PAUSED)
			return;

		Animation tail = new Animation(new Point(ball.pos), Style.ballColor, Animation.Type.TAIL);
		eventList.add(tail);

		// update paddel
		// if (paddel.pos + paddel.vel >= 0 && paddel.pos + paddel.size + paddel.vel <=
		// level.size.width)
		// paddel.pos += paddel.vel;

		// if (!(paddel.pos > 0 && paddel.pos < level.size.width-paddel.size)) {
		// if (paddel.pos < 0) paddel.pos = 0;
		// if (paddel.pos > level.size.width-paddel.size) paddel.pos =
		// level.size.width-paddel.size;
		// }

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
					Settings.SCORE = points;
					try {
						ClassLoader classloader = Thread.currentThread().getContextClassLoader();
						InputStream is = null;
						if (Settings.SOUND_XP_SHUTDOWN)
							is = classloader.getResourceAsStream("XP_SHUTDOWN.wav");
						else if (Settings.SOUND_SAD_TRUMPET)
							is = classloader.getResourceAsStream("trump.wav");
						if (is != null) {
							AudioInputStream audioIn = AudioSystem.getAudioInputStream(is);
							Clip clip = AudioSystem.getClip();
							clip.open(audioIn);
							clip.start();
							
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
//					Window.w.Scoreboard();
					return;
				} // else if (paddel.vel != 0)
					// ball.vel.x = paddel.vel;
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

		points += hitCount;
		if (level.neededPoints() - points == 0) {
			Settings.SCORE = level.neededPoints();
			Settings.GAME_WON = true;
//			Window.w.Scoreboard();
		}
		if (hitCount > 0)
			timeout = Settings.RESET_TIMEOUT;
		if (timeout-- == 0)
			reset();
	}

	private boolean hitBrick(int x, int y) {
		if (x < 0 || y < 0 || x >= level.size.width || y >= level.size.height - 1)
			return false;
		Brick b = level.get(x, y);
		if (b.getType() == 0)
			return false;
		eventList.add(new Animation(new Point(x, y), b, Animation.Type.BRICKHIT));
		eventList.add(new Animation(new Point(x, y), Style.brickColor[b.getType()], Animation.Type.EXPLOSION));
		b.hit();
		return true;
	}

	public SimpleBall getBall() {
		return ball;
	}

	public SimplePaddel getPaddel() {
		return paddel;
	}

	public void changePaddelPosition(int newP) {
		if (paddel.pos + newP < 0 || paddel.pos + newP > level.size.width - paddel.size)
			return;
		paddel.pos += newP;
	}

	public void debug() {
		eventList.clear();
	}
}