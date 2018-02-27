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
		this.vel.x = this.pos.x+this.vel.x > 13 ? -this.vel.x : this.vel.x;
		this.vel.x = this.pos.x+this.vel.x < 0  ? -this.vel.x : this.vel.x;
		this.vel.y = this.pos.y+this.vel.y > 27 ? -this.vel.y : this.vel.y;
		this.vel.y = this.pos.y+this.vel.y < 0  ? -this.vel.y : this.vel.y;
		
		this.pos.x += (int)this.vel.x;
		this.pos.y += (int)this.vel.y;
	}
}
