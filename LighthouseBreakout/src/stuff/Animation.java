package stuff;

import java.awt.Color;
import java.awt.Point;
import java.util.Iterator;

public class Animation implements Iterator<Color[][]> {
	private int frame;
	private Point origin;
	private Color color, color2;
	private Type type;
	private Brick brick;

	public static enum Type {
		TAIL(2), EXPLOSION(4), BRICKHIT(4);

		private int frameCount;

		private Type(int l) {
			frameCount = l;
		}

		private int getFrameCount() {
			return frameCount;
		}
	}

	public Animation(Point origin, Color c, Type type) {
		this.type = type;
		this.origin = origin;
		this.color = new Color((int) (c.getRed() / 1.5), (int) (c.getGreen() / 1.5), (int) (c.getBlue() / 1.5));
	}
	
	public Animation(Point origin, Brick brick, Type type) {
		this.origin = origin;
		this.brick = brick;
		this.type = type;
		this.color = Style.brickColor[brick.getType()];
		this.color2 =  Style.brickColor[brick.getType()-1]; 
	}

	@Override
	public boolean hasNext() {
		return frame < type.getFrameCount();
	}

	@Override
	public Color[][] next() {
		if (!hasNext())
			return null;
		switch (type) {
		case EXPLOSION:
			this.color =  new Color((int) (color.getRed() / 2.5), (int) (color.getGreen() / 2.5), (int) (color.getBlue() / 2.5));
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
		switch (frame++) {
		case 0:
			tail[origin.x][origin.y] = color;
			break;
		case 1:
			tail[origin.x][origin.y] = color;
			break;
		}
		return tail;
	}
	
	private Color[][] hitStage() {
		Color[][] hit = new Color[28][14];
		switch (frame++) {
		case 0:
			hit[origin.x][origin.y] = color2;
			break;
		case 1:
			hit[origin.x][origin.y] = color;
			break;
		case 2:
			hit[origin.x][origin.y] = color2;
			break;
		case 3:
			hit[origin.x][origin.y] = color;
			break;
		}
		return hit;
	}

	private Color[][] explosionStage() {
		Color[][] explosion = new Color[28][14];
		if (frame++ <= 1) {
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
		return explosion;
	}
}
