package stuff;

public class Engine implements Runnable {
	private int tickRate = 10;

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
		main(-1);
	}

	public void init() {
		ball = new Ball();
	}

	public void main(int ticks) {
		ball.vel.x = -1;
		ball.vel.y = 1;
		do {
			// TODO: improve
			Main.sleep(1000 / tickRate);

			for (int q = 0, y = 0; y < level.size.height; y++) {
				for (int x = 0; x < level.size.width; x++) {
					// draw block
					Brick b = level.get(x, y);
					data[q++] = data[q++] = data[q++] = (byte) (b == null ? 0 : 100 + b.getType() * 10);
				}
			}

			// draw ball
			int p = (ball.pos.x + ball.pos.y * 28) * 3;
			data[p++] = -1;
			data[p++] = 0;
			data[p] = 0;

			// random position when hitting ground for testing
			// if (ball.pos.y == level.size.height - 1 || ball.pos.y == 0)
			// ball.pos.x = (int) (Math.random() * (level.size.width - 2)) + 1;
			//
			// move ball

			Boolean changedX = false;
			Boolean changedY = false;
			
			if (ball.pos.x + ball.vel.x > 27) {
				ball.vel.x *= -1;
			}
			if (ball.pos.x + ball.vel.x < 0) {
				ball.vel.x *= -1;
				changedX = true;
			}
			if (ball.pos.y + ball.vel.y < 0) {
				ball.vel.y *= -1;
				changedY = true;
			}
			if (ball.pos.y + ball.vel.y > 13) {
				ball.vel.y *= -1;
				changedY = true;
			}

			Brick checkX = level.get(ball.pos.x + ball.vel.x, ball.pos.y);
			Brick checkY = level.get(ball.pos.x, ball.pos.y + ball.vel.y);
			Brick checkXY = level.get(ball.pos.x + ball.vel.x, ball.pos.y + ball.vel.y);

			ball.pos.x += ball.vel.x;
			ball.pos.y += ball.vel.y;
			
			if (checkX != null) {
				checkX.hit();
				if (!changedX)
					ball.vel.x *= -1;
				changedX = true;
			}
			if (checkY != null) {
				checkY.hit();
				if (!changedY)
					ball.vel.y *= -1;
				changedY = true;
			}
			if (checkXY != null) {
				checkXY.hit();
				if (!changedX)
					ball.vel.x *= -1;
				if (!changedY)
					ball.vel.y *= -1;
			}

			// ball.pos.x += ball.pos.x > 0 && ball.pos.x < 13 ? ball.vel.x : (ball.vel.x *=
			// -1);
			// ball.pos.y += ball.pos.y > 0 && ball.pos.y < 27 ? ball.vel.y : (ball.vel.y *=
			// -1);

			// daniel smarte fast funktionierrende l�sung
			// ball.pos.x += ball.pos.x > 0 && ball.pos.x < level.size.width - 1
			// && level.get(ball.pos.x + ball.vel.x, ball.pos.y) == null ? ball.vel.x
			// : level.get(ball.pos.x - ball.vel.x, ball.pos.y) == null ? ball.vel.x *= -1 :
			// 0;
			// ball.pos.y += ball.pos.y > 0 && ball.pos.y < level.size.height - 1
			// && level.get(ball.pos.x, ball.pos.y + ball.vel.y) == null ? ball.vel.y
			// : level.get(ball.pos.x, ball.pos.y - ball.vel.y) == null ? ball.vel.y *= -1 :
			// 0;
			//
		} while (ticks == -1 || --ticks > 0);
	}
}
