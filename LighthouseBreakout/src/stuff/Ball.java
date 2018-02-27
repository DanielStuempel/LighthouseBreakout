package stuff;

import java.awt.Point;

public class Ball {
	public Point pos;
	public Point vel = new Point(-1, 0);

	public Ball() {
		this(0, 13);
	}

	public Ball(int x, int y) {
		this(new Point(x, y));
	}

	public Ball(Point pos) {
		this.pos = pos;
	}
	public void move() {
		pos.x += pos.x > 0 && pos.x < 13 ? vel.x : (vel.x *= -1);
		pos.y += pos.y > 0 && pos.y < 27 ? vel.y : (vel.y *= -1);
	}
}
