package stuff;

import java.awt.Color;
import java.awt.Point;
import java.util.Iterator;

import stuff.Style.Theme;

public class Animation implements Iterator<Color[][]> {
	private int frame;
	private Point origin;
	private Color color, color2;
	private Type type;

	public static enum Type {
		TAIL(8), EXPLOSION(20), BRICKHIT(20);

		private int frameCount;

		private Type(int l) {
			frameCount = l;
		}
	}

	public Animation(Point origin, Color c, Type type) {
		this.type = type;
		this.origin = origin;
		if (Settings.THEME == Theme.LIGHT) {
			int red = c.getRed() <= 200 ? c.getRed()+50 : c.getRed();
			int green = c.getGreen() <= 200 ? c.getGreen()+50 : c.getGreen();
			int blue = c.getBlue() <= 200 ? c.getBlue()+50 : c.getBlue();
			this.color = new Color(red,green,blue);
		} else {
			this.color = new Color((int) (c.getRed() / 2.5), (int) (c.getGreen() / 2.5), (int) (c.getBlue() / 2.5));
		}
	}

	public Animation(Point origin, Brick brick, Type type) {
		this.origin = origin;
		this.type = type;
		this.color = Style.brickColor[brick.getType()];
		this.color2 = Style.brickColor[brick.getType() - 1];
	}

	@Override
	public boolean hasNext() {
		return frame < type.frameCount;
	}

	@Override
	public Color[][] next() {
		if (!hasNext())
			return null;
		switch (type) {
		case EXPLOSION:
			return explosionStage();
		case BRICKHIT:
			return hitStage();
		case TAIL:
			return tailStage();
		default:
			return null;
		}
	}

	private Color[][] tailStage() {
		Color[][] tail = new Color[28][14];
		if (frame++ > type.frameCount / 2)
			tail[origin.x][origin.y] = color;
		else
			tail[origin.x][origin.y] = color;
		return tail;
	}

	private Color[][] hitStage() {
		Color[][] hit = new Color[28][14];
		if (frame <= type.frameCount / 4)
			hit[origin.x][origin.y] = color2;
		else if (frame <= type.frameCount / 2)
			hit[origin.x][origin.y] = color;
		else if (frame <= type.frameCount / 4 * 3)
			hit[origin.x][origin.y] = color2;
		else
			hit[origin.x][origin.y] = color;
		frame++;
		return hit;
	}

	private Color[][] explosionStage() {
		Color[][] explosion = new Color[28][14];
		if (frame <= type.frameCount / 2) {
			for (int x = 0; x < 28; x++) {
				for (int y = 0; y < 14; y++) {
					if (((origin.x - 1 == x || origin.x + 1 == x) && origin.y == y)
							|| ((origin.y - 1 == y || origin.y + 1 == y) && origin.x == x)) {
						explosion[x][y] = color;
					}
				}
			}
		} else {
			for (int x = 0; x < 28; x++) {
				for (int y = 0; y < 14; y++) {
					if (((origin.x - 1 == x || origin.x + 1 == x) && (origin.y - 1 == y || origin.y + 1 == y))) {
						explosion[x][y] = color;
					}
				}
			}
		}
		frame++;
		return explosion;
	}
}