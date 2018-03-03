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
		ball.vel.x = 1;
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
		Main.timer.schedule(gameTickTimer, 0, 100);
	}

	public synchronized void main() throws InterruptedException {
		this.wait();
		
		Animation tail = new Animation(new Point (ball.pos), Color.MAGENTA, Animation.Type.TAIL);
		eventList.add(tail);
		
		if (paddel.pos + paddel.vel >= 0 && paddel.pos + paddel.size + paddel.vel <= level.size.width)
			paddel.pos += paddel.vel;

		Point newPos = new Point(ball.pos.x + ball.vel.x, ball.pos.y + ball.vel.y);
		
		boolean x, y;
		//test walls
		if (newPos.x < 0 || newPos.x >= level.size.width)
			ball.vel.x *= -1;
		//test ground
		if (newPos.y >= level.size.height - 1) {
			if (newPos.x < paddel.pos || newPos.x > paddel.pos + paddel.size) {
				ball.pos.x = 13;
				ball.pos.y = 5;
				ball.vel.x = 0;
				ball.vel.y = 1;
				paddel.pos = 11;
				level.reset();
				return;
			} else
				ball.vel.x = paddel.vel;
			ball.vel.y *= -1;
		//test ceiling
		} else if (newPos.y < 0)
			ball.vel.y *= -1;
		if ((x = testBrick(newPos.x, ball.pos.y)) | (y = testBrick(ball.pos.x, newPos.y)) | testBrick(newPos.x, newPos.y))
			if (x && !y)
				ball.vel.x *= -1;
			else if (y && !x)
				ball.vel.y *= -1;
			else {
				ball.vel.x *= -1;
				ball.vel.y *= -1;
			}
	
			ball.pos.x += ball.vel.x;
			ball.pos.y += ball.vel.y;
	}
	
	private boolean testBrick(int x, int y) {
		if (x < 0 || y < 0 || x >= level.size.width || y >= level.size.height - 1)
			return false;
		boolean hit = level.get(x, y).hit();
		if (hit) {
			Brick b = level.get(x, y);
			Animation expl = new Animation(new Point(x,y), Style.brickColor[b.getType()+1], Animation.Type.EXPLOSION);
			eventList.add(expl);
		}
		return hit;
	}
}