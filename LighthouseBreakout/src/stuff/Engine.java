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
		do {
			//TODO: improve
			Main.sleep(1000 / tickRate);
			
			Brick b = m[ball.pos.x][ball.pos.y];
//			System.out.println(b == null ? null : b.getType());
			if (b != null)
				m[ball.pos.x][ball.pos.y] = b.hit();
			
			for (int q = 0; q < data.length;) {
				Brick c = m[q / 3 % level.size.width][q / 3 / level.size.width];
				//draw block
				data[q++] = data[q++] = data[q++] = (byte) (c == null ? 0 : 100 + c.getType() * 10);
			}
			
			//draw ball
			int p = (ball.pos.x + ball.pos.y * 28 ) * 3;
			data[p++] = -1;
			data[p++] = 0;
			data[p] = 0;
			
			//move ball
			ball.pos.x += ball.pos.x > 0 && ball.pos.x < level.size.width - 1 ? ball.vel.x : (ball.vel.x *= -1);
			ball.pos.y += ball.pos.y > 0 && ball.pos.y < level.size.height - 1 ? ball.vel.y : (ball.vel.y *= -1);
			
		} while (ticks == -1 || --ticks > 0);
	}
}
