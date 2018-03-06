package stuff;

import java.awt.Point;

public class SimplePaddel {
	public int pos;
	public int size;

	public SimplePaddel(int y, int w) {
		pos = y;
		size = w;
	}
	public int getPosition() {
		return pos;
	}
	public Point getSize() {
		return new Point(size,1);
	}
}
