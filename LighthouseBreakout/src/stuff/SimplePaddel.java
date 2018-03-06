package stuff;

import java.awt.Point;

public class SimplePaddel {
	public int pos;
	public int size;

	public SimplePaddel(int y, int w) {
		pos = y;
		size = w;
	}
	public Point getPosition() {
		return new Point(pos, 13);
	}
	public Point getSize() {
		return new Point(size,1);
	}
}
