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
		TAIL(8), EXPLOSION(20), BRICKHIT(20);

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
		this.color = new Color((int) (c.getRed() /2), (int) (c.getGreen() / 2), (int) (c.getBlue() / 2));
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
		if (frame++ > type.getFrameCount() / 2)
			tail[origin.x][origin.y] = color;
		else
			tail[origin.x][origin.y] = color;
		return tail;
	}
	
	private Color[][] hitStage() {
		Color[][] hit = new Color[28][14];
		if (frame <= 5) {
			hit[origin.x][origin.y] = color2;
		}
		else if(frame <= 10) {
			hit[origin.x][origin.y] = color;
		}
		else if (frame <= 15) {
			hit[origin.x][origin.y] = color2;
		}
		else {
			hit[origin.x][origin.y] = color;
		}
		frame++;
		return hit;
	}

	private Color[][] explosionStage() {
		Color[][] explosion = new Color[28][14];
		if (frame <= type.getFrameCount()/2) {
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
