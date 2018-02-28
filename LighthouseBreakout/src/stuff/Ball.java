package stuff;

import java.awt.Point;

public class Ball {
	public Point pos;
	public Point vel;

	public Ball() {
		this(1, 12);
	}

	public Ball(int x, int y) {
		this(new Point(x, y));
	}

	public Ball(Point pos) {
		this.pos = pos;
		vel = new Point();
	}

}
