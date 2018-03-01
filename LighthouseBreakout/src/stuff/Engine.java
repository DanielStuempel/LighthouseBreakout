package stuff;

import java.awt.Point;

public class Engine implements Runnable {
	private int tickRate = 100;	//100

	private TickTimer gameTickTimer;
	
	private byte[] data;
	Level level;
	Ball ball;

	public Engine(Level level, byte[] data) {
		this.level = level;
		this.data = data;
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
		ball = new Ball(1, 12);
		ball.vel.x = -1;
		ball.vel.y = 2;
	}

	public void main() {
		// TODO: improve
		// TODO: don't output in engine thread
		for (int q = 0, y = 0; y < level.size.height; y++) {
			for (int x = 0; x < level.size.width; x++) {
				// draw block
				Brick b = level.get(x, y);
				//Color Brick
				//data[q++] = data[q++] = data[q++] = (byte) (b == null ? 0 : 100 + b.getType() * 10);
				data[q++] = (byte) (b == null ? 0 : Style.theme.BrickStyle[b.getType()][0]);
				data[q++] = (byte) (b == null ? 0 : Style.theme.BrickStyle[b.getType()][1]);
				data[q++] = (byte) (b == null ? 0 : Style.theme.BrickStyle[b.getType()][2]);

			}
		}

		// draw ball
		int p = (ball.pos.x + ball.pos.y * 28) * 3;
		data[p++] = Style.theme.BallStyle[0];
		data[p++] = Style.theme.BallStyle[1];
		data[p] = Style.theme.BallStyle[2];

		// random position when hitting ground for testing
		// if (ball.pos.y == level.size.height - 1 || ball.pos.y == 0)
		// ball.pos.x = (int) (Math.random() * (level.size.width - 2)) + 1;

		Point newPos = ball.pos.getLocation();
		Point curPos = ball.pos.getLocation();

		// distance the ball is to travel in each direction
		Point dist = new Point(Math.abs(ball.vel.x), Math.abs(ball.vel.y));

		// hold either one or negative one depending on balls direction of motion
		Point dir = new Point(ball.vel.x / dist.x, ball.vel.y / dist.y);

		Brick a, b, c;

		//TODO: brick might get hit twice and not be recognized as broken
		//TODO: improve... a lot.
		//TODO: implement helper functions
		for (boolean collision = false; dist.x > 0 || dist.y > 0; collision = false) {
//			System.out.println("testing pos " + curPos);
//			System.out.println("left to go: " + dist);
			newPos.x = curPos.x + (dist.x > 0 ? dir.x : 0);
			newPos.y = curPos.y + (dist.y > 0 ? dir.y : 0);
			if (dist.x > dist.y) {
				if (newPos.x < 0 || newPos.x >= level.size.width) {
					collision = true;
				} else if (newPos.y >= 0 && newPos.y < level.size.height) {
					a = level.get(newPos.x, curPos.y);
					b = level.get(newPos.x, newPos.y);
					if (a != null) {
						a.hit();
						collision = true;
					}
					if (b != null) {
						b.hit();
						collision = true;
					}
				}
				if (collision) {
					dir.x = -dir.x;
				} else {
					dist.x--;
					curPos.x += dir.x;
				}
			} else if (dist.x < dist.y) {
				if (newPos.y < 0 || newPos.y >= level.size.height) {
					// TODO: paddel
					collision = true;
				} else if (newPos.x >= 0 && newPos.x < level.size.width) {
					a = level.get(curPos.x, newPos.y);
					b = level.get(newPos.x, newPos.y);
					if (a != null) {
						a.hit();
						collision = true;
					}
					if (b != null) {
						b.hit();
						collision = true;
					}
				}
				if (collision) {
					dir.y *= -1;
				} else {
					dist.y--;
					curPos.y += dir.y;
				}
			} else {
				if (newPos.x < 0 || newPos.x >= level.size.width) {
					//TODO: maybe use flag to not change at all
					dir.y *= -1;
					collision = true;
				} else if (newPos.y < 0 || newPos.y >= level.size.height) {
					//TODO: paddel
					dir.x *= -1;
					collision = true;
				} else {
					a = level.get(newPos.x, curPos.y);
					b = level.get(curPos.x, newPos.y);
					c = level.get(newPos.x, newPos.y);
					if (a != null) {
						a.hit();
						collision = true;
					}
					if (b != null) {
						b.hit();
						collision = true;
					}
					if (c != null) {
						c.hit();
						collision = true;
					}
				}
				if (collision) {
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

		/*
		 * boolean changedX = false; boolean changedY = false;
		 * 
		 * 
		 * if (ball.pos.x + ball.vel.x > 27) { ball.vel.x *= -1; changedX = true; } if
		 * (ball.pos.x + ball.vel.x < 0) { ball.vel.x *= -1; changedX = true; } if
		 * (ball.pos.y + ball.vel.y < 0) { ball.vel.y *= -1; changedY = true; } if
		 * (ball.pos.y + ball.vel.y > 13) { ball.vel.y *= -1; changedY = true; }
		 * 
		 * Brick checkX = level.get(ball.pos.x + ball.vel.x, ball.pos.y); Brick checkY =
		 * level.get(ball.pos.x, ball.pos.y + ball.vel.y); Brick checkXY =
		 * level.get(ball.pos.x + ball.vel.x, ball.pos.y + ball.vel.y);
		 * 
		 * ball.pos.x += ball.vel.x; ball.pos.y += ball.vel.y;
		 * 
		 * if (checkX != null) { checkX.hit(); if (!changedX) ball.vel.x *= -1; changedX
		 * = true; } if (checkY != null) { checkY.hit(); if (!changedY) ball.vel.y *=
		 * -1; changedY = true; } if (checkXY != null) { checkXY.hit(); if (!changedX)
		 * ball.vel.x *= -1; if (!changedY) ball.vel.y *= -1; }
		 */

		// ball.pos.x += ball.pos.x > 0 && ball.pos.x < 13 ? ball.vel.x : (ball.vel.x *=
		// -1);
		// ball.pos.y += ball.pos.y > 0 && ball.pos.y < 27 ? ball.vel.y : (ball.vel.y *=
		// -1);

		// ball.pos.x += ball.pos.x > 0 && ball.pos.x < level.size.width - 1
		// && level.get(ball.pos.x + ball.vel.x, ball.pos.y) == null ? ball.vel.x
		// : level.get(ball.pos.x - ball.vel.x, ball.pos.y) == null ? ball.vel.x *= -1 :
		// 0;
		// ball.pos.y += ball.pos.y > 0 && ball.pos.y < level.size.height - 1
		// && level.get(ball.pos.x, ball.pos.y + ball.vel.y) == null ? ball.vel.y
		// : level.get(ball.pos.x, ball.pos.y - ball.vel.y) == null ? ball.vel.y *= -1 :
		// 0;
		//
	}
}
