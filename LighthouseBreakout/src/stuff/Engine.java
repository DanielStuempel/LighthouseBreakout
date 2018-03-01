package stuff;

import java.awt.Point;

public class Engine implements Runnable {
	private int tickRate = 100;	//100

	private TickTimer gameTickTimer;
	
	Level level;
	Ball ball;

	public Engine(Level level, Ball ball) {
		this.level = level;
		this.ball = ball;
	}

	@Override
	public void run() {
		init();
		gameTickTimer = new TickTimer() {
			@Override
			public void tick() {
				main();
			}
		};
		Main.systemTimer.schedule(gameTickTimer, 0, tickRate);
	}

	public void init() {
		ball.vel.x = 0;
		ball.vel.y = 1;
	}

	public void main() {
		// TODO: improve
		// TODO: don't output in engine thread
		
		 //random position when hitting ground for testing
		 if (ball.pos.y == level.size.height - 1 || ball.pos.y == 0) {
			 ball.pos.x = (int) (Math.random() * (level.size.width - 2)) + 1;
			 ball.vel.x = (int) (Math.random() * 3) - 1;
		 }

		Point newPos = ball.pos.getLocation();
		Point curPos = ball.pos.getLocation();

		// distance the ball is to travel in each direction
		Point dist = new Point(Math.abs(ball.vel.x), Math.abs(ball.vel.y));

		// balls direction: -1, 0, 1
		Point dir = new Point(
				dist.x == 0 ? ball.vel.x : ball.vel.x / dist.x,
				dist.y == 0 ? ball.vel.y : ball.vel.y / dist.y);

		//TODO: brick might get hit twice and not be recognized as broken
		//TODO: improve... a lot.
		//TODO: implement helper functions
		//TODO: paddel
		while (dist.x > 0 || dist.y > 0) {
			newPos.x = curPos.x + (dist.x > 0 ? dir.x : 0);
			newPos.y = curPos.y + (dist.y > 0 ? dir.y : 0);
			if (dist.x > dist.y) {
				if (newPos.x < 0 || newPos.x >= level.size.width || newPos.y >= 0 && newPos.y < level.size.height && level.get(newPos.x, curPos.y).hit() | level.get(curPos.x, newPos.y).hit())
					dir.x = -dir.x;
				else {
					dist.x--;
					curPos.x += dir.x;
				}
			} else if (dist.x < dist.y) {
				if (newPos.y < 0 || newPos.y >= level.size.height || newPos.x >= 0 && newPos.x < level.size.width && (level.get(curPos.x, newPos.y).hit() | level.get(newPos.x, newPos.y).hit()))
					dir.y *= -1;
				else {
					dist.y--;
					curPos.y += dir.y;
				}
			} else {
				if (newPos.x < 0 || newPos.x >= level.size.width)
					dir.x *= -1;
				else if (newPos.y < 0 || newPos.y >= level.size.height)
					dir.y *= -1;
				else if (level.get(newPos.x, curPos.y).hit() | level.get(curPos.x, newPos.y).hit() | level.get(newPos.x, newPos.y).hit()) {
					dir.x *= -1;
					dir.y *= -1;
				} else {
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

//		ball.pos.x += ball.pos.x > 0 && ball.pos.x < level.size.width - 1
//				&& level.get(ball.pos.x + ball.vel.x, ball.pos.y) == null ? ball.vel.x
//						: level.get(ball.pos.x - ball.vel.x, ball.pos.y) == null ? ball.vel.x *= -1 : 0;
//		ball.pos.y += ball.pos.y > 0 && ball.pos.y < level.size.height - 1
//				&& level.get(ball.pos.x, ball.pos.y + ball.vel.y) == null ? ball.vel.y
//						: level.get(ball.pos.x, ball.pos.y - ball.vel.y) == null ? ball.vel.y *= -1 : 0;
	}
}
