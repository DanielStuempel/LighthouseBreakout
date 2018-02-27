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
		Brick[][] m = level.getMap();
		ball.vel.y = 1;
		do {
			//TODO: improve
			Main.sleep(1000 / tickRate);
			
			for (int q = 0, x = 0; x < level.size.width; x++) {
				for (int y = 0; y < level.size.height; y++) {
					if (m[x][y].isDestroyed())
						m[x][y] = null;
					//draw block
					data[q++] = data[q++] = data[q++] = (byte) (m[x][y] == null ? 0 : 100 + m[x][y].getType() * 10);
				}
			}
			
			//draw ball
			int p = (ball.pos.x + ball.pos.y * 28 ) * 3;
			data[p++] = -1;
			data[p++] = 0;
			data[p] = 0;
			
			//random direction when hitting ground for testing
			if (ball.pos.y == level.size.height - 1) ball.vel.x = (int) (Math.random() * 3) - 1;
			
			//move ball
			ball.pos.x += ball.pos.x > 0 && ball.pos.x < level.size.width - 1
					&& m[ball.pos.x + ball.vel.x][ball.pos.y] == null ? ball.vel.x
							: m[ball.pos.x - ball.vel.x][ball.pos.y] == null ? ball.vel.x *= -1 : 0;
			ball.pos.y += ball.pos.y > 0 && ball.pos.y < level.size.height - 1
					&& m[ball.pos.x][ball.pos.y + ball.vel.y] == null ? ball.vel.y
							: m[ball.pos.x][ball.pos.y - ball.vel.y] == null ? ball.vel.y *= -1 : 0;

		} while (ticks == -1 || --ticks > 0);
	}
}
