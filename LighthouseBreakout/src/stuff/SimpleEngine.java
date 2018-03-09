package stuff;

import java.awt.Point;

public class SimpleEngine extends Engine {
	Level level;
	Paddel paddel;
	Ball ball;
	SyncList<Animation> eventList;

	int newPaddelPosition;
	
	public SimpleEngine(Level level, SyncList<Animation> eventList) {
		this.level = level;
		this.eventList = eventList;
		paddel = new Paddel(0, 7);
		ball = new Ball(0, 0);
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

	public synchronized void main() throws InterruptedException {
		wait();

		if (isPaused())
			return;

		Animation tail = new Animation(new Point((int) ball.getPosition().getX(), (int) ball.getPosition().getY()), Style.ballColor, Animation.Type.TAIL);
		eventList.syncAdd(tail);

		// update paddel
		paddel.setPosition(paddel.getPosition().getX() + newPaddelPosition);
		if (paddel.getPosition().getX() < 0)
			paddel.setPosition(0);
		if (paddel.getPosition().getX() + paddel.getSize().getX() > level.size.width)
			paddel.setPosition(level.size.width - paddel.getSize().getX());
		newPaddelPosition = 0;

		Point newPos, pos, vel;
		pos = new Point((int) ball.getPosition().getX(), (int) ball.getPosition().getY());
		vel = new Point((int) ball.getVelocity().getX(), (int) ball.getVelocity().getY());
		boolean x, y;
		while (true) {
			newPos = new Point(pos.x + vel.x, pos.y + vel.y);
			// test walls
			if (newPos.x < 0 || newPos.x >= level.size.width)
				vel.x *= -1;
			// test ground
			else if (newPos.y >= level.size.height - 1) {
				if (newPos.x < paddel.getPosition().getX() || newPos.x > paddel.getPosition().getX() + paddel.getSize().getX()) {
					new SoundEngine().playSound(SoundEngine.GAME_LOST);
					pause();
					Settings.SCORE = level.getScore();
					for (GameEventListener l : getEventListeners()) {
						l.gameLost();
					}
					reset();
					return;
				}
				vel.y *= -1;
				// test ceiling
			} else if (newPos.y < 0)
				vel.y *= -1;

			else if (newPos.x == pos.x && hitBrick(pos.x, newPos.y))
				vel.y *= -1;
			else if ((x = hitBrick(newPos.x, pos.y)) | (y = hitBrick(pos.x, newPos.y))
					| hitBrick(newPos.x, newPos.y)) {
				if (x && !y)
					vel.x *= -1;
				else if (y && !x)
					vel.y *= -1;
				else {
					vel.x *= -1;
					vel.y *= -1;
				}
			} else
				break;

		}
		ball.setPosition(new Vector2f(pos));
		ball.setVelocity(new Vector2f(vel));
		ball.move();
		
		if (level.maxScore == level.getScore()) {
			pause();
			Settings.GAME_WON = true;
			Settings.SCORE = level.getScore();
			reset();
			for(GameEventListener l : getEventListeners()) {
				l.gameWon();
			}
		}
	}

	private boolean hitBrick(int x, int y) {
		int type = level.hit(x, y);
		if (type == 0)
			return false;
		eventList.syncAdd(new Animation(new Point(x, y), type, Animation.Type.BRICKHIT));
		eventList.syncAdd(new Animation(new Point(x, y), Style.brickColor[type], Animation.Type.EXPLOSION));
		return true;
	}
	
	public void reset() {
		ball.setPosition(level.size.width / 2 - 1, level.size.height - 3);
		ball.setVelocity(1, 1);
		paddel.setPosition((level.size.width - paddel.getSize().getX()) / 2);
		level.reset();

		new SoundEngine().playSound(SoundEngine.GAME_START);
	}

	public void debug() {
		eventList.clear();
	}

	@Override
	public Level getLevel() {
		return level;
	}

	@Override
	public void movePaddel(float f) {
		newPaddelPosition = (int) f;
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