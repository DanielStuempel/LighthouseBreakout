package stuff;

import java.awt.Color;
import java.awt.Point;
import java.util.LinkedList;

public class Engine implements Runnable {
	Level level;
	Paddel paddel;
	Ball ball;
	LinkedList<Animation> eventList;

	public Engine(Level level, Paddel paddel, Ball ball, LinkedList<Animation> eventList, String... args) {
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
		
		Engine e = this;
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
		
		//random position when hitting ground for testing
		if (ball.pos.y == level.size.height - 2 || ball.pos.y == 0) {
//			ball.pos.x = (int) (Math.random() * (level.size.width - 2)) + 1;
			ball.vel.x = (int) (Math.random() * 3) - 1;
		}
		
		if (paddel.pos + paddel.vel >= 0 && paddel.pos + paddel.size + paddel.vel <= level.size.width)
			paddel.pos += paddel.vel;

		Point newPos = ball.pos.getLocation();
		Point curPos = ball.pos.getLocation();

		// distance the ball is to travel in each direction
		Point dist = new Point(Math.abs(ball.vel.x), Math.abs(ball.vel.y));

		// balls direction: -1, 0, 1
		Point dir = new Point(
				dist.x == 0 ? 0 : ball.vel.x / dist.x,
				dist.y == 0 ? 0 : ball.vel.y / dist.y);
		
		boolean x, y;
		
		while (dist.x > 0 || dist.y > 0) {
			newPos.x = curPos.x + (dist.x > 0 ? dir.x : 0);
			newPos.y = curPos.y + (dist.y > 0 ? dir.y : 0);
			if (dist.x > dist.y) {
				if (testWall(newPos.x) || !testCeiling(newPos.y) && !testGround(newPos.y, newPos.x) && testBrick(newPos.x, curPos.y) | testBrick(curPos.x, newPos.y))
					dir.x = -dir.x;
				else {
					dist.x--;
					curPos.x += dir.x;
				}
			} else if (dist.x < dist.y) {
				if (testGround(newPos.y, newPos.x) || testCeiling(newPos.y) || !testWall(newPos.x) && (testBrick(curPos.x, newPos.y) | testBrick(newPos.x, newPos.y)))
					dir.y *= -1;
				else {
					dist.y--;
					curPos.y += dir.y;
				}
			} else {
				if (testWall(newPos.x))
					dir.x *= -1;
				else if (testGround(newPos.y, newPos.x)) {
					dir.y *= -1;
				
				} else if (testCeiling(newPos.y))
					dir.y *= -1;
				else if ((x = testBrick(newPos.x, curPos.y)) | (y = testBrick(curPos.x, newPos.y)) | testBrick(newPos.x, newPos.y))
					if (x && !y)
						dir.x *= -1;
					else if (y && !x)
						dir.y *= -1;
					else {
						dir.x *= -1;
						dir.y *= -1;
					}
				else {
					dist.x--;
					dist.y--;
					curPos.x += dir.x;
					curPos.y += dir.y;
				}
			}
		}
		
		//move ball
		ball.pos.x = curPos.x;
		ball.pos.y = curPos.y;
		
		//check if the ball has changed it's direction and act accordingly
		if (ball.vel.x * dir.x < 0)
			ball.vel.x *= -1;
		if (ball.vel.y * dir.y < 0)
			ball.vel.y *= -1;
	}
	
	private boolean testBrick(int x, int y) {
		boolean hit = level.get(x, y).hit();
		if (hit) {
			Brick b = level.get(x, y);
			Animation expl = new Animation(new Point(x,y), Style.brickColor[b.getType()+1], Animation.Type.EXPLOSION);
			eventList.add(expl);
		}
		return hit;
	}
	
	private boolean testWall(int x) {
		boolean hit = x < 0 || x >= level.size.width;
		return hit;
	}
	
	private boolean testCeiling(int y) {
		return y < 0;
	}
	
	private boolean testGround(int y, int x) {
		boolean hit = y >= level.size.height - 1;
		if (hit) {
			if (!testPaddel(x)) {
				System.out.println("n00b");
			}
		}
		return hit;
	}
	
	private boolean testPaddel(int x) {
		return x >= paddel.pos && x <= paddel.pos + paddel.size;
	}
}
