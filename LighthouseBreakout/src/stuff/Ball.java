package stuff;

import java.awt.Point;

public class Ball {
	public Point pos;
	public Point vel;

	public Ball(int x, int y) {
		this(new Point(x, y));
	}

	public Ball(Point pos) {
		this.pos = pos;
		vel = new Point();
	}
}