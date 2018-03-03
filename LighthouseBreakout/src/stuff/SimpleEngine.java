package stuff;

import java.awt.Color;
import java.awt.Point;
import java.util.LinkedList;

public class SimpleEngine implements Runnable {
	Level level;
	Paddel paddel;
	Ball ball;
	LinkedList<Animation> eventList;

	public SimpleEngine(Level level, Paddel paddel, Ball ball, LinkedList<Animation> eventList, String... args) {
		this.level = level;
		this.paddel = paddel;
		this.ball = ball;
		this.eventList = eventList;
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
		ball.vel.x = 0;
		ball.vel.y = 1;
		
		SimpleEngine e = this;
		TickTimer gameTickTimer = new TickTimer() {
			@Override
			public void tick() {
				synchronized (e) {
					e.notify();
				}
			}
		};
		Main.timer.schedule(gameTickTimer, 0, Settings.GAME_TICK__MS);
	}

	public synchronized void main() throws InterruptedException {
		this.wait();
		
		Animation tail = new Animation(new Point (ball.pos), Color.MAGENTA, Animation.Type.TAIL);
		eventList.add(tail);
		
		//update paddel
		if (paddel.pos + paddel.vel >= 0 && paddel.pos + paddel.size + paddel.vel <= level.size.width)
			paddel.pos += paddel.vel;
		
		Point newPos = new Point(ball.pos.x + ball.vel.x, ball.pos.y + ball.vel.y);
		
		boolean x, y;
		while (true) {
		newPos = new Point(ball.pos.x + ball.vel.x, ball.pos.y + ball.vel.y);
		//test walls
		if (newPos.x < 0 || newPos.x >= level.size.width) {
			ball.vel.x *= -1;
		}
		//test ground
		else if (newPos.y >= level.size.height - 1) {
			if (newPos.x < paddel.pos || newPos.x > paddel.pos + paddel.size) {
				ball.pos.x = 13;
				ball.pos.y = 5;
				ball.vel.x = 0;
				ball.vel.y = 1;
				paddel.pos = 11;
				level.reset();
				return;
			} else if (ball.vel.x != paddel.vel)
				ball.vel.x += paddel.vel;
			ball.vel.y *= -1;
		//test ceiling
		} else if (newPos.y < 0)
			ball.vel.y *= -1;
		
		else if (newPos.x == ball.pos.x && hitBrick(ball.pos.x, newPos.y)) {
			hitBrick(ball.pos.x - 1, newPos.y);
			hitBrick(ball.pos.x + 1, newPos.y);
			ball.vel.y *= -1;
			
		} else if ((x = hitBrick(newPos.x, ball.pos.y)) | (y = hitBrick(ball.pos.x, newPos.y)) | hitBrick(newPos.x, newPos.y))
			if (x && !y)
				ball.vel.x *= -1;
			else if (y && !x)
				ball.vel.y *= -1;
			else {
				ball.vel.x *= -1;
				ball.vel.y *= -1;
			}
		else
			break;

		}ball.pos.x+=ball.vel.x;ball.pos.y+=ball.vel.y;

	}
	
	private boolean hitBrick(int x, int y) {
		if (x < 0 || y < 0 || x >= level.size.width || y >= level.size.height - 1)
			return false;
		Brick b = level.get(x, y);
		int type = b.getType();
		if (type == 0) return false;
		b.hit();
		eventList.add(new Animation(new Point(x,y), Style.brickColor[type], Animation.Type.EXPLOSION));
		return true;
	}
}