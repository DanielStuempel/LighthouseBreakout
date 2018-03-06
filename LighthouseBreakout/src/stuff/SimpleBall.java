package stuff;

import java.awt.Point;

public class SimpleBall {
	public Point pos;
	public Point vel;

	public SimpleBall(int x, int y) {
		this(new Point(x, y));
	}

	public SimpleBall(Point pos) {
		this.pos = pos;
		vel = new Point();
	}
	public Point getPosition() {
		return pos;
	}
}