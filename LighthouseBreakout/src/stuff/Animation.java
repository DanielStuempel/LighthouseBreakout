package stuff;

import java.awt.Color;
import java.awt.Point;
import java.util.Iterator;

public class Animation implements Iterator<Color[][]> {

	public int progress;
	public Point point;
	public Color color = Color.WHITE;
	private Type type;

	public static enum Type {
		TAIL(3), EXPLOSION(4);

		private int length;

		private Type(int l) {
			length = l;
		}

		private int getLength() {
			return length;
		}
	}

	public Animation(Point origin, Color c, Type type) {
		this.type = type;
		this.point = origin;
		this.color = c;
		this.progress = 0;
	}

	@Override
	public boolean hasNext() {
		return progress < type.getLength();
	}

	@Override
	public Color[][] next() {
		if (!hasNext()) {
			return null;
		}
		switch (type) {
		case EXPLOSION:
			return explosionStage();
		case TAIL:
			return tailStage();
		default:
			return null;
		}
	}

	private Color[][] tailStage() {
		Color[][] tail = new Color[28][14];
		if (progress == 0) tail[point.x][point.y] = color;
		if (progress == 1) tail[point.x][point.y] = color;
		if (progress == 2) tail[point.x][point.y] = color;
		progress++;
		return tail;
	}

	private Color[][] explosionStage() {
		Color[][] explosion = new Color[28][14];
		if (progress <= 1) {
			for (int x = 0; x < 28; x++) {
				for (int y = 0; y < 14; y++) {
					if (((point.x - 1 == x || point.x + 1 == x) && point.y == y)
							|| ((point.y - 1 == y || point.y + 1 == y) && point.x == x)) {
						explosion[x][y] = color;
					}
				}
			}
		} else {
			for (int x = 0; x < 28; x++) {
				for (int y = 0; y < 14; y++) {
					if (((point.x - 1 == x || point.x + 1 == x) && (point.y -1 == y || point.y +1 == y))){
						explosion[x][y] = color;
					}
				}
			}
		}
		progress++;
		return explosion;
	}
}
